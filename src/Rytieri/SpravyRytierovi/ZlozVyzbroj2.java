package Rytieri.SpravyRytierovi;

import HernyBalik.Predavatelne;
import Lokality.Miesto;
import Rytieri.LegendarnyRytier;
import Rytieri.ObycajnyRytier;

public class ZlozVyzbroj2 extends KontextovaAkcia {
    private final LegendarnyRytier rytier;
    public ZlozVyzbroj2(LegendarnyRytier rytier) {
        super(rytier, "Obrazky/zlozitVyzbroj2.png",140, 218);
        this.rytier = rytier;
    }

    @Override
    public void klikolAkciu() {
        this.rytier.zlozPredmet(this.rytier.getVyzbroj2().get());
    }
}
