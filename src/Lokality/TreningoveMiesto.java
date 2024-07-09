package Lokality;

import HernyBalik.Efekt;
import HernyBalik.MaAktivitu;
import HernyBalik.ManazerEventov;
import HernyBalik.StavEfektu;
import Hraci.Hrac;
import Rytieri.LegendarnyRytier;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import Rytieri.Schopnsoti.Schopnost;
import fri.shapesge.Image;
import fri.shapesge.Rectangle;
import fri.shapesge.Square;

import javax.swing.*;
import java.util.Random;

/**
 * Slúži ako predok Cvicisku, Krcme a Cechu, kt. po určitom čase od príchodu rytiera niečo vykonajú (zvýšia stat rytierovi alebo pridajú predmet hráčovi).
 * Sú tu určené aj samostatné akcie a správanie týchto tried
 */
public abstract class TreningoveMiesto implements MaAktivitu, Miesto {
    protected ObycajnyRytier rytier;
    private final ManazerEventov manazerEventov;
    private final Hrac hrac;
    private int koniecAkcie;
    private int dobaTrvania;
    private final Rectangle progres;
    protected Image sivyObdlznik;

    public TreningoveMiesto(ManazerEventov manazerEventov, Hrac hrac, int x, int y) {
        this.manazerEventov = manazerEventov;
        this.hrac = hrac;
        this.sivyObdlznik = new Image("Obrazky/sivyObdlznik.png" , x, y);
        this.progres = new Rectangle(x + 2, y + 2);
        this.progres.changeSize(0,0);
        this.progres.changeColor("#E88E08");
    }

    public Hrac getHrac() {
        return hrac;
    }

    public int getKoniecAkcie() {
        return this.koniecAkcie;
    }

    public ManazerEventov getManazerEventov() {
        return manazerEventov;
    }

    /**
     * nastaví koniec akcie podľa času v ktorom sa metóda pošle + doba trvania a zároveň pridá udalosť do ManazeraEventov
     */
    public void zacniUdalost() {
        this.koniecAkcie = this.manazerEventov.pridajAkciuTreningovehoMiesta(this);
        this.sivyObdlznik.makeVisible();
        this.progres.makeVisible();
    }
    public void progresRytiera(int cas) {
        this.progres.changeSize(118 * (this.dobaTrvania - (this.koniecAkcie - cas)) / this.dobaTrvania, 24);
    }

