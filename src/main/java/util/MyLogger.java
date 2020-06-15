package util;

import java.util.Date;

public class MyLogger {
    public static void log(String message){
        System.out.println(new Date() + ":" + message);
    }
}
