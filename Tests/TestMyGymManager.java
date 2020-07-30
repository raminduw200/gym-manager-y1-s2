import Manager.MyGymManager;
import Member.DefaultMember;
import Member.Over60Member;
import Member.StudentMember;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static org.junit.Assert.*;

public class TestMyGymManager {

    private static MyGymManager testAdmin = new Manager.MyGymManager();

    public static ArrayList<DefaultMember> test() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy MM dd", Locale.ENGLISH);
        testAdmin.addNewMember(new DefaultMember("001", "AAA", LocalDate.parse(
                "2020 05 21", dateFormat)));
        testAdmin.addNewMember(new StudentMember("002", "BBB", LocalDate.parse(
                "2020 05 21", dateFormat), "BBBSchool"));
        testAdmin.addNewMember(new Over60Member("003", "CCC", LocalDate.parse(
                "2020 05 21", dateFormat), 65));
//        testAdmin.printMembers();
        return testAdmin.getMembersArray();
    }

    @Test
    public void testDefaultMember() {
        assertSame(test().get(0),test().get(0));
        assertSame(test().get(1),test().get(1));
        assertSame(test().get(2),test().get(2));
    }

    @After
    public void clear0() {
        testAdmin.clearMemberCount();
        testAdmin.getMembersArray().clear();
    }

    @Test
    public void testMemberCapacityEquals() {
        assertEquals(3, test().size());
    }

    @After
    public void clear1() {
        testAdmin.clearMemberCount();
        testAdmin.getMembersArray().clear();
    }

    @Test
    public void testMemberCapacityAccuracy() {
        assertTrue(test().size() < 100);
        testAdmin.getMembersArray().clear();
        testAdmin.clearMemberCount();
    }

    @After
    public void clear2() {
        testAdmin.clearMemberCount();
        testAdmin.getMembersArray().clear();
    }

    @Test
    public void testFirstObjectsExists() {
        assertNotNull(test().get(0));
    }

    @After
    public void clear3() {
        testAdmin.clearMemberCount();
        testAdmin.getMembersArray().clear();
    }

    @Test
    public void testLastObjectsExists() {
        assertNotNull(test().get(test().size() - 1));
    }

    @After
    public void clear4() {
        testAdmin.clearMemberCount();
        testAdmin.getMembersArray().clear();
    }

    @Test
    public void testSorting() {
        Collections.sort(test());
        testAdmin.sortMembers();
        assertSame(test().get(0),test().get(0));
        assertSame(test().get(1),test().get(1));
        assertSame(test().get(2),test().get(2));
    }

    @After
    public void clear5() {
        testAdmin.clearMemberCount();
        testAdmin.getMembersArray().clear();
    }

}
