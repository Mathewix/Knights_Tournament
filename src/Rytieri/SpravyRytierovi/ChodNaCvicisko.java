package Rytieri.SpravyRytierovi;

import Lokality.Cvicisko;
import Lokality.Miesto;
import Rytieri.ObycajnyRytier;
import fri.shapesge.Image;

import javax.swing.*;

public class ChodNaCvicisko extends KontextovaAkcia {
    private final ObycajnyRytier rytier;
    private final Cvicisko[] cvicisko;

    public ChodNaCvicisko(ObycajnyRytier rytier, Cvicisko[] cvicisko) {
        super(rytier,"Obrazky/chodnaCvicisko.png",  140, - 20);
        this.rytier = rytier;
        this.cvicisko = cvicisko;
    }

    @Override
    public void klikolAkciu() {
        for (Cvicisko c : this.cvicisko) {
            if (c.pridajRytiera(this.rytier)) {
                return;
            }
        }
        rytier.getHrac().getManazerEventov().pozastavHru();
        JOptionPane.showMessageDialog(null, "Tu sa uz rytier nachadza", "Chyba", JOptionPane.ERROR_MESSAGE);
    }
}
