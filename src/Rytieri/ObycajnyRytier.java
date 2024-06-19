package Rytieri;


import HernyBalik.*;
import Hraci.Hrac;
import Lokality.*;
import Predmety.MiestoNaPredmet;
import Predmety.Predmet;
import Predmety.Trinket;
import Predmety.Vyzbroj;
import Rytieri.Schopnsoti.Schopnost;
import Rytieri.SpravyRytierovi.*;
import fri.shapesge.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda slúži na vytvorenie základného rytiera do hry a implementuje Interface Predavatelne aby sa dal "predávať" v obchode.
 * ObycajnyRytier tiež slúži ako predok pre ďalšie typy Rytierov
 *
 */
public class ObycajnyRytier implements Predavatelne, Hodnotitelne {

    private Image ikona;
    private Miesto umiestnenie;
    private final String meno;
    protected int sila;
    protected int popularita;

    private int skore;
    private int pocetTurnajov;

    private Image hviezda;
    private Image mec;

    private Image miestoNaTrinket;
    private Image miestoNaVyzbroj;

    private Image obrazok;
    protected String obrazokCesta;
    private Image pozadie;
    private Image cenaObrazok;

    private Text cenaText;
    private Text silaText;
    private Text popularitaText;
    private Text menoT;
    private int[] suradnice;
    private int[] suradniceTrinket;
    private int[] suradniceVyzbroj;

    private boolean oddychuje;
    private boolean bolVCechu;

    private ArrayList<KontextovaAkcia> vsetkyAkcie;

    private final MiestoNaPredmet<Vyzbroj> vyzbroj;
    private final MiestoNaPredmet<Trinket> trinket;
    protected Rank rank;
    private int skusenosti;
    private Hrac hrac;
    private Image rarita;
    private int cas;
    private int odmena;
    private String raritaText;
    protected Image rankObrazok;

    /**
     * Konštruktov slúži na vytvorenie inštancie rytiera s náhodnou silou a popularitou (staty)
     * statov je dokopy 10
     * @param meno pridelí meno rytierovi
     */
    public ObycajnyRytier(String meno) {
        Random r = new Random();
        this.skore = 0;
        this.sila = r.nextInt(1, 10);
        this.popularita = 10 - this.sila;
        this.oddychuje = true;
        this.meno = meno;
        this.vyzbroj = new MiestoNaPredmet<>();
        this.trinket = new MiestoNaPredmet<>();
        this.skusenosti = 0;
        this.raritaText = "Obrazky/obycajny.png";
        this.obrazokCesta = "Obrazky/obycajnyRytier.png";
    }
    public ObycajnyRytier(String meno, int sila, int popularita) {
        this.sila = sila;
        this.popularita = popularita;
        this.oddychuje = true;
        this.meno = meno;
        this.vyzbroj = new MiestoNaPredmet<>();
        this.trinket = new MiestoNaPredmet<>();
        this.raritaText = "Obrazky/obycajny.png";
        this.obrazokCesta = "Obrazky/obycajnyRytier.png";
    }
    public void zobrazIkonuRytiera() {
        this.ikona.makeVisible();
    }
    public void skryIkonuRytiera() {
        this.ikona.makeInvisible();
        this.ikona = new Image("Obrazky/rytierIkona.png",0,0);
    }

