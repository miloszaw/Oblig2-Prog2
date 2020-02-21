/**
 *
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
