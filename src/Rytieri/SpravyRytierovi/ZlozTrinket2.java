package Rytieri.SpravyRytierovi;

import HernyBalik.Predavatelne;
import Lokality.Miesto;
import Rytieri.PokrocilyRytier;

public class ZlozTrinket2 extends KontextovaAkcia{
    private final PokrocilyRytier rytier;
    public ZlozTrinket2(PokrocilyRytier rytier) {
        super(rytier, "Obrazky/zlozitTrinket2.png",140, 184);
        this.rytier = rytier;
    }

    @Override
    public void klikolAkciu() {
        this.rytier.zlozPredmet(this.rytier.getTrinket2().get());
    }
}
