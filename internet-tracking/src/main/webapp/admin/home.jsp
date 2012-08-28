<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="zsec" uri="/WEB-INF/security.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="pragma" content="no-chach" />
<title>中国电信</title>
<link href="<%=request.getContextPath() %>/static/css/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static/js/Geometry.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static/js/jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static/js/leftMenu.js" type="text/javascript"></script>
<style type="text/css">
<!--
body{overflow-x:hidden; overflow-y:hidden; height:100%; margin:0; background-image: url(<%=request.getContextPath() %>/static/images/admin_left_bg2.gif); background-repeat:repeat-y;} 
-->
</style> 
<script type="text/javascript">
var changeWidth = 224;

function chg(id_num){
         var oa = document.getElementById(id_num);
         var ob = document.getElementById("ImgArrow");
		 var imgButton = document.getElementById("imgButton");
         if(oa.style.display == "block"){
                 oa.style.display = "none";
                 imgButton.src = "<%=request.getContextPath() %>/static/images/switch_right.gif";
				 imgButton.alt = "打开左侧导航栏";
				 changeWidth = 9;
         }else{
                 oa.style.display = "block";
                 imgButton.src = "<%=request.getContextPath() %>/static/images/switch_left.gif";
				 imgButton.alt = "隐藏左侧导航栏";
				 changeWidth = 224;
         }
		 resizeIframe();
         return false;
}

function resizeAll(){
	resizeIframe();

}

function resizeIframe(){
	document.getElementById("mainFrame").height=Geometry.getViewportHeight()-67;
	document.getElementById("mainFrame").width = Geometry.getViewportWidth()-changeWidth;
	document.getElementById("menuLine").style.height=Geometry.getViewportHeight()-133+"px";
}

</script>

</head>
<body scroll="no" onresize="resizeAll();">
<div class="topBg">
  <div class="topRight">
    <div class="logo"><img src="<%=request.getContextPath() %>/static/images/logo.gif" /></div>
    <div class="topInfo">
	  <div class="loginName">欢迎您： <b><sec:authentication property="principal.username" /></b>［<a href="<%=request.getContextPath() %>">系统首页</a>，<a href="<%=request.getContextPath() %>/j_spring_security_logout">退出</a>］</div>
	  <div class="topMenu">
	  <s:if test="projectList.size != 0">
	   <s:iterator value="projectList">
	   	<div <s:if test="id == #session.projectId">class="tabOn"</s:if><s:else>class="tabOff"</s:else> >
	    	<a href="<%=request.getContextPath() %>/admin/home.action?projectId=${id}">${name }</a>
	    </div>
	  </s:iterator>
	  </s:if>
	  </div>
	</div>
  </div>
</div>
<table class="mainTable" border="0" cellPadding="0" cellSpacing="0">
  <tr>
    <td class="left" id="leftNav" style="display:block;">
  <div class="leftTop">
  <zsec:authorize ifAnyGranted="BG_PROJECT">  
  	<s:a namespace="/admin/project" action="project" method="input" target="mainFrame">
  		<span class="bb1"><i class="icon_listOK">&nbsp;</i><b>新建项目</b></span>
  	</s:a>
  	<s:a namespace="/admin" action="preview" target="_blank">
  		<span class="bb2"><i class="icon_edit">&nbsp;</i><b>指标预览</b></span>
  	</s:a>
  </zsec:authorize>
  </div>
  <!--menu -->
  <div class="menuLine" id="menuLine">
  <ul id="menu">
    <li class="item"><a href="javascript:void(0)" target="mainFrame" class="title" name="1">数据管理</a>
	  <ul id="opt_1" class="optiton">
	  <zsec:authorize ifAnyGranted="BG_INDEX">  
	    <li><s:a namespace="/admin/index" action="index-category-list" target="mainFrame"><span class="icon_edit">&nbsp;</span>指标管理</s:a></li>
<!--	    <li><s:a namespace="/admin/index" action="index-item-list" target="mainFrame"><span class="icon_edit">&nbsp;</span>所有指标</s:a></li>-->
	  </zsec:authorize>
	  <zsec:authorize ifAnyGranted="BG_CUSTOMERCATEGORY">  
	    <li><s:a namespace="/admin/dimension" action="customer-category-list?currentParentId=0" target="mainFrame"><span class="icon_edit">&nbsp;</span>客户群</s:a></li>
	  </zsec:authorize>
<!--	    <li><s:a namespace="/admin/dimension" action="term-list" target="mainFrame"><span class="icon_edit">&nbsp;</span>期数管理</s:a></li>-->
	    <li><s:a namespace="/admin/data" action="data-item!uploadPage.action" target="mainFrame"><span class="icon_edit">&nbsp;</span>数据导入</s:a></li>
	    <li><s:a namespace="/admin/dimension" action="term-list.action?flag=true" target="mainFrame"><span class="icon_edit">&nbsp;</span>数据编辑</s:a></li>
	  </ul>
	</li>
	<zsec:authorize ifAnyGranted="BG_SECURITY">  
	<li class="item"><a href="javascript:void(0)" target="mainFrame" class="title" name="5">后台管理</a>
	  <ul id="opt_5" class="optiton">
		<li><a href="<s:url action="user-list" namespace="/admin/security" />" target="mainFrame"><span class="icon_edit">&nbsp;</span>用户管理</a></li>
		<li><a href="<s:url action="role-list" namespace="/admin/security" />" target="mainFrame"><span class="icon_edit">&nbsp;</span>角色管理</a></li>
		<li><a href="<s:url action="security-resource-list" namespace="/admin/security" />" target="mainFrame"><span class="icon_edit">&nbsp;</span>资源管理</a></li>
	  </ul>
	</li>
	</zsec:authorize>
  </ul>	
  </div>
  <!--end menu -->
	</td>
	<td class="center">
<div id="switchpic">
  <a href="javascript:void(0)" onClick="return chg('leftNav');" id="ImgArrow"><img src="<%=request.getContextPath() %>/static/images/switch_left.gif" id ="imgButton" alt="隐藏左侧导航栏" /></a>
</div>	
	</td>
    <td class="right">
	<iframe src="<s:url namespace="/admin/project" action="project-show" />?projectId=${projectId}" name="mainFrame" width="100%" height="100%" scrolling-y="yes" scrolling-x="no" id="mainFrame" border="0" frameborder="0" onload="resizeIframe();">
	</td>
  </tr>
</table>





</body>
</html>
