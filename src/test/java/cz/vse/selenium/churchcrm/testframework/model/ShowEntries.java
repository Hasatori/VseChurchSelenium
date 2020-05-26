package cz.vse.selenium.churchcrm.testframework.model;

public enum ShowEntries {
    E_10(10), E_25(25), E_50(50), E_100(100);

    private final Integer value;

     ShowEntries(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
