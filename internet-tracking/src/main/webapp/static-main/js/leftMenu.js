$(document).ready(function() {
	initMenu();

	$('.category').click(function() {
		var categoryId = $(this).attr('param');
		$('#conditionSelect').load('index!category.action', {
			'categoryId' : categoryId
		});
	});
	$('.customer').click(function() {
		$('#conditionSelect').load('index!customer.action');
	});
	$('.term').click(function() {
		$('#conditionSelect').load('index!term.action');
	});
	$('.options').live('click',function(){
		var id=$(this).attr('param');
		$(':checkbox[param1="'+id+'"]').attr('checked',false);
		$(this).remove();
	});
	$('#cleanAll').click(function(){
		$('.optionInfo a').remove();
		$('.optionInfo :checkbox').attr('checked',false);
		delCookie('zhibiaos');
		delCookie('kehus');
		delCookie('qishus');
	});
	
	$('#showTable').click(function(){
		var zhibiaos = '';
		$('#zhibiao .options').each(function(){
			zhibiaos = zhibiaos + $(this).attr('param') + '|';
		});
		if (zhibiaos == '') {
			alert('指标不能为空！');
			return false;
		}
		var kehus = '';
		$('#kehu .options').each(function(){
			kehus = kehus + $(this).attr('param') + '|';
		});
		if (kehus == '') {
			alert('客户群不能为空！');
			return false;
		}
		var qishus = '';
		$('#qishu .options').each(function(){
			qishus = qishus + $(this).attr('param') + '|';
		});
		if (qishus == '') {
			alert('期数不能为空！');
			return false;
		}
		addCookie('zhibiaos',zhibiaos);
		addCookie('kehus',kehus);
		addCookie('qishus',qishus);
		window.open("table!showTableGrid.action");
	});
	
	$('#showDiagram').click(function(){
		var zhibiaos = '';
		$('#zhibiao .options').each(function(){
			zhibiaos = zhibiaos + $(this).attr('param') + '|';
		});
		if (zhibiaos == '') {
			alert('指标不能为空！');
			return false;
		}
		var kehus = '';
		$('#kehu .options').each(function(){
			kehus = kehus + $(this).attr('param') + '|';
		});
		if (kehus == '') {
			alert('客户群不能为空！');
			return false;
		}
		var qishus = '';
		$('#qishu .options').each(function(){
			qishus = qishus + $(this).attr('param') + '|';
		});
		if (qishus == '') {
			alert('期数不能为空！');
			return false;
		}
		window.open("diagram.action?zhibiaos=" + zhibiaos + "&kehus=" + kehus + "&qishus=" + qishus);
	});
});

function initMenu() {
	$('#menu ul').hide();
	$('#menu ul:first').show();
	$('#menu li a').click(function() {
		var checkElement = $(this).next();
		if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
			return false;
		}
		if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
			$('#menu ul:visible').slideUp('fast');
			checkElement.slideDown('fast');
			return false;
		}
	});
	
}

