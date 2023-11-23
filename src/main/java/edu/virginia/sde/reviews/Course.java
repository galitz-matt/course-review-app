package edu.virginia.sde.reviews;

import java.util.Objects;

public class Course {
    private int id;
    private String subject;
    private int number;
    private String title;
    private double avgRating;

    public Course(int id, String subject, int number, String title,double avgRating) {
        if(subject==null|title==null && subject.length()<=4 && number<=9999 &&title.length()<=50){
            throw new IllegalArgumentException("Illegal course information");
        }
        this.id = id;
        this.subject = subject;
        this.number =  number;
        this.title = title;
        this.avgRating=avgRating;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return id == course.id && number == course.number && Objects.equals(subject, course.subject) && Objects.equals(title, course.title);
    }

}
