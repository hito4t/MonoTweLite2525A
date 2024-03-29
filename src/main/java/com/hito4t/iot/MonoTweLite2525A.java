package com.hito4t.iot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;

public class MonoTweLite2525A implements AutoCloseable {

    private SerialPort port;


    public MonoTweLite2525A(String portName) throws Exception {
        port = getPort(portName);
    }

    public MonoTweLite2525AData read() throws IOException {
        return read(-1);
    }

    public MonoTweLite2525AData read(long timeout) throws IOException {
        long start = System.currentTimeMillis();
        while (true) {
            byte[] header = new byte[1];
            if (port.readBytes(header, header.length) == 1) {
                if (header[0] == ':') {
                    break;
                }
            }
            if (timeout >= 0 && start + timeout > System.currentTimeMillis()) {
            	return null;
            }
        }

        long time = System.currentTimeMillis();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[2];
        while (true) {
            port.readBytes(buffer, buffer.length);
            if (buffer[0] == '\r' && buffer[1] == '\n') {
                return new MonoTweLite2525AData(time, bytes.toByteArray());
            }

            bytes.write(toInt(buffer[0], buffer[1]));
        }
    }

    private int toInt(byte b1, byte b2) throws IOException {
        return toInt(b1) << 4 | toInt(b2);
    }

    private int toInt(byte b) throws IOException {
        char c = (char)b;
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return 0x0A + c - 'A';
        }
        if (c >= 'a' && c <= 'f') {
            return 0x0A + c - 'a';
        }
        throw new IOException(String.format("Unexpected value : %02X", b));
    }

    public void close() throws Exception {
        port.closePort();
    }

    private SerialPort getPort(String portName) throws Exception {
        List<String> portNames = new ArrayList<String>();
        for (SerialPort port : SerialPort.getCommPorts()) {
            if (port.getSystemPortName().equals(portName)) {
                port.setBaudRate(115200);
                if (!port.openPort(1000)) {
                    throw new Exception("Cannot open port.");
                }
                port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 100, 100);
                return port;
            } else {
                portNames.add(port.getSystemPortName());
            }
        }
        throw new Exception("Port \"" + portName + "\" is not found (found ports = " + portNames + ").");
    }

    private static void debug(String line) {
        System.out.println(line);
    }
}
