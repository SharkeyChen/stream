import org.java_websocket.WebSocket;
import util.MyLogger;
import util.Tool;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ClientTest {
    public static void main(String[] args) throws URISyntaxException, IOException {
        MyClient c = new MyClient(new URI("ws://localhost:8088"));
        c.connect();
        while(!c.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
            MyLogger.log(c.getReadyState().name());
        }
        File file = Tool.ChooseFile();
        c.SendFile(file);
    }
}
