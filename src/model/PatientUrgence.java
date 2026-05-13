package model;
public class PatientUrgence extends Patient {
    private int niveauUrgence; // 1=critique, 2=urgent, 3=normal
    private String motif;
    public PatientUrgence(String nom, String prenom, String telephone,
                          String dateAdmission, String antecedents,
                          int niveauUrgence, String motif) {
        super(nom, prenom, telephone, dateAdmission, antecedents);
        this.niveauUrgence = niveauUrgence;
        this.motif = motif;
    }
    public int getNiveauUrgence()  { return niveauUrgence; }
    public String getMotif()       { return motif; }
    public String getNiveauLabel() {
        return switch (niveauUrgence) {
            case 1 -> "CRITIQUE";
            case 2 -> "URGENT";
            default -> "NORMAL";
        };
    }
    @Override public String getRole() { return "Urgence"; }
}