<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>Title Here</title>
<link href="<%=request.getContextPath() %>/static/css/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static/js/jquery.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/css/validator.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidator.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidatorRegex.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(document).ready(function(){
 	$.formValidator.initConfig({formid:"form1",onerror:function(msg){alert(msg)},onsuccess:function(){return true;}});
 	$("#name").formValidator({onshow:"",onfocus:"名称不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"名称不能为空"});
});
</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">期数管理</b>
</div>
<s:form id="form1" action="%{actionName+'!'+nextMethod}" namespace="/admin/dimension" method="post">
<s:hidden name="nextMethod" />
<s:hidden name="requestId" />
<s:hidden name="id" />
<!--list -->
<div class="mainAdd">
<table class="addTable">
  <tr>
    <th>项目：</th> 
	<td> 
		<%-- <s:if test="nextMethod == 'insert'">
			<s:select list="projects" listKey="id" listValue="name" name="projectId" value="project.id"></s:select><span id="projectIdTip"></span> 
		</s:if>
		<s:elseif test="nextMethod == 'update'">
			${project.name }
		</s:elseif> --%>
		<s:property value="thisProject.name"/><input type="hidden" name="projectId" value="${thisProject.id}"/>
	</td>
  </tr>
  <tr>
    <th>名称：</th> 
	<td> <s:textfield name="name" id="name" size="30"></s:textfield><span id="nameTip"></span> </td>
  </tr>
  <tr>
    <th>时间：</th> 
	<td> 
		<s:select name="statYear" list="#{'2008':'2008','2009':'2009','2010':'2010','2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015'}"/>
		年&nbsp;&nbsp;
		<s:select name="statMonth" list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10','11':'11','12':'12'}"/>
		月&nbsp;&nbsp;
	</td>
  </tr>
  <tr>
    <th>备注：</th> 
	<td> <s:textfield name="intro" id="intro" size="30"></s:textfield><span id="introTip"></span> </td>
  </tr>
</table>
</div>
<!--end list -->
<div class="addToolbar">
    <p><button class="btn" type="submit"><b>确定提交</b></button>
    <button class="btn" type="button" onclick="location.href='<s:url action="term-list" namespace="/admin/dimension"/>?restore_params=true'">取消</button></p>
</div>
 
 </s:form>
</body>
</html>

