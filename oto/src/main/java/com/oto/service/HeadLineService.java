package com.oto.service;

import com.oto.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {
    /**
     * 根据传入的条件返回指定的头条列表
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
