package it.polito.oop.vaccination;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonLoader {
    public static String getHeader() {
        return header;
    }

    private static final String header = "SSN,LAST,FIRST,YEAR\n";

    public static Person[] loadFromFile(String path) throws Exception {
        File f = new File(path);
        FileInputStream fis = new FileInputStream(f);
        ByteArrayOutputStream bos =new ByteArrayOutputStream();
        int i = 0;
        while (true){
            i = fis.read();
            if(i==-1)
                break;
            bos.write(i);
        }
        String wholefile = new String(bos.toByteArray());
        String[] persons = wholefile.split("\n");
        if(persons.length<2)
            throw new Exception("bad!");
        Person[] answer = new Person[persons.length-1];
        for (int j = 1; j <persons.length ; j++) {
            answer[i-1] = new Person(persons[i]);
        }
        return answer;

    }

    public static Person[] loadPersons(Stream<String> s, BiConsumer<Integer, String> lst, HashMap<String, Person> mapSSN2Person){
        List<String> ww = s.collect(Collectors.toList());
        return ww.stream().map(w->{
            try {
                Person p = new Person(w);
                if(mapSSN2Person.containsKey(p.getSsn()))
                    lst.accept(ww.lastIndexOf(w)+2,w);
                else
                    mapSSN2Person.put(p.getSsn(),p);
                return p;
            }catch (Exception e){
                if(lst!=null)
                lst.accept(ww.indexOf(w)+2,w);
                return null;
            }
        }).collect(Collectors.toList()).toArray(new Person[0]);
    }

    public void writeToFile(Person[] ps,String path) throws IOException {
        File f = new File(path);
        if(f.exists())
            f.delete();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(header.getBytes());
        for (Person p:
             ps) {
            fos.write((p.toString()+"\n").getBytes());
        }
        fos.flush();
        fos.close();
    }
}
