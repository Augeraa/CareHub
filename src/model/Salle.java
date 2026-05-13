package model;
import java.io.Serializable;
public class Salle implements Serializable {
    private static int compteur = 1;
    private final int id;
    private String nom;
    private String type;
    private int capacite;
    private int occupation;
    public Salle(String nom, String type, int capacite) {
        this.id = compteur++;
        this.nom = nom;
        this.type = type;
        this.capacite = capacite;
        this.occupation = 0;
    }
    public int getId()          { return id; }
    public String getNom()      { return nom; }
    public String getType()     { return type; }
    public int getCapacite()    { return capacite; }
    public int getOccupation()  { return occupation; }
    public int getLitsLibres()  { return capacite - occupation; }
    public boolean estPleine()  { return occupation >= capacite; }
    public void admettre()      { if (!estPleine()) occupation++; }
    public void liberer()       { if (occupation > 0) occupation--; }
    public String toString()    { return nom + " [" + type + "] " + occupation + "/" + capacite; }
}