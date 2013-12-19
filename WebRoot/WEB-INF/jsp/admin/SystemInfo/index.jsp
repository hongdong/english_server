<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
    var systemInfoChartData;
    var systemInfoChartType="line";
    systemInfoChartDateType(4);//时
    
	function systemInfoChartDateType(type){
		var url='${demoPath}admin/SystemInfo/index.html?type='+type;
		$.getJSON(url, function(result) {
			systemInfoChartData=result;
			systemInfoChart(systemInfoChartType,result);
		});
	}
    
	function systemInfoChart(type,result){
		var data;
		if(result=='' || result==null){
			result=systemInfoChartData;
		}
		if(type=='' || type==null){
			type=systemInfoChartType;
		}
		if(type=='pie' && result.categories.length>0){
			var pieData='[';
			for(var i=0;i<result.categories.length;i++){
				pieData+='[\"'+result.categories[i]+'\",'+result.data[i]+'],';
			}
			pieData=pieData.substr(0,pieData.length-1)+']'; //alert(pieData);
			data=eval('('+pieData+')');
		}else{
			data=result.data;
		}
		
		$('#systemInfoChart').highcharts({
			chart : {
				type : type
			},
			credits : {
				enabled : true,
				href : "",
				text : ''
			},
			title : {
				text : '线状图分析 '
			},
		    plotOptions: {
	            pie: {
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.name}: {point.percentage:.1f} %'
	                }
	            },
	            line: {
	                dataLabels: {
	                    enabled: true
	                }
	            },
	            column :{
	            	 dataLabels: {
		                    enabled: true
		                }
	            }
	        },
			xAxis : {
				categories : [ 'CPU', '内存' ]
				//categories : result.categories
			},
			yAxis : {
				title : {
					text : 'JeeTemp'
				}
			},
			series : [ {
				name : '数据分析',
				data : [ 100, 100], //data : data
			    dataLabels: {
                    enabled: true,
                    rotation: -90,
                    color: '#FFFFFF',
                    align: 'right',
                    x: 4,
                    y: 10,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
			} ]
		});
	}
</script>
<div style="padding: 5px;padding-left: 10px;">
    <div style="float: left;">
		<a href="javascript:void(0)" onclick="javascript:systemInfoLogChart('line','')" class="easyui-linkbutton">线状图</a> 
		<a href="javascript:void(0)" onclick="javascript:systemInfoChart('column','')" class="easyui-linkbutton">柱状图</a> 
		<a href="javascript:void(0)" onclick="javascript:systemInfoChart('pie','')" class="easyui-linkbutton">饼状图</a>
		<a href="javascript:void(0)" onclick="javascript:systemInfoChartDateType('1')" class="easyui-linkbutton">年</a>
		<a href="javascript:void(0)" onclick="javascript:systemInfoChartDateType('2')" class="easyui-linkbutton">月</a>
		<a href="javascript:void(0)" onclick="javascript:systemInfoChartDateType('3')" class="easyui-linkbutton">日</a>
		<a href="javascript:void(0)" onclick="javascript:systemInfoChartDateType('4')" class="easyui-linkbutton">时</a>
		<a href="javascript:void(0)" onclick="javascript:systemInfoChartDateType('5')" class="easyui-linkbutton">分</a>
		<a href="javascript:void(0)" onclick="javascript:systemInfoChartDateType('6')" class="easyui-linkbutton">秒</a>
	</div>
	<div align="right" style="padding-right: 10px;padding-top: 3px;">
		<label>开始时间</label>
		<input id="systemInfoStartTime" class="easyui-datetimebox"/>
	    <label>结束时间</label> 
		<input id="systemInfoEndTime" class="easyui-datetimebox"/>
	</div>
</div>

<div style="margin:0 1em;padding-top: 2px;">
	<div id="systemInfoChart" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
</div>
