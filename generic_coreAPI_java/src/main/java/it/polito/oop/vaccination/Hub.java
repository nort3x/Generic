package it.polito.oop.vaccination;

public class Hub {
    int doctors;
    int nurses;
    int others;

    public Hub(int doctors, int nurses, int others) {
        this.doctors = doctors;
        this.nurses = nurses;
        this.others = others;
    }

    public void setDNO(int d,int n,int o){
        doctors =d;
        nurses =n;
        others = o;
    }

    public int getDoctors() {
        return doctors;
    }

    public void setDoctors(int doctors) {
        this.doctors = doctors;
    }

    public int getNurses() {
        return nurses;
    }

    public void setNurses(int nurses) {
        this.nurses = nurses;
    }

    public int getOthers() {
        return others;
    }

    public void setOthers(int others) {
        this.others = others;
    }
}
