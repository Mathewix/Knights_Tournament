package Rytieri.Schopnsoti;

/**
 * Enum schopností, kt. môžu Pokrocili Rytieri mat.
 * Bonusove staty dalej vo forme sila/popularita, staty sa prirátavajú rytierovi až v Aréne aby kvôli nim nemusel dlhšie trénovať
 */
public enum Schopnost {
    // +4/+5 ak nie je rytier vybavený žiadnymi predmetmi
    NATURALISTA("Obrazky/naturalista.png", 36),
    // +4/+3 ak je rytier vybavený maximálnym počtom predmetov
    PAN_PRIPRAVENY("Obrazky/panPripraveny.png", 36),
    // +0/+5
    RODENA_HVIEZDA("Obrazky/rodenaHviezda.png", 38),
    // +5/+0
    CHLAP_JAK_HORA("Obrazky/chlapJakHora.png", 38),
    // +3/+3
    PRIRODZENY_TALENT("Obrazky/prirodzenyTalent.png", 36),
    // skrátenie doby potrebnej na zisk popularity v Krcme
    TEN_OBLUBENY("Obrazky/tenOblubeny.png", 40),
    // skrátenie doby potrebnej na zisk sily na Cvicisku
    TEN_ATLETICKY("Obrazky/tenAtleticky.png", 40),
    // skrátenie doby potrebnej na zisk predmetu v Cechu
    TEN_DOSLEDNY("Obrazky/tenDosledny.png", 45),
    // +2/+1 po každom turnaji, kt. sa rytier zúčastnil
    GLADIATOR("Obrazky/gladiator.png", 55),
    // nahodne prida rytierovi silu/popularitu/skusenost(mala sanca) 3-krat po každom ukončení výpravy v Cechu
    DOBRODRUH("Obrazky/dobrodruh.png", 50),
    EXKALIBER("Obrazky/Artus.png", 434),
    JEDEN_ZA_VSETKYCH("Obrazky/Darta.png", 448),
    ZIJUCA_LEGENDA("Obrazky/Hanibal.png", 409),
    ANGLICKY_KOMUNIZMUS("Obrazky/Robin.png", 425),
    BOZI_BOJOVNIK("Obrazky/Joan.png", 480);
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
