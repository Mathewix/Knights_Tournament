package HernyBalik;

public enum Efekt {
    SLACHTA("Obrazky/E1"), //DONE
    OBCHODNIK("Obrazky/E2"), //DONE
    ADRENALIN("Obrazky/E3"), //DONE
    VETERANSKY_TRENING("Obrazky/E4"), //DONE
    MAJSTER_KOVAC("Obrazky/E5"), //DONE
    ZBERATEL("Obrazky/E6"), //DONE
    TIMOVA_PRACA("Obrazky/E7"),
    SLAVNOSTI("Obrazky/E8"),
    KRATKODOBA_INVESTICIA("Obrazky/E9"), //DONE
    OSUDOVA_VYPRAVA("Obrazky/E10"), //DONE
    SEBAVEDOMI_RYTIERI("Obrazky/E11"), //DONE
    NEZABUDNUTELNE_DOBRODRUZSTVO("Obrazky/E12");

    private final String obrazok;
    private final String ikona;
    private final String vylepsie;
    Efekt(String obrazok) {
        this.obrazok = obrazok + ".png";
        this.ikona = obrazok + "_ikona.png";
        this.vylepsie = obrazok + "_vylepsenie.png";
    }
    public String getObrazok() {
        return this.obrazok;
    }
    public String getIkona() {return this.ikona;}
    public String getVylepsie() {return this.vylepsie;}
}
