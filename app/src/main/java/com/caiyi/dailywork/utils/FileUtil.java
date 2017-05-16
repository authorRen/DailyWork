package com.caiyi.dailywork.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * 文件工具类。读写sdcard记得申请权限.
 * Created by HuoGuangxu on 2016/10/18.
 */

public class FileUtil {
    public static final long KB_IN_BYTES = 1024;
    public static final long MB_IN_BYTES = KB_IN_BYTES * 1024;
    public static final long GB_IN_BYTES = MB_IN_BYTES * 1024;
    public static final long TB_IN_BYTES = GB_IN_BYTES * 1024;
    public static final long PB_IN_BYTES = TB_IN_BYTES * 1024;

    private FileUtil() {
        throw new UnsupportedOperationException("No instances!");
    }

    /**
     * 判断文件是否存在
     *
     * @param absolutePath 文件全路径
     */
    public static boolean isFileExists(String absolutePath) {
        if (StringUtil.isNullOrEmpty(absolutePath)) {
            return false;
        }
        File file = new File(absolutePath);
        return isFileExists(file);
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 创建新文件.如果指定文件存在，则删除。
     *
     * @param absolutePath 文件全路径
     * @return 文件对象
     */
    public static File createFile(String absolutePath) {
        if (StringUtil.isNullOrEmpty(absolutePath)) {
            return null;
        }
        File file = new File(absolutePath);
        if (isFileExists(file)) {
            if (!file.delete()) {
                return null;
            }
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                return null;
            }
        }
        return file;
    }

    /**
     * 创建文件.如果指定文件存在，则删除。
     *
     * @param dir  文件目录
     * @param name 文件名称
     * @return 文件对象
     */
    public static File createFile(String dir, String name) {
        if (StringUtil.isNullOrEmpty(dir) || StringUtil.isNullOrEmpty(name)) {
            return null;
        }
        return createFile(dir + File.separator + name);
    }

    /**
     * 创建文件.如果指定文件存在，则删除。
     *
     * @param dirFile 文件夹对象
     * @param name    文件名称
     * @return 文件对象
     */
    public static File createFile(File dirFile, String name) {
        if (dirFile == null || !dirFile.isDirectory() || StringUtil.isNullOrEmpty(name)) {
            return null;
        }
        return createFile(dirFile.getAbsolutePath(), name);
    }

    /**
     * 创建文件目录
     *
     * @param dir 文件目录
     * @return 文件对象
     */
    public static File createDir(String dir) {
        if (StringUtil.isNullOrEmpty(dir)) {
            return null;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                return null;
            }
        }
        return dirFile;
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(File file) {
        return file != null && file.isFile() && file.delete();
    }

    /**
     * 删除目录下的全部文件,删除成功只留下空目录。有一个删除不成功返回false
     */
    public static boolean deleteDir(String dir) {
        return deleteDir(getFileByPath(dir));
    }

    /**
     * 删除目录下的全部文件,删除成功只留下空目录。有一个删除不成功返回false
     */
    public static boolean deleteDir(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file == null) {
                continue;
            }
            if (file.isFile()) {
                if (!deleteFile(file)) {
                    return false;
                }
            } else if (file.isDirectory()) {
                if (!deleteDir(file)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 根据文件全路径获取文件对象.
     */
    public static File getFileByPath(String filePath) {
        return StringUtil.isNullOrEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 图片的uri路径转换为图片文件路径
     */
    public static String uri2ImageFile(Context context, Uri imageUri) {
        String filePath = "";
        if (context == null || imageUri == null) {
            return filePath;
        }

        if (imageUri.toString().startsWith("file:///")) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imageUri.getPath(), options);
            return imageUri.getPath();
        }

        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(imageUri, filePathColumn, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }

    /**
     * 返回byte的数据大小对应的格式化大小文字
     *
     * @param size   大小
     * @param format 格式化格式（DecimalFormat参数）
     * @return 格式化后的文字
     */
    public static String formatFileSize(long size, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            return decimalFormat.format(size / 1024f) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            return decimalFormat.format(size / 1024f / 1024f) + "MB";
        } else {
            return decimalFormat.format(size / 1024f / 1024f / 1024f) + "GB";
        }
    }

    /**
     * 获取全路径中的文件拓展名.
     *
     * @param file 文件
     * @return 文件拓展名
     */
    public static String getFileExtension(File file) {
        return file == null ? "" : getFileExtension(file.getAbsolutePath());
    }

    /**
     * 获取文件拓展名.eg: jpg、apk、txt.
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    public static String getFileExtension(String filePath) {
        if (StringUtil.isNullOrEmpty(filePath)) {
            return "";
        }
        int lastComma = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastComma == -1 || lastSep >= lastComma) {
            return "";
        }
        return filePath.substring(lastComma + 1);
    }

    /**
     * 保存stream到文件
     *
     * @param file        目标文件。如果文件已存在，返回false。
     * @param inputStream 要保存的文件流。
     * @param forceSave   如果目标文件已存在是否强制删除。
     */
    public static boolean saveStream(File file, InputStream inputStream, boolean forceSave) {
        if (inputStream == null) {
            return false;
        }
        if (isFileExists(file)) {//文件存在
            if (forceSave) {//需要强制删除
                if (!deleteFile(file)) {//删除失败
                    return false;
                }
            } else {
                return false;
            }
        }
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            IOUtil.close(outputStream);
            IOUtil.close(inputStream);
        }
    }
}
