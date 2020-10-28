package com.o2o.dao;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import com.oto.dao.ShopDao;
import com.oto.entity.Area;
import com.oto.entity.PersonInfo;
import com.oto.entity.Shop;
import com.oto.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.o2o.BaseTest;

public class ShopDaoTest extends BaseTest{

    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore
    public void testInsertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void testqueryByShopId(){
        long shopId = 10;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getArea().getAreaId());
    }

    @Test
    public void testQueryShopListAndCount() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表的大小" + shopList.size());
        System.out.println("店铺的大小：" + count);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(3L);
        shopCondition.setShopCategory(sc);
        shopList = shopDao.queryShopList(shopCondition, 0, 2);
        count = shopDao.queryShopCount(shopCondition);
        System.out.println(shopList);
        System.out.println(count);
    }
}
