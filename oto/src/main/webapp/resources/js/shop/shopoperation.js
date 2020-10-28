$(function(){
    var shopId = getQueryString('shopId');
    var isEdit = shopId ? true : false;

    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    // 注册店铺
    var registerShopUrl = '/o2o/shopadmin/registershop';

    var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
    var editShopUrl = '/o2o/shopadmin/modifyshop';

    if(!isEdit){
        getShopInitInfo();
    }else{
        getShopInfo(shopId);
    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl,function(data){
            if(data.success){
                var shop =data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory='<option data-id="'
                    + shop.shopCategory.shopCategoryId+'"selected>'
                    + shop.shopCategory.shopCategoryName+'</option>';
                var tempAreaHtml = '';

                data.areaList.map(function (item,index) {
                    tempAreaHtml+='<option data-id="'+item.areaId+'">'
                        + item.areaName+'</option>';
                });

                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled','disabled');
                $('#area').html(tempAreaHtml);
                $("#area option[data-id='"+shop.area.areaId+"]").attr("selected","selected");

            }
        });
    }

    function getShopInitInfo() {
        $.getJSON(initUrl,function (data) {
            if(data.success){
                var tempHtml="";
                //获取区域信息
                var tempAreaHtml="";
                //后台获取的店铺分类列表，遍历
                data.shopCategoryList.map(function (item,index) {
                    tempHtml+='<option data-id="'+item.shopCategoryId+'">'+item.shopCategoryName+'</option>'
                });
                //区域信息
                data.areaList.map(function (item,index) {
                    tempAreaHtml+='<option data-id="'+item.areaId+'">'+item.areaName+'</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);

            }
        });

    }

    //点击提交后的响应
    $('#submit').click(function () {
        var shop ={};
        if(isEdit){
            shop.shopId=shopId;
        }
        //从控件中获取信息
        shop.shopName=$('#shop-name').val();
        shop.shopAddr=$('#shop-addr').val();
        shop.phone=$('#shop-phone').val();
        shop.shopDesc=$('#shop-desc').val();
        //获取列表中的数据
        shop.shopCategory={
            shopCategoryId:$('#shop-category').find('option').not(function(){
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId:$('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        var shopImg =$('#shop-img')[0].files[0];
        var formData = new FormData();
        formData.append('shopImg',shopImg);
        formData.append('shopStr',JSON.stringify(shop));
        var verifyCodeActual=$('#j_captcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码！');
            return;
        }
        formData.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            url:(isEdit?editShopUrl:registerShopUrl),
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if(data.success){
                    $.toast('提交成功');
                }else{
                    $.toast('提交失败，'+data.errMsg);
                }
                $('#captcha_img').click();
            }
        });
    });


})