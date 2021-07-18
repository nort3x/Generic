package it.polito.oop.vaccination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Vaccines {

    public final static int CURRENT_YEAR = java.time.LocalDate.now().getYear();
    private HashMap<String,Person> mapSSN2Person = new HashMap<>();
    private static final Person noPerson = new Person("NULL","NULL",-1,"NULL");
    private int[] ageIntervals = new int[]{0,0};
    public int currentYear = 2021;
    HashMap<String,Hub> hubs = new HashMap<>();
    // R1
    /**
     * Add a new person to the vaccination system.
     *
     * Persons are uniquely identified by SSN (italian "codice fiscale")
     *
     * @param firstName first name
     * @param lastName last name
     * @param ssn italian "codice fiscale"
     * @param y birth year
     * @return {@code false} if ssn is duplicate,
     */
    public boolean addPerson(String firstName, String lastName, String ssn, int y) {
        if(mapSSN2Person.containsKey(ssn))
            return false;
        mapSSN2Person.put(ssn,new Person(firstName,lastName,currentYear - y,ssn));
        return true;
    }

    /**
     * Count the number of people added to the system
     *
     * @return person count
     */
    public int countPeople() {
        return mapSSN2Person.size();
    }

    /**
     * Retrieves information about a person.
     * Information is formatted as ssn, last name, and first name
     * separate by {@code ','} (comma).
     *
     * @param ssn "codice fiscale" of person searched
     * @return info about the person
     */
    public String getPerson(String ssn) {
        Person p= mapSSN2Person.getOrDefault(ssn,null);
        if(p==null)
            return null;
        StringBuilder sb = new StringBuilder();
        sb.append(ssn).append(",").append(p.firstname).append(",").append(p.lastname);
        return sb.toString();
    }

    /**
     * Retrieves of a person given their SSN (codice fiscale).
     *
     * @param ssn "codice fiscale" of person searched
     * @return age of person (in years)
     */
    public int getAge(String ssn) {
        return (mapSSN2Person.get(ssn)==null)?-1:mapSSN2Person.get(ssn).getAge();
    }

    /**
     * Define the age intervals by providing the breaks between intervals.
     * The first interval always start at 0 (non included in the breaks)
     * and the last interval goes until infinity (not included in the breaks).
     * All intervals are closed on the lower boundary and open at the upper one.
     * <p>
     * For instance {@code setAgeIntervals(40,50,60)}
     * defines four intervals {@code "[0,40)", "[40,50)", "[50,60)", "[60,+)"}.
     *
     * @param breaks the array of breaks
     */
    public void setAgeIntervals(int... breaks) {
        ageIntervals = breaks;
    }

    /**
     * Retrieves the labels of the age intervals defined.
     *
     * Interval labels are formatted as {@code "[0,10)"},
     * if the upper limit is infinity {@code '+'} is used
     * instead of the number.
     *
     * @return labels of the age intervals
     */
    public Collection<String> getAgeIntervals() {
        ArrayList<String> intervals = new ArrayList<>();
        intervals.add("["+0+","+ageIntervals[0]+")");
        for (int i = 0; i < ageIntervals.length-1; i++) {
            intervals.add("["+ageIntervals[i]+","+ageIntervals[i+1]+")");
        }
        intervals.add("["+ageIntervals[ageIntervals.length-1]+","+"+"+")");
        return intervals;
    }

    /**
     * Retrieves people in the given interval.
     *
     * The age of the person is computed by subtracting
     * the birth year from current year.
     *
     * @param interval age interval label
     * @return collection of SSN of person in the age interval
     */
    public Collection<String> getInInterval(String interval) {

        int min = Integer.valueOf(interval.substring(1,interval.indexOf(",")));
        String s = interval.substring(interval.indexOf(",")+1,interval.length()-1);
        int max = s.equals("+")?Integer.MAX_VALUE:Integer.valueOf(s);
        return mapSSN2Person.values().stream().filter((p)-> p.getAge()<max && p.age>=min).map((p->p.getSsn())).collect(Collectors.toList());
    }

    // R2
    /**
     * Define a vaccination hub
     *
     * @param name name of the hub
     * @throws VaccineException in case of duplicate name
     */
    public void defineHub(String name) throws VaccineException {
        if(hubs.containsKey(name))
            throw new VaccineException("Hub already exists");
        hubs.put(name,new Hub(-1,-1,-1));
    }

    /**
     * Retrieves hub names
     *
     * @return hub names
     */
    public Collection<String> getHubs() {
        return hubs.keySet();
    }

    /**
     * Define the staffing of a hub in terms of
     * doctors, nurses and other personnel.
     *
     * @param name name of the hub
     * @param doctors number of doctors
     * @param nurses number of nurses
     * @param o number of other personnel
     * @throws VaccineException in case of undefined hub, or any number of personnel not greater than 0.
     */
    public void setStaff(String name, int doctors, int nurses, int o) throws VaccineException {
        if(!hubs.containsKey(name))
            throw new VaccineException("Hub not defined");
        hubs.get(name).setDNO(doctors,nurses,o);
    }

    /**
     * Estimates the hourly vaccination capacity of a hub
     *
     * The capacity is computed as the minimum among
     * 10*number_doctor, 12*number_nurses, 20*number_other
     *
     * @param hubName name of the hub
     * @return hourly vaccination capacity
     * @throws VaccineException in case of undefined or hub without staff
     */
    public int estimateHourlyCapacity(String hubName) throws VaccineException {
        if(!hubs.containsKey(hubName))
            throw new VaccineException("Hub not defined");
        Hub h = hubs.get(hubName);
        if(h.getDoctors()*h.getNurses()*h.getOthers()<0)
            throw new VaccineException("Unstaffed");
        return Math.min(10*h.getDoctors(),Math.min(12*h.getNurses(),20*h.getOthers()));
    }

    // R3
    /**
     * Load people information stored in CSV format.
     *
     * The header must start with {@code "SSN,LAST,FIRST"}.
     * All lines must have at least three elements.
     *
     * In case of error in a person line the line is skipped.
     *
     * @param people {@code Reader} for the CSV content
     * @return number of correctly added people
     * @throws IOException in case of IO error
     * @throws VaccineException in case of error in the header
     */
    public long loadPeople(Reader people) throws IOException, VaccineException {
        // Hint:
        BufferedReader br = new BufferedReader(people);
        AtomicBoolean goodHeader = new AtomicBoolean(false);
        br.lines().findFirst().ifPresent(x->{
            goodHeader.set(x.equals(PersonLoader.getHeader().replace("\n","")));
        });
        if(!goodHeader.get())
            throw new VaccineException("bad header");

        Person[] p = PersonLoader.loadPersons(br.lines(),lst,mapSSN2Person);
        return p.length;
    }


    int[] times = null;
    // R4
    /**
     * Define the amount of working hours for the days of the week.
     *
     * Exactly 7 elements are expected, where the first one correspond to Monday.
     *
     * @param h workings hours for the 7 days.
     * @throws VaccineException if there are not exactly 7 elements or if the sum of all hours is less than 0 ore greater than 24*7.
     */
    public void setHours(int... h) throws VaccineException {
        int total = 0;
        // sum and check
        for (int i : h) {
            total+=i;
            if(i>12)
                throw new VaccineException("illegal timing");
        }
        if(h.length<7 || total<0 || total > 24*7)
            throw new VaccineException("illegal timing");
        times = h;
    }

    /**
     * Returns the list of standard time slots for all the days of the week.
     *
     * Time slots start at 9:00 and occur every 15 minutes (4 per hour) and
     * they cover the number of working hours defined through method {@link #setHours}.
     * <p>
     * Times are formatted as {@code "09:00"} with both minuts and hours on two
     * digits filled with leading 0.
     * <p>
     * Returns a list with 7 elements, each with the time slots of the corresponding day of the week.
     *
     * @return the list hours for each day of the week
     */
    public List<List<String>> getHours() {
        ArrayList<List<String>> answer = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            ArrayList<String> slots = new ArrayList<>();
            for (int j = 0; j < times[i]; j++) {
                String o = (j + 9) < 10 ? "0" + (j + 9) : String.valueOf((j + 9));
                slots.add( o +":00");
                slots.add(o +":15");
                slots.add(o +":30");
                slots.add(o +":45");
            }
            answer.add(slots);
        }
        return answer;
    }

    /**
     * Compute the available vaccination slots for a given hub on a given day of the week
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the hourly capacity (see {@link Vaccines#estimateHourlyCapacity(String)} of the hub.
     *
     * @return
     */
    public int getDailyAvailable(String hubName, int d) {
        try {
            return estimateHourlyCapacity(hubName)*times[d];
        } catch (VaccineException e) {
            return 0; // case it is no hub to do the service
        }
    }

    /**
     * Compute the available vaccination slots for each hub and for each day of the week
     * <p>
     * The method returns a map that associates the hub names (keys) to the lists
     * of number of available hours for the 7 days.
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the capacity (see {@link Vaccines#estimateHourlyCapacity(String)} of the hub.
     *
     * @return
     */
    public Map<String, List<Integer>> getAvailable() {
        HashMap<String,List<Integer>> hub_hour = new HashMap<>();
        hubs.forEach((hubname,hub)->{
            try{
                ArrayList<Integer>  cap_dat = new ArrayList<>();
                for (int time : times) {
                    cap_dat.add(estimateHourlyCapacity(hubname)*time);
                }
                hub_hour.put(hubname,cap_dat);
            }catch (Exception ignored){}
        });
        return hub_hour;
    }


    Map<String,List<String>> hub_allocations = new HashMap<>();

    /**
     * Computes the general allocation plan a hub on a given day.
     * Starting with the oldest age intervals 40%
     * of available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is the list of SSNs (codice fiscale) of the
     * persons allocated to that day
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     *
     * @param hubName name of the hub
     * @param d day of week index (0 = Monday)
     * @return the list of daily allocations
     */
    public List<String> allocate(String hubName, int d) {
        int total = getAvailable().get(hubName).get(d);
        List<String> intervals = (List<String>) getAgeIntervals();
        ArrayList<String> allocations = new ArrayList<>();
        for (String chunk :
                intervals) {
         List<String> patients = (List<String>) getInInterval(chunk);
            int alloc;
         if(patients.size()<total)
             alloc = patients.size();
         else
          alloc = (int) (total*0.4);
         total -= alloc;
            for (int i = 0; i <= alloc; i++) {
                allocations.add(patients.get(i));
            }

        }
        hub_allocations.put(hubName,allocations);
        return allocations;
    }

    /**
     * Removes all people from allocation lists and
     * clears their allocation status
     */
    public void clearAllocation() {
        hub_allocations.clear();
    }

    /**
     * Computes the general allocation plan for the week.
     * For every day, starting with the oldest age intervals
     * 40% available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is a list with 7 elements, one
     * for every day of the week, each element is a map that
     * links the name of each hub to the list of SSNs (codice fiscale)
     * of the persons allocated to that day in that hub
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     * but the same invocation (after {@link #clearAllocation}) must return the same
     * allocation.
     *
     * @return the list of daily allocations
     */
    public List<Map<String, List<String>>> weekAllocate() {
        ArrayList<Map<String,List<String>>> allalloc = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            Map<String,List<String>> mapOfHubToAlloc = new HashMap<>();
            int finalI = i;
            hubs.keySet().forEach(x->{
                mapOfHubToAlloc.put(x,allocate(x, finalI));
            });
            allalloc.add(mapOfHubToAlloc);
        }
        return allalloc;
    }

    // R5
    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system
     *
     * @return proportion of allocated people
     */
    public double propAllocated() {
        return -1.0;
    }

    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system, divided by age interval.
     * <p>
     * The map associates the age interval label
     * to the proportion of allocates people in that interval
     *
     * @return proportion of allocated people by age interval
     */
    public Map<String, Double> propAllocatedAge() {
        return null;
    }

    /**
     * Retrieves the distribution of allocated persons
     * among the different age intervals.
     * <p>
     * For each age intervals the map reports the
     * proportion of allocated persons in the corresponding
     * interval w.r.t the total number of allocated persons
     *
     * @return
     */
    public Map<String, Double> distributionAllocated() {
        return null;
    }

    // R6
    /**
     * Defines a listener for the file loading method.
     * The {@ accept()} method of the listener is called
     * passing the line number and the offending line.
     * <p>
     * Lines start at 1 with the header line.
     *
     * @param lst the listener for load errors
     */
    BiConsumer<Integer,String> lst;
    public void setLoadListener(BiConsumer<Integer, String> lst) {
        this.lst = lst;
        // lst is used in load method

    }
}
