package model;
public abstract class Personnel extends Personne implements Planifiable {
    private String service;
    private java.util.List<String> planning = new java.util.ArrayList<>();
    public Personnel(String nom, String prenom, String telephone, String service) {
        super(nom, prenom, telephone);
        this.service = service;
    }
    public String getService()       { return service; }
    public void setService(String s) { this.service = s; }
    public void ajouterCreneau(String c) { planning.add(c); }
    public java.util.List<String> getPlanning() { return planning; }
}