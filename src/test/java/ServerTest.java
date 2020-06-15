import util.MyLogger;
import util.Tool;

import java.io.IOException;

public class ServerTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        MyServer s = new MyServer();
        MyLogger.log("公网IP：" + Tool.getNetIp());
        MyLogger.log("端口号：" + s.getPort());
        MyLogger.log("Address：" + s.getAddress());
        s.start();
    }
}
