<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2018/8/30
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <%--${ pageContext.request.contextPath }--%>
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/ztree/jquery.ztree.all-3.5.js"></script>
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../js/ztree/zTreeStyle.css">

</head>
<body class="easyui-layout">
    <div style="height: 100px" data-options="region:'north'">北</div>
    <div title="系统菜单" style="width: 200px" data-options="region:'west'">
        <!--fit:true---自适应（填充父容器）-->
        <div class="easyui-accordion" data-options="fit:true">
            <div title="面板一" data-options="iconCls:'icon-cut'">
                <a class="easyui-linkbutton" id="but1">添加一个选项卡</a>
                    <script type="text/javascript">
                        $(function () {
                            //页面加载完毕后，为上面的选择卡添加一个事件
                            $("#but1").click(function () {
                                //判断选项卡"系统管理"是否存在
                                var e=$("#mytabs").tabs("exists","系统管理");
                                if (e){
                                    //已经存在，选中选择卡
                                    $("#mytabs").tabs("select","系统管理");
                                }
                                else {
                                    //调用tabs对象的add方法动态添加一个选择卡
                                    $("#mytabs").tabs("add",{
                                        title:"系统管理",
                                        closable:true,
                                        content:"<iframe frameborder=\"0\" height=\"100%\" width=\"100%\" src=\"https://www.jd.com\"></iframe>"
                                    });
							}
                            });
                        });
                    </script>
            </div>
            <div title="面板二">
                <!-- 展示ztree效果：使用标准json数据构造ztree  -->
                <ul class="ztree" id="ztree1"></ul>
                <script type="text/javascript">
                    $(function(){
                        //页面加载完毕之后，执行这段代码， --- 动态创建ztree
                        var setting = {};
                        //构造节点数据
                        var zNodes = [
                            {name: "父节点1", children: [
                                    {name: "子节点1"},
                                    {name: "子节点2"}
                                ]},
                            {name: "父节点2", children: [
                                    {name: "子节点1"},
                                    {name: "子节点2"}
                                ]}
                        ];
                        //调用API初始化ztree
                        $.fn.zTree.init($("#ztree1"), setting, zNodes);
                    });
                </script>
            </div>
            <div title="面板三">
                <!-- 使用简单joson数据构造 -->
                <ul id="ztree2" class="ztree"></ul>
                <script type="text/javascript">
                    $(function(){
                        //页面加载完毕之后，执行这段代码， --- 动态创建ztree
                        var setting = {
                            data: {
                                simpleData: {
										enable: true//使用简单json 数据构造ztree
                                }
                            }
                        };
                        //构造节点数据
                        var zNodes = [
                            {"id":1, "pId":0, "name":"节点一"},
                            {"id":11, "pId":1, "name":"节点二"},
                            {"id":12, "pId":1, "name":"节点三"},
                            {"id":12, "pId":1, "name":"节点五"},
                            {"id":111, "pId":1, "name":"节点四"},
                            {"id":113, "pId":1, "name":"节点六"}
                        ];
                        $.fn.zTree.init($("#ztree2"), setting, zNodes);
                    });
                </script>

            </div>
            <div title="面板四">
                <!-- 发送ajax 请求获取简单json数据构造  -->
                <ul id="ztree3" class="ztree"></ul>
                <script type="text/javascript">
                    $(function(){
                        //页面加载完毕之后，执行这段代码， --- 动态创建ztree
                        var setting = {
                            data: {
                                simpleData: {
                                    enable: true//使用简单json 数据构造ztree
                                }
                            }
                        };
                        //发送ajax  请求 获取json数据
                        <%--//ajax ，post ，get ，load，getJSON,getScript${pageContext.request.contextPath}--%>
                        var url ="../json/menu.json";
                        $.get(url,{},
                            function(data){
                                //alert("Data Loaded: " + data[0].name);
                                //调用api初始化
                                $.fn.zTree.init($("#ztree3"), setting, data);
                            },"json");
                    });
                </script>
            </div>
            <div title="面板五">
                <!-- 发送ajax 请求获取简单json数据构造为ztree节点绑定点击事件  -->
                <ui class="ztree" id="ztree4"></ui>
                <script type="text/javascript">
                    $(function () {
                        //页面加载完毕之后，执行这段代码， --- 动态创建ztree
                        var setting ={
                            data:{
                                simpleData:{
                                    enable:true//使用简单json 数据构造ztree
                                }
                            },
								callback: {
									//为ztree节点绑定点击事件
									onClick: function(event, treeId, treeNode){
                                    //判断treeNode.page是否有page路径
                                    if(treeNode.page!=undefined){
                                        //判断选项卡是否已经存在
                                        var e=$("#mytabs").tabs("exists",treeNode.name);
                                        if (e){
                                            //已经存在，选中
                                            $("#mytabs").tabs("select",treeNode.name);
                                        }
                                        else {
                                            //调用tabs对象的add方法动态添加一个选择卡
                                            $("#mytabs").tabs("add",{
                                                title:treeNode.name,
                                                closable:true,
                                                content:'<iframe frameborder="0" height="100%" width="100%" src="\'+treeNode.page+\'"></iframe>'
                                            });
                                        }
                                    }

                                }
                            }
                        };
                        //发送ajax  请求 获取json数据
                        //ajax ，post ，get ，load，getJSON,getScript
                        var url ="../json/menu.json";
                        $.get(url,{},function (data) {
                            //调用api初始化
                            $.fn.zTree.init($("#ztree4"),setting,data);
                        },"json");

                    });
                </script>
            </div>
        </div>
    </div>
    <div  data-options="region:'center'">
        <div id="mytabs" class="easyui-tabs" data-options="fit:true" >
            <div title="选择卡一" data-options="iconCls:'icon-cut', closable:true">111</div>
            <div title="选择卡二" data-options="closable:true">111</div>
            <div title="选择卡一" data-options="closable:true">2</div>
            <div title="选择卡一" data-options="closable:true"> 311</div>
            <div title="选择卡一" data-options="closable:true">411</div>
        </div>

    </div>
    <div  style="width: 100px" data-options="region:'east'">东</div>
    <div style="height: 100px" data-options="region:'south'">南</div>
</body>
</html>
