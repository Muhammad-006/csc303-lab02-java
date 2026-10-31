import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * CSC303 Lab 02 - Java programming fundamentals.
 * Compile : javac Main.java Device.java Connectable.java MobileDevice.java
 * Run     : java -cp . Main
 *
 * Each task prints under its own heading so the output can be screenshotted per task.
 */
public class Main {

    public static void main(String[] args) {
        heading("TASK 1 - VERIFY JAVA AND RUN THE STARTER");
        System.out.println("CSC303 Lab 02 - Java ready");

        heading("TASK 2 - TYPES, CONTROL FLOW AND METHODS");
        task2();

        heading("TASK 3 - ARRAYS AND COLLECTIONS");
        task3();

        heading("TASK 4 - CLASSES AND ENCAPSULATION");
        task4();

        heading("TASK 5 - INHERITANCE, INTERFACES AND POLYMORPHISM");
        task5();

        heading("TASK 6 - EXCEPTION HANDLING AND TESTING (T1-T8)");
        task6();

        heading("TASK 7 - INTEGRATED INVENTORY");
        task7();
    }

    // ------------------------------------------------------------------ Task 2
    private static void task2() {
        // 2A - declarations and arithmetic
        String deviceName = "Sensor-A";
        int battery = 15;
        boolean connected = false;
        double ratio = 90 / 60.0;
        System.out.println(deviceName + " | battery=" + battery);
        System.out.println("Connected: " + connected);
        System.out.println("Ratio: " + ratio);
        System.out.println("Phone-B".equals(deviceName));
        System.out.println("90 / 60   = " + (90 / 60) + "   (integer division: remainder discarded)");
        System.out.println("90 / 60.0 = " + (90 / 60.0) + " (floating-point division keeps the fraction)");

        // 2B - the decision method
        System.out.println("--- 2B batteryStatus ---");
        int[] samples = {15, 20, 80};
        for (int sample : samples) {
            System.out.println("batteryStatus(" + sample + ") = " + batteryStatus(sample));
        }

        // 2C - several readings
        System.out.println("--- 2C readings ---");
        int[] readings = {15, 80, 45};
        int lowCount = 0;
        for (int reading : readings) {
            String status = batteryStatus(reading);
            System.out.println("Reading " + reading + " -> " + status);
            if (status.equals("LOW")) {
                lowCount++;
            }
        }
        System.out.println("Sum: " + sum(readings) + " / count: " + readings.length);
        System.out.printf(Locale.ROOT, "Average: %.2f%n", averageBattery(readings));
        System.out.println("Number of LOW readings: " + lowCount);
    }

    /** Below 20 is LOW; 20 through 100 is READY; values outside 0-100 are invalid (Task 6). */
    static String batteryStatus(int battery) {
        if (battery < 0 || battery > 100) {
            throw new IllegalArgumentException("Battery must be between 0 and 100 but was " + battery);
        }
        if (battery < 20) {
            return "LOW";
        } else {
            return "READY";
        }
    }

    /** Floating-point average; rejects a null or empty array and any reading outside 0-100. */
    static double averageBattery(int[] readings) {
        if (readings == null || readings.length == 0) {
            throw new IllegalArgumentException("Readings must not be null or empty");
        }
        for (int reading : readings) {
            if (reading < 0 || reading > 100) {
                throw new IllegalArgumentException("Reading out of range 0-100: " + reading);
            }
        }
        // (double) forces floating-point division; plain int / int would drop the decimals.
        return (double) sum(readings) / readings.length;
    }

    static int sum(int[] readings) {
        int total = 0;
        for (int reading : readings) {
            total += reading;
        }
        return total;
    }

    // ------------------------------------------------------------------ Task 3
    private static void task3() {
        List<String> names = new ArrayList<>();
        names.add("Sensor-A");
        names.add("Phone-B");
        names.add("Tablet-C");
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("Size: " + names.size());
        System.out.println("Contains Phone-B: " + names.contains("Phone-B"));

        // Remove by value OUTSIDE the traversal loop (removing inside would break the iterator).
        names.remove("Tablet-C");
        System.out.println("Remaining:");
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("Size: " + names.size());

        System.out.println("Why: an array exposes a fixed 'length' field; a List is a class with a size() method");
        System.out.println("because its size changes. List<String> lets the compiler reject non-String items;");
        System.out.println("a raw List accepts any Object and can fail later with ClassCastException.");
    }

    // ------------------------------------------------------------------ Task 4
    private static void task4() {
        List<Device> devices = new ArrayList<>();
        devices.add(new Device("Sensor-A", 15));
        devices.add(new Device("Phone-B", 80));
        devices.add(new Device("Tablet-C", 45));
        for (Device device : devices) {
            System.out.println("Summary: " + device.summary() + " | Status: " + batteryStatus(device.getBattery()));
        }
    }

    // ------------------------------------------------------------------ Task 5
    private static void task5() {
        MobileDevice phone = new MobileDevice("Phone-B", 80);
        Connectable connection = phone;                         // interface reference
        System.out.println("isConnected() before connect(): " + connection.isConnected());
        connection.connect();                                   // call made through the INTERFACE reference
        Device item = phone;                                    // superclass reference
        System.out.println(item.summary());                     // call made through the SUPERCLASS reference
        System.out.println("The actual object type (MobileDevice) decides which summary() runs.");
    }

    // ------------------------------------------------------------------ Task 6
    private static int passed = 0;
    private static int failed = 0;

