<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="zsec" uri="/spring_security_tag" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- top -->
<div class="top">
  <div class="topMenu">

  </div>
  <div class="out"><a href="<%=request.getContextPath() %>/j_spring_security_logout">退出系统</a></div>
  <zsec:authorize ifAnyGranted="BG_ADMIN">
  <div class="backHome"><a href="<%=request.getContextPath() %>/admin/home.action">后台管理</a></div>
  </zsec:authorize>
</div>
<!-- top end -->
<div class="mainTop">
  <div class="welcome">欢迎您：<sec:authentication property="principal.username" /></div>
  
  <s:if test="projectList.size != 0">
<!--  <div class="mleft"><a href="#"></a></div>-->
	<div>
	  <s:iterator value="projectList">
	  	<div <s:if test="id == #session.projectId">class="tabOn"</s:if><s:else>class="tabOff"</s:else> >
	  		<a href="<%=request.getContextPath() %>/main/index.action?projectId=${id}">${name }</a>
	  	</div>
	  </s:iterator>
	</div>
<!--  <div class="mright"><a href="#"></a></div>-->
  </s:if>
  
</div>
