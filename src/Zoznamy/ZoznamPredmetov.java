package Zoznamy;

import Predmety.Predmet;
import Predmety.Trinket;
import Predmety.Vyzbroj;

import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda slúži ako zoznam možných predmetov v hre, kt. môže hráč získať v Cechu alebo kúpiť v Obchode,
 * zoznam slúži aj na náhodný výber z týchto predmetov
 */
public class ZoznamPredmetov {
    private final ArrayList<Predmet> predmety;

    public ZoznamPredmetov() {
        Random r = new Random();
        this.predmety = new ArrayList<>();
        this.predmety.add(new Trinket("Necklace", 2, r.nextInt(11,12)));
        this.predmety.add(new Trinket("Beads", 2, r.nextInt(9,14)));
        this.predmety.add(new Vyzbroj("Gloves", 2, r.nextInt(10,13)));
        this.predmety.add(new Trinket("Earring", 3, r.nextInt(17,20)));
        this.predmety.add(new Vyzbroj("Shield", 3, r.nextInt(16,22)));
        this.predmety.add(new Vyzbroj("Armor", 4, r.nextInt(23,26)));
        this.predmety.add(new Trinket("Ring", 4, r.nextInt(25,30)));
        this.predmety.add(new Vyzbroj("Helmet", 4, r.nextInt(21,27)));
        this.predmety.add(new Vyzbroj("Sword", 5, r.nextInt(30,40)));

    }

    public Predmet dajNahodnuOdmenu(int fazaHry) {
        Random r = new Random();
        var pravdepodobnost = r.nextDouble();
        if (pravdepodobnost < (0.8 - (fazaHry * 0.25))) {
            Predmet predmet;
            if (r.nextDouble() < 0.64) {
                predmet = predmety.get(r.nextInt(0, 2));
            } else {
                predmet = predmety.get(2);
            }
            predmety.remove(predmet);
            return predmet;
        } else if (pravdepodobnost < (1.1 - (fazaHry * 0.25))) {
            var predmet = predmety.get(3);
            predmety.remove(predmet);
            return predmet;
        } else if (pravdepodobnost < (1.2 - (fazaHry * 0.2))){
            var predmet = predmety.get(4);
            predmety.remove(predmet);
            return predmet;
        } else if (pravdepodobnost < (1 - ((0.05 * (fazaHry - 1) * fazaHry) / 2))) {
            var predmet = predmety.get(r.nextInt(5, 7));
            predmety.remove(predmet);
            return predmet;
        } else {
            var predmet = predmety.get(7);
            predmety.remove(predmet);
            return predmet;
        }
    }
    public Predmet dajNahodnuOdmenu() {
        Random r = new Random();

        var predmet = predmety.get(r.nextInt(0,this.predmety.size()));
        predmety.remove(predmet);
        return predmet;
    }


    /**
     * Pridá do zoznamu ďalšie predmety rovnakého typu aby nedošli
     */
    public void dopln() {
        this.predmety.removeAll(this.predmety);
        Random r = new Random();
        this.predmety.add(new Trinket("Necklace", 2, r.nextInt(8,10)));
        this.predmety.add(new Trinket("Beads", 2, r.nextInt(7,12)));
        this.predmety.add(new Vyzbroj("Gloves", 2, r.nextInt(7,10)));
        this.predmety.add(new Trinket("Earring", 3, r.nextInt(14,17)));
        this.predmety.add(new Vyzbroj("Shield", 3, r.nextInt(12,15)));
        this.predmety.add(new Vyzbroj("Armor", 4, r.nextInt(17,20)));
        this.predmety.add(new Trinket("Ring", 4, r.nextInt(22,25)));
        this.predmety.add(new Vyzbroj("Helmet", 4, r.nextInt(21,27)));
        this.predmety.add(new Vyzbroj("Sword", 5, r.nextInt(21,27)));
    }
}
