package model;
import java.util.*;
public class Medecin extends Personnel implements Soignable {
    private String specialite;
    private List<Soin> soins = new ArrayList<>();
    public Medecin(String nom, String prenom, String telephone, String service, String specialite) {
        super(nom, prenom, telephone, service);
        this.specialite = specialite;
    }
    public String getRole()             { return "Medecin"; }
    public String getSpecialite()       { return specialite; }
    public void setSpecialite(String s) { this.specialite = s; }
    public void ajouterSoin(Soin s)     { soins.add(s); }
    public List<Soin> getSoins()        { return soins; }
}