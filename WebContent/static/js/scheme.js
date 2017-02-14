/***************************************攻击图生成*******************************/

function doSearch(){
	var txtsearch=$(".txt_search").val();
	//alert(txtsearch);
	$.ajax({
		url :"../../Message/GetMessageByKeyWord",
		data:{
			keyword:txtsearch
		},
		type : 'post',
		async: true,
		success : function(data){
			//alert("success");
			for (var i=0;i<data.length;i++)
			{
				$("#grid_10").append("<div>"+data[i]+"</div>")
			}
		}
	});
}

function schNormal(){
	//切换界面至生成防御措施集合
	$("#grid_11").empty();
	var div_operateall="<div class='operate_all' id='opearte_all' >"
	+"<div style='margin:auto; height=60px; position: absolute; left:240px; top:160px;'>"
	+"种群数：<input  size='30' style='width:100px; height=60px;'class='txt_search' id = 'pop' type='number'/>&nbsp&nbsp"
	+"遗传代数：<input  size='30' style='width:100px; height=60px;'class='txt_search' id = 'gen' type='number'/>&nbsp&nbsp"
	+"可选防御方案数量：<input  size='30' style='width:100px; height=60px;'class='txt_search' id = 'num' type='number'/>&nbsp&nbsp"
	+"风险阈值：<input  size='30' style='width:100px; height=60px;'class='txt_search' id = 'riskcon' type='number'/>&nbsp&nbsp"
	+"成本阈值：<input  size='30' style='width:100px; height=60px;'class='txt_search' id = 'costcon' type='number'/>&nbsp&nbsp"
	+"<button class='btn_search' type='button' onclick='genSchNormal()'>生成</button>"
	+"</div>";
	+"</div>";
	$("#grid_11").append(div_operateall);
	//alert("success");
}

function genSchNormal(){
	var pop=$("#pop").val();
	var gen=$("#gen").val();
	var num=$("#num").val();
	var riskcon=$("#riskcon").val();
	var costcon=$("#costcon").val();
	$.ajax({
		url :"../../scheme/normal",
		type : 'post',
		data:{
			pop:pop,
			gen:gen,
			num:num,
			riskcon:riskcon,
			costcon:costcon
		},
		async: true,
		success : function(res){
			if(res.back=='success'){
//				window.location.href = "http://localhost:8080/defence/pages/systemmanager/" + res.url+".html";
				var meaning = res["realmean"];
				var risk = res["risk"];
				var cost = res["cost"];
				var myChart = echarts.init(document.getElementById('draw'));
				option = {
					    title : {
					        text: '可选防御方案集合',
					    },
					    tooltip : {
					        trigger: 'axis',
					        showDelay : 0,
					        axisPointer:{
					            show: true,
					            type : 'cross',
					            lineStyle: {
					                type : 'dashed',
					                width : 1
					            }
					        }
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            mark : {show: true},
					            dataZoom : {show: true},
					            dataView : {show: true, readOnly: false},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    xAxis : [
					        {
					            type : 'value',
					            scale:true,
					          	'name':'防御成本',
					            axisLabel : {
					                formatter: '{value} '
					            }
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            scale:true,
					            'name':'剩余风险',
					            axisLabel : {
					                formatter: '{value} '
					            }
					        }
					    ],
					    series : [ {
							// FIXME No data
							type : 'scatter',
						} ],

					};
				for (var i=0;i<meaning.length;i++){
					option.series[i]= {
							name : meaning[i],
							type : 'scatter',
							data : [[risk[i],cost[i]]]
					};
				}


				myChart.setOption(option);
					                    
			}else{
				alert(res.msg);
			}
			
		}
	});
}

