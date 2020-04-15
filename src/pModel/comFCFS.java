package pModel;

import java.util.Comparator;

public class comFCFS implements Comparator<process> {
    @Override
    public int compare(process o1, process o2) {
        return o1.getcTime() - o2.getcTime();
    }
}
