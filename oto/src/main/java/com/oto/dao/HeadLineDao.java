package com.oto.dao;

import com.oto.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {
    /**
     * 根据传入的查询条件(头条名查询头条)
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition")HeadLine
                                         headLineCondition);
}
