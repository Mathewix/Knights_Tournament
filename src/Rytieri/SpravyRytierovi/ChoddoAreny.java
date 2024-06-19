package Rytieri.SpravyRytierovi;

import HernyBalik.Efekt;
import HernyBalik.StavEfektu;
import Lokality.Arena;
import Lokality.Miesto;
import Rytieri.ObycajnyRytier;
import fri.shapesge.Image;

public class ChoddoAreny extends KontextovaAkcia {
    private final ObycajnyRytier rytier;
    private final Arena arena;

    public ChoddoAreny(ObycajnyRytier rytier, Arena arena) {
        super(rytier, "Obrazky/choddoAreny.png", 140, 82);
        this.rytier = rytier;
        this.arena = arena;
    }

    @Override
    public void klikolAkciu() {
        if (this.rytier.getHrac().getMapa().getEfekt() == Efekt.SLACHTA) {
            if (this.rytier.getHrac().getMapa().getStavEfektu() == StavEfektu.BEZ_NEVYHODY) {
                this.arena.pridajRyteraHraca(this.rytier);
            }
        } else {
            this.arena.pridajRyteraHraca(this.rytier);
        }
    }
}
