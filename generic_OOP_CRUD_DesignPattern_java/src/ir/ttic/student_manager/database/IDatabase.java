package ir.ttic.student_manager.database;

import ir.ttic.student_manager.objects.Course;
import ir.ttic.student_manager.objects.Student;

import java.util.List;
import java.util.Optional;

public interface IDatabase {

    List<Student> getAllStudents();
    List<Course>  getAllCourses();

    List<Student> findAllWithSameField(String field);
    List<Student> findAllWithSameEntranceYear(int year);
    List<Student> findAllWithSameAge(int age);
    List<Student> findAllWhoTakenCourse(String course);

    Optional<Student> find(String firstname,String lastname);
    Optional<Student> find(String studentID);

    Optional<Course> getCourse(Student student,String courseName);
    List<Course> getAllCourses(Student student);

    void insertStudent(Student student);
    void insertCourse(Student student,Course course);
    void insertCourse(Course course);

}
