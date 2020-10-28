package com.o2o.service;

import com.o2o.BaseTest;
import com.oto.entity.Area;
import com.oto.service.AreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("六食堂", areaList.get(0).getAreaName());
    }
}
