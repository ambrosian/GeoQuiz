package com.example.a20210305032_;

public class User {
    private String mName;
    private int mScore;
    private String mFirstName;

    public User(String name, String firstName, int score) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        this.mName = name;
        this.mFirstName = firstName;
        this.mScore = score;
    }

    public User() {
        this.mName = "Unknown";
        this.mFirstName = "Unknown";
        this.mScore = 0;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.mName = name;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        this.mFirstName = firstName;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.mScore = score;
    }
}