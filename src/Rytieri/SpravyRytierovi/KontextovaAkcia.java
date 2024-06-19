package Rytieri.SpravyRytierovi;

import HernyBalik.Efekt;
import HernyBalik.Predavatelne;
import Lokality.*;
import Predmety.Predmet;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import fri.shapesge.Image;

/**
 * Abstraktná trieda slúžiaca ako predok pre ostatné kontextové akcie (KA).
 * Je tu určené správanie každej KA a aj vykreslovanie na správnych pozíciach vzhľadom na pozíciu rytiera alebo predmetu
 */
public abstract class KontextovaAkcia {
    private final Image obrazok;
    protected Predavatelne predavatelne;

    public KontextovaAkcia(Predavatelne predavatelne, String obrazok, int x, int y) {
        this.predavatelne = predavatelne;
        this.obrazok = new Image(obrazok, this.predavatelne.getSuradnice()[0] + x,this.predavatelne.getSuradnice()[2] + y);
    }
    public void zobrazSpravu() {
        this.obrazok.makeVisible();
    }

    /**
     * Metóda na základe toho, o akú inštanciu KA sa jedná (podľa ich miesta ku ktorému su relevantné)
     * určí čo má dané miesto vykonať ak hráč klikol na KA
     */
    public abstract void klikolAkciu();


    /**
     *
     * @return - vráti súradnice, kde sa KA nachádza v tvare int[minX, maxX, minY, maxY]
     */
    public int[] getSuradnice() {
        var suradnice = new int[4];
        suradnice[0] = this.predavatelne.getSuradnice()[0] + 140;
        suradnice[1] = suradnice[0] + 187;
        if (this instanceof ChodNaCvicisko) {
            suradnice[2] = this.predavatelne.getSuradnice()[2] - 20;

        } else if (this instanceof ChodDoKrcmy) {
            suradnice[2] = this.predavatelne.getSuradnice()[2] + 14;
        } else if (this instanceof ChodDoCechu) {
            suradnice[2] = this.predavatelne.getSuradnice()[2] + 48;
        } else if (this instanceof ChoddoAreny || this instanceof PrerusAktivitu) {
            suradnice[2] = this.predavatelne.getSuradnice()[2] + 82;
        } else if (this instanceof KupSa || this instanceof Nasad ) {
            suradnice[0] = this.predavatelne.getSuradnice()[0] + 70;
            suradnice[1] = suradnice[0] + 187;
            suradnice[2] = this.predavatelne.getSuradnice()[2] - 10;
        } else if (this instanceof ZlozTrinket1) {
            suradnice[2] = this.predavatelne.getSuradnice()[2] + 116;
        } else if (this instanceof ZlozVyzbroj1) {
            suradnice[2] = this.predavatelne.getSuradnice()[2] + 150;
        } else if (this instanceof ZlozTrinket2) {
            suradnice[2] = this.predavatelne.getSuradnice()[2] + 184;
        } else if (this instanceof ZlozVyzbroj2) {
            suradnice[2] = this.predavatelne.getSuradnice()[2] + 218;
        } else if (this instanceof Vylepsit) {
            suradnice[0] = this.predavatelne.getSuradnice()[0] + 70;
            suradnice[1] = suradnice[0] + 187;
            suradnice[2] = this.predavatelne.getSuradnice()[2] + 24;
        } else if (this instanceof Predat) {
            if (this.predavatelne instanceof Predmet) {
                suradnice[0] = this.predavatelne.getSuradnice()[0] + 70;
                suradnice[1] = suradnice[0] + 187;
                suradnice[2] = this.predavatelne.getSuradnice()[2] + 58;
            } else {
                suradnice[0] = this.predavatelne.getSuradnice()[0] + 14;
                suradnice[1] = suradnice[0] + 187;
                suradnice[2] = this.predavatelne.getSuradnice()[2] + 272;
            }
        }
        suradnice[3] = suradnice[2] + 34;
        return suradnice;
    }

    public void skrySpravu() {
        this.obrazok.makeInvisible();
    }

}
