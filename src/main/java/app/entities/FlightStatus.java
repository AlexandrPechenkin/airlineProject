package app.entities;

public enum FlightStatus {
    ACCORDING_TO_PLAN("ACCORDING_TO_PLAN"),
    DELAY("DELAY"),
    CANCELLATION("CANCELLATION");

    private String name;

    FlightStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
