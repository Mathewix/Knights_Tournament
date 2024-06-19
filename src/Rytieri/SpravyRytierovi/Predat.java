package Rytieri.SpravyRytierovi;

import HernyBalik.Efekt;
import HernyBalik.Predavatelne;
import HernyBalik.StavEfektu;
import Lokality.Doska;
import Predmety.Predmet;
import Rytieri.ObycajnyRytier;
import fri.shapesge.FontStyle;
import fri.shapesge.Text;

public class Predat extends KontextovaAkcia{
    private final Predavatelne predavatelne;
    private final Doska doska;
    private final Text text;
    public Predat(Predavatelne predavatelne, Doska doska, int x, int y) {
        super(predavatelne,"Obrazky/Predat.png" , x, y);
        this.predavatelne = predavatelne;
        this.doska = doska;

        if (this.predavatelne instanceof ObycajnyRytier) {
            this.text = new Text("za  " + this.predavatelne.getCena(), this.predavatelne.getSuradnice()[0] + 128,
                    this.predavatelne.getSuradnice()[2] + 299);
        } else {
            this.text = new Text("za  " + this.predavatelne.getCena(), this.predavatelne.getSuradnice()[0] + 180,
                    this.predavatelne.getSuradnice()[2] + 85);
        }
        this.text.changeFont("New Rocker", FontStyle.BOLD, 22);
    }

    @Override
    public void klikolAkciu() {
        if (this.predavatelne instanceof Predmet predmet) {
            var mapa = this.doska.getHrac().getMapa();
            if (mapa.getEfekt() == Efekt.ZBERATEL && mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                this.doska.getHrac().getManazerEventov().odstranAkciuPredmetu(predmet);
            }
            this.doska.getHrac().odstranPredmet(predmet);
            predmet.skryKartu();
            if (mapa.getEfekt() != Efekt.OBCHODNIK) {
                this.doska.getHrac().zmenStavPenazi(predmet.getCena() / 2);
            } else {
                this.doska.getHrac().zmenStavPenazi(predmet.getCena());
                if (mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                    this.doska.getHrac().pridajBonus(predmet.getCena() / 2);
                }
            }
        } else if (this.predavatelne instanceof ObycajnyRytier rytier){
            this.doska.getHrac().predajRytiera(rytier);
        }
    }

    @Override
    public void zobrazSpravu() {
        super.zobrazSpravu();
        if (this.doska.getHrac().getMapa().getEfekt() != Efekt.OBCHODNIK) {
            this.text.changeText("za  " + (this.predavatelne.getCena() / 2));
        } else {
            this.text.changeText("za  " + this.predavatelne.getCena());
        }
        this.text.makeVisible();
    }

    @Override
    public void skrySpravu() {
        super.skrySpravu();
        this.text.makeInvisible();
    }
}
