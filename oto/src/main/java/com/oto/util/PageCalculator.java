package com.oto.util;

public class PageCalculator {
    // PageIndex转换为RowIndex
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0)?(pageIndex-1)*pageSize:0;
    }
}
