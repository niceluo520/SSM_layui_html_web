package com.cbox.base.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class TicketUtil {
    // private static final int DEFAULT_BYTE = 255;
    // private static final int MESSAGEID_LENGTH = 25;
    // private static final int MAXRANDOMSEQ = 238327;
    // private static final int MAXRANDOM = 916132831;
    private static String macAdderss = null;
    private static int randomSEQ = 0;

    public static String getTicket() {
        StringBuffer ticket = new StringBuffer(25);
        String s1 = toBase62String(System.nanoTime());
        ticket.append(fillString(s1, 8));
        if (StringUtils.isEmpty(macAdderss)) {
            String random = toBase62String(Long.decode("0x" + getMACAddress()).longValue());
            macAdderss = fillString(random, 9);
        }

        ticket.append(macAdderss);
        Random random1 = new Random(System.nanoTime());
        int n = random1.nextInt(916132831);
        ticket.append(fillString(toBase62String((long) n), 5));
        ticket.append(fillString(toBase62String((long) getRandomSeq()), 3));
        return ticket.toString();
    }

    private static synchronized int getRandomSeq() {
        if (randomSEQ > 238327) {
            randomSEQ = 0;
        }

        return randomSEQ++;
    }

    private static String fillString(String str, int len) {
        StringBuffer result = new StringBuffer(len);
        if (!StringUtils.isEmpty(str)) {
            result.append(str);
        }

        for (int i = str.length(); i < len; ++i) {
            result.insert(0, '0');
        }

        return result.substring(0, len);
    }

    private static String getMACAddress() {
        String result = "";

        try {
            byte[] b1 = null;
            byte[] bup = null;
            Enumeration<?> nis = NetworkInterface.getNetworkInterfaces();

            while (nis.hasMoreElements()) {
                NetworkInterface i = (NetworkInterface) nis.nextElement();
                if (!i.isLoopback() && !i.isPointToPoint() && !i.isVirtual()) {
                    if (i.isUp()) {
                        bup = i.getHardwareAddress();
                        break;
                    }

                    b1 = i.getHardwareAddress();
                }
            }

            if (bup == null) {
                bup = b1;
            }

            for (int arg6 = 0; bup != null && arg6 < bup.length; ++arg6) {
                String s = Integer.toHexString(bup[arg6] & 255);
                if (s.length() == 1) {
                    s = "0" + s;
                }

                result = result + s;
            }
        } catch (SocketException arg5) {
            ;
        }

        if (StringUtils.isEmpty(result)) {
            result = "FFFFFFFFFFFF";
        }

        return result;
    }

    private static String toBase62String(long value) {
        StringBuffer str1 = new StringBuffer(15);
        long pValue = value;
        char[] charList = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        do {
            int index = (int) (pValue % 62L);
            pValue /= 62L;
            str1.append(charList[index]);
        } while (pValue != 0L);

        return str1.reverse().toString();
    }
}