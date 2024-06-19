package Zoznamy;

import Predmety.Trinket;
import Predmety.Vyzbroj;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import Rytieri.Schopnsoti.Schopnost;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Trieda sluzi ako zoznam Obycajnych rytierov, ktory sa zucastnuju turnajov v Arene proti Hracovi,
 * v triede je taktiez implementovany ich rast po kazdom kole turnaja
 */
public class ZoznamSuperovVArene {
    private final ArrayList<ObycajnyRytier> superi;

    public ZoznamSuperovVArene() {
        this.superi = new ArrayList<>();
        this.superi.add(new PokrocilyRytier("Algebrix", Schopnost.GLADIATOR,5, 14));
        this.superi.add(new PokrocilyRytier("Informatikix", Schopnost.PAN_PRIPRAVENY, 6, 7));
        this.superi.add(new ObycajnyRytier("Algoritmix", 9, 2));
        this.superi.add(new ObycajnyRytier("Matematix", 8, 4));
        this.superi.add(new ObycajnyRytier("Ekonomix", 5, 2));
        this.superi.add(new ObycajnyRytier("Sieteix", 7, 4));
        this.superi.add(new ObycajnyRytier("Ekonomixix", 8, 0));
        this.superi.add(new ObycajnyRytier("DPravix", 6, 5));

    }

    public ArrayList<ObycajnyRytier> getSuperov() {
        ArrayList<ObycajnyRytier> result = new ArrayList<>();
        Iterator<ObycajnyRytier> iterator = this.superi.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    /**
     * metoda pridava staty rytierom a dava im predmety na zaklade kola
     * @param kolo - arena dava svoje cisloTurnaja ako parameter podla ktoreho sa urci co rytieri ziskavaju
     */
    public void noveKolo(int kolo) {
        var r = new Random();
        for (int i = 0; i < this.superi.size(); i++) {
            this.superi.get(i).pridajSilu();
            this.superi.get(i).pridajPopularitu();
            this.specialnaOdmena(i, this.superi.get(i).getOdmena());
            if (kolo < 4) {
                if (r.nextDouble() > 0.5) {
                    this.superi.get(i).pridajSilu();
                } else {
                    this.superi.get(i).pridajPopularitu();
                }
            } else {
                this.superi.get(i).pridajSilu();
                this.superi.get(i).pridajPopularitu();
            }
            switch (i) {
                case 0 :
                    PokrocilyRytier pR1 = (PokrocilyRytier) this.superi.get(0);
                    if (kolo == 3) {
                            pR1.getVyzbroj().put(new Vyzbroj("knife", 2, 0));
                    } else if (kolo % 2 == 1) {
                        if (r.nextDouble() <= 0.5) {
                            pR1.pridajSilu();
                        } else {
                            pR1.pridajPopularitu();
                        }
                        if (pR1.getVyzbroj().get() != null && pR1.getVyzbroj().get().mozemVylepsit()) {
                            pR1.getVyzbroj().get().vylepsiPredmet();
                        } else if (pR1.getTrinket().isEmpty()){
                            pR1.getTrinket().put(new Trinket("dice", 2, 0));
                        } else if (pR1.getTrinket2().isEmpty()){
                            pR1.getTrinket2().put(new Trinket("cube", 2, 0));
                        }
                    }
                    break;
                case 1 :
                    PokrocilyRytier pR2 = (PokrocilyRytier) this.superi.get(1);
                    if (kolo % 2 == 1) {
                        pR2.pridajSilu();
                    } else if (kolo % 3 == 2) {
                        if (pR2.getTrinket().isEmpty()) {
                            pR2.getTrinket().put(new Trinket("beads", 2, 0));
                        } else if (pR2.getTrinket2().isEmpty()) {
                            pR2.getTrinket().put(new Trinket("ring", 3, 0));
                        }
                    } else if (kolo == 6) {
                        pR2.getVyzbroj().put(new Vyzbroj("sword", 5, 0));
                    } else if (kolo > 6){
                        pR2.getVyzbroj().get().vylepsiPredmet();
                        pR2.getTrinket().get().vylepsiPredmet();
                        pR2.getTrinket2().get().vylepsiPredmet();
                    }
                    break;
                case 2 :
                    if (kolo == 3) {
                        this.superi.get(2).getVyzbroj().put(new Vyzbroj("algorithm", 3, 0));
                    } else if (kolo > 4) {
                        if (this.superi.get(2).getVyzbroj().get().mozemVylepsit()) {
                            this.superi.get(2).getVyzbroj().get().vylepsiPredmet();
                        }
                    }
                    break;
                case 3 :
                    this.superi.get(3).pridajSilu();
                    break;
                case 4 :
                    if (kolo == 2) {
                        this.superi.get(4).getTrinket().put(new Trinket("testy", 3, 0));
                    } else if (kolo > 3 && kolo % 2 == 0) {
                        if (this.superi.get(4).getTrinket().get().mozemVylepsit()) {
                            this.superi.get(4).getTrinket().get().vylepsiPredmet();
                        }
                    }
                case 5 :
                    this.superi.get(5).pridajPopularitu();
                    break;
                case 7 :
                    var cislo = r.nextDouble();
                    if (cislo <= 0.1) {
                        this.superi.get(7).pridajSilu(r.nextInt(2,4));
                    } else if (cislo <= 0.3) {
                        this.superi.get(7).pridajPopularitu(r.nextInt(2,4));
                    } else if (cislo <= 0.65) {
                        this.superi.get(7).pridajSilu();
                        if (kolo == 3) {
                            this.superi.get(7).getVyzbroj().put(new Vyzbroj("ruler", r.nextInt(1, 3), 0));
                        }
                    } else {
                        this.superi.get(7).pridajPopularitu();
                        if (kolo == 3) {
                            this.superi.get(7).getTrinket().put(new Trinket("pen", r.nextInt(1, 3), 0));
                        }
                    }
                    break;
            }
        }
    }

    private void specialnaOdmena(int i, int odmena) {
        var rytier = this.superi.get(i);
        if (odmena == 0 && rytier.getSkore() < 150) {
            rytier.setOdmena();
            switch (i) {
                case 0, 4, 6 -> rytier.pridajPopularitu();
                case 2 -> rytier.getTrinket().put(new Trinket("body", 2, 10));
                case 7 -> rytier.pridajSilu();
            }
        } else if (odmena == 1 && rytier.getSkore() < 450) {
            rytier.setOdmena();
            switch (i) {
                case 3, 4, 6, 7 -> rytier.pridajSkore(100, false, null);
            }
        } else if (odmena == 2 && rytier.getSkore() < 600) {
            rytier.setOdmena();
            switch (i) {
                case 1, 2, 3, 5 -> rytier.pridajPopularitu();
            }
        } else if (odmena == 3 && rytier.getSkore() < 900) {
            rytier.setOdmena();
            switch (i) {
                case 0, 3, 4, 6, 7 -> rytier.pridajSilu(2);
            }
        }
    }
}
