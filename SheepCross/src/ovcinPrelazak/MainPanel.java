package ovcinPrelazak;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public final class MainPanel extends javax.swing.JPanel implements ActionListener, KeyListener {

    private static final int visina = MainFrame.getVisina() - 60;
    private static int sirina = MainFrame.getSirina() - 40;

    static BufferedImage slikaPozadine = null;
    static boolean ucitanaSlika = false;
    private Ovca ovca;
    private ArrayList<Prepreke> listaPrepreka;
    int moguciRedPrepreke = (int) ((visina-80)/Prepreke.getVisinaPrepreke());
    int pomjeraja = 0;

    private int vremenskiRazmakIzmedjuPrepreka = 5;
    private int brojacFrejmova = 0;
    Timer tajmer = new Timer(100, this);

    public static int getVisina() {
        return visina;
    }

    public static int getSirina() {
        return sirina;
    }

    public MainPanel() {

        setPreferredSize(new Dimension(sirina, visina));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        setVisible(true);
        requestFocusInWindow();
        loadImages();

        listaPrepreka = new ArrayList<>();
        ovca = new Ovca();

        System.out.println("panel gotov!");
    }
    void novaIgra()
    {
        listaPrepreka.clear();
        ovca.pocetnaPozicija();
        for(int i = 0; i < 10; i++)
            dodajPrepreku();
        pomjeraja = 0;

        tajmer.start();
    }
    private boolean provjeriSudare() {
        if (ovca.getyOvce() >= 0) {
            for (Prepreke prepreka : listaPrepreka) {
                if (prepreka.okvirPrepreke().intersects(ovca.okvirOvce())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void dodajPrepreku() {
        Random random = new Random();
        int prolaza = 0;
        int strana = random.nextInt(2);
        int mjestoPrepreke = random.nextInt(moguciRedPrepreke)*Prepreke.getVisinaPrepreke();
        int brzina = random.nextInt(4) + 1;
        boolean vecPostoji = false;
        do{
            prolaza++;
            if(prolaza == 40)
            {
                prolaza = 0;
                strana = 1 - strana;
            }
            mjestoPrepreke = random.nextInt(moguciRedPrepreke)*Prepreke.getVisinaPrepreke();
            for(int i = 0; i < listaPrepreka.size(); i++)
                if(listaPrepreka.get(i).getyPrepreke() == mjestoPrepreke)
                {
                    if(listaPrepreka.get(i).stranaPrepreke == strana)
                        vecPostoji = false;
                    else
                        vecPostoji = true;
                    break;
                }
                else
                    vecPostoji = false;
        }while (vecPostoji);
        Prepreke novaPrepreka = new Prepreke(strana*(sirina - Prepreke.getSirinaPrepreke())
                + (strana*2 - 1)*(Prepreke.getSirinaPrepreke() - 15),
                mjestoPrepreke, strana*2 - 1, brzina);
        listaPrepreka.add(novaPrepreka);

    }

    public static void loadImages() {
            Ovca.loadImages();
            Prepreke.loadImages();
        try {
            slikaPozadine = ImageIO.read(new File("src/images/voda_velika.jpg"));
            ucitanaSlika = true;
        } catch (IOException e) {
            ucitanaSlika = false;
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(ucitanaSlika)
            g.drawImage(slikaPozadine, 0, 0, MainFrame.getSirina(), MainFrame.getVisina(), this);
        for (Prepreke prepreka : listaPrepreka) {
            prepreka.crtajPrepreku(g, this);
        }
        ovca.crtajOvcu(g, this);
        drawScore(g);

    }
    private void drawScore(Graphics g2d) {
        Font mainFont = new Font("Arial", Font.BOLD, 20);
        g2d.setFont(mainFont);

        String message = "SCORE: " + pomjeraja;

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);
        g2d.setColor(Color.gray);
        g2d.drawString(message, 0, 20);
        g2d.setColor(Color.black);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        brojacFrejmova++;
        if (provjeriSudare()) {
            tajmer.stop();
            brojacFrejmova = 0;
            String odgovor;
            do{
                odgovor = JOptionPane.showInputDialog(this, "igra gotova. Osvojili ste " + pomjeraja + " poena");
            if(odgovor == null)
                break;

            else if(odgovor.isEmpty())
                JOptionPane.showMessageDialog(this, "Molim vas unesite ime");
            else
            {
                new skor(this);
            }
            }while(odgovor.isEmpty() || odgovor == null);
        }
        else
        {
            for (Prepreke prepreka : listaPrepreka) {
                prepreka.pomjeriPrepreku();
            }
            for (int i = 0; i < listaPrepreka.size(); i++) {
                if (listaPrepreka.get(i).getxPrepreke() > getSirina() + 50
                    || listaPrepreka.get(i).getxPrepreke() + Prepreke.getSirinaPrepreke() < 0) {
                    listaPrepreka.remove(i);
                }
            }
            if (brojacFrejmova % vremenskiRazmakIzmedjuPrepreka == 0) {
                dodajPrepreku();
            }
            if(ovca.getyOvce() <= Ovca.getVisinaOvce()*3 + 1)
            {
                ovca.pomjeriDole();
                for (Prepreke prepreka : listaPrepreka) {
                    prepreka.pomjeriPreprekuDole();
                }
                moguciRedPrepreke = 5;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    boolean pressed;
    @Override
    public void keyPressed(KeyEvent e) {
        if(!pressed)
        {
            pressed = true;
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                ovca.pomjeriGore();
                pomjeraja++;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                ovca.pomjeriDesno();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                ovca.pomjeriDole();
                pomjeraja--;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                ovca.pomjeriLijevo();
            }
            else if(e.getKeyCode() == KeyEvent.VK_F2)
            {
                novaIgra();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed = false;
    }
}
