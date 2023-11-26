package edu.virginia.sde.reviews;

import java.util.Objects;

public class Course {
    private int id;
    private String subject;
    private int number;
    private String title;
    private double avgRating;

    public Course(int id, String subject, int number, String title,double avgRating) {
        this.id = id;
        this.subject = subject;
        this.number =  number;
        this.title = title;
        this.avgRating = avgRating;
    }

    public double getAvgRating() {
        return avgRating;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return id == course.id && number == course.number && Objects.equals(subject, course.subject) && Objects.equals(title, course.title);
    }

}
