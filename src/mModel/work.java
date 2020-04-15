package mModel;

import java.util.List;
import java.util.Vector;

public class work {
    public static List<work> works = new Vector<>();
    private Integer id;
    private Integer size;//占用内存大小
    private Integer time;//占用时间
    public work (Integer size, Integer time) {
        this.id = works.size() + 1;
        this.size = size;
        this.time = time;
        works.add(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
