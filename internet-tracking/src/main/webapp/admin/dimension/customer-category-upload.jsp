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
 	$("#file_data").formValidator({onshow:"",onfocus:"文件不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"文件不能为空"});
 	$("#file_index").formValidator({onshow:"",onfocus:"文件不能为空",oncorrect:""}).inputValidator({min:1,max:100,onerror:"文件不能为空"});
 	
});

</script>
</head>
<body>
<div class="mTitle">
  <b class="bbig">导入数据</b>(请按照模板进行填写，可先下载模板填写)
</div>
<s:form id="form1" action="customer-category!importExcelEntities.action" namespace="/admin/dimension" method="post" enctype="MULTIPART/FORM-DATA">
<!--list -->
<div class="mainAdd">
<table class="addTable">
  <tr>
   <th>上传客户群</th>
	<td><s:file name="dataFile" id="file_data"/></td>
  </tr>
</table>
</div>
<!--end list -->
<div class="addToolbar">
    <p><button class="btn" type="submit"><b>确定提交</b></button>
    <%--<button class="btn" type="button" onclick="location.href='<s:url action="sign-in-record-list" namespace="/admin/signin"/>?restore_params=true'">取消</button> --%></p>
</div>
 
 </s:form>
<s:form id="form2" action="index-item!importExcelEntities.action" namespace="/admin/index" method="post" enctype="MULTIPART/FORM-DATA">
<!--list -->
<div class="mainAdd">
<table class="addTable">
  <tr>
  <th>上传指标</th>
	<td><s:file name="indexFile" id="file_index"/></td>
  </tr>
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

