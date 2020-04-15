package pModel;

import java.util.Comparator;

public class process {
    private Integer pID;
    private Integer cTime;//到达时间
    private Integer pSize;//处理时间
    private Integer nTime;//剩余时间
    private Integer pLevel;//优先级
    private Double pRes = 0.00;//响应比
    private Integer pStatus = 0;//状态 （0：就绪 1：执行 -1：阻塞）

    public process () {}
    public process(Integer pId, Integer cTime, Integer pSize, Integer pLevel){
        this.pID = pId;
        this.cTime = cTime;
        this.pSize = pSize;
        this.pLevel = pLevel;
        this.nTime = pSize;

    }
    public process (Integer pId, Integer pSize, Integer pLevel) {
        this.pID = pId;
        this.cTime = algorithm.i[0];
        this.pSize = pSize;
        this.pLevel = pLevel;
        this.nTime = pSize;
    }

    public  Double getpRes() {
        return pRes;
    }

    public void setpRes(Double pRes) {
        this.pRes = pRes;
    }

    public Integer getpStatus() {
        return pStatus;
    }

    public Integer getnTime() {
        return nTime;
    }

    public void setnTime(Integer nTime) {
        this.nTime = nTime;
    }

    public void setpStatus(Integer pStatus) {
        this.pStatus = pStatus;
    }

    public Integer getpLevel() {
        return pLevel;
    }

    public void setpLevel(Integer pLevel) {
        this.pLevel = pLevel;
    }

    public Integer getpID() {
        return pID;
    }

    public Integer getcTime() {
        return cTime;
    }

    public Integer getpSize() {
        return pSize;
    }

    public void setpSize(Integer pSize) {
        this.pSize = pSize;
    }

    public void setcTime(Integer cTime) {
        this.cTime = cTime;
    }

    public void setpID(Integer pID) {
        this.pID = pID;
    }
}