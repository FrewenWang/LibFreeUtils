package com.frewen.freeutils.utils;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class FreeMD5 {
    private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'B', 'C', 'D', 'B', 'O', 'T', 'H', 'O', 'F', 'Y', 'O', 'U' };

    /**
     * 对文件进行加密
     * 
     * @param file
     * @return
     */
    public static String encode(File file) {
        FileInputStream in = null;
        MessageDigest md5 = null;
        try {
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toHex(md5.digest());
    }

    /**
     * 对密码、用户信息等字符串进行加密
     * 
     * @param arg
     * @return
     */
    public static String encode(String arg) {
        if (arg == null) {
            arg = "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(arg.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex(md5.digest());
    }

    /**
     * 转化成长度为32位16进制字符
     * 
     * @param bytes
     * @return
     */
    @SuppressLint("DefaultLocale")
    private static String toHex(byte[] bytes) {
        StringBuffer str = new StringBuffer(32);
        for (byte b : bytes) {
            str.append(hexDigits[(b & 0xf0) >> 4]);
            str.append(hexDigits[(b & 0x0f)]);
        }
        return str.toString().toLowerCase();
    }

    //****************第二种：下面这种方法和上面的加密方法，返回的结果是一样的***************
    /**
     * MD5加密 以byte数组表示的字符串
     * 结果与encode(String arg)返回的一样
     * 
     * @param str
     * @return MD5加密后的字符串
     */
    public static String encode2(String str) {
        return encode2(str.getBytes());
    }

    public static String encode2(byte[] bytes) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex(md5.digest());
    }

    ///****************第三种：和上面加密效果是一样的。****************
    public static String encode3(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex3(md5.digest());
    }

    @SuppressLint("DefaultLocale")
    public static String toHex3(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String shex = Integer.toHexString(bytes[i] & 0xff);
            if (shex.length() == 1) {
                shex = '0' + shex;
            }
            sb.append(shex);
        }
        //返回32为的大写的16进制字符
        return sb.toString().toUpperCase();
    }

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        System.out.println(FreeMD5.encode("2011123456"));
        System.out.println(FreeMD5.encode2("2011123456"));
        System.out.println(FreeMD5.encode3("2011123456"));
    }
}