package com.qikenet.time;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;

public class NTPServer {

    public static long getTime() throws Exception {
        String host = "ntp1.aliyun.com";
        int port=123;

        InetAddress address = InetAddress.getByName(host);

        DatagramSocket datagramSocket = new DatagramSocket();

        //发送包
        // NTP message size - 16 bytes of the digest (RFC 2030)
        byte[] buf = new byte[48];
        // Setting the Leap Indicator, Version Number and Mode values
        buf[0] = 0x1B; // LI = 0 (no warning), VN = 3 (IPv4 only), Mode = 3 (Client Mode)

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        Long begin = System.currentTimeMillis();
        datagramSocket.send(packet);

        datagramSocket.receive(packet);
        long serverReplyTime = System.currentTimeMillis() - begin;

        NtpMessage msg = new NtpMessage(buf);
        double timestamp=msg.transmitTimestamp;
        // 是因为获得到的时间是格林尼治时间，所以要变成东八区的时间，否则会与与北京时间有8小时的时差
        double utc = timestamp - (2208988800.0);

        // milliseconds
        long ms = (long) (utc * 1000.0);
        ms += serverReplyTime;

        return ms;
    }
    public static void main(String[] args) throws Exception {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getTime()));

    }


}
