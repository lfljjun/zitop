<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			url : '<s:url action="index-category!checkSub.action" namespace="/admin/index"/>',
			data : "requestId="+requestId,
			dataType: "txt",
			success : function(txt) {
				if(txt=="true"){
					window.location.href="index-category!delete.action?requestId="+requestId;
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
		imgButton.src = "<%=request.getContextPath()%>/static/images/icon_down.gif";
		imgButton.alt = "展开搜索";
	}else{
		oa.style.display = "block";
		imgButton.src = "<%=request.getContextPath()%>/static/images/icon_up.gif";
		imgButton.alt = "隐藏搜索";
	}
	return false;
}
</script>
</head>
<body>
<div class="mTitle">
  <span class="hidsearch"><a href="javascript:void(0)" onclick="return chg('searchList');" id="ImgArrow"><img src="<%=request.getContextPath() %>/static/images/icon_down.gif" id ="imgButton" />展开搜索</a></span>
  <b class="bbig">指标分类列表</b>
</div>
<s:form  id="searchForm" action="index-category-list" namespace="/admin/index">
<div class="searchBar" id="searchList" style="display:none;">
  <s:hidden name="parentId"></s:hidden>
  <table class="searchTable">
  <tr>
    <th width="10%">分类名称：</th>
    <td width="20%"><input name="name" type="text" size="25" /></td>
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
	<th>分类名称</th>
	<th>默认图表类型</th>
	<th>创建人</th>
    <th>操作</th>
  </tr>
  <s:iterator value="pager.items" status="st">
  <tr>
  <td> ${st.count } </td>
  	<td width="30%"> <s:property value="name"/> </td>
	<td width="10%">
		<s:if test="!hasChilds"> 
			<s:property value="graphTypeToNameMap[graphType]"/>
		</s:if>
	</td>
	<td> ${creator } </td>
    <td> 
    	<s:if test="hasChilds">
    	<s:a  action="index-category-list" namespace="/admin/index">
    		<s:param name="parentId" value="id"/>
    		<s:param name="parentParentId" value="parentId"/>
    		子分类管理
    	</s:a>  
    	</s:if>
    	<s:else>
    	<s:a  action="index-item-list" namespace="/admin/index">
    		<s:param name="categoryId" value="id"/>
    		指标管理
    	</s:a> 
    	</s:else>
    	<a href="<s:url action="index-category!edit" namespace="/admin/index"/>?requestId=<s:property value="id"/>">修改</a>  
    	<a href="javascript:ajaxDel(${id})">删除</a> 
    </td>
  </tr>
  </s:iterator>
</table>
</div>
<!--end list -->
<div class="Toolbar">
  <div class="toolBt">
  	<s:a action="index-category!input" namespace="/admin/index" title="新建"><s:param name="parentId" value="parentId" /><span class="btn">新建</span></s:a>
  	<s:if test="parentId!=0">
  	<s:a action="index-category-list" namespace="/admin/index" title="返回">
  		<s:param name="parentId" value="parentParentId" />
  		<span class="btn">返回</span>
  	</s:a>
  	</s:if>
  </div>
  <z:pagination></z:pagination>
</div>
 
</body>
</html>

