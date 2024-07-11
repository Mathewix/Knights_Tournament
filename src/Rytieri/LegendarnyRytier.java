package Rytieri;

import HernyBalik.Mapa;
import Lokality.Obchod;
import Predmety.MiestoNaPredmet;
import Predmety.Vyzbroj;
import Rytieri.Schopnsoti.Schopnost;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.Text;

public class LegendarnyRytier extends PokrocilyRytier{
    private final MiestoNaPredmet<Vyzbroj> vyzbroj2;
    private Image miestoNaVyzbroj2;
    private int skusenosti;
    private final Schopnost schopnost;
    private int pocetTurnajov;
    private int[] suradniceVyzbroj2;
    private String IconName;

    public LegendarnyRytier(String meno, Schopnost schopnost, int sila, int popularita) {
        super(meno, schopnost, sila, popularita);
        this.schopnost = schopnost;
        switch (meno) {
            case "Robin Hood" -> super.obrazokCesta = "Obrazky/RobinHood.png";
            case "Jean d'Arc" -> super.obrazokCesta = "Obrazky/JoanOfArc.png";
            case "Hannibal" -> super.obrazokCesta = "Obrazky/HannibalBarca.png";
            case "K. Arthur" -> super.obrazokCesta = "Obrazky/KingArthur.png";
            case "d'Artagnan" -> super.obrazokCesta = "Obrazky/Dartagnan.png";
        }
        this.vyzbroj2 = new MiestoNaPredmet<>();
        this.IconName = "Obrazky/" + this.getMeno() + ".png";

    }

    @Override
    public void kartaRytiera(int suradnicaX, int suradnicaY, Mapa mapa, boolean prehlad, boolean zobraz) {
        super.setRaritaText("Obrazky/legendarny.png");
        super.kartaRytiera(suradnicaX, suradnicaY, mapa, prehlad, zobraz);
        if (this.schopnost == Schopnost.EXKALIBER) {
            this.nasadExcalibur();
            if (prehlad) {
                this.getVyzbroj2().get().skryKartu();
            }
        } else if (this.schopnost == Schopnost.ZIJUCA_LEGENDA) {
            this.setSkusenost(12);
        }
    }
    public void skryIkonuRytiera() {
        this.ikona.makeInvisible();
        this.ikona = new Image(this.IconName,0,0);
    }
    private void nasadExcalibur() {
        var excalibur = new Excalibur();
        excalibur.nasadNaRytiera(this);
    }

    public Image getMiestoNaVyzbroj2() {
        return this.miestoNaVyzbroj2;
    }

    public MiestoNaPredmet<Vyzbroj> getVyzbroj2() {
        return this.vyzbroj2;
    }
    public void setSuradniceVyzbroj2(int[] suradnice) {
        this.suradniceVyzbroj2 = suradnice;
    }
    public void setMiestoNaVyzbroj2(Image miestoNaVyzbroj2) {
        this.miestoNaVyzbroj2 = miestoNaVyzbroj2;
    }
    @Override
    public void pridajSkusenost(boolean moveTheText) {
        System.out.println(this.skusenosti);
        this.skusenosti++;
        var sText = this.getSkusenostiText();
        if (sText != null) {
            sText.makeInvisible();
            sText.changeText("" + this.skusenosti);
            if (moveTheText || this.skusenosti == 10) {
                sText.moveHorizontal(-6);
            }
            sText.makeVisible();
            if (this.getUmiestnenie() instanceof Obchod) {
                sText.makeInvisible();
            }
        }
        if (this.skusenosti == 1) {
            this.rank = Rank.NOVICE;
            this.rankObrazok.changeImage("Obrazky/Novice.png");
            this.refreshImage();
            this.rankCheck = "Novice";
        } else if (this.skusenosti == 2 || this.skusenosti == 3 && !this.rankCheck.equals("JourneyMan")) {
            this.rank = Rank.JOURNEYMAN;
            this.rankObrazok.changeImage("Obrazky/Journey.png");
            this.refreshImage();
            this.rankCheck = "JourneyMan";
        } else if (this.skusenosti >= 4 && this.skusenosti <= 6 && !this.rankCheck.equals("Champion")) {
            this.rank = Rank.CHAMPION;
            this.rankObrazok.changeImage("Obrazky/Champ.png");
            this.refreshImage();
            this.rankCheck = "Champion";
        } else if (this.skusenosti >= 7 && !this.rankCheck.equals("Legend")) {
            this.rank = Rank.LEGEND;
            this.rankObrazok.changeImage("Obrazky/Legend.png");
            this.refreshImage();
            this.rankCheck = "Legend";
        }

    }

