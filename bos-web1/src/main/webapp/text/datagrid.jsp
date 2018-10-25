<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>04-动态添加选择卡</title>
	<script type="text/javascript" src="/Bos/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/Bos/js/easyui/jquery.easyui.min.js"></script>

	<link rel="stylesheet" type="text/css" href="/Bos/js/easyui/themes/default/easyui.css">
	
	<link rel="stylesheet" type="text/css" href="/Bos/js/easyui/themes/icon.css">
	
	
	<script type="text/javascript" src="/Bos/js/ztree/jquery.ztree.all-3.5.js"></script>

	<link rel="stylesheet" type="text/css" href="/Bos/js/ztree/zTreeStyle.css">
	
	<script src="/Bos/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	
</head>
<body>
	<!-- 方式一：将静态html渲染为datagrid样式 -->
	<table  class="easyui-datagrid" >
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>	
		<tbody>
			<tr>
				<td>001</td>
				<td>校长</td>
				<td>80</td>
			</tr>
			<tr>
				<td>007</td>
				<td>小明</td>
				<td>1</td>
			</tr>
		</tbody>
	</table>
	
	<!-- 方式二：发送ajax请求获取json数据创建datagrid -->
	<table  class="easyui-datagrid"   data-options="url:'/Bos/json/datagrid_data.json'" >
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>	
	</table>
	<!-- 方式三：使用easyui 提供的api创建datagrid-->
	<table id="mytable"></table>
	<script type="text/javascript">
		$(function(){
			//页面加载完毕之后，创建数据表格datagrid 
			$("#mytable").datagrid({ 
				//定义标题行所有列
				columns:[[ 
						{field:'id',title:'编号',width:150,align:'center',checkbox:true}, 
						{field:'name',title:'姓名',width:150,align:'center'}, 
						{field:'age',title:'年龄',width:150,align:'center'} 
					]],
					singleSelect:true,
					url:'/Bos/json/decidedzone_subarea.json',
					//工具栏 
					toolbar: [
							{ "text":"添加",iconCls:'icon-add',
								//为按钮绑定点击事件
								handler:function(){
									alert("add...");
								}
							},
							{ "text":"修改",iconCls:'icon-edit' 	 },
							{ "text":"查询",iconCls:'icon-search' 	 },
							{ "text":"删除",iconCls:'icon-remove' 	 }
						],
						pagination:true,
						pageList:[1,2,3,4,5]
					
					});
		});
	</script>
	
	
</body>
</html>