package Predmety;

/**
 * Generická trieda slúžiaca ako mieste do ktorého si môže rytier nasadiť predmet daného typu
 * @param <T> - Potomkovia Predmetu
 */
public class MiestoNaPredmet<T> {
    private T predmet;


    // Konštruktor triedy
    public MiestoNaPredmet() {
        this.predmet = null; // inicializácia premennej na null
    }

    // Metóda pre vloženie predmetu
    public void put(T item) {
        this.predmet = item;
    }

    // Metóda pre získanie predmetu
    public T get() {
        return this.predmet;
    }

    // Metóda pre overenie, či je box prázdny
    public boolean isEmpty() {
        return this.predmet == null;
    }
    public void remove() {this.predmet = null;}
}
