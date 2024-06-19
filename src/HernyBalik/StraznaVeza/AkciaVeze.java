package HernyBalik.StraznaVeza;

import fri.shapesge.Image;

public abstract class AkciaVeze {
    private final Image obrazok;
    private int cena;
    private int navysenie;
    protected StraznaVeza straznaVeza;
    public AkciaVeze(StraznaVeza straznaVeza,String obrazok, int cena, int navysenie, int x, int y) {
        this.straznaVeza = straznaVeza;
        this.obrazok = new Image(obrazok, x, y);
        this.cena = cena;
        this.navysenie = navysenie;
    }

    public void klikolAkciu() {
        if (this.straznaVeza.getHrac().zmenStavPenazi(-cena)) {
            this.straznaVeza.pridajZisk(navysenie);
        }
    }
    public void skry() {
        this.obrazok.makeInvisible();
    }
    public void zobraz() {
        this.obrazok.makeVisible();
    }
    public abstract int[] getSuradnice();
}
