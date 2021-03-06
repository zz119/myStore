<%@page language="java" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <script type="text/javascript" src="lib/PIE_IE678.js"></script>
    <![endif]-->
    <link href="${ctxResource}/css/H-ui.css" rel="stylesheet" type="text/css" />
    <link href="${ctxResource}/css/admin.css" rel="stylesheet" type="text/css" />
    <link href="${ctxResource}/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctxResource}/css/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />

    <title></title>
</head>
<body>
<div class="pd-20 minwidth">
    <form class="form form-horizontal" id="form-raw-add" action = "<%=request.getContextPath()%>/server/raw/add" type = "post">
        <div class="row cl mb-30">
            <div class="col-8">
            	<div class="row cl">
		            <label class="form-label col-3">是否启用：</label>
		            <div class="formControls col-6">
		            	<div class="radio-box">
				          	<input type="radio" id="using-1" name="status" value = "1" checked>
				          	<label for="using-1">是</label>
				        </div>
				        <div class="radio-box">
				          	<input type="radio" id="using-2" name="status" value = "0">
				          	<label for="using-2">否</label>
				        </div>
		            </div>
		            <div class="col-3"></div>
		        </div>
                <div class="row cl">
                    <label class="form-label col-3"><span class="c-red">* </span>条码：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius" value="" id="rawNo" name = "rawNo">
                    </div>
                    <div class="col-3">
                        <input type="button" class="btn btn-primary size-S radius" value="随机生成" id="randomNo"/>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-3"><span class="c-red">* </span>品名：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius" value=""  id="rawName" name = "name">
                    </div>
                    <div class="col-3"> </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-3"><span class="c-red">* </span>分类：</label>
                    <div class="formControls col-6">
                    	<div class="radius">
                            <input type="hidden" value name = "categoriesName" id="categoriesName"/>
                            <select id="categories" name = "categoriesId" class="select" style="height: 35px;">
                                <option value selected>- 请选择原材料分类 -</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-3"> </div>
                </div>
                <div class="row cl">
                	<label class="form-label col-3"><span class="c-red">* </span>售价：</label>
                	<div class="formControls col-6"><input type = "text" class="input-text radius text-r" name = "price" id="price" style="width: 90%;"/>&nbsp;<label>元</label></div>
                	<div class="col-3"> </div>
                </div>
                <div class="row cl">
                	<label class="form-label col-3"><span class="c-red">* </span>进价：</label>
                	<div class="formControls col-6"><input type = "text" class="input-text radius text-r" id = "bid" name = "bid" style="width: 90%;"/>&nbsp;<label>元</label></div>
                	<div class="col-3"> </div>
                </div>
            </div>
            <div class="col-4">
                <div class="formControls clearfix">
                </div>
                <div class="clearfix">
                	<div id="btnShowEditImages" class="defaultImage">
                    	<h1>编辑图片</h1>
	                	<img src="" id="imgPath"/>
                        <input type = "hidden" value="" name = "goodsRawImg" id = "goodsImg">
                	</div>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-6 cl">
                <label class="form-label col-3">库存：</label>
                <div class="formControls col-6">
                    <input type = "number" class="input-text radius text-r" value="0" id = "stock" name = "stock" style="width: 100%;" placeholder="请输入库存量（单位：个）"/>
                </div>
                <div class="col-3"> </div>
            </div>
            <div class="col-6 cl">
            <label class="form-label col-3">单位：</label>
            <div class="formControls col-6">
                <span class="select-box radius">
                    <input type="hidden" name = "mainUnitName" id="mainUnitName" value>
                    <select id="mainUnit" name = "mainUnitId" class="select">
                        <option value = "-1">- 请选择 -</option>
                    </select>
                </span>
            </div>
            <div class="col-3"> </div>
            </div>
        </div>
        <div class="row cl">
        	<div class="col-6 cl">
        		<label class="form-label col-3">拼音码：</label>
	            <div class="formControls col-6">
	                <input type="text" name="pinyin" class="input-text radius" placeholder="请输入拼音码"/>
	            </div>
	            <div class="col-3"></div>
        	</div>
        	<div class="col-6 cl">
        		<label class="form-label col-3">供货商：</label>
	            <div class="formControls col-6">
	            	<span class="select-box radius">
                        <input type="hidden" value name = "supplierName" id="supplierName">
		                <select class="select" id = "supplier" name = "supplierId">
		                    <option value = "0">- 请选择供货商 -</option>
		                </select>
	                </span>
	            </div>
	            <div class="col-3"> </div>
        	</div>
        </div>
        <div class="row cl">
            <div class="col-6">
                <label class="form-label col-3">生产日期：</label>
                <div class="formControls col-6">
                    <input type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d',readOnly:true,skin:'twoer'})" id="productionDate" name="productionDate" class="input-text Wdate radius" placeholder="请选择生产日期"/>
                </div>
                <div class="col-3"> </div>
            </div>
            <div class="col-6">
                <label class="form-label col-3">保质期：</label>
                <div class="formControls col-6">
                    <input type="number" class="input-text radius mr-6 text-r" id="ExpirationDate"  name = "shelfLife" min="0" value="0" style="width: 90%;"/>&nbsp;<label>天</label>
                </div>
                <div class="col-3"> </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-6">
                <label class="form-label col-3">库存上限：</label>
                <div class="formControls col-6">
                    <input type="number"  class="input-text radius text-r" id="stockUp"  name = "stockUp" min="0" style="width: 100%;"/>
                </div>
                <div class="col-3"> </div>
            </div>
            <div class="col-6">
                <label class="form-label col-3">库存下限：</label>
                <div class="formControls col-6">
                    <input type="number"  class="input-text radius text-r" id="stockDown"  name = "stockDown" min="0" style="width: 100%;"/>
                </div>
                <div class="col-3"> </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-10 col-offset-5 mt-20">
                <input class="btn btn-primary radius" type="submit" id="goodsRawAddBtn" value="&nbsp;&nbsp;&nbsp;&nbsp;确认&nbsp;&nbsp;&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="${ctxResource}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxResource}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctxResource}/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctxResource}/js/H-ui.js"></script>
