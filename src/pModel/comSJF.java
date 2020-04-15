package pModel;

import java.util.Comparator;

public class comSJF implements Comparator<process> {
    @Override
    public int compare(process o1, process o2) {
        return o1.getpSize() - o2.getpSize();
    }
}
