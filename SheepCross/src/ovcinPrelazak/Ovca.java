package ovcinPrelazak;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ovca {

    private static final int visinaOvce = 50;
    private static final int sirinaOvce = 50;
    private int xOvce;
    private int yOvce;
    static BufferedImage slikaOvce = null;
    static boolean ucitanaSlika = false;

    public static int getVisinaOvce() {
        return visinaOvce;
    }

    public static int getSirinaOvce() {
        return sirinaOvce;
    }

    public int getxOvce() {
        return xOvce;
    }

    public int getyOvce() {
        return yOvce;
    }

    public void setxOvce(int xOvce) {
        this.xOvce = xOvce;
    }

    public void setyOvce(int yOvce) {
        this.yOvce = yOvce;
    }


    public Ovca(){
        this.xOvce = MainPanel.getSirina()/2;
        this.yOvce = 500;
    }
    public Ovca(int xOvce, int yOvce) {
        this.xOvce = xOvce;
        this.yOvce = 500;
    }

    public void pocetnaPozicija()
    {
        this.xOvce = MainPanel.getSirina()/2;
        this.yOvce = 500;
    }
    public static void loadImages() {
        try {
            slikaOvce = ImageIO.read(new File("src/images/ovca.jpg"));
            ucitanaSlika = true;
        } catch (IOException e) {
            ucitanaSlika = false;
        }
    }
    public Rectangle okvirOvce()
    {
        return new Rectangle(xOvce, yOvce, sirinaOvce, visinaOvce);
    }

    void crtajOvcu(Graphics g, MainPanel panel) {
     g.setColor(Color.red);
        if(ucitanaSlika)
            g.drawImage(slikaOvce, xOvce, yOvce, sirinaOvce, visinaOvce, panel);
        else
            g.fillRect(xOvce, yOvce, sirinaOvce, visinaOvce);
    g.setColor(Color.black);
    }

    void pomjeriGore() {
        if (yOvce - visinaOvce > 3*visinaOvce) {
            yOvce -= visinaOvce;
        } else {
            yOvce = 3*visinaOvce;
        }
    }

    void pomjeriDole() {
        if (MainPanel.getVisina() - (yOvce + visinaOvce) > 0) {
            yOvce += visinaOvce;
        } else {
            yOvce = 500;
        }
    }

    void pomjeriLijevo() {
        if (xOvce - sirinaOvce > 0) {
            xOvce -= sirinaOvce;
        } else {
            xOvce = 0;
        }
    }

    void pomjeriDesno() {
        if (MainPanel.getSirina() - (xOvce + sirinaOvce) > 0) {
            xOvce += sirinaOvce;
        } else {
            xOvce = MainPanel.getSirina();
        }
    }
}
