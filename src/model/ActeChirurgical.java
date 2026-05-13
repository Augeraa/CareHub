package model;
public class ActeChirurgical extends Soin implements Urgence {
    private String chirurgien;
    private boolean enUrgence = false;
    private String motifUrgence;
    public ActeChirurgical(String date, String description, double cout, String chirurgien) {
        super(date, description, cout);
        this.chirurgien = chirurgien;
    }
    public String getTypeSoin()   { return "Chirurgie"; }
    public String getChirurgien() { return chirurgien; }
    public void declencherUrgence(String motif) { this.enUrgence = true; this.motifUrgence = motif; }
    public String getMotifUrgence() { return motifUrgence != null ? motifUrgence : "-"; }
    public boolean estEnUrgence()   { return enUrgence; }
}