package com.frewen.freeutils.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.frewen.freeutils.config.FreeConfig;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作类
 *
 * @author Frewen.W QQ:61511225
 * @version [版本号, 2015-6-17]
 * @since [产品/模块版本]
 */
@SuppressWarnings("deprecation")
public class FreeFiles {

    private static final String TAG = null;

    /**
     * 默认APP目录下面Doanload根目录.
     */
    public static String mDownloadRootDir = null;

    /**
     * 默认下载图片文件目录.
     */
    public static String mDownloadImageDir = null;

    /**
     * 默认下载文件目录.
     */
    public static String mDownloadFileDir = null;

    /**
     * 默认APP目录下面Cache根目录.
     */
    public static String mCacheRootDir = null;

    /**
     * 默认默认图片缓存的缓存目录
     */
    public static String mCacheImagesDir = null;

    /**
     * 默认异常信息缓存目录
     */
    public static String mCacheErrorDir = null;

    /**
     * 默认下载数据库文件的目录.
     */
    public static String mDatabaseRootDir = null;

    /**
     * 默认SharedPrefernce的文件的目录.
     */
    public static String mSharedRootDir = null;

    /**
     * 剩余空间大于200M才使用SD缓存.
     */
    private static int freeSdSpaceNeededToCache = 200 * 1024 * 1024;

    private static final int BUFF_SIZE = 1024; // 1k Byte

