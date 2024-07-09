package Lokality;

import HernyBalik.*;
import Hraci.Hrac;
import Predmety.Predmet;
import Rytieri.ObycajnyRytier;
import Rytieri.SpravyRytierovi.KontextovaAkcia;
import Zoznamy.ZoznamPredmetov;
import Zoznamy.ZoznamRytierov;
import fri.shapesge.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Obchod slúži hráčovi ako miesto, kde si môže nakúpiť rytierov a predmety
 * obchod sa pravidelne otvára a zatvára a pritom mení svoj tovar (strieda rytierov a predmety)
 */
public class Obchod implements Miesto{
    private Mapa mapa;
    private final int cyklus;
    private boolean otvorene;
    private int cisloTovaru;
    private final Image ikona;
    private final Image pozadieObchodu;
    private final Image reroll;
    private final ZoznamRytierov zoznamRytierov;
    private final ZoznamPredmetov zoznamPredmetov;

    private final int MIN_X = 790;
    private final int MAX_X = this.MIN_X + 160;
    private final int MIN_Y = 490;
    private final int MAX_Y = this.MIN_Y + 160;

    private ArrayList<Predavatelne> tovar;

    /**
     * Konštruktor inicializuje zoznamy, kt. bude využívať a aj svoje grafické znázornenie
     * @param cyklus - určuje v akých intervaloch sa bude obchod otvárať
     */
    public Obchod(int cyklus) {
        this.cyklus = cyklus;
        this.cisloTovaru = 0;
        this.tovar = new ArrayList<>();
        this.zoznamPredmetov = new ZoznamPredmetov();
        this.zoznamRytierov = new ZoznamRytierov();
        this.pozadieObchodu = new Image("Obrazky/PozadieObchodu.png",175,50);
        this.ikona = new Image("Obrazky/Obchod.png",MIN_X + 100, MIN_Y - 30);
        this.reroll = new Image("Obrazky/EffectReroll.png", 828, 450);
    }

    public int getCyklus() {
        return this.cyklus;
    }

    public ArrayList<Predavatelne> getTovar() {
        ArrayList<Predavatelne> result = new ArrayList<>();
        Iterator<Predavatelne> iterator = this.tovar.iterator();
        while (iterator.hasNext()) {
                result.add(iterator.next());
        }
        return result;
    }

    public void zobrazIkonu() {
        this.ikona.makeVisible();
    }

