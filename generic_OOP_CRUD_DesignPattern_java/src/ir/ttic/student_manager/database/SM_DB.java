package ir.ttic.student_manager.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ir.ttic.student_manager.objects.Course;
import ir.ttic.student_manager.objects.Student;

import static ir.ttic.student_manager.database.SM_Model.*;

public class SM_DB implements IDatabase {

  private final Statement statement;

  public SM_DB() throws SQLException {

    String workDirectory = System.getProperty("user.dir");
    statement = DriverManager.getConnection("jdbc:sqlite:" + workDirectory + "/SM_DM.db").createStatement();

    createCourseTableIfNotExist(SM_Model.Course.values());
    createCourseTableIfNotExist(SM_Model.Student.values());

  }

  private void createCourseTableIfNotExist(TableFieldType<?>... types) throws SQLException {

    StringBuilder query = new StringBuilder("create table if not exists " + types[0].getEnumName() + "(");

    for (TableFieldType<?> type : types) {

      query.append(type.getFieldName()).append(" ").append(type.getType()).append(",");

    }

    query.deleteCharAt(query.length() - 1);
    query.append(")");

    statement.execute(query.toString());

  }


  @Override
  public List<Student> getAllStudents() {

    try {

      ResultSet resultSet = statement.executeQuery("select * from Student");
      return studentFromResultSet(resultSet);

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return List.of();
    }

  }

  @Override
  public List<Course> getAllCourses() {

    try {

      ResultSet resultSet = statement.executeQuery("select  * from Course");
      return courseFromResultSet(resultSet);

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return List.of();
    }

  }

  @Override
  public List<Student> findAllWithSameField(String field) {

    try {

      ResultSet resultSet = statement.executeQuery("select * from Student where " + SM_Model.Student.Field + "='" + field + "'");
      return studentFromResultSet(resultSet);

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return List.of();
    }
  }


  @Override
  public List<Student> findAllWithSameEntranceYear(int year) {

    try {

      ResultSet resultSet = statement.executeQuery("select * from Student where " + SM_Model.Student.EntranceYear + "=" + year);
      return studentFromResultSet(resultSet);

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return List.of();
    }
  }

  @Override
  public List<Student> findAllWithSameAge(int age) {

    try {

      ResultSet resultSet = statement.executeQuery("select * from Student where " + SM_Model.Student.Age + "=" + age);
      return studentFromResultSet(resultSet);

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return List.of();
    }
  }

  @Override
  public List<Student> findAllWhoTakenCourse(String course) {

    try {

      ResultSet resultSet = statement.executeQuery("select * from Student S inner join Course C on S.Student_ID = C.Student_ID where " + SM_Model.Course.CourseName + "='" + course + "'");
      return studentFromResultSet(resultSet);

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return List.of();
    }
  }

  @Override
  public Optional<Student> find(String firstname, String lastname) {

    try {

      ResultSet resultSet = statement.executeQuery("select * from Student where " + SM_Model.Student.FirstName.getFieldName() + "=" + firstname + " and " + SM_Model.Student.LastName.getFieldName() + "=" + lastname);
      return studentFromResultSet(resultSet).stream().findAny();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return Optional.empty();
    }
  }

  @Override
  public Optional<Student> find(String studentID) {
    try {

      ResultSet resultSet = statement.executeQuery("select * from Student where " + SM_Model.Student.Student_ID.getFieldName() + "='" + studentID+"'");
      return studentFromResultSet(resultSet).stream().findAny();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return Optional.empty();
    }
  }

  @Override
  public Optional<Course> getCourse(Student student, String courseName) {
    try {

      ResultSet resultSet = statement.executeQuery("select * from Course where " + SM_Model.Course.Student_ID.getFieldName() + "=" + student.getStudentID() + " and " + SM_Model.Course.CourseName.getFieldName() + "=" + courseName);
      return courseFromResultSet(resultSet).stream().findAny();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return Optional.empty();
    }
  }

  @Override
  public List<Course> getAllCourses(Student student) {
    try {

      ResultSet resultSet = statement.executeQuery("select * from Course where " + SM_Model.Course.Student_ID.getFieldName() + "='" + student.getStudentID()+"'");
      return courseFromResultSet(resultSet);

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return List.of();
    }
  }

  @Override
  public void insertStudent(Student student) {

    try {
      statement.execute("insert into Student values ('" + student.getStudentID() + "','" + student.getFirstName() + "','" + student.getLastName() + "','" + student.getField() + "'," + student.getEntranceYear() + "," + student.getAge() + ")");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }

  @Override
  public void insertCourse(Student student, Course course) {

    course.setStudentCode(student.getStudentID());
    insertStudent(student);
    insertCourse(course);

  }

  @Override
  public void insertCourse(Course course) {
    try {
      statement.execute("insert into Course values('" + course.getName() + "','" + course.getStudentCode() + "'," + course.getWeight() + "," + course.getMark() + ")");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }


  private List<Student> studentFromResultSet(ResultSet resultSet) throws SQLException {

    List<Student> students = new ArrayList<>();

    while (resultSet.next()) {

      students.add(new Student(
        resultSet.getString(SM_Model.Student.FirstName.getIndexForResultSet()),
        resultSet.getString(SM_Model.Student.LastName.getIndexForResultSet()),
        resultSet.getString(SM_Model.Student.Field.getIndexForResultSet()),
        resultSet.getString(SM_Model.Student.Student_ID.getIndexForResultSet()),
        resultSet.getInt(SM_Model.Student.EntranceYear.getIndexForResultSet()),
        resultSet.getInt(SM_Model.Student.Age.getIndexForResultSet())));

    }

    return students;
  }

  private List<Course> courseFromResultSet(ResultSet resultSet) throws SQLException {

    List<Course> courses = new ArrayList<>();

    while (resultSet.next()) {

      courses.add(new Course(
        resultSet.getDouble(SM_Model.Course.Mark.getIndexForResultSet()),
        resultSet.getInt(SM_Model.Course.Weight.getIndexForResultSet()),
        resultSet.getString(SM_Model.Course.CourseName.getIndexForResultSet()),
        resultSet.getString(SM_Model.Course.Student_ID.getIndexForResultSet())));

    }

    return courses;
  }


}
