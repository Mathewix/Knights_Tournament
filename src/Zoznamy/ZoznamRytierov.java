package Zoznamy;

import Rytieri.LegendarnyRytier;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import Rytieri.Schopnsoti.Schopnost;

import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda slúži ako zoznam rytierov, kt. sa môžu predávať v obchode a náhodných výber z týchto rytierov
 */
public class ZoznamRytierov {
    private ArrayList<ObycajnyRytier> zoznam;
    private ArrayList<PokrocilyRytier> pokrocilyZoznam;
    private final ArrayList<LegendarnyRytier> legendarnyZoznam;

    /**
     * Inicializácia Obycajnych Rytierov a Pokrocilych Rytierov
     */
    public ZoznamRytierov() {
        this.zoznam = new ArrayList<>();
        this.zoznam.add(new ObycajnyRytier("Aelfric"));
        this.zoznam.add(new ObycajnyRytier("Clarice"));
        this.zoznam.add(new ObycajnyRytier("Darian"));
        this.zoznam.add(new ObycajnyRytier("Edith"));
        this.zoznam.add(new ObycajnyRytier("Finnian"));
        this.zoznam.add(new ObycajnyRytier("Gregor"));
        this.zoznam.add(new ObycajnyRytier("Ianah"));
        this.zoznam.add(new ObycajnyRytier("Jocelyn"));
        this.zoznam.add(new ObycajnyRytier("Percival"));
        this.zoznam.add(new ObycajnyRytier("Vincent"));
        this.pokrocilyZoznam = new ArrayList<>();
        this.pokrocilyZoznam.add(new PokrocilyRytier("Azazel", Schopnost.CHLAP_JAK_HORA));
        this.pokrocilyZoznam.add(new PokrocilyRytier("Berthold", Schopnost.DOBRODRUH));
        this.pokrocilyZoznam.add(new PokrocilyRytier("Cristoph", Schopnost.GLADIATOR));
        this.pokrocilyZoznam.add(new PokrocilyRytier("Dezider", Schopnost.NATURALISTA));
        this.pokrocilyZoznam.add(new PokrocilyRytier("Ezechiel", Schopnost.PAN_PRIPRAVENY));
        this.pokrocilyZoznam.add(new PokrocilyRytier("Ferdinand", Schopnost.PRIRODZENY_TALENT));
        this.pokrocilyZoznam.add(new PokrocilyRytier("Godfrey", Schopnost.TEN_ATLETICKY));
        this.pokrocilyZoznam.add(new PokrocilyRytier("Herald", Schopnost.TEN_DOSLEDNY));
        this.pokrocilyZoznam.add(new PokrocilyRytier("Ignace", Schopnost.TEN_OBLUBENY));
        this.pokrocilyZoznam.add(new PokrocilyRytier("Joseph", Schopnost.RODENA_HVIEZDA));
        this.legendarnyZoznam = new ArrayList<>();
        this.legendarnyZoznam.add(new LegendarnyRytier("King Arthur", Schopnost.EXKALIBER, 8, 12));
        this.legendarnyZoznam.add(new LegendarnyRytier("Hannibal", Schopnost.ZIJUCA_LEGENDA, 14, 16));
        this.legendarnyZoznam.add(new LegendarnyRytier("Jean d'Arc", Schopnost.BOZI_BOJOVNIK, 1, 1));
        this.legendarnyZoznam.add(new LegendarnyRytier("d'Artagnan", Schopnost.JEDEN_ZA_VSETKYCH, 8, 7));
        this.legendarnyZoznam.add(new LegendarnyRytier("Robin Hood", Schopnost.ANGLICKY_KOMUNIZMUS, 6, 16));
    }

    /**
     *
     * @return - vráti náhodného Obycanjneho Rytiera zo zoznamu
     */
    public ObycajnyRytier getNahodnyRytier() {
        Random r = new Random();
        var nahodnyRytier = this.zoznam.get(r.nextInt(0, this.zoznam.size()));
        this.zoznam.remove(nahodnyRytier);
        return nahodnyRytier;
    }

    /**
     *
     * @return - vráti náhodného Pokrocileho Rytiera zo zoznamu
     */
    public ObycajnyRytier getNahodnyPokrociliRytier() {
        Random r = new Random();
        var nahodnyRytier = this.pokrocilyZoznam.get(r.nextInt(0, this.pokrocilyZoznam.size()));
        this.pokrocilyZoznam.remove(nahodnyRytier);
        return nahodnyRytier;
    }
    public ObycajnyRytier getNahodnyLegendarnyRytier() {
        Random r = new Random();
        var nahodnyRytier = this.legendarnyZoznam.get(r.nextInt(0, this.legendarnyZoznam.size()));
        this.legendarnyZoznam.remove(nahodnyRytier);
        return nahodnyRytier;
    }

    /** public void vratRytiera(ObycajnyRytier obycajnyRytier) {
        this.zoznam.add(obycajnyRytier);
    }
     */

}
