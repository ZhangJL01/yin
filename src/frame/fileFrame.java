package frame;

import fModel.*;
import sun.plugin2.main.client.PluginEmbeddedFrame;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;


public class fileFrame extends JFrame{
    private static final long serialVersionUID = 1L;//序列化？

    public static JFrame fF;
    public static DefaultTableModel files = new DefaultTableModel();
    public static JTable fT;
    public static JFrame CMD;
    public static String showPath = "OSFM";
    public static JTextArea echoArea = null;
    public static File re = null;


    /*public fileFrame() {
        super();
        // 设置外观感觉;
        String laf = UIManager.getSystemLookAndFeelClassName();
        //System.out.println(laf);
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println("Warning: UnsupportedLookAndFeel: " + laf);
        } catch (Exception exc) {
            System.err.println("Error loading " + laf + ": " + exc);
        }

        //窗口图标
        Toolkit tool = this.getToolkit();
        Image frameIcon = tool.getImage(getClass().getResource("netdisk.gif"));
        this.setIconImage(frameIcon);

        //居中
        tool = Toolkit.getDefaultToolkit();
        Dimension d = tool.getScreenSize();
        int x = (int)(d.getWidth() - this.getWidth()) / 2;
        int y = (int)(d.getHeight() - this.getHeight()) / 2;
        this.setLocation(x, y);

        JPanel jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.add(getSplitPane(), BorderLayout.CENTER);

        this.setSize(537, 382);
        this.setJMenuBar(getMyMenuBar());
        this.setForeground(new Color(110, 178, 198));
        this.setContentPane(jContentPane);
        this.setTitle("文件管理");

        // 对命令进行处理的类;
        cmd = new command(this);
        addChildren(cmd.ListDisks(), root);
        fileTree.expandRow(0);
        restart.doClick();
        // addChildren()
    }*/

    /**
     * 界面初始化
     */
    public static void showUI() {
        mainFrame.UI();

        /*SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                fileFrame thisClass = new fileFrame();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });*/
        // 设置外观感觉;
        String laf = UIManager.getSystemLookAndFeelClassName();
        //System.out.println(laf);
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println("Warning: UnsupportedLookAndFeel: " + laf);
        } catch (Exception exc) {
            System.err.println("Error loading " + laf + ": " + exc);
        }

        fF = new JFrame();

        //窗口图标
        Toolkit tool = fF.getToolkit();
        Image frameIcon = tool.getImage("netdisk.gif");
        fF.setIconImage(frameIcon);

        //居中
        /*tool = Toolkit.getDefaultToolkit();
        Dimension d = tool.getScreenSize();
        int x = (int)(d.getWidth() - fF.getWidth()) / 2;
        int y = (int)(d.getHeight() - fF.getHeight()) / 2;
        fF.setLocation(x, y);*/

        //分割界面
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(150);// 设置分格位置;
        splitPane.setDividerSize(5);// 设置分格条的宽度;
        splitPane.setFont(new Font("Dialog", Font.PLAIN, 14));//设置字体
        splitPane.setToolTipText("文件系统界面");//小提示

        //文件树
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("我的电脑", true);
        //model = new DefaultTreeModel(root, true);
        JTree fileTree = new JTree(root);
        DefaultTreeModel model = (DefaultTreeModel) fileTree.getModel();
        model.setAsksAllowsChildren(true);
        model.addTreeModelListener(new TreeModelListener() {
            @Override
            public void treeNodesChanged(TreeModelEvent e) {//修改节点的父节点
                if (re !=null) {
                    int i = e.getChildIndices()[0];//返回当前所修改的节点的索引
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getTreePath().getLastPathComponent();
                    DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt(i);
                    re.renameTo(new File(ML.toFilePath(e.getTreePath().toString()) + File.separator +
                            n.getUserObject().toString()));
                    re = null;
                }
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e) {

            }

            @Override
            public void treeNodesRemoved(TreeModelEvent e) {

            }

            @Override
            public void treeStructureChanged(TreeModelEvent e) {

            }
        });
        fileTree.setCellRenderer(new treeCR());// 添加渲染
        fileTree.addMouseListener(new ML());//添加鼠标监听
        fileTree.addTreeWillExpandListener(new EL());//添加树的展开和收缩监听
        fileTree.setDragEnabled(true);
        fileTree.setEditable(true);
        JScrollPane treeScroll = new JScrollPane();
        treeScroll.setViewportView(fileTree);

