package Rytieri.SpravyRytierovi;

import Lokality.Arena;
import Lokality.StavAreny;
import Rytieri.ObycajnyRytier;

public class PrerusAktivitu extends KontextovaAkcia {
    private ObycajnyRytier rytier;

    public PrerusAktivitu(ObycajnyRytier rytier) {
        super(rytier, "Obrazky/PrerusAktivitu.png", 140, 82);//ine
        this.rytier = rytier;
    }

    @Override
    public void klikolAkciu() {
        if (!(this.rytier.getUmiestnenie() instanceof Arena arena && arena.getStav() == StavAreny.VYHODNOTENIE)) {
            this.rytier.getUmiestnenie().vratRytierov(false, this.rytier);
        }
    }
}
