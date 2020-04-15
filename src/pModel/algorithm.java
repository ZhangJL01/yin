package pModel;

import frame.proFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class algorithm {
    public static List<process> s = new Vector<>();
    public static Boolean z = false;
    public static final Integer[] i = {1};

    /**
     * 通用函数
     */
    public static void init () {
        PCB.PCB.sort(new comFCFS());
        PCB.PCB.forEach(process -> {
            process.setnTime(process.getpSize());
        });
        PCB.ready.clear();
        PCB.block.clear();
        s.clear();
        proFrame.temp.setRowCount(0);
        proFrame.block.setRowCount(0);
        proFrame.tempT.updateUI();
        proFrame.blockT.updateUI();

        PCB.PCB.forEach(process -> {
            process temp = new process(process.getpID(), process.getcTime(), process.getnTime(), process.getpLevel());
            s.add(temp);
        });
        PCB.ready.add(s.get(0));
        proFrame.temp.addRow(new Vector<>(Arrays.asList(s.get(0).getpID(), s.get(0).getnTime(),
                s.get(0).getpLevel(), s.get(0).getpRes())));
        proFrame.tempT.validate();
        proFrame.tempT.updateUI();
        s.remove(0);
        i[0] = 1;
    }

    /**
     * 先来先服务
     */
    public static void fcfs () {

        PCB.PCB.sort(new comFCFS());
        //PCB.ready = PCB.PCB;
        PCB.PCB.forEach(process -> {
            process.setnTime(process.getpSize());
            process temp = new process(process.getpID(), process.getcTime(), process.getnTime(), process.getpLevel());
            PCB.ready.add(temp);
        });
        PCB.ready.forEach(process -> {
            if (0 == process.getcTime()) {
                proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                        process.getpLevel(), process.getpRes())));
            }
        });
        proFrame.tempT.validate();
        proFrame.tempT.updateUI();

        i[0] = 1;
        while (z) {
            if (!PCB.ready.isEmpty()) {

                process process = PCB.ready.get(0);
                process.setpStatus(1);
                proFrame.temp.removeRow(0);
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();


                while (process.getnTime() > 0) {

                    //System.out.println(i[0] +  "：执行进程" + process.getpID());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (process.getpStatus() == -1) {
                            /*PCB.block.add(process);
                            PCB.ready.remove(process);*/
                            /*proFrame.block.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                                    process.getpLevel(), process.getpRes())));
                            proFrame.temp.removeRow(0);
                            proFrame.blockT.validate();
                            proFrame.tempT.validate();
                            proFrame.blockT.updateUI();
                            proFrame.tempT.updateUI();*/
                        break;
                    }
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "执行进程" + process.getpID())));

                    process.setnTime(process.getnTime() - 1);
                    i[0]++;
                    PCB.ready.forEach(pro -> {
                        if (i[0] == pro.getcTime()) {
                            proFrame.temp.addRow(new Vector<>(Arrays.asList(pro.getpID(), pro.getnTime(),
                                    pro.getpLevel(), pro.getpRes())));
                        }
                    });
                    proFrame.tempT.validate();
                    proFrame.tempT.updateUI();
                }
                if (process.getnTime() == 0) {
                    process.setpStatus(0);
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0] - 1, "进程" + process.getpID() + "执行完成")));
                    //System.out.println(i[0] - 1 +  "：进程" + process.getpID() + "执行完成");
                    //Iterator<process> ite = PCB.ready.iterator();
                    //ite.remove();
                    PCB.ready.get(0).setpStatus(0);
                    PCB.ready.remove(0);
                } else {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + process.getpID() + "发生阻塞")));
                    //System.out.println(i[0] - 1 +  "：进程" + process.getpID() + "发生阻塞");

                }
                proFrame.temp.setRowCount(0);
                PCB.ready.forEach(p -> {
                    proFrame.temp.addRow(new Vector<>(Arrays.asList(p.getpID(), p.getnTime(),
                            p.getpLevel(), p.getpRes())));
                });
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "等待")));
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
                i[0]++;

            }

        }

    }

    /**
     * 短作业优先
     */
    public static void sjfInt () {//抢占
        init();
        
        while (z) {
            if (!PCB.ready.isEmpty()) {
                PCB.ready.get(0).setpStatus(1);
                //int pId = PCB.ready.get(0).getpID();
                process p = PCB.ready.get(0);
                proFrame.temp.removeRow(0);
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();

                //System.out.println(i[0] +  "：执行进程" + PCB.ready.get(0).getpID());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int x = 0; x < s.size(); x++) {
                    if (s.get(0).getcTime() <= i[0]) {
                        PCB.ready.add(s.get(0));
                        s.remove(0);
                    } else {
                        break;
                    }
                }
                if (p.getpStatus() == -1) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + p.getpID() + "发生阻塞")));
                    //System.out.println(i[0] +  "：进程" + PCB.ready.get(0).getpID() + "发生阻塞");
                    i[0]--;
                } else {
                    PCB.ready.get(0).setpSize(PCB.ready.get(0).getnTime() - 1);
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "执行进程" + p.getpID())));
                }


                if (PCB.ready.get(0).getnTime() == 0) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + PCB.ready.get(0).getpID() + "执行完毕")));
                    //System.out.println(i[0] +  "：进程" + PCB.ready.get(0).getpID() + "执行完毕");
                    PCB.ready.get(0).setpStatus(0);
                    PCB.ready.remove(0);
                }
                PCB.ready.sort(new comSJF());
                proFrame.temp.setRowCount(0);
                PCB.ready.forEach(process -> {
                    proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                            process.getpLevel(), process.getpRes())));
                });
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "等待")));
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
                //i[0]++;

            }

            i[0]++;
        }

    }

    public static void sjfNoInt () {//非抢占
        init();
        
        while (z) {
            if (!PCB.ready.isEmpty()) {
                PCB.ready.get(0).setpStatus(1);
                process p = PCB.ready.get(0);
                for (; p.getnTime() > 0; i[0]++) {
                    proFrame.temp.removeRow(0);
                    proFrame.tempT.validate();
                    proFrame.tempT.updateUI();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int x = 0; x < s.size(); x++) {
                        if (s.get(0).getcTime() <= i[0]) {
                            PCB.ready.add(s.get(0));
                            s.remove(0);
                        } else {
                            break;
                        }
                    }
                    proFrame.temp.setRowCount(0);
                    PCB.ready.forEach(process -> {
                        proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                                process.getpLevel(), process.getpRes())));
                    });
                    proFrame.tempT.validate();
                    proFrame.tempT.updateUI();
                    if (p.getpStatus() == -1) {
                        break;
                    } else {
                        proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "执行进程" + PCB.ready.get(0).getpID())));
                        //System.out.println(i[0] +  "：执行进程" + PCB.ready.get(0).getpID());
                        PCB.ready.get(0).setpSize(PCB.ready.get(0).getnTime() - 1);
                    }

                }

                if (PCB.ready.get(0).getnTime() == 0) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0] - 1, "进程" + PCB.ready.get(0).getpID() + "执行完毕")));
                    //System.out.println(i[0] - 1 +  "：进程" + PCB.ready.get(0).getpID() + "执行完毕");
                    PCB.ready.get(0).setpStatus(0);
                    PCB.ready.remove(0);
                } else {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + p.getpID() + "发生阻塞")));
                    //System.out.println(i[0] - 1 +  "：进程" + PCB.ready.get(0).getpID() + "发生阻塞");
                    //i[0]--;
                }
                PCB.ready.sort(new comSJF());
                proFrame.temp.setRowCount(0);
                PCB.ready.forEach(process -> {
                    proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                            process.getpLevel(), process.getpRes())));
                });
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "等待")));
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
                i[0]++;

            }

        }
    }

    /**
     * 高优先级优先
     */
    public static void psaInt () {//抢占

        init();
        
        while (z) {
            if (!PCB.ready.isEmpty()) {
                PCB.ready.get(0).setpStatus(1);
                //int pId = PCB.ready.get(0).getpID();
                process p = PCB.ready.get(0);
                proFrame.temp.removeRow(0);
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();

                //System.out.println(i[0] +  "：执行进程" + PCB.ready.get(0).getpID());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int x = 0; x < s.size(); x++) {
                    if (s.get(0).getcTime() <= i[0]) {
                        PCB.ready.add(s.get(0));
                        s.remove(0);
                    } else {
                        break;
                    }
                }
                if (p.getpStatus() == -1) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + p.getpID() + "发生阻塞")));
                    //System.out.println(i[0] +  "：进程" + PCB.ready.get(0).getpID() + "发生阻塞");
                    i[0]--;
                } else {
                    PCB.ready.get(0).setpSize(PCB.ready.get(0).getnTime() - 1);
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "执行进程" + p.getpID())));
                }


                if (PCB.ready.get(0).getnTime() <= 0) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + PCB.ready.get(0).getpID() + "执行完毕")));
                    //System.out.println(i[0] +  "：进程" + PCB.ready.get(0).getpID() + "执行完毕");
                    PCB.ready.get(0).setpStatus(0);
                    PCB.ready.remove(0);
                }
                PCB.ready.sort(new comPSA());
                proFrame.temp.setRowCount(0);
                PCB.ready.forEach(process -> {
                    proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                            process.getpLevel(), process.getpRes())));
                });
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "等待")));
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
                //i[0]++;


            }

            i[0]++;
        }


    }
    public static void psaNoInt () {//非抢占

        init();
        
        while (z) {
            if (!PCB.ready.isEmpty()) {
                PCB.ready.get(0).setpStatus(1);
                process p = PCB.ready.get(0);
                for (; p.getnTime() > 0; i[0]++) {
                    proFrame.temp.removeRow(0);
                    proFrame.tempT.validate();
                    proFrame.tempT.updateUI();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int x = 0; x < s.size(); x++) {
                        if (s.get(0).getcTime() <= i[0]) {
                            PCB.ready.add(s.get(0));
                            s.remove(0);
                        } else {
                            break;
                        }
                    }
                    proFrame.temp.setRowCount(0);
                    PCB.ready.forEach(process -> {
                        proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                                process.getpLevel(), process.getpRes())));
                    });
                    proFrame.tempT.validate();
                    proFrame.tempT.updateUI();
                    if (p.getpStatus() == -1) {
                        break;
                    } else {
                        proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "执行进程" + PCB.ready.get(0).getpID())));
                        //System.out.println(i[0] +  "：执行进程" + PCB.ready.get(0).getpID());
                        PCB.ready.get(0).setpSize(PCB.ready.get(0).getnTime() - 1);
                    }

                }

                if (PCB.ready.get(0).getnTime() == 0) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0] - 1, "进程" + PCB.ready.get(0).getpID() + "执行完毕")));
                    //System.out.println(i[0] - 1 +  "：进程" + PCB.ready.get(0).getpID() + "执行完毕");
                    PCB.ready.get(0).setpStatus(0);
                    PCB.ready.remove(0);
                } else {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + p.getpID() + "发生阻塞")));
                    //System.out.println(i[0] - 1 +  "：进程" + PCB.ready.get(0).getpID() + "发生阻塞");
                    //i[0]--;
                }
                PCB.ready.sort(new comPSA());
                proFrame.temp.setRowCount(0);
                PCB.ready.forEach(process -> {
                    proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                            process.getpLevel(), process.getpRes())));
                });
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "等待")));
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
                i[0]++;

            }

        }
    }

    /**
     * 高响应比优先
     */
    public static void hrrnInt () {//抢占

        init();
        
        while (z) {
            if (!PCB.ready.isEmpty()) {
                PCB.ready.get(0).setpStatus(1);
                //int pId = PCB.ready.get(0).getpID();
                process p = PCB.ready.get(0);
                proFrame.temp.removeRow(0);
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                //proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "执行进程" + PCB.ready.get(0).getpID())));
                //System.out.println(i[0] +  "：执行进程" + PCB.ready.get(0).getpID());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int x = 0; x < s.size(); x++) {
                    if (s.get(0).getcTime() <= i[0]) {
                        PCB.ready.add(s.get(0));
                        s.remove(0);
                    } else {
                        break;
                    }
                }

                if (p.getpStatus() == -1) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + p.getpID() + "发生阻塞")));
                    //System.out.println(i[0] +  "：进程" + PCB.ready.get(0).getpID() + "发生阻塞");
                    i[0]--;
                } else {
                    PCB.ready.get(0).setpSize(PCB.ready.get(0).getnTime() - 1);
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "执行进程" + p.getpID())));
                }


                if (PCB.ready.get(0).getnTime() <= 0) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + PCB.ready.get(0).getpID() + "执行完毕")));
                    //System.out.println(i[0] +  "：进程" + PCB.ready.get(0).getpID() + "执行完毕");
                    PCB.ready.get(0).setpStatus(0);
                    PCB.ready.remove(0);
                }
                PCB.ready.forEach(process -> {
                    Double res = Double.valueOf((i[0] - process.getcTime() + process.getnTime()) / process.getnTime());
                    process.setpRes(res);
                });
                PCB.ready.sort(new comHRRN());
                proFrame.temp.setRowCount(0);
                PCB.ready.forEach(process -> {
                    proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                            process.getpLevel(), process.getpRes())));
                });
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "等待")));
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();

            }

            i[0]++;
        }


    }
    public static void hrrnNoInt () {//非抢占

        init();
        
        while (z) {
            if (!PCB.ready.isEmpty()) {
                PCB.ready.get(0).setpStatus(1);
                process p = PCB.ready.get(0);
                for (; p.getnTime() > 0; i[0]++) {
                    proFrame.temp.removeRow(0);
                    proFrame.tempT.validate();
                    proFrame.tempT.updateUI();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int x = 0; x < s.size(); x++) {
                        if (s.get(0).getcTime() <= i[0]) {
                            PCB.ready.add(s.get(0));
                            s.remove(0);
                        } else {
                            break;
                        }
                    }
                    proFrame.temp.setRowCount(0);
                    PCB.ready.forEach(process -> {
                        proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                                process.getpLevel(), process.getpRes())));
                    });
                    proFrame.tempT.validate();
                    proFrame.tempT.updateUI();
                    if (PCB.ready.get(0).getpStatus() == -1) {
                        break;
                    } else {
                        proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "执行进程" + PCB.ready.get(0).getpID())));
                        //System.out.println(i[0] +  "：执行进程" + PCB.ready.get(0).getpID());
                        PCB.ready.get(0).setpSize(PCB.ready.get(0).getnTime() - 1);
                    }
                }

                if (PCB.ready.get(0).getnTime() == 0) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0] - 1, "进程" + PCB.ready.get(0).getpID() + "执行完毕")));
                    //System.out.println(i[0] - 1 +  "：进程" + PCB.ready.get(0).getpID() + "执行完毕");
                    PCB.ready.get(0).setpStatus(0);
                    PCB.ready.remove(0);
                } else {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + PCB.ready.get(0).getpID() + "发生阻塞")));
                    //System.out.println(i[0] - 1 +  "：进程" + PCB.ready.get(0).getpID() + "发生阻塞");
                }
                PCB.ready.forEach(process -> {
                    Double res = Double.valueOf((i[0] - process.getcTime() + process.getnTime()) / process.getnTime());
                    process.setpRes(res);
                });
                PCB.ready.sort(new comHRRN());

                proFrame.temp.setRowCount(0);
                PCB.ready.forEach(process -> {
                    proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                            process.getpLevel(), process.getpRes())));
                });
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "等待")));
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
                i[0]++;

            }
        }

    }

    /**
     * 时间片轮转
     */
    public static Integer order = 1;//阶
    public static void rr () {

        PCB.PCB.sort(new comFCFS());
        

        i[0] = 1;
        PCB.PCB.forEach(process -> {
            process.setnTime(process.getpSize());
            process temp = new process(process.getpID(), process.getcTime(), process.getnTime(), process.getpLevel());
            PCB.ready.add(temp);
        });

        PCB.ready.forEach(process -> {
            if (0 == process.getcTime()) {
                proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                        process.getpLevel(), process.getpRes())));
            }
        });
        proFrame.tempT.validate();
        proFrame.tempT.updateUI();
        while (z) {
            if (!PCB.ready.isEmpty()) {
                int x = 0, y = 0;
                proFrame.temp.removeRow(0);
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                process p = new process();
                for (; x < order; x++) {
                    if ((i[0] - 1) % order == 0 || y == -1) {
                        p = PCB.ready.get(0);
                        p.setpStatus(1);
                        //System.out.println(p);
                        y = 0;
                    }

                    //int pId = p.getpID();
                    PCB.ready.forEach(pro -> {
                        if (i[0] == pro.getcTime()) {
                            proFrame.temp.addRow(new Vector<>(Arrays.asList(pro.getpID(), pro.getnTime(),
                                    pro.getpLevel(), pro.getpRes())));
                        }
                    });
                    proFrame.tempT.validate();
                    proFrame.tempT.updateUI();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (p.getpStatus() == -1) {
                        /*PCB.block.add(process);
                        PCB.ready.remove(process);*/
                        /*proFrame.block.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                                process.getpLevel(), process.getpRes())));
                        proFrame.temp.removeRow(0);
                        proFrame.blockT.validate();
                        proFrame.tempT.validate();
                        proFrame.blockT.updateUI();
                        proFrame.tempT.updateUI();*/
                        y = -1;
                        break;
                    }
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "执行进程" + p.getpID())));
                    //System.out.println(i[0] +  "：执行进程" + PCB.ready.get(i[0] % PCB.ready.size()).getpID());
                    //PCB.ready.get(i[0] % PCB.ready.size()).setpStatus(1);
                    p.setpSize(p.getnTime() - 1);

                    if (p.getnTime() == 0) {
                        p.setpStatus(0);
                        proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + p.getpID() + "执行完毕")));
                        //System.out.println(i[0] +  "：进程" + PCB.ready.get(i[0] % PCB.ready.size()).getpID() + "执行完毕");
                        PCB.ready.remove(0);
                        proFrame.msgT.validate();
                        proFrame.msgT.updateUI();
                        i[0]++;
                        break;
                    }
                    i[0]++;
                    proFrame.msgT.validate();
                    proFrame.msgT.updateUI();

                }

                if (p.getpStatus() == -1) {
                    proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "进程" + p.getpID() + "发生阻塞")));
                    //System.out.println(i[0] +  "：进程" + PCB.ready.get(i[0] % PCB.ready.size()).getpID() + "发生阻塞");
                    proFrame.msgT.validate();
                    proFrame.msgT.updateUI();
                } else if (p.getnTime() !=0) {
                    PCB.ready.remove(0);
                    p.setpStatus(0);
                    PCB.ready.add(p);
                }

                proFrame.temp.setRowCount(0);
                PCB.ready.forEach(process -> {
                    proFrame.temp.addRow(new Vector<>(Arrays.asList(process.getpID(), process.getnTime(),
                            process.getpLevel(), process.getpRes())));
                });
                proFrame.tempT.validate();
                proFrame.tempT.updateUI();
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();

            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proFrame.msg.addRow(new Vector<>(Arrays.asList(i[0], "等待")));
                proFrame.msgT.validate();
                proFrame.msgT.updateUI();
                i[0]++;

            }

        }

    }

}
