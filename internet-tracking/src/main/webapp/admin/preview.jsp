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
<link href="<%=request.getContextPath() %>/static-main/css/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static/js/jquery.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/css/validator.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidator.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidatorRegex.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/thickbox.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/leftMenu.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/index.js" type="text/javascript"></script>

</head>
<body>
<div class="mTitle">
  <b class="bbig">指标预览</b>
</div>
<!-- right -->
 <div class="mainAdd">
 <div class="tableBox">
<table class="tb_wb">
  <tr class="wb1">
    <th class="n1" rowspan="2">模块</th>
    <th class="n2" rowspan="2">分类</th>
    <th class="n3" rowspan="2">指标</th>
    <th class="n4" rowspan="2">呈现方式</th>
    <s:iterator value="colMap.keySet()" var="key1">
    <th colspan='<s:property value="colMap.get(#key1).size()"/>'>${key1 } </th>
    </s:iterator>
    </tr>
  <tr class="wb2">
     <s:iterator value="colMap.keySet()" var="key2" >
     <s:iterator value="colMap.get(#key2)" var="var2">
     <th>${var2.name}</th>
    </s:iterator>
    </s:iterator>
  </tr>
 <s:iterator value="rowMap.keySet()" var="key3" status="stat1">
 <s:set name="flag1" value="true"/>
 <s:iterator value="rowMap.get(#key3).keySet()" var="key3s" status="stat2">
 <s:set name="flag2" value="true"/>
 <s:iterator value="rowMap.get(#key3).get(#key3s)" var="var3" status="stat3" >
 <tr>
 <s:if test="flag1">
 <td rowspan='<s:property value="rowCount[#stat1.index]"/>'>${key3}</td>
 <s:set name="flag1" value="false"/>
 </s:if>
 <s:if test="flag2">
 <td rowspan='<s:property value="rowMap.get(#key3).get(#key3s).size()"/>'>${key3s.name}</td>
 </s:if>
 <td>${var3.name}</td>
 <s:if test="flag2">
 <td rowspan='<s:property value="rowMap.get(#key3).get(#key3s).size()"/>'>${graphTypeArray[key3s.graphType]}</td>
  <s:set name="flag2" value="false"/>
 </s:if>
 <s:iterator value="colMap.keySet()" var="key2" >
 <s:iterator value="colMap.get(#key2)" var="var2" >
 <td></td>
  </s:iterator>
  </s:iterator>
 </tr> 
 </s:iterator>
 </s:iterator>
 </s:iterator> 

</table>
	
	</div>
	</div>





</body>
</html>

