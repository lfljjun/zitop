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
<link href="<%=request.getContextPath()%>/static/css/validator.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/css/easyui/datagrid.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static/js/jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static/js/jquery.resizable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static/js/htmldatagrid.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidator.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidatorRegex.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/thickbox.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/leftMenu.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/index.js" type="text/javascript"></script>
<script type="text/javascript">
function ggLiuflj(indexItemId, customerCategoryId, value){
	return $.ajax({
		type : "POST",
		url : '<s:url action="data-item!updateData" namespace="/admin/data"/>',
		data : "indexItemId="+indexItemId+"&customerCategoryId="+customerCategoryId+"&termId="+$('#termId').val()+"&dataValue="+value,
		dataType: "txt",
		async:false,
		success : function(txt) {
			if(txt!="true"){
				$("#datagrid-mask-msg").css("left", $("#datagrid-view").width()/2).html("更新失败，请检查数据的正确性，只能是合法数据").show().fadeOut(3000);
				//alert("更新失败，请检查数据的正确性，只能是合法数据");
			}
		}
	});	
}
$(document).ready(function(){
	htmldatagrid();
	ajaxIndexValueInit();
});
</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">数据管理-${thisTerm.name}</b>
</div>
<s:hidden name="termId" id="termId"/>
<!-- right -->
 <div class="mainAdd" style="padding: 0px;">
 	<div id="datagrid-mask" style="display: block;" class="datagrid-mask"></div>
 	<div id="datagrid-mask-msg" style="display: block; left: 200px;" class="datagrid-mask-msg">加载中, 请稍后 ...</div>
	 <div id="datagrid-view" class="datagrid-view" style="height: 450px;">
		<!-- div left -->
		<div class="datagrid-view1">
			<div class="datagrid-header">
				<div class="datagrid-header-inner">
				<!-- 模块分类指标=标题显示 -->
				<table cellspacing="0" cellpadding="0" border="0" class="datagrid-htable" style="height: 55px;">
					<tbody>
						<tr class="datagrid-header-row">
							<td rowspan="1" field="rownumber"><div class="datagrid-header-rownumber"></div></td>
							<td field="productId" width="70"><div class="datagrid-cell"><span>模块</span></div></td>
						    <td field="indexCategory" width="100"><div class="datagrid-cell"><span>分类</span></div></td>
						    <td field="indexItem" width="150"><div class="datagrid-cell"><span>指标</span></div></td>
<!--						    <td field="graphType"><div class="datagrid-cell"><span>呈现方式</span></div></td>-->
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<!-- 模块分类指标列表 left value -->
			<div class="datagrid-body">
				<div class="datagrid-body-inner">
				<table cellspacing="0" cellpadding="0" border="0" class="datagrid-btable">
					<tbody>
					<s:set var="rownumber" value="0"></s:set>
					<s:iterator value="rowMap.keySet()" var="key3" status="stat1">
						<s:set name="productflag" value="true"/>
						<s:iterator value="rowMap.get(#key3).keySet()" var="key3s">
							<s:set name="indexCategoryFlag2" value="true"/>
							<s:iterator value="rowMap.get(#key3).get(#key3s)" var="var3">
							<s:set var="rownumber" value="#rownumber+1"></s:set>
							<tr class="datagrid-row" id="datagrid-row-r1-1-<s:property value="#rownumber"/>" datagrid-row-index="<s:property value="#rownumber"/>" style="height: 25px;">
								<td class="datagrid-td-rownumber" field="rownumber"><div class="datagrid-cell-rownumber"><s:property value="#rownumber"/></div></td>
								<s:if test="productflag">
									<td field="productId" rowspan='<s:property value="rowCount[#stat1.index]"/>' title="${key3}"><div class="datagrid-cell">${key3}</div></td>
									<s:set name="productflag" value="false"/>
								</s:if>
								<s:if test="indexCategoryFlag2">
									<td field="indexCategory" rowspan='<s:property value="rowMap.get(#key3).get(#key3s).size()"/>' title="${key3s.name}"><div class="datagrid-cell">${key3s.name}</div></td>
								</s:if>
								<td field="indexItem" title="${var3.name}"><div class="datagrid-cell">${var3.name}</div></td>
								<s:if test="indexCategoryFlag2">
<!--									<td field="graphType" rowspan='<s:property value="rowMap.get(#key3).get(#key3s).size()"/>'><div class="datagrid-cell">${graphTypeArray[key3s.graphType]}</div></td>-->
									<s:set name="indexCategoryFlag2" value="false"/>
								</s:if>
							</tr>
							</s:iterator>
						</s:iterator>
					</s:iterator>
					</tbody>
				</table>
				</div>
			</div>
			<div class="datagrid-footer"><div class="datagrid-footer-inner" style="display: none; "></div></div>
		</div>
		<!-- div right -->
		<div class="datagrid-view2">
			<div class="datagrid-header">
				<div class="datagrid-header-inner">
				<!-- 客户群标题列表显示 -->
				<table cellspacing="0" cellpadding="0" border="0" class="datagrid-htable" style="height: 55px;">
					<tbody>
						<tr class="datagrid-header-row">
							<s:iterator value="colMap.keySet()" var="key1" status="st">
						    	<td colspan='<s:property value="colMap.get(#key1).size()"/>' align="center"><div class="datagrid-cell"><span>${key1}</span></div></td>
						    </s:iterator>
						</tr>
						<tr class="datagrid-header-row">
							<s:iterator value="colMap.keySet()" var="key2" >
						     <s:iterator value="colMap.get(#key2)" var="var2">
						     	<td field="custChild${var2.id}" append="20" align="center"><div class="datagrid-cell"><span>${var2.name}</span></div></td>
						     </s:iterator>
						    </s:iterator>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<!-- 指标值显示 right value -->
			<div class="datagrid-body" style="display: none;">
				<table cellspacing="0" cellpadding="0" border="0" class="datagrid-btable">
					<tbody>
					<s:set var="rownumber" value="0"></s:set>
					<s:iterator value="rowMap.keySet()" var="key3" status="stat1">
						<s:iterator value="rowMap.get(#key3).keySet()" var="key3s">
							<s:iterator value="rowMap.get(#key3).get(#key3s)" var="var3">
							<s:set var="rownumber" value="#rownumber+1"></s:set>
							<tr class="datagrid-row" id="datagrid-row-r1-2-<s:property value="#rownumber"/>" datagrid-row-index="<s:property value="#rownumber"/>" style="height: 25px;">
								<s:iterator value="colMap.keySet()" var="key2" >
									<s:iterator value="colMap.get(#key2)" var="var2" >
										<s:set name="valueKey" value="'('+#var2.id+','+#var3.id+')'"/>
										<td field="custChild${var2.id}">
											<div class="datagrid-cell">
											<label style="width: 100%; height: 20px; display: block;" indexItemId="<s:property value="#var3.id" />" customerCategoryId="<s:property value="#var2.id" />"><s:property value="valueMap.get(#valueKey)"/></label>
											</div>
										</td>
									</s:iterator>
								</s:iterator>
							</tr> 
							</s:iterator>
						</s:iterator>
					</s:iterator>
					</tbody>
				</table>
			</div>
			<div class="datagrid-footer"><div class="datagrid-footer-inner" style="display: none; "></div></div>
		</div>
	</div>
</div>
<!--end list -->
<div class="addToolbar">
    <button class="btn" type="button" onclick="location.href='<s:url action="term-list" namespace="/admin/dimension"/>?restore_params=true'">返回</button></p>
</div>
</body>
</html>

