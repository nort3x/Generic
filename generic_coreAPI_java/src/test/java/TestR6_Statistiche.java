import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.vaccination.VaccineException;
import it.polito.oop.vaccination.Vaccines;

public class TestR6_Statistiche {
	private Vaccines v;
	private double priority = 0.4;
	
	@Before
	public void setUp() throws VaccineException {
		v = new Vaccines();
		TestUtils.generateAndAddPerson(20000, v);
    	v.setAgeIntervals(30,50,60,70);

    	v.defineHub("Hub 1");
    	v.defineHub("Hub 2");
    	v.defineHub("Hub 3");
    	
    	v.setStaff("Hub 1", 7, 6, 4);
    	v.setStaff("Hub 2", 4, 3, 2);
    	v.setStaff("Hub 3", 3, 3, 2);

    	v.setHours(8,8,8,8,8,6,4);
    	
    	v.weekAllocate();
	}

	@Test
	public void propAllocated() throws VaccineException {

    	double propAlloc = v.propAllocated();
    	assertEquals(0.34,propAlloc,0.01);
	}
	
	@Test
	public void testPropAllocatedAge() throws VaccineException {
    	Map<String,Double> allInAge = v.propAllocatedAge();
    	assertNotNull(allInAge);
    	assertEquals(5,allInAge.size());
    	assertEquals(0.16, allInAge.get("[70,+)"), 0.01);

	}
	
	@Test
	public void testDistributionAllocated() throws VaccineException {
    	Map<String,Double> distribution = v.distributionAllocated();
    	assertNotNull(distribution);
    	assertEquals(5,distribution.size());
    	assertEquals(0.48, distribution.get("[70,+)"), 0.01);
    	assertEquals(priority*(1-priority), distribution.get("[60,70)"), 0.01);
    	assertEquals(priority*(1-priority)*(1-priority), distribution.get("[50,60)"), 0.01);
	}

}
