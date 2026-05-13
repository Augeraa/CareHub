package exception;
public class CapaciteDepasseeException extends Exception {
    public CapaciteDepasseeException(int max) {
        super("Capacite maximale de " + max + " patients atteinte.");
    }
}