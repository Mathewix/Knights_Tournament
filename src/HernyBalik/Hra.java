package HernyBalik;

import Hraci.Hrac;
import Predmety.Artefakt;
import Predmety.Vyzbroj;
import Rytieri.LegendarnyRytier;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import Rytieri.Schopnsoti.Schopnost;

public class Hra {
    public Hra() {
        Hrac hrac = new Hrac(20);
        ManazerEventov manazerEventov = new ManazerEventov(4800);
        new Mapa(hrac, manazerEventov);
        ObycajnyRytier r = new ObycajnyRytier("Justus", 5, 5);
        ObycajnyRytier r2 = new ObycajnyRytier("Justus", 5, 5);
        PokrocilyRytier r1 = new PokrocilyRytier("aBubu",Schopnost.DOBRODRUH, 2, 2);
        hrac.pridajRytiera(r);
        hrac.pridajRytiera(r1);
        hrac.pridajRytiera(r2);
    }

}
