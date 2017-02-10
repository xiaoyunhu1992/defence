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

function genUnsimAg(){
	$.ajax({
		url :"../../attackgraph/generate",
		type : 'post',
		data:{
			simply:0,
		},
		async: true,
		success : function(){
			//alert("success");
			$("#out").attr("src","../../static/images/systemmanager/outunsimple.gif");
//			$("#out").attr("src","/Users/hxy/Documents/workspace/defence/WebContent/static/images/systemmanager");
//			$("#grid_11").append("<img id ='out' src=../../static/images/systemmanager/outunsimple.gif>")
		}
	});
}

function gensimAg(){
	$.ajax({
		url :"../../attackgraph/generate",
		type : 'post',
		data:{
			simply:1,
		},
		async: true,
		success : function(){
			//alert("success");
			//$("#outunsimple").remove;
			//$("#grid_11").append("<img src=../../static/images/systemmanager/outsimple.gif>")
			$("#out").attr("src","../../static/images/systemmanager/outsimple.gif");
//			$("#out").attr("src","/Users/hxy/Documents/workspace/defence/WebContent/static/images/systemmanager/outsimple.gif");
		}
	});
}