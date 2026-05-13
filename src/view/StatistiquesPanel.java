package view;
import controller.HopitalController;
import javax.swing.*;
import java.awt.*;
public class StatistiquesPanel extends JDialog {
    public StatistiquesPanel(JFrame parent, HopitalController controller) {
        super(parent, "Statistiques", true);
        JTextArea area=new JTextArea(controller.getStatistiques());
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        area.setEditable(false);
        area.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        JButton close=new JButton("Fermer");
        close.addActionListener(e->dispose());
        JPanel b=new JPanel(new FlowLayout(FlowLayout.RIGHT)); b.add(close);
        setLayout(new BorderLayout());
        add(new JScrollPane(area),BorderLayout.CENTER);
        add(b,BorderLayout.SOUTH);
        setSize(380,280); setLocationRelativeTo(parent);
    }
}