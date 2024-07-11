package Predmety;

import HernyBalik.Efekt;
import HernyBalik.ManazerEventov;
import HernyBalik.Predavatelne;
import HernyBalik.StavEfektu;
import Lokality.Doska;
import Lokality.Miesto;
import Lokality.Obchod;
import Rytieri.LegendarnyRytier;
import Rytieri.ObycajnyRytier;
import Rytieri.PokrocilyRytier;
import Rytieri.SpravyRytierovi.*;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.Text;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Abstraktná trieda Predmet slúži ako predok Vyzbroje (prdmet pridavajuci silu rytierovi) a Trinketu (predmet pridavajuci popularitu rytierovi)
 * inicializuje ich obrázky a spravuje aké kontextové akcie môžu používať
 */
public abstract class Predmet implements Predavatelne {
    protected String nazov;
    private Text nazovText;
    protected int hodnota;
    private final int cena;
    protected Image obrazok;
    private int[] suradnice;
    private Miesto umiestnenie;
    private boolean nasadeny;
    private ArrayList<KontextovaAkcia> vsetkyAkcie;
    private ObycajnyRytier rytier;
    protected Image ikona;
    private Image cenaObrazok;
    private Text cenaText;
    protected Text hodnotaText;
    private int cenaVylepsenia;
    private ManazerEventov manazerEventov;
    protected int vylepsenia;
    protected String obrazokIkony;
    protected String obrazokCesta;
    private Integer cas;


    public Predmet(String nazov, int hodnota, int cena) {
        this.nazov = nazov;
        this.hodnota = hodnota;
        this.cena = cena;
        this.rytier = null;
        this.vylepsenia = 0;
    }

    public void vylepsiPredmet() {
        this.vylepsenia++;
        if (this.suradnice != null) {
            this.skryKartu();
            this.zobrazKartu();
            this.vsetkyAkcie.get(2).skrySpravu();
            this.vsetkyAkcie.get(2).zobrazSpravu();
        }
    }
    public void vylepsiPredmet(int kolko) {
        this.vylepsenia += kolko;
        if (this.suradnice != null) {
            this.skryKartu();
            this.zobrazKartu();
            this.vsetkyAkcie.get(2).skrySpravu();
            this.vsetkyAkcie.get(2).zobrazSpravu();
        }
    }

    public boolean mozemVylepsit() {
        if (this.umiestnenie instanceof Doska doska) {
            var kovac = doska.getHrac().getMapa().getEfekt() != Efekt.MAJSTER_KOVAC;
            if (kovac) {
                return this.vylepsenia < (1 + doska.getHrac().getManazerEventov().getFazaHry());
            } else {
             if (doska.getHrac().getMapa().getStavEfektu() != StavEfektu.BEZ_NEVYHODY) {
                return this.vylepsenia != 4;
             } else {
                 return true;
             }
            }
        }
        return false;
    }

    public int getHodnota() {
        if (this.vylepsenia < 5) {
            return this.hodnota + this.vylepsenia;
        }  else {
            return  this.hodnota + 7; // alebo 6
        }
    }


