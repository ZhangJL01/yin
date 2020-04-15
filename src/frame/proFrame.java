package frame;

import pModel.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.Vector;

public class proFrame {
    public static DefaultTableModel temp = new DefaultTableModel();//就绪队列
    public static DefaultTableModel model = new DefaultTableModel();//所有进程信息
    public static DefaultTableModel block = new DefaultTableModel();//阻塞队列
    public static DefaultTableModel msg = new DefaultTableModel();//输出信息
    public static javax.swing.JFrame jf = new javax.swing.JFrame();
    public static JTable msgT;//信息表
    public static JTable tempT;//就绪表
    public static JTable blockT;//阻塞表
    public static void showUI () {
        mainFrame.UI();
        //窗体类
        //javax.swing.JFrame jf = new javax.swing.JFrame();
        //窗体名称
        jf.setTitle("进程调度模拟");
        //窗体大小（具体值跟电脑显示器的像素有关，可调整到合适大小）
        jf.setSize(780, 550);
        //设置居中显示用3
        jf.setLocationRelativeTo(null);

        //流式布局管理器
        java.awt.FlowLayout flow = new java.awt.FlowLayout();
        jf.setLayout(flow);  //给窗体设置为流式布局——从左到右然后从上到下排列自己写的组件顺序

        /**
         * 进程管理
         */
        //按钮
        javax.swing.JButton add = new javax.swing.JButton("添加");
        add.setSize(50, 30);
        jf.add(add);   //给窗体添加一个按钮对象*/
        javax.swing.JButton del = new javax.swing.JButton("撤销");
        del.setSize(50, 30);
        jf.add(del);   //给窗体添加一个按钮对象*/
        javax.swing.JButton wake = new javax.swing.JButton("唤醒");
        wake.setSize(50, 30);
        jf.add(wake);   //给窗体添加一个按钮对象*/
        javax.swing.JButton block = new javax.swing.JButton("阻塞");
        block.setSize(50, 30);
        jf.add(block);   //给窗体添加一个按钮对象*/

        //边界效果

        TitledBorder t1 = BorderFactory.createTitledBorder("进程管理");
        java.util.List<Component> com = new ArrayList<>();
        com.add(add);
        com.add(del);
        com.add(wake);
        com.add(block);

        //jf.getContentPane().add(new mainFrame().border(t1, com));

        /**
         * 进程调度
         */
        JCheckBox inte = new JCheckBox("是否抢占");
        JComboBox<String> alg = new JComboBox(new Object[]{"先来先服务", "短作业优先",
                "高响应比优先", "高优先级优先", "时间片轮转"});//时间片轮转询问时间片大小

        JLabel a = new JLabel("选择算法:");
        JButton cer = new JButton("开始");
        cer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cer.getText() == "开始") {
                    algorithm.z = true;
                    cer.setText("结束");

                    try
                    {
                        new Thread(() -> {
                            try{
                                if (alg.getSelectedItem().toString() == "时间片轮转") {//输入对话框效果
                                    JFrame f = new JFrame();
                                    algorithm.order = Integer.parseInt(JOptionPane.showInputDialog(f,
                                            "请输入时间片大小（大于零的整数）："));
                                    msg.setRowCount(0);
                                    algorithm.rr();
                                } else if (alg.getSelectedItem().toString() == "先来先服务") {
                                    msg.setRowCount(0);
                                    algorithm.fcfs();
                                } else if (alg.getSelectedItem().toString() == "短作业优先" && inte.isSelected()) {
                                    msg.setRowCount(0);
                                    algorithm.sjfInt();
                                } else if (alg.getSelectedItem().toString() == "短作业优先" && !inte.isSelected()) {
                                    msg.setRowCount(0);
                                    algorithm.sjfNoInt();
                                } else if (alg.getSelectedItem().toString() == "高响应比优先" && inte.isSelected()) {
                                    msg.setRowCount(0);
                                    algorithm.hrrnInt();
                                } else if (alg.getSelectedItem().toString() == "高响应比优先" && !inte.isSelected()) {
                                    msg.setRowCount(0);
                                    algorithm.hrrnNoInt();
                                } else if (alg.getSelectedItem().toString() == "高优先级优先" && inte.isSelected()) {
                                    msg.setRowCount(0);
                                    algorithm.psaInt();
                                } else if (alg.getSelectedItem().toString() == "高优先级优先" && !inte.isSelected()) {
                                    msg.setRowCount(0);
                                    algorithm.psaNoInt();
                                }
                            } catch(Exception ie){
                                System.out.println(ie.toString());
                                //ignored
                            }
                        }).start();
                    } catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                } else {
                    algorithm.z = false;
                    cer.setText("开始");
                    PCB.ready.clear();
                    PCB.block.clear();
                    proFrame.block.setRowCount(0);
                    proFrame.temp.setRowCount(0);
                    blockT.updateUI();
                    tempT.updateUI();
                }

