package com.oto.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oto.dto.ImageHolder;
import com.oto.dto.ShopExecution;
import com.oto.entity.Area;
import com.oto.entity.PersonInfo;
import com.oto.entity.Shop;
import com.oto.entity.ShopCategory;
import com.oto.enums.ShopStateEnum;
import com.oto.exceptions.ShopOperationException;
import com.oto.service.AreaService;
import com.oto.service.ShopCategoryService;
import com.oto.service.ShopService;
import com.oto.util.CodeUtil;
import com.oto.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopInitInfo(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        // 1.接受并转化相应的参数
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            // 接受前端的信息并转换为shop实体类
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        //2.注册店铺
        if(shop != null){
//            PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
            PersonInfo owner = new PersonInfo();
            //Session TODO
            owner.setUserId(1L);


            shop.setOwner(owner);
            ShopExecution se;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                se = shopService.addShop(shop, imageHolder);
                if(se.getState() == ShopStateEnum.CHECK.getState()){
                    modelMap.put("success",true);
                    // 该用户可操作的店铺列表
                    @SuppressWarnings("unchecked")
                    List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
                    if(shopList == null || shopList.size() == 0){
                        shopList = new ArrayList<Shop>();
                    }
                    shopList.add(shop);
                    request.getSession().setAttribute("shopList", shopList);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }

            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
    }

    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if(shopId > -1){
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 验证码
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        // 1.接受并转化相应的参数
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            // 接受前端的信息并转换为shop实体类
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }
        //2.修改店铺
        if(shop != null && shop.getShopId() != null){
            PersonInfo owner = new PersonInfo();
            //Session TODO
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExecution se;
            try {
                if(shopImg == null){
                    se = shopService.modifyShop(shop, null);
                }else{
                    ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                    se = shopService.modifyShop(shop, imageHolder);
                }
                 if(se.getState() == ShopStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
    }

    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>getShopList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        //获取用户信息
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("test");
        request.getSession().setAttribute("user",user);
        user =(PersonInfo)request.getSession().getAttribute("user");

        try{
            //获取用户店铺列表
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition,0, 100);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("user",user);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        //管理session相关的操作
        Map<String,Object> modelMap = new HashMap<String,Object>();
        //获取店铺id
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId<=0){
            //如果前端没有传id的话就从seesion中获取
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj==null){
                //重定向 没登录就直接到/shoplist店铺列表中
                modelMap.put("redirect",true);
                modelMap.put("url","/o2o/shopadmin/shoplist");
            }else {
                Shop currentShop= (Shop)currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }
}