    /**
     * Ak sa tu ešte rytier nenachádza, zmením umiestnenie rytiera na príslušnú triedu
     * @param rytier
     */
    protected boolean pridajRytiera(ObycajnyRytier rytier) {
        if (this.rytier == null) {
            this.rytier = rytier;
            this.rytier.zmenUmiestnenie(this);
            this.zacniUdalost();
            return true;
        } else {
            rytier.getHrac().getManazerEventov().pozastavHru();
            JOptionPane.showMessageDialog(null, "Tu sa uz rytier nachadza", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    protected ObycajnyRytier getRytier() {
        return this.rytier;
    }

    /**
     * Určuje dobu trvania aktivity, po ktorej rytier dostane svoju odmenu a vráti sa hráčovi
     * Obyčajný rytier má iné výpočty ako Pokročilý rytier v Cvicisku a Krcme (ďalšie zmeny podla schopností Pokročilých rytierov)
     * @param treningoveMiesto - určuje o akú triedu ide a podla toho určí ako vyrátať dobu trvania
     * @return - vráti dobu trvania pre ďalšie spracovanie ManazeromEventov
     */
    public abstract int getDobaTrvania(MaAktivitu treningoveMiesto);
    public int getDobaTrvaniaCvicisko() {
        if (this.getRytier() != null) {
            var mapa = this.manazerEventov.getMapa();
            var adrenalin = 0;
            if (mapa.getEfekt() == Efekt.ADRENALIN && mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                adrenalin = 10;
            }
            if (this.getRytier() instanceof PokrocilyRytier pR) {
                if (pR.getSchopnost() == Schopnost.TEN_ATLETICKY || pR instanceof LegendarnyRytier) {
                    this.dobaTrvania = 9 + (getRytier().getSila(this) * (4 - (adrenalin / 10))) - (getRytier().getPopularita(this) * 2)
                            - adrenalin  - (this.getRytier().getCviciskoVisits() * 3) - mapa.getCviciskoUpgrade();
                    if (this.dobaTrvania <= 20) {
                        this.dobaTrvania = 20;
                    }
                    return this.dobaTrvania;
                }
                this.dobaTrvania = 20 + (getRytier().getSila(this) * (5 - (adrenalin / 10))) - (getRytier().getPopularita(this) * 3)
                        - adrenalin  - (this.getRytier().getCviciskoVisits() * 4) - mapa.getCviciskoUpgrade();
                if (this.dobaTrvania <= 20) {
                    this.dobaTrvania = 20;
                }
                return this.dobaTrvania;
            }
            this.dobaTrvania = 24 + (getRytier().getSila(this) * (8 - (adrenalin / 10))) - (getRytier().getPopularita(this) * 3)
                    - adrenalin - (this.getRytier().getCviciskoVisits() * 5) - mapa.getCviciskoUpgrade() * 2;
            if (this.dobaTrvania <= 20) {
                this.dobaTrvania = 20;
            }
            return this.dobaTrvania;
        } else {
            return 20;
        }
    }

    public int getDobaTrvaniaKrcma() {
        if (getRytier() != null) {
            var mapa = this.manazerEventov.getMapa();
            var dobrodruzstvo = 0;
            if (mapa.getEfekt() == Efekt.NEZABUDNUTELNE_DOBRODRUZSTVO && mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                dobrodruzstvo = 10;
            }
            if (this.getRytier() instanceof PokrocilyRytier pR) {
                if (pR.getSchopnost() == Schopnost.TEN_OBLUBENY || pR instanceof LegendarnyRytier) {
                    this.dobaTrvania = 12  + (getRytier().getPopularita(this) * (7 - dobrodruzstvo/10)) - (this.manazerEventov.getFazaHry() * 8)
                            - (getRytier().getSila(this) * 4) - dobrodruzstvo;
                    if (this.dobaTrvania <= 20) {
                        this.dobaTrvania = 20;
                    }
                    return this.dobaTrvania;
                }
                this.dobaTrvania = 23  + (getRytier().getPopularita(this) * (9 - dobrodruzstvo/10)) - (this.manazerEventov.getFazaHry() * 8)
                        - (getRytier().getSila(this) * 5) - dobrodruzstvo;
                if (this.dobaTrvania <= 20) {
                    this.dobaTrvania = 20;
                }
                return this.dobaTrvania;
            }
            this.dobaTrvania = 29 + (getRytier().getPopularita(this) * (13 - dobrodruzstvo/5)) - (this.manazerEventov.getFazaHry() * 8)
                    - (getRytier().getSila(this) * 5) - dobrodruzstvo;
            if (this.dobaTrvania <= 20) {
                this.dobaTrvania = 20;
            }
            return this.dobaTrvania;
        } else {
            return 20;
        }
    }
    public void setDobaTrvania(int dobaTrvania) {
        this.dobaTrvania = dobaTrvania;
    }

    /**
     * Ak sa na konci akcie v TreningovejMiestosti stále nachádza rytier dá mu odmenu
     * pre Pokročilých rytierov so správnou schopnosťou sú niektoré odmeny pozmenené
     */
    public void vykonajAktivitu() {
        var mapa = this.manazerEventov.getMapa();
         if (this instanceof Cvicisko cvicisko) {
            if (getRytier() != null) {
                if (mapa.getEfekt() != Efekt.ADRENALIN) {
                    this.getRytier().pridajSilu();
                } else {
                    this.getRytier().pridajSilu(2);
                }
                if (mapa.getEfekt() == Efekt.TIMOVA_PRACA && mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                    mapa.upgradeCvicisko();
                }
                cvicisko.vratRytierov(true, cvicisko.getRytier());
            }
        } else if (this instanceof Krcma krcma) {
            if (getRytier() != null) {
                if (mapa.getEfekt() == Efekt.SLAVNOSTI) {
                    if (mapa.getStavEfektu() == StavEfektu.VYLEPSENE){
                        this.getRytier().pridajPopularitu(2);
                    }
                } else {
                    this.getRytier().pridajPopularitu();
                }
                if (mapa.getEfekt() == Efekt.NEZABUDNUTELNE_DOBRODRUZSTVO) {
                    this.getRytier().pridajSkusenost(false);
                }
                krcma.vratRytierov(true, krcma.getRytier());
            }
        }
    }

    /**
     * Vráti rytiera hráčovi aby ho mohol poslať niekam inam
     * @param ukoncena - určuje či si ho hráč vypítal sám (false) a akcia sa odstráni z ManazeraEventov
     *                 alebo či sa akcia ukončila (true) a rytier sa sám vrátil
     */

    public void vratRytierov(boolean ukoncena, ObycajnyRytier rytier) {
        if (!ukoncena) {
            this.manazerEventov.odstranAkciuTreningovehoMiesta(this);
        }
        this.rytier.zmenUmiestnenie(this.hrac.getDoska());
        this.rytier = null;
        this.sivyObdlznik.makeInvisible();
        this.progres.changeSize(0,28);
        this.progres.makeInvisible();
    }
}
