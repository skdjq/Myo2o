package com.oto.service.impl;

import com.oto.dao.ShopDao;
import com.oto.dto.ImageHolder;
import com.oto.dto.ShopExecution;
import com.oto.entity.Shop;
import com.oto.enums.ShopStateEnum;
import com.oto.exceptions.ShopOperationException;
import com.oto.service.ShopService;
import com.oto.util.ImageUtil;
import com.oto.util.PageCalculator;
import com.oto.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        if(shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum <= 0){
                throw new ShopOperationException("店铺创建失败");
            }else{
                if(thumbnail.getImage() != null){
                    //存储图片
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum <= 0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    @Override
    public Shop getByShopId(long shopId) {
        Shop shop = shopDao.queryByShopId(shopId);
        return shop;
    }

    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if(shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            try {
                // 1.判断是否需要处理图片
                if(thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if(tempShop.getShopImg() != null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShop(shop, thumbnail);
                }
                // 2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if(effectedNum <= 0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else{
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error:" + e.getMessage());
            }
        }
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        //得到从第几列开始
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,
                pageSize);
        //获取总数
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList!=null){
            se.setShopList(shopList);
            se.setCount(count);
        }else{
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        // 获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }
}
