package HernyBalik;

import HernyBalik.Navod.Prirucka;
import HernyBalik.Navod.StavPrirucky;
import HernyBalik.StraznaVeza.AkciaVeze;
import HernyBalik.StraznaVeza.StraznaVeza;
import Hraci.Hrac;
import Lokality.*;
import Predmety.Predmet;
import Rytieri.SpravyRytierovi.KontextovaAkcia;
import Rytieri.SpravyRytierovi.Predat;
import Rytieri.SpravyRytierovi.Vylepsit;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.Manager;
import fri.shapesge.Text;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Trieda inicializuje časti hry a spravuje akciu klikania pomocov metódy vyberSuradnicu z Triedy Manager.
 * Na základe pohľadu, v ktorom sa mapa nachádza reaguje rôzne na kliknutie ľavým tlačidlom
 */
public class Mapa {
    private final Manager manazer;
    private final EfektyHry efektyHry;
    private final Prirucka prirucka;
    private Pohlad pohlad;
    private Pohlad predosli;
    private final Arena arena;
    private final Obchod obchod;
    private Cech cech;
    private final Cvicisko cvicisko;
    private final Cvicisko cvicisko2;
    private final Cvicisko cvicisko3;
    private final Krcma krcma;
    private final Krcma krcma2;
    private final Krcma krcma3;
    private final StraznaVeza veza;
    private final Hrac hrac;
    private final ManazerEventov manazerEventov;
    private ArrayList<KontextovaAkcia> akcie;
    private final Image menuObrazok;
    private boolean vidimMenu;
    private Image obrazokEfektu;
    private boolean vidimEfekt;
    private final Image vyhodnotenieHry;
    private Efekt efekt;
    private StavEfektu stavEfektu;
    private int cviciskoUpgrade;


    public Mapa(Hrac hrac,ManazerEventov manazerEventov) {
        this.manazer = new Manager();
        this.manazer.manageObject(this);
        this.pohlad = Pohlad.DEFAULT;
        this.manazerEventov = manazerEventov;
        Image obrazok = new Image("Obrazky/mapa.png", 0, 0);
        obrazok.makeVisible();
        Image menu = new Image("Obrazky/menu.png", 1116, 16);
        menu.makeVisible();
        this.menuObrazok = new Image("Obrazky/menuObrazok.png",350,170);
        this.vyhodnotenieHry = new Image("Obrazky/vyhodnotenieHry.png", 380, 50);
        Image cechName = new Image("Obrazky/Cech.png",655,83);
        cechName.makeVisible();
        Image cviciskoName = new Image("Obrazky/Cvicisko.png",381,448);
        cviciskoName.makeVisible();
        this.hrac = hrac;
        Image krcmaName = new Image("Obrazky/Krcma.png",358,120);
        krcmaName.makeVisible();
        this.hrac.setMapa(this);
        this.efektyHry = new EfektyHry();
        this.arena = new Arena(400, this.hrac);
        this.manazerEventov.setMapa(this);
        this.manazerEventov.setArena(this.arena);
        this.manazerEventov.setEfektyHry(this.efektyHry);
        this.hrac.setManazerEventov(this.manazerEventov);
        this.arena.setManazerEventov(this.manazerEventov);
        this.obchod = new Obchod(200);
        this.obchod.setMapa(this);
        this.cech = new Cech(manazerEventov, hrac);
        this.cvicisko = new Cvicisko(this.manazerEventov, this.hrac,0);
        this.cvicisko2 = new Cvicisko(this.manazerEventov, this.hrac,1);
        this.cvicisko3 = new Cvicisko(this.manazerEventov, this.hrac,2);
        this.krcma = new Krcma(this.manazerEventov, this.hrac,0);
        this.krcma2 = new Krcma(this.manazerEventov, this.hrac,1);
        this.krcma3 = new Krcma(this.manazerEventov, this.hrac,2);
        this.cech = new Cech(this.manazerEventov, this.hrac);
        this.veza = new StraznaVeza(this.manazerEventov);
        this.manazerEventov.setObchod(this.obchod);
        this.manazerEventov.setVeza(this.veza);
        this.prirucka = new Prirucka(this);
        ArrayList<Miesto> miesta = new ArrayList<>();
        miesta.add(this.cech);
        miesta.add(this.krcma);
        miesta.add(this.arena);
        miesta.add(this.cvicisko);
        miesta.add(this.cvicisko2);
        miesta.add(this.cvicisko3);
        this.manazerEventov.zapniHru();
        this.manazerEventov.pozastavHru();
        this.manazerEventov.showTime();
        this.stavEfektu = StavEfektu.PRAZDNE;
    }


