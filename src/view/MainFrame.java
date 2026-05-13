package view;
import controller.HopitalController;
import exception.*;
import model.*;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class MainFrame extends JFrame {
    private final HopitalController controller = new HopitalController();
    private HopitalTableModel tableModel;
    private JTable table;
    private JComboBox<String> cbRole;
    private JTextField tfNom, tfDate;

    public MainFrame() {
        super("CareHub - Gestionnaire Hopital");
        buildUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1150, 620);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUI() {
        tableModel = new HopitalTableModel(controller.getTous());
        table = new JTable(tableModel);
        table.setRowSorter(new TableRowSorter<>(tableModel));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        table.getTableHeader().setReorderingAllowed(false);

        JButton btnA    = new JButton("Ajouter");
        JButton btnM    = new JButton("Modifier");
        JButton btnS    = new JButton("Supprimer");
        JButton btnSoin = new JButton("+ Soin");
        JButton btnSort = new JButton("Sortie");
        JButton btnSo   = new JButton("Voir soins");
        JButton btnSt   = new JButton("Statistiques");
        JButton btnU    = new JButton("Urgences");
        JButton btnSl   = new JButton("Salles");
        JButton btnTN   = new JButton("Trier nom");
        JButton btnTR   = new JButton("Trier role");

        btnSoin.setBackground(new Color(60,179,113)); btnSoin.setForeground(Color.WHITE);
        btnSort.setBackground(new Color(210,105,30)); btnSort.setForeground(Color.WHITE);
        btnU.setBackground(new Color(220,50,50));     btnU.setForeground(Color.WHITE);

        btnA.addActionListener(e    -> ajouterPersonne());
        btnM.addActionListener(e    -> modifierPersonne());
        btnS.addActionListener(e    -> supprimerPersonne());
        btnSoin.addActionListener(e -> ajouterSoin());
        btnSort.addActionListener(e -> sortiePatient());
        btnSt.addActionListener(e   -> new StatistiquesPanel(this, controller).setVisible(true));
        btnSo.addActionListener(e   -> voirSoins());
        btnTN.addActionListener(e   -> tableModel.setEntites(controller.trierParNom()));
        btnTR.addActionListener(e   -> tableModel.setEntites(controller.trierParRole()));
        btnU.addActionListener(e    -> new UrgencesPanel(this, controller).setVisible(true));
        btnSl.addActionListener(e   -> new SallesPanel(this, controller).setVisible(true));

        cbRole = new JComboBox<>(new String[]{"Tous","Patient","Medecin","Infirmier","Urgence"});
        tfNom  = new JTextField(10);
        tfDate = new JTextField(8);
        tfDate.setToolTipText("Date ex: 2024-01");
        JButton btnF = new JButton("Filtrer");
        JButton btnR = new JButton("Reset");
        btnF.addActionListener(e -> appliquerFiltre());
        btnR.addActionListener(e -> {
            cbRole.setSelectedIndex(0); tfNom.setText(""); tfDate.setText(""); rafraichir();
        });

        JPanel bar1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        bar1.add(btnA); bar1.add(btnM); bar1.add(btnS);
        bar1.add(new JSeparator(SwingConstants.VERTICAL));
        bar1.add(btnSoin); bar1.add(btnSort); bar1.add(btnSo);
        bar1.add(new JSeparator(SwingConstants.VERTICAL));
        bar1.add(btnSt); bar1.add(btnU); bar1.add(btnSl);
        bar1.add(new JSeparator(SwingConstants.VERTICAL));
        bar1.add(btnTN); bar1.add(btnTR);

        JPanel bar2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        bar2.add(new JLabel("Role :")); bar2.add(cbRole);
        bar2.add(new JLabel("Nom :"));  bar2.add(tfNom);
        bar2.add(new JLabel("Date :")); bar2.add(tfDate);
        bar2.add(btnF); bar2.add(btnR);

        JPanel nord = new JPanel(new BorderLayout());
        nord.add(bar1, BorderLayout.NORTH);
        nord.add(bar2, BorderLayout.SOUTH);

        JLabel statut = new JLabel("  CareHub - Sauvegarde automatique dans resources/hopital.csv");
        statut.setBorder(BorderFactory.createEtchedBorder());

        setLayout(new BorderLayout());
        add(nord, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(statut, BorderLayout.SOUTH);
    }

    private void appliquerFiltre() {
        String role = "Tous".equals(cbRole.getSelectedItem()) ? "" : (String) cbRole.getSelectedItem();
        tableModel.setEntites(controller.filtrer(role, tfNom.getText().trim(), tfDate.getText().trim()));
    }
    private void rafraichir() { tableModel.setEntites(controller.getTous()); }

    private void ajouterPersonne() {
        AjoutPersonneDialog d = new AjoutPersonneDialog(this);
        d.setVisible(true);
        Personne p = d.getResultat();
        if (p == null) return;
        try {
            controller.ajouter(p); rafraichir();
            JOptionPane.showMessageDialog(this, p.getNomComplet() + " ajoute avec succes.");
        } catch (CapaciteDepasseeException | PatientDejaAdmisException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur metier", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierPersonne() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selectionnez une personne."); return; }
        Personne p = tableModel.getPersonneAt(table.convertRowIndexToModel(row));
        ModifierPersonneDialog d = new ModifierPersonneDialog(this, p);
        d.setVisible(true);
        if (d.isConfirme()) {
            controller.modifier(p, d.getNom(), d.getPrenom(), d.getTelephone());
            rafraichir();
        }
    }

    private void supprimerPersonne() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selectionnez une personne."); return; }
        Personne p = tableModel.getPersonneAt(table.convertRowIndexToModel(row));
        if (JOptionPane.showConfirmDialog(this, "Supprimer " + p.getNomComplet() + " ?",
                "Confirmer", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.supprimer(p); rafraichir();
        }
    }

    private void ajouterSoin() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selectionnez un patient dans la liste."); return; }
        Personne p = tableModel.getPersonneAt(table.convertRowIndexToModel(row));
        if (!(p instanceof Patient pt)) {
            JOptionPane.showMessageDialog(this, "Selectionnez un patient.", "Info", JOptionPane.WARNING_MESSAGE); return;
        }
        AjoutSoinDialog d = new AjoutSoinDialog(this);
        d.setVisible(true);
        Soin s = d.getResultat();
        if (s != null) {
            pt.ajouterSoin(s);
            controller.sauvegarder();
            rafraichir();
            JOptionPane.showMessageDialog(this,
                "Soin ajoute.\nTotal facture : " + String.format("%.2f", pt.calculerFacture()) + " EUR");
        }
    }

    private void sortiePatient() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selectionnez un patient dans la liste."); return; }
        Personne p = tableModel.getPersonneAt(table.convertRowIndexToModel(row));
        if (!(p instanceof Patient pt)) {
            JOptionPane.showMessageDialog(this, "Selectionnez un patient.", "Info", JOptionPane.WARNING_MESSAGE); return;
        }
        SortiePatientDialog d = new SortiePatientDialog(this, pt);
        d.setVisible(true);
        if (d.isConfirme() && !d.getDate().isEmpty()) {
            pt.sortir(d.getDate());
            controller.sauvegarder();
            rafraichir();
            JOptionPane.showMessageDialog(this, pt.getNomComplet() + " sorti le " + d.getDate());
        }
    }

    private void voirSoins() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selectionnez un patient."); return; }
        Personne p = tableModel.getPersonneAt(table.convertRowIndexToModel(row));
        if (!(p instanceof Patient pt)) {
            JOptionPane.showMessageDialog(this, "Selectionnez un patient.", "Info", JOptionPane.WARNING_MESSAGE); return;
        }
        if (pt.getSoins().isEmpty()) { JOptionPane.showMessageDialog(this, "Aucun soin enregistre."); return; }
        StringBuilder sb = new StringBuilder("Soins de " + pt.getNomComplet() + ":\n\n");
        pt.getSoins().forEach(s -> sb.append(
            String.format("- [%s] %s | %s | %.2f EUR\n", s.getTypeSoin(), s.getDate(), s.getDescription(), s.getCout())
        ));
        sb.append(String.format("\nTotal : %.2f EUR", pt.calculerFacture()));
        JOptionPane.showMessageDialog(this, sb.toString(), "Dossier medical", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(MainFrame::new); }
}