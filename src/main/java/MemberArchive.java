/**
 * The member archive class which is responsible for
 * @author Milosz A. Wudarczyk
 * @version v. 1.0.0
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class MemberArchive {

    ArrayList<BonusMember> memberArchive;
    public MemberArchive()
    {
        memberArchive = new ArrayList<>();
    }

    /**
     * Gets the member at the given index
     * @param index index of the member
     * @return object of class BonusMember
     */
    public BonusMember getAt(int index)
    {
        return memberArchive.get(index);
    }

    /**
     * Finds the total amount of points for a member with the use of their memberNo and their password
     * @param memberNo memberNo of the member
     * @param password password of the member
     * @return total amount of points the user has
     */
    public int findPoints(int memberNo, String password)
    {
        for(int i = 0; i < memberArchive.size(); i++)
        {
            if (memberArchive.get(i).getMemberNo() == memberNo)
            {
                BonusMember m = memberArchive.get(i);
                if(m.okPassword(password))
                {
                    return m.getPoints();
                }
                else {
                    return -1;
                }
            }
        }
        return -1;
    }

    /**
     * Registers amount of points to a certain user by the member of memberNo
     * @param memberNo memberNo of the member
     * @param points amount of points to be registered
     * @param date date the points were awared
     * @return true or false depending on the success of the operation
     */
    public boolean registerPoints(int memberNo, int points, LocalDate date)
    {

        for(int i = 0; i < memberArchive.size(); i++)
        {
            if (memberArchive.get(i).getMemberNo() == memberNo)
            {
                BonusMember m = memberArchive.get(i);
                i = memberArchive.size();
                m.registerPoints(points, date);
            }
        }
        return false;
    }

    /**
     * Adds a new member
     * @param pers personal information of the user
     * @param dateEnrolled date which the user was enrolled
     * @param bonuspoints amount of bonuspoints they start with
     */
    public void addMember(Personals pers, LocalDate dateEnrolled, int bonuspoints)
    {
        memberArchive.add(new BasicMember(getAvailableNo(), pers, dateEnrolled, bonuspoints));
    }

    /**
     * Gets a random number between 1 and 1000 that has not yet been taken up as a memberNo
     * @return random unused number
     */
    private int getAvailableNo()
    {
        Random rand = new Random();
        boolean found = false;
        while (!found)
        {
            boolean unique = true;
            int n = rand.nextInt(1000);
            for( int i = 0 ; i < memberArchive.size(); i++)
            {
                BonusMember m = memberArchive.get(i);
                if (m.getMemberNo() == n)
                {
                    unique = false;
                    i = memberArchive.size();
                }
            }
            if (unique)
            {
                return n;
            }
        }
        return -1;
    }

    /**
     * Checks which members qualify for an upgrade and promptly upgrades them
     * @param date the date which is to be checked against
     */
    public void checkMembers(LocalDate date)
    {
        for (int i = 0; i < memberArchive.size(); i++)
        {
            BonusMember m = memberArchive.get(i);
            if(m instanceof BasicMember)
            {
                if (m.findQualificationPoints(date) >= 75000)
                {
                    GoldMember newM = new GoldMember(m.getMemberNo(), m.getPersonals(), m.getEnrolledDate(), m.getPoints(), m.getBonuspointList());
                    memberArchive.set(i, newM);
                }
                else if (m.findQualificationPoints(date) >= 25000)
                {
                    SilverMember newM = new SilverMember(m.getMemberNo(), m.getPersonals(), m.getEnrolledDate(), m.getPoints(), m.getBonuspointList());
                    memberArchive.set(i, newM);
                }
            }
            else if (m instanceof SilverMember)
            {
                if (m.findQualificationPoints(date) >= 75000)
                {
                    GoldMember newM = new GoldMember(m.getMemberNo(), m.getPersonals(), m.getEnrolledDate(), m.getPoints(), m.getBonuspointList());
                    memberArchive.set(i, newM);
                }
            }
        }
    }

}

