package Rytieri.SpravyRytierovi;

import Lokality.Cvicisko;
import Lokality.Krcma;
import Rytieri.ObycajnyRytier;
import fri.shapesge.Image;

import javax.swing.*;

public class ChodDoKrcmy extends KontextovaAkcia {
    private final ObycajnyRytier rytier;
    private final Krcma[] krcma;

    public ChodDoKrcmy(ObycajnyRytier rytier, Krcma[] krcma) {
        super(rytier,"Obrazky/choddoKrcmy.png", 140, 14);
        this.rytier = rytier;
        this.krcma = krcma;
    }

    @Override
    public void klikolAkciu() {
        for (Krcma k : this.krcma) {
            if (k.pridajRytiera(this.rytier)) {
                return;
            }
        }
        rytier.getHrac().getManazerEventov().pozastavHru();
        JOptionPane.showMessageDialog(null, "Tu sa uz rytier nachadza", "Chyba", JOptionPane.ERROR_MESSAGE);
    }
}

