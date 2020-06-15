import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import util.MyLogger;
import util.Tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;

/**
 *客户端
 *@author Sharkey
 *@date 2020/06/13
 */

public class MyClient extends WebSocketClient {
    public MyClient(URI url){
        super(url);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        MyLogger.log("连接服务器");
    }

    @Override
    public void onMessage(String s) {
        MyLogger.log("收到信息：" + s);
    }

    @Override
    public void onMessage(ByteBuffer buffer){

    }


    @Override
    public void onClose(int i, String s, boolean b) {
        MyLogger.log("关闭了连接");
    }

    @Override
    public void onError(Exception e) {
        MyLogger.log("连接出现了问题");
    }

    public void SendFile(File file) throws IOException {
        String name = file.getName();
        this.send(name);
        byte[] byteName = Tool.StringToLenBytes(name, 20);
        if(byteName == null){
            MyLogger.log("文件名字太长，请修改一下吧，秋梨膏！！！！");
            return ;
        }
        FileInputStream in = null;
        try{
            in = new FileInputStream(file);
            byte[] data = new byte[1004];
            int len = -1;
            while((len = in.read(data)) != -1){
                byte[] frame = Tool.MergeTwoBytes(byteName, data);
                this.send(frame);
            }
        } catch (Exception e){
            MyLogger.log(e.toString());
        } finally {
            if(in != null){
                in.close();
            }
        }
    }

}
