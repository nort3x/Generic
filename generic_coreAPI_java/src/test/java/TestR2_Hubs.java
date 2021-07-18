import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.vaccination.VaccineException;
import it.polito.oop.vaccination.Vaccines;

public class TestR2_Hubs {
	
	private Vaccines v;
	
	@Before
	public void setUp() throws VaccineException {
		v = new Vaccines();
		TestUtils.generateAndAddPerson(10000, v);
    	v.setAgeIntervals(30,50,60,70);

    	v.defineHub("Hub 1");
    	v.defineHub("Hub 2");
    	v.defineHub("Hub 3");
	}


	@Test
	public void testCreateHubs() throws VaccineException {
    	Collection<String> hubs = v.getHubs();
    	assertNotNull("No hub collection returned", hubs);
    	assertEquals("Wrong number of hubs", 3, hubs.size());
    	assertTrue("Missing hub", hubs.contains("Hub 1"));
	}

	@Test(expected = VaccineException.class)
	public void testDuplicateHub() throws VaccineException {
    	v.defineHub("Hub 1");
	}

	@Test(expected = VaccineException.class)
	public void testHubStaffWrongHub() throws VaccineException {
    	v.setStaff("Hub Nonexistent", 7, 6, 4);
	}

	@Test(expected = VaccineException.class)
	public void testHubStaffNegative() throws VaccineException {
    	v.setStaff("Hub Nonexistent", -1, 6, 4);
	}


	@Test
	public void textCapacity() throws VaccineException {
    	v.setStaff("Hub 1", 7, 6, 4);
    	double capacity = v.estimateHourlyCapacity("Hub 1");
    	
    	assertEquals( Math.min(7*10.0, Math.min(6*12.0, 4*20.0)), capacity, 0.1);

    	v.setStaff("Hub 3", 4, 3, 2);
    	capacity = v.estimateHourlyCapacity("Hub 3");
    	
    	assertEquals( Math.min(4*10.0, Math.min(3*12.0, 2*20.0)), capacity, 0.1);
}

	@Test(expected = VaccineException.class)
	public void testCapacityUnstaffed() throws VaccineException {
		v.estimateHourlyCapacity("Hub 1");
	}

	@Test(expected = VaccineException.class)
	public void testCapacityNonexist() throws VaccineException {
		v.estimateHourlyCapacity("Hub Nonexist");
	}
}
