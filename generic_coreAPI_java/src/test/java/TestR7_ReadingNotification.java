import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.vaccination.VaccineException;
import it.polito.oop.vaccination.Vaccines;

public class TestR7_ReadingNotification {
	private static final int NUM_PERSONS = 100;
	private Vaccines v;
	private String fileContent;
	
	@Before
	public void setUp() throws VaccineException {
		v = new Vaccines();
    	ArrayList<String> lines=new ArrayList<>();
    	lines.add("SSN,LAST,FIRST,YEAR");
    	lines.addAll(TestUtils.generatePersons(NUM_PERSONS));
    	fileContent = lines.stream().collect(Collectors.joining("\n"));
	}

	@Test
	public void testLoadPeopleNoErr() throws IOException, VaccineException {
	   	Map<Integer,String> errors = new HashMap<>();
    	v.setLoadListener((i,l)-> errors.put(i, l) );
    	StringReader sr = new StringReader(fileContent);
    	v.loadPeople(sr);
    	assertEquals("Wrong number of persons read", NUM_PERSONS, v.countPeople());
    	assertEquals("Wrong number of errors", 0, errors.size());
	}

	@Test
	public void testLoadPeople() throws IOException, VaccineException {
	   	Map<Integer,String> errors = new HashMap<>();
    	v.setLoadListener((i,l)-> errors.put(i, l) );
    	String incomplete="SSN,LAST,FIRST,YEAR\n"+				//1: header
    					  "ABCDEF01G23H456I,Smith,John,1923\n"+ //2: ok
    					  "ABCDEF01G23H456I,Smith,John,1923\n"+ //3: duplicated snn
    					  "ABCFED01G23H987J,Smith\n"+ 			//4: missing first name and year
    					  "";
    	StringReader sr = new StringReader(incomplete);
    	v.loadPeople(sr);
    	assertEquals("Wrong number of errors", 2, errors.size());
    	assertTrue("Expected errors on lines 3,4 but got: " + errors.keySet(), 
    			   errors.keySet().containsAll(Arrays.asList(3,4)));
	}

	@Test(expected = VaccineException.class)
	public void testWrongHeader1() throws IOException, VaccineException {
    	Reader r = new StringReader("SSN,LAST,FIRST\nMRIDRG47P23H501K,Draghi,Mario,1947");
    	
    	v.loadPeople(r);
	}

	@Test(expected = VaccineException.class)
	public void testWrongHeader2() throws IOException, VaccineException {
    	Reader r = new StringReader("LAST,FIRST,YEAR\nMRIDRG47P23H501K,Draghi,Mario,1947");
    	
    	v.loadPeople(r);
	}

	@Test
	public void testIncomplete() throws IOException, VaccineException {
    	Reader r = new StringReader("SSN,LAST,FIRST,YEAR\n"
    								+ "MRIDRG47P23H501H,Draghi,Mario\n"
    								+ "MRIDRG47P23H501K,Draghi,Mario,1947");
    	
    	v.loadPeople(r);
    	assertEquals(1, v.countPeople());
	}
}
