import java.time.LocalDate;

public class Points {
    private int points;
    private LocalDate date;

    public Points(int points, LocalDate date) {
        this.points = points;
        this.date = date;
    }

    /**
     * Returns amount of points
     * @return amount of points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets amount of points
     * @param points amount of points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns the date the points were added on
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
