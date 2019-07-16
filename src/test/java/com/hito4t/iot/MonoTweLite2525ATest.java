package com.hito4t.iot;


public class MonoTweLite2525ATest {
    public static void main(String[] args) throws Exception {
        String portName;
        if (args.length > 0) {
            portName = args[0];
        } else {
            portName = "/dev/ttyUSB0";
        }
        MonoTweLite2525A sensor = new MonoTweLite2525A(portName);

        for (int i = 0; i < 20; i++) {
            MonoTweLite2525AData data = sensor.read();
            System.out.println(String.format("SID=%04X, value1=%d, value2=%d, value3=%d, value4=%d", data.getSID(), data.getValue1(), data.getValue2(), data.getValue3(), data.getValue4()));
        }

        sensor.close();
    }
}
