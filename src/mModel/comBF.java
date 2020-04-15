package mModel;


import java.util.Comparator;

public class comBF implements Comparator<memory> {
    @Override
    public int compare(memory o1, memory o2) {
        return o1.getSize() - o2.getSize();
    }
}
