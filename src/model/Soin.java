package model;
import java.io.Serializable;
public abstract class Soin implements Serializable {
    private static int compteur = 1;
    private final int id;
    private String date, description;
    private double cout;
    public Soin(String date, String description, double cout) {
        this.id = compteur++;
        this.date = date; this.description = description; this.cout = cout;
    }
    public abstract String getTypeSoin();
    public int getId()             { return id; }
    public String getDate()        { return date; }
    public String getDescription() { return description; }
    public double getCout()        { return cout; }
}