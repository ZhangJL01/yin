package pModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PCB {
    public static List<process> PCB = new Vector<>();
    public static List<process> ready = new Vector<>();//就绪队列
    public static List<process> block = new Vector<>();//阻塞队列
    //public static List<process> working = new Vector<>();//正在执行

}