    /**
     *  Metóda slúži na vykreslovanie rytiera v hre a na základe toho kde sa rytier nachádza (obchod alebo u hráča) sa priradia rytierovi
     *  aj použiteľné kontextové akcie na základe toho či je rytier Pokročilý alebo obyčajný
     *
     * @param suradnicaX
     * @param suradnicaY
     * @param mapa - potrebné aby som dokázal inicializovať kontextové akcie, kt. potrebujú v parametri Miesto im relevantné
     * @param prehlad - určuje ako sa má vykresliť : false ak je iba umiestnený na doske hráča / true ak je v obchode alebo kliknem na prehlad rytiera
     * @param zobraz - ak je zobraz true karta sa rovno zobrazí
     */
    public void kartaRytiera(int suradnicaX, int suradnicaY, Mapa mapa, boolean prehlad, boolean zobraz) {

        if (this.suradnice != null) {
            this.skryKartu();
        }
        this.vsetkyAkcie = new ArrayList<>();

        this.suradnice = new int[]{suradnicaX, suradnicaX + 225, suradnicaY, suradnicaY + 300};
        this.rarita = new Image(this.raritaText, suradnicaX, suradnicaY);
        this.pozadie = new Image("Obrazky/pozadie.png", suradnicaX + 8, suradnicaY + 8);
        this.obrazok = new Image(this.obrazokCesta, suradnicaX + 50, suradnicaY + 50);

        this.hviezda = new Image("Obrazky/star.png", suradnicaX + 126, suradnicaY + 191);
        this.mec = new Image("Obrazky/sword.png", suradnicaX + 40, suradnicaY + 191);
        this.miestoNaTrinket = new Image("Obrazky/amulet.png", suradnicaX + 16, suradnicaY + 232);
        this.suradniceTrinket = new int[]{suradnicaX + 16, suradnicaX + 56, suradnicaY + 232, suradnicaY + 272};
        this.miestoNaVyzbroj = new Image("Obrazky/armor.png", suradnicaX + 64, suradnicaY + 232);
        this.suradniceVyzbroj = new int[]{suradnicaX + 64, suradnicaX + 104, suradnicaY + 232, suradnicaY + 272};
        if (this instanceof PokrocilyRytier pR) {
            pR.setMiestoNaTrinket2(new Image("Obrazky/amulet.png", suradnicaX + 112, suradnicaY + 232));
            pR.setSuradniceTrinket2(new int[]{suradnicaX + 112, suradnicaX + 152, suradnicaY + 232, suradnicaY + 272});
            pR.setSchopnostObrazok(new Image(pR.getSchopnost().getObrazok(), suradnicaX + 16, suradnicaY + 16));
        }
        if (this instanceof LegendarnyRytier lR) {
            lR.setMiestoNaVyzbroj2(new Image("Obrazky/armor.png", suradnicaX + 160, suradnicaY + 232));
            lR.setSuradniceVyzbroj2(new int[]{suradnicaX + 160, suradnicaX + 208, suradnicaY + 232, suradnicaY + 272});
            lR.setSchopnostObrazok(new Image(lR.getSchopnost().getObrazok(), suradnicaX + 16, suradnicaY + 16));
        }

        this.menoT = new Text(this.meno, suradnicaX + 60, suradnicaY + 40);
        this.menoT.changeFont("New Rocker", FontStyle.BOLD, 22);

        this.silaText = new Text("" + this.sila, suradnicaX + 75, suradnicaY + 210);
        this.silaText.changeFont("", FontStyle.BOLD, 20);

        this.popularitaText = new Text("" + this.popularita, suradnicaX + 164, suradnicaY + 210);
        this.popularitaText.changeFont("", FontStyle.BOLD, 20);

        if (prehlad) {
            this.vsetkyAkcie.add(new KupSa(this, mapa.getObchod()));
            this.cenaObrazok = new Image("Obrazky/cena.png", suradnicaX + 75, suradnicaY + 300);
            var cena = "";
            if (mapa.getEfekt() != Efekt.OBCHODNIK) {
                cena += this.getCena();
            } else {
                cena += ((int) Math.ceil(this.getCena() * 1.5));
            }
            this.cenaText = new Text(cena + "g", suradnicaX + 110 - (cena.length() * 6), suradnicaY + 325);
            this.cenaText.changeFont("New Rocker", FontStyle.BOLD, 24);
        } else {
            this.cenaText = null;
            this.cenaObrazok = null;
            this.vsetkyAkcie.add(new ChodNaCvicisko(this, mapa.getCvicisko()));
            this.vsetkyAkcie.add(new ChodDoKrcmy(this, mapa.getKrcma()));
            this.vsetkyAkcie.add(new ChodDoCechu(this, mapa.getCech()));
            this.vsetkyAkcie.add(new ChoddoAreny(this, mapa.getArena()));
            this.vsetkyAkcie.add(new ZlozTrinket1(this));
            this.vsetkyAkcie.add(new ZlozVyzbroj1(this));
            this.vsetkyAkcie.add(new Predat(this, this.hrac.getDoska(), 14, 272));

            this.vsetkyAkcie.add(new PrerusAktivitu(this));
            if (this instanceof PokrocilyRytier pR) {
                this.vsetkyAkcie.add(new ZlozTrinket2(pR));
            }
            if (this instanceof LegendarnyRytier lR) {
                this.vsetkyAkcie.add(new ZlozVyzbroj2(lR));
            }
        }

        if (zobraz) {
            this.zobrazKartu();
        }

    }
    public void zobrazKartu() {
        this.rarita.makeVisible();
        this.pozadie.makeVisible();
        this.obrazok.makeVisible();
        this.hviezda.makeVisible();
        this.mec.makeVisible();
        this.menoT.makeVisible();
        this.silaText.makeVisible();
        this.popularitaText.makeVisible();
        this.miestoNaVyzbroj.makeVisible();
        this.miestoNaTrinket.makeVisible();
        if (this.cenaObrazok != null && this.cenaText != null) {
            this.cenaObrazok.makeVisible();
            this.cenaText.makeVisible();
        }
        if (this.rankObrazok != null) {
            this.rankObrazok.makeVisible();
        }
        if (this instanceof PokrocilyRytier pR) {
            pR.getMiestoNaTrinket2().makeVisible();
            pR.getSchopnostObrazok().makeVisible();
        }
        if (this instanceof LegendarnyRytier lR) {
            lR.getMiestoNaVyzbroj2().makeVisible();
        }
    }

