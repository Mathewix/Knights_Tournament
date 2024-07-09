package Hraci;

import HernyBalik.*;
import Lokality.*;
import Predmety.Artefakt;
import Predmety.Predmet;
import Rytieri.LegendarnyRytier;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import Rytieri.Schopnsoti.Schopnost;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.Text;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda predstavuje hráča v hre, ktorý má peniaze, svoj inventár (max 16 predmetov), svojich rytierov (max 3) ktorých si môže kúpiť a pridať (aj predmety nie len rytierov)
 *
 */
public class Hrac implements Hodnotitelne {
    private final Predmet[] inventar;
    private int miestoInventar;
    private final Image[] inventarObrazky;
    private final Image inventarIkona;
    private final ObycajnyRytier[] rytieri;
    private int peniaze;
    private Text peniazeT;
    private final Image peniazeObrazok;
    private final Doska doska;
    private Mapa mapa;
    private ManazerEventov manazerEventov;
    private int bonus;

    /**
     * V konštruktore sa nastaví doska na ktorej má hráč svoje veci v priebehu hry
     * @param peniaze - začiatočná suma peňazí, kt. hráč vlastní
     */
    public Hrac(int peniaze) {
        this.inventar = new Predmet[16];
        this.inventarObrazky = new Image[16];
        this.miestoInventar = 16;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                this.inventarObrazky[i * 8 + j] = new Image("Obrazky/inventarPolicko.png",25 + 75 * i,50 + 75 * j);
            }
        }
        this.inventarIkona = new Image("Obrazky/inventar.png",25 , 775);

        this.rytieri = new ObycajnyRytier[3];
        this.peniaze = peniaze;
        this.peniazeObrazok = new Image("Obrazky/coin.png", 40, 690);

        var dlzkaPenazi ="" +  this.peniaze;
        this.peniazeT = new Text("" + this.peniaze,110 - 5 * dlzkaPenazi.length() , 725);
        this.peniazeT.changeFont("Palatino Linotype" + this.peniaze, FontStyle.BOLD, 30);
        this.peniazeT.changeColor("#FFFDF4");


        this.doska = new Doska(this);
    }

    /**
     * Zmení stav hráčovích peňazí za výhru v aréne, či nákup v obchode a vyhodnotí, či zmena prebehla úspešne
     * @param kolko - suma, kt. hráč dostal alebo zaplatil
     * @return - true ak si nákup prebehol úspešne, false ak nie
     */
    public boolean zmenStavPenazi(int kolko) {
        if (this.peniaze + kolko >= 0) {
        this.peniaze += kolko;
        this.peniazeT.makeInvisible();
        var dlzkaPenazi ="" +  this.peniaze;
        this.peniazeT = new Text("" + this.peniaze,110 - 5 * dlzkaPenazi.length() , 725);
        this.peniazeT.changeFont("Palatino Linotype" + this.peniaze, FontStyle.BOLD, 30);
        this.peniazeT.changeColor("#FFFDF4");
        this.peniazeT.makeVisible();
        return true;
        } else {
            this.manazerEventov.pozastavHru();
            JOptionPane.showMessageDialog(null, "Si chudobny a tieto vydavky si nemozes dovolit", "Chyba", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Hráč si pridá rytiera ak už nemá max.3, vykreslí ho na správnej pozícii na doske a nastaví rytierovi, že ho trénuje
     * @param rytier - rytier, kt. pridávam
     * @return - vráti false/true podľa úspešnosti pridania
     */
    public boolean pridajRytiera(ObycajnyRytier rytier) {
        var availableSlots = this.rytieri.length;
        if (!this.doska.unlocked()) {
            availableSlots--;
        }
        for (int i = 0; i < availableSlots; i++) {
            if (this.rytieri[i] == null) {
                this.rytieri[i] = rytier;
                if (this.mapa.getEfekt() == Efekt.SLACHTA) {
                    rytier.setCas(this.manazerEventov.getHodiny() % 200);
                }
                if (rytier instanceof PokrocilyRytier pR) {
                    pR.setTrenujeMaHrac(this);
                    pR.zmenUmiestnenie(this.doska);
                    pR.kartaRytiera(215 + i * 300, 690, this.mapa,false, true);
                    if (pR.getSchopnost() == Schopnost.DOBRODRUH) {
                        pR.pridajSkusenost(false);
                        pR.pridajSkusenost(false);
                    }
                    return true;
                }
                rytier.setTrenujeMaHrac(this);
                rytier.zmenUmiestnenie(this.doska);
                rytier.kartaRytiera(215 + i * 300, 690, this.mapa,false, true);
                return true;
            }
        }
        this.manazerEventov.pozastavHru();
        if (availableSlots == 3) {
            JOptionPane.showMessageDialog(null, "Jeden clovek nedokaze spravovat 4 rytierov. \n " +
                    "Skus prepustit jedneho zo svojich a skus to znova", "Chyba", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Na spravovanie 3 rytierov potrebuješ licenciu. \n " +
                    "Skus si ju kúpiť v pravo dole za 100 toliarov \n alebo prepustit jedneho zo svojich rytierov a skus to znova", "Chyba", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }
    public void predajRytiera(ObycajnyRytier rytier) {

        for (int i = 0; i < 3; i++) {
            if (this.rytieri[i] == rytier) {
                if (!rytier.getTrinket().isEmpty()) {
                    rytier.zlozPredmet(rytier.getTrinket().get());
                }
                if (!rytier.getVyzbroj().isEmpty()) {
                    rytier.zlozPredmet(rytier.getVyzbroj().get());
                }
                if (rytier instanceof PokrocilyRytier pR && !pR.getTrinket2().isEmpty()) {
                    pR.zlozPredmet(pR.getTrinket2().get());
                }
                if (rytier instanceof LegendarnyRytier lR && (!lR.getVyzbroj2().isEmpty() && !lR.getMeno().equals("King Arthur"))) {
                    lR.zlozPredmet(lR.getVyzbroj2().get());
                }
                this.rytieri[i].skryKartu();
                this.rytieri[i] = null;
            }
        }
        if (this.mapa.getEfekt() != Efekt.VETERANSKY_TRENING) {
            if (this.mapa.getEfekt() != Efekt.OBCHODNIK) {
                this.zmenStavPenazi(rytier.getCena() / 2);
            } else {
                this.zmenStavPenazi(rytier.getCena());
                if (this.mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                    this.pridajBonus(rytier.getCena() / 2);
                }
            }
        } else {
            if (this.mapa.getStavEfektu() != StavEfektu.VYLEPSENE) {
                ObycajnyRytier rytier1 = null;
                ObycajnyRytier rytier2 = null;
                for (ObycajnyRytier obycajnyRytier : this.rytieri) {
                    if (obycajnyRytier != null) {
                        if (rytier1 == null) {
                            rytier1 = obycajnyRytier;
                        }
                        if (rytier1 != obycajnyRytier){
                            rytier2 = obycajnyRytier;
                        }
                    }
                }
                if (rytier1 != null && rytier2 == null) {
                    rytier1.pridajSilu((int) Math.floor(rytier.getSila(this.doska) * 0.5));
                    rytier1.pridajPopularitu((int) Math.floor(rytier.getPopularita(this.doska) * 0.5));
                } else if (rytier1 != null) {
                    for (int i = 0; i < (int) Math.floor(rytier.getSila(this.doska) * 0.5); i++) {
                        var r = new Random();
                        if (r.nextDouble() < 0.5) {
                            rytier1.pridajSilu();
                        } else {
                            rytier2.pridajSilu();
                        }
                    }
                    for (int i = 0; i < (int) Math.floor(rytier.getPopularita(this.doska) * 0.5); i++) {
                        var r = new Random();
                        if (r.nextDouble() < 0.5) {
                            rytier1.pridajPopularitu();
                        } else {
                            rytier2.pridajPopularitu();
                        }
                    }
                }
            } else if (this.mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                ObycajnyRytier rytier1 = null;
                ObycajnyRytier rytier2 = null;
                for (ObycajnyRytier obycajnyRytier : this.rytieri) {
                    if (obycajnyRytier != null) {
                        if (rytier1 == null) {
                            rytier1 = obycajnyRytier;
                        }
                        if (rytier1 != obycajnyRytier){
                            rytier2 = obycajnyRytier;
                        }
                    }
                }
                if (rytier1 != null && rytier2 == null) {
                    rytier1.pridajSilu((int) Math.ceil(rytier.getSila(this.doska) * 0.5));
                    rytier1.pridajPopularitu((int) Math.ceil(rytier.getPopularita(this.doska) * 0.5));
                } else if (rytier1 != null) {
                    if (rytier1.getSila(this.doska) > rytier2.getSila(this.doska)) {
                        rytier1.pridajSilu((int) Math.ceil(rytier.getSila(this.doska) * 0.5));
                    } else {
                        rytier2.pridajSilu((int) Math.ceil(rytier.getSila(this.doska) * 0.5));
                    }
                    if (rytier1.getPopularita(this.doska) > rytier2.getPopularita(this.doska)) {
                        rytier1.pridajPopularitu((int) Math.ceil(rytier.getPopularita(this.doska) * 0.5));
                    } else {
                        rytier2.pridajPopularitu((int) Math.ceil(rytier.getPopularita(this.doska) * 0.5));
                    }
                }
            }
        }
    }

    public void premiestniRytiera(ObycajnyRytier rytier, Miesto miesto) {
            for (int i = 0; this.rytieri.length > i; i++) {
                if (this.rytieri[i] == rytier) {
                    var obrazok = "Obrazky/lokalita";
                    if (miesto instanceof Doska){
                        obrazok += ".png";
                    } else if (miesto instanceof Cvicisko) {
                        obrazok += "Cvicisko.png";
                    } else if (miesto instanceof Krcma) {
                        obrazok += "Krcma.png";
                    } else if (miesto instanceof Arena) {
                        obrazok += "Arena.png";
                    } else if (miesto instanceof Cech) {
                        obrazok += "Cech.png";
                    }
                    this.doska.zmenLokalituRytiera(i, obrazok);
                }
            }

    }
    /**
     * Hráč si pridá do inventára Predmet ak už nemá max.16 a nastaví, že ho má v inventári
     * @param predmet - predmet, kt. pridávam
     * @return - vráti false/true podľa úspešnosti pridania
     */
    public boolean pridajPredmet(Predmet predmet) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.inventar[i * 8 + j] == null) {
                    this.inventar[i * 8 + j] = predmet;
                    predmet.zmenUmiestnenie(this.doska);
                    this.inventar[i * 8 + j].kartaPredmetu(25 + 75 * i, 50 + 75 * j, this.mapa.getObchod(), false);
                    this.inventar[i * 8 + j].zobrazKartu();
                    if (this.mapa.getEfekt() == Efekt.ZBERATEL) {
                        this.manazerEventov.pridajAkciuPredmetu(predmet);
                    } else {
                        if (predmet instanceof Artefakt artefakt) {
                            this.manazerEventov.pridajAkciuPredmetu(artefakt);
                        }
                    }
                    this.miestoInventar--;
                    return true;
                }
            }
        }
        this.manazerEventov.pozastavHru();
        JOptionPane.showMessageDialog(null, "Nebud tak chamtivy, nemas dost miesta v kapse. \n " +
                "Skus predat alebo dat nejaky s predmetov rytietovi a potom skus znova", "Chyba", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    public void odstranPredmet(Predmet predmet) {
        for (int i = 0; i < this.inventar.length; i++) {
            if (predmet == this.inventar[i]) {

                if (this.mapa.getEfekt() == Efekt.ZBERATEL) {
                    if (this.mapa.getStavEfektu() != StavEfektu.VYLEPSENE) {
                        this.manazerEventov.odstranAkciuPredmetu(predmet);
                    }
                } else {
                    if (predmet instanceof Artefakt artefakt) {
                        this.manazerEventov.odstranAkciuPredmetu(artefakt);
                    }
                }
                this.inventar[i] = null;
                this.miestoInventar++;
                break;
            }
        }
    }

    public Doska getDoska() {
        return this.doska;
    }
    public ObycajnyRytier getRytier(int ktory) {
        return this.rytieri[ktory];
    }
    public ArrayList<ObycajnyRytier> getRytieri() {
        var rytieri = new ArrayList<ObycajnyRytier>();
        for (int i = 0; i < 3; i++) {
            if (this.rytieri[i] != null) {
                rytieri.add(this.rytieri[i]);
            }
        }
        return  rytieri;
    }
    /**
     * zobrazí inventár podľa toho čo v ňom je/ nie je
     */
    public void zobrazInventar() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.inventar[i * 8 + j] == null) {
                    this.inventarObrazky[i * 8 + j].makeVisible();
                } else {
                    this.inventar[i * 8 + j].kartaPredmetu(25 + 75 * i,50 + 75 * j, this.mapa.getObchod(), false);
                    this.inventar[i * 8 + j].zobrazKartu();
                }
            }
        }
    }

    public ArrayList<Predmet> getInventar() {
        ArrayList<Predmet> predmety = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.inventar[i * 8 + j] != null) {
                    predmety.add(this.inventar[i * 8 + j]);
                }
            }
        }
        return predmety;
    }
    public void vylepsiPredmet(Predmet predmet) {
        if (predmet.mozemVylepsit()) {
            if (this.zmenStavPenazi(-predmet.getCenaVylepsenia())) {
                predmet.vylepsiPredmet();
            }
        } else {
            this.manazerEventov.pozastavHru();
            JOptionPane.showMessageDialog(null, "Predmet momentalne nemozes vylepsit.", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void odzbrojRytierov() {
        for (int i = 0; this.rytieri.length > i; i++) {
            if (this.rytieri[i] != null) {
                if (!this.rytieri[i].getTrinket().isEmpty()) {
                    if (this.miestoInventar != 0 ) {
                        this.rytieri[i].zlozPredmet(this.rytieri[i].getTrinket().get());
                    } else {

                    }

                }
                if (!this.rytieri[i].getVyzbroj().isEmpty()) {
                    this.rytieri[i].zlozPredmet(this.rytieri[i].getVyzbroj().get());
                }
                if (this.rytieri[i] instanceof PokrocilyRytier pR && !pR.getTrinket2().isEmpty()) {
                    pR.zlozPredmet(pR.getTrinket2().get());
                }
                if (this.rytieri[i] instanceof LegendarnyRytier lR && (!lR.getVyzbroj2().isEmpty() && !lR.getMeno().equals("King Arthur"))) {
                    lR.zlozPredmet(lR.getVyzbroj2().get());
                }
            }
        }
    }
    public int getSkore() {
        var skore = 0;
        for (ObycajnyRytier obycajnyRytier : this.rytieri) {
            if (obycajnyRytier != null) {
                skore += obycajnyRytier.getCena();
            }
        }
        for (Predmet predmet : this.getInventar()) {
            skore += predmet.getCena();
        }

        return this.peniaze + skore + this.bonus;
    }

    @Override
    public String getMeno() {
        return "Hrac";
    }

    public int getPeniaze() {return this.peniaze;}

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
        this.doska.zobrazDosku();
        this.inventarIkona.makeVisible();
        this.peniazeObrazok.makeVisible();
        this.peniazeT.makeVisible();
        this.zobrazInventar();
    }


    public void setManazerEventov(ManazerEventov manazerEventov) {
        this.manazerEventov = manazerEventov;
    }

    public ManazerEventov getManazerEventov() {
        return this.manazerEventov;
    }

    public int getPocetArtefaktov() {
        var pocet = 0;
        for (Predmet predmet : this.getInventar()) {
            if (predmet instanceof Artefakt) {
                pocet++;
            }
        }
        return pocet;
    }

    public Mapa getMapa() {
        return this.mapa;
    }
    public void pridajBonus(int kolko) {
        this.bonus += kolko;
    }

    public void terapiaPreRytierov() {
        for (ObycajnyRytier obycajnyRytier : this.rytieri) {
            if (obycajnyRytier != null) {
                obycajnyRytier.setBolVCechu(false);
            }
        }
    }

    public int getInventorySpace() {
        return this.miestoInventar;
    }
}
