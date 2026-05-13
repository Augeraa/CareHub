package view;
import controller.HopitalController;
import model.PatientUrgence;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.PriorityQueue;
public class UrgencesPanel extends JDialog {
    public UrgencesPanel(JFrame parent, HopitalController controller) {
        super(parent, "File d'attente Urgences", true);
        String[] cols = {"Priorite", "Nom", "Motif", "Niveau"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        PriorityQueue<PatientUrgence> file = controller.getFileUrgences();
        while (!file.isEmpty()) {
            PatientUrgence p = file.poll();
            model.addRow(new Object[]{p.getNiveauLabel(), p.getNomComplet(), p.getMotif(), p.getNiveauUrgence()});
        }
        JTable table = new JTable(model);
        table.setRowHeight(24);
        JButton close = new JButton("Fermer");
        close.addActionListener(e -> dispose());
        JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT)); b.add(close);
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(b, BorderLayout.SOUTH);
        setSize(520, 320); setLocationRelativeTo(parent);
    }
}