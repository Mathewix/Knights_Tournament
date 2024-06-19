package HernyBalik;

/**
 * Enum pohľadov mapy, na základe kt. mapa rozhoduje na čo reaguje pri kliknutí hráčom
 */
public enum Pohlad {
    //nasadenie predmetov
    INVENTAR,
    NASAD,
    //zobrazenie obchodu
    OBCHOD,
    //defaultny pohlad
    DEFAULT,
    //ked su zobrazene kontextove akcie
    ROZHODOVANIE,
    //nakup v obchode
    NAKUP,
    //zastavenie casu
    MENU,
    VEZA,
    KONIEC, VYHODNOTENIE_ARENY, VYBER_EFEKTOV, PRIRUCKA, PRIRUCKA_TEXTY, VYBER_STAVU_EFEKTU;
}
