package ir.ttic.student_manager.objects;


public class Student {

    private String firstName;
    private String lastName;
    private String field;
    private String studentID;
    private int entranceYear;
    private int age;

    public Student(String firstName, String lastName, String field, String studentCode, int entranceYear, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.field = field;
        this.studentID = studentCode;
        this.entranceYear = entranceYear;
        this.age = age;
    }

    public Student() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setEntranceYear(int entranceYear) {
        this.entranceYear = entranceYear;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getField() {
        return field;
    }

    public String getStudentID() {
        return studentID;
    }

    public int getEntranceYear() {
        return entranceYear;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return  "firstName=\t" + firstName + '\n' +
                "lastName=\t" + lastName + '\n' +
                "field=\t" + field + '\n' +
                "studentCode=\t" + studentID + '\n' +
                "age=\t" + age+'\n'+
                "entranceYear=\t" + entranceYear + '\n';
    }
}

