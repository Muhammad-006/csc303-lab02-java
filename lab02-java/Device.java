/**
 * Task 4 - class and encapsulation: private final fields, a validating constructor
 * (Task 6) and public getters. No public fields.
 */
public class Device {
    private final String name;
    private final int battery;

    public Device(String name, int battery) {
        // null is checked BEFORE calling a method on it, so no NullPointerException occurs.
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Device name must not be null or blank");
        }
        if (battery < 0 || battery > 100) {
            throw new IllegalArgumentException("Battery must be between 0 and 100 but was " + battery);
        }
        this.name = name;
        this.battery = battery;
    }

    public String getName() {
        return name;
    }

    public int getBattery() {
        return battery;
    }

    /** Format: Sensor-A | Battery: 15% */
    public String summary() {
        return name + " | Battery: " + battery + "%";
    }
}
