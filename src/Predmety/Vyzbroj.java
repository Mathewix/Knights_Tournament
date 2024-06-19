package Predmety;

import Lokality.Obchod;

public class Vyzbroj extends Predmet{

    public Vyzbroj(String nazov, int hodnota, int cena) {
        super(nazov, hodnota, cena);
        super.setCenaVylepsenia(7);
        super.obrazokIkony = "Obrazky/ikonaVyzbroj.png";
        super.obrazokCesta = "Obrazky/ZelenaVyzbroj.png";
    }

    public int getSila() {
        return super.getHodnota();
    }

    @Override
    public void vylepsiPredmet() {
        var cenyVylepseni = new int[]{15, 23, 36, 64, 3425};
        super.setCenaVylepsenia(cenyVylepseni[super.vylepsenia]);
        switch (super.vylepsenia) {
            case 0:
                super.obrazokCesta = "Obrazky/ModraVyzbroj.png";
                super.obrazokIkony = "Obrazky/ikonaVyzbrojM.png";
                break;
            case 1:
                super.obrazokCesta = "Obrazky/OranzovaVyzbroj.png";
                super.obrazokIkony = "Obrazky/ikonaVyzbrojO.png";
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
