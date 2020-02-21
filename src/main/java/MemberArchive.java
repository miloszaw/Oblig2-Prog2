import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class MemberArchive {

    ArrayList<BonusMember> memberArchive;
    public MemberArchive()
    {
        memberArchive = new ArrayList<BonusMember>();
    }

    public BonusMember getAt(int index)
    {
        return memberArchive.get(index);
    }

    public int findPoints(int memberNo, String password)
    {
        for(int i = 0; i < memberArchive.size(); i++)
        {
            if (memberArchive.get(i).getMemberNo() == memberNo)
            {
                BonusMember m = memberArchive.get(i);
                i = memberArchive.size();
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

    public void addMember(Personals pers, LocalDate dateEnrolled, int bonuspoints)
    {
        memberArchive.add(new BasicMember(getAvailableNo(), pers, dateEnrolled, bonuspoints));
    }

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
                }
            }
            if (unique)
            {
                found = true;
                return n;
            }
        }
        return -1;
    }

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

