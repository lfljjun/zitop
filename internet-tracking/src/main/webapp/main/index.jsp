<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>移动互联网用户行为跟踪系统</title>
<link href="<%=request.getContextPath() %>/static-main/css/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static-main/js/jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/thickbox.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/leftMenu.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/cookie.js" type="text/javascript"></script>
<style>
a.category{text-decoration:none;}
</style>
</head>

<body>
<s:include value="./common/top.jsp" />
<div class="mainBox">
  <!-- left -->
  <div class="mainLeft">
    <div class="tabs">
	  <div class="tabOn"><a href="#">条件设定</a></div>
<!-- 	  <div class="tabOff"><a href="#">指标呈现</a></div> -->
<!--	  <div class="tabOff"><a href="#">&nbsp;&nbsp;期数</a></div>-->
<!--	  <div class="tabOff"><a href="#">查询历史</a></div>-->
	</div>
	<div class="tt">1.请选择条件</div>
	<!-- leftMenu -->
	<div class="menuLine" id="menuLine">
	<ul id="menu">
	<li class="item"><a href="javascript:void(0)" target="mainFrame" class="title" name="2">模块</a>
	  <ul id="opt_2" class="optiton">
		<s:iterator value="indexCategoryList">
			<li><a href="javascript:void(0)" class="category" param="${id }">${name }</a></li>
		</s:iterator>
	  </ul>
	</li>
	<li class="item"><a href="javascript:void(0)" class="title customer" name="3">客户群</a>
	</li>
	<li class="item"><a href="javascript:void(0)" class="title term" name="4">期数</a>
	</li>
	</ul>	
	</div>
	<!-- end leftMenu -->
		
  </div>
  <!-- left end -->
  
  <!-- right -->
  <div class="mainRight">
    <table class="tbm">
      <tr>
        <th class="t2"><b>2.选择搜索项</b></th>
        <th class="t3"><b>3.确认搜索项</b></th>
      </tr>
      <tr>
        <td class="s2">
		  <div class="optionTitle">待选择</div>
		  <div class="optionInfo" id="conditionSelect" >
			
		  </div>
		</td>
        <td class="s3">
		  <div class="optionTitle">你已经选择&nbsp;&nbsp;<a href="javascript:void(0)" id="cleanAll">全部清除</a></div>
		  <div class="optionInfo">
		    <h6>指标</h6>
			<p id="zhibiao"></p>
			<h6>客户群</h6>
			<p id="kehu"></p>
			<h6>期数</h6>
			<p id="qishu"></p>
		  </div>
		  
		</td>
      </tr>
    </table>
	<div class="buttonLine">
	  <a class="bigButton1" href="javascript:void(0)" id="showDiagram">图形展示结果</a>
	  <a class="bigButton1" href="javascript:void(0)" id="showTable">表格展示结果</a>
	</div>
  </div>
  <!-- right end -->

</div>
<s:include value="./common/footer.jsp" />
</body>
</html>
