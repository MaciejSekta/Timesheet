package com.msekta.timesheet.enums;

public enum UserRole {

    WORKER("Worker"),
    MANAGER("Manager"),
    ADMIN("Admin"),
    ACCOUNTANT("Accountant");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserRole getEnum(String value) {
        for(UserRole v : values())
            if(v.getName().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
