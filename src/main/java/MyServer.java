import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import util.MyLogger;
import util.Tool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 *服务器端
 *@author Sharkey
 *@date 2020/06/13
 */
public class MyServer extends WebSocketServer {

    // 存放所有套接字
    private List<WebSocket> sockets = new ArrayList<WebSocket>();

    public List<WebSocket> getSockets(){
        return sockets;
    }

    public MyServer(){
        super(new InetSocketAddress(8088));
    }

    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        Write(webSocket, "已经连接上服务器");
        sockets.add(webSocket);
    }

    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println(webSocket.getRemoteSocketAddress() + "连接失败");
        sockets.remove(webSocket);
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println(webSocket.getRemoteSocketAddress() + "发来消息：" + s);
        if(webSocket.hasBufferedData()){
            MyLogger.log("有东西");
        }
        Write(webSocket, "收到消息了");
    }

    @Override
    public void onMessage(WebSocket ws, ByteBuffer buffer){
        MyLogger.log("有BUFFER进来了" + buffer.array().length);
        byte[] data = buffer.array();
        byte[] name = new byte[20];
        for(int i = 0;i < 20;i ++){
            name[i] = data[i];
        }
        byte[] frame = new byte[1004];
        for(int i = 20;i < data.length;i ++){
            frame[i - 20] = data[i];
        }
        String path = Tool.GetSystemHomePath();
        path += "/" + new String(name);
        MyLogger.log(path);
        try {
            Tool.SaveFileByBytes(frame, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onError(WebSocket webSocket, Exception e) {
        System.out.println(webSocket.getRemoteSocketAddress() + "连接发生错误：" + e.getMessage());
        sockets.remove(webSocket);
    }

    public void onStart() {
        System.out.println("服务器端已经开启");
    }

    public void Write(WebSocket ws, String message){
        System.out.println("发送给" + ws.getRemoteSocketAddress() + " 消息为:" + message);
        ws.send(message);
    }
}
