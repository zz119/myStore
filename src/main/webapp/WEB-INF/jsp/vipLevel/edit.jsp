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
    <form class="form form-horizontal" id="form-vipLevel-edit" action = "<%=request.getContextPath()%>/server/vip/vipLevelEdit" type = "post">
        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">* </span>等级名称：</label>
            <div class="formControls col-7">
                <input type = "hidden" value = "${vipLevel.id}" name = "id"/>
                <input type = "hidden" value = "${vipLevel.storeId}" name = "storeId"/>
                <input value = "${vipLevel.name}" name = "name" id="name" class="input-text radius" type="text"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">* </span>优惠折扣：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text radius" value="${vipLevel.rebate}"  id="rebate" name = "rebate"><br/>
                <div class="PS">（例如：8.5折填写85）</div>
            </div>
            <div class="col-2"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">是否积分：</label>
            <div class="formControls col-7">
                <div class="radio-box">
                    <input type="radio" id="level_using-1" name="integral" value = "1" <c:if test="${vipLevel.integral eq 1}">checked</c:if>>
                    <label for="level_using-1">是</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="level_using-2" name="integral" value = "0" <c:if test="${vipLevel.integral eq 0}">checked</c:if>>
                    <label for="level_using-2">否</label>
                </div>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <div class="col-10 col-offset-5 mt-20">
                <input class="btn btn-primary radius" type="button" id="vipLevelEditBtn" value="&nbsp;&nbsp;&nbsp;&nbsp;确认&nbsp;&nbsp;&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="${ctxResource}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxResource}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctxResource}/js/H-ui.js"></script>
<script type="text/javascript" src="${ctxResource}/js/H-ui.admin.js"></script>
<script type="text/javascript" src="${ctxResource}/js/myself.js"></script>
<script type="text/javascript" src="${ctxResource}/js/Validform_v5.3.2_min.js"></script>
<script>
    $(function () {
        var  validtor = $("#form-vipLevel-edit").Validform({
            tiptype:4,
            showAllError:true,
            ajaxPost:true,
            ignoreHidden:true, //可选项 true | false 默认为false，当为true时对:hidden的表单元素将不做验证;
            tipSweep:true,//可选项 true | false 默认为false，只在表单提交时触发检测，blur事件将不会触发检测
            btnSubmit:"#vipLevelEditBtn",
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
                ele:"#name",
                datatype:"*",
                nullmsg :"等級名称必填项"
            },
            {
                ele:"#rebate",
                datatype:"n",
                nullmsg :"优惠折扣必填"
            }
        ]);
    });
</script>
</body>
</html>