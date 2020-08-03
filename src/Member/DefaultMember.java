package Member;

import java.io.Serializable;
import java.time.LocalDate;

public class DefaultMember implements Comparable<DefaultMember>, Serializable {
    private final String membershipNumber;
    private final String name;
    private final LocalDate startMembershipDate;

    public DefaultMember(String membershipNumber, String name, LocalDate startMembershipDate){
        super();
        this.name = name;
        this.startMembershipDate = startMembershipDate;
        this.membershipNumber = membershipNumber;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartMembershipDate() {
        return startMembershipDate;
    }

    //override the compareTo method to compare two DefaultMember Object with its Name
    @Override
    public int compareTo(DefaultMember member){
        return this.name.compareTo(member.getName());
    }
}
