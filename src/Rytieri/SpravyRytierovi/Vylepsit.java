package Rytieri.SpravyRytierovi;

import Lokality.Doska;
import Predmety.Predmet;
import fri.shapesge.FontStyle;
import fri.shapesge.Text;

public class Vylepsit extends KontextovaAkcia{
    private final Predmet predmet;
    private final Doska doska;
    private final Text text;
    public Vylepsit(Predmet predmet, Doska doska) {
        super(predmet, "Obrazky/Vylepsit.png",70, 24);
        this.predmet = predmet;
        this.doska = doska;
        this.text = new Text("za  " + predmet.getCenaVylepsenia(), predmet.getSuradnice()[0] + 198, predmet.getSuradnice()[2] + 50);
        this.text.changeFont("New Rocker", FontStyle.BOLD, 22);
    }

    @Override
    public void klikolAkciu() {
            this.doska.getHrac().vylepsiPredmet(this.predmet);
    }

    @Override
    public void zobrazSpravu() {
        super.zobrazSpravu();
        if (predmet.getCenaVylepsenia() < 3425) {
            this.text.changeText("za  " + predmet.getCenaVylepsenia());
        } else {
            this.text.changeText("MAX");
        }
        this.text.makeVisible();
    }

    @Override
    public void skrySpravu() {
        super.skrySpravu();
        this.text.makeInvisible();
    }

}
