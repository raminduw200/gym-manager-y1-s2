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

    //creating an object from MyGymManager
    private static MyGymManager admin = new Manager.MyGymManager();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Print the introduction
        System.out.println(
                "|**********************************************************************************************|\n" +
                "|-----------------------           Welcome to the Gym Manager         -------------------------|\n" +
                "|-----------------------     Please select one of the option below    -------------------------|\n" +
                "|**********************************************************************************************|"
        );

        //When the CLI runs, open the GUI in background.
        new Thread() {
            @Override
            public void run(){
                super.run();
                GUI.main();
            }
        }.start();

        //displaying menu while taking a correct input
        displayMenu();
        while (!sc.hasNextInt()){
            System.out.println("Please enter a given option.");
            System.out.print("\tSelect one given option: ");
            sc.next();
        }
        String selection = sc.next();

        do {
            if (selection.equals("1")) {
                addMember();
            } else if (selection.equals("2")) {
                deleteMember();
            } else if (selection.equals("3")) {
                admin.printMembers();
            } else if (selection.equals("4")) {
                admin.sortMembers();
            } else if (selection.equals("5")) {
                saveData();
            } else if (selection.equals("6")) {
                System.out.println("Opening GUI....");
                Platform.runLater(GUI::stageShow);
//                GUI.stageShow();
            } else {
                //if user input is non of the options given,
                System.out.println("Please enter the given option from 1-6 or -1 to exit.");
            }
            displayMenu();
            selection = sc.next();
        } while (!selection.equals("-1"));
        System.out.println("------------------------------Have a nice day ! Bye-----------------------------\n");
    }

    private static void addMember(){
        Scanner sc = new Scanner(System.in);
        String membershipID;
        String name;
        String membershipDate;
        boolean flag;
        do {
            flag = false;
            System.out.print("Membership ID : ");
            membershipID = sc.nextLine().trim();
            System.out.print("Name : ");
            name = sc.nextLine().trim();
            System.out.print("Membership CLI.Date : ");
            //take user input for membership date using CLI.Date.dateValidate method
            membershipDate = Date.dateValidate();
            for (DefaultMember mem : admin.getMembersArray()){
                if (mem.getMembershipNumber().equals(membershipID)){
                    System.out.println("Membership ID cannot repeat.");
                    flag = true;
                }
            }
            if (name.equals("") || membershipID.equals("")) {
                System.out.println("MembershipID , Name can not be empty !");
                flag = true;
            }
        } while (membershipDate.equals("error") || flag);

        //change data type membershipDate to java.util.LocalDate and assign to a new variable membershipValidDate
        LocalDate membershipValidDate = null;
        try {
            membershipValidDate = LocalDate.parse(
                    membershipDate, DateTimeFormatter.ofPattern("yyyy MM dd", Locale.ENGLISH)
            );
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

        //Input School Name
        if (memberType.equals("2")) {
            System.out.print("Enter school of the student : ");
            String school = sc.nextLine();
            school = sc.nextLine();
            StudentMember memberObj = new StudentMember(membershipID, name, membershipValidDate, school);
            admin.addNewMember(memberObj);

        } else if (memberType.equals("3")) {

            //Input Age
            System.out.print("Enter age of the member : ");
            while (!sc.hasNextInt()){
                System.out.println("Please enter an integer.");
                sc.next();
            }
            int age = sc.nextInt();
            Over60Member memberObj = new Over60Member(membershipID, name, membershipValidDate, age);
            do {
                while (!sc.hasNextInt()){
                    System.out.println("Please enter an integer.");
                    sc.next();
                }
                age = sc.nextInt();
            } while (!memberObj.setAge(age));
            memberObj.setAge(age);
            if (memberObj.setAge(age)) {
                admin.addNewMember(memberObj);
            }

        } else {
            DefaultMember memberObj = new DefaultMember(membershipID, name, membershipValidDate);
            admin.addNewMember(memberObj);
        }
        System.out.println("\nMember Added Successfully.\n");
    }

    //Taking an input for which user to be delete - passing it to deleteMember method
    private static void deleteMember(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the membership ID of the member : ");
        String deleteMemID = sc.next().trim();
        admin.deleteMember(deleteMemID);
    }

    //Taking an input for the name of the file to be saved - passing it to writeFile method
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
