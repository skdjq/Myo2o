package com.o2o.dao;

import com.o2o.BaseTest;
import com.oto.dao.AreaDao;
import com.oto.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areas = areaDao.queryArea();
        assertEquals(2, areas.size());
    }
}
