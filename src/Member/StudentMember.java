package Member;

import java.time.LocalDate;
import java.util.Date;

public class StudentMember extends DefaultMember {

    private String schoolName;

    public StudentMember(String membershipNumber, String name, LocalDate startMembershipDate, String schoolName){
        super(membershipNumber, name, startMembershipDate);
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}