    public int[] getSuradnice() {
        return this.suradnice;
    }
    public void skryKartu() {
        this.rarita.makeInvisible();
        this.pozadie.makeInvisible();
        this.obrazok.makeInvisible();
        this.hviezda.makeInvisible();
        this.mec.makeInvisible();
        this.menoT.makeInvisible();
        this.silaText.makeInvisible();
        this.popularitaText.makeInvisible();
        this.miestoNaVyzbroj.makeInvisible();
        this.miestoNaTrinket.makeInvisible();
        if (this.cenaObrazok != null && this.cenaText != null) {
            this.cenaObrazok.makeInvisible();
            this.cenaText.makeInvisible();
        }
        if (this.rankObrazok != null) {
            this.rankObrazok.makeInvisible();
        }
        if (this instanceof PokrocilyRytier pR) {
            pR.getMiestoNaTrinket2().makeInvisible();
            pR.getSchopnostObrazok().makeInvisible();
        }
        if (this instanceof LegendarnyRytier lR) {
            lR.getMiestoNaVyzbroj2().makeInvisible();
        }
    }

    @Override
    public int getCena() {
        int cena = this.getSila(null) * 2 + this.getPopularita(null) * 3 + 5;
        return cena;
    }

    /**
     * Metóda vracia silu rytiera na výpočet času, ktorý strávy na Cvicisku, výpočet ceny rytiera alebo na výpočet umiestnenia v Aréne
     * @param miesto - rozhoduje akú silu rytiera má vrátiť na základe inštancie miesta (v Aréne aj so silou vybavení na rytierovi inak bez)
     * @return Sila rytiera
     */
    public int getSila(Miesto miesto) {
        var bonusovaSila = 0;
        if (this instanceof LegendarnyRytier lR) {
            if (!lR.getVyzbroj2().isEmpty() && miesto instanceof Arena) {
                bonusovaSila += lR.getVyzbroj2().get().getSila();
            }
        }
        if (!this.vyzbroj.isEmpty() && miesto instanceof Arena) {
            bonusovaSila += this.vyzbroj.get().getSila();
        }
        if (this.getHrac() != null) {
            if (miesto instanceof Arena) {
                var mapa = this.getHrac().getMapa();
                if (mapa.getEfekt() == Efekt.SEBAVEDOMI_RYTIERI && mapa.getStavEfektu() != StavEfektu.VYLEPSENE) {
                    bonusovaSila += (int) Math.floor(this.popularita * 0.5);
                } else if (mapa.getEfekt() == Efekt.SEBAVEDOMI_RYTIERI && mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                    bonusovaSila += (int) Math.ceil(this.popularita * 0.7);
                }
            }
        }
        return this.sila + bonusovaSila;
    }

