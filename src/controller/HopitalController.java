package controller;
import exception.*;
import model.*;
import util.Gestionnaire;
import util.PersistanceCSV;
import java.util.*;
import java.util.stream.Collectors;
public class HopitalController {
    private final Gestionnaire<Personne> gestionnaire = new Gestionnaire<>();
    private final List<Salle> salles = new ArrayList<>();
    private static final int CAPACITE_MAX = 100;

    public HopitalController() {
        salles.add(new Salle("Salle A", "Chirurgie", 10));
        salles.add(new Salle("Salle B", "Urgences", 5));
        salles.add(new Salle("Salle C", "Medecine", 15));
        List<Personne> charges = PersistanceCSV.charger();
        if (charges.isEmpty()) {
            try {
                ajouter(new Patient("Bernard", "Alice", "0601010101", "2024-01-10", "Diabete"));
                ajouter(new Patient("Petit", "Louis", "0602020202", "2024-01-12", "Aucun"));
                ajouter(new Medecin("Martin", "Jean", "0603030303", "Cardiologie", "Cardiologue"));
                ajouter(new Medecin("Dupont", "Sophie", "0604040404", "Urgences", "Generaliste"));
                ajouter(new Infirmier("Leroy", "Marc", "0605050505", "Chirurgie", "Bloc A"));
                ajouter(new PatientUrgence("Durand", "Paul", "0606060606", "2024-01-15", "Asthme", 1, "Crise cardiaque"));
                ajouter(new PatientUrgence("Moreau", "Julie", "0607070707", "2024-01-15", "Aucun", 2, "Fracture"));
                getPatients().get(0).ajouterSoin(new Consultation("2024-01-10", "Bilan cardiaque", 80.0, "Dr Martin", "Repos"));
                salles.get(0).admettre(); salles.get(1).admettre(); salles.get(1).admettre();
            } catch (Exception e) { e.printStackTrace(); }
        } else {
            charges.forEach(gestionnaire::ajouter);
        }
    }

    public void ajouter(Personne p) throws CapaciteDepasseeException, PatientDejaAdmisException {
        if (p instanceof Patient) {
            long nb = gestionnaire.getTous().stream().filter(x -> x instanceof Patient).count();
            if (nb >= CAPACITE_MAX) throw new CapaciteDepasseeException(CAPACITE_MAX);
            boolean existe = gestionnaire.getTous().stream()
                .filter(x -> x instanceof Patient)
                .anyMatch(x -> x.getNomComplet().equalsIgnoreCase(p.getNomComplet()));
            if (existe) throw new PatientDejaAdmisException(p.getNomComplet());
        }
        gestionnaire.ajouter(p);
        sauvegarder();
    }

    public void supprimer(Personne p) {
        gestionnaire.supprimer(p);
        sauvegarder();
    }

    public void modifier(Personne p, String nom, String prenom, String tel) {
        p.setNom(nom); p.setPrenom(prenom); p.setTelephone(tel);
        sauvegarder();
    }

    public void sauvegarder() {
        PersistanceCSV.sauvegarder(gestionnaire.getTous());
    }

    public List<Personne> getTous()    { return gestionnaire.getTous(); }
    public List<Patient> getPatients() {
        return gestionnaire.getTous().stream()
            .filter(p -> p instanceof Patient).map(p -> (Patient) p).collect(Collectors.toList());
    }
    public List<Personnel> getPersonnel() {
        return gestionnaire.getTous().stream()
            .filter(p -> p instanceof Personnel).map(p -> (Personnel) p).collect(Collectors.toList());
    }
    public List<Salle> getSalles() { return Collections.unmodifiableList(salles); }
    public PriorityQueue<PatientUrgence> getFileUrgences() { return gestionnaire.getFileUrgences(); }

    public List<Personne> filtrer(String role, String nom, String date) {
        return gestionnaire.filtrer(p ->
            (role == null || role.isEmpty() || p.getRole().equalsIgnoreCase(role)) &&
            (nom  == null || nom.isEmpty()  || p.getNomComplet().toLowerCase().contains(nom.toLowerCase())) &&
            (date == null || date.isEmpty() || (p instanceof Patient pt && pt.getDateAdmission().contains(date)))
        );
    }
    public List<Personne> trierParNom()  { return gestionnaire.trierPar(Comparator.comparing(Personne::getNom)); }
    public List<Personne> trierParRole() { return gestionnaire.trierPar(Comparator.comparing(Personne::getRole)); }

    public String getStatistiques() {
        List<Personne> tous = gestionnaire.getTous();
        Map<String, Long> parRole = gestionnaire.compterParRole();
        double factTotal = getPatients().stream().mapToDouble(Patient::calculerFacture).sum();
        long nbHosp  = getPatients().stream().filter(p -> "Hospitalise".equals(p.getDateSortie())).count();
        long nbSoins = getPatients().stream().mapToLong(p -> p.getSoins().size()).sum();
        double factMoy = getPatients().isEmpty() ? 0 : factTotal / getPatients().size();
        long nbUrgences = gestionnaire.getFileUrgences().size();
        int litsLibres = salles.stream().mapToInt(Salle::getLitsLibres).sum();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Total personnes  : %d%n", tous.size()));
        parRole.forEach((r, n) -> sb.append(String.format("  %-12s : %d%n", r, n)));
        sb.append(String.format("Hospitalises     : %d%n", nbHosp));
        sb.append(String.format("Total soins      : %d%n", nbSoins));
        sb.append(String.format("Patients urgence : %d%n", nbUrgences));
        sb.append(String.format("Lits libres      : %d%n", litsLibres));
        sb.append(String.format("Facture totale   : %.2f EUR%n", factTotal));
        sb.append(String.format("Facture moyenne  : %.2f EUR", factMoy));
        return sb.toString();
    }
}