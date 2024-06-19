package HernyBalik.Navod;

import HernyBalik.Mapa;
import HernyBalik.Pohlad;
import fri.shapesge.Image;

public class Prirucka {
    private StavPrirucky stav;
    private StavPrirucky predchadzajuciStav;
    private final Mapa mapa;
    private final Image[] obrazky;
    private final Image prirucka;
    public Prirucka(Mapa mapa) {
        this.mapa = mapa;
        this.stav = StavPrirucky.VYPNUTA;
        this.predchadzajuciStav = StavPrirucky.VYPNUTA;
        this.prirucka = new Image("Obrazky/Prirucka.png", 400, 100);
        this.obrazky = new Image[7];
        this.obrazky[0] = new Image("Obrazky/P1.png", 150, 100);
        this.obrazky[1] = new Image("Obrazky/P2_a.png", 150, 100);
        this.obrazky[2] = new Image("Obrazky/P2_b.png", 150, 100);
        this.obrazky[3] = new Image("Obrazky/P2_c.png", 150, 100);
        this.obrazky[4] = new Image("Obrazky/P3.png", 150, 100);
        this.obrazky[5] = new Image("Obrazky/P4.png", 150, 100);
        this.obrazky[6] = new Image("Obrazky/P5.png", 150, 100);
    }

    public void zobrazPrirucku() {

        this.stav = StavPrirucky.PRIRUCKA;
        this.prirucka.makeVisible();
        if (this.predchadzajuciStav == StavPrirucky.VYPNUTA) {
            this.mapa.setPohlad(Pohlad.PRIRUCKA);
        }
    }
    public void zmenStavPrirucky(StavPrirucky stav) {
        this.predchadzajuciStav = this.stav;
        this.stav = stav;
        if (this.predchadzajuciStav == StavPrirucky.PRIRUCKA) {
            this.prirucka.makeInvisible();
        }
        if (stav == StavPrirucky.OVLADANIE){
            this.obrazky[0].makeVisible();
        } else if (stav == StavPrirucky.ZAKLADY_1) {
            if (this.predchadzajuciStav == StavPrirucky.ZAKLADY_2) {
                this.obrazky[2].makeInvisible();
            }
            this.obrazky[1].makeVisible();
        } else if (stav == StavPrirucky.ZAKLADY_2) {
            if (this.predchadzajuciStav == StavPrirucky.ZAKLADY_1) {
                this.obrazky[1].makeInvisible();
            } else if (this.predchadzajuciStav == StavPrirucky.ZAKLADY_3) {
                this.obrazky[3].makeInvisible();
            }
            this.obrazky[2].makeVisible();
        } else if (stav == StavPrirucky.ZAKLADY_3) {
            if (this.predchadzajuciStav == StavPrirucky.ZAKLADY_2) {
                this.obrazky[2].makeInvisible();
            }
            this.obrazky[3].makeVisible();
        } else if (stav == StavPrirucky.FAZY) {
            this.obrazky[4].makeVisible();
        }  else if (stav == StavPrirucky.SCHOPNOSTI) {
            this.obrazky[5].makeVisible();
        } else if (stav == StavPrirucky.RANKY) {
            this.obrazky[6].makeVisible();
        }
        this.mapa.setPohlad(Pohlad.PRIRUCKA_TEXTY);
    }

    public void klikNaX() {
        if (this.stav == StavPrirucky.PRIRUCKA) {
            this.mapa.setPohlad(Pohlad.MENU);
            this.mapa.getMenuObrazok().makeVisible();
            this.stav = StavPrirucky.VYPNUTA;
            this.predchadzajuciStav = StavPrirucky.VYPNUTA;
            this.prirucka.makeInvisible();
        } else {
            if (this.stav.getPoradieObrazka() >= 0) {
                this.obrazky[this.stav.getPoradieObrazka()].makeInvisible();
            }
            this.zobrazPrirucku();
            this.mapa.setPohlad(Pohlad.PRIRUCKA);
        }
    }

    public void klikNaNasledovne() {
        if (this.stav == StavPrirucky.ZAKLADY_1) {
            this.zmenStavPrirucky(StavPrirucky.ZAKLADY_2);
            return;
        } else if (this.stav == StavPrirucky.ZAKLADY_2) {
            this.zmenStavPrirucky(StavPrirucky.ZAKLADY_3);
        }
    }
    public void klikNaPredchadzajuce() {
        if (this.stav == StavPrirucky.ZAKLADY_2) {
            this.zmenStavPrirucky(StavPrirucky.ZAKLADY_1);
            return;
        } else if (this.stav == StavPrirucky.ZAKLADY_3) {
            this.zmenStavPrirucky(StavPrirucky.ZAKLADY_2);
        }
    }
}
