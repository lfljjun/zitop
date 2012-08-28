<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
	var container;//pie
	
	<s:iterator value="customerCategorys" var="theCustomerCat">
		<s:iterator value="indexCategorys" var="theIndexCat">	
		container = document.createElement("div");
		$(container).css("width","375px");
		$(container).css("height","325px");
		$(container).css("float","left");
		$(".imgBox").append(container);
		chart = new Highcharts.Chart({
			chart:{
				renderTo:container,
				spacingTop:50,
				plotBackgroundImage:null
			},
			title:{
				text:'${theIndexCat.name} - ${theCustomerCat.name}',
				style:{
					fontSize:'16px'
				}
			},
			credits:{
				text:''
			},
			exporting:{
				enabled:true
				,url:'<s:url action="diagram-export.action" namespace="/main" />'
				,buttons:{
					exportButton:{
						menuItems:null,
						onclick:function(){
							this.exportChart();
						}
					}
				}
	        },
			plotOptions:{
				pie:{
					allowPointSelect:true,
					cursor:'pointer',
					size:'30%',
					center:['50%','35%'],
					showInLegend:false,
					dataLabels:{
						formatter:function(){
							return this.point.name + ':' + Math.round(this.percentage*100)/100 + '%';
						}
					}
				},
				column:{
				<s:if test="graphTypeToSeriesMap[#theIndexCat.graphType] eq 'pie'">stacking:'percent'</s:if>
				<s:else>stacking:'normal'</s:else>
				}
			},
			legend:{
				enabled:false
			}, 
			yAxis:{
				title:'',
				allowDecimals:false
			},
			tooltip:{
				formatter:function(){
					if(typeof this.percentage == undefined || this.percentage == null)
						return this.series.name + "<br/>" + this.point.name + ':' + this.y;
					else
						return this.series.name + "<br/>" + this.point.name + ':' + Math.round(this.percentage*100)/100 + '%';
				}
			},
			<s:if test="isSingleTerm">
			xAxis:{
				categories:[
					<s:set name="dot0" value="false" />
					<s:iterator value="#theIndexCat.indexItems">
					<s:if test="#dot0">,</s:if><s:else><s:set name="dot0" value="true" /></s:else>
						'${name }'
					</s:iterator>
				],
				labels:{
					rotation:330
				}
			},
			series:[
				<s:set name="dot0" value="false" />
				<s:iterator value="terms" var="theTerm">
					<s:if test="#dot0">,</s:if><s:else><s:set name="dot0" value="true" /></s:else>
					<s:set name="indexToCustomer0Map" value="indexToCustomerMap.get(id)" />
					<s:set name="customerToIndex0Map" value="customerToIndexMap.get(id)" />
					{	
						name:'${theCustomerCat.name}'
						,type:'<s:property value="graphTypeToSeriesMap[#theIndexCat.graphType]" />'
						,data:[
							<s:set name="indexMap" value="#customerToIndex0Map.get(#theCustomerCat.id)" />
							<s:set name="dot1" value="false" />
							<s:iterator value="#theIndexCat.indexItems">
								<s:if test="#dot1">,</s:if><s:else><s:set name="dot1" value="true" /></s:else>
								<s:if test="#indexMap.get(id).value != null">
									{name:'${name }',y:<s:property value="#indexMap.get(id).value" />}
								</s:if>
								<s:else>{name:'${name }',y:null}</s:else>
							</s:iterator>
					    ]
					}
				</s:iterator>
			]
			</s:if>
			<s:else>
			xAxis:{
				categories:[
					<s:set name="dot0" value="false" />
					<s:iterator value="terms">
					<s:if test="#dot0">,</s:if><s:else><s:set name="dot0" value="true" /></s:else>
						'${name }'
					</s:iterator>
				],
				labels:{
					rotation:330
				}
			},
			series:[
				<s:set name="dot0" value="false" />
				<s:iterator value="#theIndexCat.indexItems" var="theIndex">
					<s:if test="#dot0">,</s:if><s:else><s:set name="dot0" value="true" /></s:else>
					{	
						name:'${theCustomerCat.name}'
						<s:if test="graphTypeToSeriesMap[#theIndexCat.graphType] eq 'pie'">
							,type:'column'
						</s:if>
						<s:else>
							,type:'spline'
						</s:else>
						,data:[
							<s:set name="dot1" value="false" />
							<s:iterator value="terms" var="theTerm">
								<s:set name="indexToCustomer0Map" value="indexToCustomerMap.get(#theTerm.id)" />
								<s:set name="customerToIndex0Map" value="customerToIndexMap.get(#theTerm.id)" />
								<s:set name="indexMap" value="#customerToIndex0Map.get(#theCustomerCat.id)" />
								<s:if test="#dot1">,</s:if><s:else><s:set name="dot1" value="true" /></s:else>
								<s:if test="#indexMap.get(#theIndex.id).value != null">
									{name:'${theIndex.name }',y:<s:property value="#indexMap.get(#theIndex.id).value" />}
								</s:if>
								<s:else>{name:'${theIndex.name }',y:null}</s:else>
							</s:iterator>
					    ]
					}
					</s:iterator>
			]
			</s:else>
		});
		chart.indexCategoryId = ${theIndexCat.id };
		theCharts.push(chart);
		</s:iterator>
	</s:iterator>