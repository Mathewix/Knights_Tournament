package HernyBalik;

import Lokality.Miesto;
import Rytieri.SpravyRytierovi.KontextovaAkcia;

import java.util.ArrayList;

/**
 * Interface spájajúci Triedy, ktoré sa dajú predávať obchodom (Predmety a rytieri)
 */
public interface Predavatelne {
    void zobrazKartu();
    void skryKartu();
    int getCena();

    int[] getSuradnice();
    Miesto getUmiestnenie();
    void zmenUmiestnenie(Miesto miesto);
    ArrayList<KontextovaAkcia> pouzitelneSpravy();
}
