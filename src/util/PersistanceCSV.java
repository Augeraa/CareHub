package util;
import model.*;
import java.io.*;
import java.util.*;
public class PersistanceCSV {
    private static final String FICHIER = "resources/hopital.csv";
    public static void sauvegarder(List<Personne> personnes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FICHIER))) {
            for (Personne p : personnes) {
                if (p instanceof PatientUrgence pu)
                    pw.println("Urgence," + pu.getNom() + "," + pu.getPrenom() + "," +
                        pu.getTelephone() + "," + pu.getDateAdmission() + "," +
                        pu.getAntecedents() + "," + pu.getNiveauUrgence() + "," + pu.getMotif());
                else if (p instanceof Patient pt)
                    pw.println("Patient," + pt.getNom() + "," + pt.getPrenom() + "," +
                        pt.getTelephone() + "," + pt.getDateAdmission() + "," + pt.getAntecedents());
                else if (p instanceof Medecin m)
                    pw.println("Medecin," + m.getNom() + "," + m.getPrenom() + "," +
                        m.getTelephone() + "," + m.getService() + "," + m.getSpecialite());
                else if (p instanceof Infirmier i)
                    pw.println("Infirmier," + i.getNom() + "," + i.getPrenom() + "," +
                        i.getTelephone() + "," + i.getService() + "," + i.getUnite());
            }
        } catch (IOException e) { System.err.println("Erreur sauvegarde : " + e.getMessage()); }
    }
    public static List<Personne> charger() {
        List<Personne> liste = new ArrayList<>();
        File f = new File(FICHIER);
        if (!f.exists()) return liste;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] c = ligne.split(",");
                switch (c[0]) {
                    case "Patient"   -> liste.add(new Patient(c[1],c[2],c[3],c[4],c[5]));
                    case "Medecin"   -> liste.add(new Medecin(c[1],c[2],c[3],c[4],c[5]));
                    case "Infirmier" -> liste.add(new Infirmier(c[1],c[2],c[3],c[4],c[5]));
                    case "Urgence"   -> liste.add(new PatientUrgence(c[1],c[2],c[3],c[4],c[5],Integer.parseInt(c[6]),c[7]));
                }
            }
        } catch (IOException e) { System.err.println("Erreur chargement : " + e.getMessage()); }
        return liste;
    }
}