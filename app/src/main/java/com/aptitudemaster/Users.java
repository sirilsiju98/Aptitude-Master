package com.aptitudemaster;

public class Users {
    long id;
    long pin;
    String name;
    double score;
    public Users(){

    }

    public Users(long id, long pin, String name, double score) {
        this.id = id;
        this.pin = pin;
        this.name = name;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public long getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
