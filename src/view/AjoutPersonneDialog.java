package view;
import model.*;
import javax.swing.*;
import java.awt.*;
public class AjoutPersonneDialog extends JDialog {
    private Personne resultat = null;
    private final JComboBox<String> cbRole = new JComboBox<>(new String[]{"Patient","Medecin","Infirmier"});
    private final JTextField tfNom    = new JTextField(14);
    private final JTextField tfPrenom = new JTextField(14);
    private final JTextField tfTel    = new JTextField(14);
    private final JTextField tfInfo1  = new JTextField(14);
    private final JTextField tfInfo2  = new JTextField(14);
    private final JLabel lbInfo1 = new JLabel("Admission :");
    private final JLabel lbInfo2 = new JLabel("Antecedents :");
    public AjoutPersonneDialog(JFrame parent) {
        super(parent, "Ajouter une personne", true);
        buildUI(); pack(); setLocationRelativeTo(parent);
    }
    private void buildUI() {
        cbRole.addActionListener(e -> updateLabels());
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12,16,4,16));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5,5,5,5); gc.anchor = GridBagConstraints.WEST;
        Object[][] rows = {{"Role :",cbRole},{"Nom :",tfNom},{"Prenom :",tfPrenom},{"Tel :",tfTel},{lbInfo1,tfInfo1},{lbInfo2,tfInfo2}};
        for (int i=0;i<rows.length;i++) {
            gc.gridx=0; gc.gridy=i;
            form.add(rows[i][0] instanceof String ? new JLabel((String)rows[i][0]) : (Component)rows[i][0], gc);
            gc.gridx=1; form.add((Component)rows[i][1], gc);
        }
        JButton ok=new JButton("Ajouter"); JButton cancel=new JButton("Annuler");
        ok.addActionListener(e -> valider()); cancel.addActionListener(e -> dispose());
        JPanel b=new JPanel(new FlowLayout(FlowLayout.RIGHT)); b.add(cancel); b.add(ok);
        setLayout(new BorderLayout()); add(form,BorderLayout.CENTER); add(b,BorderLayout.SOUTH);
    }
    private void updateLabels() {
        String r=(String)cbRole.getSelectedItem();
        if ("Patient".equals(r))      { lbInfo1.setText("Admission :"); lbInfo2.setText("Antecedents :"); }
        else if ("Medecin".equals(r)) { lbInfo1.setText("Service :"); lbInfo2.setText("Specialite :"); }
        else                          { lbInfo1.setText("Service :"); lbInfo2.setText("Unite :"); }
    }
    private void valider() {
        String nom=tfNom.getText().trim(),prenom=tfPrenom.getText().trim(),tel=tfTel.getText().trim(),i1=tfInfo1.getText().trim(),i2=tfInfo2.getText().trim();
        if (nom.isEmpty()||prenom.isEmpty()||tel.isEmpty()||i1.isEmpty()||i2.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Tous les champs sont obligatoires.","Erreur",JOptionPane.WARNING_MESSAGE); return;
        }
        String r=(String)cbRole.getSelectedItem();
        resultat=switch(r){case "Patient"->new Patient(nom,prenom,tel,i1,i2);case "Medecin"->new Medecin(nom,prenom,tel,i1,i2);default->new Infirmier(nom,prenom,tel,i1,i2);};
        dispose();
    }
    public Personne getResultat() { return resultat; }
}