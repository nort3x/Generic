package ir.ttic.student_manager.objects;

import java.util.List;

public class Course {
    double mark; // nomre
    int weight; // vahed
    String name;
    String studentCode;
    boolean isPassed;

    public Course(double mark, int weight, String name, String studentCode) {
        this.mark = mark;
        this.weight = weight;
        this.name = name;
        this.studentCode = studentCode;
        this.isPassed = mark>=10;
    }

    public Course() {
    }

    public static double averageOfCourses(List<Course> takenCourses) {
        int totalWeight = takenCourses.stream().mapToInt(x-> x.weight).sum();
        return takenCourses.stream().mapToDouble(x->x.mark* ((double)(x.weight)/totalWeight)).sum();
    }

    public void setMark(double mark) {
        this.mark = mark;
        this.isPassed = mark>=10;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }


    public double getMark() {
        return mark;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public boolean isPassed() {
        return isPassed;
    }

    @Override
    public String toString() {
        return "\n" +
                "mark=" + mark +'\n'+
                "weight=" + weight +'\n'+
                "name=" + name  +'\n'+
                "studentCode=" + studentCode  +'\n'+
                "isPassed=" + isPassed +'\n'+
                '\n';
    }
}
