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
<script src="<%=request.getContextPath() %>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(document).ready(function(){
 
});
</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">配置值</b>
</div>

  
<!--list -->
<div class="mainList">
<table class="listTable">
  <tr>
	<th>配置名称</th>
	<th>值</th>
  </tr>
  <s:iterator value="map">
  <tr>  
  	<td> <s:property value="key"/>  </td>
	<td> <s:property value="value"/>  </td>
  </tr>
  </s:iterator>
</table>
</div>
<!--end list -->
<div class="Toolbar">
  <div class="toolBt">
 		<a href="<s:url action="load-parameters.action" namespace="/admin/appsetting"/>" ><span class="btn">重新加载配置</span></a>
  </div>
</div>
 
</body>
</html>

