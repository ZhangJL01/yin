package frame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class mainFrame {
    public static void showUI() {
        UI();
        JFrame jF = new JFrame("YinOS模拟");
        jF.setSize(400, 200);
        //设置退出进程的方法
        //jf.setDefaultCloseOperation(3);
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置居中显示用3
        jF.setLocationRelativeTo(null);

        //流式布局管理器
        java.awt.FlowLayout flow = new java.awt.FlowLayout();
        jF.setLayout(flow);  //给窗体设置为流式布局——从左到右然后从上到下排列自己写的组件顺序
        Dimension d = new Dimension(150, 45);

        JLabel wel = new JLabel("    欢迎使用YinOS模拟系统！    ");
        Font f = new Font("行楷", Font.PLAIN, 25);
        wel.setFont(f);
        wel.setSize(300, 60);
        //wel.setPreferredSize(d);
        //进程管理
        JButton pro = new JButton("进程调度模拟");
        pro.setPreferredSize(d);
        pro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proFrame.showUI();
            }
        });
        //文件系统
        JButton file = new JButton("文件系统模拟");
        file.setPreferredSize(d);
        file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileFrame.showUI();
            }
        });
        //内存管理
        JButton mem = new JButton("内存分配模拟");
        mem.setPreferredSize(d);
        mem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memFrame.showUI();
            }
        });
        //设备管理
        JButton equ = new JButton("设备管理模拟");
        equ.setPreferredSize(d);
        equ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equFrame.showUI();
            }
        });

        jF.add(wel);
        jF.add(pro);
        jF.add(file);
        jF.add(mem);
        jF.add(equ);

        jF.setVisible(true);

    }

    public JPanel border(TitledBorder t, List<Component> l, Dimension d) {
        //边界效果
        JPanel pM = new JPanel();
        pM.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));//边框之间的距离
        pM.setLayout(new BoxLayout(pM, BoxLayout.Y_AXIS));
        JPanel comp = new JPanel();
        l.forEach(component -> {
            comp.add(component);
        });
        TitledBorder x = t;
        comp.setBorder(x);
        pM.setSize(d);
        pM.add(Box.createRigidArea(new Dimension(0, 10)));
        pM.add(comp);
        pM.setOpaque(true);//不透明
        return pM;
    }
    public  static void UI () {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}
