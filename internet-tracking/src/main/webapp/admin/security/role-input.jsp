<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>角色管理</title>
<link href="<%=request.getContextPath() %>/static/css/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static/js/jquery.js" type="text/javascript"></script>
<link href="<%=request.getContextPath() %>/static/css/validator.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="<%=request.getContextPath() %>/static/js/formValidator.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath() %>/static/js/formValidatorRegex.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(document).ready(function(){
 	$.formValidator.initConfig({formid:"form1",onerror:function(msg){alert(msg)},onsuccess:function(){return true;}});
 	$("#code").formValidator({onshow:"",onfocus:"角色代码不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"角色代码不能为空"});
 	$("#name").formValidator({onshow:"",onfocus:"名称不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"名称不能为空"});
});
</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">角色维护</b>
</div>
<s:form id="form1" action="%{actionName+'!'+nextMethod}" namespace="/admin/security">
<s:hidden name="nextMethod" />
<s:hidden name="requestId" />
<s:hidden name="id" />
<!--list -->
<div class="mainAdd">
<table class="addTable">
  <tr>
    <th>角色代码：</th> 
	<td> <s:textfield name="code" id="code" size="30"></s:textfield><span id="codeTip"></span> </td>
  </tr>
  <tr>
    <th>角色名称：</th> 
	<td> <s:textfield name="name" id="name" size="30"></s:textfield><span id="nameTip"></span> </td>
  </tr>
  <!-- <tr>
    <th>是否预设：</th> 
	<td> <s:select list="#{'false':'否','true':'是'}" name="preinstall"></s:select>  </td>
  </tr>
  <tr>
    <th>是否显示：</th> 
	<td> <s:select list="#{'true':'是','false':'否'}" name="showed"></s:select>  </td>
  </tr> -->
  <tr>
    <th>是否启用：</th> 
	<td> <s:select list="#{'true':'是','false':'否'}" name="enabled"></s:select>  </td>
  </tr>
  <tr>
    <th>描述：</th> 
	<td> <s:textarea name="description" id="description" ></s:textarea><span id="descriptionTip"></span> </td>
  </tr>
</table>
</div>
<!--end list -->
<div class="addToolbar">
    <p><button class="btn" type="submit"><b>确定提交</b></button>
    <button class="btn" type="button" onclick="location.href='<s:url action="role-list" namespace="/admin/security"/>?restore_params=true'">取消</button></p>
</div>
 
 </s:form>
</body>
</html>

