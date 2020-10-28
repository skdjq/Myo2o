package com.oto.service.impl;

import com.oto.dao.ProductCategoryDao;
import com.oto.dao.ProductDao;
import com.oto.dto.ProductCategoryExecution;
import com.oto.entity.ProductCategory;
import com.oto.enums.ProductCategoryStateEnum;
import com.oto.exceptions.ProductCategoryOperationException;
import com.oto.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution betchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if(productCategoryList != null && productCategoryList.size() > 0){
            try{
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if(effectedNum <= 0){
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                }else{
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
            }
        }else{
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        //解除tb_product里的商品与该productcategoryId的关联
        try {
            int effectedNum =
                    productDao.updateProductCategoryToNull(productCategoryId);
            if(effectedNum<0){
                throw new RuntimeException("商品类别更新失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("deleteProductCategory error"+e.getMessage());
        }
        //删除该productCategory
        try{
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if(effectedNum <= 0){
                throw new ProductCategoryOperationException("商品类别删除失败");
            }else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new ProductCategoryOperationException("deleteproductcategory error" + e.getMessage());
        }
    }
}
