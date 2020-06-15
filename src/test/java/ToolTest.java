import util.MyLogger;
import util.Tool;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ToolTest {
    public static void main(String[] args) throws IOException {
        MyLogger.log(Tool.getLocalIp());
        MyLogger.log(Tool.getNetIp());
        Tool.ChooseFile();
    }
}
