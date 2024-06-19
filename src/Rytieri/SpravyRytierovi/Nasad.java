package Rytieri.SpravyRytierovi;

import HernyBalik.Predavatelne;
import Lokality.Doska;
import Predmety.Predmet;

public class Nasad extends KontextovaAkcia {
    private final Predmet predmet;
    private final Doska doska;
    public Nasad(Predmet predmet, Doska doska) {
        super(predmet, "Obrazky/nasadit.png",70, -10);
        this.predmet = predmet;
        this.doska = doska;
    }

    @Override
    public void klikolAkciu() {
        this.doska.nastavujePredmet(this.predmet);
    }
}
