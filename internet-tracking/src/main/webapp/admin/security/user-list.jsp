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
  <span class="hidsearch"><a href="javascript:void(0)" onclick="return chg('searchList');" id="ImgArrow"><img src="<%=request.getContextPath() %>/static/images/icon_down.gif" id ="imgButton" />展开搜索</a></span>
  <b class="bbig">用户列表</b>
</div>
<s:form  id="searchForm" action="user-list" namespace="/admin/security">
<div class="searchBar" id="searchList" style="display:none;">
  <table class="searchTable">
  <tr>
    <th width="10%">账号：</th>
    <td width="20%"><input name="username" type="text" size="25" value="${username }"/></td>
    <td><button class="btn" type="submit">确定搜索</button></td>
  </tr>

  </table>
</div>
</s:form> 
 
<!--list -->
<div class="mainList">
<table class="listTable">
  <tr>
	<th>帐号</th>
	<th>是否未失效</th>
	<th>是否未锁定</th>
	<th>是否未过期</th>
	<th>是否启用</th>
    <th>操作</th>
  </tr>
  <s:iterator value="pager.items">
  <tr>  
  	<td> <s:property value="username"/> </td>
	<td> <s:property value="accountNonExpired?'是':'否'"/> </td>
	<td> <s:property value="accountNonLocked?'是':'否'"/> </td>
	<td> <s:property value="credentialsNonExpired?'是':'否'"/> </td>
	<td> <s:property value="enabled?'是':'否'"/> </td>
    <td> 
    	<a href="<s:url action="user-role-list" namespace="/admin/security"/>?userId=<s:property value="id"/>" >分配角色</a>  
    </td>
  </tr>
  </s:iterator>
</table>
</div>
<!--end list -->
<div class="Toolbar">
  <div class="toolBt">
 		<a href="<s:url action="entry!syncUser" namespace="/admin"/>" title="同步用户"><span class="btn">同步用户</span></a>
  </div>
  <z:pagination></z:pagination>
</div>
 
</body>
</html>

