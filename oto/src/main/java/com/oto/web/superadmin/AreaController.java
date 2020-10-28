package com.oto.web.superadmin;

import com.oto.entity.Area;
import com.oto.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
    Logger logger = getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
//    java对象转为json格式的数据
    @ResponseBody
    private Map<String, Object> listArea(){
        logger.info("==start==");
        long startTime = System.currentTimeMillis();
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Area> list = new ArrayList<Area>();
        try{
            list = areaService.getAreaList();
            modelMap.put("rows", list);
            modelMap.put("total", list.size());
        }catch(Exception e){
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]", endTime - startTime);
        logger.info("==end==");
        return modelMap;
    }
}
