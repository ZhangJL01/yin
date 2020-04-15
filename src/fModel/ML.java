package fModel;


import frame.fileFrame;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ML extends MouseAdapter{
    private static TreePath movePath;
    private static File moveFile;
    //按下鼠标时候获得被拖动的节点
    public void mousePressed(MouseEvent e)
    {
        //如果需要唯一确定某个节点，必须通过TreePath来获取。
        JTree tree = (JTree) e.getSource();
        TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
        if (tp != null)
        {
            movePath = tp;
            moveFile = new File(toFilePath(movePath.toString()));
        }
    }

    //鼠标松开时获得需要拖到哪个父节点
    public void mouseReleased(MouseEvent e)
    {
        //根据鼠标松开时的TreePath来获取TreePath
        JTree tree = (JTree)e.getSource();
        TreePath tp = tree.getPathForLocation(e.getX(), e.getY());

        if (tp != null && movePath != null)
        {
            //阻止向子节点拖动
            if (movePath.isDescendant(tp) && movePath != tp){

            }
            //既不是向子节点移动，而且鼠标按下、松开的不是同一个节点
            else if (movePath != tp)
            {
                //System.out.println(tp.getLastPathComponent());
                //add方法可以先将原节点从原父节点删除，再添加到新父节点中
                ((DefaultMutableTreeNode)tp.getLastPathComponent()).add(
                        (DefaultMutableTreeNode)movePath.getLastPathComponent());
                movePath = null;
                tree.updateUI();
                File f = new File(toFilePath(tp.toString()) + File.separator + moveFile.getName());
                moveFile.renameTo(f);

            }
        }
    }

    public void mouseClicked(MouseEvent e) {

        JTree tree = (JTree) e.getSource();
        //int selRow = tree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
        //DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
        if (selPath != null) {
            File f = new File(toFilePath(selPath.toString()));
            fileFrame.files.setRowCount(0);
            if (e.getButton() == e.BUTTON1) {//左键
                //File f = new File(toFilePath(selPath.toString()));
                if (f.isDirectory()) {
                    List<File> fs = Arrays.asList(f.listFiles());
                    fs.forEach(file -> {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d = new Date(file.lastModified());
                        fileFrame.files.addRow(new Vector(Arrays.asList(file.getName(),
                                file.length(), sdf.format(d))));
                    });
                }

                //File f = new File(toFilePath(selPath.toString()));
                if (e.getClickCount() == 2 && f.isFile()) {
                    try {
                        Desktop.getDesktop().open(f);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } /*else if (e.getClickCount() == 2) {
                if(tree.isExpanded(selPath))//默认双击
                    tree.collapsePath(selPath);
                else
                    tree.expandPath(selPath);
            }*/
            } else if (e.getButton() == e.BUTTON3) {

                //todo 复制 剪切;
                JPopupMenu r = new JPopupMenu();
                JMenuItem add = new JMenuItem("新建");
                add.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //获取选中节点
                        DefaultMutableTreeNode selectedNode
                                = (DefaultMutableTreeNode) selPath.getLastPathComponent();
                        //如果节点为空，直接返回
                        if (selectedNode == null) return;
                        //创建一个新节点
                        JFrame f = new JFrame();
                        String fileName = JOptionPane.showInputDialog(f,
                                "请输入新建文件名：");
                        Boolean floder = fileName.contains(".");
                        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(fileName, !floder);
                        File newFile = new File(toFilePath(selPath.toString()) + File.separator + fileName);
                        try {
                            if (floder) {
                                newFile.createNewFile();
                            } else {
                                newFile.mkdir();
                            }

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        //直接通过model来添加新节点，则无需通过调用JTree的updateUI方法
                        //model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
                        //直接通过节点添加新节点，则需要调用tree的updateUI方法
                        selectedNode.add(newNode);
                        //--------下面代码实现显示新节点（自动展开父节点）-------
                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                        TreeNode[] nodes = model.getPathToRoot(newNode);
                        TreePath path = new TreePath(nodes);
                        tree.scrollPathToVisible(path);
                        tree.updateUI();

                    }
                });
                JMenuItem del = new JMenuItem("删除");
                del.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultMutableTreeNode selectedNode
                                = (DefaultMutableTreeNode) selPath.getLastPathComponent();
                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                        if (selectedNode != null && selectedNode.getParent() != null) {
                            //删除指定节点
                            model.removeNodeFromParent(selectedNode);
                        }
                        if (f.isDirectory()) {
                            File[] d = f.listFiles();
                            for (File f : d) {
                                f.delete();
                            }
                        }

                        f.delete();

                    }
                });
                JMenuItem rename = new JMenuItem("重命名");
                rename.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultMutableTreeNode selectedNode
                                = (DefaultMutableTreeNode) selPath.getLastPathComponent();
                        if (selPath != null) {
                            //编辑选中节点
                            tree.startEditingAtPath(selPath);
                        }
                        fileFrame.re = new File(toFilePath(selPath.toString()));
                    }
                });
                r.add(rename);
                r.add(add);
                r.add(del);
                r.show(e.getComponent(), e.getX(), e.getY());
            }
            fileFrame.fT.updateUI();
        }

        /*if(selRow != -1) {
            if(e.getClickCount() == 1) {
                System.out.println(toFilePath(selPath.toString()));
            }else if(e.getClickCount()==2){

            }
        }*/
    }


    /**
     * 树路径转化为系统文件路径
     */
    public static String toFilePath(String str) {
        // 先去掉头尾的[];
        String pa = str.substring(1, str.length() - 1);
        StringBuilder path = new StringBuilder();
        //System.out.println(pa);
        String[] temp = pa.split(", ");
        temp[0] = "D:\\test\\os\\OSFM\\";
        for (String s : temp) {
            if (!path.toString().endsWith("\\") && !path.toString().equals(""))//不为空是为去根节点;
                path.append("\\");
            path.append(s);
        }
        return path.toString();
    }


}

