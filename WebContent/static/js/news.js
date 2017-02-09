/***************************************消息传播分析*******************************/

function Search(){
	//切换界面至社区发现
	$("#grid_10").empty();
	var div_operateall="<div style='background:white; width: 100%; height:560px' class='operate_all'>"
	+"<div style='margin:auto; height=60px; position: absolute; left:400px; top:400px;'>"
	+"<input  size='30' style='width:200px; height=60px;'class='txt_search' type='text'/>&nbsp&nbsp&nbsp"
	+"<button class='btn_search' type='button' onclick='doSearch()'>搜索</button>"
	+"</div>";
	+"</div>";
	$("#grid_10").append(div_operateall);
	//alert("success");
}
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
				("#grid_10").append("<div>"+data[i]+"</div>")
			}
		}
	});
}