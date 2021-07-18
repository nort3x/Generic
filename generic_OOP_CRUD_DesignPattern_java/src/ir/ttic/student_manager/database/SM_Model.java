package ir.ttic.student_manager.database;


public class SM_Model {

  public enum Student implements TableFieldType<Student>{

    Student_ID("varchar"),
    FirstName("varchar"),
    LastName("varchar"),
    Field("varchar"),
    EntranceYear("int"),
    Age("int");


    private final String type;

    Student(String type){
      this.type = type;
    }


    @Override
    public String getType() {
      return type;
    }

    @Override
    public String getFieldName() {
      return this.name();
    }

    @Override
    public String getEnumName() {
      return "Student";
    }

    @Override
    public int getIndexForResultSet() {
      return ordinal() + 1;
    }


  }

  public enum Course implements TableFieldType<Course>{

    CourseName("varchar"),
    Student_ID("varchar"),
    Weight("int"),
    Mark("int");

    private final String type;


    Course(String type){
      this.type = type;
    }

    @Override
    public String getType() {
      return type;
    }

    @Override
    public String getFieldName() {
      return name();
    }

    @Override
    public String getEnumName() {
      return "Course";
    }

    @Override
    public int getIndexForResultSet() {
      return ordinal() + 1;
    }

  }


  public enum TableNames{

    Student,
    Course

  }


  interface TableFieldType<Enum>{

    String getType();
    String getFieldName();
    String getEnumName();
    int getIndexForResultSet();

  }



}
