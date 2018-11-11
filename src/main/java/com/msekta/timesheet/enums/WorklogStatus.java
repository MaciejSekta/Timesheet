package com.msekta.timesheet.enums;

public enum WorklogStatus {

    ACCEPTED ("Accepted"),
    REJECTED ("Rejected"),
    PENDING ("Pending");

    private String name;

    WorklogStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WorklogStatus getEnum(String value) {
        for(WorklogStatus v : values())
            if(v.getName().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
