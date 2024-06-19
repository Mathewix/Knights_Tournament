package Lokality;

import HernyBalik.Efekt;
import HernyBalik.MaAktivitu;
import HernyBalik.ManazerEventov;
import Hraci.Hrac;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import Rytieri.Schopnsoti.Schopnost;

/**
 * Cvičisko zvýši rytierovi silu ak v ňom rytier zostane do konca akcie
 */
public class Cvicisko extends TreningoveMiesto implements Miesto{


    private final int MIN_X = 355;
    private final int MIN_Y = 450;
    private final int MAX_X = this.MIN_X + 165;
    private final int MAX_Y = this.MIN_Y + 150;



    public Cvicisko(ManazerEventov manazerEventov, Hrac hrac, int Yposun) {
        super(manazerEventov, hrac, 216, 475 + Yposun*40);

    }


    public int[] getSuradnice() {
        return new int[]{this.MIN_X, this.MAX_X, this.MIN_Y, this.MAX_Y};
    }


    @Override
    public int getDobaTrvania(MaAktivitu maAktivitu) {
        if (super.getManazerEventov().getMapa().getEfekt() != Efekt.SLAVNOSTI) {
            return getDobaTrvaniaCvicisko();
        } else {
            return getDobaTrvaniaKrcma();
        }
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
