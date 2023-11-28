package edu.virginia.sde.reviews;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

public class Course {
    private int id;
    private final String subject;
    private final int number;
    private final String title;
    private double avgRating;

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

    public Optional<Integer> getId() {
        return id == -1 ? Optional.empty() : Optional.of(id);
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
        var rating = BigDecimal.valueOf(avgRating);
        var truncatedRating = rating.setScale(2, RoundingMode.DOWN);
        return truncatedRating.doubleValue();
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
            stringBuilder.append(getAvgRating());
        } else {
            stringBuilder.append("N/A");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return id == course.id && number == course.number && Objects.equals(subject, course.subject) && Objects.equals(title, course.title);
    }
}
