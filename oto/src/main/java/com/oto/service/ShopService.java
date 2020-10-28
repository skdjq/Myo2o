package com.oto.service;

import com.oto.dto.ImageHolder;
import com.oto.dto.ShopExecution;
import com.oto.entity.Shop;
import com.oto.exceptions.ShopOperationException;

public interface ShopService {
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 查询指定店铺信息
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息(从店家角度)
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 根据shopCondition分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition,int pageIndex, int pageSize);
}
