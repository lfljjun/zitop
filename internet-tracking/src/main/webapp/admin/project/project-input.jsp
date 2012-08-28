<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>项目</title>
<link href="<%=request.getContextPath() %>/static/css/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static/js/jquery.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/css/validator.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidator.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidatorRegex.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(document).ready(function(){
 	$.formValidator.initConfig({formid:"form1",onerror:function(msg){alert(msg)},onsuccess:function(){return true;}});
 	$("#name").formValidator({onshow:"",onfocus:"名称不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"名称不能为空"});
 	$("#intro").formValidator({onshow:"",onfocus:"少于1000字"}).inputValidator({max:1000,onerror:"简介少于1000字"});
});
</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">项目</b>
</div>
<s:form id="form1" action="%{actionName+'!'+nextMethod}" namespace="/admin/project" method="post">
<s:hidden name="nextMethod" />
<s:hidden name="requestId" />
<s:hidden name="id" />
<!--list -->
<div class="mainAdd">
<table class="addTable">
  <tr>
    <th>项目名称：</th> 
	<td> <s:textfield name="name" id="name" size="30"></s:textfield><span id="nameTip"></span> </td>
  </tr>
  <tr>
    <th>简介：</th> 
	<td> <s:textarea name="intro" id="intro" ></s:textarea><span id="introTip"></span> </td>
  </tr>
</table>
</div>
<!--end list -->
<div class="addToolbar">
    <p><button class="btn" type="submit"><b>确定提交</b></button>
    <button class="btn" type="button" onclick="location.href='<s:url namespace="/admin/project" action="project-show" />?projectId=${id}'">取消</button></p>
</div>
 
 </s:form>
</body>
</html>

