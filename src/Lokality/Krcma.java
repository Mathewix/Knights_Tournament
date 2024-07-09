package Lokality;

import HernyBalik.Efekt;
import HernyBalik.MaAktivitu;
import HernyBalik.ManazerEventov;
import Hraci.Hrac;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import Rytieri.Schopnsoti.Schopnost;

/**
 * Krcma zvýši rytierovi popularitu ak v nej rytier zostane do konca akcie
 */
public class Krcma extends TreningoveMiesto implements Miesto{
    private final int MIN_X = 305;
    private final int MAX_X = this.MIN_X + 215;
    private final int MIN_Y = 105;
    private final int MAX_Y = this.MIN_Y + 200;
    public Krcma(ManazerEventov manazerEventov, Hrac hrac, int Yposun) {
        super(manazerEventov, hrac, 180, 180 + Yposun*40);
    }


    public int[] getSuradnice() {
        var suradnice = new int[]{this.MIN_X, this.MAX_X, this.MIN_Y, this.MAX_Y};
        return suradnice;
    }

    @Override
    public int getDobaTrvania(MaAktivitu maAktivitu) {
        return getDobaTrvaniaKrcma();
    }


    @Override
    public boolean pridajRytiera(ObycajnyRytier rytier) {
        if (this.rytier == null) {
            this.rytier = rytier;
            this.rytier.zmenUmiestnenie(this);
            this.zacniUdalost();
            return true;
        }
        return false;
    }


}
