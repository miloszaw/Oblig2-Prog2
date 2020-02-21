/**
 *
 * @author Milosz A. Wudarczyk
 * @version v. 1.0.0
 */

import java.time.LocalDate;

public class BasicMember extends BonusMember {

    public BasicMember(int memberNo, Personals personals, LocalDate enrolledDate, int bonuspoints) {
        super(memberNo, personals, enrolledDate, bonuspoints);
    }
}
