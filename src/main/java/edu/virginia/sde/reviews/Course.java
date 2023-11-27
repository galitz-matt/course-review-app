package edu.virginia.sde.reviews;

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
        this.avgRating = 0;
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
        return avgRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return id == course.id && number == course.number && Objects.equals(subject, course.subject) && Objects.equals(title, course.title);
    }
}
