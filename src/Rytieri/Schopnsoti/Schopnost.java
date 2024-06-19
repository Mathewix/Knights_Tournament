package Rytieri.Schopnsoti;

/**
 * Enum schopností, kt. môžu Pokrocili Rytieri mat.
 * Bonusove staty dalej vo forme sila/popularita, staty sa prirátavajú rytierovi až v Aréne aby kvôli nim nemusel dlhšie trénovať
 */
public enum Schopnost {
    // +3/+3 ak nie je rytier vybavený žiadnymi predmetmi
    NATURALISTA("Obrazky/naturalista.png", 30),
    // +3/+3 ak je rytier vybavený maximálnym počtom predmetov
    PAN_PRIPRAVENY("Obrazky/panPripraveny.png", 30),
    // +0/+4
    RODENA_HVIEZDA("Obrazky/rodenaHviezda.png", 36),
    // +4/+0
    CHLAP_JAK_HORA("Obrazky/chlapJakHora.png", 36),
    // +2/+2
    PRIRODZENY_TALENT("Obrazky/prirodzenyTalent.png", 32),
    // skrátenie doby potrebnej na zisk popularity v Krcme
    TEN_OBLUBENY("Obrazky/tenOblubeny.png", 40),
    // skrátenie doby potrebnej na zisk sily na Cvicisku
    TEN_ATLETICKY("Obrazky/tenAtleticky.png", 40),
    // skrátenie doby potrebnej na zisk predmetu v Cechu
    TEN_DOSLEDNY("Obrazky/tenDosledny.png", 45),
    // +1/+1 po každom turnaji, kt. sa rytier zúčastnil
    GLADIATOR("Obrazky/gladiator.png", 45),
    // +1/+0 alebo +0/+1 po každom ukončení výpravy v Cechu
    DOBRODRUH("Obrazky/dobrodruh.png", 40),
    EXKALIBER("Obrazky/Artus.png", 438),
    JEDEN_ZA_VSETKYCH("Obrazky/Darta.png", 453),
    ZIJUCA_LEGENDA("Obrazky/Hanibal.png", 414),
    ANGLICKY_KOMUNIZMUS("Obrazky/Robin.png", 430),
    BOZI_BOJOVNIK("Obrazky/Joan.png", 485);
    private final String obrazok;
    private final int cena;

    Schopnost(String obrazok, int cena) {
        this.obrazok = obrazok;
        this.cena = cena;
    }
    public String getObrazok() {
        return this.obrazok;
    }

    public int getCena() {
        return this.cena;
    }
}
