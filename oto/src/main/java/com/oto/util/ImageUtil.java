package com.oto.util;

import com.oto.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.oto.util.PathUtil.getImgBasePath;

public class ImageUtil {
    private static String basePath = Thread.currentThread()
            .getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat =
            new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        //随机文件名，当前年月日小时分钟秒+五位随机数
        String realFileName = getRandomFileName();
        //文件的扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        //创建目标路径所涉及到的目录
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(getImgBasePath() + relativeAddr);
        //给缩略图上水印
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "avatar.png")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //返回图片存储路径
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/o2o/xxx.jpg,
     * 那么 home work o2o 这三个文件夹都得自动创建
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        // TODO Auto-generated method stub
        String realFileParentPath = getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }


    /**
     * 生成随机文件名，当前年月日小时分钟秒+五位随机数
     */
    public static String getRandomFileName() {
        //获取随机的五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    /**
     * 获取输入文件流的扩展名
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    public static void main(String[] args) throws IOException {
        basePath= URLDecoder.decode(basePath,"utf-8");
        Thumbnails.of(new File("D:/img/favicon.png")).size(200, 200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/avatar.png")), 0.25f)
                .outputQuality(0.8f).toFile("D:/img/faviconnew.png");
    }

    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(getImgBasePath() + storePath);
        if (fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File[] files = fileOrPath.listFiles();
                for(int i = 0; i < files.length; ++i){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        //获取文件的扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        //如果目标路径不存在则自动创建
        makeDirPath(targetAddr);
        //获取文件存储的相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        //获取文件要保存到的目标路径
        File dest = new File(getImgBasePath() + relativeAddr);
        //调用Thumbnails生成图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640).outputQuality(0.5f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        //返回图片的相对路径
        return relativeAddr;
    }
}
