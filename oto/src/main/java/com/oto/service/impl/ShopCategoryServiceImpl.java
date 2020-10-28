package com.oto.service.impl;

import com.oto.dao.ShopCategoryDao;
import com.oto.entity.ShopCategory;
import com.oto.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
        return shopCategoryList;
    }
}
