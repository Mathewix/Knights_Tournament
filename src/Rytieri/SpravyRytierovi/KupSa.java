package Rytieri.SpravyRytierovi;

import HernyBalik.Predavatelne;
import Lokality.Obchod;

public class KupSa extends KontextovaAkcia {
    private final Predavatelne predavatelne;
    private final Obchod obchod;

    public KupSa(Predavatelne predavatelne, Obchod obchod) {
        super(predavatelne,"Obrazky/KupSa.png", 60, -10);
        this.predavatelne = predavatelne;
        this.obchod = obchod;
    }

    @Override
    public void klikolAkciu() {
        this.obchod.predaj(this.obchod.getMapa().getHrac(), this.predavatelne);
    }
}
