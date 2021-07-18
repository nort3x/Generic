import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import it.polito.oop.vaccination.VaccineException;
import it.polito.oop.vaccination.Vaccines;

public class TestR5_Pianificazione {
	private Vaccines v;
	
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
	}

	@Test
	public void testAllocate() throws VaccineException {
    	List<String> alloc = v.allocate("Hub 1", 1);
    	assertNotNull(alloc);
    	assertEquals("wrong number of allocated people", 70*8, alloc.size() );
    	long n = alloc.stream().filter( p -> v.getAge(p)>=70 ).count();
    	assertTrue("Wrong proportiono of 70+ people", n >= 70*8*0.48 );
	}
	
	@Test
	public void testAllocateDetail() throws VaccineException {
    	List<String> alloc = v.allocate("Hub 1", 1);
    	assertNotNull(alloc);
    	long n = alloc.stream().filter( p -> v.getAge(p)>=70 ).count();
    	assertEquals("Wrong proportiono of 70+ people", 70*8*0.48, n, 1);
	}

	@Test
	public void testClearAllocate() throws VaccineException {
    	List<String> alloc1 = v.allocate("Hub 1", 1);
    	assertNotNull(alloc1);
    	v.clearAllocation();
    	List<String> alloc2 = v.allocate("Hub 1", 1);
    	assertNotNull(alloc1);
    	assertEquals("After clear allocate the same people should be allocated", alloc1,alloc2);
	}

	@Test
	public void testAllocate2() throws VaccineException {
    	List<String> alloc = v.allocate("Hub 2", 1);
    	assertNotNull(alloc);
    	assertEquals("wrong number of allocated people", 36*8, alloc.size() );
    	
	}
	
	@Test
	public void testAllocateWeek() {
    	List<Map<String,List<String>>> wp = v.weekAllocate();
    	assertNotNull("Missing week allocations plan", wp);
    	assertEquals("Week plan should span a whole week", 7, wp.size());
    	Map<Integer,Long> freqs = wp.stream()
    			.flatMap( dm -> dm.values().stream().flatMap(List::stream) )
    			.collect(Collectors.groupingBy(v::getAge,
    					 					   Collectors.counting()));
    	
    	long allocated = freqs.values().stream().mapToLong(i->i).sum();
    	assertEquals("Unallocated people", 6800, allocated);
    	
    	double propOver70 = freqs.entrySet().stream().filter(e->e.getKey()>=70).mapToLong(e->e.getValue()).sum() / (double)allocated;
    	assertEquals("Wrong proportion of over 70", 0.48, propOver70, 0.01);
 
    	double propUnder30 = freqs.entrySet().stream().filter(e->e.getKey()<30).mapToLong(e->e.getValue()).sum() / (double)allocated;
    	assertTrue("Too many under 30", propUnder30 < 0.1);
	}

}
