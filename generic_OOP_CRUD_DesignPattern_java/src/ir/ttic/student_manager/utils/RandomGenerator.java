package ir.ttic.student_manager.utils;

import ir.ttic.student_manager.objects.Course;
import ir.ttic.student_manager.objects.Student;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomGenerator {
    private static final List<String> names = List.of("ali","amir","akbar","sara","salim",
            "mahmood","kobra","mina","karim","arman","tina","maryam","shahriar","nasrin","ziba","zhila",
            "kamran","elahe","nazanin","somayeh","shirin","mohammad","asqar","sina","artin");

    private static final List<String> fields = List.of("computer","mechanic","riazi","memari","sakhteman",
            "barq","physic","zist","shimi","ulum siasi","ravan shenasi","modiriat","joqrafi");

    private static final List<String> courses = List.of("riazi1","riazi2","physic1","physic2","englisi","farsi","shimi1","computer 1",
            "computer 2", "kargah","varzesh","memari","azmayeshgah");


    private static final List<Character> alphaNumeric = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890".chars().mapToObj(x->(char)x).collect(Collectors.toList());

    private static final Random r = new Random();
    public <E> E randomOf(List<E> arr){
        return arr.get(r.nextInt(arr.size()));
    }


    public String randomFirstName(){
        return randomOf(names);
    }

    public String randomLastName(){
        String name  = randomOf(names);
        if(name.endsWith("i") || name.endsWith("o") || name.endsWith("a") || name.endsWith("h") || name.endsWith("e"))
            name += "pour";
        else
            name += "i";
        return name;
    }

    public  String randomID(int len){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(randomOf(alphaNumeric));
        }
        return sb.toString();
    }

    public int randomAge(){
        return 18 + r.nextInt(10);
    }

    public String randomField(){
        return randomOf(fields);
    }


    public int randomEntranceYear (){
        return 1380 + r.nextInt(20);
    }

    public Student randomStudent(){
        return new Student(randomFirstName(),randomLastName(),randomField(),randomID(20),randomEntranceYear(),randomAge());
    }

    public Course randomCourse(Student s){
        return new Course(r.nextDouble()*20,1+r.nextInt(4),randomOf(courses),s.getStudentID());
    }


}
