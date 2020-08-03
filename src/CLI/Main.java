package CLI;

import Manager.MyGymManager;
import Member.DefaultMember;
import Member.Over60Member;
import Member.StudentMember;

import GUI.GUI;

import javafx.application.Platform;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {

    //creating an object from MyGymManager
    private static MyGymManager admin = new Manager.MyGymManager();
    private static int membershipID;

    public static void main(String[] args) throws IOException {
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

        admin.readSerializer();
        try {
            admin.setMemberCount(parseInt(readTemp("count.txt")));
            membershipID = parseInt(readTemp("memID.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR : temp file not found.");
        }

        //displaying menu while taking a correct input
        displayMenu();
//        while (!sc.hasNextInt()){
//            System.out.println("Please enter a given option.");
//            System.out.print("\tSelect one given option: ");
//            sc.next();
//        }
        String selection = sc.next();

        do {
            switch (selection) {
                case "1":
                    addMember();
                    break;
                case "2":
                    deleteMember();
                    break;
                case "3":
                    admin.printMembers();
                    break;
                case "4":
                    admin.sortMembers();
                    break;
                case "5":
                    saveData();
                    break;
                case "6":
                    System.out.println("Opening GUI....");
                    Platform.runLater(GUI::stageShow);
//                GUI.stageShow();
                    break;
                default:
                    //if user input is non of the options given,
                    System.out.println("Please enter the given option from 1-6 or -1 to exit.");
                    break;
            }
            displayMenu();
            selection = sc.next();
        } while (!selection.equals("-1"));

        admin.writeSerializer();
        try {
            writeTemp("count.txt", String.valueOf(admin.getMemberCount()));
        } catch (IOException e) {
            System.out.println(
                    "ERROR : Main - writeTemp\n" +
                            "count.txt file not found."
            );
        }
        writeTemp("memID.txt",String.valueOf(membershipID));
        System.out.println("------------------------------Have a nice day ! Bye-----------------------------\n");

    }

    private static void addMember() throws IOException {
        Scanner sc = new Scanner(System.in);
        String name;
        String membershipDate;
        boolean flag;
        do {
            flag = false;
            System.out.printf("%46s","Membership ID : ");
            System.out.println(String.format("%04d",membershipID));
            System.out.printf("%46s","Name : ");
            name = sc.nextLine().trim();
            System.out.printf("%46s","Membership Date : (YYYY MM DD) integers only: ");
            //take user input for membership date using CLI.Date.dateValidate method
            membershipDate = Date.dateValidate();
//            for (DefaultMember mem : admin.getMembersArray()){
//                if (mem.getMembershipNumber().equals(membershipID)){
//                    System.out.println("Membership ID cannot repeat.");
//                    flag = true;
//                }
//            }
            if (name.equals("")){
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
            StudentMember memberObj = new StudentMember(String.valueOf(String.format("%04d",membershipID)), name, membershipValidDate, school);
            admin.addNewMember(memberObj);

        } else if (memberType.equals("3")) {

            //Input Age
            int age;
            Over60Member memberObj = new Over60Member(String.valueOf(String.format("%04d",membershipID)), name, membershipValidDate, 60);
            System.out.print("Enter age of the member : ");
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
            DefaultMember memberObj = new DefaultMember(String.valueOf(String.format("%04d",membershipID)), name, membershipValidDate);
            admin.addNewMember(memberObj);
        }
        membershipID++;
        writeTemp("memID.txt",String.valueOf(membershipID));
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
        System.out.print(
                "If you want to write in previous file enter the previous name. \n" +
                        "Current data will append in previous file.\n" +
                "Enter the name of the file you want to store : ");
        String fileName = sc.nextLine();
        try {
            admin.writeFile(fileName);
        } catch (IOException e) {
            System.out.println("Input Output Error : CLI.Main - saveData\n" +
                    "file not found.");
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

    //  method to write in a text file.
    public static void writeTemp(String fName, String tempTxt) throws IOException {
        //  Write into a file - Character Stream
        File txtFile = new File("Output/tempFiles/"+fName);
        FileWriter fw = null;
        PrintWriter pw = null;
        try{
            fw = new FileWriter(txtFile, false);
            pw = new PrintWriter(fw, true);
            pw.print(tempTxt);
        } catch (FileNotFoundException e) {
            System.out.println(
                    "ERROR : Main - writeTemp\n" +
                            "count.txt file not found."
            );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fw.close();
            pw.close();
        }
    }

    //  method to read from a text file
    public static String readTemp(String fName) throws FileNotFoundException {
        File txt = new File("Output/tempFiles/"+fName);
        Scanner sc = new Scanner(txt);
        return sc.nextLine();
    }
}