    /**
     * 在应用的Application中来初始化我们应用需要的文件目录
     *
     * @param appName 传入应用的名字信息，在磁盘下建立该目录。用于存储本地文件或者缓存
     */
    public static void initFileDir(String appName) {

        //默认下载文件根目录.  /AppName/download/
        String downloadRootPath = File.separator + appName + File.separator + FreeConfig.DOWNLOAD_ROOT_DIR
                + File.separator;

        //默认下载图片文件目录. /AppName/download/images/
        String imageDownloadPath = downloadRootPath + FreeConfig.DOWNLOAD_IMAGE_DIR + File.separator;

        //默认下载文件目录./AppName/download/files/
        String fileDownloadPath = downloadRootPath + FreeConfig.DOWNLOAD_FILE_DIR + File.separator;

        //默认的文件缓存的根目录 /AppName/cache/
        String cacheRootPath = File.separator + appName + File.separator + FreeConfig.CACHE_DIR + File.separator;

        //默认图片缓存缓存目录./AppName/cache/image_cache/
        String cacheImagePath = cacheRootPath + FreeConfig.CACHE_IMAGE_PATH + File.separator;

        //默认图片缓存缓存目录./AppName/cache/error_cache/
        String cacheErrorPath = cacheRootPath + FreeConfig.ERROR_CACHE_DIR + File.separator;

        //默认DB目录.
        String dbRootPath = File.separator + appName + File.separator + FreeConfig.DB_DIR + File.separator;

        //默认的SharedPreference的存储根目录
        String sharedRootPath = File.separator + appName + File.separator + FreeConfig.SHARED_PATH + File.separator;

        try {
            if (!isCanUseSD()) {
                return;
            } else {

                File root = Environment.getExternalStorageDirectory();
                //下载目录的根目录
                File downloadDir = new File(root.getAbsolutePath() + downloadRootPath);
                if (!downloadDir.exists()) {
                    downloadDir.mkdirs();
                }

                mDownloadRootDir = downloadDir.getPath();
                Log.i(TAG, "Msg:initFileDir: mDownloadRootDir==" + mDownloadRootDir);

                File imageDownloadDirFile = new File(root.getAbsolutePath() + imageDownloadPath);
                if (!imageDownloadDirFile.exists()) {
                    imageDownloadDirFile.mkdirs();
                }
                mDownloadImageDir = imageDownloadDirFile.getPath();

                File fileDownloadDirFile = new File(root.getAbsolutePath() + fileDownloadPath);
                if (!fileDownloadDirFile.exists()) {
                    fileDownloadDirFile.mkdirs();
                }
                mDownloadFileDir = fileDownloadDirFile.getPath();

                File cacheRootDirFile = new File(root.getAbsolutePath() + cacheRootPath);
                if (!cacheRootDirFile.exists()) {
                    cacheRootDirFile.mkdirs();
                }
                mCacheRootDir = cacheRootDirFile.getPath();

                File cacheImageFile = new File(root.getAbsolutePath() + cacheImagePath);
                if (!cacheImageFile.exists()) {
                    cacheImageFile.mkdirs();
                }
                mCacheImagesDir = cacheImageFile.getPath();

                File cacheErrorFile = new File(root.getAbsolutePath() + cacheErrorPath);
                if (!cacheImageFile.exists()) {
                    cacheImageFile.mkdirs();
                }
                mCacheErrorDir = cacheErrorFile.getPath();

                File dbRootFile = new File(root.getAbsolutePath() + dbRootPath);
                if (!dbRootFile.exists()) {
                    dbRootFile.mkdirs();
                }
                mDatabaseRootDir = dbRootFile.getPath();

                File sharedRootFile = new File(root.getAbsolutePath() + sharedRootPath);
                if (!sharedRootFile.exists()) {
                    sharedRootFile.mkdirs();
                }
                mSharedRootDir = sharedRootFile.getPath();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述：SD卡是否能用.
     *
     * @return true 可用,false不可用
     */
    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 文件夹是否存在的判断
     *
     * @param dir
     * @return
     */
    public static boolean isFolderExist(String dir) {

        File folder = Environment.getExternalStoragePublicDirectory(dir);
        return (folder != null && folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * 压缩文件
     *
     * @param resFile  需要压缩的文件（夹）
     * @param zipout   压缩的目的文件
     * @param rootpath 压缩的文件路径
     */
    public static void zipFile(File resFile, ZipOutputStream zipout, String rootpath) {
        try {
            if (TextUtils.isEmpty(rootpath)) {
                rootpath = resFile.getName();
            } else {
                rootpath = rootpath + (rootpath.endsWith("/") ? "" : File.separator) + resFile.getName();
            }

            rootpath = new String(rootpath.getBytes("8859_1"), "UTF-8");

            if (resFile.isDirectory()) {
                File[] fileList = resFile.listFiles();
                for (File file : fileList) {
                    zipFile(file, zipout, rootpath);
                }
            } else {
                byte buffer[] = new byte[BUFF_SIZE];
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), BUFF_SIZE);
                zipout.putNextEntry(new ZipEntry(rootpath));
                int realLength;
                while ((realLength = in.read(buffer)) != -1) {
                    zipout.write(buffer, 0, realLength);
                }
                in.close();
                zipout.flush();
                zipout.closeEntry();
            }
            FreeLog.i("TAG", "zip file successed");
        } catch (IOException e) {
            FreeLog.e(TAG, "Error ouccured when zip file===" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 描述：获取Asset中的图片资源.
     *
     * @param context  the context
     * @param fileName the file name
     * @return Bitmap 图片
     */
    public static Bitmap getBitmapFromAsset(Context context, String fileName) {
        Bitmap bit = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);
            bit = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            FreeLog.e(TAG, "getBitmapFromAsset error ：" + e.getMessage());
        }
        return bit;
    }

    /**
     * 描述：获取Asset中的图片资源.
     *
     * @param context  the context
     * @param fileName the file name
     * @return Drawable 图片
     */
    public static Drawable getDrawableFromAsset(Context context, String fileName) {
        Drawable drawable = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);
            drawable = Drawable.createFromStream(is, null);
        } catch (Exception e) {
            FreeLog.d(TAG, "获取图片异常：" + e.getMessage());
        }
        return drawable;
    }

    /**
     * 获取Asset中的资源文件内容
     *
     * @param context
     * @param path
     * @return
     */
    public static String getStringFromAsset(Context context, String path) {
        StringBuilder sb = new StringBuilder();
        org.json.JSONObject json = null;

        try {
            InputStream in = context.getAssets().open(path);
            InputStreamReader is = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(is);
            String read;
            read = br.readLine();
            while (read != null) {
                sb.append(read);
                read = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 下载网络文件到SD卡中.如果SD中存在同名文件将不再下载
     *
     * @param url     要下载文件的网络地址
     * @param dirPath 文件路径
     * @return 下载好的本地文件地址
     */
    public static String downloadFile(String url, String dirPath) {
        InputStream in = null;
        FileOutputStream fileOutputStream = null;
        HttpURLConnection connection = null;
        String downFilePath = null;
        File file = null;
        try {
            if (!isCanUseSD()) {
                return null;
            }
            //先判断SD卡中有没有这个文件，不比较后缀部分比较
            String fileNameNoMIME = getCacheFileNameFromUrl(url);
            File parentFile = new File(dirPath);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            File[] files = parentFile.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; ++i) {
                    String fileName = files[i].getName();
                    String name = fileName.substring(0, fileName.lastIndexOf("."));
                    if (name.equals(fileNameNoMIME)) {
                        //文件已存在
                        return files[i].getPath();
                    }
                }
            }

            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.connect();
            //获取文件名，下载文件
            String fileName = getCacheFileNameFromUrl(url, connection);

            file = new File(dirPath, fileName);
            downFilePath = file.getPath();
            if (!file.exists()) {
                file.createNewFile();
            } else {
                //文件已存在
                return file.getPath();
            }
            in = connection.getInputStream();
            fileOutputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int temp = 0;
            while ((temp = in.read(b)) != -1) {
                fileOutputStream.write(b, 0, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FreeLog.e(TAG, "有文件下载出错了,已删除");
            //检查文件大小,如果文件为0B说明网络不好没有下载成功，要将建立的空文件删除
            if (file != null) {
                file.delete();
            }
            file = null;
            downFilePath = null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return downFilePath;
    }

    /**
     * 描述：获取网络文件的大小.
     *
     * @param Url 图片的网络路径
     * @return int 网络文件的大小
     */
    public static int getContentLengthFromUrl(String Url) {
        int mContentLength = 0;
        try {
            URL url = new URL(Url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) url.openConnection();
            mHttpURLConnection.setConnectTimeout(5 * 1000);
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection
                    .setRequestProperty(
                            "Accept",
                            "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            mHttpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
            mHttpURLConnection.setRequestProperty("Referer", Url);
            mHttpURLConnection.setRequestProperty("Charset", "UTF-8");
            mHttpURLConnection
                    .setRequestProperty(
                            "User-Agent",
                            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            mHttpURLConnection.connect();
            if (mHttpURLConnection.getResponseCode() == 200) {
                // 根据响应获取文件大小
                mContentLength = mHttpURLConnection.getContentLength();
            }
        } catch (Exception e) {
            e.printStackTrace();
            FreeLog.d(TAG, "获取长度异常：" + e.getMessage());
        }
        return mContentLength;
    }

    /**
     * 获取文件名，通过网络获取.
     *
     * @param url 文件地址
     * @return 文件名
     */
    public static String getRealFileNameFromUrl(String url) {
        String name = null;
        try {
            if (FreeString.isEmpty(url)) {
                return name;
            }

            URL mUrl = new URL(url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
            mHttpURLConnection.setConnectTimeout(5 * 1000);
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection
                    .setRequestProperty(
                            "Accept",
                            "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            mHttpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
            mHttpURLConnection.setRequestProperty("Referer", url);
            mHttpURLConnection.setRequestProperty("Charset", "UTF-8");
            mHttpURLConnection.setRequestProperty("User-Agent", "");
            mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            mHttpURLConnection.connect();
            if (mHttpURLConnection.getResponseCode() == 200) {
                for (int i = 0; ; i++) {
                    String mine = mHttpURLConnection.getHeaderField(i);
                    if (mine == null) {
                        break;
                    }
                    if ("content-disposition".equals(mHttpURLConnection.getHeaderFieldKey(i).toLowerCase())) {
                        Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
                        if (m.find())
                            return m.group(1).replace("\"", "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            FreeLog.e(TAG, "网络上获取文件名失败");
        }
        return name;
    }

    /**
     * 获取真实文件名（xx.后缀），通过网络获取.
     *
     * @param connection 连接
     * @return 文件名
     */
    public static String getRealFileName(HttpURLConnection connection) {
        String name = null;
        try {
            if (connection == null) {
                return name;
            }
            if (connection.getResponseCode() == 200) {
                for (int i = 0; ; i++) {
                    String mime = connection.getHeaderField(i);
                    if (mime == null) {
                        break;
                    }
                    // "Content-Disposition","attachment; filename=1.txt"
                    // Content-Length
                    if ("content-disposition".equals(connection.getHeaderFieldKey(i).toLowerCase())) {
                        Matcher m = Pattern.compile(".*filename=(.*)").matcher(mime.toLowerCase());
                        if (m.find()) {
                            return m.group(1).replace("\"", "");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            FreeLog.e(TAG, "网络上获取文件名失败");
        }
        return name;
    }

    /**
     * 获取文件名（不含后缀）.
     *
     * @param url 文件地址
     * @return 文件名
     */
    public static String getCacheFileNameFromUrl(String url) {
        if (FreeString.isEmpty(url)) {
            return null;
        }
        String name = null;
        try {
            name = FreeMD5.encode(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取文件名（.后缀），外链模式和通过网络获取.
     *
     * @param url        文件地址
     * @param connection the connection
     * @return 文件名
     */
    public static String getCacheFileNameFromUrl(String url, HttpURLConnection connection) {
        if (FreeString.isEmpty(url)) {
            return null;
        }
        String name = null;
        try {
            //获取后缀
            String suffix = getMIMEFromUrl(url, connection);
            if (FreeString.isEmpty(suffix)) {
                suffix = ".ab";
            }
            name = FreeMD5.encode(url) + suffix;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取文件后缀，本地.
     *
     * @param url        文件地址
     * @param connection the connection
     * @return 文件后缀
     */
    public static String getMIMEFromUrl(String url, HttpURLConnection connection) {

        if (FreeString.isEmpty(url)) {
            return null;
        }
        String suffix = null;
        try {
            //获取后缀
            if (url.lastIndexOf(".") != -1) {
                suffix = url.substring(url.lastIndexOf("."));
                if (suffix.indexOf("/") != -1 || suffix.indexOf("?") != -1 || suffix.indexOf("&") != -1) {
                    suffix = null;
                }
            }
            if (FreeString.isEmpty(suffix)) {
                //获取文件名  这个效率不高
                String fileName = getRealFileName(connection);
                if (fileName != null && fileName.lastIndexOf(".") != -1) {
                    suffix = fileName.substring(fileName.lastIndexOf("."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suffix;
    }

    /**
     * 描述：从sd卡中的文件读取到byte[].
     *
     * @param path sd卡中文件路径
     * @return byte[]
     */
    public static byte[] getByteArrayFromSD(String path) {
        byte[] bytes = null;
        ByteArrayOutputStream out = null;
        try {
            File file = new File(path);
            //SD卡是否存在
            if (!isCanUseSD()) {
                return null;
            }
            //文件是否存在
            if (!file.exists()) {
                return null;
            }

            long fileSize = file.length();
            if (fileSize > Integer.MAX_VALUE) {
                return null;
            }

            FileInputStream in = new FileInputStream(path);
            out = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
            in.close();
            bytes = out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
        return bytes;
    }


    /**
     * 描述：将byte数组写入文件.
     *
     * @param path    the path 写入的文件地址
     * @param content the content  写入的Bytes数组
     * @param create  the create 文件不存在是否创建文件
     */
    public static void writeByteArrayToSD(String path, byte[] content, boolean create) {
        //文件输出流
        FileOutputStream fos = null;
        try {
            File file = new File(path);
            //SD卡是否存在
            if (!isCanUseSD()) {
                return;
            }
            //文件是否存在
            if (!file.exists()) {
                if (create) {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                        file.createNewFile();
                    }
                } else {
                    return;
                }
            }
            fos = new FileOutputStream(path);
            fos.write(content);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 将bitmap写入文件.
     *
     * @param path
     * @param bitmap png
     */
    public static void writeBitmapToSD(String path, Bitmap bitmap, boolean create) {

        FileOutputStream fos = null;
        try {
            File file = new File(path);
            //SD卡是否存在
            if (!isCanUseSD()) {
                return;
            }
            //文件是否存在
            if (!file.exists()) {
                if (create) {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                        file.createNewFile();
                    }
                }
            }
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 拷贝Assets目录内容到sd卡目录
     *
     * @param context
     * @param assetDir "dir"
     * @param outDir   完整sd卡路径
     */
    public static void copyAssets2SD(Context context, String assetDir, String outDir) {
        String[] files;
        try {
            files = context.getAssets().list(assetDir);
            File outDirFile = new File(outDir);
            if (!outDirFile.exists()) {
                outDirFile.mkdirs();
            }

            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];

                String[] filesChild = context.getAssets().list(fileName);
                if (filesChild != null && filesChild.length > 0) {
                    copyAssets2SD(context, fileName, outDir + "/" + fileName);
                } else {
                    InputStream in = null;
                    if (!FreeString.isEmpty(assetDir)) {
                        in = context.getAssets().open(assetDir + "/" + fileName);
                    } else {
                        in = context.getAssets().open(fileName);
                    }
                    File outFile = new File(outDir + "/" + fileName);
                    if (outFile.exists()) {
                        outFile.delete();
                    }
                    outFile.createNewFile();
                    OutputStream out = new FileOutputStream(outFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    in.close();
                    out.close();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算sdcard上的剩余空间.
     *
     * @return the int
     */
    public static int freeSpaceOnSD() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / 1024 * 1024;
        return (int) sdFreeMB;
    }

    /**
     * 根据文件的最后修改时间进行排序.
     */
    public static class FileLastModifSort implements Comparator<File> {

        /*
         * (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 删除所有缓存文件.
     *
     * @return true, if successful
     */
    public static boolean clearDownloadFile() {
        try {
            File fileDirectory = new File(mDownloadRootDir);
            deleteFile(fileDirectory);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除文件.
     *
     * @return true, if successful
     */
    public static boolean deleteFile(File file) {

        try {
            if (!isCanUseSD()) {
                return false;
            }
            if (file == null) {
                return true;
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            } else {
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 描述：读取Assets目录的文件内容.
     *
     * @param context  the context
     * @param name     the name
     * @param encoding the encoding
     * @return the string
     */
    public static String readAssetsByName(Context context, String name, String encoding) {
        String text = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getAssets().open(name));
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                buffer.append(line);
            }
            text = new String(buffer.toString().getBytes(), encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    /**
     * 描述：读取Raw目录的文件内容.
     *
     * @param context  the context
     * @param id       the id
     * @param encoding the encoding
     * @return the string
     */
    public static String readRawByName(Context context, int id, String encoding) {
        String text = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().openRawResource(id));
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                buffer.append(line);
            }
            text = new String(buffer.toString().getBytes(), encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    /**
     * Gets the free sd space needed to cache.
     *
     * @return the free sd space needed to cache
     */
    public static int getFreeSdSpaceNeededToCache() {
        return freeSdSpaceNeededToCache;
    }

}
