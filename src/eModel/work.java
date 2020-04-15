package eModel;

import java.util.List;
import java.util.Vector;

public class work {
    public static List<work> works = new Vector<>();
    private Integer id;
    private String status;
    private Integer eID;//占用设备ID
    private Integer cTime;//使用设备时间
    private Integer eTime;//占用设备时长

    public work(){
        this.status = "等待使用";
    }

    public work(Integer cTime, Integer eTime, Integer eID) {
        this.id = works.size() + 1;
        this.cTime = cTime;
        this.eTime = eTime;
        this.eID = eID;
        this.status = "等待使用";
    }
    public work(Integer id, Integer cTime, Integer eTime, Integer eID) {
        this.id = id;
        this.cTime = cTime;
        this.eTime = eTime;
        this.eID = eID;
        this.status = "等待使用";
    }

    public Integer geteID() {
        return eID;
    }

    public void seteID(Integer eID) {
        this.eID = eID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getcTime() {
        return cTime;
    }

    public void setcTime(Integer cTime) {
        this.cTime = cTime;
    }

    public Integer geteTime() {
        return eTime;
    }

    public void seteTime(Integer eTime) {
        this.eTime = eTime;
    }
}
