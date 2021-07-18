import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.vaccination.VaccineException;
import it.polito.oop.vaccination.Vaccines;

public class TestR4_Orario {
	private Vaccines v;
	
	@Before
	public void setUp() throws VaccineException {
		v = new Vaccines();

		v.defineHub("Hub 1");
    	v.defineHub("Hub 2");
    	v.defineHub("Hub 3");
    	
    	v.setStaff("Hub 1", 7, 6, 4);
    	v.setStaff("Hub 2", 4, 3, 2);
    	v.setStaff("Hub 3", 3, 3, 2);

	}

	@Test
	public void testHours() throws VaccineException {
    	v.setHours(8,8,8,8,8,6,4);
    	
    	List<List<String>> hours = v.getHours();
    	//System.out.println(hours);
    	assertNotNull("Missing hours", hours);
    	assertEquals(7,hours.size());
    	assertTrue( hours.stream().allMatch( d -> d.size() >= 16) );
    	assertTrue( hours.stream().allMatch( d -> d.size() <= 32) );
    	assertTrue( hours.stream().map( d-> d.get(0)).allMatch(s -> s.equals("09:00")) );
    	assertEquals(200, hours.stream().mapToInt( d -> d.size()).sum() );
	}

	@Test(expected =VaccineException.class)
	public void testHourOver12() throws VaccineException {
    	v.setHours(8,8,8,8,15,6,4);    	
	}
	
	@Test
	public void testDailyAvailable() throws VaccineException {
		v.setHours(8,8,8,8,8,6,4);
		assertEquals("Wrong daily availability",8*7*10, v.getDailyAvailable("Hub 1", 1));
		assertEquals("Wrong daily availability",6*7*10, v.getDailyAvailable("Hub 1", 5));
	}

	@Test
	public void testAvailable() throws VaccineException {
		v.setHours(8,8,8,8,8,6,4);
    	Map<String,List<Integer>> availability = v.getAvailable();
    	assertNotNull("Missing availability", availability);
    	assertTrue(availability.keySet().containsAll(v.getHubs()));
    	int slots = availability.values().stream().
    							    flatMap(List::stream).mapToInt(Integer::intValue).sum();
		assertEquals("Wrong umber of slots", 6800, slots);
	}

}
