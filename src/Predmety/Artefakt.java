package Predmety;

import HernyBalik.MaAktivitu;
import Lokality.Doska;
import Lokality.Obchod;
import Rytieri.SpravyRytierovi.KontextovaAkcia;

import java.util.ArrayList;

public class Artefakt extends Predmet {
    public Artefakt(int hodnota) {
        super("", hodnota, 100);
        super.obrazokCesta = "Obrazky/artefakt.png";
    }

    @Override
    public void vykonajAktivitu() {
        super.vykonajAktivitu();
        if (this.getUmiestnenie() instanceof Doska doska) {
            this.hodnota += doska.getHrac().getPocetArtefaktov();
            this.skryKartu();
            this.zobrazKartu();
        }
    }



    @Override
    public ArrayList<KontextovaAkcia> pouzitelneSpravy() {
        ArrayList<KontextovaAkcia> pouzitelne = new ArrayList<>();
        pouzitelne.add(super.pouzitelneSpravy().get(super.pouzitelneSpravy().size() - 1));
        return pouzitelne;
    }
}
