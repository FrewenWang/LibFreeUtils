package com.frewen.freeutils.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class FreeIOStream {

    private static final String TAG = FreeIOStream.class.getSimpleName();

    /**
     * 从输入流中获取数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] inputStream2bytes(InputStream inStream) throws Exception {
        // 实例化一个字节数组输出流
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 实例化一个缓冲字节数组
        byte[] buffer = new byte[1024];
        int len = 0;
        // 依次读取输入流的到缓冲字节数组中，然后再将其写入到字节数组输出流中
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        // 释放所有的输入输出流资源
        closeAllIO(inStream, outStream);
        // 将其转化成为字节数组，返回
        return outStream.toByteArray();
    }

    /**
     * 将输入流转换成String
     *
     * @param in
     * @return
     */
    public static String inputStream2String(InputStream in) throws IOException {
        // 实例化一个字节数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        // 按行读取的输入流。当长度不等于-1
        while ((len = in.read(buffer)) != -1) {
            // 写入到缓存字节数组
            out.write(buffer, 0, len);
        }
        // 转成String
        String result = out.toString();
        // 释放所有的输入输出流资源
        closeAllIO(in, out);
        return result;
    }

    /**
     * 关闭所有的输入输出流
     *
     * @param closeables
     */
    public static void closeAllIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        //遍历所有的可变参数。进行关闭流
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                FreeLog.e(TAG, "an error occured when close outputStream" + e.toString());
                throw new RuntimeException(
                        FreeIOStream.class.getClass().getName(), e);
            }
        }
    }
}
