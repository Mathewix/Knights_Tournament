package Lokality;

import Rytieri.ObycajnyRytier;

/**
 * Interface spájajúci všetky triedy, v kt. sa môže rytier nachádzať
 */
public interface Miesto {

    void vratRytierov(boolean ukoncene, ObycajnyRytier rytier);

    int[] getSuradnice();
}
