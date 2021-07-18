import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import it.polito.oop.vaccination.VaccineException;
import it.polito.oop.vaccination.Vaccines;

public final class TestApp {

    @Test
    public void test() throws VaccineException, IOException  {
    	Vaccines vs = new Vaccines();
    	
    	Set<String> codes = generateAndAddPerson(
    			10000,  // generatates 10 000 (random) persons
    			
    			// and for each person calls this lambda that adds the person to the vaccine system.
    			(String first, String last, String ssn, int year) -> 
    													vs.addPerson(first,last,ssn, year));
       	
       	assertEquals("Wrong number of persons added", codes.size(), vs.countPeople());
       	
       	for(String person : codes) {
       		String[] parts=person.split(",");
       		String p = vs.getPerson(parts[0]);
       		assertNotNull("Missing " + parts[0], p);
       		assertTrue(p.startsWith(parts[0]));
       		assertTrue(p.contains(parts[1]));
       	}
    	
    	vs.setAgeIntervals(30,40,50,60,70);
    	
    	Collection<String> intervals = vs.getAgeIntervals();
    	assertNotNull("Missing intervals", intervals);
    	
    	assertEquals(6, intervals.size());
    	String[] expIntervals = {"[0,30)","[30,40)","[40,50)","[50,60)","[60,70)","[70,+)"};
    	assertTrue(intervals.containsAll(Arrays.asList(expIntervals)));

    	Collection<String> people = vs.getInInterval("[70,+)");
    	
    	assertNotNull(people);
    	assertEquals("Missing people in interval", 3033, people.size());
    	assertEquals("Missing people in interval", 996, vs.getInInterval("[60,70)").size());

    	
    	// R2
    	
    	vs.defineHub("Hub 1");
    	vs.defineHub("Hub 2");
    	vs.defineHub("Hub 3");
    	
    	Collection<String> hubs = vs.getHubs();
    	assertNotNull("No hub collection returned", hubs);
    	assertEquals(3, hubs.size());
    	
    	try {
    		vs.defineHub("Hub 1");
    	}catch(VaccineException e) {
    		// ok: duplicate
    	}
    	
    	vs.setStaff("Hub 1", 7, 6, 4);
    	double capacity = vs.estimateHourlyCapacity("Hub 1");
    	
    	assertEquals( Math.min(7*10.0, Math.min(6*12.0, 4*20.0)), capacity, 0.1);
    	vs.setStaff("Hub 2", 4, 3, 2);
    	vs.setStaff("Hub 3", 3, 3, 2);
    	
    	
    	// R3
    	ArrayList<String> lines=new ArrayList<>();
    	lines.add("SSN,LAST,FIRST,YEAR");
    	lines.addAll(generatePersons(10000));
    	Files.write(Paths.get("people.csv"), lines);
    	
    	vs.loadPeople(new FileReader("people.csv"));
    	
    	assertEquals(19997,vs.countPeople());
    	
    	// R4
    	vs.setHours(8,8,8,8,8,6,4); // 8 hours mon to fri, 6 hours on sat and 4 hours on sun
    	
    	List<List<String>> hours = vs.getHours();

    	assertEquals(7,hours.size());
    	assertTrue("Every day must have at least 16 time slots", hours.stream().allMatch( d -> d.size() >= 16) );
    	assertTrue("Every day should start at 09:00", hours.stream().map( d-> d.get(0)).allMatch(s -> s.equals("09:00")) );
    	assertEquals("Overall there shoudl be 200 time slosts", 4*(8+8+8+8+8+6+4), hours.stream().mapToInt( d -> d.size()).sum() );
    	
    	int av1_3 = vs.getDailyAvailable("Hub 1", 3);
    	int av1_6 = vs.getDailyAvailable("Hub 1", 6);
    	assertEquals("In hub 1 there should be 8 * 70 available places on wed", 560, av1_3);
    	assertEquals("In hub 1 there should be 4 * 70 available places on sun", 280, av1_6);
    	
    	Map<String,List<Integer>> availability = vs.getAvailable();
    	assertNotNull("Missing availability", availability);
    	assertTrue("Not all hubs reported in availability", availability.keySet().containsAll(vs.getHubs()));
    	int totalAvailable = availability.values().stream().flatMap(List::stream).mapToInt(Integer::intValue).sum();
    	assertEquals("Total available places is wrong", 6800, totalAvailable);
    	
    	// R5
    	List<String> alloc = vs.allocate("Hub 1", 1);
    	assertNotNull(alloc);
    	assertEquals("wrong number of allocated people", 70*8, alloc.size() );
    	long count_60_70 = alloc.stream().map(vs::getAge).filter(a -> a>=60 && a<70).count();
    	assertEquals("Wrong number of people in [60,70)",70*8*24/100, count_60_70);

    	long count_over_70 = alloc.stream().map(vs::getAge).filter(a -> a>=70).count();
    	assertEquals("Wrong number of people over 70, should be 40% + 4.8%",Math.round(70*8*0.448), count_over_70);

    	vs.clearAllocation();
    	List<String> alloc2 = vs.allocate("Hub 1", 1);
    	assertEquals("Should get the same result", alloc, alloc2);
    	vs.clearAllocation();

    	
    	List<Map<String,List<String>>> wp = vs.weekAllocate();
    	assertNotNull("Missing week allocations plan", wp);
    	assertEquals("Week plan should span a whole week", 7, wp.size());
    	
    	int countAllocated = wp.stream().flatMap(m -> m.values().stream()).mapToInt(List::size).sum();
    	assertEquals("Wrong number of allocated people", 6800, countAllocated);
    	
    	
    	// R6
    	double propAlloc = vs.propAllocated();
    	assertEquals("Wring proportion of allocated persons", 0.34,propAlloc,0.01);
    	
    	Map<String,Double> allInAge = vs.propAllocatedAge();
    	assertNotNull("Missing prop allocated in age interval", allInAge);
    	assertEquals("There should be six intervals", 6, allInAge.size());
    	assertEquals("~15% of people over 70 should be allocated", 0.15, allInAge.get("[70,+)"), 0.01);

    	
    	Map<String,Double> distribution = vs.distributionAllocated();
    	assertNotNull("Missing distribution", distribution);
    	assertEquals("There shoild be size age intervals", 6,distribution.size());
    	assertEquals("Wrong propotion of over 70 among allocated", 0.448, distribution.get("[70,+)"), 0.01);
    	assertEquals("Wrong propotion of [60,70) among allocated", 0.4*0.6, distribution.get("[60,70)"), 0.01);

    	
    	// R7
    	Map<Integer,String> errors = new HashMap<>();
    	vs.setLoadListener((i,l)-> errors.put(i, l) );
    	String incomplete="SSN,LAST,FIRST,YEAR\n"+				//1: header
    					  "ABCDEF01G23H456I,Smith,John,1923\n"+  //2: ok
    					  "ABCDEF01G23H456I,Smith,John,1923\n"+  //3: duplicated ssn
    					  "ABCFED01G23H987J,Smith\n"+ 			//4: missing first name
    					  "";
    	StringReader sr = new StringReader(incomplete);
    	vs.loadPeople(sr);
    	assertEquals("Wrong number of errors", 2, errors.size());
    	assertTrue("Expected errors on lines 3,4,5 but got: " + errors.keySet(), 
    			   errors.keySet().containsAll(Arrays.asList(3,4)));
    }
    
    
    
