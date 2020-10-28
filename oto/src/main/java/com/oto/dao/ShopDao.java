package com.oto.dao;

import com.oto.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    /**
     * 新增店铺
     * @param shop
     * @return 1:成功  -1:失败
     */
    int insertShop(Shop shop);


    /**
     * 更新店铺信息
     */
    int updateShop(Shop shop);

    /**
     * 通过owner id 查询店铺
     */
    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺，可输入的条件有，店铺名(模糊)查询，店铺状态，店铺类别，区域id，owner
     * @param shopCondition
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
                             @Param("rowIndex")int rowIndex,
                             @Param("pageSize")int pageSize);

    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition")Shop shopCondition);
}
