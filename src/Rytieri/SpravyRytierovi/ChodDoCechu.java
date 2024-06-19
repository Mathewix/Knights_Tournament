package Rytieri.SpravyRytierovi;

import Hraci.Hrac;
import Lokality.Cech;
import Lokality.Miesto;
import Rytieri.ObycajnyRytier;
import fri.shapesge.Image;

public class ChodDoCechu extends KontextovaAkcia {

    private final ObycajnyRytier rytier;
    private final Cech cech;

    public ChodDoCechu(ObycajnyRytier rytier, Cech cech) {
        super(rytier, "Obrazky/choddoCechu.png", 140, 48);
        this.rytier = rytier;
        this.cech = cech;

    }

    @Override
    public void klikolAkciu() {
        if (!this.rytier.getBolVCechu()) {
            this.cech.pridajRytiera(this.rytier);
        } else {
            System.out.println("NO MORE BRO");
        }
    }
}
