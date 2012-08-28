<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>用户维护</title>
<link href="<%=request.getContextPath() %>/static/css/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static/js/jquery.js" type="text/javascript"></script>
<link href="<%=request.getContextPath() %>/static/css/validator.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="<%=request.getContextPath() %>/static/js/formValidator.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath() %>/static/js/formValidatorRegex.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(document).ready(function(){
 	$.formValidator.initConfig({formid:"form1",onerror:function(msg){alert(msg)},onsuccess:function(){return true;}});
 	$("#username").formValidator({onshow:"",onfocus:"名称不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"名称长度不正确"});
});
</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">用户资料维护</b>
</div>
<s:form id="form1" action="%{actionName+'!'+nextMethod}" namespace="/admin/security">
<s:hidden name="nextMethod" />
<s:hidden name="requestId" />
<s:hidden name="id" />
<!--list -->
<div class="mainAdd">
<table class="addTable">
  <tr>
    <th>帐号：</th> 
	<td> <s:textfield name="username" id="username" size="30"></s:textfield><span id="usernameTip"></span> </td>
  </tr>
  <tr>
    <th>密码:</th> 
	<td> 
		<input name="repassword"/><span id="repassword">新建请输入密码，修改时不改变密码请留空</span> 
	 </td>
  </tr>
  <tr>
    <th>是否锁定：</th> 
	<td> <s:select list="#{'true':'是','false':'否'}" name="accountNonLocked"></s:select>  </td>
  </tr>
  <tr>
    <th>认证失效：</th> 
	<td> <s:select list="#{'true':'是','false':'否'}" name="credentialsNonExpired"></s:select>  </td>
  </tr>
  <tr>
    <th>是否启用：</th> 
	<td> <s:select list="#{'true':'是','false':'否'}" name="enabled"></s:select>  </td>
  </tr>
</table>
</div>
<!--end list -->
<div class="addToolbar">
    <p><button class="btn" type="submit"><b>确定提交</b></button>
    <button class="btn" type="button" onclick="location.href='<s:url action="user-list" namespace="/admin/security"/>?restore_params=true'">取消</button></p>
</div>
 
 </s:form>
</body>
</html>

