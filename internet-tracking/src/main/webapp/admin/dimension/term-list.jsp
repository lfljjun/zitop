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
function ajaxDel(requestId)
{
	if(confirm("删除记录后，数据将不可恢复！\n确认继续删除？"))
	{
		$.ajax({
			type : "POST",
			url : '<s:url action="term!checkSub.action" namespace="/admin/dimension"/>',
			data : "requestId="+requestId,
			dataType: "txt",
			success : function(txt) {
				if(txt=="true"){
					window.location.href="term!delete.action?requestId="+requestId;
				}
				if(txt=="false"){
					alert("有数据 ，不能删除");
				}
				
			}
		});	
	}
	
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
function confirmDel()
{
	if(confirm("删除记录后，数据将不可恢复！\n确认继续删除？"))
	{
		return true;
	}
	return false;
}
</script>
</head>
<body>
<div class="mTitle">
  <span class="hidsearch"><a href="javascript:void(0)" onclick="return chg('searchList');" id="ImgArrow"><img src="<%=request.getContextPath() %>/static/images/icon_down.gif" id ="imgButton" />展开搜索</a></span>
  <b class="bbig">期数列表</b>
</div>
<s:form  id="searchForm" action="term-list" namespace="/admin/dimension">
<div class="searchBar" id="searchList" style="display:none;">
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
  <tr>
	<th>序号</th>
	<th>名称</th>
	<th>所属项目</th>
	<th>调查时间</th>
	<th>创建人</th>
    <th>操作</th>
  </tr>
  <s:iterator value="pager.items" status="st">
  <tr>  
  	<td> ${st.count } </td>
	<td> ${name } </td>
	<td> ${project.name } </td>
	<td>
		<s:if test="statYear!=null && statMonth!=null">${statYear }年${statMonth }月</s:if>
	</td>
	<td> ${creator } </td>
    <td> 
    <s:if test="flag==true">
    	<a href="<s:url action="data-item!editByTermGrid" namespace="/admin/data"/>?termId=<s:property value="id"/>">管理数据</a>  
    	<a href="<s:url action="data-item!deleteByTerm" namespace="/admin/data"/>?termId=<s:property value="id"/>"  onclick="return confirmDel();">删除</a>
    </s:if>
    <s:else>
    	<a href="<s:url action="term!edit" namespace="/admin/dimension"/>?requestId=<s:property value="id"/>">修改</a>  
    	<a href="javascript:ajaxDel(${id})">删除</a>
    </s:else>
    </td>
  </tr>
  </s:iterator>
</table>
</div>
<!--end list -->
<div class="Toolbar">
  <div class="toolBt">
 		<a href="<s:url action="term!input" namespace="/admin/dimension"/>" title="新建"><span class="btn">新建</span></a>
  </div>
  <z:pagination></z:pagination>
</div>
 
</body>
</html>