        //文件列表
        files.setColumnIdentifiers(new Object[] {"文件名", "文件大小", "最后修改时间"});
        fT = new JTable(files){
            public boolean isCellEditable (int rowData,int column) {

                return false;}
        };
        JScrollPane fS = new JScrollPane(fT);

        splitPane.setRightComponent(fS);
        splitPane.setLeftComponent(treeScroll);// 设置右边的组件;

        //菜单栏
        JMenuBar myMenuBar = new JMenuBar();
        myMenuBar.setPreferredSize(new Dimension(13, 25));

        JMenu fileMenu = new JMenu("命令行模式");
        fileMenu.setFont(new Font("DialogInput", Font.BOLD, 12));
        fileMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileFrame.CMD();
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        myMenuBar.add(fileMenu);

        JPanel jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.add(splitPane, BorderLayout.CENTER);

        fF.setSize(537, 382);
        fF.setJMenuBar(myMenuBar);
        fF.setForeground(new Color(110, 178, 198));
        fF.setContentPane(jContentPane);
        fF.setTitle("文件管理");

        // 对命令进行处理的类;
        File[] files = new File[2];
        files[0] = new File("D:" + File.separator + "test" + File.separator + "os" +
                File.separator + "OSFM" + File.separator + "C");
        files[1] = new File("D:" + File.separator + "test" + File.separator + "os" +
                File.separator + "OSFM" + File.separator + "D");

