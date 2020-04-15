package mModel;

import frame.memFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class algorithm {
    private static Object obj = new Object();
    public static memory start;
    /**
     * 通用操作
     */
    private static void com(memory mem, work work) {
        try {
            work.works.remove(work);
            for (int i = 0; i < memFrame.model.getRowCount(); i++) {
                if (memFrame.model.getValueAt(i, 0).equals(work.getId())) {
                    memFrame.model.removeRow(i);
                    memFrame.jt.updateUI();
                    break;
                }
            }
            memory m;
            if (mem.getSize().equals(work.getSize())) {
                m = mem;
            } else {
                m = new memory((work.getSize()));
                m.setwId(work.getId());
                m.setAdd(mem.getAdd());
                mem.setSize(mem.getSize() - work.getSize());
                mem.setAdd(mem.getAdd() + work.getSize());
                link(mem, m);
            }

            while (memFrame.first.before != null) {
                memFrame.first = memFrame.first.before;
            }
            //改变图形
            memFrame.blocks.setRowCount(0);
            memFrame.simulation.removeAll();
            memFrame.simulation.repaint();
            memFrame.blocks.addRow(new Vector(Arrays.asList(memFrame.first.getSize(), memFrame.first.getAdd(),
                    memFrame.first.getStatus())));
            memory temp = memFrame.first;
            memFrame.sim(memFrame.first);
            //System.out.println(memFrame.first.getSize());
            while (temp.next != null) {
                temp = temp.next;
                //System.out.println(temp.getSize());
                memFrame.blocks.addRow(new Vector(Arrays.asList(temp.getSize(), temp.getAdd(), temp.getStatus())));
                memFrame.sim(temp);
            }
            memFrame.simulation.revalidate();
            memFrame.blockT.updateUI();
            try {
                new Thread(() -> {
                    try {

                        try {
                            Thread.sleep(1000 * work.getTime());
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                        synchronized (obj) {//线程同步
                            release(m);
                        }

                    } catch (Exception el) {
                        System.out.println("3" + el.toString());
                    }
                }).start();
            } catch (Exception e1) {
                System.out.println("4" + e1.toString());
            }
        } catch (Exception e) {
            System.out.println("5" + e.toString());
        }



    }
    /**
     * 分配
     */
    public static Boolean FF(work work){
        if (memFrame.first.getStatus().equals("空闲") && memFrame.first.getSize() >= work.getSize()) {
            com(memFrame.first, work);
            return true;
        } else {
            memory temp = memFrame.first;
            while (temp.next !=null) {
                temp = temp.next;
                if (temp.getStatus().equals("空闲") && temp.getSize() >= work.getSize()) {
                    com(temp, work);
                    return true;
                }
            }
        }
        return false;

    }

    public static Boolean NF(work work){
        memory end = start;
        if (start.getStatus().equals("空闲") && start.getSize() >= work.getSize()) {
            com(start, work);
            return true;
        } else {
            do {
                if (start.next == null) {
                    start = memFrame.first;
                } else {
                    start = start.next;
                }

                if (start.getStatus().equals("空闲") && start.getSize() >= work.getSize()) {
                    com(start, work);
                    return true;
                }
            } while (!start.equals(end));
        }
        return false;
    }

    public static Boolean BF(work work){
        memory.free.clear();
        if (memFrame.first.getStatus().equals("空闲")) {
            memory.free.add(memFrame.first);
        }
        memory temp = memFrame.first;
        while (temp.next != null) {
            temp = temp.next;
            if (temp.getStatus().equals("空闲")) {
                memory.free.add(temp);
            }
        }
        memory.free.sort(new comBF());
        for (memory mem : memory.free) {
            if (mem.getSize() >= work.getSize()) {
                com(mem, work);
                return true;
            }
        }
        return false;
    }

    public static Boolean WF(work work){
        memory.free.clear();
        if (memFrame.first.getStatus().equals("空闲")) {
            memory.free.add(memFrame.first);
        }
        memory temp = memFrame.first;
        while (temp.next != null) {
            temp = temp.next;
            if (temp.getStatus().equals("空闲")) {
                memory.free.add(temp);
            }
        }
        memory.free.sort(new comBF());
        for (int i = memory.free.size() - 1; i > -1; i--) {
            if (memory.free.get(i).getSize() >= work.getSize()) {
                com(memory.free.get(i), work);
                return true;
            }
        }
        return false;
    }

    public static void link (memory old, memory fresh) {
        if (old.before != null) {
            old.before.next = fresh;
            fresh.before = old.before;
        }
        fresh.next = old;
        old.before = fresh;
    }

    /**
     * 回收
     */
    public static void release (memory m) {
        m.setStatus("空闲");
        if (m.next != null && m.next.getStatus().equals("空闲")) {
            m.setSize(m.getSize() + m.next.getSize());
            if (m.next.next != null) {
                m.next.next.before = m;
            }
            m.next = m.next.next;
        }
        if (m.before != null && m.before.getStatus().equals("空闲")) {
            m.before.setSize(m.getSize() + m.before.getSize());
            m.before.next = m.next;
            if (m.next != null) {
                m.next.before = m.before;
            }
        }
        try {
            memFrame.blocks.setRowCount(0);
            memFrame.simulation.removeAll();
            memFrame.simulation.repaint();
            memFrame.blocks.addRow(new Vector(Arrays.asList(memFrame.first.getSize(), memFrame.first.getAdd(),
                    memFrame.first.getStatus())));
            memory te = memFrame.first;
            memFrame.sim(memFrame.first);
            while (te.next != null) {
                te = te.next;
                //System.out.println(temp.getSize());
                memFrame.sim(te);
                memFrame.blocks.addRow(new Vector(Arrays.asList(te.getSize(), te.getAdd(), te.getStatus())));

            }
            memFrame.simulation.revalidate();
            memFrame.blockT.updateUI();
            //改变图形
        } catch (Exception e) {
            System.out.println("6" + e.toString());
        }

    }
}
