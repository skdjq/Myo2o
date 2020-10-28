package com.oto.service;

import com.oto.dto.ProductCategoryExecution;
import com.oto.entity.ProductCategory;
import com.oto.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 查询指定某个店铺下所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    ProductCategoryExecution betchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    /**
     * 将此类别下的商品里的类别id置为空，再删除掉该商品的类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException;
}
