package view;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class ModifierPersonneDialog extends JDialog {
    private boolean confirme = false;
    private final JTextField tfNom    = new JTextField(14);
    private final JTextField tfPrenom = new JTextField(14);
    private final JTextField tfTel    = new JTextField(14);
    private final Personne personne;
    private DefaultTableModel soinsModel;

    public ModifierPersonneDialog(JFrame parent, Personne p) {
        super(parent, "Modifier " + p.getNomComplet(), true);
        this.personne = p;
        tfNom.setText(p.getNom()); tfPrenom.setText(p.getPrenom()); tfTel.setText(p.getTelephone());
        buildUI(); setSize(520, 420); setLocationRelativeTo(parent);
    }

    private void buildUI() {
        JTabbedPane tabs = new JTabbedPane();

        // --- Onglet 1 : Infos generales ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(12,16,4,16));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6); gc.anchor = GridBagConstraints.WEST;
        Object[][] rows = {{"Nom :", tfNom},{"Prenom :", tfPrenom},{"Telephone :", tfTel}};
        for (int i=0; i<rows.length; i++) {
            gc.gridx=0; gc.gridy=i; formPanel.add(new JLabel((String)rows[i][0]), gc);
            gc.gridx=1; formPanel.add((Component)rows[i][1], gc);
        }
        tabs.addTab("Informations", formPanel);

        // --- Onglet 2 : Soins (uniquement si Patient) ---
        if (personne instanceof Patient pt) {
            JPanel soinsPanel = new JPanel(new BorderLayout(5,5));
            soinsPanel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

            String[] cols = {"Type","Date","Description","Cout (EUR)"};
            soinsModel = new DefaultTableModel(cols, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            chargerSoins(pt);
            JTable soinsTable = new JTable(soinsModel);
            soinsTable.setRowHeight(24);

            JButton btnAjouter = new JButton("Ajouter un soin");
            btnAjouter.addActionListener(e -> {
                AjoutSoinDialog d = new AjoutSoinDialog((JFrame) getOwner());
                d.setVisible(true);
                Soin s = d.getResultat();
                if (s != null) {
                    pt.ajouterSoin(s);
                    chargerSoins(pt);
                    JOptionPane.showMessageDialog(this,
                        "Soin ajoute. Total facture : " + String.format("%.2f", pt.calculerFacture()) + " EUR");
                }
            });

            JLabel lblTotal = new JLabel();
            majTotal(pt, lblTotal);

            JPanel south = new JPanel(new BorderLayout());
            south.add(btnAjouter, BorderLayout.WEST);
            south.add(lblTotal, BorderLayout.EAST);

            soinsPanel.add(new JScrollPane(soinsTable), BorderLayout.CENTER);
            soinsPanel.add(south, BorderLayout.SOUTH);
            tabs.addTab("Soins & Facture", soinsPanel);

            // Mettre a jour le total quand on change d'onglet
            tabs.addChangeListener(e -> majTotal(pt, lblTotal));
        }

        // --- Boutons bas ---
        JButton ok = new JButton("Enregistrer"); JButton cancel = new JButton("Annuler");
        ok.addActionListener(e -> { confirme = true; dispose(); });
        cancel.addActionListener(e -> dispose());
        JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT)); b.add(cancel); b.add(ok);

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
        add(b, BorderLayout.SOUTH);
    }

    private void chargerSoins(Patient pt) {
        soinsModel.setRowCount(0);
        pt.getSoins().forEach(s -> soinsModel.addRow(new Object[]{
            s.getTypeSoin(), s.getDate(), s.getDescription(),
            String.format("%.2f", s.getCout())
        }));
    }

    private void majTotal(Patient pt, JLabel lbl) {
        lbl.setText("Total facture : " + String.format("%.2f", pt.calculerFacture()) + " EUR   ");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
    }

    public boolean isConfirme()  { return confirme; }
    public String getNom()       { return tfNom.getText().trim(); }
    public String getPrenom()    { return tfPrenom.getText().trim(); }
    public String getTelephone() { return tfTel.getText().trim(); }
}