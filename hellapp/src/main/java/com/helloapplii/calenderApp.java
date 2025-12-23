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
                        scanner.nextLine();
                        num1 = 0;
                    }
                    switch(num1) {
                        case 1:
                            System.out.print("Chose a day to view. \n");
                            int num2;
                            try {
                                num2 = scanner.nextInt();
                                scanner.nextLine();
                            } catch(InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("That's not a number, try again");
                                break;
                            }
                            int num3 = (c.get(Calendar.MONTH) + 1);
                            int num4 = c.get(Calendar.YEAR);
                            System.out.print("Do you want to use a month and year besides the currunt one? 1-yes 0-no \n");
                            try {
                                num1 = scanner.nextInt();
                                scanner.nextLine();
                            } catch(InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("That's not a number, try again");
                                break;
                            }
                            switch(num1) {
                                case 1:
                                    try {
                                        num3 = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch(InputMismatchException e) {
                                        scanner.nextLine();
                                        System.out.println("That's not a number, try again");
                                        break;
                                    }
                                    try {
                                        num4 = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch(InputMismatchException e) {
                                        scanner.nextLine();
                                        System.out.println("That's not a number, try again");
                                        break;
                                    }
                                case 0:
                            }
                            if(ser.isValidDay(num4, num3, num2) == false){
                                System.out.println("That's not a valid day, try again");
                                break;
                            }
                            System.out.print(dao.getEvents(num4, num3, num2) + "\n");
                            break;
                        case 2:
                            System.out.print("Chose a day to modify. \n");
                            // Get an input and test if it is a valid day in decemeber
                            try {
                                num2 = scanner.nextInt();
                                scanner.nextLine();
                            } catch(InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("That's not a number, try again");
                                break;
                            }
                            // Okay here is some year stuff
                            num3 = (c.get(Calendar.MONTH) + 1);
                            num4 = c.get(Calendar.YEAR);
                            System.out.print("Do you want to use a month and year besides the current one? 1-yes 0-no \n");
                            try {
                                num1 = scanner.nextInt();
                                scanner.nextLine();
                            } catch(InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("That's not a number, try again");
                                break;
                            }
                            switch(num1) {
                                case 1:
                                    try {
                                        num3 = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch(InputMismatchException e) {
                                        scanner.nextLine();
                                        System.out.println("That's not a number, try again");
                                        break;
                                    }
                                    try {
                                        num4 = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch(InputMismatchException e) {
                                        scanner.nextLine();
                                        System.out.println("That's not a number, try again");
                                        break;
                                    }
                                case 0:
                            }
                            if(ser.isValidDay(num4, num3, num2) == false){
                                System.out.println("That's not a valid day, try again");
                                break;
                            }
                            System.out.print("Do you want to add or delete an event from a day? 1-add 0-delete \n");
                            try {
                                num1 = scanner.nextInt();
                                scanner.nextLine();
                            } catch(InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("That's not a number, try again");
                                break;
                            }
                            String string1;
                            if(num1 == 1){
                                System.out.print("Write out the name of the event you want to add. \n");
                            }
                            else{
                                System.out.print("Write out the name of the event you want to delete. \n");
                            }
                            try {
                                string1 = scanner.nextLine();
                            } catch(InputMismatchException e) {
                                scanner.nextLine();
                                System.out.print("Somehow you broke this, try again. \n");
                                break;
                            }
                            // Trying out stuff for a descripation
                            String string2 = null;
                            if(num1 == 1){
                                System.out.print("Do you want to add a event descripation? 1-yes 0-no \n");
                                try {
                                    num1 = scanner.nextInt();
                                    scanner.nextLine();
                                } catch(InputMismatchException e) {
                                    scanner.nextLine();
                                    System.out.println("That's not a number, try again");
                                    break;
                                }
                                switch(num1) {
                                    case 1:
                                        try {
                                            string2 = scanner.nextLine();
                                        } catch(InputMismatchException e) {
                                            scanner.nextLine();
                                            System.out.print("Somehow you broke this, try again. \n");
                                            break;
                                        }
                                    case 0:
                                        num1 = 1;
                                }
                            }
                            Day day1 = new Day(num4, num3, num2);
                            event event1 = new event(string1, string2);
                            dao.insertDay(day1);
                            dao.insertEvent(event1);
                            if(num1 == 1){
                                dao.bindDayToEvent(day1, event1);
                            }
                            else{
                                dao.unbindDayToEvent(day1, event1);
                            }
                            break;
                        case 3:
                            System.out.print("Have a nice day.");
                            on = false;
                            break;
                        case 0:
                            System.out.print("That is not a valid order, try again!\n");
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