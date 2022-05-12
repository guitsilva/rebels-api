package com.github.guitsilva.rebelsapi.domain;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
