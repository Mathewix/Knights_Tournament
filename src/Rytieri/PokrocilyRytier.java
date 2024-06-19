package Rytieri;

import HernyBalik.Mapa;
import Lokality.Arena;
import Lokality.Miesto;
import Predmety.MiestoNaPredmet;
import Predmety.Trinket;
import Rytieri.Schopnsoti.Schopnost;
import fri.shapesge.Image;

import java.util.Random;

/**
 * Trieda, ktorá dedí po Obyčajnom rytierovi.
 * Pokrocily rytier má navyše miesto na ďalší trinket a má svoji schopnosť, kt. v niečom rytiera zlepšuje
 * oproti obyčajnému rytierovi
 */

public class PokrocilyRytier extends ObycajnyRytier{
    private int[] suradniceTrinket2;
    private final Schopnost schopnost;
    private Image schopnostObrazok;
    private final MiestoNaPredmet<Trinket> trinket2;
    private Image miestoNaTrinket2;
    private int skusenosti;
    private String raritaText;

    public PokrocilyRytier(String meno, Schopnost schopnost) {
        super(meno);
        Random r = new Random();
        var sila = r.nextInt(6);
        super.pridajSilu(sila);
        super.pridajPopularitu(6 - sila);
        this.schopnost = schopnost;
        this.trinket2 = new MiestoNaPredmet<>();
        this.raritaText = "Obrazky/pokrocily.png";
    }
    public PokrocilyRytier(String meno, Schopnost schopnost, int sila, int popularita) {
        super(meno);
        super.sila = sila;
        super.popularita = popularita;
        this.schopnost = schopnost;
        this.trinket2 = new MiestoNaPredmet<>();
        this.raritaText = "Obrazky/pokrocily.png";
    }

    public Image getMiestoNaTrinket2() {
        return this.miestoNaTrinket2;
    }

    public MiestoNaPredmet<Trinket> getTrinket2() {
        return this.trinket2;
    }
    public void setMiestoNaTrinket2(Image miestoNaTrinket2) {
        this.miestoNaTrinket2 = miestoNaTrinket2;
    }
    public void setRaritaText(String s) {
        this.raritaText = s;
    }

    public void kartaRytiera(int suradnicaX, int suradnicaY, Mapa mapa, boolean prehlad, boolean zobraz) {
        super.setRaritaText(this.raritaText);
        super.kartaRytiera(suradnicaX, suradnicaY, mapa, prehlad, zobraz);

    }

    @Override
    public int getCena() {
        return super.getCena() + this.schopnost.getCena() + 5;
    }

    @Override
    public void pridajSkusenost() {
        this.skusenosti++;
        switch (this.skusenosti) {
            case 1 -> {
                this.rank = Rank.NOVICE;
                super.rankObrazok = new Image("Obrazky/Novice.png", this.getSuradnice()[0] + 175, this.getSuradnice()[2] + 15);
                super.rankObrazok.makeVisible();
            }
            case 3 -> {
                this.rank = Rank.JOURNEYMAN;
                this.rankObrazok.changeImage("Obrazky/Journey.png");
            }
            case 6 -> {
                this.rank = Rank.CHAMPION;
                this.rankObrazok.changeImage("Obrazky/Champ.png");
            }
            case 9 -> {
                this.rank = Rank.LEGEND;
                this.rankObrazok.changeImage("Obrazky/Legend.png");
            }
        }
    }
    public void pridajSkusenost(int pocet) {
        this.skusenosti += pocet;
    }

    public Schopnost getSchopnost() {
        return this.schopnost;
    }

    @Override
    public int getSila(Miesto miesto) {
        if (miesto instanceof Arena a) {
            if (this.schopnost == Schopnost.CHLAP_JAK_HORA) {
                return super.getSila(a) + 4;
            } else if (this.schopnost == Schopnost.PRIRODZENY_TALENT) {
                return super.getSila(a) + 2;
            } else if (this.schopnost == Schopnost.NATURALISTA) {
                if (super.getVyzbroj() == null && super.getTrinket() == null && this.trinket2 == null) {
                    return super.getSila(a) + 3;
                }
            } else if (this.schopnost == Schopnost.PAN_PRIPRAVENY) {
                if (super.getVyzbroj() != null && super.getTrinket() != null && this.trinket2 != null) {
                    return super.getSila(a) + 3;
                }
            }
        }
        return super.getSila(miesto);
    }

    @Override
    public int getPopularita(Miesto miesto) {
        if (miesto instanceof Arena a) {

            if (this.schopnost == Schopnost.RODENA_HVIEZDA) {
                return super.getPopularita(a) + 4;
            } else if (this.schopnost == Schopnost.PRIRODZENY_TALENT) {
                return super.getPopularita(a) + 2;
            } else if (this.schopnost == Schopnost.NATURALISTA) {
                if (this.getVyzbroj() == null && this.getTrinket() == null && this.trinket2 == null) {
                    return super.getPopularita(a) + 3;
                }
            } else if (this.schopnost == Schopnost.PAN_PRIPRAVENY) {
                if (this.getVyzbroj() != null && this.getTrinket() != null && this.trinket2 != null) {
                    return super.getPopularita(a) + 3;
                }
            }
        }
        return super.getPopularita(miesto);
    }

    @Override
    public int getSkusenosti() {
        return super.getSkusenosti() + 3;
    }

    public int[] getSuradniceTrinket2() {
        return this.suradniceTrinket2;
    }
    public void setSuradniceTrinket2(int[] suradnice) {
        this.suradniceTrinket2 = suradnice;
    }

    public void setSchopnostObrazok(Image schopnostObrazok) {
        this.schopnostObrazok = schopnostObrazok;
    }

    public Image getSchopnostObrazok() {
        return this.schopnostObrazok;
    }
}
