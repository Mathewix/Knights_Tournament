package HernyBalik.StraznaVeza;

public class plusJedna extends AkciaVeze{

    public plusJedna(StraznaVeza straznaVeza) {
        super(straznaVeza,"Obrazky/plus1.png", 160, 1, 970, 130);
    }

    @Override
    public int[] getSuradnice() {
        return new int[]{970, 1157, 130, 166};
    }
}
