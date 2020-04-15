package frame;

import mModel.algorithm;
import mModel.memory;
import mModel.work;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;


public class memFrame {
    public static memory root = new memory();
    public static memory first = root;
    public static DefaultTableModel model;
    public static DefaultTableModel blocks;
    public static JTable jt;
    public static JTable blockT;
    public static JPanel simulation;
    public static void showUI () {
        mainFrame.UI();
        //窗体类
        javax.swing.JFrame jf = new javax.swing.JFrame("内存管理模拟");
        //窗体大小（具体值跟电脑显示器的像素有关，可调整到合适大小）
        jf.setSize(760, 550);
        //设置居中显示用null，要放在设置大小之后
        jf.setLocationRelativeTo(null);

        //流式布局管理器
        java.awt.FlowLayout flow = new java.awt.FlowLayout();
        jf.setLayout(flow);  //给窗体设置为流式布局——从左到右然后从上到下排列自己写的组件顺序

        /**
         * 任务管理
         */
        //按钮
        javax.swing.JButton add = new javax.swing.JButton("添加");
        add.setSize(50, 30);//事件
        jf.add(add);   //给窗体添加一个按钮对象*/
        javax.swing.JButton del = new javax.swing.JButton("删除");
        del.setSize(50, 30);//事件
        jf.add(del);   //给窗体添加一个按钮对象*/


        java.util.List<Component> com = new ArrayList<>();
        com.add(add);
        com.add(del);

        //jf.getContentPane().add(new mainFrame().border(t1, com));

        JComboBox alg = new JComboBox(new Object[]{"首次适应", "循环首次适应",
                "最佳适应", "最坏适应"});

        JLabel a = new JLabel("选择算法:");
        JButton cer = new JButton("开始");
        cer.addActionListener(e -> {

            try
            {
                algorithm.start = first;
                cer.setEnabled(false);
                new Thread(() -> {
                    try{
                        switch (alg.getSelectedItem().toString()) {
                            case "首次适应":
                                while (!work.works.isEmpty()) {
                                    for (AtomicInteger i = new AtomicInteger(); i.get() < work.works.size(); ) {
                                        work w = work.works.get(i.get());
                                        if (!algorithm.FF(w)) {
                                            i.getAndIncrement();
                                        }
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException ie) {
                                            ie.printStackTrace();
                                        }
                                    }
                                }
                                break;
                            case "循环首次适应":
                                while (!work.works.isEmpty()) {
                                    for (AtomicInteger i = new AtomicInteger(); i.get() < work.works.size(); ) {
                                        work w = work.works.get(i.get());
                                        if (!algorithm.NF(w)) {
                                            i.getAndIncrement();
                                        }
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException ie) {
                                            ie.printStackTrace();
                                        }

                                    }
                                }
                                break;
                            case "最佳适应":
                                while (!work.works.isEmpty()) {
                                    for (AtomicInteger i = new AtomicInteger(); i.get() < work.works.size(); ) {
                                        work w = work.works.get(i.get());
                                        if (!algorithm.BF(w)) {
                                            i.getAndIncrement();
                                        }
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException ie) {
                                            ie.printStackTrace();
                                        }

                                    }
                                }
                                break;
                            case "最坏适应":
                                while (!work.works.isEmpty()) {
                                    for (AtomicInteger i = new AtomicInteger(); i.get() < work.works.size(); ) {
                                        work w = work.works.get(i.get());
                                        if (!algorithm.WF(w)) {
                                            i.getAndIncrement();
                                        }
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException ie) {
                                            ie.printStackTrace();
                                        }

                                    }
                                }
                                break;
                            default:
                                System.out.println("?");
                        }
                    } catch(Exception ie){

                        System.out.println("1" + ie.toString());
                        //ignored
                    }
                    cer.setEnabled(true);
                }).start();
            } catch(Exception ex)
            {
                System.out.println("2" + ex.toString());
            }

            /*SwingUtilities.invokeLater(newRunnable(){
                publicvoidrun(){
                    pane.setText(pane.getText()+msg);
                }
            });*/

        });
        TitledBorder manage = BorderFactory.createTitledBorder("作业管理");
        //com.clear();
        com.add(a);
        com.add(alg);
        com.add(cer);
        //边界效果
        jf.getContentPane().add(new mainFrame().border(manage, com, new Dimension(320, 320)));

        /**
         * 所有作业信息
         */
        //创建表头
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"PID","占用内存大小","占用时间"});//到达时间
        work.works.forEach(work -> model.addRow(new Vector(Arrays.asList(work.getId(), work.getSize(), work.getTime()))));

        jt= new JTable(model) {
            public boolean isCellEditable (int rowData,int column) {

                return false;}
        };
        jt.setPreferredScrollableViewportSize(new Dimension(300, 100));
        JScrollPane scrollPane = new JScrollPane(jt);   //支持滚动
        TitledBorder t2 = BorderFactory.createTitledBorder("所有作业信息");
        com.clear();
        com.add(scrollPane);

        //jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jt.getColumn("PID").setMinWidth(50);
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();

        //单元格居中
        cr.setHorizontalAlignment(JLabel.CENTER);

        //hr.setHorizontalAlignment(SwingConstants.CENTER);

        jt.setDefaultRenderer(Object.class, cr);
        jf.getContentPane().add(new mainFrame().border(t2, com, new Dimension(320, 320)));

        add.addActionListener(e -> {
            JFrame add1 = new JFrame("新建作业");
            add1.setSize(240, 180);
            //设置居中显示用3
            add1.setLocationRelativeTo(null);

            //流式布局管理器
            FlowLayout flow1 = new FlowLayout();
            add1.setLayout(flow1);  //给窗体设置为流式布局——从左到右然后从上到下排列自己写的组件顺序

            JLabel size = new JLabel("所需内存(KB):", SwingConstants.RIGHT);
            JLabel time = new JLabel("所需占用时间:", SwingConstants.RIGHT);

            JTextField s = new JTextField(10);
            JTextField t = new JTextField(10);

            add1.add(time);
            add1.add(t);
            add1.add(size);
            add1.add(s);

            JButton cer1 = new JButton("确认");
            cer1.setPreferredSize(new Dimension(80, 20));
            add1.add(cer1);
            cer1.addActionListener(e1 -> {
                if (s.isValid() && t.isValid()) {
                    work work = new work(Integer.parseInt(s.getText()), Integer.parseInt(t.getText()));
                    model.addRow(new Vector<>(Arrays.asList(work.getId(), work.getSize(), work.getTime())));
                    jt.validate();
                    jt.updateUI();
                    add1.dispose();
                }
            });
            add1.setVisible(true);
        });
        del.addActionListener(e -> {
            if (jt.getSelectedRow() != -1) {
                work.works.remove(jt.getSelectedRow());
                model.removeRow(jt.getSelectedRow());
                jt.validate();
                jt.updateUI();

                /*JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f, "该作业正在执行！", "错误", JOptionPane.ERROR_MESSAGE);
                // 创建一个新计时器
                new java.util.Timer().schedule(new TimerTask() {
                    public void run() {
                        f.setVisible(false);
                        f.dispose();
                    }
                }, 1000);*/
            }
        });

        /**
         * 内存分配表
         */

        blocks = new DefaultTableModel();
        blocks.setColumnIdentifiers(new Object[]{"内存块大小(KB)", "内存块始址(K)", "状态"});
        blocks.addRow(new Vector(Arrays.asList(root.getSize(), root.getAdd(), root.getStatus())));
        blockT = new JTable(blocks) {
            public boolean isCellEditable (int rowData,int column) {

                return false;}
        };
        blockT.setPreferredScrollableViewportSize(new Dimension(300, 100));
        JScrollPane sB = new JScrollPane(blockT);   //支持滚动
        TitledBorder distribution = BorderFactory.createTitledBorder("内存分配表");

        com.clear();
        com.add(sB);


        //单元格居中

        blockT.setDefaultRenderer(Object.class, cr);
        jf.getContentPane().add(new mainFrame().border(distribution, com, new Dimension(320, 320)));



        /**
         * 模拟演示
         */
        simulation = new JPanel();
        sim(first);
        com.clear();
        com.add(simulation);
        TitledBorder simulate = BorderFactory.createTitledBorder("分配结果模拟演示");
        jf.getContentPane().add(new mainFrame().border(simulate, com, new Dimension(640, 320)));


        //jf.pack();//根据预设大小调整窗口大小
        jf.setVisible(true);   //设置可见，放在代码最后一句
        //FCFS.fcfs();
    }

    public static void sim (memory m) {
        JButton button;
        if (m.getStatus().equals("空闲")) {
            button = new JButton("空闲");
        } else {
            button = new JButton("进程" + m.getwId());
        }
        button.setPreferredSize(new Dimension(60 * m.getSize(), 100));
        button.setMaximumSize(new Dimension(600, 100));
        button.setFont(new Font("宋体", Font.BOLD, 20));
        simulation.add(button);
    }
}

