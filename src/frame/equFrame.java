package frame;

import eModel.dao;
import eModel.equipments;
import mModel.algorithm;
import mModel.memory;
import eModel.work;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class equFrame {
    public static List<work> all = new Vector<>();
    public static List<work> ready = new Vector<>();
    public static JButton[] buttons;
    public static memory root = new memory();
    public static memory first = root;
    public static DefaultTableModel model;
    public static DefaultTableModel blocks;
    public static JTable jt;
    public static JTable blockT;
    public static JPanel simulation;
    public static void showUI () {
        mainFrame.UI();
        addEqu();
        //窗体类
        javax.swing.JFrame jf = new javax.swing.JFrame("设备管理模拟");
        //窗体大小（具体值跟电脑显示器的像素有关，可调整到合适大小）
        jf.setSize(600, 550);
        //设置居中显示用null，要放在设置大小之后
        jf.setLocationRelativeTo(null);

        //流式布局管理器
        java.awt.FlowLayout flow = new java.awt.FlowLayout();
        jf.setLayout(new BorderLayout());  //给窗体设置为流式布局——从左到右然后从上到下排列自己写的组件顺序

        JPanel cen = new JPanel();
        cen.setLayout(new BorderLayout());

        /**
         * 任务管理
         */
        //按钮
        javax.swing.JButton add = new javax.swing.JButton("添加");
        add.setSize(100, 40);//事件
        jf.add(add);   //给窗体添加一个按钮对象*/
        javax.swing.JButton del = new javax.swing.JButton("删除");
        del.setSize(100, 40);//事件
        jf.add(del);   //给窗体添加一个按钮对象*/


        java.util.List<Component> com = new ArrayList<>();
        com.add(add);
        com.add(del);

        //jf.getContentPane().add(new mainFrame().border(t1, com));



        JButton cer = new JButton("开始");
        cer.addActionListener(e -> {

            try
            {
                cer.setEnabled(false);
                new Thread(() -> {
                    try{
                        dao.op();
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
        com.add(cer);
        //边界效果
        jf.getContentPane().add(new mainFrame().border(manage, com, new Dimension(320, 320)), BorderLayout.NORTH);

        /**
         * 所有作业信息
         */
        //创建表头
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"PID","使用设备时间","使用设备时长", "使用设备ID"});
        work.works.forEach(work -> model.addRow(new Vector(Arrays.asList(work.getId(), work.getcTime(),
                work.geteTime(), work.geteID()))));

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

        jt.getColumn("PID").setPreferredWidth(40);
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();

        //单元格居中
        cr.setHorizontalAlignment(JLabel.CENTER);

        //hr.setHorizontalAlignment(SwingConstants.CENTER);

        jt.setDefaultRenderer(Object.class, cr);
        cen.add(new mainFrame().border(t2, com, new Dimension(320, 320)), BorderLayout.NORTH);

        add.addActionListener(e -> {
            JFrame add1 = new JFrame("新建作业");
            add1.setSize(240, 180);
            //设置居中显示用3
            add1.setLocationRelativeTo(null);

            //流式布局管理器
            FlowLayout flow1 = new FlowLayout();
            add1.setLayout(flow1);  //给窗体设置为流式布局——从左到右然后从上到下排列自己写的组件顺序

            JLabel cTime = new JLabel("使用设备时间:", SwingConstants.RIGHT);
            JLabel eTime = new JLabel("使用设备时长:", SwingConstants.RIGHT);
            JLabel eID = new JLabel("使用设备ID:", SwingConstants.RIGHT);

            JTextField c = new JTextField(10);
            JTextField eT = new JTextField(10);
            JComboBox eI = new JComboBox();
            equipments.getEquipments().forEach(equipments -> {
                eI.addItem(equipments.getId());
            });

            eI.setPreferredSize(new Dimension(100, 20));
            add1.add(cTime);
            add1.add(c);
            add1.add(eTime);
            add1.add(eT);
            add1.add(eID);
            add1.add(eI);

            JButton cer1 = new JButton("确认");
            cer1.setPreferredSize(new Dimension(80, 40));
            add1.add(cer1);
            cer1.addActionListener(e1 -> {
                if (c.isValid() && eI.isValid()) {
                    work work = new work(Integer.parseInt(c.getText()), Integer.parseInt(eT.getText()),
                            Integer.parseInt(eI.getSelectedItem().toString()));
                    work.works.add(work);
                    model.addRow(new Vector<>(Arrays.asList(work.getId(), work.getcTime(),
                            work.geteTime(), work.geteID())));
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
         * 设备列表
         */
        /**
         * 模拟演示
         */
        simulation = new JPanel();
        buttons = new JButton[equipments.getEquipments().size()];
        final int[] i = {0};
        equipments.getEquipments().forEach(equipments -> {
            JLabel j = new JLabel("设备" + (i[0] + 1));
            buttons[i[0]] = new JButton(equipments.getStatus());
            buttons[i[0]].setPreferredSize(new Dimension(100, 200 / equipments.getEquipments().size()));
            buttons[i[0]].setMaximumSize(new Dimension(100, 600));
            buttons[i[0]].setFont(new Font("宋体", Font.BOLD, 20));
            simulation.add(j);
            simulation.add(buttons[i[0]]);
            i[0]++;
        });
        com.clear();
        simulation.setPreferredSize(new Dimension(150, 350));
        com.add(simulation);
        TitledBorder simulate = BorderFactory.createTitledBorder("分配结果模拟演示");
        jf.getContentPane().add(new mainFrame().border(simulate, com, new Dimension(150, 640)), BorderLayout.EAST);

        /**
         * 设备请求表
         */

        blocks = new DefaultTableModel();
        blocks.setColumnIdentifiers(new Object[]{"PID", "状态"});
        blockT = new JTable(blocks) {
            public boolean isCellEditable (int rowData,int column) {

                return false;}
        };
        blockT.setPreferredScrollableViewportSize(new Dimension(300, 100));
        JScrollPane sB = new JScrollPane(blockT);   //支持滚动
        TitledBorder distribution = BorderFactory.createTitledBorder("设备请求表");

        com.clear();
        com.add(sB);


        //单元格居中

        blockT.getColumn("PID").setMaxWidth(80);
        blockT.setDefaultRenderer(Object.class, cr);
        cen.add(new mainFrame().border(distribution, com, new Dimension(320, 320)), BorderLayout.SOUTH);


        jf.add(cen, BorderLayout.CENTER);

        //jf.pack();//根据预设大小调整窗口大小
        jf.setVisible(true);   //设置可见，放在代码最后一句
        //FCFS.fcfs();
    }
    private static void addEqu () {
        equipments.getEquipments().clear();
        for (int i = 0; i < 3; i++) {
            equipments e = new equipments();
        }
    }
}