    /**
     * Vytvorí grafické znázornenie predmetu a jeho súradnice a KontextovéAktivity (KA)
     * @param x
     * @param y
     * @param obchod - na inicializáciu KA KupSa
     * @param jeVObchode - určuje, kde a ako sa má vytvoriť grafické znázornenie predmetu pre obchod
     */
    public void kartaPredmetu(int x, int y, Obchod obchod, boolean jeVObchode) {
        if (this.suradnice != null) {
            this.skryKartu();
        }

        this.suradnice = new int[]{x, x + 75, y, y + 75};
        this.obrazok = new Image(this.obrazokCesta, x, y);
        this.nazovText = new Text(this.nazov, x + 16 - (this.nazov.length() / 2), y + 20);
        this.nazovText.changeFont("", FontStyle.BOLD, 20 - this.nazov.length());
        this.nazovText.changeColor("#090909");
        this.hodnotaText = new Text("+ " + this.getHodnota(), x + 34 , y + 66);
        this.hodnotaText.changeFont("", FontStyle.BOLD, 13);
        this.vsetkyAkcie = new ArrayList<>();
        this.vsetkyAkcie.add(new KupSa(this, obchod));
        if (this.umiestnenie instanceof Doska doska) {
            this.vsetkyAkcie.add(new Nasad(this, doska));
            this.vsetkyAkcie.add(new Vylepsit(this, doska));
            this.vsetkyAkcie.add(new Predat(this, doska, 70, 58));
        }
        if (jeVObchode) {
            this.cenaObrazok = new Image("Obrazky/cena.png", x, y + 79);
            var cena = "";
            if (obchod.getMapa().getEfekt() == Efekt.ZBERATEL) {
                if (obchod.getMapa().getStavEfektu() == StavEfektu.BEZ_NEVYHODY) {
                    cena += this.getCena();
                } else {
                    cena += this.getCena() * 2;
                }
            } else {
                cena += this.getCena();
            }
            this.cenaText = new Text(cena + "g", x + 32 - (cena.length() * 5), y + 100);
            this.cenaText.changeFont("", FontStyle.BOLD, 16);
            this.cenaText.changeColor("#312E26");
        } else {
            this.cenaObrazok = null;
            this.cenaText = null;
        }

    }

    /**
     * Zobrazí inicializované časti predmetu
     */
    public void zobrazKartu() {
        this.obrazok.changeImage(this.obrazokCesta);
        this.obrazok.makeVisible();
        this.nazovText.changeText(this.nazov);
        this.nazovText.changeFont("", FontStyle.BOLD, 20 - this.nazov.length());
        this.nazovText.makeVisible();
        this.hodnotaText.changeText("+ " + this.getHodnota());
        this.hodnotaText.makeVisible();
        if (this.cenaObrazok != null && this.cenaText != null) {
            this.cenaObrazok.makeVisible();
            this.cenaText.makeVisible();
        }
    }

    /**
     * skryje inicializované časti predmetu
     */
    @Override
    public void skryKartu() {
        this.obrazok.makeInvisible();
        this.nazovText.makeInvisible();
        this.hodnotaText.makeInvisible();
        if (this.cenaObrazok != null && this.cenaText != null) {
            this.cenaObrazok.makeInvisible();
            this.cenaText.makeInvisible();
        }
    }

    @Override
    public int getCena() {
        return this.cena + this.getHodnota() + (this.vylepsenia * 4);
    }
    public int[] getSuradnice() {
        return this.suradnice;
    }

    @Override
    public Miesto getUmiestnenie() {
        return this.umiestnenie;
    }

    @Override
    public void zmenUmiestnenie(Miesto miesto) {
        this.umiestnenie = miesto;
    }



    @Override
    public ArrayList<KontextovaAkcia> pouzitelneSpravy() {
        ArrayList<KontextovaAkcia> pouzitelneSpravy = new ArrayList<>();
        if (this.umiestnenie instanceof Obchod) {
            pouzitelneSpravy.add(this.vsetkyAkcie.get(0));
        } else if (this.umiestnenie instanceof Doska && !this.nasadeny) {
            pouzitelneSpravy.add(this.vsetkyAkcie.get(1));
            pouzitelneSpravy.add(this.vsetkyAkcie.get(2));
            pouzitelneSpravy.add(this.vsetkyAkcie.get(3));
        }
        return pouzitelneSpravy;
    }


