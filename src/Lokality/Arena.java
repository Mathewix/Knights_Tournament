package Lokality;

import HernyBalik.*;
import Rytieri.LegendarnyRytier;
import Rytieri.ObycajnyRytier;

import javax.swing.*;
import java.util.*;

import Hraci.Hrac;
import Rytieri.PokrocilyRytier;
import Rytieri.Schopnsoti.Schopnost;
import Zoznamy.ZoznamSuperovVArene;
import Zoznamy.ZoznamVyhier;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.Text;

/**
 * Trieda slúžiaca na vyhodnotenie turnajov a dávanie cien rytierom v nich
 * slúži ako miesto, kde hráči získavajú peniaze
 */
public class Arena implements Miesto{
    private final ArrayList<ObycajnyRytier> zoznamRytierov;
    private final ZoznamSuperovVArene superi;
    private int cyklus;
    private int cisloTurnaja;
    private StavAreny stav;
    private final Image otvorena;
    private final Image vyhodnotArenu;
    private final ArrayList<Text> menaRytierov;
    private final ArrayList<Text> statyRytierov;
    private final ArrayList<Text> odmenyRytierov;
    private ManazerEventov manazerEventov;
    private final int MIN_X = 630;
    private final int MIN_Y = 305;
    private final Arena miesto = this;
    private final Hrac hrac;
    private ZoznamVyhier zoznamVyhier;
    private final int MAX_X = this.MIN_X + 195;
    private final int MAX_Y = this.MIN_Y + 180;

    public Arena(int cyklus, Hrac hrac) {
        this.cyklus = cyklus;
        this.zoznamRytierov = new ArrayList<>();
        this.stav = StavAreny.ZATVORENA;
        this.zoznamVyhier = new ZoznamVyhier();
        this.hrac = hrac;
        this.cisloTurnaja = 1;
        this.superi = new ZoznamSuperovVArene();
        this.menaRytierov = new ArrayList<>();
        this.statyRytierov = new ArrayList<>();
        this.odmenyRytierov = new ArrayList<>();
        this.otvorena = new Image("Obrazky/otvorenaArena.png",535,320);
        this.vyhodnotArenu = new Image("Obrazky/vysledokTurnaja.png", 450, 200);
        var arena = new Image("Obrazky/arena.png", 618, 296);
        arena.makeVisible();
    }
    public Arena getMiesto() {
        return this.miesto;
    }
    public int getCyklus() {
        return this.cyklus;
    }
    public void setManazerEventov(ManazerEventov manazerEventov) {
        this.manazerEventov = manazerEventov;
    }

