package model;
import java.util.*;
public class Patient extends Personne implements Soignable, Facturable {
    private String dateAdmission, dateSortie, antecedents;
    private List<Soin> soins = new ArrayList<>();
    private double totalFacture = 0;
    public Patient(String nom, String prenom, String telephone, String dateAdmission, String antecedents) {
        super(nom, prenom, telephone);
        this.dateAdmission = dateAdmission;
        this.antecedents = antecedents;
        this.dateSortie = "Hospitalise";
    }
    public String getRole()              { return "Patient"; }
    public String getDateAdmission()     { return dateAdmission; }
    public String getDateSortie()        { return dateSortie; }
    public String getAntecedents()       { return antecedents; }
    public void setDateAdmission(String d){ this.dateAdmission = d; }
    public void setAntecedents(String a) { this.antecedents = a; }
    public void sortir(String date)      { this.dateSortie = date; }
    public void ajouterSoin(Soin s)      { soins.add(s); totalFacture += s.getCout(); }
    public List<Soin> getSoins()         { return soins; }
    public double calculerFacture()      { return totalFacture; }
    public void ajouterMontant(double m) { totalFacture += m; }
}