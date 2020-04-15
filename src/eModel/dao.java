package eModel;

import frame.equFrame;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class dao {
    private static List<work> e1 = new Vector<>();
    private static List<work> e2 = new Vector<>();
    private static List<work> e3 = new Vector<>();
    private static Object obj = new Object();
    public static void op () {
        work.works.sort(new Comparator<work>() {
            @Override
            public int compare(work o1, work o2) {
                return o1.getcTime() - o2.getcTime();
            }
        });
        equFrame.ready.clear();
        equFrame.all.clear();
        work.works.forEach(work -> {
            work w = new work(work.getId(), work.getcTime(), work.geteTime(), work.geteID());
            equFrame.all.add(w);
        });
        Integer t = 0;//记录时间
        while (!equFrame.all.isEmpty() || !equFrame.ready.isEmpty()) {
            while (!equFrame.all.isEmpty()) {
                work w = equFrame.all.get(0);
                if (w.getcTime().equals(t)) {
                    equFrame.ready.add(w);
                    if (w.geteID().equals(1)) {
                        e1.add(w);
                    } else if (w.geteID().equals(2)) {
                        e2.add(w);
                    } else {
                        e3.add(w);
                    }
                    equFrame.all.remove(0);
                } else {
                    break;
                }
            }//将到达时间的添加到执行行列

            //3线程
            new Thread(() -> {
                if (!e1.isEmpty()) {
                    work w = e1.get(0);
                    if (equipments.getEquipments().get(0).getStatus().equals("空闲")) {
                        equipments.getEquipments().get(0).setStatus(w.getId().toString());
                        equFrame.buttons[0].setText(w.getId().toString());
                        w.setStatus("正在使用设备");
                        /*equFrame.blocks.setRowCount(0);
                        if (!equFrame.ready.isEmpty()) {
                            equFrame.ready.forEach(work -> {
                                equFrame.blocks.addRow(new Vector(Arrays.asList(work.getId(), work.getStatus())));
                            });
                        }
                        equFrame.blockT.updateUI();*/
                        try {
                            Thread.sleep(1000 * w.geteTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        w.setStatus("使用完成");
                        equipments.getEquipments().get(0).setStatus("空闲");
                        equFrame.buttons[0].setText("空闲");
                        //synchronized (obj) {//线程同步
                        e1.remove(0);
                        equFrame.ready.remove(w);
                        //}

                        if (equFrame.ready.isEmpty()) {
                            equFrame.blocks.setRowCount(0);
                            equFrame.blockT.updateUI();
                        }


                    }
                }


            }).start();
            new Thread(() -> {
                if (!e2.isEmpty()) {
                    work w = e2.get(0);
                    if (equipments.getEquipments().get(1).getStatus().equals("空闲")) {
                        equipments.getEquipments().get(1).setStatus(w.getId().toString());
                        equFrame.buttons[1].setText(w.getId().toString());
                        w.setStatus("正在使用设备");
                        /*equFrame.blocks.setRowCount(0);
                        if (!equFrame.ready.isEmpty()) {
                            equFrame.ready.forEach(work -> {
                                equFrame.blocks.addRow(new Vector(Arrays.asList(work.getId(), work.getStatus())));
                            });
                        }
                        equFrame.blockT.updateUI();*/
                        try {
                            Thread.sleep(1000 * w.geteTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        w.setStatus("使用完成");
                        equipments.getEquipments().get(1).setStatus("空闲");
                        equFrame.buttons[1].setText("空闲");
                        //synchronized (obj) {//线程同步
                        e2.remove(0);
                        equFrame.ready.remove(w);
                        //}

                        if (equFrame.ready.isEmpty()) {
                            equFrame.blocks.setRowCount(0);
                            equFrame.blockT.updateUI();
                        }


                    }

                }

            }).start();
            new Thread(() -> {
                if (!e3.isEmpty()) {
                    work w = e3.get(0);
                    if (equipments.getEquipments().get(2).getStatus().equals("空闲")) {
                        equipments.getEquipments().get(2).setStatus(w.getId().toString());
                        equFrame.buttons[2].setText(w.getId().toString());
                        w.setStatus("正在使用设备");
                        /*equFrame.blocks.setRowCount(0);
                        if (!equFrame.ready.isEmpty()) {
                            equFrame.ready.forEach(work -> {
                                equFrame.blocks.addRow(new Vector(Arrays.asList(work.getId(), work.getStatus())));
                            });
                        }
                        equFrame.blockT.updateUI();*/
                        try {
                            Thread.sleep(1000 * w.geteTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        w.setStatus("使用完成");
                        equipments.getEquipments().get(2).setStatus("空闲");
                        equFrame.buttons[2].setText("空闲");
                        //synchronized (obj) {//线程同步
                        e3.remove(0);
                        equFrame.ready.remove(w);
                        //}

                        if (equFrame.ready.isEmpty()) {
                            equFrame.blocks.setRowCount(0);
                            equFrame.blockT.updateUI();
                        }


                    }

                }

            }).start();

            equFrame.blocks.setRowCount(0);
            if (!equFrame.ready.isEmpty()) {
                equFrame.ready.forEach(work -> {
                    equFrame.blocks.addRow(new Vector(Arrays.asList(work.getId(), work.getStatus())));
                });
            }
            equFrame.blockT.updateUI();

           /* equFrame.blocks.setRowCount(0);
            if (!equFrame.ready.isEmpty()) {
                equFrame.ready.forEach(work -> {
                    equFrame.blocks.addRow(new Vector(Arrays.asList(work.getId(), work.getStatus())));
                });
            }
            equFrame.blockT.updateUI();*/
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t += 1;
        }
    }
}
/*synchronized (obj) {//线程同步
        for (int i = 0; i < equFrame.ready.size(); i++) {
        work w = equFrame.ready.get(i);
        if (w.getStatus().equals("等待使用") &&
        equipments.getEquipments().get(w.geteID() - 1).getStatus().equals("空闲")) {
        new Thread(() -> {
        equipments.getEquipments().get(w.geteID() - 1).setStatus(w.getId().toString());
        equFrame.buttons[w.geteID() - 1].setText(w.getId().toString());
        w.setStatus("正在使用设备");
        equFrame.blocks.setRowCount(0);
        if (!equFrame.ready.isEmpty()) {
        equFrame.ready.forEach(work -> {
        equFrame.blocks.addRow(new Vector(Arrays.asList(work.getId(), work.getStatus())));
        });
        }
        equFrame.blockT.updateUI();
        try {
        Thread.sleep(1000 * w.geteTime());
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        w.setStatus("使用完成");
        equipments.getEquipments().get(w.geteID() - 1).setStatus("空闲");
        equFrame.buttons[w.geteID() - 1].setText("空闲");
        //synchronized (obj) {//线程同步
        equFrame.ready.remove(w);
        //}

        if (equFrame.ready.isEmpty()) {
        equFrame.blocks.setRowCount(0);
        equFrame.blockT.updateUI();
        }


        }).start();

        }

        }*/
