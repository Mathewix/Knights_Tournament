package Rytieri.SpravyRytierovi;

import HernyBalik.Predavatelne;
import Lokality.Miesto;
import Rytieri.ObycajnyRytier;

public class ZlozTrinket1 extends KontextovaAkcia {
    private final ObycajnyRytier rytier;
    public ZlozTrinket1(ObycajnyRytier rytier) {
        super(rytier, "Obrazky/zlozitTrinket1.png",140, 116);
        this.rytier = rytier;
    }

    @Override
    public void klikolAkciu() {
        this.rytier.zlozPredmet(this.rytier.getTrinket().get());
    }
}
