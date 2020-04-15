package fModel;

import frame.fileFrame;

import java.awt.*;
import java.io.*;
import java.nio.channels.FileChannel;


public class command {

    public static String currentPath = "D:" + File.separator + "test" + File.separator + "os"
            + File.separator;
    //cd， mkdir， ls， mv（重命名，剪切）， cp， rm，
    //touch， cat， remove

    /**
     * 清空
     * clear
     */
    public static void clear () {
        fileFrame.echoArea.setText("");
    }
    /**
     * 新建文件
     * touch
     */

    public static void touch() {
        if (!currentPath.equals("")) {
            File newFile = new File(currentPath);
            if (newFile.exists()) {
                fileFrame.echoArea.append("文件已经存在!" + "\n");
            } else {
                try {//使用file类的方法新建文件
                    newFile.createNewFile();
                } catch (Exception e) {
                     fileFrame.echoArea.append("文件创建失败:" + e.getMessage() + "\n");
                }
            }
        }
    }

    /**
     * 删除文件或目录
     * rm
     */

    public static void rm() {
        if (!currentPath.equals("")) {
            File newFile = new File(currentPath);
            if (!newFile.exists()) {
                fileFrame.echoArea.append("文件或目录不存在!" + "\n");
            } else {
                try {
                    if (newFile.isDirectory()) {
                        File[] d = newFile.listFiles();
                        for (File f : d) {
                            f.delete();
                        }
                    }
                    newFile.delete();
                } catch (Exception e) {
                    fileFrame.echoArea.append("删除失败:" + e.getMessage() + "\n");
                }
            }
        }
    }

    /**
     * 打开文件
     * open
     */
    public static void open() {
        if (!currentPath.equals("")) {
            File newFile = new File(currentPath);
            if (!newFile.exists()) {
                fileFrame.echoArea.append("文件不存在!" + "\n");
            } else if (newFile.isDirectory()){
                fileFrame.echoArea.append("目录不可打开!" + "\n");
            } else {
                {
                    try {
                        Desktop.getDesktop().open(newFile);
                    } catch (Exception e) {
                        fileFrame.echoArea.append("文件打开失败:" + e.getMessage() + "\n");
                    }
                }
            }
        }
    }

    /**
     * 查看文件
     * cat
     */
    public static void cat() {
        if (!currentPath.equals("")) {
            File newFile = new File(currentPath);
            if (!newFile.exists()) {
                fileFrame.echoArea.append("文件不存在!" + "\n");
            } else if (newFile.isDirectory()) {
                fileFrame.echoArea.append("目录不可查看!" + "\n");
            } else {
                try {
                    StringBuffer sbf = new StringBuffer();
                    BufferedReader reader = new BufferedReader(new FileReader(newFile));
                    String tempStr;
                    while ((tempStr = reader.readLine()) != null) {
                        sbf.append(tempStr + "\n");
                    }
                    fileFrame.echoArea.append(sbf.toString());
                    reader.close();
                } catch (Exception e) {
                    fileFrame.echoArea.append("文件打开失败:" + e.getMessage() + "\n");
                }
            }
        }
    }

    /**
     * 重命名
     * 剪切
     * mv
     */
    public static void mv (String newName) {
        if (!currentPath.equals("")) {
            File newFile = new File(currentPath);
            if (!newFile.exists()) {
                fileFrame.echoArea.append("文件或目录不存在!" + "\n");
            } else {
                try {
                    newFile.renameTo(new File(newName));
                } catch (Exception e) {
                    fileFrame.echoArea.append("操作失败:" + e.getMessage() + "\n");
                }
            }
        }
    }

    /**
     * 新建目录
     * mkdir
     */
    public static void mkdir() {
        if (!currentPath.equals("")) {
            File newFile = new File(currentPath);
            if (newFile.exists()) {
                fileFrame.echoArea.append("目录已存在!" + "\n");
            } else {
                try {
                    newFile.mkdir();
                } catch (Exception e) {
                    fileFrame.echoArea.append("目录创建失败:" + e.getMessage() + "\n");
                }
            }
        }
    }

    /**
     * 打开目录
     * cd
     */
    public static void cd(String fName) {
        if (!currentPath.equals("")) {
            if (fName.equals("..")) {
                //上一级目录
                fileFrame.showPath = fileFrame.showPath.substring(0, fileFrame.showPath.lastIndexOf(File.separator));
            } else if (fName.equals("/")) {
                fileFrame.showPath = "OSFM";
            } else {
                File newFile = new File(currentPath);
                if (!newFile.exists() || fName.contains(".")) {
                    fileFrame.echoArea.append("\n" + "目录输入错误!");
                } else {
                    if (fName.contains("OSFM")) {
                        fileFrame.showPath = fName;
                    } else {
                        fileFrame.showPath += File.separator + fName;
                    }

                }
            }
        }
    }

