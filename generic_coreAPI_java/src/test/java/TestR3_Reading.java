import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;


import it.polito.oop.vaccination.VaccineException;
import it.polito.oop.vaccination.Vaccines;

public class TestR3_Reading {
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
	public void testLoadPeople() throws IOException, VaccineException {
    	Reader r = new StringReader(fileContent);
    	
    	v.loadPeople(r);
    	
    	assertEquals(NUM_PERSONS,v.countPeople());
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
