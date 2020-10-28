package com.oto.util;

public class PathUtil {
    private static String seperator = System.getProperty("file.separator");

    // 返回项目的根路径
    public static String getImgBasePath() {
        String os =System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")) {
            // 根据自己的实际路径进行设置
            basePath = "D:/projectdev/image/";
        }else {
            // 根据自己的实际路径进行设置
            basePath = "/home/o2o/image/";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    // 根据不同的业务需求返回不同的子路径
    public static String getShopImagePath(long shopId) {
        String imagePath = "upload/item/shop/"+ shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
