package HernyBalik;


import HernyBalik.StraznaVeza.StraznaVeza;
import Lokality.*;
import Predmety.Predmet;
import Rytieri.ObycajnyRytier;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.Manager;
import fri.shapesge.Text;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Trieda ManazerEventov je hlavná trieda hry zodpovedná za správu akcií vykonávaných triedami MaAktivitu v daných časoch
 * a zodpovedá za ukončenie hry po uplinutí dĺžky hry.
 * Trieda na toto využíva triedu Manager z knižnice ShapesGE, ktorým spravuje čas hry každú sekundu
 */
public class ManazerEventov implements MaAktivitu{
    private Arena arena;
    private Obchod obchod;
    private StraznaVeza veza;
    private final Manager manager;
    private int hodiny;
    private boolean efektObchodnik;
    private final Text cas;
    private final int koniecHry;
    private int fazaHry;
    private final HashMap<Integer, ArrayList<MaAktivitu>> aktivity;
    private final HashMap<Integer, ArrayList<Predmet>> predmety;


    private Mapa mapa;
    private boolean spustene;
    private final Image spusteneObrazok;
    private EfektyHry efektyHry;

    /**
     * Konštruktor inicializuje koniec hry a pridá ho do HashMapy aktivit ktore ma spravovat
     * @param dlzkaHry - nastaví aká dlhá má hra byť
     */
    public ManazerEventov(int dlzkaHry) {
        this.fazaHry = 0;
        this.manager = new Manager();
        this.hodiny = 0;
        this.koniecHry = dlzkaHry;
        this.aktivity = new HashMap<>();
        this.predmety = new HashMap<>();
        var toto =  new ArrayList<MaAktivitu>();
        toto.add(this);
        this.aktivity.put(dlzkaHry, toto);
        this.cas = new Text("", 40, 40);
        this.cas.changeFont("", FontStyle.BOLD, 30);
        this.cas.changeColor("#F4F4E3");
        this.spusteneObrazok = new Image("Obrazky/play.png", 142, 12);
        this.spusteneObrazok.makeVisible();
    }
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public void setObchod(Obchod obchod) {
        this.obchod = obchod;
    }

    public void novaFazaHry() {
        if (this.hodiny - 1 == (this.fazaHry * this.koniecHry / 4)) {
            this.fazaHry++;
            System.out.println("Faza hry: " + this.fazaHry);
        }
    }


    /**
     * Metóda tik je z knižnice ShapesGE a po každej sekunde vykoná metódy tejto triedy na základe stavu hodín,
     * ktoré každú sekundu zvyšuje.
     * Ak sa hodiny zhodujú z kľúčom v HashMape aktivít, vykoná aktivitu vo všetkých triedach MaAktivitu pre daný kľúč
     * a odstráni tieto aktivity z HashMapy
     */
    public void tik() {
        this.hodiny++;
        this.vykonajAkciuAreny();
        if (this.hodiny < 4700) {
            this.zmenStavObchodu();
        }
        this.novaFazaHry();

        if (this.hodiny % 10 == 0) {
            this.vykonajAkciuVeze();
            this.cas();
        }
        if (this.efektObchodnik) {
            if (this.hodiny % 1000 == 350) {
                this.mapa.getHrac().zmenStavPenazi( -((int) (this.mapa.getHrac().getPeniaze() / 10)));
            }
        }
        for (ArrayList<MaAktivitu>  aktivity : this.aktivity.values()) {
            for (MaAktivitu miesta : aktivity) {
                if (miesta instanceof TreningoveMiesto tm) {
                    tm.progresRytiera(this.hodiny);
                }
            }
        }
        if (this.hodiny < 4780) {
            if (this.aktivity.containsKey(this.hodiny)) {
                for (var miestost : this.aktivity.get(this.hodiny)) {
                    miestost.vykonajAktivitu();
                }
                this.aktivity.remove(this.hodiny);
            }
        }
        if (this.predmety.containsKey(this.hodiny % 200)) {
            for (Predmet predmet : this.predmety.get(this.hodiny % 200)) {
                predmet.vykonajAktivitu();
            }
        }
        if (this.mapa.getEfekt() == Efekt.SLACHTA) {
            var rytieri = this.mapa.getHrac().getRytieri();
            if (this.mapa.getStavEfektu() != StavEfektu.VYLEPSENE) {
                for (ObycajnyRytier obycajnyRytier : rytieri) {
                    if (obycajnyRytier.getCas() == (this.hodiny % 300)) {
                        obycajnyRytier.vyrobPeniaze();
                    }
                }
            } else {
                for (ObycajnyRytier obycajnyRytier : rytieri) {
                    if (obycajnyRytier.getCas() == (this.hodiny % 200)) {
                        obycajnyRytier.vyrobPeniaze();
                    }
                }
            }

        }
        if (this.hodiny == 1350) { //1350
            this.pozastavHru();
            this.efektyHry.zobrazMoznosti();
            this.mapa.setPredosli(this.mapa.getPohlad());
            this.mapa.setPohlad(Pohlad.VYBER_EFEKTOV);
        } else if (this.hodiny == 1360) {
            /*
            if (this.mapa.getEfekt() == Efekt.OBCHODNIK) {
                this.efektObchodnik = true;
            }
             */
            if (this.mapa.getEfekt() == Efekt.SEBAVEDOMI_RYTIERI) {
                this.mapa.getHrac().odzbrojRytierov();
            }
        }
        if (this.hodiny == 2400) {
            this.pozastavHru();
            this.mapa.setPredosli(this.mapa.getPohlad());
            this.mapa.setPohlad(Pohlad.VYBER_STAVU_EFEKTU);
            this.efektyHry.zobrazVylepsenie(this.mapa.getEfekt());
        }
        if (this.hodiny == 2410) {
            if (this.mapa.getStavEfektu() == StavEfektu.BEZ_NEVYHODY && this.mapa.getEfekt() == Efekt.OSUDOVA_VYPRAVA) {
                this.mapa.getHrac().terapiaPreRytierov();
            }
            /*
            if (this.mapa.getStavEfektu() == StavEfektu.BEZ_NEVYHODY && this.mapa.getEfekt() == Efekt.OBCHODNIK) {
                this.efektObchodnik = false;
            }
             */
        }
        if (this.hodiny == 4800) {
            this.mapa.vyhodnotenie();
            this.ukonci();
        }
        if (this.hodiny == 4810) {
            this.mapa.vyhodnotenie();
            this.ukonci();
        }
    }

