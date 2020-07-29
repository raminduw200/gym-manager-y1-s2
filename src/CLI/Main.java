package CLI;

import Manager.MyGymManager;
import Member.DefaultMember;
import Member.Over60Member;
import Member.StudentMember;

import GUI.GUI;

import javafx.application.Platform;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static MyGymManager admin = new Manager.MyGymManager();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(
                "|**********************************************************************************************|\n" +
                "|-----------------------           Welcome to the Gym Manager         -------------------------|\n" +
                "|-----------------------     Please select one of the option below    -------------------------|\n" +
                "|**********************************************************************************************|"
        );

        new Thread() {
            @Override
            public void run(){
                super.run();
                GUI.main();
            }
        }.start();


        displayMenu();
        while (!sc.hasNextInt()){
            System.out.println("Please enter a given option.");
            System.out.print("\tSelect one given option: ");
            sc.next();
        }
        int selection = sc.nextInt();

        do {
            if (selection == 1) {
                addMember();
            } else if (selection == 2) {
                deleteMember();
            } else if (selection == 3) {
                admin.printMembers();
            } else if (selection == 4) {
                admin.sortMembers();
            } else if (selection == 5) {
                saveData();
            } else if (selection == 6) {
                System.out.println("Opening GUI....");
                Platform.runLater(GUI::stageShow);
//                GUI.stageShow();
            } else {
                System.out.println("Please enter the given option from 1-6 or -1 to exit.");
            }
            displayMenu();
            selection = sc.nextInt();
        } while (selection !=-1);
        System.out.println("------------------------------Have a nice day ! Bye-----------------------------\n");
    }

    private static void addMember(){
        Scanner sc = new Scanner(System.in);
        String membershipID;
        String name;
        String membershipDate;
        do {
            System.out.print("Membership ID : ");
            membershipID = sc.nextLine().trim();
            System.out.print("Name : ");
            name = sc.nextLine().trim();
            System.out.print("Membership CLI.Date : ");
            //take user input for membership date using CLI.Date.dateValidate method
            membershipDate = Date.dateValidate();
            if (name.equals("") || membershipID.equals("")) {
                System.out.println("MembershipID , Name, school name can not be empty !");
            }
        } while (membershipDate.equals("error") || name.equals("") || membershipID.equals(""));

        //change data type membershipDate to java.util.LocalDate and assign to a new variable membershipValidDate
        LocalDate membershipValidDate = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd", Locale.ENGLISH);
            membershipValidDate = LocalDate.parse(membershipDate, formatter);
        } catch (DateTimeParseException e){
            System.out.println("Enter a valid date format as given. YYYY MM DD (with correct blanks and digits).");
        }


        String instructions = (
                "Enter the type of the member : \n" +
                        "\t1  - for Default Member\n" +
                        "\t2  - for Student Member\n" +
                        "\t3  - for Over 60 Member\n"
        );
        System.out.println(instructions);
        String memberType = sc.next();
        //validating the input
        while (!memberType.equals("1") && !memberType.equals("2") && !memberType.equals("3")) {
            System.out.println("Invalid input !\n" + instructions);
            memberType = sc.next();
        }

        if (memberType.equals("2")) {
            System.out.print("Enter school of the student : ");
            String school = sc.nextLine();
            school = sc.nextLine();
            StudentMember memberObj = new StudentMember(membershipID, name, membershipValidDate, school);
            admin.addNewMember(memberObj);
        } else if (memberType.equals("3")) {
            System.out.print("Enter age of the member : ");
            int age = sc.nextInt();
            Over60Member memberObj = new Over60Member(membershipID, name, membershipValidDate, age);
            admin.addNewMember(memberObj);
        } else {
            DefaultMember memberObj = new DefaultMember(membershipID, name, membershipValidDate);
            admin.addNewMember(memberObj);
        }
        System.out.println("\nMember Added Successfully.\n");
    }

    private static void deleteMember(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the membership ID of the member : ");
        String deleteMemID = sc.next().trim();
        admin.deleteMember(deleteMemID);
    }

    private static void saveData(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the file you want to store :");
        String fileName = sc.nextLine();
        try {
            admin.writeFile(fileName);
        } catch (IOException e) {
            System.out.println("Input Output Error : CLI.Main - saveData");
        }

        System.out.println("File exported. Saved in : Output/" + fileName);

    }

    public static ArrayList<DefaultMember> getMembers(){
        return admin.getMembersArray();
    }

    private static void displayMenu(){
        System.out.print(
                "\t\t\t\t + | 1 | --------------------------------- Add a new Member +\n" +
                "\t\t\t\t + | 2 | ------------------------------------ Delete Member +\n" +
                "\t\t\t\t + | 3 | ------------------------ Print the list of Members +\n" +
                "\t\t\t\t + | 4 | ------- Sort the Members by name (Ascending order) +\n" +
                "\t\t\t\t + | 5 | ----------------------------------- Save in a file +\n" +
                "\t\t\t\t + | 6 | ----------------------------------------- Open GUI +\n" +
                "\t\t\t\t + |-1 | --------------------------------------------- Exit +\n" +
                "\n\tSelect one : "
        );
    }

}
