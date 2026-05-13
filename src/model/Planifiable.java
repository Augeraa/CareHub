package model;
public interface Planifiable {
    void ajouterCreneau(String creneau);
    java.util.List<String> getPlanning();
}