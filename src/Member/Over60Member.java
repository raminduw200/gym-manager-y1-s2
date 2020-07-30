package Member;

import java.time.LocalDate;
import java.util.Date;

public class Over60Member extends DefaultMember {
    private int age;

    public Over60Member(String membershipNumber, String name, LocalDate startMembershipDate, int age){
        super(membershipNumber, name, startMembershipDate);
        setAge(age);
    }

    public int getAge() {
        return age;
    }

    public boolean setAge(int age) {
        if (age>=60) {
            this.age = age;
            return true;
        } else {
            System.out.println("Please enter valid age for Over 60 member " +
                    "or change the member type.");
            return false;
        }
    }
}
