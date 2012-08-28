<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="z" uri="/zitop_common" %>
<%@ taglib prefix="d" uri="/diagram" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>移动互联网用户行为跟踪系统</title>
<link href="<%=request.getContextPath() %>/static-main/css/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/static-main/js/jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/thickbox.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/leftMenu.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/highcharts.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/static-main/js/exporting.js" type="text/javascript"></script>
<script>
	var theCharts = [];
	var url = "<s:url action="diagram" namespace="/main" />?zhibiaos=${zhibiaos}&kehus=${kehus}&qishus=${qishus}";
	$(document).ready(function(){
		<s:include value="./charts/chart-pie.jsp" />
	});
	function changeType()
	{    var i = 0;
		var types = [];
		<s:iterator value="graphTypeToSeriesMap">
		<s:if test="key != null">
			types[${key}] = '${value}';
		</s:if>
		</s:iterator>
		$("[name='graphType']").each(function(){
			var indexCategoryId = $(this).attr("indexCategoryId");
			for(var j = 0 ; j < theCharts.length; j++)
			if(theCharts[j].indexCategoryId == indexCategoryId)
			{
				var options = theCharts[j].options;
				for(var i = 0 ; i < options.series.length ; i++ )			
					options.series[i].type = types[this.value];
				<s:if test="!isSingleTerm">
				if(types[this.value] == 'column')
					options.plotOptions.column.stacking = 'percent';
				else
					options.plotOptions.column.stacking = 'normal';
				</s:if>
				theCharts[j].destroy();
				theCharts[j] = new Highcharts.Chart(options);
				theCharts[j].indexCategoryId = indexCategoryId;
			}
		});
		
	}
	function showChartProp()
	{
		var text = "";
		for(var i in chart)
				text = text + (chart[i]) + "\n";
		alert(text);
	}
	function pieSelect(checked,toBePie)
	{
		if(checked)
			location.href=url+"&toBePie="+toBePie;
		else
			location.href=url+"&toBePie=0";
	}
</script>
</head>

<body>
<s:set name="tabPage" value="'diagram'" />
<s:include value="./common/top.jsp"/>
<div class="mainBox">
  <!-- left -->
  <div class="mainLeft">
    <div class="tabs">
<!--	  <div class="tabOff"><s:a action="index" namespace="/main"><s:param name="projectId" value="1"/>条件查询</s:a></div>-->
	  <div class="tabOn"><a href="#">指标呈现</a></div>
<!--	  <div class="tabOff"><a href="#">&nbsp;&nbsp;期数</a></div>-->
<!--	  <div class="tabOff"><a href="#">查询历史</a></div>-->
	</div>

<!-- 条件查询窗口 -->
	<div>
		<div class="tt">2、请选择指标的图形</div>
		<div class="leftInfo">
		<table class="tbzc">
		<s:iterator value="indexCategorys">
			<tr>
		      <td>
		      	<p>${name }</p>
		      	<select name="graphType" indexCategoryId="${id}" onchange="changeType()">
		      	<s:iterator value="graphTypeToNameMap">
		      	 	<s:if test="!isSingleTerm && key == 1">
		      	 	</s:if>
		      	 	<s:else>
		      			<option value="${key }" <s:if test="isSingleTerm && key == graphType || !isSingleTerm && graphType == 1 && key == 2 ">selected</s:if>>${value }</option>
		      		</s:else>
		      	</s:iterator>
		      	</select>
		      </td>
			</tr>
		</s:iterator>
<!--		<tr>-->
<!--		  <th>X轴</th>-->
<!--		  <td><s:select name="xaxis" list="#{'1':'指标','2':'客户群'}" value="xaxis"/></td>-->
<!--		</tr>-->
		</table>
		</div>
	</div>
  </div>
  <!-- left end -->
  
  <!-- right -->
  <div class="mainRight">
  	<div class="imgBox"></div>
<!--	<div class="buttonLine" style="text-align:right;">-->
<!--	  <a class="bigButton2" href="imgShows.htm">保存查询条件</a>-->
<!--	</div>-->
  </div>
  <!-- right end -->

</div>
<s:include value="./common/footer.jsp" />
</body>
</html>