        addChildren(files, root);
        //fileTree.expandRow(0);
        //restart.doClick();
        // addChildren()
        fF.setLocationRelativeTo(null);
        fF.setVisible(true);
    }


    /**
     * cmd界面
     */
    public static void CMD() {

        CMD = new JFrame("命令行");
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BorderLayout());

        JLabel jLabel = new JLabel();
        jLabel.setText("输 入 命 令:");
        jLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        jLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);

        JTextField cmdText = new JTextField();
        cmdText.setColumns(20);
        // 添加键盘监听事件;
        cmdText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    // 对文本进行处理;
                    String[] op = cmdText.getText().split(" ");
                    command.currentPath = "D:" + File.separator + "test" + File.separator + "os"
                            + File.separator;
                    String newPath = "D:" + File.separator + "test" + File.separator + "os"
                            + File.separator;
                    if (op.length > 1) {
                        if (op[1].contains("OSFM")) {
                            command.currentPath += op[1];//绝对路径
                        } else {
                            command.currentPath += showPath + File.separator + op[1];//相对路径
                        }
                    } else {
                        command.currentPath += showPath + File.separator;
                    }

                    echoArea.append(cmdText.getText() + "\n");
                    switch (op[0]) {
                        //cd， mkdir， ls， mv（重命名，剪切）， cp， rm，
                        //touch， cat， remove
                        case "cd":
                            command.cd(op[1]);
                            break;
                        case "touch":
                            command.touch();
                            break;
                        case "mkdir":
                            command.mkdir();
                            break;
                        case "ls":
                            command.ls();
                            break;
                        case "mv":
                            if (op[2].contains("OSFM")) {
                                newPath += op[2];//绝对路径
                            } else {
                                newPath += showPath + File.separator + op[2];//相对路径
                            }
                            command.mv(newPath);
                            break;
                        case "cp":
                            if (op[2].contains("OSFM")) {
                                newPath += op[2];//绝对路径
                            } else {
                                newPath += showPath + File.separator + op[2];//相对路径
                            }
                            command.cp(command.currentPath, newPath);
                            break;
                        case "rm":
                            command.rm();
                            break;
                        case "cat":
                            command.cat();
                            break;
                        case "open":
                            command.open();
                            break;
                        case "clear":
                            command.clear();
                            break;
                        default:
                            echoArea.append("\n" + "命令错误！");
                            break;


                    }
                    echoArea.append(showPath + ":>");
                    cmdText.setText("");
                    //command.operate(cmdText.getText().trim());
                }

            }
        });

        JPanel cmdPane = new JPanel();
        cmdPane.setLayout(new FlowLayout());
        cmdPane.setPreferredSize(new Dimension(0, 30));
        cmdPane.add(jLabel, null);
        cmdPane.add(cmdText, null);

        echoArea = new JTextArea();
        echoArea.setEditable(false);
        echoArea.append(showPath + ":>");
        JScrollPane areaScroll = new JScrollPane();
        areaScroll.setViewportView(echoArea);

        JMenuItem help = new JMenuItem("帮助");
        help.setSize(70, 30);
        help.setFont(new Font("DialogInput", Font.BOLD, 12));
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new Object[] {"命令", "参数", "功能"});
                //cd， mkdir， ls， mv（重命名，剪切）， cp， rm，
                //touch， cat， remove
                model.addRow(new Vector(Arrays.asList("cd", "path", "打开path目录")));
                model.addRow(new Vector(Arrays.asList("mkdir", "dirName", "创建dirName目录")));
                model.addRow(new Vector(Arrays.asList("ls", "/", "查看当前目录所有文件")));
                model.addRow(new Vector(Arrays.asList("mv", "oldname/path， newname/path", "重命名/剪切")));
                model.addRow(new Vector(Arrays.asList("cp", "oldpath， newpath", "复制")));
                model.addRow(new Vector(Arrays.asList("rm", "fileName", "删除")));
                model.addRow(new Vector(Arrays.asList("touch", "fileName", "创建fileName文件")));
                model.addRow(new Vector(Arrays.asList("cat", "fileName", "查看fileName文件内容")));
                model.addRow(new Vector(Arrays.asList("open", "fileName", "打开fileName文件")));
                model.addRow(new Vector(Arrays.asList("clear", "/", "清屏")));
                JTable h = new JTable(model);
                h.setEnabled(false);
                h.getColumnModel().getColumn(0).setPreferredWidth(15);
                JScrollPane s = new JScrollPane(h);
                JFrame help = new JFrame("帮助");
                help.add(s);
                help.setSize(400, 250);
                help.setLocationRelativeTo(null);//放在设置大小的后面
                help.setVisible(true);
            }
        });

        rightPane.add(cmdPane, BorderLayout.NORTH);
        rightPane.add(areaScroll, BorderLayout.CENTER);

        CMD.add(help);
        CMD.add(rightPane);
        CMD.setSize(500, 300);
        CMD.setLocationRelativeTo(null);
        CMD.setVisible(true);
    }



    // 在回显框显示结果;
    public void display(String str) {
        echoArea.append(str + "\n");
        echoArea.setCaretPosition(echoArea.getText().length());
        //cmdText.setText("");
    }

    /**
     * 在node节点下添加树枝children;
     */
    public static void addChildren(File[] children, DefaultMutableTreeNode node) {
        if (children != null && node != null) {
            for (File child : children) {
                if (child != null) {
                    DefaultMutableTreeNode temp;
                    //System.out.println(children[i].);
                    if (child.getName().equals("C")) {
                        temp = new DefaultMutableTreeNode("C", true);
                    } else if (child.getName().equals("D")) {
                        temp = new DefaultMutableTreeNode("D", true);
                    } else {
                        temp = new DefaultMutableTreeNode(child.getName(), child.isDirectory());
                    }

                    if (node.getAllowsChildren()) {
                        node.add(temp);
                    }
                    //if (!children[i].getPath().equals("A:\\") && children[i].getPath().length() <= 3)// 防止读取A软驱,会出现异常;用于初始用的;
                    //addChildren(command.listAll(children[i].getPath()), temp);
                }
            }
        }
    }
}
