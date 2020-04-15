package mModel;

import java.util.List;
import java.util.Vector;

public class memory {
    public static List<memory> free = new Vector();//空闲
    public static List<memory> occupied = new Vector();//占用

/*    private static int totalSize  = 10;
    private static int occupiedSize = 0;//被占用总大小*/
    public memory next = null;
    public memory before = null;
    private String status = "忙碌";//状态
    private Integer wId;//进程号
    private Integer size;
    private Integer add;//分区始址
    public memory () {
        this.size = 10;
        this.status = "空闲";
        this.add = 0;
    }
    memory (Integer size) {
        this.size = size;
    }

    public Integer getwId() {
        return wId;
    }

    public void setwId(Integer wId) {
        this.wId = wId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getAdd() {
        return add;
    }

    public void setAdd(Integer add) {
        this.add = add;
    }

    public memory getNext() {
        return next;
    }

    public void setNext(memory next) {
        this.next = next;
    }

    public memory getBefore() {
        return before;
    }

    public void setBefore(memory before) {
        this.before = before;
    }
}