    /**
     * zobrazuje cas v hre vo forme minuty : sekundy (00:00)
     */
    private void cas() {
        int minuty = (this.hodiny / 10) / 60;
        var sekundy = (this.hodiny / 10) % 60;
        String minutyT = "" + minuty;
        String sekundyT = "" + sekundy;
        if (minuty < 10) {
            minutyT = "0" + minuty;
        }
        if (sekundy < 10) {
            sekundyT = "0" + sekundy;
        }
        this.cas.changeText(minutyT + " : " + sekundyT);
        this.cas.makeInvisible();
        this.cas.makeVisible();
    }

    /**
     * Periodicky sa aréna otvorí, vyhodnotí pozície rytierov v nej a potom zavrie
     */
    public void vykonajAkciuAreny() {
        if (this.hodiny < 1400) {
            if ((this.hodiny % this.arena.getCyklus()) == 0) {
                this.arena.zmenStav(StavAreny.REGISTRACIA);
                if (this.mapa.getPohlad() == Pohlad.OBCHOD) {
                    this.obchod.skryObchod();
                    this.obchod.zobrazObchod();
                }
                this.arena.pridajRytierov();
                System.out.println(this.arena.getStav());
            } else if ((this.hodiny % this.arena.getCyklus() == 100 && this.hodiny > this.arena.getCyklus() && this.arena.getStav() == StavAreny.REGISTRACIA)) {
                this.arena.zmenStav(StavAreny.VYHODNOTENIE);
                this.arena.vyhodnotenieTurnaja();
                var pohlad = this.mapa.getPohlad();
                this.mapa.setPredosli(pohlad);
                this.mapa.setPohlad(Pohlad.VYHODNOTENIE_ARENY);
                this.pozastavHru();
            } else if ((this.hodiny % this.arena.getCyklus() == 150 && this.hodiny > this.arena.getCyklus() && this.arena.getStav() == StavAreny.VYHODNOTENIE)) {
                this.arena.vratRytierov(true, null);
                this.arena.zmenStav(StavAreny.ZATVORENA);
            }
        } else {
            for (int i = 1; i <= 7; i++) {
                if (this.hodiny == 1200 + (i * 450) + (((i - 1) * i * 50)) / 2) {
                    this.arena.zmenStav(StavAreny.REGISTRACIA);
                    if (this.mapa.getPohlad() == Pohlad.OBCHOD) {
                        this.obchod.skryObchod();
                        this.obchod.zobrazObchod();
                    }
                    this.arena.pridajRytierov();
                    System.out.println(this.arena.getStav());
                } else if ((this.hodiny == 1300 + (i * 450) + (((i - 1) * i * 50)) / 2 && this.arena.getStav() == StavAreny.REGISTRACIA)) {
                    this.arena.zmenStav(StavAreny.VYHODNOTENIE);
                    this.arena.vyhodnotenieTurnaja();
                    var pohlad = this.mapa.getPohlad();
                    this.mapa.setPredosli(pohlad);
                    this.mapa.setPohlad(Pohlad.VYHODNOTENIE_ARENY);
                    this.pozastavHru();
                } else if ((this.hodiny == 1350 + (i * 450) + (((i - 1) * i * 50)) / 2 && this.arena.getStav() == StavAreny.VYHODNOTENIE)) {
                    this.arena.vratRytierov(true, null);
                    this.arena.zmenStav(StavAreny.ZATVORENA);
                }
            }
        }
    }

