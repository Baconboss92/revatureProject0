package com.helloapplii;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class calenderApp {

    // This whole set of files has been rewritten so many times
    // God why did I strruggle so much with this? It was not that hard besides the unit stuff

    public static void main(String[] args) {
        try(Connection conn = dataConf.getConnection()){
            calDAO dao = new calDAO(conn);
            calSer ser = new calSer(dao);
            dao.createFirstTable();
            dao.createSecondTable();
            dao.createThirdTable();
            //BEGIN
            try (Scanner scanner = new Scanner(System.in)) {
                boolean on = true;
                Calendar c = Calendar.getInstance();
                System.out.println("Year: " + c.get(Calendar.YEAR));
                System.out.println("Month: " + (c.get(Calendar.MONTH) + 1));
                System.out.println("Day in current month: " + c.get(Calendar.DAY_OF_MONTH));
                while(on){
                    System.out.print("Please select a command: \n");
                    System.out.print(" 1--Check a day of the month for an event\n");
                    System.out.print(" 2--Modify a day's events\n");
                    System.out.print(" 3--Exit");
                    int num1;
                    try {
                        num1 = scanner.nextInt();
                        scanner.nextLine();
                    } catch(InputMismatchException e) {
                        num1 = 0;
                    }
                    switch(num1) {
                        case 1:
                            int num2;
                            try {
                                num2 = scanner.nextInt();
                                scanner.nextLine();
                            } catch(InputMismatchException e) {
                                num2 = 0;
                            }
                            System.out.print(dao.getEvents(c.get(Calendar.YEAR), (c.get(Calendar.MONTH) + 1), num2) + "\n");
                            break;
                        case 2:
                            System.out.print("Chose a day to modify. \n");
                            // Get an input and test if it is a valid day in decemeber
                            try {
                                num2 = scanner.nextInt();
                                scanner.nextLine();
                            } catch(InputMismatchException e) {
                                System.out.println("That's not a number, try again\n");
                                break;
                            }
                            // Okay here is some year stuff
                            int num3 = (c.get(Calendar.MONTH) + 1);
                            int num4 = c.get(Calendar.YEAR);
                            System.out.print("Do you want to use a month and date besides the currunt one? 1-yes 0-no \n");
                            try {
                                num1 = scanner.nextInt();
                                scanner.nextLine();
                            } catch(InputMismatchException e) {
                                num1 = 0;
                            }
                            switch(num1) {
                                case 1:
                                    try {
                                        num3 = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch(InputMismatchException e) {
                                    }
                                    try {
                                        num4 = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch(InputMismatchException e) {
                                    }
                                case 0:
                                    System.out.print("You either pressed 0 or entered something valid, thanks. \n");
                            }
                            if(ser.isValidDay(num4, num3, num2) == false){
                                System.out.println("That's not a valid day, try again\n");
                                break;
                            }
                            String string1;
                            System.out.print("Write out the name of the event you want to add. \n");
                            try {
                                string1 = scanner.nextLine();
                            } catch(InputMismatchException e) {
                                System.out.print("Somehow you broke this, try again. \n");
                                break;
                            }
                            // Trying out stuff for a descripation
                            String string2 = null;
                            System.out.print("Do you want to add a event descripation? 1-yes 0-no \n");
                            try {
                                num1 = scanner.nextInt();
                                scanner.nextLine();
                            } catch(InputMismatchException e) {
                                num1 = 0;
                            }
                            switch(num1) {
                                case 1:
                                    try {
                                        string2 = scanner.nextLine();
                                    } catch(InputMismatchException e) {
                                        System.out.print("Somehow you broke this, try again. \n");
                                    }
                                case 0:
                                    System.out.print("You either pressed 0 or entered something valid, thanks. \n");
                            }
                            Day day1 = new Day(num4, num3, num2);
                            event event1 = new event(string1, string2);
                            dao.insertDay(day1);
                            dao.insertEvent(event1);
                            dao.bindDayToEvent(day1, event1);
                            break;
                        case 3:
                            System.out.print("Have a nice day.");
                            on = false;
                            break;
                        default:
                            System.out.print("That is not a valid order, try again!\n");
                            break;
                    }
                }
            }
        //END 
        }catch(SQLException e){
            System.err.println("Connection Failed!");
        }
    }
}