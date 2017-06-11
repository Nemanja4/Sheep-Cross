
package ovcinPrelazak;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class skor extends JFrame{
    private static JLabel[] imeIgraca = new JLabel[11];
    private static JLabel[] pozicija = new JLabel[11];
    private static JLabel[] listaOsvojenihPoena = new JLabel[11];

    private static ArrayList<String> scoreTracker = new ArrayList<>();

    public skor(String ime, int osvojenihPoena, Component panel) throws HeadlessException {
        postavi(panel);
        setScore(ime, osvojenihPoena);
    }
    public skor(Component panel)
    {
        postavi(panel);
    }
    public void postavi(Component panel)
    {
        this.setTitle("Rang lista");
        this.setSize(350, 350);
        this.setVisible(true);
        setLocationRelativeTo(panel);
        for (int i = 0; i < 11; i++) {
            imeIgraca[i] = new JLabel("");
            listaOsvojenihPoena[i] = new JLabel("");
            pozicija[i] = new JLabel(""+(i+1));
        }

        readTextFileLineByLine();
        String[] spliter = new String[2];
        for (int i = 0; i < 11; i++) {
            pozicija[i].setBounds(0, i * 20, 20, 20);
            imeIgraca[i].setBounds(40, i * 20, 220, 20);
               listaOsvojenihPoena[i].setBounds(240, i * 20, 300, 20);
            pozicija[i].setHorizontalAlignment(JLabel.CENTER);
            imeIgraca[i].setHorizontalAlignment(JLabel.LEFT);
            add(pozicija[i]);
            add(imeIgraca[i]);
            add(listaOsvojenihPoena[i]);
        }

        for (int i = 0; i < scoreTracker.size(); i++) {
            spliter = scoreTracker.get(i).split("-");
            imeIgraca[i].setText(spliter[0]);
            listaOsvojenihPoena[i].setText(spliter[1]);
        }

        listaOsvojenihPoena[10].setVisible(false);
        imeIgraca[10].setVisible(false);
        pozicija[10].setVisible(false);
    }
    public void setScore(String ime, final int score) {
        boolean moved = false;
        for(int i = 0; i < scoreTracker.size(); i++) {
            if (score > Integer.parseInt(listaOsvojenihPoena[i].getText())) {
                if(scoreTracker.size() < 10)
                    scoreTracker.add(scoreTracker.get(scoreTracker.size()-1));
                for (int k = scoreTracker.size()-1; k > i; k--) {
                    imeIgraca[k].setText(imeIgraca[k-1].getText());
                    listaOsvojenihPoena[k].setText(listaOsvojenihPoena[k-1].getText());
                    scoreTracker.set(k, scoreTracker.get(k-1));
                    moved = true;
                }
                listaOsvojenihPoena[i].setText("" + score);
                listaOsvojenihPoena[i].setVisible(true);

                String nameToPut = ime + "-" + score;
                scoreTracker.set(i, nameToPut);
                    break;
            }
        }
        if(!moved)
        {
            if(scoreTracker.size() < 10)
                scoreTracker.add(ime + "-" + score);
        }
        String[] spliter = new String[2];
        for (int i = 0; i < scoreTracker.size(); i++) {
            spliter = scoreTracker.get(i).split("-");
            imeIgraca[i].setText(spliter[0]);
            listaOsvojenihPoena[i].setText(spliter[1]);
        }
        writeTextFileLineByLine();
    };
    public static void readTextFileLineByLine() {
        FileReader in = null;

        BufferedReader bin = null;

        File file = new File(".\\files\\rezultati.txt");
        scoreTracker.clear();
        try {
            in = new FileReader(file);
            bin = new BufferedReader(in);
            String data;
            int count = 0;
                while ((data = bin.readLine()) != null) {
                    scoreTracker.add(data);
                    count++;
                }
            if(count == 0)
                System.out.println("error");
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
        }
    }
    public static void writeTextFileLineByLine() {
        FileWriter out = null;

        try {
            out = new FileWriter(".\\files\\rezultati.txt");
            for (int i = 0; i < scoreTracker.size(); i++) {
                for (int j = 0; j < scoreTracker.get(i).length(); j++) {
                    out.write(scoreTracker.get(i).charAt(j));
                }
                out.write("\n");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }

        }
    }
}
