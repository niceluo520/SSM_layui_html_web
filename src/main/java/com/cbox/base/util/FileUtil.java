package com.cbox.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FileUtil {
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            return false;
        } else if (destFileName.endsWith(File.separator)) {
            return false;
        } else if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return false;
        } else {
            try {
                return file.createNewFile();
            } catch (IOException arg2) {
                arg2.printStackTrace();
                return false;
            }
        }
    }

    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return false;
        } else {
            if (!destDirName.endsWith(File.separator)) {
                destDirName = destDirName + File.separator;
            }

            return dir.mkdirs();
        }
    }

    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try {
                tempFile = File.createTempFile(prefix, suffix);
                return tempFile.getCanonicalPath();
            } catch (IOException arg5) {
                arg5.printStackTrace();
                return null;
            }
        } else {
            File dir = new File(dirName);
            if (!dir.exists() && !createDir(dirName)) {
                return null;
            } else {
                try {
                    tempFile = File.createTempFile(prefix, suffix, dir);
                    return tempFile.getCanonicalPath();
                } catch (IOException arg6) {
                    arg6.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static String readFileByLines(String fileName, String charsetName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();

        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName(charsetName)));
            String e = null;

            for (int line = 1; (e = reader.readLine()) != null; ++line) {
                System.out.println("line " + line + ": " + e);
                if (1 != line) {
                    sb.append("\n");
                }

                sb.append(e);
            }

            reader.close();
        } catch (IOException arg14) {
            arg14.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException arg13) {
                    ;
                }
            }

        }

        return sb.length() > 0 ? sb.toString() : null;
    }

}