    /**
     *
     * Metóda vracia silu rytiera na výpočet času, ktorý strávy v Krčme, výpočet ceny rytiera alebo na výpočet zisku v Aréne
     * @param miesto - rozhoduje akú popularitu rytiera má vrátiť na základe inštancie miesta (v Aréne aj s popularitou trinketu na rytierovi inak bez)
     * @return Popularita rytiera
     */
    public int getPopularita(Miesto miesto) {
        var bonusovaPopularita = 0;
        if (this instanceof PokrocilyRytier pR) {
            if (!pR.getTrinket2().isEmpty() && miesto instanceof Arena) {
                bonusovaPopularita += pR.getTrinket2().get().getPopularita();
            }
        }
        if (!this.trinket.isEmpty() && miesto instanceof Arena) {
            bonusovaPopularita += this.trinket.get().getPopularita();
        }
        if (miesto instanceof Arena) {
            if (this.getHrac() != null) {
                var mapa = this.getHrac().getMapa();
                if (mapa.getEfekt() == Efekt.SEBAVEDOMI_RYTIERI && mapa.getStavEfektu() != StavEfektu.VYLEPSENE) {
                    bonusovaPopularita += (int) Math.floor(this.sila * 0.5);
                } else if (mapa.getEfekt() == Efekt.SEBAVEDOMI_RYTIERI && mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                    bonusovaPopularita += (int) Math.ceil(this.sila * 0.7);
                }
            }
        }
        return this.popularita + bonusovaPopularita;
    }

    public String getMeno() {
        return meno;
    }

    public Miesto getUmiestnenie() {
        return umiestnenie;
    }

