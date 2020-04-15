package eModel;

import java.util.List;
import java.util.Vector;

public class equipments {
    private static List<equipments> equipments = new Vector<>();
    private Integer id;
    private String status;
    public equipments(){
        this.id = equipments.size() + 1;
        this.status = "空闲";
        equipments.add(this);
    }

    public static List<eModel.equipments> getEquipments() {
        return equipments;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
