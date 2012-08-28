function htmldatagrid(){
	//
	var datagrid = $("#datagrid-view");
	var view1 = datagrid.find("div.datagrid-view1");
	var view2 = datagrid.find("div.datagrid-view2");
	var header1 = view1.find("div.datagrid-header"), header2 = view2.find("div.datagrid-header");
	var body1 = view1.find("div.datagrid-body"), body2 = view2.find("div.datagrid-body");
	body1.show();
	body2.show();
	// 初始化Field宽度
	function initFieldWidth(header, body, appendw){
		appendw = appendw || 0;
		var mwidth = 0, headerTd = null, fieldx = null;
		header.find("table>tbody>tr>td").each(function() {
			headerTd = $(this);
			fieldx = headerTd.attr("field");
			if(fieldx){
				var headerCell = headerTd.find("div.datagrid-cell");
				var tappend = parseInt(headerTd.attr("append")) || 0;
				var bodyTd = body.find("table>tbody>tr>td[field='"+fieldx+"']");
				var bodyCell = bodyTd.find("div.datagrid-cell");
				if(bodyCell.length == 0) bodyCell = bodyTd;
				// 宽度控制
				var width = bodyTd.width();
				var tdwidth = parseInt(headerTd.attr("width"));
				if(tdwidth || width < headerTd.width()) { // 头的宽度大于内容宽度
					width = headerTd.width() + tappend;
					if(tdwidth){
						width = tdwidth + tappend;
						headerTd.removeAttr("width");
					}
					headerCell.width(width);
					bodyCell.width(width + appendw);
				} else { // 内容宽度大于头的宽度
					width = width + tappend;
					headerCell.width(width + appendw);
					bodyCell.width(width);
				}
				mwidth += bodyTd.width() + 1;
			}
		});
		return mwidth;
	}
	// 设置宽度高度
	function initWidthHeight(){
		var width = datagrid.width(), height = datagrid.height();
		view2.css("left", view1.outerWidth());
		view2.width(width - view1.outerWidth());
		view1.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer").width(view1.width());
		view2.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer").width(view2.width());
		var bodyHeight = height - view2.children("div.datagrid-header").outerHeight(true) - view2.children("div.datagrid-footer").outerHeight(true);
		view1.children("div.datagrid-body").height(bodyHeight);
		view2.children("div.datagrid-body").height(bodyHeight);
	}
	// ie下宽度控制
	view1.width(initFieldWidth(header1, body1));
	body2.find("table").width(initFieldWidth(header2, body2));
	initWidthHeight();
	//
	function getTr(index, rowType){
		rowType = rowType || 0;
		if (rowType == 0) {
			var tr1 = getTr(index, 1);
			var tr2 = getTr(index, 2);
			return tr1.add(tr2);
		} else {
			var tr = $("#datagrid-row-r1-" + rowType + "-" + index);
			if (!tr.length) {
				var body = (rowType == 1 ? body1 : body2);
				tr = body.find(">table>tbody>tr[datagrid-row-index=" + index + "]");
			}
			return tr;
		}
	}
	
	var headers = header1.add(header2).add(body1).add(body2).find("div.datagrid-cell");
	var proxy = null, resizing = false;
	headers.each(function() {
		$(this).resizable({
			handles : "e", 
			disabled : ($(this).attr("resizable") ? $(this).attr("resizable") == "false" : false),
			minWidth : 25, 
			onStartResize : function(e) {
				resizing = true;
				headers.css("cursor", "e-resize");
				if (!proxy) {
					proxy = $("<div class=\"datagrid-resize-proxy\"></div>").appendTo(datagrid);
				}
				proxy.css({ left : e.pageX - $(datagrid).offset().left - 1, display : "none" });
				setTimeout(function() {
					if (proxy) {
						proxy.show();
					}
				}, 500);
			},
			onResize : function(e) {
				proxy.css({ left : e.pageX - $(datagrid).offset().left - 1, display : "block" });
				return false;
			},
			onStopResize : function(e) {
				headers.css("cursor", "");
				var fieldx = $(this).parent().attr("field");
				// 设置  datagrid-cell field的宽度
				var col = header1.add(body1).find("table>tbody>tr>td[field='"+fieldx+"']").find("div.datagrid-cell");
				if(col.length==0){
					col = header2.add(body2).find("table>tbody>tr>td[field='"+fieldx+"']").find("div.datagrid-cell");
				}
				col.width($(this).outerWidth());
				col.boxWidth = parseInt(this.style.width);
				col.auto = undefined;
				view2.children("div.datagrid-header").scrollLeft(body2.scrollLeft());
				proxy.remove();
				proxy = null;
				// 设置datagrid div 的宽度
				var v1 = view1.find("table").outerWidth();
				var width = datagrid.width(), height = datagrid.height();
				view1.width(v1);
				view2.css("left", v1);
				view2.width(width - v1);
				view1.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer").width(view1.width());
				view2.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer").width(view2.width());
				//
				setTimeout(function() {
					resizing = false;
				}, 0);
			}
		});
	});
	//
	body2.find(">table>tbody>tr").unbind().bind("mouseover", function(e) {
		var tr = $(e.target).closest("tr.datagrid-row");
		if (!tr.length) {
			return;
		}
		index = parseInt(tr.attr("datagrid-row-index"));
		getTr(index).addClass("datagrid-row-selected")
		e.stopPropagation();
	}).bind("mouseout", function(e) {
		var tr = $(e.target).closest("tr.datagrid-row");
		if (!tr.length) {
			return;
		}
		index = parseInt(tr.attr("datagrid-row-index"));
		getTr(index).removeClass("datagrid-row-selected")
		e.stopPropagation();
	});
	//
	body2.unbind().bind("scroll", function() {
		view1.children("div.datagrid-body").scrollTop($(this).scrollTop());
		view2.children("div.datagrid-header").scrollLeft($(this).scrollLeft());
	});
	$("#datagrid-mask").hide();
	$("#datagrid-mask-msg").hide();
}