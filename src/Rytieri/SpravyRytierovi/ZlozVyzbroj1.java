package Rytieri.SpravyRytierovi;

import HernyBalik.Predavatelne;
import Lokality.Miesto;
import Rytieri.ObycajnyRytier;

public class ZlozVyzbroj1 extends KontextovaAkcia{
    private final ObycajnyRytier rytier;
    public ZlozVyzbroj1(ObycajnyRytier rytier) {
        super(rytier, "Obrazky/zlozitVyzbroj1.png",140, 150);
        this.rytier = rytier;
    }

    @Override
    public void klikolAkciu() {
        this.rytier.zlozPredmet(this.rytier.getVyzbroj().get());
    }
}
