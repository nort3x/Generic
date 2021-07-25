package interfaces;

import exceptions.AlreadyExist;
import objects.Exam;
import objects.Mark;
import objects.Student;

import java.util.List;
import java.util.Optional;

public interface IDataBase {
    boolean isAuthAble(Student s);
    void insertIfNotExist(Student s)throws AlreadyExist;
    Optional<Exam> provideExamForCorrespondingStudent(Student s);
    Optional<Exam> getExam();
    void saveExamResults(Student s,int percent);
    List<Mark> getAllStudentMark(Student s);
}
