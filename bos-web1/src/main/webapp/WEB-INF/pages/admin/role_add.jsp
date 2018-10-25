<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="css/default.css">
<script type="text/javascript"
	src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="js/easyui/ext/jquery.cookie.js"></script>
<script
	src="js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>	
<script type="text/javascript">
	$(function(){
		// 授权树初始化
		var setting = {
			data : {
				key : {
					title : "t"
				},
				simpleData : {
					enable : true
				}
			},
			check : {
                    enable : true
			}
		};
		
		$.ajax({
                url : 'functionAction_listajax.action',
			type : 'get',
                dataType : 'json',
			success : function(data) {
                    $.fn.zTree.init($("#functionTree"), setting, data);
			},
			error : function(msg) {
				alert('树加载异常!');
			}
		});
		
		
		
		// 点击保存
		$('#save').click(function(){
                var v = $("#roleForm").form("validate");
                if(v){
                    //根据tree上选中的节点对象
                    var treeObj = $.fn.zTree.getZTreeObj("functionTree");
                    var nodes = treeObj.getCheckedNodes(true);//在提交表单之前将选中的checkbox收集
                    var array = new Array();
                    for(var i=0;i<nodes.length;i++){
                        array.push(nodes[i].id);
                    }
                    var functionIds = array.join(",");
                    $("input[name=functionIds]").val(functionIds);
                    $("#roleForm").submit();

                }
		});
	});
</script>	
</head>
<body class="easyui-layout">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
    <form id="roleForm" method="get" action="roleAction_add.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
                <td width="200">关键字</td>
						<td>
                    <input type="hidden"  name="functionIds">
							<input type="text" name="id" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>名称</td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<textarea name="description" rows="4" cols="60"></textarea>
						</td>
					</tr>
					<tr>
						<td>授权</td>
						<td>
							<ul id="functionTree" class="ztree"></ul>
						</td>
					</tr>
					</table>
			</form>
		</div>
</body>
</html>