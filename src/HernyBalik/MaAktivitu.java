package HernyBalik;

/**
 * interface spájajúci triedy, ktoré môže spravovať ManazerEventov na základe trvania aktivít v jednotlivých triedach
 */
public interface MaAktivitu {
    void vykonajAktivitu();
    int getDobaTrvania(MaAktivitu maAktivitu);

    int getKoniecAkcie();
}
