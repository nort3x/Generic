import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import it.polito.oop.vaccination.Vaccines;

public class TestUtils {

	
	/**
     * Generates fake people information and and 
     * return the relative information as a set.
     * 
     *  
     * @param n			number of people to generate
     * @return			the se of person descriptions
     */
    public static Set<String> generatePersons( int n) {
    	return generateAndAddPerson(n, null);
    }

	/**
     * Generates fake people information and and 
     * optionally adds them to the vaccine system.
     * 
     * Returns a set with the people information
     * 
     * If {@code system} is {@code null} persons are not added.
     *  
     * @param n			number of people to generate
     * @param system 	the Vaccine system to add persons to
     * @return			the se of person descriptions
     */
    public static Set<String> generateAndAddPerson( int n, Vaccines system) {
    	HashSet<String> cfs = new HashSet<>();
    	HashSet<String> people = new HashSet<>();
    	while(cfs.size() < n) {
	    	String first = pick(firsts,rg);
	    	String last = pick(lasts,rg);
	    	int year = 1922+rg.nextInt(2021-1921);
	    	String place = pick(places,rg);
	    	char cin1 = ((char) ('A'+rg.nextInt('Z'-'A')));
	    	char cin2 = ((char) ('A'+rg.nextInt('Z'-'A')));
	    	String cf=toCode(last)+toCode(first)+(year%100)+place+cin1+cin2; // generate a fake ssn (codice fiscale)
	    			  
	    	String person = cf + "," + last + "," + first + "," + year;
	    	if(cfs.add(cf)){
	    		people.add(person);
	    		if(system!=null) system.addPerson(first, last, cf, year);
	    	}
    	}
    	return people;
    }

    static String[] firsts = {"Ali","Anna","Enrica","Giorgio","Luca","Lucia","Mario","Miriam","Paola","Xavier"};
    static String[] lasts = {"Abaco","Draghi","Letto","Melone","Mattei","Russo","Neri","Rossi","Verdi","Xavier"};
    static String[] places = {"L219","A213","F789","G456","H501","Z403","R129"};
    
    private static String pick(String [] options, Random r) {
    	return options[r.nextInt(options.length)];
    }
    private static Random rg = new Random(4713);
    
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
    
    public final static int CURRENT_YEAR=java.time.LocalDate.now().getYear();
}
