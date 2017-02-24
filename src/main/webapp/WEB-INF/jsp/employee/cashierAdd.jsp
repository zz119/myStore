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
    <div class="form form-horizontal" id="form-goods-add">
        <div class="row cl mb-30">
            <div class="col-8">
            	<div class="row cl">
		            <label class="form-label col-3">是否启用：</label>
		            <div class="formControls col-6">
		            	<div class="radio-box">
				          	<input type="radio" id="cashier_using-1" name="cashier_using" value = "1" checked>
				          	<label for="cashier_using-1">是</label>
				        </div>
				        <div class="radio-box">
				          	<input type="radio" id="cashier_using-2" name="cashier_using" value = "2">
				          	<label for="cashier_using-2">否</label>
				        </div>
		            </div>
		            <div class="col-3"></div>
		        </div>
                <div class="row cl">
                    <label class="form-label col-3"><span class="c-red">* </span>编号：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius" value="" id="cashier_No"/>
                    </div>
                    <div class="col-3"> </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-3"><span class="c-red">* </span>姓名：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius" value=""  id="cashier_name"/>
                    </div>
                    <div class="col-3"> </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-3"><span class="c-red">* </span>密码：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius" value=""  id="cashier_pinyin"/>
                    </div>
                    <div class="col-3"> </div>
                </div>
                <div class="row cl">
                	<label class="form-label col-3">电话：</label>
                	<div class="formControls col-6"><input type = "text" class="input-text radius" id="cashier_contacts"/></div>
                	<div class="col-3"> </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-3">角色：</label>
                    <div class="formControls col-6">
	            	<span class="select-box radius">
		                <select class="select" id = "cashier_role">
		                    <option value = "0">- 无 -</option>
		                </select>
	                </span>
                    </div>
                    <div class="col-3"> </div>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-6 cl">
                <label class="form-label col-3">配送费返点：</label>
                <div class="formControls col-6">
                    <input type = "text"  class="input-text radius mr-5" id="cashier_packingFeePoint"  style="width: 90%;"/><label>&nbsp;%</label>
                </div>
            </div>
            <div class="col-6 cl">
                <label class="form-label col-3">固定返利点：</label>
                <div class="formControls col-6">
                    <input type = "text"  class="input-text radius mr-5"  id="cashier_rebatePoint"  style="width: 90%;"/><label>&nbsp;%</label>
                </div>
            </div>
        </div>
        <div class="row cl">
        	<div class="col-8">
	            <label class="form-label col-3">地址：</label>
	            <div class="formControls col-9">
	                <textarea rows="2" maxlength="200" class="edit_txt textarea radius" id="cashier_addr"></textarea>
	            </div>
	        </div>
        </div>
        <div class="row cl">
            <div class="col-8">
                <label class="form-label col-3">备注：</label>
                <div class="formControls col-9">
                    <textarea rows="2" maxlength="200" class="edit_txt textarea radius" id="cashier_desc"></textarea>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-10 col-offset-5 mt-20">
                <input class="btn btn-primary radius" type="button" id="userAddBtn" value="&nbsp;&nbsp;&nbsp;&nbsp;确认&nbsp;&nbsp;&nbsp;&nbsp;">
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctxResource}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxResource}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctxResource}/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctxResource}/js/H-ui.js"></script>
<script type="text/javascript" src="${ctxResource}/js/H-ui.admin.js"></script>
<script type="text/javascript" src="${ctxResource}/js/myself.js"></script>
<script>
    $(function () {
        $.post("<%=request.getContextPath()%>/server/role/findAll", function(data){
            for(var n in data){
                $("#cashier_role").append("<option value = '"+data[n].id+"'>"+data[n].name+"</option>");
            }
        })
    })
</script>
</body>
</html>