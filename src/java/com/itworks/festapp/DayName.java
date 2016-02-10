package com.itworks.festapp;

public enum DayName {
    Day1("First Day"),
    Day2("Second Day");

    private final String text;


    DayName(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
