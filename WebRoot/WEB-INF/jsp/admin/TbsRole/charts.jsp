<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
    var tbsRoleChartData;
    var tbsRoleChartType="line";
    tbsRoleChartDateType(4);//时
    
	function tbsRoleChartDateType(type){
		var url='${demoPath}admin/TbsRole/chartsJson.html?type='+type;
		$.getJSON(url, function(result) {
			tbsRoleChartData=result;
			tbsRoleChart(tbsRoleChartType,result);
		});
	}
    
	function tbsRoleChart(type,result){
		var data;
		if(result=='' || result==null){
			result=tbsRoleChartData;
		}
		if(type=='' || type==null){
			type=tbsRoleChartType;
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
		
		$('#tbsRoleChart').highcharts({
			chart : {
				type : type
			},
			credits : {
				enabled : true,
				href : "http://www.cms606.com/",
				text : 'JeeTemp模板系统'
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
				//categories : [ '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12' ]
				categories : result.categories,
				labels: {
	                   rotation: -45,
	                   align: 'right',
	                   style: {
	                       fontSize: '13px',
	                       fontFamily: 'Verdana, sans-serif'
	                   }
	             }
			},
			yAxis : {
				title : {
					text : 'JeeTemp'
				}
			},
			series : [ {
				name : '数据分析',
				//data : [ 7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6 ]
				data : data
			} ]
		});
	}
</script>
<div style="padding: 5px;padding-left: 10px;">
    <div style="float: left;">
		<a href="javascript:void(0)" onclick="javascript:tbsRoleChart('line','')" class="easyui-linkbutton">线状图</a> 
		<a href="javascript:void(0)" onclick="javascript:tbsRoleChart('column','')" class="easyui-linkbutton">柱状图</a> 
		<a href="javascript:void(0)" onclick="javascript:tbsRoleChart('pie','')" class="easyui-linkbutton">饼状图</a>
		<a href="javascript:void(0)" onclick="javascript:tbsRoleChartDateType('1')" class="easyui-linkbutton">年</a>
		<a href="javascript:void(0)" onclick="javascript:tbsRoleChartDateType('2')" class="easyui-linkbutton">月</a>
		<a href="javascript:void(0)" onclick="javascript:tbsRoleChartDateType('3')" class="easyui-linkbutton">日</a>
		<a href="javascript:void(0)" onclick="javascript:tbsRoleChartDateType('4')" class="easyui-linkbutton">时</a>
		<a href="javascript:void(0)" onclick="javascript:tbsRoleChartDateType('5')" class="easyui-linkbutton">分</a>
		<a href="javascript:void(0)" onclick="javascript:tbsRoleChartDateType('6')" class="easyui-linkbutton">秒</a>
	</div>
	<div align="right" style="padding-right: 10px;padding-top: 3px;">
		<label>开始时间</label>
		<input id="tbsRoleStartTime" class="easyui-datetimebox"/>
	    <label>结束时间</label> 
		<input id="tbsRoleEndTime" class="easyui-datetimebox"/>
	</div>
</div>

<div style="margin:0 1em;padding-top: 2px;">
	<div id="tbsRoleChart" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
</div>