    /**
     * Metóda pridá do Hashmapu aktivity akciu s kľúčom koniec
     * @param tm - trieda ktorá má v čase koniec vykonať svoju akciu
     * @return - vráti čas v ktorom má akcia skončiť
     */
    public int pridajAkciuTreningovehoMiesta(MaAktivitu tm) {
        var koniec = this.hodiny + tm.getDobaTrvania(tm);

        if (this.aktivity.containsKey(koniec)) {
            this.aktivity.get(koniec).add(tm);
        } else {
            var treningoveMiesta = new ArrayList<MaAktivitu>();
            treningoveMiesta.add(tm);
            this.aktivity.put(koniec, treningoveMiesta);
        }
        return koniec;
    }

    public void pridajAkciuPredmetu(Predmet predmet) {
        int zvysok = this.hodiny % 200;
        if (this.predmety.containsKey(zvysok)) {
            this.predmety.get(zvysok).add(predmet);
        } else {
            var skupinaPredmetov = new ArrayList<Predmet>();
            skupinaPredmetov.add(predmet);
            this.predmety.put(zvysok, skupinaPredmetov);
        }
        predmet.setCas(zvysok);
    }
    public void odstranAkciuPredmetu(Predmet predmet) {
        this.predmety.get(predmet.getCas()).removeIf(p -> predmet == p);

    }

    public void vykonajAkciuVeze() {
            this.veza.akcia();
    }
    /**
     * Metóda odstráni z Hashmapu akciu tm v danom case
     * @param tm - trieda, kt. akciu sme odstranili
     */
    public void odstranAkciuTreningovehoMiesta(MaAktivitu tm) {
        var koniec = tm.getKoniecAkcie();
        this.aktivity.get(koniec).remove(tm);
        if (this.aktivity.get(koniec).isEmpty()) {
            this.aktivity.remove(koniec);
        }
    }

    /**
     * Periodicky sa bude meniť stav obchodu (otvárať a zatvárať)
     */
    public void zmenStavObchodu() {
        if (this.hodiny % this.obchod.getCyklus() == 0) {
            this.obchod.zmenStav();
        }
    }

    /**
     * vyhodnoti hru ked prejde cely cas hry
     */
    @Override
    public void vykonajAktivitu() {
        this.manager.stopManagingObject(this);
        this.mapa.setPohlad(Pohlad.KONIEC);
        this.mapa.vyhodnotenie();
    }

    /**
     * vypne hru predcasne
     */
    public void ukonci() {
        this.manager.stopManagingObject(this);
        this.spustene = false;
        this.mapa.setPohlad(Pohlad.KONIEC);
        this.mapa.ukoncenie();
    }

    /**
     * pustí hodiny
     */
    public void zapniHru() {
        this.manager.manageObject(this);
        this.spustene = true;
        this.spusteneObrazok.makeInvisible();
        this.spusteneObrazok.changeImage("Obrazky/play.png");
        this.spusteneObrazok.makeVisible();
    }

    /**
     * zastavi hodiny
     */
    public void pozastavHru() {
        this.manager.stopManagingObject(this);
        this.spustene = false;
        this.spusteneObrazok.makeInvisible();
        this.spusteneObrazok.changeImage("Obrazky/pause.png");
        this.spusteneObrazok.makeVisible();
    }

    @Override
    public int getDobaTrvania(MaAktivitu maAktivitu) {
        return 0;
    }
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    @Override
    public int getKoniecAkcie() {
        return this.koniecHry;
    }
    public int getFazaHry() {
        return this.fazaHry;
    }

    public boolean isSpustene() {
        return spustene;
    }

    public void setEfektyHry(EfektyHry efektyHry) {
        this.efektyHry = efektyHry;
    }
    public Mapa getMapa() {
        return this.mapa;
    }

    public int getHodiny() {
        return this.hodiny;
    }

    public void setVeza(StraznaVeza veza) {
        this.veza = veza;
    }
    public void showTime() {
        this.cas.changeText( "00 : 00" );
        this.cas.makeVisible();
    }
}