    /**
     * Metóda je z knižnice ShapesGE a vykonáva metódy triedy Mapa po kliknutí ľavým tlačidlom myši.
     * metódy vykonáva podľa súradníc, na ktoré sme klikli a pohladu Mapy
     *
     * @param x - súradnica X na ktorú sme klikli ľavým tlačidlom
     * @param y - súradnica Y na ktorú sme klikli ľavým tlačidlom
     */
    public void vyberSuradnice(int x, int y) {
        if (this.pohlad == Pohlad.DEFAULT) {
            if (this.klikNaVezu(x, y)) {
                this.setPohlad(Pohlad.VEZA);
                return;
            }
            if (this.klikNaMenu(x, y)) {
                return;
            }
            this.akcie = this.klikNaRytiera(x, y);
            if (this.akcie == null) {
                this.akcie = this.klikNaInventar(x, y);
            }
            if (this.obchod.isOtvorene()) {
                this.klikNaObchod(x, y);
            }
            if (this.efekt != null) {
                this.klikNaEfekt(x, y);
            }
        } else if (this.pohlad == Pohlad.OBCHOD) {
            this.akcie = this.nakup(x, y);
            this.klikNaObchod(x, y);

        }else if (this.pohlad == Pohlad.ROZHODOVANIE) {
            if (this.akcie != null) {
                this.klikNaAkciu(x, y);
            }
        } else if (this.pohlad == Pohlad.NASAD) {
            this.klikNaRytiera(x, y);
        } else if (this.pohlad == Pohlad.INVENTAR) {
            this.klikNaAkciu(x, y);
        } else if (this.pohlad == Pohlad.NAKUP) {
            this.klikNaAkciu(x, y);
        } else if (this.pohlad == Pohlad.MENU) {
            this.klikNaMenu(x, y);
        } else if (this.pohlad == Pohlad.VYHODNOTENIE_ARENY) {
            this.klikNaArenu(x, y);
        } else if (this.pohlad == Pohlad.VYBER_EFEKTOV) {
            this.klikNaEfekt(x, y);
        } else if (this.pohlad == Pohlad.VEZA) {
            this.klikNaVezu(x, y);
        } else if (this.pohlad == Pohlad.PRIRUCKA || this.pohlad == Pohlad.PRIRUCKA_TEXTY) {
            this.klikNaPrirucku(x, y);
        } else if (this.pohlad == Pohlad.VYBER_STAVU_EFEKTU) {
            this.klikNaStavEfektu(x, y);
        }
    }


    public void aktivuj() {
        if (this.pohlad != Pohlad.VYHODNOTENIE_ARENY) {
            if (this.pohlad != Pohlad.VYBER_EFEKTOV && this.pohlad != Pohlad.VYBER_STAVU_EFEKTU) {
                if (this.manazerEventov.isSpustene()) {
                    this.manazerEventov.pozastavHru();
                } else {
                    this.manazerEventov.zapniHru();
                }
            }
        }
    }
    public void zrus() {
        if (this.pohlad != Pohlad.VYHODNOTENIE_ARENY) {
            if (this.pohlad != Pohlad.VYBER_EFEKTOV && this.pohlad != Pohlad.VYBER_STAVU_EFEKTU) {
                if (this.vidimMenu) {
                    this.klikNaMenu(500, 400);
                    this.manazerEventov.zapniHru();
                    return;
                } else {
                    this.vyberSuradnice(0, 0);
                    this.klikNaMenu(1150, 50);
                }
            }
        }
    }
    public void posunVlavo() {

    }
    public void posunVpravo() {

    }
    public void posunDole() {

    }
    public void posunHore() {

    }

    private void klikNaArenu(int x, int y) {
        var klikZavriX = x > 810 && x <= 834;
        var klikZavriY = y > 216 && y <= 240;
        var klikOdmenyX = x > 507 && x <= 620;
        var klikTlacidloY = y > 470 && y <= 514;
        var klikStatyX = x > 680 && x <= 793;
        if (klikZavriX && klikZavriY) {
            this.arena.skryVysledok();
            this.manazerEventov.zapniHru();
            this.pohlad = this.predosli;
        } else if (klikStatyX && klikTlacidloY) {
            this.arena.zobrazStaty();
        } else if (klikOdmenyX && klikTlacidloY) {
            this.arena.zobrazOdmeny();
        }

    }

