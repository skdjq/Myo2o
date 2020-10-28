package com.o2o.dao;

import com.o2o.BaseTest;
import com.oto.dao.ProductCategoryDao;
import com.oto.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void queryProductCategoryList() {
        long shopId=1;
        List<ProductCategory> productCategoryList =
                productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("该店铺自定义类别数为："+productCategoryList.size());
    }

}
