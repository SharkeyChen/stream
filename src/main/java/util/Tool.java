package util;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 *工具类
 *@author Sharkey
 *@date 2020/06/13
 */

public class Tool {

    // 获取局域网IP
    public static String getLocalIp() throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();
        return ia.getHostAddress();
    }

    //获取公网IP
    public static String getNetIp() throws IOException {
        InputStream ins = null;
        try{
            URL url = new URL("http://39.107.26.8:9100/tool/getIp");
            URLConnection connection = url.openConnection();
            ins = connection.getInputStream();
            InputStreamReader isReader = new InputStreamReader(ins, "GB2312");
            BufferedReader bReader = new BufferedReader(isReader);
            StringBuffer webReader = new StringBuffer();
            String str = null;
            while((str = bReader.readLine()) != null){
                webReader.append(str);
            }
            return webReader.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(ins != null){
                ins.close();
            }
        }
        return null;
    }

    // 文件选择
    public static File ChooseFile(){
        int result = 0;
        File file = null;
        JFileChooser chooser = new JFileChooser();
        FileSystemView fsv = FileSystemView.getFileSystemView();
        chooser.setCurrentDirectory(fsv.getHomeDirectory());
        chooser.setDialogTitle("选择你的文件");
        chooser.setApproveButtonText("确定");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        result = chooser.showOpenDialog(chooser);
        if(result == JFileChooser.APPROVE_OPTION){
            MyLogger.log(chooser.getSelectedFile().getPath());
            file = chooser.getSelectedFile();
        }
        return file;
    }

    // 读取文件，返回byte数组
    public static byte[] FileToByteArray(File file) throws IOException {
        InputStream in = null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try{
            in = new FileInputStream(file);
            byte[] b = new byte[1024];
            int len = -1;
            while((len = in.read(b)) != -1){
                bout.write(b,0, len);
            }
            return bout.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                in.close();
            }
            if(bout != null){
                bout.close();
            }
        }
        return null;
    }

    // ByteBuffer 解码
    public static String ByteBufferToString(ByteBuffer bf){
        Charset charset = Charset.forName("utf-8");
        return charset.decode(bf).toString();
    }

    // 获取当前系统的桌面位置
    public static String GetSystemHomePath(){
        return FileSystemView.getFileSystemView().getHomeDirectory().getPath();
    }

    // 保存byte数组为文件
    public static void SaveFileByBytes(byte[] data, String path) throws IOException {
        if(data.length < 3 || path == null || path.equals("")){
            return ;
        }
        FileOutputStream out = null;
        try{
            //设置为追加模式
            out = new FileOutputStream(new File(path), true);
            out.write(data, 0, data.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                out.close();
            }
        }
    }

    // 拼接byte数组
    public static byte[] MergeTwoBytes(byte[] list1, byte[] list2){
        byte[] list3 = new byte[list1.length + list2.length];
        System.arraycopy(list1, 0, list3 , 0 , list1.length);
        System.arraycopy(list2, 0 , list3 , list1.length, list2.length);
        return list3;
    }

    // 将String转化为指定长度的byte[]
    public static byte[] StringToLenBytes(String str, int len){
        byte[] list = str.getBytes();
        byte[] myList = new byte[len];
        if(list.length > len){
            return null;
        }
        for(int i = 0;i < list.length;i ++){
            myList[i] = list[i];
        }
        for(int i = list.length;i < len;i ++){
            myList[i] = (byte)32;
        }
        return myList;
    }
}
