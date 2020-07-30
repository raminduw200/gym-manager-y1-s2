package Manager;

import Member.DefaultMember;
import Member.Over60Member;
import Member.StudentMember;

import java.io.*;
import java.util.ArrayList;

public class MyGymManager implements GymManager {

    private final ArrayList<DefaultMember> membersArray = new ArrayList<>();
    //change maximum number that can store by change below static variable
    private static final int MAX_MEMBERS = 100;
    private static int memberCount = 1;

    @Override
    public void addNewMember(DefaultMember newMember) {
        if (memberCount<=MAX_MEMBERS){
            System.out.print("\nNumber of members registered in the system : " + memberCount);
            membersArray.add(newMember);
            memberCount++;
        } else {
            System.out.println("Member count exceeds. Please remove ");
        }
    }

    @Override
    public void deleteMember(String membershipID) {
        /*
        Flag is used to find out whether the entered member is in the current list
        and if only prints the membershipID not found error
         */
        boolean flag = false;
        if (membersArray.isEmpty()){
            System.out.println("Cannot remove a member without adding. Add a member first.");
        } else {

            for (DefaultMember member : membersArray) {
                String tempMemNo = member.getMembershipNumber();
                if (tempMemNo.equals(membershipID)) {
                    if (member.getClass().getName().equals("Member.StudentMember")) {
                        System.out.println("Type of the membership : StudentMember");
                    } else if (member.getClass().getName().equals("Member.Over60Member")) {
                        System.out.println("Type of the membership : Over 60 Member");
                    } else {
                        System.out.println("Type of the membership : Default Member");
                    }
                    membersArray.remove(member);
                    System.out.println("Membership ID : " + membershipID + " member Successfully removed.");
                    flag = true;
                    if (memberCount != 0) {
                        memberCount--;
                        System.out.println(memberCount + " Free member slots are available");
                    }
                    break;
                }
            }

        }
        try {
            if (!flag){
                System.out.println("Membership ID not found. Please check the membership ID.");
//                System.out.println(membersArray.get(0).getClass().getName());
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Cannot remove a member without adding. Add a member first.");
        }
    }

    @Override
    public void printMembers() {
        for (DefaultMember member:membersArray){
            System.out.print(
                    "Membership Number : " + member.getMembershipNumber() + "\n" +
                            "Member Name : " + member.getName() + "\n" +
                            "Membership date : " + member.getStartMembershipDate() + "\n"
            );
            if (member.getClass().getName().equals("Member.StudentMember")){
                StudentMember tempStudent = (StudentMember) member;
                System.out.println("School name : " + tempStudent.getSchoolName() + "\n");
            } else if (member.getClass().getName().equals("Member.Over60Member")){
                Over60Member tempOver60 = (Over60Member) member;
                System.out.println("Member's age  : " + tempOver60.getAge() + "\n");
            } else {
                System.out.println();
            }
        } if (membersArray.isEmpty()){
            System.out.println("No members available to display.");
        }
    }

     //Sort Every member in the memberArray by it's name in ascending order
    @Override
    public void sortMembers() {
        if (membersArray.isEmpty()) {
            System.out.println("No members available to display.");
        } else {
            //bubble sorting
            for (int i = 0; i < membersArray.size() - 1; i++) {
                for (int j = 0; j < membersArray.size() - i - 1; j++) {
                    if (membersArray.get(j).compareTo(membersArray.get(j + 1)) > 0) {
                        DefaultMember tempMember = membersArray.get(j);
                        membersArray.set(j, membersArray.get(j + 1));
                        membersArray.set(j + 1, tempMember);
                    }
                }
            }
            System.out.println("Sort by name ( Ascending ) : successfully completed.\n");
        }
    }

    @Override
    public void writeFile(String txtFileName) throws IOException {
        if (!membersArray.isEmpty()) {
            //  Write to txt file - Character Stream
            File file = new File("Output/" + txtFileName + ".txt");
            FileWriter fw = null;
            PrintWriter pw = null;

            String memberDetails = null;

            for (DefaultMember member : membersArray) {
                memberDetails =
                        "Membership Number : " + member.getMembershipNumber() + "\n" +
                                "Member Name : " + member.getName() + "\n" +
                                "Membership date : " + member.getStartMembershipDate() + "\n";
                if (member.getClass().getName().equals("Member.StudentMember")) {
                    StudentMember tempStudent = (StudentMember) member;
                    memberDetails += "School name : " + tempStudent.getSchoolName() + "\n";
                } else if (member.getClass().getName().equals("Member.Over60Member")) {
                    Over60Member tempOver60 = (Over60Member) member;
                    memberDetails += "Member's age  : " + tempOver60.getAge() + "\n";
                } else {
                    memberDetails += "\n";
                }
            }

            try {
                fw = new FileWriter(file, true);
                pw = new PrintWriter(fw, true);
                pw.print(memberDetails);
            } catch (FileNotFoundException e) {
                System.out.println("Files are missing - Output/" + txtFileName + " is missing.");
            } catch (IOException e) {
                System.out.println("Input Output Error : MyGymManager - writeFile");
            } finally {
                assert fw != null;
                fw.close();
                assert pw != null;
                pw.close();
            }
        }else {
            System.out.println("No members available to write in the file. Press enter to continue.");
        }
    }

    @Override
    public ArrayList<DefaultMember> getMembersArray() {
        return membersArray;
    }

    @Override
    public void clearMemberCount(){
        memberCount=1;
    }
}