    /**
     * 复制
     * cp
     */
    public static void cp (String currentPath, String newPath) {
        if (!currentPath.equals("")) {
            System.out.println(currentPath);
            System.out.println(newPath);
            File oldFile = new File(currentPath);
            File newFile = new File(newPath);
            if (!oldFile.exists()) {
                fileFrame.echoArea.append("文件或目录不存在!" + "\n");
            } else {
                try {
                    if (oldFile.isDirectory()) {
                        if (!newFile.exists()) {
                            newFile.mkdir();
                        }
                        for (File f:oldFile.listFiles()) {
                            cp(currentPath  + File.separator  + f.getName(),
                                    newPath  + File.separator + f.getName());
                        }
                    }
                    if (oldFile.isFile()) {
                        if (!newFile.exists()) {
                            newFile.createNewFile();
                        }
                        FileChannel in = new FileInputStream(oldFile).getChannel();
                        FileChannel out = new FileOutputStream(newFile).getChannel();
                        out.transferFrom(in, 0, in.size());
                        in.close();
                        out.close();
                    }

                } catch (Exception e) {
                    fileFrame.echoArea.append("复制失败:" + e.getMessage() + "\n");
                }
            }
        }
    }

    /**
     * 查看目录
     * ls
     */
    public static void ls () {
        File newFile = new File(currentPath);
        File[] fs =  newFile.listFiles();
        for (File f:fs) {
            fileFrame.echoArea.append(f.getName() + "\n");
        }
    }

    public static File[] listDirectory(String path){
        File f = new File(path);
        File[] files;
        if (f.isDirectory()) {
            files = f.listFiles();
            //for (int i = 0; i < fileName.length; i++)
            //System.out.println(fileName[0]);
            //System.out.println(files[0].getPath());
            return files;
        } else {
            //System.out.println(path+"是文件");
            return null;
        }
    }
}
/*


    */
/*private static fileFrame mainFrame = null;

    private static final String cmd[] = { "cd", "dir", "md", "rd", "edit", "del",
            "exit" };

    private static final int cmdInt[] = { 1, 2, 3, 4, 5, 6, 7 };

    public command(fileFrame mainFrame) {
        currentPath = "C:";
        this.mainFrame = mainFrame;
    }
    public command () {}


    /**
     * 显示系统中的所有盘符;
     *//*

    public static String[] ListDisks() {
        File root = new File("D:" + File.separator + "test" + File.separator + "os" +
                File.separator + "OSFM" + File.separator + "C");
        File root2 = new File("D:" + File.separator + "test" + File.separator + "os" +
                File.separator + "OSFM" + File.separator + "D");
        List<File> roots = new Vector();// 根盘符;
        roots.add(root);
        roots.add(root2);
        String disks[] = new String[roots.size()];
        for (int i = 0; i < roots.size(); i++) {
            disks[i] = roots.get(i).toString();
        }

        return disks;
    }

    public static String[] separate(String operation) {
        String[] str = operation.split(" ");// 按分号分割;
        //主要解决文件夹或文件中含有空格的情况;
        if(str.length>2){
            String[] tempStr=new String[2];
            tempStr[0]=str[0];
            tempStr[1]=str[1];
            for(int i=2;i<str.length;i++)
                tempStr[1]+=" "+str[i];
            return tempStr;
        }
        return str;
    }

    */
/**
     * 根据参数operation执行相应的操作;
     *//*

    public static void operate(String operation) {
        String[] str = separate(operation);
        // System.out.println(str.length);
        String mycmd = "";
        // mycmd对应的整数代号;
        int mycmdInt = 0;
        String path = "";
        if (str.length == 1) {
            mycmd = str[0];
            if (mycmd.indexOf(":") != -1) {// 如果是直接盘符转换;执行些操作;
                File newFile = new File(mycmd);
                if (newFile.exists()) {
                    currentPath = mycmd;
                    // System.out.print(getPath());
                    mainFrame.display(getPath());
                    return;
                }
            }
        }
        if (str.length >= 2) {
            mycmd = str[0];
            path = str[1];
        }
        // 选择执行命令;
        // ///System.out.println(mycmd+"\\"+path);
        for (int i = 0; i < cmd.length; i++) {
            if (mycmd.equalsIgnoreCase(cmd[i])) {
                mycmdInt = cmdInt[i];
            }
        }
        switch (mycmdInt) {
            case 1:
                cd(currentPath, path);
                break;
            case 2:
                dir(currentPath);
                break;
            case 3:
                md(path);
                break;
            case 4:
                rd(path);
                break;
            case 5:
                edit(path);
                break;
            case 6:
                del(path);
                break;
            case 7:
                exit();
                break;
            default:
                mainFrame.display("无效的命令!");
        }
        mainFrame.display(getPath());

    }

    */
