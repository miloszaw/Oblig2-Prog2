/**
 * The silver member class which contains an increased point factor for each points registered
 * @author Milosz A. Wudarczyk
 * @version v. 1.0.0
 */

import java.time.LocalDate;
import java.util.ArrayList;

public class SilverMember extends BonusMember {

    public SilverMember(int memberNo, Personals personals, LocalDate enrolledDate, int bonuspoints) {
        super(memberNo, personals, enrolledDate, bonuspoints);
    }

    public SilverMember(int memberNo, Personals personals, LocalDate enrolledDate, int bonuspoints, ArrayList<Points> bonuspointList)
    {
        super(memberNo, personals, enrolledDate, bonuspoints, bonuspointList);
    }

    /**
     * Registers points to be added with a factor of 1.2
     * @param points amount of points to add
     * @param date date at which the points were earned
     */
    @Override
    public void registerPoints(int points, LocalDate date)
    {
        if (points > 0 && points == Math.floor(points)) // Checks if point value is valid, greater than 0 and not a double / decimal value
        {
            bonuspointList.add(new Points((int) (points*FACTOR_SILVER), date));
            updatePoints();
        }
    }
}
