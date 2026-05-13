package exception;
public class PatientDejaAdmisException extends Exception {
    public PatientDejaAdmisException(String nom) {
        super("Le patient " + nom + " est deja admis.");
    }
}