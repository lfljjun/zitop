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
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/demo/demo.css">
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
<link href="<%=request.getContextPath()%>/static/css/validator.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidator.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidatorRegex.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static-main/js/thickbox.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static-main/js/leftMenu.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static-main/js/index.js" type="text/javascript"></script>
<script type="text/javascript">
 	////
 	var rowjson = ${rowJson}, products = rowjson["productId"], categories = rowjson["indexCategory"];
 	rowjson = null;
 	function formatProduct(value){
 		var tmp = products[value];
 		if(tmp){
 			return tmp.name;
 		}
		return value;
	}
 	function formatCategory(value){
 		var tmp = categories[value];
 		if(tmp){
 			return tmp.name;
 		}
		return value;
	}
 	function mergeCells(merges, fieldx){
 		var key = null, merge=null;
		for(key in merges){
			merge = merges[key];
			if(merge.rowspan>0){
				if(!fieldx){
					fieldx = merge.field
				}
				$('#tt').datagrid('mergeCells',{
					index: merge.index,
					field: fieldx,
					rowspan: merge.rowspan
				});
			}
		}
 	}
$(document).ready(function(){
 	$.formValidator.initConfig({formid:"form1",onerror:function(msg){alert(msg)},onsuccess:function(){return true;}});
 	$("#name").formValidator({onshow:"",onfocus:"名称不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"名称不能为空"});
 	$('#tt').datagrid({
		onLoadSuccess:function(){
			mergeCells(products);
			mergeCells(categories);
			mergeCells(categories, "graphType");
		}
	});
});
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
	<table id="tt" idField="indexItem" style="width:900px;height:380px;" url="<s:url action="data-item!gridJson" namespace="/admin/data"><s:param name="termId" value="termId"/></s:url>">
	  <thead frozen="true"> 
	  <tr>
		<th field="productId" formatter="formatProduct">模块</th>
	    <th field="indexCategory" formatter="formatCategory">分类</th>
	    <th field="indexItem" width="120">指标</th>
	    <th field="graphType">呈现方式</th>
	  </tr>
	</thead>
	<thead>
	  <tr>
	    <s:iterator value="colMap.keySet()" var="key1" status="st">
	    	<th colspan='<s:property value="colMap.get(#key1).size()"/>'>${key1}</th>
	    </s:iterator>
	   </tr>
	   <tr>
	     <s:iterator value="colMap.keySet()" var="key2" >
	     <s:iterator value="colMap.get(#key2)" var="var2">
	     	<th field="custChild${var2.id}" width="100" align="center">${var2.name}</th>
	     </s:iterator>
	    </s:iterator>
	  </tr>
	 </thead>
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

