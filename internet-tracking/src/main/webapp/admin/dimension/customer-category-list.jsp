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
 	$(".part").click(function(){
 		var bu = $(this).attr("value");
 		alert(bu);
 	});
});
function ajaxDel(requestId,currentParentId)
{
	if(confirm("删除记录后，数据将不可恢复！\n确认继续删除？"))
	{
		$.ajax({
			type : "POST",
			url : '<s:url action="customer-category!checkSub.action" namespace="/admin/dimension"/>',
			data : "requestId="+requestId,
			dataType: "txt",
			success : function(txt) {
				if(txt=="true"){
					window.location.href="customer-category!delete.action?requestId="+requestId+"&currentParentId="+currentParentId;
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

</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">客户群列表</b>
</div>
 
<!--list -->
<div class="mainList">
<table class="listTable">
  <tr>
	<th>序号</th>
	<th>名称</th>
	<!-- <th>是否有子分类</th> -->
	<th>创建人</th>
    <th>操作</th>
  </tr>
  <s:iterator value="pager.items" status="st">
  <tr>  
  <td> ${st.count } </td>
  	<td>${name}</td>
	<td> ${creator } </td>
  <%-- 	<td><s:property value="%{hasChilds==true?'是':'否'}"/> </td> --%>
    <td> 
    	<s:if test="hasChilds==true"><a href="<s:url action="customer-category-list" namespace="/admin/dimension"/>?currentParentId=<s:property value="id"/>">管理子分类</a> </s:if>
    	<a href="<s:url action="customer-category!edit" namespace="/admin/dimension"/>?requestId=<s:property value="id"/>&currentParentId=${currentParentId}">修改</a>  
    	<a href="javascript:ajaxDel(${id},${currentParentId})">删除</a>
    </td>
  </tr>
  </s:iterator>
</table>
</div>
<!--end list -->
<div class="Toolbar">
  <div class="toolBt">
 		<a href="<s:url action="customer-category!input" namespace="/admin/dimension"/>?currentParentId=${currentParentId}" title="新建"><span class="btn">新建</span></a>
 		<s:if test="currentParentId!=null&&currentParentId!=0">
 		<a href="<s:url action="customer-category!back" namespace="/admin/dimension"/>?currentParentId=${currentParentId}" title="返回"><span class="btn">返回</span></a>
 		</s:if>
  </div>
  <z:pagination></z:pagination>
</div>
 
</body>
</html>

