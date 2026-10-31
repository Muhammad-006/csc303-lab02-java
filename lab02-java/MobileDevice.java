/**
 * Task 5B - inheritance + interface. A MobileDevice IS-A Device and CAN-DO Connectable.
 * This models program behaviour only; no Bluetooth, Wi-Fi or Android connection is made.
 */
public class MobileDevice extends Device implements Connectable {
    private boolean connected;

    public MobileDevice(String name, int battery) {
        super(name, battery); // the superclass constructor validates name and battery
        connected = false;
    }

    @Override
    public void connect() {
        connected = true; // calling connect() again leaves the state true
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public String summary() {
        // Reuse the superclass text instead of duplicating the name/battery fields.
        return super.summary() + " | Connected: " + connected;
    }
}
