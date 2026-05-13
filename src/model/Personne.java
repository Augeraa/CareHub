package model;
import java.io.Serializable;
public abstract class Personne implements Serializable {
    private static int compteur = 1;
    private final int id;
    private String nom, prenom, telephone;
    public Personne(String nom, String prenom, String telephone) {
        this.id = compteur++;
        this.nom = nom; this.prenom = prenom; this.telephone = telephone;
    }
    public abstract String getRole();
    public int getId()             { return id; }
    public String getNom()         { return nom; }
    public String getPrenom()      { return prenom; }
    public String getTelephone()   { return telephone; }
    public void setNom(String n)   { this.nom = n; }
    public void setPrenom(String p){ this.prenom = p; }
    public void setTelephone(String t){ this.telephone = t; }
    public String getNomComplet()  { return prenom + " " + nom; }
}