    ///------------------------------------------------------------------------------------------------------------
    //
    // 	UTILIY METHODS
    //
    //
    /**
     * Utility methods that generates fake people information 
     * and notifies a listener for each one.
     * 
     * The listener can be used, e.g., to add the person to the system.
     * 
     * Returns a set with the people information (SSN,last,first,year separated by commas)
     * 
     * If {@code listener} is {@code null} it is not called
     *  
     * @param n			number of people to generate
     * @param listener 	the listener to notify new person information
     * @return			the set of person descriptions
     */
    private Set<String> generateAndAddPerson( int n, PersonListener listener) {
    	HashSet<String> cfs = new HashSet<>();
    	HashSet<String> people = new HashSet<>();
    	while(cfs.size() < n) {
	    	String first = pick(firsts,rg);
	    	String last = pick(lasts,rg);
	    	int year = 1922+rg.nextInt(2021-1921);
	    	String place = pick(places,rg);
	    	char cin1 = ((char) ('A'+rg.nextInt('Z'-'A')));
	    	char cin2 = ((char) ('A'+rg.nextInt('Z'-'A')));
	    	String ssn=toCode(last)+toCode(first)+(year%100)+place+cin1+cin2; // generate a fake ssn (codice fiscale)
	    			  
	    	String person = ssn + "," + last + "," + first + "," + year;
	    	if(cfs.add(ssn)){ // skip possible duplicates
	    		people.add(person);
	    		if(listener!=null) listener.handlePerson(first, last, ssn, year);
	    	}
    	}
    	return people;
    }

    /**
     * Listener for newly generated person info
     *
     */
    private interface PersonListener {
    	boolean handlePerson(String first, String last, String ssn, int year);
    }

    /**
     * Generates fake people information 
     * 
     * Returns a set with the people information
     * 
     * @param n			number of people to generate
     * @return			the set of person descriptions
     */
    private Set<String> generatePersons( int n) {
    	return generateAndAddPerson(n,null);
    }


    static String[] firsts = {"Ali","Anna","Enrica","Giorgio","Luca","Lucia","Mario","Miriam","Paola","Xavier"};
    static String[] lasts = {"Abaco","Draghi","Letto","Melone","Mattei","Russo","Neri","Rossi","Verdi","Xavier"};
    static String[] places = {"L219","A213","F789","G456","H501","Z403","R129"};
    
    private static String pick(String [] options, Random r) {
    	return options[r.nextInt(options.length)];
    }
    private static Random rg = new Random(1971);
    
    /**
     * Take a name (first or last) and emulates 
     * how they are encoded in real Italian "codice fiscale"
     * 
     * @param name
     * @return
     */
    private static String toCode(String name) {
    	name = name.toUpperCase();
    	String consonants = name.replaceAll("[AEIOU ]", "");
    	String vouels = name.replaceAll("[^AEIOU]", "");
    	return (consonants+vouels).substring(0,3);
    }
    
    final static int CURRENT_YEAR=java.time.LocalDate.now().getYear();
}
