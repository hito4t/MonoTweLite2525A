package com.hito4t.iot;

import java.io.IOException;

public class MonoTweLite2525AData {

    private final long time;
    private final int sid;
    private final short value1;
    private final short value2;
    private final short value3;
    private final short value4;


    public MonoTweLite2525AData(long time, byte[] bytes) throws IOException {
        this.time = time;
        byte sum = 0;
        for (int i = 0; i < bytes.length ; i++) {
            sum += bytes[i];
        }
        if (sum != 0) {
            throw new IOException("Checksum error");
        }

        sid = toInt(bytes, 5);

        value1 = getValue(bytes, 0);
        value2 = getValue(bytes, 1);
        value3 = getValue(bytes, 2);
        value4 = getValue(bytes, 3);
    }

    public long getTime() {
        return time;
    }

    public int getSID() {
        return sid;
    }

    public short getValue1() {
        return value1;
    }

    public short getValue2() {
        return value2;
    }

    public short getValue3() {
        return value3;
    }

    public short getValue4() {
        return value4;
    }

    private int toInt(byte[] bytes, int index) {
        return
                (bytes[index    ] << 24) & 0xFF000000 |
                (bytes[index + 1] << 16) & 0x00FF0000 |
                (bytes[index + 2] <<  8) & 0x0000FF00 |
                (bytes[index + 3]      ) & 0x000000FF;
    }

    private short getValue(byte[] bytes, int index) {
        short value = (short)((bytes[18 + index] & 0xFF) << 4);
        value |= (bytes[22] >> index * 2) & 0x03;
        return value;
    }

}
