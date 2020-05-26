package cz.vse.selenium.churchcrm.testframework.model;

import java.util.Arrays;

public enum EventType {

    Church_Service("Church Service", "1-Church Service"), Sunday_School("Sunday School", "2-Sunday School");

    private final String value1, value2;

    EventType(String value1, String value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }


    public static EventType customValueOf(String value) {
        return Arrays.stream(EventType.values())
                .filter(eventType -> eventType.name().equals(value) || eventType.value1.equals(value) || eventType.value2.equals(value))
                .findFirst().orElseThrow(() -> new IllegalStateException("No EventType found for value " + value));
    }
}
