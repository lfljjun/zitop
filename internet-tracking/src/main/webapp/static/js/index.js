function ajaxIndexValueInit()
{
	$("label").each(function(){
		$(this).click(function(){
			ajaxInputInit($(this))
		});	
 	});
} 	
function ajaxInputInit(label)
{
	var input = $("<input />");			
	input.attr("type","text");
	input.css({"width": label.width()});
	input.val(label.html());
	input.attr("labelValue",label.html());
	input.attr("indexItemId",label.attr("indexItemId"));
	input.attr("customerCategoryId",label.attr("customerCategoryId"));
	label.before(input);
	input.focus();
	input.blur(function(){
		ajaxLabelInit($(this))
	});
	label.remove();
}
function ajaxLabelInit(input){
	var result = ggLiuflj(input.attr("indexItemId"),input.attr("customerCategoryId"),input.val());
	var label = $("<label />");			
	label.css({"width":"100%","height":"20px","display":"block"})
	if(result.responseText != "true")
		label.html(input.attr("labelValue"));
	else
		label.html(input.val());
	label.attr("indexItemId",input.attr("indexItemId"));
	label.attr("customerCategoryId",input.attr("customerCategoryId"));
	$(input).before(label);
	label.click(function(){
		ajaxInputInit($(this));
	});	
	input.remove();
}