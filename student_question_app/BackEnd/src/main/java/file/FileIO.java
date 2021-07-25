package file;

import com.google.gson.Gson;

import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.AlreadyExist;
import interfaces.IDataBase;
import objects.Exam;
import objects.Mark;
import objects.Student;

public class FileIO implements IDataBase {

  private final Gson gson;
  private final File examFile;
  private final String workingDir ;
  private final Statement statement;



  public FileIO() throws Exception {

    gson = new Gson();
    workingDir = System.getProperty("user.dir");
    examFile = new File(workingDir+"/exam.json");
    statement = DriverManager.getConnection("jdbc:sqlite:"+workingDir+"/database.db").createStatement();

    createStudentTableIfNotExist();
    createExamTableIfNotExist();

    if (!examFile.exists())
      examFile.createNewFile();

  }

  void createStudentTableIfNotExist() throws SQLException {
    statement.execute("create table if not exists Students(ID  varchar  , password varchar)");
  }

  void createExamTableIfNotExist() throws SQLException {
    statement.execute("create table if not exists Exam(ID  varchar  , Mark int , Date varchar)");
  }

  @Override
  public boolean isAuthAble(Student s)  {
    try {

      ResultSet resultSet = statement.executeQuery("select count(*) from Students where ID='"+s.getId()+"' and password='"+s.getPassword()+"'");
      return resultSet.getInt(1) != 0;

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }

  @Override
  public void insertIfNotExist(Student s) throws AlreadyExist {

    try {

      ResultSet resultSet = statement.executeQuery("select count(*) from Students where ID='"+s.getId()+"'");

      if (resultSet.getInt(1) == 0){
        statement.execute("insert into Students values('"+s.getId()+"','"+s.getPassword()+"')");
      }else {
        throw  new AlreadyExist();
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }

  @Override
  public Optional<Exam> provideExamForCorrespondingStudent(Student s) {
    return getExam();
  }

  @Override
  public Optional<Exam> getExam() {

    try {
      Exam exam = gson.fromJson(new FileReader(examFile),Exam.class);
      return Optional.of(exam);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return Optional.empty();
    }

  }

  @Override
  public void saveExamResults(Student s, int percent) {

    try {
      long date = System.currentTimeMillis();
      statement.execute("insert into Exam values('"+s.getId()+"',"+percent+",'"+date+"')");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }

  @Override
  public List<Mark> getAllStudentMark(Student s) {

    try {

      ResultSet  resultSet = statement.executeQuery("select * from Exam where ID='"+s.getId()+"'order by Date asc");
      List<Mark> marks = new ArrayList<>();

      while (resultSet.next()){

        int date = resultSet.getInt(1);
        int mark = Integer.parseInt(resultSet.getString(2));

        marks.add(new Mark(mark,date));

      }

      return marks;

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return List.of();
    }

  }


}
