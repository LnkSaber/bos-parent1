<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>04-动态添加选择卡</title>
	<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
	<script src="../js/highcharts/highcharts.js"></script>
    <script src="../js/highcharts/modules/exporting.js"></script>
</head>
    <body>
        <div id="container" style=" boder:1px solid red;min-width:400px;height:400px"></div>
   		<script type="text/javascript">
   		$('#container').highcharts({
   	        title: {
   	            text: '各浏览器份额'
   	        },
   	        series: [{
   	            type: 'pie',
   	            name: '浏览器占比',
   	            data: [
   	                ['Firefox',  1 ],
   	                ['IE',       1 ],
   	                ['Safari',   1 ],
   	                ['Opera',    1 ],
   	                ['Others',   1 ]
   	            ]
   	        }]
   	    });
   		
   		</script>
    </body>
</html>