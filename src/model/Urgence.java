package model;
public interface Urgence {
    void declencherUrgence(String motif);
    String getMotifUrgence();
    boolean estEnUrgence();
}