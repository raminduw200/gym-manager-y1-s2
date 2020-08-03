package Manager;

import Member.DefaultMember;

import java.io.IOException;
import java.util.ArrayList;

public interface GymManager {
    public void addNewMember (DefaultMember member);
    public void deleteMember (String membershipID);
    public void printMembers ();
    public void sortMembers ();
    public void writeFile (String txtFile) throws IOException;
    public ArrayList<DefaultMember> getMembersArray();
    public void writeSerializer ();
    public void readSerializer ();
    public void clearMemberCount();
}
