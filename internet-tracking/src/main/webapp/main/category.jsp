<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<ul class="list1">
	<li class="list1a"><input type="checkbox" id="selectAll"/>全选
	<ul class="list2">
		<s:iterator value="indexCategoryList" status="st">
		<li class="${st.count==fn:length(indexCategoryList)?'listb':'list2a' }">
			<input type="checkbox" name="${id }" class="father" param1="${id }" param2="${name }" id="cate${id }"/><label for="cate${id }">${name }</label>
		</li>
		</s:iterator>
	</ul>
	</li>
</ul>

<script>
$(document).ready(function() {
	$('#zhibiao a').each(function(){
		var id=$(this).attr('param');
		$(':checkbox[param1="'+id+'"]').attr('checked',true);
	});
	
	$('.father').click(function(){
		var id=$(this).attr('name');
		if($(this).attr('checked')){
			$('.father[name="'+id+'"]').each(function(){
				$('#zhibiao a[param="'+$(this).attr('param1')+'"]').remove();
				$('#zhibiao').append('<a class="options" href="javascript:void(0)" param="'+$(this).attr('param1')+'">'+$(this).attr('param2')+'</a>');
				$(this).attr('checked',true);
			});
		} else {
			$('[name="'+id+'"]').each(function(){
				$('#zhibiao a[param="'+$(this).attr('param1')+'"]').remove();
				$(this).attr('checked',false);
			});
		}
	});
	
/* 	$('.child').click(function(){
		if ($(this).attr('checked')) {
			$('#zhibiao').append('<a class="options" href="javascript:void(0)" param="'+$(this).attr('param1')+'">'+$(this).attr('param2')+'</a>');
		} else {
			var id=$(this).attr('param1');
			$('#zhibiao a[param="'+id+'"]').remove();
		}
	}); */
	
	$('#selectAll').click(function(){
		if ($(this).attr('checked')) {
			$(':checkbox').each(function(){
				$(this).attr('checked',true);
				if ($(this).hasClass('father')) {
					$('#zhibiao a[param="'+$(this).attr('param1')+'"]').remove();
					$('#zhibiao').append('<a class="options" href="javascript:void(0)" param="'+$(this).attr('param1')+'">'+$(this).attr('param2')+'</a>');
				}
			});
		} else {
			$(':checkbox').each(function(){
				$(this).attr('checked',false);
				if ($(this).hasClass('father')) {
					$('#zhibiao a[param="'+$(this).attr('param1')+'"]').remove();
				}
			});
		}
	});
});
</script>