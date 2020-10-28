package com.oto.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request, String name){
        try {
            /*decode适合用来分析数字
             8进:010=>分析后为 8 10进:10=>分析后为 10 16进:#10|0X10|0x10=>分析后是 16
             而valueof    只能分析纯数字的String
             像 010 这样的8进制 他会解析成 =>10
             parseInt(s,int radix)
             若想获得Integer：
             String 为十进制. 采用valueof(String)合适. 非十进制,采用decode(String)
              想要获得int
              String 为十进制. 采用parseInt(String )合适. 非十进制,采用parseInt(String ,int)
            */
            return Integer.decode(request.getParameter(name));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String name){
        try {
            return Long.valueOf(request.getParameter(name));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Double getDouble(HttpServletRequest request,String name){
        try {
            return Double.valueOf(request.getParameter(name));
        } catch (NumberFormatException e) {
            return -1d;
        }
    }

    public static Boolean getBoolean(HttpServletRequest request,String name){
        try {
            return Boolean.valueOf(request.getParameter(name));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request,String name){
        try {
            String result = request.getParameter(name);
            if (result != null){
                //去掉左右两侧的空格
                result = result.trim();
            }
            if ("".equals(result)){
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
