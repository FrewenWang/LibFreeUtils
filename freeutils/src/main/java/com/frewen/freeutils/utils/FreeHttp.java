/*
 * 文件名称：FreeHttp.java
 * 版    权：Frewen.Wong Copyright 2015,  All rights reserved
 * 描    述：<概要描述>
 * 创 建 人：Frewen.W  QQ:61511225
 * 创建时间：2016-2-15
 * 
 * 修改记录：1.<修改时间>  <修改人>  <修改描述>
 *           2.<修改时间>  <修改人>  <修改描述>
 */
package com.frewen.freeutils.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author Frewen.W QQ:61511225
 * @version [版本号, 2016-2-15]
 * @since [产品/模块版本]
 */
public class FreeHttp {

    private static final String TAG = FreeHttp.class.getSimpleName();

    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 异步的Get请求
     * 
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack) {
        //异步任务，起一个子线程来进行异步任务请求，执行下面的doGet()方法，任务完成之后，回调请求完成的接口，回传请求结果
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    /**
     * 异步的Post请求
     * 
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr, final String params, final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();

    }

    /**
     * Get请求，获得返回数据
     * 
     * @param urlStr get请求的Url
     * @return
     */
    public static String doGet(String urlStr) {
        URL url = null;
        //HttpURL的IO流连接
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            //实例化一个带url参数的URL
            url = new URL(urlStr);
            //根据实例化URL来打开一个连接
            conn = (HttpURLConnection) url.openConnection();
            //设置这个连接的一些参数
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            //如果连接的返回参数一个200，说明请求成功，然后就使用连接来获取输入流
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                //这个实例化的一个缓存字节数据，按照每128个字节来进行读取
                byte[] buf = new byte[128];
                //将输入流读取到缓存自己数组中去
                while ((len = is.read(buf)) != -1) {
                    //并将读取的字节写入到字节数组输出流中
                    baos.write(buf, 0, len);
                }
                //读取完成之后，清空这个字节数组输出流中
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //很重要：资源回收，关闭输入流、关闭输出流、关闭连接
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url 发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
