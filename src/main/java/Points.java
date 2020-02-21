import java.time.LocalDate;

public class Points {
    private int points;
    private LocalDate date;

    public Points(int points, LocalDate date) {
        this.points = points;
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
