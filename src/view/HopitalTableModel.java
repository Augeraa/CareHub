package view;
import model.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;
public class HopitalTableModel extends AbstractTableModel {
    private static final String[] COLS = {"ID","Nom","Prenom","Role","Info","Telephone"};
    private List<Personne> entites;
    public HopitalTableModel(List<Personne> e) { this.entites = e; }
    public void setEntites(List<Personne> e)   { this.entites = e; fireTableDataChanged(); }
    public Personne getPersonneAt(int row)      { return entites.get(row); }
    public int getRowCount()    { return entites.size(); }
    public int getColumnCount() { return COLS.length; }
    public String getColumnName(int c) { return COLS[c]; }
    public Object getValueAt(int row, int col) {
        Personne p = entites.get(row);
        return switch (col) {
            case 0 -> p.getId();
            case 1 -> p.getNom();
            case 2 -> p.getPrenom();
            case 3 -> p.getRole();
            case 4 -> {
                if (p instanceof Medecin m)   yield m.getSpecialite();
                if (p instanceof Infirmier i) yield "Unite: " + i.getUnite();
                if (p instanceof Patient pt)  yield "Adm: " + pt.getDateAdmission() + " | " + pt.getDateSortie();
                yield "-";
            }
            case 5 -> p.getTelephone();
            default -> "";
        };
    }
}