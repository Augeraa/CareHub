package view;
import controller.HopitalController;
import model.Salle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class SallesPanel extends JDialog {
    public SallesPanel(JFrame parent, HopitalController controller) {
        super(parent, "Gestion des Salles", true);
        String[] cols = {"Salle", "Type", "Capacite", "Occupation", "Lits libres", "Statut"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (Salle s : controller.getSalles()) {
            model.addRow(new Object[]{
                s.getNom(), s.getType(), s.getCapacite(),
                s.getOccupation(), s.getLitsLibres(),
                s.estPleine() ? "PLEINE" : "Disponible"
            });
        }
        JTable table = new JTable(model);
        table.setRowHeight(24);
        JButton close = new JButton("Fermer");
        close.addActionListener(e -> dispose());
        JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT)); b.add(close);
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(b, BorderLayout.SOUTH);
        setSize(500, 250); setLocationRelativeTo(parent);
    }
}