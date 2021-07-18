package ir.ttic.student_manager.database;

import ir.ttic.student_manager.objects.Course;
import ir.ttic.student_manager.objects.Student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Averager implements IDatabase{
    final IDatabase db;
    public Averager(IDatabase db){
        this.db = db;
    }

    public double averageOfAllStudentsMark(){
        return Course.averageOfCourses(db.getAllCourses());
    }

    public double averageOfAllStudentsMarkOfSameEntranceYear(int entranceYear){
        return Course.averageOfCourses(db.findAllWithSameEntranceYear(entranceYear).stream().map(x->db.getAllCourses(x)).flatMap(List<Course>::stream).collect(Collectors.toList()));
    }

    public double averageOfAllStudentsMarkInAField(String field){
        return Course.averageOfCourses(db.findAllWithSameField(field).stream().map(x->db.getAllCourses(x)).flatMap(List<Course>::stream).collect(Collectors.toList()));
    }

    public double averageOfAllStudentsMarkOfSameAge(int age){
        return Course.averageOfCourses(db.findAllWithSameAge(age).stream().map(x->db.getAllCourses(x)).flatMap(List<Course>::stream).collect(Collectors.toList()));
    }

    public double averageOfAllMarksForStudent(Student s){
        return Course.averageOfCourses(db.getAllCourses(s));
    }
















    /////////////////////////////////////////

    @Override
    public List<Student> getAllStudents() {
        return db.getAllStudents();
    }

    @Override
    public List<Course> getAllCourses() {
        return db.getAllCourses();
    }

    @Override
    public List<Student> findAllWithSameField(String field) {
        return db.findAllWithSameField(field);
    }

    @Override
    public List<Student> findAllWithSameEntranceYear(int year) {
        return db.findAllWithSameEntranceYear(year);
    }

    @Override
    public List<Student> findAllWithSameAge(int age) {
        return db.findAllWithSameAge(age);
    }

    @Override
    public List<Student> findAllWhoTakenCourse(String course) {
        return db.findAllWhoTakenCourse(course);
    }

    @Override
    public Optional<Student> find(String firstname, String lastname) {
        return db.find(firstname,lastname);
    }

    @Override
    public Optional<Student> find(String studentID) {
        return db.find(studentID);
    }

    @Override
    public Optional<Course> getCourse(Student student, String courseName) {
        return db.getCourse(student,courseName);
    }

    @Override
    public List<Course> getAllCourses(Student student) {
        return db.getAllCourses(student);
    }

    @Override
    public void insertStudent(Student student) {
        db.insertStudent(student);
    }

    @Override
    public void insertCourse(Student student, Course course) {
        db.insertCourse(student,course);
    }

    @Override
    public void insertCourse(Course course) {
        db.insertCourse(course);
    }
}
