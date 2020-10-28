package com.o2o.service;

import com.o2o.BaseTest;
import com.oto.dto.ShopExecution;
import com.oto.entity.Shop;
import com.oto.entity.ShopCategory;
import com.oto.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

//    @Test
//    @Ignore
//    public void testAddShop() throws FileNotFoundException {
//        Shop shop = new Shop();
//        PersonInfo owner = new PersonInfo();
//        Area area = new Area();
//        ShopCategory shopCategory = new ShopCategory();
//        owner.setUserId(1L);
//        area.setAreaId(2);
//        shopCategory.setShopCategoryId(1L);
//        shop.setOwner(owner);
//        shop.setArea(area);
//        shop.setShopCategory(shopCategory);
//        shop.setShopName("测试的店铺3");
//        shop.setShopDesc("test3");
//        shop.setShopAddr("test3");
//        shop.setPhone("test3");
//        shop.setCreateTime(new Date());
//        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
//        shop.setAdvice("审核中");
//        File shopImg = new File("D:\\projectdev\\image\\favicon.png");
//        FileInputStream is = new FileInputStream(shopImg);
//        ShopExecution se = shopService.addShop(shop, is, shopImg.getName());
//        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
//    }

    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(2L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition,1,2);
        System.out.println("店铺列表数为"+se.getShopList().size());
        System.out.println("店铺总数为"+se.getCount());
    }
}