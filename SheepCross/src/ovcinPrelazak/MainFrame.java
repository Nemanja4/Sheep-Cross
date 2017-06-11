package ovcinPrelazak;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;

public final class MainFrame extends javax.swing.JFrame {

    private static int visina = 600;
    private static int sirina = 800;
    private JMenuBar meniTabla = new JMenuBar();
    private MainPanel panel = new MainPanel();


    public static int getVisina() {
        return visina;
    }

    public static int getSirina() {
        return sirina;
    }

    public MainFrame() {
        setSize(sirina, visina);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setJMenuBar(setMB());

        panel.addKeyListener(panel);
        add(panel);
        panel.grabFocus();
        validate();
        repaint();
        panel.novaIgra();
    }

    public JMenuBar setMB() {

        javax.swing.JMenu novaIgra = new javax.swing.JMenu("Nova igra F2");;
        javax.swing.JMenu tabela = new javax.swing.JMenu("tabela");;
        javax.swing.JMenu izlaz = new javax.swing.JMenu("Izlaz");

        novaIgra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.novaIgra();
            }
        });

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new skor(panel);
            }
        });

        izlaz.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int answer;
                panel.tajmer.stop();
                answer = javax.swing.JOptionPane.showConfirmDialog(null,
                        "Da li ste sigurni da zelite da izadjete?",
                        "Pitanje?", javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                if (answer == javax.swing.JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    panel.tajmer.start();
                }
            }
        });

        meniTabla.add(novaIgra);
        meniTabla.add(tabela);
        meniTabla.add(izlaz);

        return meniTabla;
    }
}