    private static void task6() {
        // T1 - valid boundary values
        int[] boundaryInputs = {0, 19, 20, 100};
        String[] boundaryExpected = {"LOW", "LOW", "READY", "READY"};
        for (int i = 0; i < boundaryInputs.length; i++) {
            check("T1", "batteryStatus(" + boundaryInputs[i] + ")", boundaryExpected[i], batteryStatus(boundaryInputs[i]));
        }

        // T2 - invalid values must throw, and each case is handled and reported separately
        check("T2", "batteryStatus(-1)", "IllegalArgumentException", thrownBy(() -> batteryStatus(-1)));
        check("T2", "batteryStatus(101)", "IllegalArgumentException", thrownBy(() -> batteryStatus(101)));

        // T3 - average of {15, 80, 45}: 46.666... internally, 46.67 for display
        String average = String.format(Locale.ROOT, "%.2f", averageBattery(new int[]{15, 80, 45}));
        check("T3", "averageBattery({15, 80, 45})", "46.67", average);

        // T4 - invalid inputs to averageBattery
        check("T4", "averageBattery({})", "IllegalArgumentException", thrownBy(() -> averageBattery(new int[]{})));
        check("T4", "averageBattery(null)", "IllegalArgumentException", thrownBy(() -> averageBattery(null)));
        check("T4", "averageBattery({15, 101})", "IllegalArgumentException", thrownBy(() -> averageBattery(new int[]{15, 101})));

        // T5 - a valid Device
        Device valid = new Device("Sensor-A", 15);
        check("T5", "new Device(\"Sensor-A\", 15).getName()", "Sensor-A", valid.getName());
        check("T5", "getBattery()", "15", String.valueOf(valid.getBattery()));
        check("T5", "summary()", "Sensor-A | Battery: 15%", valid.summary());

        // T6 - invalid constructor calls are rejected
        check("T6", "new Device(null, 15)", "IllegalArgumentException", thrownBy(() -> new Device(null, 15)));
        check("T6", "new Device(\"  \", 15)", "IllegalArgumentException", thrownBy(() -> new Device("  ", 15)));
        check("T6", "new Device(\"Sensor-A\", -1)", "IllegalArgumentException", thrownBy(() -> new Device("Sensor-A", -1)));
        check("T6", "new Device(\"Sensor-A\", 101)", "IllegalArgumentException", thrownBy(() -> new Device("Sensor-A", 101)));

        // T7 - MobileDevice connection state: false, then true, then true
        MobileDevice mobile = new MobileDevice("Phone-B", 80);
        check("T7", "isConnected() before connect()", "false", String.valueOf(mobile.isConnected()));
        mobile.connect();
        check("T7", "isConnected() after connect()", "true", String.valueOf(mobile.isConnected()));
        mobile.connect();
        check("T7", "isConnected() after second connect()", "true", String.valueOf(mobile.isConnected()));

        // T8 - Integer.parseInt: each input is handled independently, so later inputs still run.
        // NumberFormatException is a subclass of IllegalArgumentException, so it is caught FIRST.
        String[] inputs = {"80", "abc", "120"};
        String[] expected = {"READY", "NumberFormatException", "IllegalArgumentException"};
        for (int i = 0; i < inputs.length; i++) {
            String text = inputs[i];
            String actual;
            String message;
            try {
                int value = Integer.parseInt(text);
                actual = batteryStatus(value);
                message = text + ": " + actual;
            } catch (NumberFormatException ex) {
                actual = "NumberFormatException";
                message = text + ": enter a whole number";
            } catch (IllegalArgumentException ex) {
                actual = "IllegalArgumentException";
                message = text + ": " + ex.getMessage();
            }
            System.out.println("   (" + message + ")");
            check("T8", "parse \"" + text + "\"", expected[i], actual);
        }

        System.out.println();
        System.out.println("Summary: " + passed + " passed, " + failed + " failed");
    }

    /** Runs the code and returns the simple name of the exception it throws, or "no exception". */
    private static String thrownBy(Runnable code) {
        try {
            code.run();
            return "no exception";
        } catch (RuntimeException ex) {
            return ex.getClass().getSimpleName();
        }
    }

    private static void check(String id, String input, String expected, String actual) {
        boolean ok = expected.equals(actual);
        if (ok) {
            passed++;
        } else {
            failed++;
        }
        System.out.println(id + " | Input: " + input + " | Expected: " + expected
                + " | Actual: " + actual + " | " + (ok ? "PASS" : "FAIL"));
    }

    // ------------------------------------------------------------------ Task 7
    private static void task7() {
        MobileDevice phone = new MobileDevice("Phone-B", 80);
        phone.connect();

        List<Device> inventory = new ArrayList<>();
        inventory.add(new Device("Sensor-A", 15));
        inventory.add(phone);                       // the connected MobileDevice replaces the plain Phone-B
        inventory.add(new Device("Tablet-C", 45));

        for (Device device : inventory) {
            System.out.println(device.summary());
            System.out.println("Status: " + batteryStatus(device.getBattery()));
        }

        System.out.println();
        System.out.println("--- Polymorphism Check ---");
        Device item = inventory.get(1);
        System.out.println("Reference type: Device");
        System.out.println("Actual object type: " + item.getClass().getSimpleName());
        System.out.println("Summary through Device reference:");
        System.out.println(item.summary());
    }

    private static void heading(String title) {
        System.out.println();
        System.out.println("========================================");
        System.out.println(title);
        System.out.println("========================================");
    }
}