    /**
     * nastaví aký tovar sa dá hráčom kúpiť podľa cislaTovaru
     */
    public void nastavObchod() {

        if (this.cisloTovaru == 0) {
            for (int i = 0; i < 6; i++) {
                var predmet = this.zoznamPredmetov.dajNahodnuOdmenu();
                this.tovar.add(predmet);
            }
        } else if (this.cisloTovaru < 8) {
            if (this.cisloTovaru % 2 == 1) {
                this.nastavRytierov();
            } else {
                this.nastavPredmety();
            }
        } else {
            if (this.cisloTovaru % 2 == 0) {
                this.nastavRytierov();
            } else {
                this.nastavPredmety();
            }
        }
        this.vytvorTovar();
    }
    private void nastavPredmety() {
        Random r  = new Random();
            for (int i = 0; i < 6; i++) {
                var predmet = this.zoznamPredmetov.dajNahodnuOdmenu();
                for (int j = 0; j < r.nextInt(0, (this.cisloTovaru) - 1); j++) {
                    if (j < 5) {
                        predmet.vylepsiPredmet();
                    }
                }
                this.tovar.add(predmet);
            }
    }
    private void nastavRytierov() {
        var r = new Random();
        switch (this.cisloTovaru) {
            case 1 -> {
                for (int i = 0; i < 3; i++) {
                    var rytier = this.zoznamRytierov.getNahodnyRytier();
                    for (int j = 0; j < 2 * this.cisloTovaru; j++) {
                        if (r.nextInt() <= 0.5) {
                            rytier.pridajSilu();
                        } else {
                            rytier.pridajPopularitu();
                        }
                    }
                    this.tovar.add(rytier);
                }
            }
            case 3 -> {
                if (this.getMapa().getEfekt() != Efekt.VETERANSKY_TRENING || (this.getMapa().getEfekt() == Efekt.VETERANSKY_TRENING && this.getMapa().getStavEfektu() == StavEfektu.BEZ_NEVYHODY)) {
                    for (int i = 0; i < 2; i++) {
                        var rytier = this.zoznamRytierov.getNahodnyRytier();
                        for (int j = 0; j < 2 * this.cisloTovaru; j++) {
                            if (r.nextInt() <= 0.5) {
                                rytier.pridajSilu();
                            } else {
                                rytier.pridajPopularitu();
                            }
                        }
                        this.tovar.add(rytier);
                    }
                    var rytier = this.zoznamRytierov.getNahodnyPokrociliRytier();
                    for (int j = 0; j < 8; j++) {
                        if (r.nextInt() <= 0.5) {
                            rytier.pridajSilu();
                        } else {
                            rytier.pridajPopularitu();
                        }
                    }
                    this.tovar.add(rytier);
                } else {
                    var rytier = this.zoznamRytierov.getNahodnyRytier();
                    for (int j = 0; j < 2 * this.cisloTovaru; j++) {
                        if (r.nextInt() <= 0.5) {
                            rytier.pridajSilu();
                        } else {
                            rytier.pridajPopularitu();
                        }
                    }
                    this.tovar.add(rytier);
                }
            }
            case 5 -> {
                if (this.getMapa().getEfekt() != Efekt.VETERANSKY_TRENING || (this.getMapa().getEfekt() == Efekt.VETERANSKY_TRENING && this.getMapa().getStavEfektu() == StavEfektu.BEZ_NEVYHODY)) {
                    var rytier = this.zoznamRytierov.getNahodnyRytier();
                    for (int j = 0; j < 2 * this.cisloTovaru; j++) {
                        if (r.nextInt() <= 0.5) {
                            rytier.pridajSilu();
                        } else {
                            rytier.pridajPopularitu();
                        }
                    }
                    this.tovar.add(rytier);
                    for (int i = 0; i < 2; i++) {
                        var ryzzier = this.zoznamRytierov.getNahodnyPokrociliRytier();
                        for (int j = 0; j < 12; j++) {
                            if (r.nextInt() <= 0.5) {
                                ryzzier.pridajSilu();
                            } else {
                                ryzzier.pridajPopularitu();
                            }
                        }
                        this.tovar.add(ryzzier);
                    }
                } else {
                    var rytier = this.zoznamRytierov.getNahodnyPokrociliRytier();
                    for (int j = 0; j < 2 * this.cisloTovaru; j++) {
                        if (r.nextInt() <= 0.5) {
                            rytier.pridajSilu();
                        } else {
                            rytier.pridajPopularitu();
                        }
                    }
                    this.tovar.add(rytier);
                }
            }
            case 7 -> {
                if (this.getMapa().getEfekt() != Efekt.VETERANSKY_TRENING || (this.getMapa().getEfekt() == Efekt.VETERANSKY_TRENING && this.getMapa().getStavEfektu() == StavEfektu.BEZ_NEVYHODY)) {
                    for (int i = 0; i < 2; i++) {
                        var rytier = this.zoznamRytierov.getNahodnyPokrociliRytier();
                        for (int j = 0; j < 2 * this.cisloTovaru; j++) {
                            if (r.nextInt() <= 0.5) {
                                rytier.pridajSilu();
                            } else {
                                rytier.pridajPopularitu();
                            }
                        }
                        this.tovar.add(rytier);
                    }
                    this.tovar.add(this.zoznamRytierov.getNahodnyLegendarnyRytier());
                } else {
                    this.tovar.add(this.zoznamRytierov.getNahodnyPokrociliRytier());
                }
            }
            case 8 -> {
                if (this.getMapa().getEfekt() != Efekt.VETERANSKY_TRENING  || (this.getMapa().getEfekt() == Efekt.VETERANSKY_TRENING && this.getMapa().getStavEfektu() == StavEfektu.BEZ_NEVYHODY)) {
                    var ryzzier = this.zoznamRytierov.getNahodnyPokrociliRytier();
                    for (int j = 0; j < 2 * this.cisloTovaru; j++) {
                        if (r.nextInt() <= 0.5) {
                            ryzzier.pridajSilu();
                        } else {
                            ryzzier.pridajPopularitu();
                        }
                    }
                    this.tovar.add(ryzzier);
                    for (int i = 0; i < 2; i++) {
                        var rytier = this.zoznamRytierov.getNahodnyLegendarnyRytier();
                        this.tovar.add(rytier);
                    }
                } else {
                    this.tovar.add(this.zoznamRytierov.getNahodnyLegendarnyRytier());
                }
            }
            case 10 -> {
                if (this.getMapa().getEfekt() != Efekt.VETERANSKY_TRENING  || (this.getMapa().getEfekt() == Efekt.VETERANSKY_TRENING && this.getMapa().getStavEfektu() == StavEfektu.BEZ_NEVYHODY)) {
                    for (int i = 0; i < 3; i++) {
                        ObycajnyRytier rytier;
                        var nahoda = r.nextInt();
                        if (nahoda <= 0.38) {
                            rytier = this.zoznamRytierov.getNahodnyRytier();
                            for (int  j = 0; j < 20; j++) {
                                if (r.nextInt() <= 0.5) {
                                    rytier.pridajSilu();
                                } else {
                                    rytier.pridajPopularitu();
                                }
                            }
                        } else if (nahoda <= 0.75) {
                            rytier = this.zoznamRytierov.getNahodnyPokrociliRytier();
                            for (int  j = 0; j < 16; j++) {
                                if (r.nextInt() <= 0.5) {
                                    rytier.pridajSilu();
                                } else {
                                    rytier.pridajPopularitu();
                                }
                            }
                        } else {
                            rytier = this.zoznamRytierov.getNahodnyLegendarnyRytier();
                        }
                        this.tovar.add(rytier);
                    }
                } else {
                    this.tovar.add(this.zoznamRytierov.getNahodnyPokrociliRytier());
                }

            }
        }
    }

