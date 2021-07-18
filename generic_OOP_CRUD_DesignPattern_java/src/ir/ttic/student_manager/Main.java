package ir.ttic.student_manager;

import ir.ttic.student_manager.database.SM_DB;
import ir.ttic.student_manager.objects.CommandLine;
import ir.ttic.student_manager.utils.Populater;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws SQLException {

    SM_DB db = new SM_DB();

    Populater.populateRandom(db,20,40);

    CommandLine cm = new CommandLine(db);
    Scanner sc = new Scanner(System.in);
    while(true){
      System.out.print(cm.color(cm.getS().getStateName(), CommandLine.Color.ANSI_GREEN));
      cm.process(sc.nextLine());
    }

  }


}
