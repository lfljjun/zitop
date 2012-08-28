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
            imgButton.src = "<%=request.getContextPath() %>/static/images/icon_down.gif";
			 imgButton.alt = "展开搜索";
    }else{
            oa.style.display = "block";
            imgButton.src = "<%=request.getContextPath() %>/static/images/icon_up.gif";
			 imgButton.alt = "隐藏搜索";
    }
    return false;
}
</script>
</head>
<body>
<div class="mTitle">
  <span class="hidsearch"><a href="javascript:void(0)" onclick="return chg('searchList');" id="ImgArrow"><img src="<%=request.getContextPath() %>/static/images/icon_down.gif" id ="imgButton" />展开搜索</a></span>
  <b class="bbig">数据展示</b>
</div>
<s:form  id="searchForm" action="show-list" namespace="admin/show">
<div class="searchBar" id="searchList" style="display:block;">
  <table class="searchTable">
  <tr>
    <th width="10%">xxx：</th>
    <td width="20%"><input name="" type="text" size="25" /></td>
	<th width="10%">xxx：</th>
    <td>
	</td>
  </tr>

  <tr>
    <th>&nbsp;</th>
    <td><button class="btn" type="submit">确定搜索</button></td>
  </tr>
  </table>
</div>
</s:form> 
 
<!--list -->
<div class="mainList">

<table class="listTable">
	<%--<tr>
		<th rowspan="2">模块</th>
		<th rowspan="2">分类</th>
		<th rowspan="2">指标</th>
		<th rowspan="2">总体</th>
		<th colspan="4">重点客户群分类</th>
	</tr>
	<tr>
		<th>商务人士</th>
		<th>时尚人群</th>
		<th>白领</th>
		<th>流动人群</th>
	</tr> --%>
	
	<tr>
		<th>指标</th>
		<s:iterator value="customerList" status="st">
			<th>${name }</th>
		</s:iterator>
	</tr>
	<s:iterator value="indexItemList" status="st">
		<tr>
			<td>${name }</td>
		</tr>
	</s:iterator>
</table>

</div>
<!--end list -->
 
</body>
</html>