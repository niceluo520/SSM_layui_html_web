package com.cbox.base.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 图片水印工具类 
 * @author cbox
 *
 */
public class ImageMarkLogoUtil {

    // 水印透明度
    private static float alpha = 0.5f;
    // 水印横向位置
    private static int positionWidth = 150;
    // 水印纵向位置
    private static int positionHeight = 300;
    // 水印文字字体
    // private static Font font = new Font("宋体", Font.BOLD, 20);
    // 水印文字颜色
    private static Color color = Color.red;

    private static String formatName = "JPG";

    // 水印横向间隔
    private static int intervalWidth = 700;

    /**
     * 水印文字字体
     * @param fontSize
     * @return
     */
    private static Font getFont(int fontSize) {
        return new Font("宋体", Font.BOLD, fontSize);
    }

    /**
     * 给图片添加水印图片
     * 
     * @param iconPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targerPath) {
        markImageByIcon(iconPath, srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     * 
     * @param iconPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param degree
     *            水印图片旋转角度
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targerPath, Integer degree) {
        OutputStream os = null;
        try {

            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 3、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }

            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            // 5、得到Image对象。
            Image img = imgIcon.getImage();

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            // 6、水印图片的位置
            g.drawImage(img, positionWidth, positionHeight, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();

            // 8、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, formatName, os);

            System.out.println("图片完成添加水印图片");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给图片添加水印文字
     * @param logoText  水印文字
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void markImageByText(String logoText, String srcImgPath, String targerPath) {
        markImageByText(logoText, srcImgPath, targerPath, positionWidth, positionHeight, 30);
    }

    /**
     * 给图片添加水印文字
     * @param logoText  水印文字
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param positionWidth 水印横向位置
     * @param positionHeight 水印纵向位置
     * @param fontSize 水印文字字号
     */
    public static void markImageByText(String logoText, String srcImgPath, String targerPath, int positionWidth, int positionHeight, int fontSize) {
        markImageByText(logoText, srcImgPath, targerPath, positionWidth, positionHeight, fontSize, null);
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     * 
     * @param logoText 水印文字
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param positionWidth 水印横向位置
     * @param positionHeight 水印纵向位置
     * @param fontSize 水印文字字号
     * @param degree 旋转角度
     */
    public static void markImageByText(String logoText, String srcImgPath, String targerPath, int positionWidth, int positionHeight, int fontSize, Integer degree) {

        InputStream is = null;
        OutputStream os = null;
        try {
            // 1、源图片
            File file = new File(srcImgPath);
            Image srcImg = ImageIO.read(file);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // System.out.println(String.format("%.1f", file.length() /
            // 1024.0));// 文件大小
            // int imgWidth = buffImg.getWidth();// 图片宽度
            int imgHeight = buffImg.getHeight();// 图片高度

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(getFont(fontSize));
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            // g.drawString(logoText, positionWidth, positionHeight);

            // 水印平铺
            System.out.println("imgHeight=" + imgHeight);
            // 水印纵向间隔
            int intervalHeight = imgHeight / 6;
            for (int height = positionHeight; height < imgHeight + intervalHeight; height = height + intervalHeight) {
                g.drawString(logoText, positionWidth, height);
                height = height + intervalHeight;
                g.drawString(logoText, positionWidth + intervalWidth, height);
                System.out.println(height);
            }

            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, formatName, os);

            System.out.println("图片完成添加水印文字");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**  
     * 给图片不同位置添加多个图片水印、可设置水印图片旋转角度  
     * @param icon 水印图片路径（如：F:/images/icon.png）
     * @param source 没有加水印的图片路径（如：F:/images/6.jpg）
     * @param output 加水印后的图片路径（如：F:/images/6.jpg）
     * @param imageName 图片名称（如：11111）
     * @param imageType 图片类型（如：jpg）
     * @param degree 水印图片旋转角度，为null表示不旋转
     */
    public static String markImageByMoreIcon(String icon, String source, String targerPath, Integer degree) {
        String result = "添加图片水印出错";
        OutputStream os = null;
        try {
            File file = new File(source);
            File ficon = new File(icon);
            if (!file.isFile()) {
                return source + " 不是一个图片文件!";
            }
            // 将icon加载到内存中
            Image ic = ImageIO.read(ficon);
            // icon高度
            int icheight = ic.getHeight(null);
            int icWidth = ic.getWidth(null);

            // 将源图片读到内存中
            Image img = ImageIO.read(file);
            // 图片宽
            int width = img.getWidth(null);
            // 图片高
            int height = img.getHeight(null);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 创建一个指定 BufferedImage 的 Graphics2D 对象
            Graphics2D g = bi.createGraphics();
            // x,y轴默认是从0坐标开始
            int x = 0;
            int y = 0;
            // 默认两张水印图片的间隔高度是水印图片的1/3
            int temp = icheight / 3;
            int icWidthNum = width / icWidth;
            int space = 1;
            if (height >= icheight) {
                space = height / (icheight + icheight / 3);
                if (space >= 2) {
                    temp = y = icheight / 2;
                    if (space == 1 || space == 0) {
                        x = 0;
                        y = 0;
                    }
                }
            } else {
                x = 0;
                y = 0;
            }
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            // 呈现一个图像，在绘制前进行从图像空间到用户空间的转换
            g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            Random rand = new Random();
            int one = 1;
            if (icWidthNum > 1) {
                one = icWidthNum / 2;
            }
            for (int i = 0; i < space; i++) {

                if (null != degree) {
                    // 设置水印旋转
                    g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
                }
                if (i % 2 == 0) {
                    x = icWidth * rand.nextInt(one);
                } else {
                    x = icWidth * (rand.nextInt(one) + one);
                }
                if (i == (space - 1)) {
                    x = width - icWidth - 10;
                    y = height - icheight - 10;
                }
                // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
                ImageIcon imgIcon = new ImageIcon(icon);

                // 得到Image对象。
                Image con = imgIcon.getImage();
                // 透明度，最小值为0，最大值为1
                float clarity = 0.6f;
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, clarity));

                // 表示水印图片的坐标位置(x,y)
                // g.drawImage(con, 300, 220, null);
                g.drawImage(con, x, y, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                y += (icheight + temp);
            }
            g.dispose();
            os = new FileOutputStream(targerPath);
            ImageIO.write(bi, formatName, os);// 保存图片
            result = "图片完成添加Icon水印";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String srcImgPath = "E:/Workspace/paperless/img/1400216112316593472356011.jpg";
        String logoText = "仅限办理中国联通业务时使用20161123";
        String targerTextPath2 = "E:/Workspace/paperless/img/14002161123165934723560111111.png";

        System.out.println("给图片添加水印文字开始...");
        // 给图片添加水印文字
        // setImageMarkOptions(0.3f, 700, 1, null, null);
        // markImageByText(logoText, srcImgPath, targerTextPath);
        // 给图片添加水印文字,水印文字旋转-45
        markImageByText(logoText, srcImgPath, targerTextPath2, 300, 0, 30, 20);
        System.out.println("给图片添加水印文字结束...");

        // String iconPath = "d:/shuimu.jpg";
        // String targerIconPath = "d:/qie_icon.jpg";
        // String targerIconPath2 = "d:/qie_icon_rotate.jpg";
        // System.out.println("给图片添加水印图片开始...");
        // setImageMarkOptions(0.3f, 1, 1, null, null);
        // // 给图片添加水印图片
        // markImageByIcon(iconPath, srcImgPath, targerIconPath);
        // // 给图片添加水印图片,水印图片旋转-45
        // markImageByIcon(iconPath, srcImgPath, targerIconPath2, -45);
        // System.out.println("给图片添加水印图片结束...");
    }

}
