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

        byte[] bytes = sensor.read();
        for (byte b : bytes) {
        	System.out.print(String.format("%02X ", b));
        }
        System.out.println();

        sensor.close();
    }
}
