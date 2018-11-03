package com.msekta.timesheet.enums;

public enum UserType {

    INTERNAL ("Internal"),
    EXTERNAL ("External");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserType getEnum(String value) {
        for(UserType v : values())
            if(v.getName().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
