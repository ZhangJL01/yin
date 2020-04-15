package fModel;

import frame.fileFrame;

import java.util.Enumeration;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class EL implements TreeWillExpandListener {


    /**
     * 展开监听
     */
    public void treeWillExpand(TreeExpansionEvent event) {
        // 对节点的路径进行转化,由[c:\, cattong, kdk]->c:\cattong\kdk;
        String path = toFilePath(event.getPath().toString());
        //System.out.println(event.getPath().toString());
        TreePath treePath = event.getPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
                .getLastPathComponent();
        //System.out.println("所展开节点的路径:" + path);
        //System.out.println("当前展开节点的深度:" + node.getDepth());
        if (node.getDepth() < 2) {
            Enumeration children = node.children();
            String filePath;
            while (children.hasMoreElements()) {
                DefaultMutableTreeNode temp = (DefaultMutableTreeNode) children
                        .nextElement();
                filePath = path;
                if (!filePath.endsWith("\\"))
                    filePath += "\\";
                filePath += temp.toString();
                // System.out.println("temp=" +filePath);
                fileFrame.addChildren(command.listDirectory(filePath), temp);
            }
        }


    }

    /**
     * 收缩监听
     */
    public void treeWillCollapse(TreeExpansionEvent event) {}

    /**
     * 树路径转化为系统文件路径
     */
    public String toFilePath(String str) {
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