    /**
     * Zistí či sa jedná o Trinket alebo Vyzbroj, prejde ktoré rytierovo miesto na predmety je prázdne a ak je to možné,
     * nasadí tento predmet a zobrazí ho na daných súradniciach rytierovi
     * @param rytier - na ktorého rytiera chceme nasadiť predmet
     * @return - úspešnosť akcie na základe, ktorej sa predmet ne/odstráni z inventára hráča
     */
    public boolean nasadNaRytiera(ObycajnyRytier rytier) {

        this.rytier = rytier;
        if (this instanceof Trinket t) {
            if (this.rytier instanceof PokrocilyRytier pR) {
                if (pR.getTrinket().isEmpty()) {
                    pR.getTrinket().put(t);
                    this.skryKartu();
                    this.ikona = new Image(this.obrazokIkony, pR.getSuradniceTrinket()[0], pR.getSuradniceTrinket()[2]);
                    this.hodnotaText = new Text("+ " + this.getHodnota(), pR.getSuradniceTrinket()[0] + 14 , pR.getSuradniceTrinket()[2] + 34);
                    this.hodnotaText.changeFont("", FontStyle.BOLD, 13);
                } else if (pR.getTrinket2().isEmpty()) {
                    pR.getTrinket2().put(t);
                    this.skryKartu();
                    this.ikona = new Image(this.obrazokIkony, pR.getSuradniceTrinket2()[0], pR.getSuradniceTrinket2()[2]);
                    this.hodnotaText = new Text("+ " + this.getHodnota(), pR.getSuradniceTrinket2()[0] + 14 , pR.getSuradniceTrinket2()[2] + 34);
                    this.hodnotaText.changeFont("", FontStyle.BOLD, 13);
                } else {
                    rytier.getHrac().getManazerEventov().pozastavHru();
                    JOptionPane.showMessageDialog(null, "Rytier " + pR.getMeno() + " nema na trinket miesto", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                if (this.rytier.getTrinket().isEmpty()) {
                    this.rytier.getTrinket().put(t);
                    this.skryKartu();
                    this.ikona = new Image(this.obrazokIkony, this.rytier.getSuradniceTrinket()[0], this.rytier.getSuradniceTrinket()[2]);
                    this.hodnotaText = new Text("+ " + this.getHodnota(), this.rytier.getSuradniceTrinket()[0] + 14 , this.rytier.getSuradniceTrinket()[2] + 34);
                    this.hodnotaText.changeFont("", FontStyle.BOLD, 13);
                } else {
                    rytier.getHrac().getManazerEventov().pozastavHru();
                    JOptionPane.showMessageDialog(null, "Rytier " + this.rytier.getMeno() + " nema na trinket miesto", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else if (this instanceof Vyzbroj v) {
            if (this.rytier instanceof LegendarnyRytier lR) {
                if (lR.getVyzbroj().isEmpty()) {
                    lR.getVyzbroj().put(v);
                    this.skryKartu();
                    this.ikona = new Image(this.obrazokIkony, lR.getSuradniceVyzbroj()[0], lR.getSuradniceVyzbroj()[2]);
                    this.hodnotaText = new Text("+ " + this.getHodnota(), lR.getSuradniceVyzbroj()[0] + 14 , lR.getSuradniceVyzbroj()[2] + 34);
                    this.hodnotaText.changeFont("", FontStyle.BOLD, 13);
                } else if (lR.getVyzbroj2().isEmpty()) {
                    lR.getVyzbroj2().put(v);
                    this.skryKartu();
                    this.ikona = new Image(this.obrazokIkony, lR.getSuradniceVyzbroj2()[0], lR.getSuradniceVyzbroj2()[2]);
                    this.hodnotaText = new Text("+ " + this.getHodnota(), lR.getSuradniceVyzbroj2()[0] + 14 , lR.getSuradniceVyzbroj2()[2] + 34);
                    this.hodnotaText.changeFont("", FontStyle.BOLD, 13);
                } else {
                    rytier.getHrac().getManazerEventov().pozastavHru();
                    JOptionPane.showMessageDialog(null, "Rytier " + lR.getMeno() + " nema na trinket miesto", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                if (this.rytier.getVyzbroj().isEmpty()) {
                    this.rytier.getVyzbroj().put(v);
                    this.skryKartu();
                    this.ikona = new Image(this.obrazokIkony, this.rytier.getSuradniceVyzbroj()[0], this.rytier.getSuradniceVyzbroj()[2]);
                    this.hodnotaText = new Text("+ " + this.getHodnota(), this.rytier.getSuradniceVyzbroj()[0] + 14, this.rytier.getSuradniceVyzbroj()[2] + 34);
                    this.hodnotaText.changeFont("", FontStyle.BOLD, 13);
                } else {
                    rytier.getHrac().getManazerEventov().pozastavHru();
                    JOptionPane.showMessageDialog(null, "Rytier " + this.rytier.getMeno() + " nema na vyzbroj miesto", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }

        this.ikona.makeVisible();
        this.hodnotaText.makeVisible();
        this.nasadeny = true;
        return true;
    }

    /**
     * zloží predmet z rytiera ak sa na danom mieste predmet nachádza
     * @return - úspešnosť akcie, určuje či ne/pridať predmet do inventára hráča
     */
    public boolean zlozZRytiera() {
        if (this.rytier.getHrac().getInventorySpace() != 0) {
            this.zobrazKartu();
            if (this instanceof Trinket t) {
                if (this.rytier.getTrinket().get() == t) {
                    this.rytier.getTrinket().remove();
                } else if (this.rytier instanceof PokrocilyRytier pR) {
                    if (pR.getTrinket2().get() == t) {
                        pR.getTrinket2().remove();
                    }
                } else {
                    this.rytier.getHrac().getManazerEventov().pozastavHru();
                    JOptionPane.showMessageDialog(null, "Rytier " + this.rytier.getMeno() + " nie je v tomto slote vybaveni", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else if (this instanceof Vyzbroj v) {
                if (this.rytier.getVyzbroj().get() == v) {
                    this.rytier.getVyzbroj().remove();
                } else if (this.rytier instanceof LegendarnyRytier lR) {
                    if (lR.getVyzbroj2().get() == v) {
                        lR.getVyzbroj2().remove();
                    }
                }
            } else {
                this.rytier.getHrac().getManazerEventov().pozastavHru();
                JOptionPane.showMessageDialog(null, "Rytier " + this.rytier.getMeno() + " nie je v tomto slote vybaveni", "Chyba", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            this.rytier = null;
            this.nasadeny = false;
            this.ikona.makeInvisible();
            this.hodnotaText.makeInvisible();
            this.hodnotaText = new Text("+ " + this.getHodnota(), this.suradnice[0] + 36 , this.suradnice[2] + 68);
            return true;
        } else {
            this.rytier.getHrac().zmenStavPenazi(this.getCena());
            this.rytier = null;
            this.nasadeny = false;
            this.ikona.makeInvisible();
            this.hodnotaText.makeInvisible();
            return false;
        }
    }

    public ObycajnyRytier getRytier() {
        return this.rytier;
    }

    public int getCenaVylepsenia() {
        if (this.umiestnenie instanceof Doska doska) {
            var mapa = doska.getHrac().getMapa();
            if (mapa.getEfekt() == Efekt.MAJSTER_KOVAC && mapa.getStavEfektu() == StavEfektu.VYLEPSENE) {
                return (int) Math.ceil(this.cenaVylepsenia * 0.5);
            }
        }
        return this.cenaVylepsenia;
    }

    public void setCenaVylepsenia(int cenaVylepsenia) {
        this.cenaVylepsenia = cenaVylepsenia;
    }

    public void vykonajAktivitu() {
        if (this.umiestnenie instanceof Doska doska) {
            doska.getHrac().zmenStavPenazi(this.getHodnota());
        }
    }

    public Integer getCas() {
        return this.cas;
    }

    public void setCas(Integer cas) {
        this.cas = cas;
    }
}
