<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<ul class="list1">
	<li class="list1a"><input type="checkbox" id="selectAll"/>全选
	<ul class="list2">
		<s:iterator value="customerCategoryList" status="st">
			<s:if test="parentId == 0">
			<li class="list2a">
				<input type="checkbox" class="father" name="${id }" id="pcust${id }" /><label for="pcust${id }">${name }</label>
				<s:set name="cid" value="id"/>
				<ul class="list3">
					<s:iterator value="customerCategoryList" status="st2">
						<s:if test="parentId==#cid">
						<li class="list3a">
							<input type="checkbox" name="${cid }" class="child" param1="${id }" param2="${name }" id="cust${id }"/><label for="cust${id }">${name }</label>
						</li>
						</s:if>
					</s:iterator>
				</ul>
			</li>
			</s:if>
		</s:iterator>
	</ul>
	</li>
</ul>

<script>
$(document).ready(function() {
	$('.list2a:last-child').removeClass('list2a').addClass('listb');
	$('.list3a:last-child').removeClass('list3a').addClass('listb');
	
	$('#kehu a').each(function(){
		var id=$(this).attr('param');
		$(':checkbox[param1="'+id+'"]').attr('checked',true);
	});
	
	$('.father').click(function(){
		var id=$(this).attr('name');
		if ($(this).attr('checked')) {
			$('.child[name="'+id+'"]').each(function(){
				$('#kehu a[param="'+$(this).attr('param1')+'"]').remove();
				$('#kehu').append('<a class="options" href="javascript:void(0)" param="'+$(this).attr('param1')+'">'+$(this).attr('param2')+'</a>');
				$(this).attr('checked',true);
			});
		} else {
			$('[name="'+id+'"]').each(function(){
				$('#kehu a[param="'+$(this).attr('param1')+'"]').remove();
				$(this).attr('checked',false);
			});
		}
	});
	
	$('.child').click(function(){
		if ($(this).attr('checked')) {
			$('#kehu').append('<a class="options" href="javascript:void(0)" param="'+$(this).attr('param1')+'">'+$(this).attr('param2')+'</a>');
		} else {
			var id=$(this).attr('param1');
			$('#kehu a[param="'+id+'"]').remove();
		}
	});
	
	$('#selectAll').click(function(){
		if ($(this).attr('checked')) {
			$(':checkbox').each(function(){
				$(this).attr('checked',true);
				if ($(this).hasClass('child')) {
					$('#kehu a[param="'+$(this).attr('param1')+'"]').remove();
					$('#kehu').append('<a class="options" href="javascript:void(0)" param="'+$(this).attr('param1')+'">'+$(this).attr('param2')+'</a>');
				}
			});
		} else {
			$(':checkbox').each(function(){
				$(this).attr('checked',false);
				if ($(this).hasClass('child')) {
					$('#kehu a[param="'+$(this).attr('param1')+'"]').remove();
				}
			});
		}
	});
});
</script>