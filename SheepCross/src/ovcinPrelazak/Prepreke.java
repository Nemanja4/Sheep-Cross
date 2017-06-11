package ovcinPrelazak;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Prepreke {
     private static final int visinaPrepreke = 50;
    private static final int sirinaPrepreke = 100;
    private int xPrepreke;
    private int yPrepreke;
    public int stranaPrepreke;
    static BufferedImage slikaPrepreke = null;
    static boolean ucitanaSlika = false;
    int brzina;

    public static int getVisinaPrepreke() { return visinaPrepreke; }
    public static int getSirinaPrepreke() { return sirinaPrepreke; }

    public int getxPrepreke() { return xPrepreke; }
    public int getyPrepreke() { return yPrepreke; }

    public void setxPrepreke(int xPrepreke) { this.xPrepreke = xPrepreke; }
    public void setyPrepreke(int yPrepreke) { this.yPrepreke = yPrepreke; }

    public Prepreke(int xOvce, int yOvce) {
        this.xPrepreke = xOvce;
        this.yPrepreke = yOvce;
        stranaPrepreke = 1;
        brzina = 1;
    }

    public Prepreke(int xOvce, int yOvce, int tempStrana, int brzina) {
        this.xPrepreke = xOvce;
        this.yPrepreke = yOvce;
        stranaPrepreke = tempStrana;
        this.brzina = brzina;
    }

    public void pomjeriPrepreku(){
        xPrepreke -= 5*stranaPrepreke*brzina;
    }

    public Rectangle okvirPrepreke(){
        return new Rectangle(xPrepreke, yPrepreke, sirinaPrepreke, visinaPrepreke);
    }

    void crtajPrepreku(Graphics g, MainPanel panel) {
        if(ucitanaSlika)
            g.drawImage(slikaPrepreke, xPrepreke, yPrepreke, sirinaPrepreke, visinaPrepreke, panel);
        else
            g.fillRect(xPrepreke, yPrepreke, sirinaPrepreke, visinaPrepreke);
    }

    void pomjeriPreprekuDole() {
        yPrepreke += Ovca.getVisinaOvce();
    }

    public static void loadImages() {
        try {
            slikaPrepreke = ImageIO.read(new File("src/images/voda_velika.jpg"));
            ucitanaSlika = true;
        } catch (IOException e) {
            ucitanaSlika = false;
        }
    }
}
