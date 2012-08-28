<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<ul class="list1">
	<li class="list1a"><input type="checkbox" id="selectAll"/><label for="selectAll" style="font-size:14px;font-weight:bolder;">全选</label>
	<ul class="list2">
		<s:iterator value="termList" status="st">
		<li class="${st.count==fn:length(termList)?'listb':'list2a' }">
			<div>
			<input type="checkbox" class="child" param1="${id }" param2="${name }" id="term${id }"/><label for="term${id }">${name }</label>
			</div>
		</li>
		</s:iterator>
	</ul>
	</li>
</ul>

<script>
$(document).ready(function() {
	$('#qishu a').each(function(){
		var id=$(this).attr('param');
		$(':checkbox[param1="'+id+'"]').attr('checked',true);
	});
	
	$('.child').click(function(){
		if ($(this).attr('checked')) {
			$('#qishu').append('<a class="options" href="javascript:void(0)" param="'+$(this).attr('param1')+'">'+$(this).attr('param2')+'</a>');
		} else {
			var id=$(this).attr('param1');
			$('#qishu a[param="'+id+'"]').remove();
		}
	});
	
	$('#selectAll').click(function(){
		if ($(this).attr('checked')) {
			$('.child').each(function(){
				$(this).attr('checked',true);
				$('#qishu a[param="'+$(this).attr('param1')+'"]').remove();
				$('#qishu').append('<a class="options" href="javascript:void(0)" param="'+$(this).attr('param1')+'">'+$(this).attr('param2')+'</a>');
			});
		} else {
			$('.child').each(function(){
				$(this).attr('checked',false);
				$('#qishu a[param="'+$(this).attr('param1')+'"]').remove();
			});
		}
	});
});
</script>