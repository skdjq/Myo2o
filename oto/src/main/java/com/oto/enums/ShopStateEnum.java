package com.oto.enums;

public enum ShopStateEnum {
    CHECK(0,"审核中"),
    OFFLINE(-1,"非法店铺"),
    SUCCESS(1,"操作成功"),
    PASS(2,"认证通过"),
    INNER_ERROR(-1001,"内部错误"),
    NULL_SHOPID(-1002,"ShopId为空"),
    NULL_SHOP(-1003,"shop信息为空");

    private int state;
    private String stateInfo;

    ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ShopStateEnum stateOf(int index){
        for(ShopStateEnum stateEnum : values()) {
            if(stateEnum.getState() == index){
                return stateEnum;
            }
        }
        return null;
    }
}
