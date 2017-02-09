/***************************************人物社交关系分析****************************/
var commData=null;

//社区发现
function CommunityDetection(){
	//切换界面至社区发现
	$("#grid_10").empty();
	var div_operateall="<div style='margin-left:10px; margin-top:10px' class='operate_all'>"
	+"种子集 <input class='txt_seedset' type='text'/>&nbsp&nbsp&nbsp"
	+"<button class='btn_conntction' type='button' onclick='getConnection()'>连通图</button>&nbsp&nbsp&nbsp"
	+"</div>";
	$("#grid_10").append(div_operateall);
	//var p_seedset="<p>种子集</p>";
	//$("#operate_all").append(p_seedset);
	
}
//寻找连通图
function getConnection(){
	var seedset=$(".txt_seedset").val();
	$.ajax({
		url :"../../Community/getConnection",
		data:{
			seedset:seedset
		},
		type : 'post',
		async: true,
		success : function(data){
			//添加连通图选择下拉列表，并将连通图加入到列表中 
			//创建select　
			var select_Conn="<select id='select_Conn'></select>&nbsp&nbsp&nbsp";
			$(".btn_conntction").remove();
			$(".operate_all").append(select_Conn);
			//为select添加options
			var x=document.getElementById("select_Conn");
			for(var i=0;i<data["seedsets"].length;i++)
			{
				var value='';
				var j=0;
				for(j=0;j<data['seedsets'][i].length-1;j++)
				{
					value=value+data['seedsets'][i][j]+",";
				}
				value=value+data['seedsets'][i][j];
				var y=document.createElement('option');
				y.text=value;
				x.add(y,null);
			}
			//添加社区发现按钮
			var commDe_btn="<button class='btn_commdetc' type='button' onclick='CommDetection()'>社区发现</button>&nbsp&nbsp&nbsp"
			$(".operate_all").append(commDe_btn);
		}
	});
}
//社区发现功能
function CommDetection(){
	//alert($("#select_Conn").val());
	//alert($("#select_Conn").find("option:selected").text());
	$.ajax({
		url :"../../Community/getCommunity",
		data:{
			conntction:$("#select_Conn").val()
		},
		type : 'post',
		async: true,
		success : function(data){
			//添加三部分功能，三个按钮即可
			var result_div="<div style='width: 100%; height:100px; margin-top:10px; margin-left:10px' class='result_div'></div>";
			$("#grid_10").append(result_div);
			var basicinfo_btn="<button class='btn_getBasicInfo' type='button' onclick='getBasicInfo()'>社区信息</button>";
			//var drawGraph_btn="<button class='btn_DrawGraph' type='button' onclick='DrawGraph()'>绘制社区</button>";
			var commanalysis_btn="<button class='btn_commanalysis' type='button' onclick='CommAnalysis()'>社区分析</button>";
			$(".result_div").append(basicinfo_btn);
			//$(".result_div").append(drawGraph_btn);
			$(".result_div").append(commanalysis_btn);
			//默认显示基本信息
			commData=data;
			getBasicInfo();
		}
	});
}
//显示信息及绘制社区图
function getBasicInfo()
{
	var graph_div="<div id='main' style='width: 700px;height:500px;'></div>";
	$(".result_div").append(graph_div);
	//var myChart = echarts.init(document.getElementById('main'));
	
	option = {
		title : {
			text : 'webkit内核依赖',
			subtext : '数据来自网络',
			x : 'right',
			y : 'bottom'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{b}"
		},
		toolbox : {
			show : true,
			feature : {
				restore : {
					show : true
				},
				magicType : {
					show : true,
					type : [ 'force', 'chord' ],
					option : {
						chord : {
							minRadius : 2,
							maxRadius : 10,
							ribbonType : false,
							itemStyle : {
								normal : {
									label : {
										show : true,
										rotate : true
									},
									chordStyle : {
										opacity : 0.2
									}
								}
							}
						},
						force : {
							minRadius : 5,
							maxRadius : 8,
							itemStyle : {
								normal : {
									label : {
										show : false
									},
									linkStyle : {
										opacity : 0.5
									}
								}
							}
						}
					}
				},
				saveAsImage : {
					show : true
				}
			}
		},
		legend : {
			data : [ 'HTMLElement', 'WebGL', 'SVG', 'CSS', 'Other' ],
			orient : 'vertical',
			x : 'left'
		},
		noDataEffect : 'none',
		series : [ {
			// FIXME No data
			type : 'force',
		} ],
	};
	
	option.series[0] = {
            type: 'force',
            name: 'webkit-dep',
            itemStyle: {
                normal : {
                    linkStyle : {
                        opacity : 0.5
                    }
                }
            },
            categories: commData.categories,
            nodes: commData.nodes,
            links: commData.links,
            minRadius: 5,
            maxRadius: 8,
            gravity: 1.1,
            scaling: 1.1,
            steps: 20,
            large: true,
            useWorker: true,
            coolDown: 0.995,
            ribbonType: false
        };
	require(
		    [
		        'echarts',
		        'echarts/chart/force'
		    ],
		    function (ec) {
		        var myChart = ec.init(document.getElementById('main'));
		        myChart.setOption(option);
		        myChart.hideLoading();
		    }
		);
        
	/*
	 * myChart.showLoading(); myChart.hideLoading(); option = { legend : { //
	 * data : [ 'HTMLElement', 'WebGL', 'SVG', 'CSS', 'Other' ] },
	 * 
	 * series : [ { type : 'graph', layout : 'force', animation : false,
	 * hoverAnimation:true, focusNodeAdjacency:true, label : { normal : {
	 * position : 'right', formatter : '{b}' } }, draggable : true, data :
	 * commData.nodes.map(function(node) { return { // name: node.name, //
	 * value: node.value, //id: node.id, name: node.label, symbol:"triangle",
	 * symbolSize : node.size, label:{ normal:{ show:true, } } }; }), //
	 * categories: webkitDep.categories, force : { // initLayout: 'circular' //
	 * repulsion: 20, edgeLength : 5, repulsion : 20, gravity : 0.2 }, edges :
	 * commData.links.map(function(edge) { return { source: edge.source, target:
	 * edge.target } }), } ] };
	 * 
	 * myChart.setOption(option);
	 */
	
	
// option = null;
// myChart.showLoading();
// $.get('../../static/data/les-miserables.gexf', function (xml) {
// myChart.hideLoading();
// var graph = echarts.dataTool.gexf.parse(xml);
// var categories = [];
// for (var i = 0; i < 9; i++) {
// categories[i] = {
// name: '类目' + i
// };
//	    }
//	    graph.nodes.forEach(function (node) {
//	        node.itemStyle = null;
//	        node.value = node.symbolSize;
//	        node.symbolSize /= 1.5;
//	        node.label = {
//	            normal: {
//	                show: node.symbolSize > 30
//	            }
//	        };
//	        node.category = node.attributes.modularity_class;
//	    });
//	    option = {
//	        title: {
//	            //text: 'Les Miserables',
//	            subtext: 'Default layout',
//	            top: 'bottom',
//	            left: 'right'
//	        },
//	        tooltip: {},
//	        legend: [{
//	            // selectedMode: 'single',
//	            data: categories.map(function (a) {
//	                return a.name;
//	            })
//	        }],
//	        animationDuration: 1500,
//	        animationEasingUpdate: 'quinticInOut',
//	        series : [
//	            {
//	                name: 'Les Miserables',
//	                type: 'graph',
//	                layout: 'none',
//	                data: graph.nodes,
//	                links: graph.links,
//	                categories: categories,
//	                roam: true,
//	                label: {
//	                    normal: {
//	                        position: 'right',
//	                        formatter: '{b}'
//	                    }
//	                },
//	                lineStyle: {
//	                    normal: {
//	                        color: 'source',
//	                        curveness: 0.3
//	                    }
//	                }
//	            }
//	        ]
//	    };
//
//	    myChart.setOption(option);
//	}, 'xml');
}
////绘制图像
//function DrawGraph()
//{
//	
//}
//社区结果分析
function CommAnalysis()
{
	
}