    /**
     * Slúži ako informácia pre ostatné triedy, aby vedeli ako sa voči rytierovi správať
     * @return
     */
    public boolean isTrenujeMaHrac() {
        if (this.hrac == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Nastaví aký hráč trénuje rytiera a inicializuje ikonu, kt. sa využíva ako reprezentácia rytiera na mieste v ktorom sa nachádza
     * ikona sa inicializuje tu, pretože iba hráč posiela rytiera na miesta na mape
     * @param hrac - nastaví rytierovi hráča, kt. ho spravuje
     */
    public void setTrenujeMaHrac(Hrac hrac) {
        this.hrac = hrac;
        this.ikona = new Image("Obrazky/rytierIkona.png",0,0);
    }

    /**
     * Metóda okrem nastavenia miesta, určuje aj či rytier oddychuje (na určenie použiteľných kontextových akcií) a spravuje umiestnenie a vyditeľnosť ikony na mape
     * @param miesto - určí kde sa rytier momentálne nachádza
     */
    public void zmenUmiestnenie(Miesto miesto) {
        this.umiestnenie = miesto;
        if (this.umiestnenie instanceof Doska) {
            this.oddychuje = true;
            if (this.isTrenujeMaHrac()) {
                this.skryIkonuRytiera();
                this.hrac.premiestniRytiera(this, miesto);
            }

        } else {
            this.oddychuje = false;
            if (this.isTrenujeMaHrac()) {
                if (miesto instanceof Arena) {
                    this.ikona.moveVertical(miesto.getSuradnice()[3] - 84);
                    this.ikona.moveHorizontal(miesto.getSuradnice()[0] + 60);
                } else {
                    this.ikona.moveVertical(miesto.getSuradnice()[3] - 75);
                    this.ikona.moveHorizontal(miesto.getSuradnice()[0]);
                }
                this.hrac.premiestniRytiera(this, miesto);
                this.zobrazIkonuRytiera();
            }

        }
    }

    public void pridajSilu() {
        this.sila++;
        if (this.silaText != null) {
            this.silaText.makeInvisible();
            this.silaText.changeText("" + this.sila);
            this.silaText.makeVisible();
        }
    }
    public void pridajSilu(int pocet) {
        this.sila += pocet;
        if (this.silaText != null) {

            this.silaText.makeInvisible();
            this.silaText.changeText("" + this.sila);
            this.silaText.makeVisible();
        }
    }

    public void pridajPopularitu() {
        this.popularita++;

        if (this.popularitaText != null) {
            this.popularitaText.makeInvisible();
            this.popularitaText.changeText("" + this.popularita);
            this.popularitaText.makeVisible();
        }

    }
    public void pridajPopularitu(int pocet) {
        this.popularita += pocet;
        if (this.popularitaText != null) {

            this.popularitaText.makeInvisible();
            this.popularitaText.changeText("" + this.popularita);
            this.popularitaText.makeVisible();
        }
    }
    public void pridajSkusenost() {
        this.skusenosti++;
        switch (this.skusenosti) {
            case 1 -> {
                this.rank = Rank.NOVICE;
                this.rankObrazok = new Image("Obrazky/Novice.png", this.suradnice[0] + 175, this.suradnice[2] + 15);
                this.rankObrazok.makeVisible();
            }
            case 4 -> {
                this.rank = Rank.JOURNEYMAN;
                this.rankObrazok.changeImage("Obrazky/Journey.png");
            }
            case 9 -> {
                this.rank = Rank.CHAMPION;
                this.rankObrazok.changeImage("Obrazky/Champ.png");
            }
            case 14 -> {
                this.rank = Rank.LEGEND;
                this.rankObrazok.changeImage("Obrazky/Legend.png");
            }
        }
    }
    public Rank getRank() {
        return this.rank;
    }
    public void nasadPredmet(Predmet p) {
        var sebavedomi = this.getHrac().getMapa().getEfekt() != Efekt.SEBAVEDOMI_RYTIERI;
        if (sebavedomi || (!sebavedomi && this.getHrac().getMapa().getStavEfektu() == StavEfektu.BEZ_NEVYHODY)) {
            if (p.nasadNaRytiera(this)) {
                this.hrac.odstranPredmet(p);
                this.hrac.getDoska().nastavujePredmet(null);
            }
        }
    }

    public void zlozPredmet(Predmet p) {

            if (p == null) {
                this.getHrac().getManazerEventov().pozastavHru();
                JOptionPane.showMessageDialog(null, "Rytier " + this.getMeno() + " nie je v tomto slote vybaveni", "Chyba", JOptionPane.ERROR_MESSAGE);
            } else {
                if (p.zlozZRytiera()) {
                        this.hrac.pridajPredmet(p);
                }
            }
    }
    /**
     * Metódu využíva mapa aby vedela na ktoré akcie má reagovať
     * @return - ArrayList kontextových akcií použiteľných na základe rytierovho stavu a umiestnenia
     */
    public ArrayList<KontextovaAkcia> pouzitelneSpravy() {
        ArrayList<KontextovaAkcia> pouzitelneSpravy = new ArrayList<>();

        if (this.oddychuje) {
            for (int i = 0; i < 7; i++) {
                pouzitelneSpravy.add(this.vsetkyAkcie.get(i));
            }
            if (this instanceof PokrocilyRytier) {
                pouzitelneSpravy.add(this.vsetkyAkcie.get(8));
            }
            if (this instanceof LegendarnyRytier lR && lR.getSchopnost() != Schopnost.EXKALIBER) {
                pouzitelneSpravy.add(this.vsetkyAkcie.get(9));
            }
        } else if (!(this.umiestnenie instanceof Obchod)){
            if (!(this.umiestnenie instanceof Arena arena && arena.getStav() == StavAreny.VYHODNOTENIE)) {
                pouzitelneSpravy.add(this.vsetkyAkcie.get(7));
                pouzitelneSpravy.add(this.vsetkyAkcie.get(4));
                pouzitelneSpravy.add(this.vsetkyAkcie.get(5));
                if (this instanceof PokrocilyRytier) {
                    pouzitelneSpravy.add(this.vsetkyAkcie.get(8));
                }
                if (this instanceof LegendarnyRytier lR && lR.getSchopnost() != Schopnost.EXKALIBER) {
                    pouzitelneSpravy.add(this.vsetkyAkcie.get(9));
                }
            }
        }
        if (this.umiestnenie instanceof Obchod) {
            pouzitelneSpravy.add(this.vsetkyAkcie.get(0));
        }

        return pouzitelneSpravy;
    }
    public void pridajSkore(int kolko, boolean arena, Efekt efekt) {
        if (arena) {
            if (this instanceof PokrocilyRytier pR && pR.getSchopnost() == Schopnost.GLADIATOR) {
                kolko = (int) Math.ceil(kolko * 1.15);
            }
        }
        if (this.hrac == null) {
            this.skore += kolko;
        } else {
            if (efekt == Efekt.KRATKODOBA_INVESTICIA && this.hrac.getMapa().getStavEfektu() != StavEfektu.VYLEPSENE) {
                kolko = (int) Math.ceil(kolko * 1.5);
            } else if (efekt == Efekt.KRATKODOBA_INVESTICIA && this.hrac.getMapa().getStavEfektu() == StavEfektu.VYLEPSENE) {
                kolko = (int) Math.ceil(kolko * 1.8);
            }
            this.hrac.zmenStavPenazi(kolko);
        }
    }

    public int getSkore() {
        return this.skore + this.getCena();
    }
    public int[] getSuradniceTrinket() {
        return this.suradniceTrinket;
    }
    public int[] getSuradniceVyzbroj() {
        return this.suradniceVyzbroj;
    }

    public MiestoNaPredmet<Trinket> getTrinket() {
        return this.trinket;
    }
    public MiestoNaPredmet<Vyzbroj> getVyzbroj() {
        return this.vyzbroj;
    }
    public Hrac getHrac() {
        return this.hrac;
    }

    public int getSkusenosti() {
        return this.skusenosti;
    }

    public void vyrobPeniaze() {
        var suma = (this.getPopularita(this.hrac.getMapa().getArena()) * 4) + (this.getSila(this.hrac.getMapa().getArena()) * 3);
        this.hrac.zmenStavPenazi(suma);
    }

    public void pridajPocetTurnajov() {
        this.pocetTurnajov++;
        if (this.getHrac() != null) {
            if (this.getHrac().getMapa().getEfekt() == Efekt.KRATKODOBA_INVESTICIA) {
                if (this.pocetTurnajov > 2 && this.hrac != null) {
                    this.hrac.predajRytiera(this);
                }
            }
        }
    }
    public void setBolVCechu(boolean bool) {
        this.bolVCechu = bool;
    }
    public boolean getBolVCechu() {
        return this.bolVCechu;
    }

    public void setCas(int cas) {
        this.cas = cas;
    }

    public int getCas() {
        return this.cas;
    }

    protected void setRaritaText(String s) {
        this.raritaText = s;
    }

    public int getOdmena() {
        return this.odmena;
    }

    public void setOdmena() {
        this.odmena++;
    }
    public void zmenObrazok(String obrazokCesta) {
        this.obrazok.changeImage(obrazokCesta);
    }
}
