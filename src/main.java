
import frame.equFrame;
import frame.mainFrame;
import frame.memFrame;
import pModel.*;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void ch (Integer a, Integer b){
        int t = a;
        a = b;
        b = t;
        System.out.println(a + " " + b);
    }
    public static void main(String[] args) {
        process p1 = new process(1, 0, 3, 1);
        process p2 = new process(2, 1, 5, 2);
        process p3 = new process(3, 2, 4, 1);
        PCB.PCB.add(p1);
        PCB.PCB.add(p2);
        PCB.PCB.add(p3);
//todo 美化


        mainFrame.showUI();
        /*JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            SmartLookAndFeel.setTheme("Green");
            SmartLookAndFeel lookFeel = new SmartLookAndFeel();
            javax.swing.UIManager.setLookAndFeel(lookFeel);

        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ToolMainFrame().setVisible(true);
            }
        });*/

    }
}
