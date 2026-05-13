package model;
public class Infirmier extends Personnel {
    private String unite;
    public Infirmier(String nom, String prenom, String telephone, String service, String unite) {
        super(nom, prenom, telephone, service);
        this.unite = unite;
    }
    public String getRole()        { return "Infirmier"; }
    public String getUnite()       { return unite; }
    public void setUnite(String u) { this.unite = u; }
}