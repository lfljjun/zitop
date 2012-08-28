//http://www.highcharts.com/ref/
var lineChart;
$(document).ready(function() {
	var options = {
		chart : { renderTo : 'container', defaultSeriesType : 'spline'},
		title : { text : '时间分布图' },
		credits : { enabled : false },
		xAxis : { categories : ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']},
		yAxis : { min: 0, title : { text : '展示次数' } },
		tooltip : { formatter : function() { return '' + this.series.name + ': ' + this.y + '次'; } },
		series : [ { name: 'demo', data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 23.3, 36.5, 18.3, 13.9, 9.6] } ]
	};
	
	function drawChart(url, param, title, options){
		jQuery.post(url, param, function(data) {
			var data = (typeof data=="string") ? eval("(" + data + ")") : data;
			if(type){ options.chart.defaultSeriesType = type; }
			options.yAxis.title.text = title;
			options.series = data.series;
			options.xAxis.categories = data.categories;
			if(lineChart) lineChart.destroy();
			lineChart = new Highcharts.Chart(options);
		});
	}
	$("#display_times").click(function(){
		var form = $("#search_form").serialize();
		drawChart(options, form+"&ajax=true&flag=1");
	});
});