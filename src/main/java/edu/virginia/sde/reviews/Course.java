package edu.virginia.sde.reviews;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Course {
    private final int id;
    private final String subject;
    private final int number;
    private final String title;
    private final double avgRating;

    public Course(String subject, int number, String title) {
        this.id = -1;
        this.subject = subject;
        this.number = number;
        this.title = title;
        this.avgRating = -1;
    }

    public Course(int id, String subject, int number, String title, double avgRating) {
        this.id = id;
        this.subject = subject;
        this.number =  number;
        this.title = title;
        this.avgRating = avgRating;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(subject)
                .append(" ")
                .append(number)
                .append(": ")
                .append(title)
                .append("\n Rating: ");
        if (avgRating != -1) {
            var truncated = BigDecimal.valueOf(getAvgRating()).setScale(2, RoundingMode.DOWN);
            stringBuilder.append(truncated);
        } else {
            stringBuilder.append("N/A");
        }
        return stringBuilder.toString();
    }
}
