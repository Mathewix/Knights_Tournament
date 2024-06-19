package HernyBalik.StraznaVeza;

public class plusTri extends AkciaVeze{
    public plusTri(StraznaVeza straznaVeza) {
        super(straznaVeza, "Obrazky/plus3.png", 420, 3, 970, 166);
    }

    @Override
    public int[] getSuradnice() {
        return new int[]{970, 1157, 166, 202};
    }
}
