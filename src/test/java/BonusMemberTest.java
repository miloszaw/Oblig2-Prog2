import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class used to test the calculation of bonus points in the different classes
 * representing the different memberships.
 * To use this class, you first have to set up the Unit-test framework in your IDE.
 */
class BonusMemberTest {

    private LocalDate testDate;
    private Personals ole;
    private Personals tove;

    @BeforeEach
    void setUp() {
        this.testDate = LocalDate.of(2020, 2, 21);
        this.ole = new Personals("Olsen", "Ole",
                "ole.olsen@dot.com", "ole");
        this.tove = new Personals("Hansen", "Tove",
                "tove.hansen@dot.com", "tove");
    }

    /**
     * Tests the accuracy of the calculation of points for the basic member Ole.
     * Ole is registered as a Basic Member and all his points have been registered
     * outside the 365 days interval that would make them qualify for an upgrade.
     * No points should show up as being qualifying.
     */
    @Test
    void testBasicMemberOle() {
        BasicMember b1 = new BasicMember(100, ole,
                LocalDate.of(2020, 2, 15), 0);
        b1.registerPoints(30000, LocalDate.of(2018, 1, 1));
        System.out.println("Test nr 1: No qualification points");
        assertEquals(0, b1.findQualificationPoints(testDate));
        assertEquals(30000, b1.getPoints());

        System.out.println("Test nr 2: Adding 15 000 points, still no qualification points");
        b1.registerPoints(15000, LocalDate.of(2012, 5, 19));
        assertEquals(0, b1.findQualificationPoints(testDate));
        assertEquals(45000, b1.getPoints());
    }

    /**
     * Tests the accuracy of the calculation of points for the Basic Member Tove,
     * whose points were registered within the 365 day interval which should make them
     * qualify for an upgrade, first to Silver, then to Gold.
     */
    @Test
    void testBasicMemberTove() {
        BasicMember b2 = new BasicMember(110, tove,
                LocalDate.of(2020, 3, 5), 0);
        b2.registerPoints(30000, LocalDate.of(2020, 1, 15));

        System.out.println("Test nr 3: Tove should qualify");
        assertEquals(30000, b2.findQualificationPoints(testDate));
        assertEquals(30000, b2.getPoints());

        System.out.println("Test nr 4: Tove as silver member");
        SilverMember b3 = new SilverMember(b2.getMemberNo(), b2.getPersonals(),
                b2.getEnrolledDate(), b2.getPoints(), b2.getBonuspointList());
        b3.registerPoints(50000, LocalDate.of(2019, 8, 21));
        assertEquals( 90000, b3.findQualificationPoints(testDate));
        assertEquals( 90000, b3.getPoints());

        System.out.println("Test nr 5: Tove as gold member");
        GoldMember b4 = new GoldMember(b3.getMemberNo(), b3.getPersonals(),
                b3.getEnrolledDate(), b3.getPoints(), b3.getBonuspointList());
        b4.registerPoints(30000, LocalDate.of(2019, 5, 25));
        assertEquals( 135000, b4.findQualificationPoints(testDate));
        assertEquals( 135000, b4.getPoints());

        System.out.println("Test nr 6: Changed test date on Tove, more than 365 days since points added");
        testDate = LocalDate.of(2021, 12, 10);
        assertEquals( 0, b4.findQualificationPoints(testDate));
        assertEquals( 135000, b4.getPoints());

    }

    /**
     * Tests the passwords of both members.
     */
    @Test
    void testPasswords() {
        System.out.println("Test nr 7: Trying wrong password on Ole");
        assertFalse(ole.okPassword("000"));
        System.out.println("Test nr 8: Trying correct password on Tove.");
        assertTrue(tove.okPassword("tove"));
    }
}