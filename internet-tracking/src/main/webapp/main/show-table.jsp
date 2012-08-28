<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>移动互联网用户行为跟踪系统</title>
<link href="<%=request.getContextPath()%>/static-main/css/admin.css"
	rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static-main/js/jquery.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/thickbox.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/leftMenu.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/cookie.js"
	type="text/javascript"></script>

<script>
	$(document).ready(function() {
		$('.tr_index_item').click(function() {
			$('.tb_wb tr').removeClass('light');
			$(this).addClass('light');
		});
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
  <div class="mainRight">
		<%-- <a href="<%=request.getContextPath() %>/main/table!exportExcel.action" onclick="return confirmExport()">导出</a> --%>
		<div class="tableBox">
			<s:iterator value="#request.term_list" status="term_st">
			<s:if test="(#request.termId==null and #term_st.index==0) or id==#request.termId">
				<%-- <center><b style="color: red;">${name }</b></center> --%>
				<table class="tb_wb">
					<tr class="wb1">
						<th class="n1" width="100px" rowspan="2">模块</th>
						<th class="n2" width="100px" rowspan="2">分类</th>
						<th class="n3" width="200px" rowspan="2">指标</th>
						<s:iterator value="#request.customer_parent_map">
							<th colspan="${value }">${key }</th>
						</s:iterator>
					</tr>
					<tr class="wb2">
						<s:iterator value="#request.customer_list">
							<th>${name }</th>
						</s:iterator>
					</tr>
					<s:iterator value="#request.index_item_list" status="index_st">
						<tr class="tr_index_item">
							<s:if test="#index_st.index==0 or indexCategory.parentId!=#request.index_item_list[#index_st.index-1].indexCategory.parentId">
								<td rowspan="${request.category_map[indexCategory.parentId] }">
									${request.category_map2[indexCategory.parentId].name }
								</td>
							</s:if>
							<s:if test="#index_st.index==0 or indexCategory.id!=#request.index_item_list[#index_st.index-1].indexCategory.id">
								<td rowspan="${request.sub_category_map[indexCategory.id] }">
									${indexCategory.name }
								</td>
							</s:if>
							<td>${name }</td>
							<s:iterator value="#request.customer_list" status="customer_st">
								<td>
									<s:if test="#request.preview==null">
									<s:property value="#request.data_list[#term_st.index][#index_st.index][#customer_st.index]"/>
									</s:if>
								</td>
							</s:iterator>
						</tr>
					</s:iterator>
				</table>
			</s:if>
			</s:iterator>
		</div>
	</div>
</div>
<s:include value="./common/footer.jsp" />
</body>
</html>
