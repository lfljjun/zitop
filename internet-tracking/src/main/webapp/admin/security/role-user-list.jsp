<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="z" uri="/zitop_common"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>title here</title>
<link href="<%=request.getContextPath() %>/static/css/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(document).ready(function(){
 
});
function confirmDel()
{
	if(confirm("删除记录后，数据将不可恢复！\n确认继续删除？"))
	{
		return true;
	}
	return false;
}
function chg(id_num){
    var oa = document.getElementById(id_num);
    var ob = document.getElementById("ImgArrow");
	 var imgButton = document.getElementById("imgButton");
	 if(oa.style.display == "block"){
         oa.style.display = "none";
		 $("#ImgArrow").html("<img src='<%=request.getContextPath() %>/static/images/icon_down.gif' id ='imgButton'  alt ='展开搜索'/>展开搜索");
 	}else{
         oa.style.display = "block";
		 $("#ImgArrow").html("<img src='<%=request.getContextPath() %>/static/images/icon_up.gif' id ='imgButton'  alt ='隐藏搜索'/>隐藏搜索");
 	}
    return false;
}
</script>
</head>
<body>
<div class="mTitle">
  <span class="hidsearch"><a href="javascript:void(0)" onclick="return chg('searchList');" id="ImgArrow"><img src="<%=request.getContextPath() %>/static/images/icon_up.gif" id ="imgButton" />展开搜索</a></span>
  <b class="bbig">角色&nbsp;&nbsp;<font color="blue"> [<s:property value="role.name"/>(<s:property value="role.code"/>)] </font>&nbsp;&nbsp;所有权限维护</b>
</div>
<s:form  id="searchForm" action="role-user-list" namespace="/admin/security">
<s:hidden name="roleId"></s:hidden>
<div class="searchBar" id="searchList" style="display:block;">
  <table class="searchTable">
  <tr>
    <th width="10%">用户帐号：</th>
    <td width="20%">
    <s:textfield name="username" size="25"></s:textfield>
    </td>
	<th width="10%">权限分配情况：</th>
    <td>
    <s:select list="#{'':'不限','true':'已分配','false':'未分配'}" name="allocated"></s:select> 
	</td>
  </tr>

  <tr>
    <th>&nbsp;</th>
    <td><button class="btn" type="submit">查询角色下用户</button></td>
  </tr>
  </table>
</div>
</s:form> 
 
<!--list -->
<div class="mainList">
<table class="listTable">
  <tr>
	<th>帐号</th>
	
	<th>状态</th>
    <th>操作</th>
  </tr>
  <s:iterator value="pager.items" var="s">
  <s:set var="allocatedStatus" value="roleUsers.contains(#s)"/>
  <tr>  
  	<td> <s:property value="username"/>  </td>
	<td>
		<s:if test="#allocatedStatus">
		<font color="blue" style="font-weight: bold;">已分配</font>
		</s:if>
		<s:else>
		未分配
		</s:else>
	  </td>
    <td> 
    	<s:if test="#allocatedStatus">
    		<a href="<s:url action="role-user-revoke!revoke" namespace="/admin/security"/>?roleId=<s:property value="roleId"/>&userId=<s:property value="id"/>" title="取消用户【<s:property value="username"/>】的角色【<s:property value="role.name"/>】"> 取消角色</a>
    	</s:if>
    	<s:else>
    		<a href="<s:url action="role-user-grant!grant" namespace="/admin/security"/>?roleId=<s:property value="roleId"/>&userId=<s:property value="id"/>" title="将角色【<s:property value="role.name"/>】分配给用户【<s:property value="username"/>】"> 分配角色 </a>
		</s:else>
    </td>
  </tr>
  </s:iterator>
</table>
</div>
<!--end list -->
<div class="Toolbar">
  <div class="toolBt">
  </div>
  <z:pagination></z:pagination>
</div>
 
</body>
</html>