/**
     * 获得当前所在目录;
     *//*

    public static String getPath() {
        return currentPath + ">";
    }

    */
/**
     * 获得路径path下的文件;
     */
/*    public static File[] listAll(String path) {

        try {
            File f = new File(path);
            String[] fileName;
            if (f.isDirectory()) {
                fileName = f.list();
                mainFrame.display("共有" + fileName.length + "个文件");
                for (int i = 0; i < fileName.length; i++)
                    mainFrame.display("    " + fileName[i]);
                return null;
            } else if (f.isFile()) {
                mainFrame.display("这是一个文件");
                return null;
            } else {
                //System.out.println(path);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public static File[] listDirectory(String path){
        File f = new File(path);
        String[] fileName;
        File[] files;
        if (f.isDirectory()) {
            fileName = f.list();
            files = f.listFiles();
            //for (int i = 0; i < fileName.length; i++)
            //System.out.println(fileName[0]);
            //System.out.println(files[0].getPath());
            return files;
        } else {
            //System.out.println(path+"是文件");
            return null;
        }
    }*/

/**
     * 判断这个路径是否正确;
     *//*

    public static boolean isRightPath(String path) {
        File file = new File(path);
        if (file.isDirectory() || file.isFile())
            return true;
        else
            return false;
    }

    */
/**
     * 进行cd操作;cd<目录名> 功能:进入下一个目录;
     *//*

    public static void cd(String path, String file) {
        String temp = path + "\\" + file;
        if (!isRightPath(temp)) {
            mainFrame.display("没有找到这个文件夹");
        } else {
            if (!file.equals(""))
                currentPath += "\\" + file;
        }
    }

    */
/**
     * 进行dir操作;dir [<目录名>] 功能: 显示目录下的所有文件;
     *//*

    public static void dir(String path) {
        if (path != null)
            listAll(path);
    }

    */
/**
     * 进行md操作;md <目录名> 功能: 创建新目录
     *//*

    public static void md(String directory) {
        if (!currentPath.equals("")) {
            String temp = currentPath + "\\" + directory;
            File newFile = new File(temp);
            if (!newFile.exists()) {
                try {
                    if (newFile.isDirectory() == false) {
                        newFile.mkdirs();
                        mainFrame.display("文件夹创建成功!");
                    } else {
                        mainFrame.display("文件夹创建出错!");
                    }
                } catch (Exception e) {
                    mainFrame.display("出错信息:" + e.getMessage());
                }
            } else {
                mainFrame.display("文件夹已经存在");
            }
        }
    }

    */
/**
     * 进行rd操作;rd <目录名> 功能: 删除目录;
     *//*

    public static void rd(String directory) {
        if (!currentPath.equals("")) {
            String temp = currentPath + "\\" + directory;
            File file = new File(temp);
            if (file.exists()) {
                if (file.delete()) {
                    mainFrame.display("文件夹删除成功!");
                } else {
                    mainFrame.display("文件夹删除操作出错!");
                }
            } else {
                mainFrame.display("文件夹不存在");
            }
        }
    }

    */
/**
     * 进行edit操作:edit <文件名> 功能: 新建文件
     *//*

    public static void edit(String file) {
        if (!currentPath.equals("")) {
            String temp = currentPath + "\\" + file;
            File newFile = new File(temp);
            if (newFile.exists()) {
                mainFrame.display("文件已经存在!");
            } else {
                try {
                    newFile.createNewFile();
                    mainFrame.display("文件创建成功!");
                } catch (Exception e) {
                    mainFrame.display("文件创建失败:" + e.getMessage());
                }
            }
        }
    }

    */
/**
     * 进行del操作;del <文件名> 功能:删除文件;
     *//*

    public static void del(String file) {
        if (!file.equals("")) {
            String temp = currentPath + "\\" + file;
            File dfile = new File(temp);
            if (dfile.exists()) {
                if (dfile.delete()) {
                    mainFrame.display("文件删除成功!");
                } else {
                    mainFrame.display("文件删除操作出错!");
                }
            } else {
                mainFrame.display("文件不存在");
            }
        }
    }

    */
/**
     * 进行exit操作; 功能:退出文件系统;
     *//*

    public static void exit() {
        mainFrame.display("退出系统");
        System.exit(1);

    }
}

*/
