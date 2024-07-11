package Lokality;

import HernyBalik.ManazerEventov;
import Hraci.Hrac;
import Predmety.Predmet;
import Rytieri.ObycajnyRytier;
import fri.shapesge.Image;


public class Doska implements Miesto{
    private final Hrac hrac;
    private ObycajnyRytier[] rytiery;
    private final Image obrazok;
    private final Image[] lokalitaRytierov;
    private final Image[] miestaRytierov;
    private final Image lockedKnight;
    private Predmet nasadzuje;
    private boolean unlocked;
    //private ManazerEventov manazerEventov;

    public Doska(Hrac hrac) {
        this.hrac = hrac;
        this.obrazok = new Image("Obrazky/doska.png", 0, 670);
        this.lokalitaRytierov = new Image[3];
        this.miestaRytierov = new Image[3];
        this.lokalitaRytierov[0] = new Image("Obrazky/lokalita.png",223 ,645);
        this.lokalitaRytierov[1] = new Image("Obrazky/lokalita.png",523 ,645);
        this.lokalitaRytierov[2] = new Image("Obrazky/lokalita.png",823 ,645);
        this.miestaRytierov[0] = new Image("Obrazky/miestoNaRytiera.png", 215, 690);
        this.miestaRytierov[1] = new Image("Obrazky/miestoNaRytiera.png", 515, 690);
        this.miestaRytierov[2] = new Image("Obrazky/miestoNaRytiera.png", 815, 690);
        this.lockedKnight = new Image("Obrazky/Locked.png",815,690);
    }
    /*public void setManazerEventov(ManazerEventov manazerEventov) {
        this.manazerEventov = manazerEventov;
    }*/
    public void zobrazDosku() {
        this.obrazok.makeVisible();
        for (Image image : this.lokalitaRytierov) {
            image.makeVisible();
        }
        for (Image image : this.miestaRytierov) {
            image.makeVisible();
        }
        this.lockedKnight.makeVisible();
    }
    public void zmenLokalituRytiera(int poradie, String lokalita) {
        this.lokalitaRytierov[poradie].makeInvisible();
        this.lokalitaRytierov[poradie].changeImage(lokalita);
        this.lokalitaRytierov[poradie].makeVisible();
    }
    @Override
    public void vratRytierov(boolean ukoncena, ObycajnyRytier r) {
        this.rytiery = new ObycajnyRytier[3];
    }

    @Override
    public int[] getSuradnice() {
        return new int[]{0, 1200, 650, 1000};
    }

    @Override
    public int[] getSuradniceIkona() {
        return new int[0];
    }

    public void nastavujePredmet(Predmet p) {
        this.nasadzuje = p;
    }

    public Predmet getNasadzuje() {
        return this.nasadzuje;
    }

    public Hrac getHrac() {
        return this.hrac;
    }

    public boolean unlocked() {
        return this.unlocked;
    }

    public void unlockThirdKnight() {
        this.unlocked = true;
        this.lockedKnight.makeInvisible();
    }
}
