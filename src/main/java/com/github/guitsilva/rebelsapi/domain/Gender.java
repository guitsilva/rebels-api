package com.github.guitsilva.rebelsapi.domain;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    private final String description;

    private Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
