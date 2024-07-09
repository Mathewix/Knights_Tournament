package HernyBalik;

import fri.shapesge.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class EfektyHry {
    private final Efekt[] efekty;
    private ArrayList<Efekt> allEfects;
    private final Image[] obrazky;
    private final Image pozadie;
    private final Image vylepsenie;
    private final Image nevyhoda;
    private final Image nevyhodaRam;
    private final Image vylepsenieRam;
    private final Image reroll;
    private final Image rerollText;
    private final Image lockedOption;
    private Image vylepseniePopis;
    private int numberOfOptions;
    private boolean unlocked;

    public EfektyHry() {
        this.allEfects = new ArrayList<>();
        Collections.addAll(this.allEfects, Efekt.values());
        this.efekty = new Efekt[4];
        this.obrazky = new Image[4];
        this.pozadie = new Image("Obrazky/pozadieEfekty.png", 130, 50);
        this.numberOfOptions = 4;
        this.vyberEfekty();
        this.vylepsenie = new Image("Obrazky/vylepsiEfekt.png",401 ,150);
        this.nevyhoda = new Image("Obrazky/odstranNevyhodu.png",643 , 150);
        this.nevyhodaRam = new Image("Obrazky/inventarNevyhoda.png", 25, 775);
        this.vylepsenieRam = new Image("Obrazky/inventarVyhoda.png", 25, 775);
        this.reroll = new Image("Obrazky/EffectReroll.png",930,550);
        this.rerollText = new Image("Obrazky/EffectRerollText.png",905,630);
        this.lockedOption = new Image("Obrazky/Locked.png",885,202);
        this.unlocked = true;
    }

    public void vyberEfekty() {
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            var cislo = r.nextInt(0, this.allEfects.size());
            this.efekty[i] = this.allEfects.get(cislo);
            this.obrazky[i] = new Image (this.allEfects.get(cislo).getObrazok(), 156 + (i * 243), 202);
            this.allEfects.remove(cislo);
        }
    }
    public Efekt[] getEfekty() {
        return this.efekty;
    }

    public void zobrazMoznosti() {
        this.pozadie.makeVisible();
        for (int i = 0; i < 4; i++) {
            this.obrazky[i].makeVisible();
        }
        if (this.numberOfOptions == 4) {
            this.reroll.makeVisible();
            this.rerollText.makeVisible();
        } else {
            this.lockedOption.makeVisible();
        }
    }

    public void skryEfekty() {
        this.pozadie.makeInvisible();
        for (int i = 0; i < 4; i++) {
            this.obrazky[i].makeInvisible();
        }
        if (this.numberOfOptions == 4) {
            this.reroll.makeInvisible();
            this.rerollText.makeInvisible();
            this.numberOfOptions--;
            this.unlocked = false;
        } else if (!this.unlocked) {
            this.lockedOption.makeInvisible();
        }
    }
    public void zobrazVylepsenie(Efekt efekt) {
        this.pozadie.makeVisible();
        this.vylepsenie.makeVisible();
        this.nevyhoda.makeVisible();
        this.vylepseniePopis = new Image(efekt.getVylepsie(),432 ,460);
        this.vylepseniePopis.makeVisible();
    }
    public void skryVylepsenie() {
        this.pozadie.makeInvisible();
        this.vylepseniePopis.makeInvisible();
        this.vylepsenie.makeInvisible();
        this.nevyhoda.makeInvisible();
    }
    public void zobrazRam(StavEfektu stav) {
        if (stav == StavEfektu.VYLEPSENE) {
            this.vylepsenieRam.makeVisible();
        } else if (stav == StavEfektu.BEZ_NEVYHODY) {
            this.nevyhodaRam.makeVisible();
        }
    }

    public int getNumberOfOptions() {
        return this.numberOfOptions;
    }

    public boolean paid() {
        return this.unlocked;
    }

    public void unlockForth() {
        this.unlocked = true;
        this.lockedOption.makeInvisible();
    }
}