    /**
     * Metoda urcuje ci sme klikli na ikonu menu ak sa nachadzame v DEFAULT a zobrazi menu a zastavi cas
     * alebo ak sme v MENU mozme sa vratit do hry alebo ju predcasne ukoncit kliknutim na prislusne tlacidla
     * @param x
     * @param y
     * @return - hodnota true je urcena na prerusenie metody vyberSuradnice
     */
    private boolean klikNaMenu(int x, int y) {
        if (this.pohlad == Pohlad.DEFAULT) {
            var klikX = x > 1116 && x <= 1180;
            var klikY = y > 16 && y <= 80;
            if (klikX && klikY) {
                this.manazerEventov.pozastavHru();
                this.menuObrazok.makeVisible();
                this.vidimMenu = true;
                this.pohlad = Pohlad.MENU;
                return true;
            }
        } else if (this.pohlad == Pohlad.MENU) {
            var klikX = x > 475 && x <= 725;
            var klikY = y > 322 && y <= 402;
            var klikX2 = x > 475 && x <= 725;
            var klikY2 = y > 442 && y <= 522;
            if (klikX && klikY) {
                this.menuObrazok.makeInvisible();
                this.vidimMenu = false;
                this.pohlad = Pohlad.DEFAULT;
                return true;
            } else if (klikX2 && klikY2) {
                this.prirucka.zobrazPrirucku();
                this.menuObrazok.makeInvisible();
                this.vidimMenu = false;
                return true;
            }
        }
        return false;
    }

    /**
     * Ak sa mapa nachádza v pohlade OBCHOD a klikneme na niektorý obrázok tovaru, zobrazí sa pri ňom kontextová akcia (KA) KupSa a pohlad sa zmeni na NAKUP
     * @param x
     * @param y
     * @return - vráti KA KupSa ak sme klikli na tovar, inak null
     */
    private ArrayList<KontextovaAkcia> nakup(int x, int y) {
        for (Predavatelne p: this.getObchod().getTovar()) {
            var klikX = x > p.getSuradnice()[0] && x <= p.getSuradnice()[1];
            var klikY = y > p.getSuradnice()[2] && y <= p.getSuradnice()[3] + 32;
            if (klikY && klikX) {
                this.pohlad = Pohlad.NAKUP;
                return this.zobrazMozneAkcie(p);
            }
        }
        return null;
    }

