package view;
import model.*;
import javax.swing.*;
import java.awt.*;
public class AjoutSoinDialog extends JDialog {
    private Soin resultat = null;
    private final JComboBox<String> cbType = new JComboBox<>(new String[]{"Consultation","Chirurgie"});
    private final JTextField tfDate    = new JTextField(10);
    private final JTextField tfDesc    = new JTextField(14);
    private final JTextField tfCout    = new JTextField(8);
    private final JTextField tfInfo1   = new JTextField(14);
    private final JTextField tfInfo2   = new JTextField(14);
    private final JLabel lbInfo1 = new JLabel("Medecin :");
    private final JLabel lbInfo2 = new JLabel("Prescription :");

    public AjoutSoinDialog(JFrame parent) {
        super(parent, "Ajouter un soin", true);
        buildUI(); pack(); setLocationRelativeTo(parent);
    }

    private void buildUI() {
        cbType.addActionListener(e -> updateLabels());
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12,16,4,16));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5,5,5,5); gc.anchor = GridBagConstraints.WEST;
        Object[][] rows = {
            {"Type :", cbType}, {"Date :", tfDate}, {"Description :", tfDesc},
            {"Cout (EUR) :", tfCout}, {lbInfo1, tfInfo1}, {lbInfo2, tfInfo2}
        };
        for (int i = 0; i < rows.length; i++) {
            gc.gridx=0; gc.gridy=i;
            form.add(rows[i][0] instanceof String ? new JLabel((String)rows[i][0]) : (Component)rows[i][0], gc);
            gc.gridx=1; form.add((Component)rows[i][1], gc);
        }
        JButton ok = new JButton("Ajouter"); JButton cancel = new JButton("Annuler");
        ok.addActionListener(e -> valider()); cancel.addActionListener(e -> dispose());
        JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT)); b.add(cancel); b.add(ok);
        setLayout(new BorderLayout()); add(form, BorderLayout.CENTER); add(b, BorderLayout.SOUTH);
    }

    private void updateLabels() {
        if ("Consultation".equals(cbType.getSelectedItem())) {
            lbInfo1.setText("Medecin :"); lbInfo2.setText("Prescription :");
        } else {
            lbInfo1.setText("Chirurgien :"); lbInfo2.setText("Motif urgence :");
        }
    }

    private void valider() {
        String date = tfDate.getText().trim(), desc = tfDesc.getText().trim();
        String coutStr = tfCout.getText().trim(), i1 = tfInfo1.getText().trim(), i2 = tfInfo2.getText().trim();
        if (date.isEmpty() || desc.isEmpty() || coutStr.isEmpty() || i1.isEmpty() || i2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double cout;
        try { cout = Double.parseDouble(coutStr); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cout invalide.", "Erreur", JOptionPane.WARNING_MESSAGE); return;
        }
        if ("Consultation".equals(cbType.getSelectedItem())) {
            resultat = new Consultation(date, desc, cout, i1, i2);
        } else {
            ActeChirurgical ac = new ActeChirurgical(date, desc, cout, i1);
            if (!i2.isEmpty()) ac.declencherUrgence(i2);
            resultat = ac;
        }
        dispose();
    }
    public Soin getResultat() { return resultat; }
}