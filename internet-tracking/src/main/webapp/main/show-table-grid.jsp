<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>移动互联网用户行为跟踪系统</title>
<link href="<%=request.getContextPath()%>/static-main/css/admin.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/css/easyui/datagrid.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static-main/js/jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static/js/jquery.resizable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static/js/htmldatagrid.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/thickbox.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/leftMenu.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/cookie.js" type="text/javascript"></script>
<script>
$(document).ready(function() {
	$('.tr_index_item').click(function() {
		$('.tb_wb tr').removeClass('light');
		$(this).addClass('light');
	});
	htmldatagrid();
});

function confirmExport(){
	if (!confirm('确定要全部导出到Excel文件吗？')) return false;
	else return true;
}
</script>
</head>

<body>
<s:include value="./common/top.jsp" />
<div class="mainBox">
  <div class="mainLeft">
    <div class="tabs">
	  <div class="tabOn"><a href="#">结果展示</a></div>
	</div>
	<div class="tt">2.请选择期数</div>
	<div class="menuLine" id="menuLine">
	<ul id="menu">
	<li class="item"><a href="#" class="title">期数</a>
	  <ul class="optiton">
		<s:iterator value="#request.term_list">
			<s:if test="#request.preview==null">
			<li><a href="<%=request.getContextPath() %>/main/table!showTableGrid.action?termId=${id}">${name }</a></li>
			</s:if>
			<s:else>
			<li>${name }</li>
			</s:else>
		</s:iterator>
	  </ul>
	</li>
	<s:if test="#request.preview==null">
	<li class="item"><a href="<%=request.getContextPath() %>/main/table!exportExcel.action" class="title" onclick="return confirmExport()">导出Excel</a>
	</li></s:if>
	</ul>	
	</div>
		
  </div>
  
  <!-- right -->
  <div class="mainRight" >
  	<div id="datagrid-mask" style="display: block;" class="datagrid-mask"></div>
 	<div id="datagrid-mask-msg" style="display: block; left: 200px;" class="datagrid-mask-msg">加载中, 请稍后 ...</div>
 	<s:iterator value="#request.term_list" status="term_st">
	<s:if test="(#request.termId==null and #term_st.index==0) or id==#request.termId">
	<div id="datagrid-view" class="datagrid-view" style="height: 480px;">
		<!-- div left -->
		<div class="datagrid-view1">
			<div class="datagrid-header">
				<div class="datagrid-header-inner">
				<!-- 模块分类指标=标题显示 -->
				<table cellspacing="0" cellpadding="0" border="0" class="datagrid-htable" style="height: 55px;">
					<tbody>
						<tr class="datagrid-header-row">
							<td rowspan="1" field="rownumber"><div class="datagrid-header-rownumber"></div></td>
							<td field="productId" width="100"><div class="datagrid-cell"><span>模块</span></div></td>
						    <td field="indexCategory" width="100"><div class="datagrid-cell"><span>分类</span></div></td>
						    <td field="indexItem" width="150"><div class="datagrid-cell"><span>指标</span></div></td>
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
					<s:iterator value="#request.index_item_list" status="index_st">
						<s:set var="rownumber" value="#rownumber+1"></s:set>
						<tr class="datagrid-row" id="datagrid-row-r1-1-<s:property value="#rownumber"/>" datagrid-row-index="<s:property value="#rownumber"/>" style="height: 25px;">
							<td class="datagrid-td-rownumber" field="rownumber"><div class="datagrid-cell-rownumber"><s:property value="#rownumber"/></div></td>
							<s:if test="#index_st.index==0 or indexCategory.parentId!=#request.index_item_list[#index_st.index-1].indexCategory.parentId">
								<td field="productId" rowspan="${request.category_map[indexCategory.parentId] }" title="${request.category_map2[indexCategory.parentId].name }">
									<div class="datagrid-cell">${request.category_map2[indexCategory.parentId].name }</div>
								</td>
							</s:if>
							<s:if test="#index_st.index==0 or indexCategory.id!=#request.index_item_list[#index_st.index-1].indexCategory.id">
								<td field="indexCategory" rowspan="${request.sub_category_map[indexCategory.id] }" title="${indexCategory.name }">
									<div class="datagrid-cell">${indexCategory.name }</div>
								</td>
							</s:if>
							<td field="indexItem" title="${name}"><div class="datagrid-cell">${name }</div></td>
						</tr>
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
						    <!-- 客户群顶级分类 -->
							<s:iterator value="#request.customer_parent_map"> 
								<td colspan="${value}" align="center"><div class="datagrid-cell"><span>${key }</span></div></td>
							</s:iterator>
						</tr>
						<tr class="datagrid-header-row">
							<!-- 客户群 -->
							<s:iterator value="#request.customer_list" status="customer_st">
								<td field="custChild${customer_st.index}" append="20" align="center"><div class="datagrid-cell"><span>${name }</span></div></td>
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
					<s:iterator value="#request.index_item_list" status="index_st">
						<s:set var="rownumber" value="#rownumber+1"></s:set>
						<tr class="datagrid-row" id="datagrid-row-r1-2-<s:property value="#rownumber"/>" datagrid-row-index="<s:property value="#rownumber"/>" style="height: 25px;">
							<s:iterator value="#request.customer_list" status="customer_st">
							<td field="custChild${customer_st.index}">
								<div class="datagrid-cell">
								<s:if test="#request.preview==null">
								<s:property value="#request.data_list[#term_st.index][#index_st.index][#customer_st.index]"/>
								</s:if>
								</div>
							</td>
							</s:iterator>
						</tr>
					</s:iterator>
					</tbody>
				</table>
			</div>
			<div class="datagrid-footer"><div class="datagrid-footer-inner" style="display: none; "></div></div>
		</div>
	</div>
	</s:if>
	</s:iterator>
 </div>
</div>
<s:include value="./common/footer.jsp" />
</body>
</html>
