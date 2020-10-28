package com.oto.enums;

public enum ProductStateEnum {
    SUCCESS(1, "操作成功"),
    PRODUCT_EMPTY(-2001, "请输入商品信息"),
    EDIT_ERROR(-2002, "商品编辑失败"),
    EMPTY(-2003, "商品为空"),
    PRODUCT_ID_EMPTY(-2004, "商品ID为空"),;

    private int state;
    private String stateInfo;

    /**
     * @Description:私有构造函数,禁止外部初始化改变定义的常量
     *
     * @param state
     * @param stateInfo
     */
    private ProductStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * @Description: 仅设置get方法,禁用set
     */
    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }


    public static ProductStateEnum stateOf(int state) {
        for (ProductStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }
}
