import static it.polito.oop.vaccination.Vaccines.CURRENT_YEAR;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.vaccination.Vaccines;


public class TestR1_Persons {
	
	private Vaccines v;
	
	@Before
	public void setUp() {
		v = new Vaccines();
	}

	@Test
	public void testAddPerson() {
		assertEquals("Initially expeceting no people", 0, v.countPeople());
		v.addPerson("John", "White", "JHNWHT65B21K456P", 1965);
		assertEquals("Wrong number of people", 1, v.countPeople());
	}

	@Test 
	public void testAddMultiplePersons() {
		Set<String> codes = TestUtils.generateAndAddPerson(100, v);

		assertEquals(codes.size(), v.countPeople());
       	
       	for(String person : codes) {
       		String[] parts=person.split(",");
       		String p = v.getPerson(parts[0]);
       		assertNotNull("Missing " + parts[0], p);
       		assertTrue(p.startsWith(parts[0]));
       		assertTrue(p.contains(parts[1]));
       	}
	}
	
	@Test 
	public void tesGetPerson() {
		Set<String> codes = TestUtils.generateAndAddPerson(100, v);

       	for(String person : codes) {
       		String[] parts=person.split(",");
       		String p = v.getPerson(parts[0]);
       		assertNotNull("Missing " + parts[0], p);
       		assertTrue("Person info should start with ssn", p.startsWith(parts[0]));
       		assertTrue("Person info should contain last name", p.contains(parts[1]));
       		assertTrue("Person info should contain first name", p.contains(parts[2]));
       	}
	}

	@Test 
	public void tesGetAge() {
		Set<String> codes = TestUtils.generateAndAddPerson(100, v);

       	for(String person : codes) {
       		String[] parts=person.split(",");
       		int age = v.getAge(parts[0]);
       		int year = Integer.parseInt(parts[3]);
       		assertEquals("Wrong age for person from " + year, CURRENT_YEAR - year, age);
       	}
	}
	
	@Test
	public void testIntervals() {
    	v.setAgeIntervals(30,50,60,70);
    	
    	Collection<String> intervals = v.getAgeIntervals();
    	assertNotNull("Missing intervals", intervals);
    	
    	assertEquals("Wrong number of intervals", 5, intervals.size());
    	String[] expIntervals = {"[0,30)","[30,50)","[50,60)","[60,70)","[70,+)"};
    	assertTrue("Wrong interval labels", intervals.containsAll(Arrays.asList(expIntervals)));
	}

	@Test
	public void testPersonsInIntervals() {
		TestUtils.generateAndAddPerson(10000, v);
    	v.setAgeIntervals(30,50,60,70);

    	Collection<String> people = v.getInInterval("[70,+)");
    	
    	assertNotNull(people);
    	assertEquals("Missing people in interval", 0.30, people.size()/(double)v.countPeople(), 0.01);

    	assertEquals("Missing people in interval", 0.10, v.getInInterval("[60,70)").size()/(double)v.countPeople(), 0.01);
	}
}
