/***************************************攻击图生成*******************************/

//function doSearch(){
//	var txtsearch=$(".txt_search").val();
//	//alert(txtsearch);
//	$.ajax({
//		url :"../../Message/GetMessageByKeyWord",
//		data:{
//			keyword:txtsearch
//		},
//		type : 'post',
//		async: true,
//		success : function(data){
//			//alert("success");
//			for (var i=0;i<data.length;i++)
//			{
//				$("#grid_10").append("<div>"+data[i]+"</div>")
//			}
//		}
//	});
//}
function uploadRule(){
	var formData = new FormData($(form2)[0]);
	
	$.ajax({
		url :"../../fileupload/rule",
		data: formData,
		type : 'post',
		async: false,
		contentType:false,
		processData:false,
		success : function(res){
			alert(res);
		}
	});
}
function uploadEvi(){
	var formData = new FormData($(form3)[0]);
	
	$.ajax({
		url :"../../fileupload/evidence",
		data: formData,
		type : 'post',
		async: false,
		contentType:false,
		processData:false,
		success : function(res){
			alert(res);
		}
	});
}

function uploadNetwork(){
	var formData = new FormData($(form1)[0]);
	
	$.ajax({
		url :"../../fileupload/network",
		data: formData,
		type : 'post',
		async: false,
		contentType:false,
		processData:false,
		success : function(res){
			alert(res);
		}
	});
}
function geninitRisk(){
	$.ajax({
		url :"../../attackgraph/initrisk",
		type : 'post',
		data:{
		},
		async: true,
		success : function(res){
			$("#tablebody").empty();
			$("#table1").show();
			var pro = res["pro"];
			var name = res["name"];
			var risk = res["riskvalue"];
			
			for (var i=0;i<name.length;i++){
				var divstring ="<tr>"+"<td>"+name[i]+"</td>"+"<td>"+pro[i]+"</td>"+"</tr>";
				$("#tablebody").append(divstring);
			}
			$("#tablebody").append("<tr> <td>整体风险值</td> <td>"+risk+"</td></tr>");
		}
	});
}

function genUnsimAg(){
	//	var removeObj = document.getElementById('submit');
	//	removeObj.parentNode.removeChild(removeObj);
	$.ajax({
		url :"../../attackgraph/generate",
		type : 'post',
		data:{
			simply:0,
		},
		async: true,
		success : function(){
			$("#table1").hide();
			//alert("success");
//			$("#out").attr("src","../../static/images/systemmanager/outunsimple.gif");
//			$("#out").attr("src","http://localhost:9999/outunsimple.png");
//			$("#out").attr("src","/Users/hxy/Documents/workspace/defence/WebContent/static/images/systemmanager");
			$("#out").attr("src","http://localhost:9999/outunsimple.svg")
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
			$("#table1").hide();
			//alert("success");
			//$("#outunsimple").remove;
			//$("#grid_11").append("<img src=../../static/images/systemmanager/outsimple.gif>")
//			$("#out").attr("src","../../static/images/systemmanager/outsimple.gif");
			$("#out").attr("src","http://localhost:9999/outsimple.svg")//
//			$("#out").attr("src","/Users/hxy/Documents/workspace/defence/WebContent/static/images/systemmanager/outsimple.gif");
		}
		
	});
}