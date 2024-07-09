package HernyBalik.StraznaVeza;

import HernyBalik.ManazerEventov;
import Hraci.Hrac;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.Text;

public class StraznaVeza {
    private int ziskSkore;
    private final ManazerEventov manazerEventov;
    private final Image zisk;
    private final Image name;
    private final Text ziskT;
    private int[] suradnice;
    private AkciaVeze[] akcie;
    public StraznaVeza(ManazerEventov manazerEventov) {
        this.ziskSkore = 0;
        this.manazerEventov = manazerEventov;
        this.zisk = new Image("Obrazky/vezaCislo.png", 915, 265);
        this.name = new Image("Obrazky/StraznaVeza.png", 880, 77);
        this.ziskT = new Text("" + this.ziskSkore, 960, 300);
        this.ziskT.changeFont("New Rocker", FontStyle.BOLD, 24);
        this.zisk.makeVisible();
        this.ziskT.makeVisible();
        this.name.makeVisible();
        this.suradnice = new int[]{864, 1093, 106, 320};
        this.akcie = new AkciaVeze[3];
        this.akcie[0] = new plusJedna(this);
        this.akcie[1] = new plusTri(this);
        this.akcie[2] = new plusPat(this);
    }

    public void pridajZisk(int kolko) {
        this.ziskSkore += kolko;
        this.ziskT.changeText("" + this.ziskSkore);
    }
    public void akcia() {
        this.manazerEventov.getMapa().getHrac().pridajBonus(this.ziskSkore);
    }


    public boolean klikolNaVezu(int x, int y, boolean jeDefault) {
        if (jeDefault) {
            if (x > this.suradnice[0] && x <= this.suradnice[1]) {
                if (y > this.suradnice[2] && y <= this.suradnice[3]) {
                    for (AkciaVeze akciaVeze : this.akcie) {
                        akciaVeze.zobraz();
                    }
                    return true;
                }
            } else {
                return false;
            }
        } else {
                for (AkciaVeze akciaVeze : this.akcie) {
                    if (x > akciaVeze.getSuradnice()[0] && x <= akciaVeze.getSuradnice()[1]) {
                        if (y > akciaVeze.getSuradnice()[2] && y <= akciaVeze.getSuradnice()[3]) {
                            akciaVeze.klikolAkciu();
                        }
                    }
                }
                for (AkciaVeze akciaVeze : this.akcie) {
                    akciaVeze.skry();
                }
        }
        return false;
    }

    public AkciaVeze[] pouzitelneSpravy() {
        return this.akcie;
    }

    public Hrac getHrac() {
        return this.manazerEventov.getMapa().getHrac();
    }
}
