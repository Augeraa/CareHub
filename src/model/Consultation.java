package model;
public class Consultation extends Soin {
    private String medecin, prescription;
    public Consultation(String date, String description, double cout, String medecin, String prescription) {
        super(date, description, cout);
        this.medecin = medecin; this.prescription = prescription;
    }
    public String getTypeSoin()     { return "Consultation"; }
    public String getMedecin()      { return medecin; }
    public String getPrescription() { return prescription; }
}