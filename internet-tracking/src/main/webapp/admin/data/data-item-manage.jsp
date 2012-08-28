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
<script type="text/javascript"> 
$(document).ready(function(){
 	$.formValidator.initConfig({formid:"form1",onerror:function(msg){alert(msg)},onsuccess:function(){return true;}});
 	$("#name").formValidator({onshow:"",onfocus:"名称不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"名称不能为空"});
 	ajaxIndexValueInit();
});

function ggLiuflj(indexItemId,customerCategoryId,value)
{
	return $.ajax({
		type : "POST",
		url : '<s:url action="data-item!updateData" namespace="/admin/data"/>',
		data : "indexItemId="+indexItemId+"&customerCategoryId="+customerCategoryId+"&termId="+$('#termId').val()+"&dataValue="+value,
		dataType: "txt",
		async:false,
		success : function(txt) {
			if(txt!="true"){
				alert("更新失败，请检查数据的正确性，数据不能包含字符");
			}
			
		}
	});	
}
</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">数据管理</b>
</div>
<s:form id="form1" action="data-item!updateByTerm.action" namespace="/admin/data" method="post">
<s:hidden name="termId" id="termId"/>
<!--list -->
<div class="mainAdd">
<table class="addTable">
  <tr>
    <th>期数：</th> 
	<td>${thisTerm.name}</td>
  </tr>
</table>
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
 <s:set name="valueKey" value="'('+#var2.id+','+#var3.id+')'"/>
 <td><label style="width:100%;height:20px;display:block;" indexItemId="<s:property value="#var3.id" />" customerCategoryId="<s:property value="#var2.id" />"><s:property value="valueMap.get(#valueKey)"/></label></td>
  </s:iterator>
  </s:iterator>
 </tr> 
 </s:iterator>
 </s:iterator>
 </s:iterator> 

</table>
	
	</div>
	</div>


<!--end list -->
<div class="addToolbar">
    
    <button class="btn" type="button" onclick="location.href='<s:url action="term-list" namespace="/admin/dimension"/>?restore_params=true'">返回</button></p>
</div>
 
 </s:form>
</body>
</html>

