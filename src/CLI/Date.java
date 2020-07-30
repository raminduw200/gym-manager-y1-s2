package CLI;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Date {

    //using static method to avoid changing the membership date once added
    //to change member should delete and add
    public static String dateValidate(){
        Scanner sc = new Scanner(System.in);

        System.out.print("(YYYY MM DD) integers only: ");
        String dateTime = sc.nextLine().trim();
        //splits to yyyy,mm,dd
        String[] dateTimeArr = dateTime.split(" ", 3);

        if (dateTime.equals("")){
            System.out.println("\nDate can not be empty !\n");
            return "error";
        }
        for (String i : dateTimeArr){
            try{
                if (parseInt(i) < 0){
                    System.out.println("\nCLI.Date can not be negative !\n");
                    return "error";
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter date with out characters and in right format. Ex : 2020 10 12\n");
                return "error";
            }
        }
        try {
            //membership should be below 2100
            if (parseInt(dateTimeArr[0]) > 2100) {
                System.out.println("\nPlease enter a valid year below 2100.\n");
                return "error";
            }
            //validate moths should be less than 12 months
            if (parseInt(dateTimeArr[1]) > 12) {
                System.out.println("\nPlease enter a valid month below 12.\n");
                return "error";
            }

            //validate moths should be less than 31 Days
            if (parseInt(dateTimeArr[2]) > 31) {
                System.out.println("\nPlease enter a valid date below 31.\n");
                return "error";
            }
            return dateTime;
        } catch (ArrayIndexOutOfBoundsException e){
            //validate user input contains missing few parameters
            System.out.println("\nPlease enter date in right format. Ex : 2020 10 12\n");
            return "error";
        }
    }
}
