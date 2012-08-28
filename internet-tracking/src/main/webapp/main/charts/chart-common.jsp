<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
		var container = document.createElement("div");//common
		$(".imgBox").append(container);
		 theChart = new Highcharts.Chart({
			chart:{
				renderTo:container
			},
			title:{
				text:''
			},
			credits:{
				text:''
			},
			yAxis:{
				title:{
					text:''
				}
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
					size:'25%',
					center:['25%','25%'],
					showInLegend:false,
					zIndex:100,
					dataLabels:{
						formatter:function(){
							return this.point.name + ':' + Math.round(this.percentage*100)/100 + '%';
						}
					}
				}
			},
			xAxis:{
				title:{
					text:''
				},
				categories:[
					<s:set name="dot1" value="false" />
					<s:iterator value="customerCategorys">
						<s:if test="isSingleTerm && toBePie != null && toBePie == id">
						</s:if>
						<s:else>
						<s:if test="#dot1">,</s:if><s:else><s:set name="dot1" value="true" /></s:else>'${name}'
						</s:else>
					</s:iterator>
				],
				labels:{
					rotation:330
				}
			},
			series:[
			    <s:set name="dot1" value="false" />
				<s:iterator value="terms" var="theTerm">
					<s:if test="#dot1">,</s:if><s:else><s:set name="dot1" value="true" /></s:else>
					<s:set name="indexMap" value="indexToCustomerMap.get(id)" />
					<s:set name="dot2" value="false" />
					<s:iterator value="indexItems" >
					<s:if test="#dot2">,</s:if><s:else><s:set name="dot2" value="true" /></s:else>
					{
						<s:if test="isSingleTerm">name:'${name}'</s:if>
						<s:else>name:'${name}(${theTerm.name })'</s:else>
						<s:if test="!isSingleTerm || !isSingleIndexCategory">
							,type:'spline'
						</s:if>
						<s:else>
							,type:'<s:property value="graphTypeToSeriesMap[indexCategory.graphType]" />'
						</s:else>
						,data:[
							<s:set name="customerMap" value="#indexMap.get(id)" />
							<s:set name="dot3" value="false" />
							<s:iterator value="customerCategorys" status="st2">
							<s:if test="isSingleTerm && toBePie != null && toBePie == id">
							</s:if>
							<s:else>
								<s:if test="#dot3">,</s:if><s:else><s:set name="dot3" value="true" /></s:else>
								<s:if test="#customerMap.get(id).value != null">
									{name:'${name }',y:<s:property value="#customerMap.get(id).value" />}
								</s:if>
								<s:else>{name:'${name }',y:null}</s:else>
							</s:else>
							</s:iterator>
					    ]
					}
					</s:iterator>
				</s:iterator>
					<s:if test="isSingleTerm && toBePie != null && toBePie > 0 ">
					,{
						name:'${toBePieCustCategory.name}',type:'pie'
						,data:[
						<s:iterator value="terms">
							<s:set name="customerMap" value="customerToIndexMap.get(id)" />
							<s:set name="indexMap" value="#customerMap.get(toBePieCustCategory.id)" />
							<s:iterator value="indexItems" status="st2">
								<s:if test="#st2.index > 0">,</s:if>
								<s:if test="#indexMap.get(id).value != null">
									{name:'${name }',y:<s:property value="#indexMap.get(id).value" />}
								</s:if>
								<s:else>{name:'${name }',y:null}</s:else>
							</s:iterator>
						</s:iterator>
					    ]
					}
					</s:if>
			]
			
		});