    @Override
    public int getSkusenosti() {
        return super.getSkusenosti() + 4;
    }

    @Override
    public void pridajSilu() {
        if (this.schopnost == Schopnost.EXKALIBER) {
            this.vyzbroj2.get().vylepsiPredmet();
        } else if (this.schopnost == Schopnost.JEDEN_ZA_VSETKYCH) {
            super.pridajSilu();
            for (int i = 0; i < 3; i++) {
                if (this.getHrac().getRytier(i) != null && this.getHrac().getRytier(i) != this) {
                    this.getHrac().getRytier(i).pridajSilu();
                }
            }
        } else {
            super.pridajSilu();
        }
    }

    @Override
    public void pridajSilu(int pocet) {
        for (int i = 0; i < pocet; i++) {
            this.pridajSilu();
        }
    }

    @Override
    public void pridajPopularitu() {
        if (this.schopnost == Schopnost.JEDEN_ZA_VSETKYCH) {
            super.pridajPopularitu();
            for (int i = 0; i < 3; i++) {
                if (this.getHrac().getRytier(i) != null && this.getHrac().getRytier(i) != this) {
                    this.getHrac().getRytier(i).pridajPopularitu();
                }
            }
        } else {
            super.pridajPopularitu();
        }
    }

    @Override
    public void pridajPocetTurnajov() {
        if (this.schopnost != Schopnost.BOZI_BOJOVNIK) {
            super.pridajPocetTurnajov();
        } else {
            this.pocetTurnajov++;
            if (this.pocetTurnajov == 1) {
                this.pridajSilu(2);
                this.pridajPopularitu(2);
            } else if (this.pocetTurnajov == 2) {
                this.pridajPopularitu(this.popularita);
                this.pridajSilu(this.sila);
                this.zmenObrazok("Obrazky/JoanOfArc2.png");
                this.IconName = this.IconName = "Obrazky/" + this.getMeno() + "2.png";
            } else {
                super.pridajPocetTurnajov();
            }
        }
    }

    public int[] getSuradniceVyzbroj2() {
        return this.suradniceVyzbroj2;
    }

    private static class Excalibur extends Vyzbroj {
        public Excalibur() {
            super("Excalibur", 3, 10);
        }

        @Override
        public void vylepsiPredmet() {
            this.hodnota++;
            this.hodnotaText.changeText("+" + this.getHodnota());
            this.hodnotaText.makeInvisible();
            this.hodnotaText.makeVisible();

        }

        @Override
        public void skryKartu() {
            this.ikona.makeInvisible();
            this.hodnotaText.makeInvisible();
        }

        @Override
        public boolean nasadNaRytiera(ObycajnyRytier rytier) {
            if (rytier instanceof LegendarnyRytier lR) {
                lR.getVyzbroj2().put(this);
                this.ikona = new Image("Obrazky/excalibur.png", lR.getSuradniceVyzbroj2()[0], lR.getSuradniceVyzbroj2()[2]);
                this.hodnotaText = new Text("+" + this.getHodnota(), lR.getSuradniceVyzbroj2()[0] + 14 , lR.getSuradniceVyzbroj2()[2] + 34);
                this.hodnotaText.changeFont("", FontStyle.BOLD, 13);
                this.ikona.makeVisible();
                this.hodnotaText.makeVisible();
                return true;
            }
            return false;
        }
    }
    public void setSkusenost(int skusenosti) {
        this.rankCheck = "";
        this.skusenosti = skusenosti - 1;
        var move = this.skusenosti >= 10;
        this.pridajSkusenost(move);
    }
}
