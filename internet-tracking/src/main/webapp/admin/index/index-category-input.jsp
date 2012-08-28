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
  <b class="bbig">指标分类编辑</b>
</div>
<s:form id="form1" action="%{actionName+'!'+nextMethod}" namespace="/admin/index" method="post">
<s:hidden name="nextMethod" />
<s:hidden name="requestId" />
<s:hidden name="id" />
<!--list -->
<div class="mainAdd">
<table class="addTable">
   <tr>
    <th>项目：</th> 
    <td>
	<%-- <s:if test="nextMethod=='insert'">
	<s:select list="projects" listKey="id" listValue="name" name="projectId" value="project.id"></s:select>
	</s:if>
	<s:else><s:property value="project.name"/><input type="hidden" name="projectId" value="${project.id}"/></s:else> --%>
	<s:property value="thisProject.name"/><input type="hidden" name="projectId" value="${thisProject.id}"/></td>
  </tr><tr>
    <th>父分类：</th> 
	<td>
	<s:if test="parentId==0">
	无<s:hidden name="parentId" value="0"/>
	</s:if>
	<s:else>
	<s:property value="parentCategory.name"/><s:hidden name="parentId" value="%{parentCategory.id}"/>
	</s:else>
	<%--  <s:select list="categories" listKey="id" listValue="name" name="parentId" headerKey="0" headerValue="无"></s:select><span id="parentIdTip"></span> 
 --%>	 
	 </td>
  </tr>
  <tr>
    <th>分类名称：</th> 
	<td> <s:textfield name="name" id="name" size="30"></s:textfield><span id="nameTip"></span> </td>
  </tr>
  <s:if test="parentId!=0">
  <tr>
    <th>图表类型：</th> 
	<td>
		<s:select name="graphType" list="graphTypeToNameMap" />
		<span id="graphTypeTip"></span> </td>
  </tr>
  </s:if>
  <tr>
  	<th>是否有子分类：</th> 
  	<td><%-- <s:property value="childs.size()>0"/> --%>
     	<%-- <s:select list="#{'true':'是','false':'否'}" name="hasChilds" value="hasChilds"></s:select> --%>
  		<s:if test="childs.size()>0">是</s:if>
	  	<s:else>
	  	<s:if test="parentId==0">是<s:hidden name="hasChilds" value="true"/></s:if>
     	<s:else>否<s:hidden name="hasChilds" value="false"/></s:else>
	  	</s:else>
  	</td>
  </tr>
</table>
</div>
<!--end list -->
<div class="addToolbar">
    <p><button class="btn" type="submit"><b>确定提交</b></button>
    <button class="btn" type="button" onclick="location.href='<s:url action="index-category-list" namespace="/admin/index"/>?restore_params=true'">取消</button></p>
</div>
 
 </s:form>
</body>
</html>