    /**
     * vytvorí grafické znázornenie tovaru
     */
    public void vytvorTovar() {

        for (int i = 0; i < this.tovar.size(); i++) {
            if (this.tovar.get(i) instanceof ObycajnyRytier rytier) {
                rytier.kartaRytiera(200 + (i * 250), 100, this.mapa, true, false);
                rytier.zmenUmiestnenie(this);
            }
            if (this.tovar.get(i) instanceof Predmet predmet) {
                if (i < 3) {
                    predmet.kartaPredmetu(270 + (i * 250), 100, this, true);
                } else {
                    predmet.kartaPredmetu(270 + ((i - 3) * 250), 300, this, true);
                }
                predmet.zmenUmiestnenie(this);
            }
        }
    }
    public void zobrazObchod() {
        this.pozadieObchodu.makeVisible();
        for (Predavatelne predavatelne : this.tovar) {
                predavatelne.zobrazKartu();
        }
        this.reroll.makeVisible();
    }
    public void skryObchod() {
        this.pozadieObchodu.makeInvisible();
        for (Predavatelne predavatelne : this.tovar) {
            predavatelne.skryKartu();
            for (KontextovaAkcia k : predavatelne.pouzitelneSpravy()) {
                k.skrySpravu();
            }
        }
        this.reroll.makeInvisible();
    }


    /**
     * ManazerEventov pravidelne volá metódu a tým otvára a zatvára obchod a zmení číslo tovaru pri každom zatvorení
     * tiež skryje tovar a odstráni ho, pri otvorení nastaví nový tovar
     */
    public void zmenStav() {
        if (this.otvorene) {
            this.otvorene = false;
            this.ikona.makeInvisible();
            this.skryObchod();
            this.vyprazdniTovar();
            if (this.mapa.getPohlad() == Pohlad.OBCHOD || this.mapa.getPohlad() == Pohlad.NAKUP) {
                this.mapa.setPohlad(Pohlad.DEFAULT);
            }
            if (this.cisloTovaru < 12) {
                this.cisloTovaru++;
            }
        } else {
            this.nastavObchod();
            this.otvorene = true;
            this.zobrazIkonu();
        }
    }


    @Override
    public void vratRytierov(boolean ukoncene, ObycajnyRytier rytier) {

    }

    public int[] getSuradnice() {
        var suradnice = new int[]{this.MIN_X, this.MAX_X, this.MIN_Y, this.MAX_Y};
        return suradnice;
    }

    public boolean isOtvorene() {
        return otvorene;
    }
    public void predaj(Hrac hrac, Predavatelne p) {
        var suma = p.getCena();
        if (this.mapa.getEfekt() == Efekt.ZBERATEL) {
            if (this.mapa.getStavEfektu() != StavEfektu.BEZ_NEVYHODY) {
                suma *= 2;
            }
        }
        if (hrac.zmenStavPenazi(-suma)) {
            if (p instanceof Predmet predmet && hrac.pridajPredmet(predmet)) {
                p.zobrazKartu();
                this.tovar.remove(p);
            } else if (p instanceof ObycajnyRytier rytier && hrac.pridajRytiera(rytier)) {
                p.zobrazKartu();
                this.tovar.remove(p);
            } else {
                hrac.zmenStavPenazi(p.getCena());
            }
        }
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }
    public Mapa getMapa() {
        return this.mapa;
    }
    public void vyprazdniTovar() {
        this.tovar = new ArrayList<>();
        this.zoznamPredmetov.dopln();
    }

    public void rerollShop() {
        for (Predavatelne predavatelne : this.tovar) {
            predavatelne.skryKartu();
            for (KontextovaAkcia k : predavatelne.pouzitelneSpravy()) {
                k.skrySpravu();
            }
        }
        this.reroll.makeInvisible();
        this.vyprazdniTovar();
        this.nastavObchod();
        this.vytvorTovar();
        for (Predavatelne predavatelne : this.tovar) {
            predavatelne.zobrazKartu();
        }
        this.reroll.makeVisible();
    }
}