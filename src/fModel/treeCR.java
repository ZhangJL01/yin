package fModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * 文件树渲染器
 */

public class treeCR extends DefaultTreeCellRenderer {


    private static final long serialVersionUID = 5980884274273876530L;

    public treeCR() {
        //super();
        setFileClosedIcon();
        setFileOpenIcon();
        setFileLeftIcon();
    }

    public void setFileOpenIcon() {
        setOpenIcon(new ImageIcon(getClass().getResource("images/icon_folderopen.gif")));
    }

    public void setFileClosedIcon() {
        setClosedIcon(new ImageIcon(getClass().getResource("images/icon_folder.gif")));
    }

    public void setFileLeftIcon() {
        setLeafIcon(new ImageIcon(getClass().getResource("images/htmlIcon.gif")));
    }

}
/*public class treeCR extends DefaultTreeCellRenderer {
    public treeCR() {
    }
    @Override
    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,
                                                  boolean expanded,boolean leaf,int row,boolean hasFocus) {
        Component c = super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if(node.getUserObject() instanceof  String){
            String file=(String)node.getUserObject();
            if(file.indexOf('.')<0){
                setIcon(createImageIcon("icon_folder.gif"));
            }else if(file.indexOf(".java")>0){
                setIcon(createImageIcon("icons/java.jpg"));
            }else if(file.indexOf(".png")>0 || file.indexOf(".PNG")>0 || file.indexOf(".jpg")>0){
                setIcon(createImageIcon("icons/png.jpg"));
            }else if(file.indexOf(".xml")>0){
                setIcon(createImageIcon("icons/xml.jpg"));
            }else if(file.indexOf(".mf")>0){
                setIcon(createImageIcon("icons/mf.jpg"));
            }else if(file.indexOf(".jar")>0){
                setIcon(createImageIcon("icons/jar.jpg"));
            }else if(file.indexOf(".class")>0){
                setIcon(createImageIcon("icons/class.jpg"));
            }else if(file.indexOf(".properties")>0){
                setIcon(createImageIcon("icons/properties.jpg"));
            }else if(file.indexOf(".txt")>0 || file.indexOf(".TXT")>0){
                setIcon(createImageIcon("icons/txt.jpg"));
            }
        }
        return c;
    }
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = treeCR.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}*/