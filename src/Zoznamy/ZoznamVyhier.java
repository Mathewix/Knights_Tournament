package Zoznamy;

import HernyBalik.Efekt;
import HernyBalik.StavEfektu;

import java.util.ArrayList;

/**
 * Trieda slúži ako zoznam výhier, ktoré udeľuje Arena rytierom na prvých pozíciach
 */
public class ZoznamVyhier {
    private ArrayList<Integer[]> vyhry;
    private Efekt efekt;
    private StavEfektu stavEfektu;

    /**
     * Inicializácia základných peňažných výhier
     */
    public ZoznamVyhier() {
        this.vyhry = new ArrayList<>();
        this.vyhry.add(new Integer[]{110, 70, 60, 30});
        this.vyhry.add(new Integer[]{130, 90, 70, 40});
        this.vyhry.add(new Integer[]{180, 120, 90, 50});
        this.vyhry.add(new Integer[]{220, 150, 120, 60});
        this.vyhry.add(new Integer[]{260, 180, 150, 70});
        this.vyhry.add(new Integer[]{320, 220, 180, 80});
        this.vyhry.add(new Integer[]{400, 280, 220, 100});
        this.vyhry.add(new Integer[]{500, 350, 280, 200});
        this.vyhry.add(new Integer[]{600, 400, 320, 200});
    }

    public void setEfekt(Efekt efekt) {
        this.efekt = efekt;
    }
    public void setStavEfektu(StavEfektu stavEfektu) {
        this.stavEfektu = stavEfektu;
    }
    /**
     * Metóda na základie kola a poradie rytiera určí akú výhru mu má aréna dať
     * @param kolo - kolo turnaja
     * @param poradie - poradie rytiera
     * @return
     */
    public int odmenaZaKolo(int kolo, int poradie) {
        if (kolo >= 1 && kolo <= this.vyhry.size()) {
            if (this.efekt == Efekt.SLAVNOSTI && this.stavEfektu != StavEfektu.BEZ_NEVYHODY) {
                return (int) (this.vyhry.get(kolo - 1)[poradie] * 1.5);
            } else if (this.efekt == Efekt.TIMOVA_PRACA && this.stavEfektu != StavEfektu.BEZ_NEVYHODY) {
                return (int) (this.vyhry.get(kolo - 1)[poradie] * 0.5);
            } else {
                return this.vyhry.get(kolo - 1)[poradie];
            }
        } else {
            return 0;
        }
    }
}
