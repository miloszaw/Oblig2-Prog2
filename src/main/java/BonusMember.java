/**
 * The bonus member class that is responsible for holding all the information of a user
 * @author Milosz A. Wudarczyk
 * @version v. 1.0.0
 */

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class BonusMember {

    private final int memberNo;
    private final Personals personals;
    private final LocalDate enrolledDate;
    protected ArrayList<Points> bonuspointList;
    protected int bonuspoints;
    protected static double FACTOR_SILVER = 1.2;
    protected static double FACTOR_GOLD = 1.5;


    /**
     * Constructor for the class of BonusMember
     * @param memberNo the ID of a member given at random
     * @param personals an object of class personals which describes the member. Name, password etc.
     * @param enrolledDate the date at which the member was created
     * @param bonuspoints amount of points the member begins with
     */
    public BonusMember(int memberNo, Personals personals, LocalDate enrolledDate, int bonuspoints)
    {
        // Checks if all the parameters contains a valid value
        if (memberNo == 0
                || personals == null
                || enrolledDate == null) {
            throw new IllegalArgumentException("One or more of the parameters are invalid.");
        }
        this.memberNo = memberNo;
        this.personals = personals;
        this.enrolledDate = enrolledDate;
        this.bonuspoints = bonuspoints;
        bonuspointList = new ArrayList<>();

    }

    public BonusMember(int memberNo, Personals personals, LocalDate enrolledDate, int bonuspoints, ArrayList<Points> bonuspointList)
    {
        this.memberNo = memberNo;
        this.personals = personals;
        this.enrolledDate = enrolledDate;
        this.bonuspoints = bonuspoints;
        this.bonuspointList = bonuspointList;
    }

    /**
     * Returns memberNo
     * @return memberNo
     */
    public int getMemberNo() {
        return memberNo;
    }

    /**
     * Returns object of class personals
     * @return personals
     */
    public Personals getPersonals() {
        return personals;
    }

    /**
     * Returns enrolled date
     * @return enrolled date
     */
    public LocalDate getEnrolledDate() {
        return enrolledDate;
    }

    /**
     * Returns amount of points
     * @return bonuspoints
     */
    public int getPoints() {
        return bonuspoints;
    }

    public ArrayList<Points> getBonuspointList() {
        return bonuspointList;
    }

    /**
     * Finds the amount of points which qualify. Points qualify if they have been acquired in the last 365 days.
     * @param date The date to check against
     * @return returns the bonuspoints or 0 if none qualify
     */
    public int findQualificationPoints(LocalDate date)
    {
        int points = 0;
        for (Points p : bonuspointList) {
            if (ChronoUnit.DAYS.between(p.getDate(), date) < 365) // checks amount of days between the given date and the date the points were registered on
            {
                points += p.getPoints();
            }
        }
        return points;
    }

    /**
     * Checks password against a parameter of String
     * @param password the String parameter
     * @return boolean based on if the password matches
     */
    public boolean okPassword(String password)
    {
        return personals.okPassword(password);
    }

    /**
     * Registers points
     * @param points amount of points to add
     */
    public void registerPoints(int points, LocalDate date) {
        // Checks if all the parameters contains a valid value
        if (memberNo == 0
                || points < 1
                || date == null
                || points != Math.floor(points)) {
            throw new IllegalArgumentException("One or more of the parameters are invalid.");
        }
        bonuspointList.add(new Points(points, date));
        updatePoints();
    }

    /**
     * Updates the total amount of points the user has
     */
    public void updatePoints()
    {
        bonuspoints = bonuspointList.stream().reduce(0, (p, d) -> p + d.getPoints(), Integer::sum);
    }

}