    /**
     * Ak sa mapa nachádza v pohlade NASTAVENIA a klikneme na obrázok rytiera vlastneného hráčom zobrazia sa pre daného rytiera
     * všetky použiteľné KA a pohlad sa zmeni na ROZHODOVANIE
     * @param x
     * @param y
     * @return - vráti použiteľné KA ak sme klikli na rytiera, inak null
     */
    public ArrayList<KontextovaAkcia> klikNaRytiera(int x, int y) {
        if (!this.hrac.getDoska().unlocked()) {
            var locationX = x > 827 && x <= 1019;
            var locationY = y > 874 && y <= 952;
            if (locationX && locationY) {
                if (this.hrac.zmenStavPenazi(-100)) {
                    this.hrac.getDoska().unlockThirdKnight();
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (this.hrac.getRytier(i) != null) {
                var klikDoskyX = x > this.hrac.getRytier(i).getSuradnice()[0] && x <= this.hrac.getRytier(i).getSuradnice()[1];
                var klikDoskyY = y > this.hrac.getRytier(i).getSuradnice()[2] && y <= this.hrac.getRytier(i).getSuradnice()[3];
                if (klikDoskyY && klikDoskyX) {
                    if (this.pohlad == Pohlad.DEFAULT) {
                        this.pohlad = Pohlad.ROZHODOVANIE;
                        return this.zobrazMozneAkcie(this.hrac.getRytier(i));
                    } else if (this.pohlad == Pohlad.NASAD) {
                        if (this.hrac.getDoska().getNasadzuje() != null){
                            this.hrac.getRytier(i).nasadPredmet(this.hrac.getDoska().getNasadzuje());
                        }
                        this.pohlad = Pohlad.DEFAULT;
                        return null;
                    }
                }

            }
        }
        this.pohlad = Pohlad.DEFAULT;
        return null;
    }

    /**
     * Metoda určuje či sme klikli na predmet v inventári, ak áno zobrazia sa KA, kt. môžme vykonať s predmetom
     * @param x
     * @param y
     * @return - vrati akcie na zobrazenie
     */
    public ArrayList<KontextovaAkcia> klikNaInventar(int x, int y) {
        if (this.pohlad == Pohlad.DEFAULT) {
            for (Predmet predmet : this.hrac.getInventar()) {
                var klikDoskyX = x > predmet.getSuradnice()[0] && x <= predmet.getSuradnice()[1];
                var klikDoskyY = y > predmet.getSuradnice()[2] && y <= predmet.getSuradnice()[3];
                if (klikDoskyY && klikDoskyX) {
                    this.pohlad = Pohlad.INVENTAR;
                    return this.zobrazMozneAkcie(predmet);
                }
            }

        }
        return null;

    }

    /**
     * Ak sa mapa nachádza v pohlade ROZHODOVANIE/NAKUP a klikneme na obrázok jednej z KA vykoná sa akcie danej KA ak je to možné a pohlad sa zmení na NASTAVENIA/OBCHOD.
     * Ak klikneme mimo obrázkov, všetky KA sa skryjú a mapa sa vráti do stavu NASTAVENIA/OBCHOD.
     * @param x
     * @param y
     */
    public void klikNaAkciu(int x, int y) {
        KontextovaAkcia kliknutaAkcia = null;
        for (KontextovaAkcia akcia : this.akcie) {
            var suradnice = akcia.getSuradnice();
            var klikX = x > suradnice[0] && x <= suradnice[1];
            var klikY = y > suradnice[2] && y <= suradnice[3];
            if (klikX && klikY) {
                akcia.klikolAkciu();
                kliknutaAkcia = akcia;
                for (KontextovaAkcia akcia1 : this.akcie) {
                    akcia1.skrySpravu();
                }

            }
        }
        if (this.pohlad == Pohlad.ROZHODOVANIE) {
            for (KontextovaAkcia akcia1 : akcie) {
                akcia1.skrySpravu();
            }
            this.pohlad = Pohlad.DEFAULT;
        } else if (this.pohlad == Pohlad.NAKUP) {
            for (KontextovaAkcia akcia1 : akcie) {
                akcia1.skrySpravu();
            }
            this.pohlad = Pohlad.OBCHOD;
        } else if (this.pohlad == Pohlad.INVENTAR) {
            for (KontextovaAkcia akcia1 : akcie) {
                akcia1.skrySpravu();
            }
            if (kliknutaAkcia instanceof Vylepsit || kliknutaAkcia instanceof Predat) {
                this.pohlad = Pohlad.DEFAULT;
            } else {
                this.pohlad = Pohlad.NASAD;
            }
        }
    }

    /**
     * Ak je obchod otvorený (zobrazený na mape), pohlad je NASTAVENIA a klikneme na ikonu obchodu zobrazí sa obchod s tovarom v ňom a pohlad sa zmení na OBCHOD
     * @param x
     * @param y
     */
    public void klikNaObchod(int x, int y) {
        var suradnice = this.obchod.getSuradnice();
        var klikX = x > suradnice[0] + 150 && x <= suradnice[1] + 150;
        var klikY = y > suradnice[2] && y <= suradnice[3];
        var rerollX = x > 828 && x <= 980;
        var rerollY = y > 450 && y <= 525;
        var klikZavri = (x > 175 && x <= 995) && (y > 50 && y <= 542);
        if (this.pohlad == Pohlad.DEFAULT) {
            if (klikX && klikY) {
                this.obchod.zobrazObchod();
                this.pohlad = Pohlad.OBCHOD;
            }
        } else if (this.pohlad == Pohlad.OBCHOD) {
            if (!klikZavri) {
                this.obchod.skryObchod();
                this.pohlad = Pohlad.DEFAULT;
            }
            if (rerollX && rerollY) {
                this.obchod.rerollShop();
            }
        }
    }
    public void klikNaEfekt(int x, int y) {
        if (this.pohlad == Pohlad.VYBER_EFEKTOV) {
            var suradniceX = x > 928 && x <= 1080;
            var suradniceY = y > 550 && y <= 625;
            if (suradniceX && suradniceY && this.efektyHry.getNumberOfOptions() == 4) {
                this.efektyHry.skryEfekty();
                this.efektyHry.vyberEfekty();
                this.efektyHry.zobrazMoznosti();
            }
            suradniceX = x > 898 && x <= 1089;
            suradniceY = y > 386 && y <= 464;
            if (suradniceX && suradniceY && !this.efektyHry.paid()) {
                if (this.hrac.zmenStavPenazi(-100)) {
                    this.efektyHry.unlockForth();
                }
            }
            for (int i = 0; i < 4; i++) {
                suradniceX = x > 156 + (i * 243) && x <= 370 + (i * 243);
                suradniceY = y > 202 && y <= 498;
                if (i != 3 || this.efektyHry.paid()) {
                    if (suradniceY && suradniceX) {
                        this.efekt = this.efektyHry.getEfekty()[i];
                        var obrazokEfektu = new Image(this.efekt.getIkona(),36 ,786);
                        obrazokEfektu.makeVisible();
                        this.efektyHry.skryEfekty();
                        this.obrazokEfektu = new Image(this.efekt.getObrazok(), 100, 554);
                        this.pohlad = this.predosli;
                        this.manazerEventov.zapniHru();
                        this.arena.getVyhry().setEfekt(this.efekt);
                        this.arena.setEfekt(this.efekt);
                        break;
                    }
                }
            }
        } else if (this.pohlad == Pohlad.DEFAULT) {
            var klik = (x > 25 && x <= 175) && (y > 775 && y <= 925);
            var klikObrazok = (x > 100 && x <= 316) && (y > 554 && y <= 850);
            if (klik) {
                if (!this.vidimEfekt) {
                    this.obrazokEfektu.makeVisible();
                    this.vidimEfekt = true;
                    return;
                }
            }
            if (!klikObrazok) {
                if (this.vidimEfekt) {
                    this.obrazokEfektu.makeInvisible();
                    this.vidimEfekt = false;
                }
            }
        }

    }

    private void klikNaStavEfektu(int x, int y) {
        var klikVyhoda = (x >= 401 && x < 617) && (y >= 150 && y < 446);
        var klikNevyhoda = (x >= 643 && x < 859) && (y >= 150 && y < 446);
        if (klikVyhoda) {
            this.stavEfektu = StavEfektu.VYLEPSENE;
            this.arena.getVyhry().setStavEfektu(this.stavEfektu);
            this.pohlad = this.predosli;
            this.manazerEventov.zapniHru();
            this.efektyHry.skryVylepsenie();
            this.efektyHry.zobrazRam(this.stavEfektu);
        } else if (klikNevyhoda) {
            this.stavEfektu = StavEfektu.BEZ_NEVYHODY;
            this.arena.getVyhry().setStavEfektu(this.stavEfektu);
            this.pohlad = this.predosli;
            this.manazerEventov.zapniHru();
            this.efektyHry.skryVylepsenie();
            this.efektyHry.zobrazRam(this.stavEfektu);
        }
    }
    private boolean klikNaVezu(int x, int y) {
        if (this.pohlad == Pohlad.DEFAULT) {
            return this.veza.klikolNaVezu(x, y, true);
        } else if (this.pohlad == Pohlad.VEZA){
            this.veza.klikolNaVezu(x, y, false);
            this.setPohlad(Pohlad.DEFAULT);
        }
        return false;
    }

    private void klikNaPrirucku(int x, int y) {
        var klikX = x >= 500 && x < 705;
        var klikY1 = y >= 193 && y < 255;
        var klikY2 = y >= 270 && y < 332;
        var klikY3 = y >= 347 && y < 409;
        var klikY4 = y >= 424 && y < 486;
        var klikY5 = y >= 501 && y < 563;
        if (this.pohlad == Pohlad.PRIRUCKA) {
            var klikNaX = (x >= 760 && x < 784) && (y >= 115 && y < 139);

            if (klikX && klikY1) {
                this.prirucka.zmenStavPrirucky(StavPrirucky.OVLADANIE);
            } else if (klikX && klikY2) {
                this.prirucka.zmenStavPrirucky(StavPrirucky.ZAKLADY_1);
            } else if (klikX && klikY3) {
                this.prirucka.zmenStavPrirucky(StavPrirucky.FAZY);
            } else if (klikX && klikY4) {
                this.prirucka.zmenStavPrirucky(StavPrirucky.SCHOPNOSTI);
            } else if (klikX && klikY5) {
                this.prirucka.zmenStavPrirucky(StavPrirucky.RANKY);
            } else if (klikNaX) {
                this.prirucka.klikNaX();
            }
        } else if (this.pohlad == Pohlad.PRIRUCKA_TEXTY) {
            var klikNaX = (x >= 1061 && x < 1085) && (y >= 115 && y < 139);
            if (klikNaX) {
                this.prirucka.klikNaX();
            }
            var klikNaPredchadzajuce = (x >= 162 && x < 210) && (y >= 596 && y < 633);
            var klikNaNasledujuce = (x >= 1036 && x < 1073) && (y >= 596 && y < 633);
            if (klikNaPredchadzajuce) {
                this.prirucka.klikNaPredchadzajuce();
            } else if (klikNaNasledujuce) {
                this.prirucka.klikNaNasledovne();
            }
        }
    }

    /**
     * Metóda slúži na zobrazenie všetkých KA použiteľných v danej situácii
     * @param p - Rytier alebo Predmet, ktorého KA majú byť zobrazené
     * @return - vráti všetky KA danej Predavatelnej veci
     */
    public ArrayList<KontextovaAkcia>  zobrazMozneAkcie(Predavatelne p) {
            ArrayList<KontextovaAkcia> akcieNaZobrazenie;
            akcieNaZobrazenie = p.pouzitelneSpravy();
            for (KontextovaAkcia akcia : akcieNaZobrazenie) {
                akcia.zobrazSpravu();
            }
            return akcieNaZobrazenie;
    }


    public Arena getArena() {
        return this.arena;
    }

    public Obchod getObchod() {
        return this.obchod;
    }

    public Cech getCech() {
        return this.cech;
    }

    public Cvicisko[] getCvicisko() {
        Cvicisko[] cviciska = new Cvicisko[3];
        cviciska[0] = this.cvicisko;
        cviciska[1] = this.cvicisko2;
        cviciska[2] = this.cvicisko3;
        return cviciska;
    }

    public Krcma[] getKrcma() {
        Krcma[] krcmy = new Krcma[3];
        krcmy[0] = this.krcma;
        krcmy[1] = this.krcma2;
        krcmy[2] = this.krcma3;
        return krcmy;
    }
    public void setPohlad(Pohlad pohlad) {
        this.predosli = this.pohlad;
        this.pohlad = pohlad;
    }

    public Hrac getHrac() {
        return this.hrac;
    }

    public void ukoncenie() {
        this.manazer.stopManagingObject(this);
    }

    /**
     * Metoda informuje hraca o jeho umiestneni
     */
    public void vyhodnotenie() {
        var vyhodnoteny = this.arena.vyhodnotenie();
        this.vyhodnotenieHry.makeVisible();
        var poradie = 0;
        for (Hodnotitelne hodnotitelne : vyhodnoteny) {
            Text text = new Text(hodnotitelne.getMeno(), 628 - ((hodnotitelne.getMeno().length() * 11) / 2), 240 + (poradie * 44));
            var string = "" + hodnotitelne.getSkore();
            Text skore = new Text(string, 802 - ((string.length()* 11) / 2), 240 + (poradie * 44));
            text.changeFont("Langar", FontStyle.BOLD, 24);
            skore.changeFont("Langar", FontStyle.BOLD, 24);
            text.makeVisible();
            skore.makeVisible();
            poradie++;
        }
        this.manazer.stopManagingObject(this);
    }

    public Pohlad getPohlad() {
        return this.pohlad;
    }

    public Efekt getEfekt() {
        return this.efekt;
    }
    public Image getMenuObrazok() {return this.menuObrazok;}

    public Pohlad getPredosli() {
        return predosli;
    }

    public void setPredosli(Pohlad predosli) {
        this.predosli = predosli;
    }

    public StavEfektu getStavEfektu() {
        return this.stavEfektu;
    }

    public void upgradeCvicisko() {
        this.cviciskoUpgrade++;
    }

    public int getCviciskoUpgrade() {
        return cviciskoUpgrade;
    }
}
