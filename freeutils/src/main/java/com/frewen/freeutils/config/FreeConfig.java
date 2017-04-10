package com.frewen.freeutils.config;

/**
 * 应用的一些配置信息数据类
 * 
 * @author Frewen.W QQ:61511225
 * @version [版本号, 2015-12-13]
 * @since [产品/模块版本]
 */
public class FreeConfig {

    /** UI设计的基准宽度. */
    public static int UI_WIDTH = 720;

    /** UI设计的基准高度. */
    public static int UI_HEIGHT = 1280;

    /** UI设计的密度. */
    public static int UI_DENSITY = 2;

    /** 默认 SharePreferences文件名. */
    public static String SHARED_PATH = "app_share";
    //*****************这些都在download目录下面的子目录************
    /** 默认下载文件地址. */
    public static String DOWNLOAD_ROOT_DIR = "download";

    /** 默认下载图片文件地址. */
    public static String DOWNLOAD_IMAGE_DIR = "images";

    /** 默认下载文件地址. */
    public static String DOWNLOAD_FILE_DIR = "files";

    public static String DOWNLOAD_APP_DIR = "app";
    //******************这些都在Cache目录下面的子目录****************
    /** APP缓存目录. */
    public static String CACHE_DIR = "cache";

    /** 默认的图片缓存的目录 */
    public static String CACHE_IMAGE_PATH = "image_cache";

    /** 错误信息的缓存路径 */
    public static String ERROR_CACHE_DIR = "error_cache";

    //**************************************************
    /** DataBase目录. */
    public static String DB_DIR = "database";

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

    }

}
