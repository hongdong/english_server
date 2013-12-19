<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
$(function () {
    $(document).ready(function() {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
    
        var chart;
        $('#systemSigarChart').highcharts({
            chart: {
                type: 'spline',
                animation: Highcharts.svg, // don't animate in old IE
                marginRight: 10,
                events: {
                    load: function() {
                        // set up the updating of the chart each second
                        var series = this.series[0];
                        setInterval(function() {
                            var x = (new Date()).getTime(), // current time
                                y = Math.random();
                            series.addPoint([x, y], true, true);
                        }, 1000);
                    }
                }
            },
            title: {
                text: '未实现!!!'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);
                }
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            series: [{
                name: 'Random data',
                data: (function() {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;
    
                    for (i = -19; i <= 0; i++) {
                        data.push({
                            x: time + i * 2000,
                            y: Math.random()
                        });
                    }
                    return data;
                })()
            }]
        });
    });
    
});
</script>
<div style="padding: 5px;padding-left: 10px;">
    <div style="float: left;">
		<a href="javascript:void(0)" onclick="javascript:browserChart('line','')" class="easyui-linkbutton">线状图</a> 
		<a href="javascript:void(0)" onclick="javascript:browserChart('column','')" class="easyui-linkbutton">柱状图</a> 
		<a href="javascript:void(0)" onclick="javascript:browserChart('pie','')" class="easyui-linkbutton">饼状图</a>
		<%--
		<a href="javascript:void(0)" onclick="javascript:browserChartDateType('1')" class="easyui-linkbutton">年</a>
		<a href="javascript:void(0)" onclick="javascript:browserChartDateType('2')" class="easyui-linkbutton">月</a>
		<a href="javascript:void(0)" onclick="javascript:browserChartDateType('3')" class="easyui-linkbutton">日</a>
		<a href="javascript:void(0)" onclick="javascript:browserChartDateType('4')" class="easyui-linkbutton">时</a>
		<a href="javascript:void(0)" onclick="javascript:browserChartDateType('5')" class="easyui-linkbutton">分</a>
		<a href="javascript:void(0)" onclick="javascript:browserChartDateType('6')" class="easyui-linkbutton">秒</a>
		--%>
	</div>
	<div align="right" style="padding-right: 10px;padding-top: 3px;">
		<label>开始时间</label>
		<input id="browserStartTime" class="easyui-datetimebox"/>
	    <label>结束时间</label> 
		<input id="browserEndTime" class="easyui-datetimebox"/>
	</div>
</div>
<div style="margin:0 1em;padding-top: 2px;">
	<div id="systemSigarChart" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
</div>
