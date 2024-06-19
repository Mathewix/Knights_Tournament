package HernyBalik.Navod;

public enum StavPrirucky {
    VYPNUTA,
    PRIRUCKA,
    OVLADANIE(0),
    ZAKLADY_1(1),
    ZAKLADY_2(2),
    ZAKLADY_3(3),
    FAZY(4),
    SCHOPNOSTI(5),
    RANKY(6);

    private final int poradieObrazka;
    StavPrirucky(int cislo) {
        this.poradieObrazka = cislo;
    }

    StavPrirucky() {
        this.poradieObrazka = -1;
    }
    public int getPoradieObrazka() {
        return this.poradieObrazka;
    }
}
