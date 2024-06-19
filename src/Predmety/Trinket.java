package Predmety;

import Lokality.Obchod;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.Text;

import javax.swing.*;

public class Trinket extends Predmet {
    public Trinket(String nazov, int hodnota, int cena) {
        super(nazov, hodnota, cena);
        super.obrazokIkony = "Obrazky/ikonaTrinket.png";
        super.obrazokCesta = "Obrazky/ZelenyTrinket.png";
        super.setCenaVylepsenia(8);
    }
    public int getPopularita() {
        return super.getHodnota();
    }

    @Override
    public void vylepsiPredmet() {
        var cenyVylepseni = new int[]{16, 24, 34, 64, 3425};
        super.setCenaVylepsenia(cenyVylepseni[super.vylepsenia]);
        switch (super.vylepsenia) {
            case 0:
                super.obrazokCesta = "Obrazky/ModryTrinket.png";
                super.obrazokIkony = "Obrazky/ikonaTrinketM.png";
                break;
            case 1:
                super.obrazokCesta = "Obrazky/OranzovyTrinket.png";
                super.obrazokIkony = "Obrazky/ikonaTrinketO.png";
                break;
            default:
                super.nazov = super.nazov + "+";
                break;
        }
        super.vylepsiPredmet();
    }

    @Override
    public void vylepsiPredmet(int kolko) {
        for (int i = 0; i < kolko; i++) {
            this.vylepsiPredmet();
        }
    }

}
