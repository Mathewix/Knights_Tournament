package HernyBalik.StraznaVeza;

public class plusPat extends AkciaVeze{
    public plusPat(StraznaVeza straznaVeza) {
        super(straznaVeza, "Obrazky/plus5.png", 600, 5, 970, 202);
    }

    @Override
    public int[] getSuradnice() {
        return new int[]{970, 1157, 202, 238};
    }
}