                /*SwingUtilities.invokeLater(newRunnable(){
                    publicvoidrun(){
                        pane.setText(pane.getText()+msg);
                    }
                });*/

            }
        });
        TitledBorder t3 = BorderFactory.createTitledBorder("进程管理与调度");
        //com.clear();
        com.add(a);
        com.add(alg);
        com.add(inte);
        com.add(cer);
        jf.getContentPane().add(new mainFrame().border(t3, com, new Dimension(320, 320)));

        /**
         * 所有进程信息
         */
        //创建表头
        model.setColumnIdentifiers(new Object[]{"PID","到达时间","大小","优先级"});//,"响应比","状态"
        PCB.PCB.forEach(process -> {
            model.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getcTime(),
                    process.getpSize(), process.getpLevel())));
        });

        JTable jt= new JTable(model) {
            public boolean isCellEditable (int rowData,int column) {

                return false;}
        };
        jt.setPreferredScrollableViewportSize(new Dimension(300, 100));
        JScrollPane scrollPane = new JScrollPane(jt);   //支持滚动
        TitledBorder t2 = BorderFactory.createTitledBorder("所有进程信息");

        com.clear();
        com.add(scrollPane);

        //jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        /*jt.getColumnModel().getColumn(0).setPreferredWidth(40);
        jt.getColumn("到达时间").setPreferredWidth(40);
        jt.getColumn("大小").setPreferredWidth(40);*/
        jt.getColumn("PID").setMinWidth(50);
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();

        //单元格居中
        cr.setHorizontalAlignment(JLabel.CENTER);

        //hr.setHorizontalAlignment(SwingConstants.CENTER);

        jt.setDefaultRenderer(Object.class, cr);
        jf.getContentPane().add(new mainFrame().border(t2, com, new Dimension(320, 320)));





        /**
         * 模拟结果
         */

        msg.setColumnIdentifiers(new Object[]{"时间", "信息"});

        msgT = new JTable(msg) {
            public boolean isCellEditable (int rowData,int column) {

                return false;}
        };
        msgT.setPreferredScrollableViewportSize(new Dimension(300, 100));
        JScrollPane sM = new JScrollPane(msgT);   //支持滚动
        TitledBorder t4 = BorderFactory.createTitledBorder("模拟结果");

        com.clear();
        com.add(sM);

        //msgT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        msgT.getColumn("信息").setPreferredWidth(223);
        /*jt.getColumnModel().getColumn(0).setPreferredWidth(40);
        jt.getColumn("到达时间").setPreferredWidth(40);
        jt.getColumn("大小").setPreferredWidth(40);*/
        //msgT.getColumn("PID").setMinWidth(50);

        //单元格居中

        msgT.setDefaultRenderer(Object.class, cr);
        jf.getContentPane().add(new mainFrame().border(t4, com, new Dimension(320, 320)));

        /**
         * 就绪队列
         */

        temp.setColumnIdentifiers(new Object[]{"PID", "所需时间", "优先级", "响应比"});

        tempT = new JTable(temp) {
            public boolean isCellEditable (int rowData,int column) {

                return false;}
        };
        tempT.setPreferredScrollableViewportSize(new Dimension(300, 100));
        JScrollPane sT = new JScrollPane(tempT);   //支持滚动
        TitledBorder t5 = BorderFactory.createTitledBorder("就绪队列");

        com.clear();
        com.add(sT);

        //tempT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //tempT.getColumn("信息").setPreferredWidth(223);
        /*jt.getColumnModel().getColumn(0).setPreferredWidth(40);
        jt.getColumn("到达时间").setPreferredWidth(40);
        jt.getColumn("大小").setPreferredWidth(40);*/
        //tempT.getColumn("PID").setMinWidth(50);

        //单元格居中

        tempT.setDefaultRenderer(Object.class, cr);
        jf.getContentPane().add(new mainFrame().border(t5, com, new Dimension(320, 320)));

        /**
         * 阻塞队列
         */

        proFrame.block.setColumnIdentifiers(new Object[]{"PID", "所需时间", "优先级", "响应比"});

        blockT = new JTable(proFrame.block) {
            public boolean isCellEditable (int rowData,int column) {

                return false;}
        };
        blockT.setPreferredScrollableViewportSize(new Dimension(300, 100));
        JScrollPane sB = new JScrollPane(blockT);   //支持滚动
        TitledBorder t6 = BorderFactory.createTitledBorder("阻塞队列");

        com.clear();
        com.add(sB);

        //blockT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //blockT.getColumn("信息").setPreferredWidth(223);
        /*jt.getColumnModel().getColumn(0).setPreferredWidth(40);
        jt.getColumn("到达时间").setPreferredWidth(40);
        jt.getColumn("大小").setPreferredWidth(40);*/
        //blockT.getColumn("PID").setMinWidth(50);

        //单元格居中

        blockT.setDefaultRenderer(Object.class, cr);
        jf.getContentPane().add(new mainFrame().border(t6, com, new Dimension(320, 320)));


        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cer.getText().equals("结束")) {
                    JFrame add = new JFrame("新建进程");
                    add.setSize(240, 180);
                    //设置居中显示用3
                    add.setLocationRelativeTo(null);

                    //流式布局管理器
                    java.awt.FlowLayout flow = new java.awt.FlowLayout();
                    add.setLayout(flow);  //给窗体设置为流式布局——从左到右然后从上到下排列自己写的组件顺序

                    //JLabel id = new JLabel("           PID:", SwingConstants.RIGHT);
                    //JLabel time = new JLabel("到达时间:", SwingConstants.RIGHT);
                    JLabel size = new JLabel("处理时间:", SwingConstants.RIGHT);
                    JLabel level = new JLabel("    优先级:", SwingConstants.RIGHT);

                    //JTextField i = new JTextField(10);
                    //JTextField t = new JTextField(10);
                    JTextField s = new JTextField(10);
                    JTextField l = new JTextField(10);

                    //add.add(id);
                    //add.add(i);
                    //add.add(time);
                    //add.add(t);
                    add.add(size);
                    add.add(s);
                    add.add(level);
                    add.add(l);

                    JButton cer = new JButton("确认");
                    cer.setPreferredSize(new Dimension(80, 40));
                    add.add(cer);
                    cer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (s.isValid() && l.isValid()) {
                                process p = new process(PCB.PCB.size() + 1, Integer.parseInt(s.getText()),
                                        Integer.parseInt(l.getText()));
                                PCB.PCB.add(p);
                                PCB.ready.add(p);
                            /*if (alg.getSelectedItem().toString() != "先来先服务" &&
                                    alg.getSelectedItem().toString() != "时间片轮转") {
                                algorithm.s.add(p);
                            }*/
                                temp.addRow(new Vector<>(Arrays.asList(p.getpID(), p.getcTime(),
                                        p.getnTime(), p.getpLevel())));
                                model.addRow(new Vector<>(Arrays.asList(p.getpID(), p.getcTime(),
                                        p.getnTime(), p.getpLevel())));
                                tempT.validate();
                                tempT.updateUI();
                                jt.validate();
                                jt.updateUI();
                                add.dispose();
                            }
                        }
                    });
                    add.setVisible(true);
                }
            }

        });
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jt.getSelectedRow() != -1) {
                    process p = PCB.PCB.get(jt.getSelectedRow());
                    if (p.getpStatus() != 1) {
                        //System.out.println(jt.getSelectedRow());

                        PCB.PCB.remove(jt.getSelectedRow());
                        if (PCB.ready.equals(p)) {
                            PCB.ready.remove(PCB.ready.equals(p));
                            proFrame.temp.setRowCount(0);
                            PCB.ready.forEach(process -> {
                                proFrame.temp.addRow(new Vector<>(Arrays.asList(p.getpID(), p.getcTime(),
                                        p.getnTime(), p.getpLevel())));
                            });
                            tempT.validate();
                            tempT.updateUI();
                        } else {
                            PCB.block.remove(PCB.block.equals(p));
                            proFrame.block.setRowCount(0);
                            PCB.block.forEach(process -> {
                                proFrame.block.addRow(new Vector<>(Arrays.asList(p.getpID(), p.getcTime(),
                                        p.getnTime(), p.getpLevel())));
                            });
                            blockT.validate();
                            blockT.updateUI();
                        }


                        model.removeRow(jt.getSelectedRow());
                        jt.validate();
                        jt.updateUI();
                    } else {
                        JFrame f = new JFrame();
                        JOptionPane.showMessageDialog(f, "该进程正在执行！", "错误", JOptionPane.ERROR_MESSAGE);
                        // 创建一个新计时器
                        new java.util.Timer().schedule(new TimerTask() {
                            public void run() {
                                f.setVisible(false);
                                f.dispose();
                            }
                        }, 1000);
                    }

                }
            }
        });
        wake.addActionListener(new ActionListener(){//添加事件
            public void actionPerformed(ActionEvent e) {
                /*//这里是关键
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
                jt.setRowSorter(sorter);
                //实现过滤，search为正则表达式，该方法支持参数为null和空字符串，因此不用对输入进行校验
                String search = jTextField.getText();
                sorter.setRowFilter(RowFilter.regexFilter(search));*/
                if (proFrame.blockT.getSelectedRow() != -1) {
                    PCB.block.get(proFrame.blockT.getSelectedRow()).setpStatus(0);
                    PCB.ready.add(PCB.block.get(proFrame.blockT.getSelectedRow()));
                    proFrame.temp.addRow(new Vector<>(Arrays.asList(PCB.block.get(proFrame.blockT.getSelectedRow()).getpID(),
                            PCB.block.get(proFrame.blockT.getSelectedRow()).getnTime(),
                            PCB.block.get(proFrame.blockT.getSelectedRow()).getpLevel(),
                            PCB.block.get(proFrame.blockT.getSelectedRow()).getpRes())));
                    PCB.block.remove(proFrame.blockT.getSelectedRow());
                    proFrame.block.removeRow(proFrame.blockT.getSelectedRow());
                    proFrame.blockT.updateUI();
                    proFrame.tempT.updateUI();
                }
            }
        });
        block.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!PCB.ready.isEmpty()) {
                    PCB.ready.get(0).setpStatus(-1);
                    PCB.block.add(PCB.ready.get(0));
                    proFrame.block.addRow(new Vector<>(Arrays.asList(PCB.ready.get(0).getpID(), PCB.ready.get(0).getnTime(),
                            PCB.ready.get(0).getpLevel(), PCB.ready.get(0).getpRes())));
                    PCB.ready.remove(0);

                    //proFrame.temp.removeRow(proFrame.tempT.getSelectedRow());
                    proFrame.blockT.updateUI();
                }
            }
        });


        /*model.addTableModelListener(new TableModelListener() { //修改表格内容
            @Override
            public void tableChanged(TableModelEvent e) {
                patience pat = new patience();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                java.util.Date d = null;
                try {
                    d = df.parse(jt.getValueAt(jt.getSelectedRow(), 7).toString());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                pat.setNo(jt.getValueAt(jt.getSelectedRow(), 0).toString());
                pat.setName(jt.getValueAt(jt.getSelectedRow(), 1).toString());
                pat.setSex(jt.getValueAt(jt.getSelectedRow(), 2).toString());
                pat.setAge(Integer.parseInt(jt.getValueAt(jt.getSelectedRow(), 3).toString()));
                pat.setType(jt.getValueAt(jt.getSelectedRow(), 4).toString());
                pat.setdId(jt.getValueAt(jt.getSelectedRow(), 5).toString());
                pat.setwNo(jt.getValueAt(jt.getSelectedRow(), 6).toString());
                pat.setIn(new Date(d.getTime()));
                patServer.update(pat);
                jt.validate();
                jt.updateUI();
            }
        });*/
        //jf.pack();//根据预设大小调整窗口大小
        jf.setVisible(true);   //设置可见，放在代码最后一句
        //FCFS.fcfs();
    }
}