    /**
     * Ak je Arena otvorená, pridá do zoznamu rytiera
     * @param rytier - rytier, kt. má pridať
     */
    public void pridajRyteraHraca(ObycajnyRytier rytier) {
        if (this.stav == StavAreny.REGISTRACIA) {
            if (!this.zoznamRytierov.contains(rytier)) {
                this.zoznamRytierov.add(rytier);
                rytier.zmenUmiestnenie(this);
            }

        } else {
            rytier.getHrac().getManazerEventov().pozastavHru();
            JOptionPane.showMessageDialog(null, "Na turnaj  momentalne nemozes prihlasit svojho rytiera", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metoda prida superov na turnaj podla toho ake cisloTurnaja prave je
     */
    public void pridajRytierov() {
        var r = new Random();
        if (this.cisloTurnaja < 4) {
            if (this.cisloTurnaja % 2 == 0) {
                for (int i = 0; i < this.superi.getSuperov().size(); i++) {
                    if (r.nextDouble() < 0.85) {
                        this.zoznamRytierov.add(this.superi.getSuperov().get(i));
                    } else {
                        this.superi.getSuperov().get(i).pridajSilu();
                        this.superi.getSuperov().get(i).pridajPopularitu();
                    }
                    i++;
                }
            } else {
                for (int i = 1; i < this.superi.getSuperov().size(); i++) {
                    if (r.nextDouble() < 0.85) {
                        this.zoznamRytierov.add(this.superi.getSuperov().get(i));
                    } else {
                        this.superi.getSuperov().get(i).pridajSilu();
                        this.superi.getSuperov().get(i).pridajPopularitu();
                    }
                    i++;
                }
            }
        } else {
            for (int i = 0; i < this.superi.getSuperov().size(); i++) {
                if (r.nextDouble() < 0.8) {
                    this.zoznamRytierov.add(this.superi.getSuperov().get(i));
                }
            }
        }
        for (ObycajnyRytier rytier : this.superi.getSuperov()) {
            if (!this.zoznamRytierov.contains(rytier)) {
                if (rytier.getSila(this) > rytier.getPopularita(this)) {
                    rytier.pridajSilu(2);
                } else if (rytier.getSila(this) == rytier.getPopularita(this)) {
                    rytier.pridajSilu();
                    rytier.pridajPopularitu();
                } else {
                    rytier.pridajPopularitu(2);
                }
            }
        }
    }

    public void zmenStav(StavAreny stav) {
        this.stav = stav;
        if (stav == StavAreny.REGISTRACIA) {
            this.otvorena.makeVisible();
        } else {
            this.otvorena.makeInvisible();
        }
    }

    /**
     * Zoradí rytierov podľa ich sily a prvým trom da vyhru (hracovim rytierom hracovi, superom kazdemu rytierovi samostatne)
     * a potom udeli ceny aj za popularitu kazdeho hraca.
     * Metoda taktiez podla poradia oznamy hracovi prve tri miesta turnaja
     */
    public void vyhodnotenieTurnaja() {
        this.menaRytierov.removeAll(this.menaRytierov);
        this.statyRytierov.removeAll(this.statyRytierov);
        this.odmenyRytierov.removeAll(this.odmenyRytierov);
        ArrayList<ObycajnyRytier> vyhrali = new ArrayList<>();
        this.superi.noveKolo(cisloTurnaja);
        if (this.stav == StavAreny.VYHODNOTENIE) {
            var arena = this.getMiesto();
            Collections.sort(this.zoznamRytierov, new Comparator<ObycajnyRytier>() {
                public int compare(ObycajnyRytier k1, ObycajnyRytier k2) {
                    return k2.getSila(arena) - k1.getSila(arena);
                }
            });
            if (this.zoznamRytierov.size() >= 4) {
                for (int i = 0; i < 4; i++) {
                    if (!this.zoznamRytierov.get(i).isTrenujeMaHrac()) {
                        this.zoznamRytierov.get(i).pridajSkore(this.zoznamVyhier.odmenaZaKolo(this.cisloTurnaja, i) * 2, true, this.manazerEventov.getMapa().getEfekt());
                    } else {
                        this.zoznamRytierov.get(i).pridajSkore(this.zoznamVyhier.odmenaZaKolo(this.cisloTurnaja, i), true, this.manazerEventov.getMapa().getEfekt());
                    }
                    this.menaRytierov.add(new Text(this.zoznamRytierov.get(i).getMeno(), 600, 304 + (i * 41)));
                    this.statyRytierov.add(new Text(this.zoznamRytierov.get(i).getSila(this) + " / " + this.zoznamRytierov.get(i).getPopularita(this), 735, 304 + (i * 41)));
                    this.odmenyRytierov.add(new Text(this.zoznamVyhier.odmenaZaKolo(this.cisloTurnaja, i) + "g", 735 , 304 + (i * 41)));
                    vyhrali.add(this.zoznamRytierov.get(i));
                }
            } else {
                for (int i = 0; i < this.zoznamRytierov.size(); i++) {
                    if (!this.zoznamRytierov.get(i).isTrenujeMaHrac()) {
                        this.zoznamRytierov.get(i).pridajSkore(this.zoznamVyhier.odmenaZaKolo(this.cisloTurnaja, i) * 2, true, this.manazerEventov.getMapa().getEfekt());
                    } else {
                        this.zoznamRytierov.get(i).pridajSkore(this.zoznamVyhier.odmenaZaKolo(this.cisloTurnaja, i), true, this.manazerEventov.getMapa().getEfekt());
                    }
                    this.menaRytierov.add(new Text(this.zoznamRytierov.get(i).getMeno(), 600, 304 + (i * 41)));
                    this.statyRytierov.add(new Text(this.zoznamRytierov.get(i).getSila(this) + " / " + this.zoznamRytierov.get(i).getPopularita(this), 735, 304 + (i * 41)));
                    this.odmenyRytierov.add(new Text(this.zoznamVyhier.odmenaZaKolo(this.cisloTurnaja, i) + "g", 735 , 304 + (i * 41)));
                    vyhrali.add(this.zoznamRytierov.get(i));
                }
            }
            for (ObycajnyRytier r: this.zoznamRytierov) {
                if (!r.isTrenujeMaHrac()) {
                    r.pridajSkore(r.getPopularita(arena) * (5 * this.manazerEventov.getFazaHry()), true, this.manazerEventov.getMapa().getEfekt());
                } else if (r.isTrenujeMaHrac()) {
                    r.pridajSkore(r.getPopularita(arena) * (5 + (this.manazerEventov.getFazaHry())), true, this.manazerEventov.getMapa().getEfekt());
                }
                if (r instanceof LegendarnyRytier lR && lR.getSchopnost() == Schopnost.ANGLICKY_KOMUNIZMUS) {
                    for (ObycajnyRytier rytier : vyhrali) {
                        rytier.pridajSkore(-(r.getPopularita(this) * 3), true, this.manazerEventov.getMapa().getEfekt());
                    }
                    lR.pridajSkore(r.getPopularita(this) * 8, true, this.manazerEventov.getMapa().getEfekt());
                } else if (r instanceof LegendarnyRytier lR && lR.getSchopnost() == Schopnost.BOZI_BOJOVNIK) {
                    lR.pridajPocetTurnajov();
                }
            }
            this.vyhodnotArenu.makeVisible();
            for (Text text : this.menaRytierov) {
                text.changeFont("Langar", FontStyle.PLAIN, 22);
                text.makeVisible();
            }
            if (this.manazerEventov.getMapa().getEfekt() == Efekt.KRATKODOBA_INVESTICIA && this.manazerEventov.getMapa().getStavEfektu() != StavEfektu.BEZ_NEVYHODY) {
                for (ObycajnyRytier obycajnyRytier : this.zoznamRytierov) {
                    obycajnyRytier.pridajPocetTurnajov();
                }
            } else if (this.manazerEventov.getMapa().getEfekt() == Efekt.ADRENALIN && this.manazerEventov.getMapa().getStavEfektu() != StavEfektu.BEZ_NEVYHODY) {
                for (ObycajnyRytier obycajnyRytier : this.zoznamRytierov) {
                    if (obycajnyRytier.getHrac() != null) {
                        obycajnyRytier.pridajSilu(-1);
                        obycajnyRytier.pridajPopularitu(-1);
                    }
                }
            }
            this.cisloTurnaja++;
        }
    }

    public void skryVysledok() {
        this.vyhodnotArenu.makeInvisible();
        for (Text text : this.menaRytierov) {
            text.makeInvisible();
        }
        for (Text text : this.statyRytierov) {
            text.makeInvisible();
        }
        for (Text text : this.odmenyRytierov) {
            text.makeInvisible();
        }
    }
    public void zobrazStaty() {
        for (Text text : this.odmenyRytierov) {
            text.makeInvisible();
        }
        for (Text text : this.statyRytierov) {
            text.changeFont("Langar", FontStyle.PLAIN, 22);
            text.makeVisible();
        }
    }
    public void zobrazOdmeny() {
        for (Text text : this.statyRytierov) {
            text.makeInvisible();
        }
        for (Text text : this.odmenyRytierov) {
            text.changeFont("Langar", FontStyle.PLAIN, 22);
            text.makeVisible();
        }
    }
    @Override
    public void vratRytierov(boolean ukoncene, ObycajnyRytier rytier) {
        if (ukoncene && rytier == null) {
            for (ObycajnyRytier r : this.zoznamRytierov) {
                if (r instanceof PokrocilyRytier pR && pR.getSchopnost() == Schopnost.GLADIATOR && pR.getSchopnost() == Schopnost.JEDEN_ZA_VSETKYCH) {
                    pR.pridajSilu();
                    pR.pridajPopularitu();
                }
                if (r.isTrenujeMaHrac()) {
                    r.zmenUmiestnenie(this.hrac.getDoska());
                    System.out.println(r.getPopularita(this));
                }
            }
            this.zoznamRytierov.removeAll(this.zoznamRytierov);
        } else {
            rytier.zmenUmiestnenie(this.hrac.getDoska());
            this.zoznamRytierov.remove(rytier);

        }
    }


    @Override
    public int[] getSuradnice() {
        var suradnice = new int[]{this.MIN_X, this.MAX_X, this.MIN_Y, this.MAX_Y};
        return suradnice;
    }

    /**
     * Zoradi Hraca a Superov podla ich skore
     * @return - zoradeny ArrayList Hraca a Superov
     */
    public ArrayList<Hodnotitelne> vyhodnotenie() {
        ArrayList<Hodnotitelne> hodnotene = new ArrayList<>();
        hodnotene.add(this.hrac);
        hodnotene.addAll(this.superi.getSuperov());
        Collections.sort(hodnotene, new Comparator<Hodnotitelne>() {
            public int compare(Hodnotitelne k1, Hodnotitelne k2) {
                return k2.getSkore() - k1.getSkore();
            }
        });
        return hodnotene;
    }

    public StavAreny getStav() {
        return this.stav;
    }

    public ZoznamVyhier getVyhry() {
        return zoznamVyhier;
    }

}
