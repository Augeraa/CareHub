package view;
import model.Patient;
import javax.swing.*;
import java.awt.*;
public class SortiePatientDialog extends JDialog {
    private boolean confirme = false;
    private final JTextField tfDate = new JTextField(12);
    public SortiePatientDialog(JFrame parent, Patient p) {
        super(parent, "Sortie de " + p.getNomComplet(), true);
        buildUI(); pack(); setLocationRelativeTo(parent);
    }
    private void buildUI() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12,16,4,16));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5,5,5,5); gc.anchor = GridBagConstraints.WEST;
        gc.gridx=0; gc.gridy=0; form.add(new JLabel("Date de sortie :"), gc);
        gc.gridx=1; form.add(tfDate, gc);
        JButton ok = new JButton("Confirmer sortie"); JButton cancel = new JButton("Annuler");
        ok.addActionListener(e -> { confirme = true; dispose(); });
        cancel.addActionListener(e -> dispose());
        JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT)); b.add(cancel); b.add(ok);
        setLayout(new BorderLayout()); add(form, BorderLayout.CENTER); add(b, BorderLayout.SOUTH);
    }
    public boolean isConfirme() { return confirme; }
    public String getDate()     { return tfDate.getText().trim(); }
}