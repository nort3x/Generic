package ir.ttic.student_manager.objects;

import ir.ttic.student_manager.database.Averager;
import ir.ttic.student_manager.database.IDatabase;

public class CommandLine {

    public enum Color{
         ANSI_RESET( "\u001B[0m"),
         ANSI_BLACK( "\u001B[30m"),
         ANSI_RED( "\u001B[31m"),
         ANSI_GREEN( "\u001B[32m"),
         ANSI_YELLOW( "\u001B[33m"),
         ANSI_BLUE( "\u001B[34m"),
         ANSI_PURPLE( "\u001B[35m"),
         ANSI_CYAN( "\u001B[36m"),
         ANSI_WHITE( "\u001B[37m");

         final String color;
        Color(String s) {
            color=s;
        }
    }

    public static String color(String s,Color c){
        if(c.equals(Color.ANSI_RESET))
            return s;
        return c.color + s + Color.ANSI_RESET.color;
    }

    State s = State.pending;

    final Averager av;
    final Student tempStudent = new Student();
    int creatingStudent = -1;


    final Course tempCourse = new Course();
    int creatingCourse = -1;

    final IDatabase db;

    public CommandLine(IDatabase db){
        this.db = db;
        this.av = new Averager(db);
        showBanner();
    }

    public void process(String command) {
        switch (s) {
            case pending -> pendingCommand(command);
            case addingCourse -> addingCourse(command);
            case addingStudent -> addingStudent(command);
        }


    }

    private void pendingCommand(String command) {
        if(command.startsWith("average_all")){
            System.out.println(av.averageOfAllStudentsMark());
            return;
        }else if(command.startsWith("average_age")){
            System.out.println(av.averageOfAllStudentsMarkOfSameAge(Integer.parseInt(command.replace("average_age","").trim())));
            return;
        }else if(command.startsWith("average_ey")){
            System.out.println(av.averageOfAllStudentsMarkOfSameEntranceYear(Integer.parseInt(command.replace("average_ey","").trim())));
            return;
        }else if(command.startsWith("average_field")){
            System.out.println(av.averageOfAllStudentsMarkInAField(command.replace("average_field","").trim()));
            return;
        }else if(command.startsWith("average_student")){
            db.find(command.replace("average_student","").trim()).ifPresentOrElse(x->System.out.println(av.averageOfAllMarksForStudent(x)),
              ()->System.out.println("Student not found")
            );
            return;
        }else if(command.startsWith("show")){
            db.find(command.replace("show","").trim()).ifPresentOrElse(
                    x-> {
                        System.out.println(x);
                        System.out.println("Courses:");
                        db.getAllCourses(x).forEach(System.out::println);
                        }
                    ,()-> System.out.println("Student not found"));
            return;
        }


        switch (command) {
            case "add_course" -> {
                s = State.addingCourse;
                creatingCourse = -1;
                s.setPostFix(color("\b--setCourseName->\t", Color.ANSI_YELLOW));
            }
            case "add_student" -> {
                s = State.addingStudent;
                creatingStudent = -1;
                s.setPostFix(color("\b--setFirstName->\t", Color.ANSI_YELLOW));
            }
            case "help" -> help();
            case "exit" -> System.exit(0);
            case "list_all" -> db.getAllStudents().forEach(System.out::println);
            default -> System.out.println(color("not recognized, use help", Color.ANSI_RED));
        }
    }

