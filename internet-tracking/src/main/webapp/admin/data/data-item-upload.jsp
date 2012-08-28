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
<link href="<%=request.getContextPath()%>/v2/css/validator.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidator.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/static/js/formValidatorRegex.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(document).ready(function(){
 	$.formValidator.initConfig({formid:"form1",onerror:function(msg){alert(msg)},onsuccess:function(){return true;}});
 	$("#termName").formValidator({onshow:"",onfocus:"期数名不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"期数名不能为空"});
 	$("#file_data").formValidator({onshow:"",onfocus:"文件不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"文件不能为空"});
 	
});

</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">导入数据</b>
</div>
<!--list -->
<div class="mainAdd">
<table class="addTable">
  <tr>
	<th><b style="color:#ff0000;">第一步：</b></th>
	<td><b>导出模板。</b><br /><span>请在导出的模板里面填充数据，只有使用模板的数据才能为系统识别。</span></td>
  </tr>
  <tr>
    <th></th>
	<td><input class="btn" type="button" onclick="window.location='data-item!download.action'" value="导出模板"></input></td>
  </tr>
</table>
</div>
<s:form id="form1" action="data-item!importData.action" namespace="/admin/data" method="post" enctype="MULTIPART/FORM-DATA">

<div class="mainAdd">
<table class="addTable">
  <tr>
	<th><b style="color:#ff0000;">第二步：</b></th>
	<td><b>填写期数。</b><br /><span>指明导入的数据属于该期数。</span></td>
  </tr>
  <tr>
    <th></th>
	<td>期数：<s:textfield name="termName" id="termName" size="30"></s:textfield></td>
	</tr>
	<tr>
	<th></th>
	<td> 时间：
		<s:select name="statYear" list="#{'2008':'2008','2009':'2009','2010':'2010','2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015'}"/>
		年&nbsp;&nbsp;
		<s:select name="statMonth" list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10','11':'11','12':'12'}"/>
		月&nbsp;&nbsp;
	</td>

  </tr>
</table>
</div>
<div class="mainAdd">
<table class="addTable">
  <tr>
	<th><b style="color:#ff0000;">第三步：</b></th>
	<td><b>导入数据。</b><br /><span>请选择需要导入的文件</span></td>
  </tr>
  <tr>
    <th></th>
	<td><s:file name="dataFile" id="file_data"/></td>
  </tr>
</table>
</div>

<div class="mainAdd">
<table class="addTable"　style="margin-left:20px;">
  <s:iterator value="%{#request.uploadInfo}" var="var">
  <tr>
	<td><font color="red"><s:property value="%{#var}"/></font>  </td>
  </tr>
  </s:iterator>
</table>
</div>
<!--end list -->
<div class="addToolbar">
    <p><button class="btn" type="submit"><b>确定提交</b></button>
    <%--<button class="btn" type="button" onclick="location.href='<s:url action="sign-in-record-list" namespace="/admin/signin"/>?restore_params=true'">取消</button> --%></p>
</div>
 
 </s:form>

</body>
</html>

