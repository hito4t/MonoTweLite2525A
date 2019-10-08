package com.hito4t.iot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MonoTweLite2525ATest {
    public static void main(String[] args) throws Exception {
        String portName;
        if (args.length > 0) {
            portName = args[0];
        } else {
            portName = "/dev/ttyUSB0";
        }
        MonoTweLite2525A sensor = new MonoTweLite2525A(portName);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        for (int i = 0; i < 20; i++) {
            MonoTweLite2525AData data = sensor.read();
            System.out.println(String.format("time=%s, SID=%04X, value1=%d, value2=%d, value3=%d, value4=%d",
                    df.format(new Date(data.getTime())), data.getSID(), data.getValue1(), data.getValue2(), data.getValue3(), data.getValue4()));
        }

        sensor.close();
    }
}
