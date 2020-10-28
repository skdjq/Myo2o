package com.oto.service;

import com.oto.dto.ImageHolder;
import com.oto.dto.ProductExecution;
import com.oto.entity.Product;
import com.oto.exceptions.ProductOperationException;

import java.util.List;

public interface ProductService {
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;

    /**
     * 通过商品id查询唯一的商品信息
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 修改商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgs
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail, List<ImageHolder> productImgs) throws ProductOperationException;

    /**
     * 查询商品列表并分页，可输入的条件有：商品名(模糊),商品状态，店铺id，商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

}
