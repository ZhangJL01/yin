package pModel;

import java.util.Comparator;

public class comHRRN implements Comparator<process> {
    @Override
    public int compare(process o1, process o2) {
        if (o1.getpRes() < o2.getpRes()) {
            return -1;
        } else if (o1.getpRes() == o2.getpRes()) {
            return 0;
        } else {
            return 1;
        }
    }
}
