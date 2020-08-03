package com.cbox.base.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImagesUtil {

    private final static ImagesUtil iu = new ImagesUtil();

    private ImagesUtil() {

    }

    public static ImagesUtil getInstance() {
        return iu;
    }

    /**
     * 切割图片
     * 
     * @param os 切割后的输出流
     * @param is 输入流
     * @param type 文件的图片类型
     * @param x x坐标
     * @param y y坐标
     * @param width 宽度
     * @param height 高度
     */
    public void cropImg(OutputStream os, InputStream is, String type, int x, int y, int width, int height) {
        Image img = null;
        BufferedImage imgBuf = null;
        ImageFilter cropFilter = null;
        try {
            imgBuf = ImageIO.read(is);
            cropFilter = new CropImageFilter(x, y, width, height);
            img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(imgBuf.getSource(), cropFilter));
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(img, 0, 0, null);
            ImageIO.write(tag, type, os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 切割图片
     * 
     * @param os 切割后的输出流
     * @param is 输入流
     * @param type 文件的图片类型
     * @param x x坐标
     * @param y y坐标
     * @param width 宽度
     * @param height 高度
     */
    public String cropImg(String oPath, InputStream is, int x, int y, int width, int height) {
        String type = oPath.substring(oPath.lastIndexOf(".") + 1);
        try {
            OutputStream os = new FileOutputStream(oPath);
            cropImg(os, is, type, x, y, width, height);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 切割图片
     * 
     * @param oPath 输出文件
     * @param iPath 输入文件名
     * @param type 文件的图片类型
     * @param x x坐标
     * @param y y坐标
     * @param width 宽度
     * @param height 高度
     */
    public String cropImg(String oPath, String iPath, int x, int y, int width, int height) {
        String type = oPath.substring(oPath.lastIndexOf(".") + 1);
        try {
            OutputStream os = new FileOutputStream(oPath);
            InputStream is = new FileInputStream(iPath);
            cropImg(os, is, type, x, y, width, height);
            return oPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 切割图片自动根据输入的文件名转换为xxx_small.type
     * 
     * @param iPath 输入文件名
     * @param type 文件的图片类型
     * @param x x坐标
     * @param y y坐标
     * @param width 宽度
     * @param height 高度
     */
    public String cropImg(String iPath, int x, int y, int width, int height) {
        String type = iPath.substring(iPath.lastIndexOf(".") + 1);
        String sname = generatorSmallFileName(iPath);
        try {
            InputStream is = new FileInputStream(iPath);
            OutputStream os = new FileOutputStream(sname);
            cropImg(os, is, type, x, y, width, height);
            return sname;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generatorSmallFileName(String name) {
        String fn = name.substring(0, name.lastIndexOf("."));
        return name.replace(fn, fn + "_small");
    }

    /**
     * 转换图片操作
     * 
     * @param os 要转换图片的输出流
     * @param is 要转换图片的输入流
     * @param width 要压缩的宽度
     * @param height 要压缩的高度
     * @param proportion 是否进行等比例压缩
     * @throws IOException
     */
    public void compressImg(OutputStream os, InputStream is, int width, int height, boolean proportion) {
        compressImg(os, is, width, height, proportion, false);
    }

    /**
     * 转换图片操作
     * 
     * @param os 要转换图片的输出流
     * @param is 要转换图片的输入流
     * @param width 要压缩的宽度
     * @param height 要压缩的高度
     * @param proportion 是否进行等比例压缩
     * @param magnify 是否进行放大
     * @throws IOException
     */
    public void compressImg(OutputStream os, InputStream is, int width, int height, boolean proportion, boolean magnify) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
            System.out.println(img);
            int newWidth;
            int newHeight;
            int oldWidth = img.getWidth(null);
            int oldHeight = img.getHeight(null);
            boolean isWrite = false;
            if (!magnify) {
                boolean iw = width >= height ? true : false;
                if (iw) {
                    if (width > oldWidth)
                        isWrite = true;
                } else {
                    if (height > oldHeight)
                        isWrite = true;
                }
                if (isWrite) {
                    System.out.println("write");
                    ImageIO.write(img, "jpg", os);
                    os.flush();
                }
            }
            if (!isWrite) {
                // 判断是否是等比缩放
                if (proportion) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) oldWidth) / (double) width + 0.1;
                    double rate2 = ((double) oldHeight) / (double) height + 0.1;
                    System.out.println((rate1 + "," + rate2));
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 < rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = width; // 输出的图片宽度
                    newHeight = height; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

                ImageIO.write(tag, "JPG", os);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 转换图片操作
     * 
     * @param outputFile 输出文件
     * @param inputFile 输入文件
     * @param width 要压缩的宽度
     * @param height 要压缩的高度
     * @param proportion 是否进行等比例压缩
     * @throws IOException
     */
    public String compressImg(File outputFile, File inputFile, int width, int height, boolean proportion) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(inputFile);
            os = new FileOutputStream(outputFile);
            compressImg(os, is, width, height, proportion);
            return outputFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换图片操作
     * 
     * @param outputFile 输出文件
     * @param is 输入流
     * @param width 要压缩的宽度
     * @param height 要压缩的高度
     * @param proportion 是否进行等比例压缩
     * @throws IOException
     */
    public String compressImg(File outputFile, InputStream is, int width, int height, boolean proportion) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(outputFile);
            compressImg(os, is, width, height, proportion);
            return outputFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换图片操作
     * 
     * @param oPath 输出路径
     * @param is 输入流
     * @param width 要压缩的宽度
     * @param height 要压缩的高度
     * @param proportion 是否进行等比例压缩
     * @throws IOException
     */
    public String compressImg(String oPath, InputStream is, int width, int height, boolean proportion) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(oPath);
            compressImg(os, is, width, height, proportion);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return oPath;
    }

    /**
     * 转换图片操作
     * 
     * @param oPath 输出路径
     * @param iPath 输入路径
     * @param width 要压缩的宽度
     * @param height 要压缩的高度
     * @param proportion 是否进行等比例压缩
     * @throws IOException
     */
    public String compressImg(String oPath, String iPath, int width, int height, boolean proportion) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(iPath);
            os = new FileOutputStream(oPath);
            compressImg(os, is, width, height, proportion);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return oPath;
    }

    public int getWidth(InputStream is) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(is);
        return img.getWidth(null);
    }

    public int getWidth(String path) {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            return getWidth(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getWidth(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getWidth(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getHeight(InputStream is) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(is);
        return img.getHeight(null);
    }

    public int getHeight(String path) {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            return getHeight(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getHeight(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getHeight(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回一个数组，第一个值是宽，第二个值是高
     * 
     * @param is
     * @return 返回一个数组，第一个值是宽，第二个值是高
     * @throws IOException
     */
    public int[] getWidthAndHeight(InputStream is) throws IOException {
        Image img = null;
        img = ImageIO.read(is);
        return new int[] { img.getWidth(null), img.getHeight(null) };
    }

    /**
     * 返回一个数组，第一个值是宽，第二个值是高
     * 
     * @param is
     * @return 返回一个数组，第一个值是宽，第二个值是高
     * @throws IOException
     */
    public int[] getWidthAndHeight(String path) {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            return getWidthAndHeight(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回一个数组，第一个值是宽，第二个值是高
     * 
     * @param is
     * @return 返回一个数组，第一个值是宽，第二个值是高
     * @throws IOException
     */
    public int[] getWidthAndHeight(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getWidthAndHeight(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据图片网络地址获取图片的byte[]类型数据
     *
     * @param urlPath 图片网络地址
     * @return 图片数据
     */
    public static byte[] getImageFromURL(String urlPath) {
        byte[] data = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            // conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);
            is = conn.getInputStream();
            if (conn.getResponseCode() == 200) {
                data = readInputStream(is);
            } else {
                data = null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return data;
    }

    /**
     * 读取InputStream数据，转为byte[]数据类型
     *
     * @param is InputStream数据
     * @return 返回byte[]数据
     */
    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = baos.toByteArray();
        try {
            is.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 图片缩放。值越大图像缩的越小
     *
     * @param im
     * @return
     */
    public static BufferedImage resize(BufferedImage im, int width, int height) {
        /* 原始图像的宽度和高度 */
        int oldWidth = im.getWidth();
        int oldHeight = im.getHeight();

        float resizeTimes;
        if (width > height) {
            resizeTimes = Float.valueOf(oldHeight + "") / height;
        } else {
            resizeTimes = Float.valueOf(oldWidth + "") / width;
        }

        /* 调整后的图片的宽度和高度 */
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) / resizeTimes);
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) / resizeTimes);

        if (toWidth % 2 != 0) {
            toWidth += 1;
        }
        if (toHeight % 2 != 0) {
            toHeight += 1;
        }

        return resizeImage(im, toWidth, toHeight);
    }

    /**
     * 图片缩放。值越大图像缩的越小
     *
     * @param im
     * @param resizeTimes
     * @return
     */
    public static BufferedImage resizeImage(BufferedImage im, float resizeTimes) {
        /* 原始图像的宽度和高度 */
        int width = im.getWidth();
        int height = im.getHeight();

        /* 调整后的图片的宽度和高度 */
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) / resizeTimes);
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) / resizeTimes);

        if (toWidth % 2 != 0) {
            toWidth += 1;
        }
        if (toHeight % 2 != 0) {
            toHeight += 1;
        }

        return resizeImage(im, toWidth, toHeight);
    }

    /**
     * 图片缩放,指定宽高
     *
     * @return
     */
    public static BufferedImage resizeImage(BufferedImage im, int toWidth, int toHeight) {
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }

    /**
     * 裁剪
     *
     * @return
     */
    public static BufferedImage cutImage(BufferedImage im, int x, int y, int width, int height) {

        return im.getSubimage(x, y, width, height);

    }
}