    private void addingStudent(String command) {



        if(creatingStudent == -1) {
            tempStudent.setFirstName(command);
            creatingStudent++;
            s.setPostFix(color("\b--setLastName->\t",Color.ANSI_RED));
        }
        else if(creatingStudent == 0) {
            tempStudent.setLastName(command);
            creatingStudent++;
            s.setPostFix(color("\b--setStudentID->\t",Color.ANSI_YELLOW));
        }
        else if(creatingStudent == 1){
            tempStudent.setStudentID(command);
            creatingStudent++;
            s.setPostFix(color("\b--setField->\t",Color.ANSI_YELLOW));
        }
        else if(creatingStudent == 2) {
            tempStudent.setField(command);
            creatingStudent++;
            s.setPostFix(color("\b--setAge->\t",Color.ANSI_YELLOW));
        }
        else if(creatingStudent == 3) {
            tempStudent.setAge(Integer.parseInt(command));
            creatingStudent++;
            s.setPostFix(color("\b--setEntranceYear->\t",Color.ANSI_YELLOW));
        }
        else if(creatingStudent == 4) {
            tempStudent.setEntranceYear(Integer.parseInt(command));
            creatingStudent++;

            System.out.println();
            System.out.println(tempStudent.toString());
            System.out.println();
            s.setPostFix(color("\b--AreAllAboveOkay(y/n)->\t",Color.ANSI_YELLOW));
        }
        else if(creatingStudent == 5){
            if(command.equals("y")){
                db.insertStudent(tempStudent);
                System.out.println("added to database successfully");
            }
            s.setPostFix("");
            s = State.pending;
        }
        if ("exit".equals(command)) {
            s.setPostFix("");
            s = State.pending;
        }
    }

    private void addingCourse(String command) {


        if(creatingCourse == -1) {
            tempCourse.setName(command);
            creatingCourse++;
            s.setPostFix(color("\b--setMark->\t",Color.ANSI_YELLOW));
        }
        else if(creatingCourse == 0) {
            tempCourse.setMark(Double.parseDouble(command));
            creatingCourse++;
            s.setPostFix(color("\b--setStudentID->\t",Color.ANSI_YELLOW));
        }
        else if(creatingCourse == 1){
            if(db.find(command).isEmpty()){
                System.out.println("such studentID doesNot Exist in database");
                creatingCourse = -1;
            }
            tempCourse.setStudentCode(command);
            creatingCourse++;
            s.setPostFix(color("\b--setWeight->\t",Color.ANSI_YELLOW));
        }
        else if(creatingCourse == 2) {
            tempCourse.setWeight(Integer.parseInt(command));
            creatingCourse++;

            System.out.println();
            System.out.println(tempCourse.toString());
            System.out.println();
            s.setPostFix(color("\b--AreAllAboveOkay(y/n)->\t",Color.ANSI_YELLOW));
        }
        else if(creatingCourse == 3){
            if(command.equals("y")){
                db.insertCourse(tempCourse);
                System.out.println("added to database successfully");
            }
            s.setPostFix("");
            s = State.pending;
        }
        if ("exit".equals(command)) {
            s.setPostFix("");
            s = State.pending;
        }

    }


    private static final String banner = """
              _____,    _..-=-=-=-=-====--,
           _.'a   /  .-',___,..=--=--==-'`
          ( _     \\ /  //___/-=---=----'
           ` `\\    /  //---/--==----=-'
        ,-.    | / \\_//-_.'==-==---='
       (.-.`\\  | |'../-'=-=-=-=--'
        (' `\\`\\| //_|-\\.`;-~````~,        _
             \\ | \\_,_,_\\.'        \\     .'_`\\
              `\\            ,    , \\    || `\\\\
                \\    /   _.--\\    \\ '._.'/  / |
                /  /`---'   \\ \\   |`'---'   \\/
               / /'          \\ ;-. \\
            __/ /           __) \\ ) `|
          ((='--;)         (,___/(,_/

      """;
    private void showBanner() {
        System.out.println(banner);
    }

    public enum State {
        pending("-pending->\t"),
        addingStudent("-addStudent->"),
        addingCourse("-addCourse->");

        State(String name){
            this.name = name;
        }
        final String name;
        String postFix = "";
        protected  void setPostFix(String pf){
            postFix = pf;
        }
        public String getStateName(){
            return name+postFix;
        }

    }

    private static final String helpBanner =
      """
          help                                  this page
          add_student                           add new student to database
          add_course                            add new course to database
          exit                                  exit or cancel current operation

          average_all                           show average mark over all students
          average_field   fieldname             show average mark over all students studing in that field
          average_age     age                   show average mark over all students with given age
          average_ey      year                  show average mark over all students with given entrance year
          average_student studentcode           show average mark of given student

          list_all                              list all students (heavy!)
          show            studentcode           list all information for given student
        """;
    private void help() {
        System.out.println(helpBanner);
    }

    public State getS() {
        return s;
    }
}

