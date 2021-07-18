package it.polito.oop.vaccination;

public class Person {
    String firstname,lastname,ssn;
    int age;

    public Person(String firstname, String lastname, int age,String ssn) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.ssn = ssn;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return ssn +","+lastname+","+firstname+","+String.valueOf(Vaccines.CURRENT_YEAR-age);
    }
    public Person(String s){
        String[]arr =  s.split(",");
        this.ssn = arr[0];
        this.lastname = arr[1];
        this.firstname = arr[2];
        this.age = Vaccines.CURRENT_YEAR - Integer.valueOf(arr[3]);
    }
}
