package util;
import model.Personne;
import model.PatientUrgence;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
public class Gestionnaire<T extends Personne> {
    private final List<T> entites       = new ArrayList<>();
    private final Set<Integer> ids      = new HashSet<>();
    private final TreeMap<String, T> index = new TreeMap<>();
    // PriorityQueue pour les urgences (priorite par niveau)
    private final PriorityQueue<PatientUrgence> fileUrgences =
        new PriorityQueue<>(Comparator.comparingInt(PatientUrgence::getNiveauUrgence));

    public void ajouter(T e) {
        entites.add(e);
        ids.add(e.getId());
        index.put(e.getNomComplet(), e);
        if (e instanceof PatientUrgence pu) fileUrgences.offer(pu);
    }
    public boolean supprimer(T e) {
        ids.remove(e.getId());
        index.remove(e.getNomComplet());
        if (e instanceof PatientUrgence pu) fileUrgences.remove(pu);
        return entites.remove(e);
    }
    public List<T> getTous()   { return Collections.unmodifiableList(entites); }
    public Set<Integer> getIds() { return Collections.unmodifiableSet(ids); }
    public TreeMap<String, T> getIndex() { return index; }
    public PriorityQueue<PatientUrgence> getFileUrgences() { return new PriorityQueue<>(fileUrgences); }

    // Wildcard : accepte n'importe quelle liste de sous-type de Personne
    public void ajouterTous(List<? extends T> liste) { liste.forEach(this::ajouter); }

    public List<T> filtrer(Predicate<T> c) {
        return entites.stream().filter(c).collect(Collectors.toList());
    }
    public List<T> trierPar(Comparator<? super T> comp) {
        return entites.stream().sorted(comp).collect(Collectors.toList());
    }
    public Map<String, Long> compterParRole() {
        return entites.stream().collect(Collectors.groupingBy(Personne::getRole, Collectors.counting()));
    }
    public int taille() { return entites.size(); }
}