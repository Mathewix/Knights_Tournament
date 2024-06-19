package Lokality;

import HernyBalik.Efekt;
import HernyBalik.MaAktivitu;
import HernyBalik.ManazerEventov;
import HernyBalik.StavEfektu;
import Hraci.Hrac;
import Predmety.Artefakt;
import Rytieri.LegendarnyRytier;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import Rytieri.Rank;
import Rytieri.Schopnsoti.Schopnost;
import Zoznamy.ZoznamPredmetov;

import java.util.Random;

/**
 * Cech dá hráčovi predmet ak v ňom rytier zostane do konca akcie
 */
public class Cech extends TreningoveMiesto implements Miesto{
    private final int MIN_X = 600;
    private final int MAX_X = this.MIN_X + 180;
    private final int MIN_Y = 160;
    private final int MAX_Y = this.MIN_Y + 90;
    private final ZoznamPredmetov zoznamPredmetov;


    public Cech(ManazerEventov manazerEventov, Hrac hrac) {
        super(manazerEventov, hrac, 578, 134);
        this.zoznamPredmetov = new ZoznamPredmetov();
    }




    public int[] getSuradnice() {
        var suradnice = new int[]{this.MIN_X, this.MAX_X, this.MIN_Y, this.MAX_Y};
        return suradnice;
    }

    @Override
    public boolean pridajRytiera(ObycajnyRytier rytier) {
        super.pridajRytiera(rytier);
        return false;
    }




    @Override
    public int getDobaTrvania(MaAktivitu maAktivitu) {
        if (this.getRytier() != null) {
            if (this.getRytier() instanceof PokrocilyRytier pR) {
                if (pR.getSchopnost() == Schopnost.TEN_DOSLEDNY || pR instanceof LegendarnyRytier) {
                    var doba = 86 - (this.getRytier().getSkusenosti() * 3);
                    if (doba < 50) {
                        doba = 50;
                    }
                    super.setDobaTrvania(doba);
                    return doba;
                }
            }
            var doba = 136 - (this.getRytier().getSkusenosti() * 4);
            if (doba < 86) {
                doba = 86;
            }
            super.setDobaTrvania(doba);
            return doba;
        } else {
            return 0;
        }
    }

    @Override
    public void vykonajAktivitu() {
        if (this.getRytier() != null) {
            if (this.getRytier() instanceof PokrocilyRytier pR) {
                if (pR.getSchopnost() == Schopnost.DOBRODRUH) {
                    var r = new Random();
                    if (r.nextDouble() >= 0.6) {
                        pR.pridajPopularitu();
                    } else {
                        pR.pridajSilu();
                    }
                }
            }
            var predmet = this.zoznamPredmetov.dajNahodnuOdmenu(super.getManazerEventov().getFazaHry());
            var mapa = this.getManazerEventov().getMapa();
            if (mapa.getEfekt() == Efekt.OSUDOVA_VYPRAVA && mapa.getStavEfektu() != StavEfektu.VYLEPSENE) {
                predmet.vylepsiPredmet(4);
            } else if (mapa.getEfekt() == Efekt.OSUDOVA_VYPRAVA && mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                predmet = new Artefakt(5);
            }
            if (this.getRytier().getRank() == Rank.NOVICE) {
                this.getRytier().pridajSkore(5, false, super.getManazerEventov().getMapa().getEfekt());
            } else if (this.getRytier().getRank() == Rank.JOURNEYMAN) {
                this.getRytier().pridajSkore(10, false, super.getManazerEventov().getMapa().getEfekt());
            } else if (this.getRytier().getRank() == Rank.CHAMPION) {
                this.getRytier().pridajSkore(20, false, super.getManazerEventov().getMapa().getEfekt());
                predmet = this.zoznamPredmetov.dajNahodnuOdmenu(super.getManazerEventov().getFazaHry() + 1);
                if (mapa.getEfekt() != Efekt.OSUDOVA_VYPRAVA) {
                    predmet.vylepsiPredmet(1);
                }
            } else if (this.getRytier().getRank() == Rank.LEGEND) {
                this.getRytier().pridajSkore(25, true, super.getManazerEventov().getMapa().getEfekt());
                Random r = new Random();
                if (mapa.getEfekt() != Efekt.OSUDOVA_VYPRAVA) {
                    if (r.nextDouble() < 0.65 - (0.005 * this.getRytier().getSkusenosti())) {
                        predmet = this.zoznamPredmetov.dajNahodnuOdmenu(super.getManazerEventov().getFazaHry() + 1);
                        predmet.vylepsiPredmet(2);
                    } else {
                        predmet = new Artefakt(5);
                    }
                }

            }
            super.getHrac().pridajPredmet(predmet);
            this.getRytier().pridajSkusenost();
            if (this.getManazerEventov().getMapa().getEfekt() == Efekt.OSUDOVA_VYPRAVA && this.getManazerEventov().getMapa().getStavEfektu() != StavEfektu.BEZ_NEVYHODY) {
                this.getRytier().setBolVCechu(true);
            }
            this.vratRytierov(true, this.getRytier());
            this.zoznamPredmetov.dopln();
        }
    }
}