<script type="text/javascript" src="${ctxResource}/js/H-ui.admin.js"></script>
<script type="text/javascript" src="${ctxResource}/js/myself.js"></script>
<script type="text/javascript" src="${ctxResource}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctxResource}/js/Validform_v5.3.2_min.js"></script>
<script>
$(function(){
    //随机生成编号
    $("#randomNo").on("click", function () {
        $.post("<%=request.getContextPath()%>/server/goods/newGoodsNo", function(data){
            if(data.result == 1){
                $("#rawNo").val(data.msg);
                $("#rawNo").removeClass("Validform_error");
                $("#rawNo").parent().find("span").removeClass("Validform_error").addClass("Validform_right").text("通过消息验证！").css("color", "black");
                $("#randomNo").css("display", "none");
                $("#help_tooltip").show();
            }
        });
    });


    var  validtor = $("#form-raw-add").Validform({
        tiptype:4,
        showAllError:true,
        ajaxPost: true,
        ignoreHidden:true, //可选项 true | false 默认为false，当为true时对:hidden的表单元素将不做验证;
        tipSweep:true,//可选项 true | false 默认为false，只在表单提交时触发检测，blur事件将不会触发检测
        btnSubmit:"#goodsRawAddBtn",
        callback:function (data) {
            if(data.result == 1){
                window.parent.table.fnDraw();
                layer.msg(data.msg, {time : 2000, icon : 6}, function () {
                    layer_close();
                });
            }else{
                layer.msg(data.msg, {time : 2000, icon : 5});
            }
        }
    });

    validtor.addRule([
        {
            ele:"#rawNo",
            datatype:"n",
            errormsg:"只能填写数字",
            nullmsg:"原材料条码必填"
        },
        {
            ele:"#rawName",
            datatype:"s",
            nullmsg:"原材料名称必填"
        },
        {
            ele:"#categories",
            datatype:"*",
            nullmsg:"请选择原材料分类"
        },
        {
            ele:"#price",
            datatype : /^\d+(?:\.\d{1,2})?$/,
            errormsg:"请填写正确的数值",
            nullmsg:"销售价必填"
        },
        {
            ele:"#bid",
            datatype:/^\d+(?:\.\d{1,2})?$/,
            errormsg:"请填写正确的数值",
            nullmsg:"进货价必填"
        }
    ]);

	//编辑图片
	$("#btnShowEditImages").click(function(){
        layer_show("商品图片", "<%=request.getContextPath()%>/server/goods/uploadImg", "1000", "500");
	});

    //分类
    $.post("<%=request.getContextPath()%>/server/categories/categoriesList", function(data){
        for(var n in data){
            $("#categories").append("<option value = "+data[n].id+">"+data[n].name+"</option>");
        }
        $("#categories").change(function () {
            var name = $("#categories").find("option:selected").text();
            $("#categoriesName").val(name);
        })
    });
    //供应商
    $.post("<%=request.getContextPath()%>/server/supplier/allSupplier", function(data){
        for(var n in data){
            $("#supplier").append("<option value = "+data[n].id+">"+data[n].name+"</option>");
        }
        $("#supplier").change(function () {
            var name = $("#supplier").find("option:selected").text();
            $("#supplierName").val(name);
        })
    });
    //单位
    $.post("<%=request.getContextPath()%>/server/goods/getAllGoodsUnit", function(data){
        for(var n in data){
            $("#mainUnit").append("<option value = "+data[n].id+">"+data[n].name+"</option>");
        }
        $("#mainUnit").change(function () {
            var name = $("#mainUnit").find("option:selected").text();
            $("#mainUnitName").val(name);
        })
    });
})
</script>
</body>
</html>