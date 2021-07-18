package ir.ttic.student_manager.utils;

import ir.ttic.student_manager.database.IDatabase;
import ir.ttic.student_manager.objects.Course;
import ir.ttic.student_manager.objects.Student;

import java.util.ArrayList;

public class Populater {
    private static final RandomGenerator rg = new RandomGenerator();
    public static void populateRandom(IDatabase db, int howManyStudents,int howManyCourses){
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        for (int i = 0; i < howManyStudents; i++) {
            students.add(rg.randomStudent());
        }
        System.out.println("generated students");
        for (int i = 0; i < howManyCourses; i++) {
            courses.add(rg.randomCourse(rg.randomOf(students)));
        }
        System.out.println("generated courses");

        students.forEach(db::insertStudent);
        System.out.println("inserted students");

        courses.forEach(db::insertCourse);
        System.out.println("inserted courses");

    }
}
