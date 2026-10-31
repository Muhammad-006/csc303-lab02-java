/** Task 5A - the contract: anything that can connect must say how and report its state. */
public interface Connectable {
    void connect();

    boolean isConnected();
}
