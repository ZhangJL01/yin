package pModel;

import java.util.Comparator;

public class comPSA implements Comparator<process> {
    @Override
    public int compare(process o1, process o2) {
        return o1.getpLevel() - o2.getpLevel();
    }
}
