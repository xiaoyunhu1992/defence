
window.__getObjectType = null;

var treeobj = null, treedata = null,count = 0;

window.gridId2objectIdMap = {};
window.flag=false;
//当前项目ID
var curItemId = null;

window._filterCondition = [];
window._filterObject = {};

window._catalog = [];

//存储gridpanel的id用于回显选中的数据
window._grid_id = [];

//存储已选变量的rowuuid
window.yxbl_rowuuid = [];

/***记录调查对象数量***/
var so_object_num = 0, 
/***记录筛选条件生成数据加载次数***/
    sx_condition_numm = 0,

	datalist_num = 0;
/**
 * 调查对象筛选窗口是否重新初始化,true是需要重新刷新
 */
window.isWinReInit = false;
window.isWin1ReInit = false;
//页面首次点击提取数据时的计数器
//var firstTqsjNum = 0;

var contentHeight = 0;

function query(){
     
    document.form1.action = rootPath+"/search/getBGQMeta.do";
    document.form1.submit();
}

$(function(){
	//计算各模块的大小
	_initframe();
	
	//获取父页面传递过来的参数
	var showval = getPageParam();
	
	//获取登录用户
	getLoginPerson();

	/**** 获得数据源(企业一套表、经济普、人普) ****/
    var param = {
    	url: rootPath+"/metadataservice/queryMdItemContent.do?itemType=DDIInstance", //"icon.json",
        callback: menucallbackfun  //回调函数
    };
    $.DataAdapter.submit(param);
    
    function menucallbackfun(data){
    	
        var newdata = data["data"], k=6, symenu = $(".sy-header-menu");
        //var arrow = $('<div class="sy-tab-menu-arrow"></div>');
        var hoverObj;
        //symenu.after(arrow);
        for(var i=0, len=newdata.length; i<len; i++){
        	var itemId = newdata[i]["itemId"];
        	var title = newdata[i]["Label"];
        	if(itemId && title){
        		if(itemId && itemId == showval){
        			refreshFuncTree(itemId);
        			symenu.append('<div class="sy-tab-ctrl jazz-inline sy-tab-ctrl-select" id="'+itemId+'">'+title+'</div>');
        			//hoverObj = arrow;
        			curItemId = itemId;
        			_loaddataCatelog(itemId);
        		}else{
        			symenu.append('<div class="sy-tab-ctrl jazz-inline" id="'+itemId+'">'+title+'</div>');        		
        		}
            	symenu.append('<div class="sy-tab-split jazz-inline">&nbsp;</div>');
        	}
        }
		arrowPosition(symenu.children(".sy-tab-ctrl-select"));
        
        $(".sy-tab-ctrl").on("click", function(){
    		var itemId = $(this).attr("id");
    		if(curItemId!=itemId && itemId){
    			refreshFuncTree(itemId);

    		    arrowPosition($(this));
    		    
    		    $("#tab-li-1").addClass("jazz-tabview-selected").addClass("jazz-state-active");
    		    $("#tab-li-2").removeClass("jazz-tabview-selected").removeClass("jazz-state-active");
    			$("#tab_1").show();
    			//$("#tab_2").hide(); 
    			$("#paramGrid").gridpanel("recalculateGridpanelWidth");
    			$("#objectGrid").gridpanel("recalculateGridpanelWidth");
    		    
    		    curItemId = itemId;
    		    _loaddataCatelog(itemId);
    		    _treeEvent(itemId);
    		}
        });       
    }    

    //定义menu的箭头指向
    function arrowPosition(obj){
    	$(".sy-tab-ctrl").removeClass("sy-tab-ctrl-select");
    	obj.addClass("sy-tab-ctrl-select");
    	var newleft =  Math.floor((obj.outerWidth() - 35 - 80)/2);
    	
    	obj.css("background-position-x", newleft);
    }
    
    $(document).on("mousedown", function(e){
    	var v_id = $(e.target).parent().attr('id'); 
    	$(".dcdx-con-list").hide();
    	$(".t-bl").hide();
    	if( v_id!=undefined && v_id!="htButton"&&window.isCloseHt == true){
    		$("#htButton").hide();
    		$("#ht").removeClass("ctrl-btn-img-ht_on").addClass("ctrl-btn-img-ht");
    	    window.isCloseHt = false;
    	}
    	
    	if( v_id!=undefined && v_id!="sxButton"&&window.isCloseSx == true){
    		$("#sxButton").hide();
    		$("#sx").removeClass("ctrl-btn-img-sx_on").addClass("ctrl-btn-img-sx");
    	    window.isCloseSx = false;
    	}
//    	
    });
    
    _treeEvent(showval);
});


$(window).resize(function(){
	//_initframe();
	var aHeight = jazz.util.windowHeight();
	var aWidth = jazz.util.windowWidth();
	//组件宽度计算
	var contentHeight = aHeight - $(".sy-header").outerHeight() - $("#bottom_1").outerHeight() - 22;
	//$("#content").height(contentHeight);
	$(".sy-bottom").width("100%");
	var centerWidth;
	if($("body").height() > $(window).height()){
		centerWidth = aWidth - $("#content_west").outerWidth() - 43;
	}else{
		centerWidth = aWidth - $("#content_west").outerWidth() - 28;
	}
	centerWidth = centerWidth < 693?693:centerWidth;
	initScroll();
	$("#content2_center").css("width",centerWidth);
	$("#content2_north").css("width",centerWidth);
	$("#paramGrid").css("width",centerWidth);
	$("#paramGrid .jazz-panel-content").css("width",centerWidth);
	$("#titleAreaWrapper").css("width",centerWidth);
	$("#titleArea").css("width",centerWidth);
	$("#coreGrid").css("width",centerWidth);
});

window.isEdit=false;
function changeRadioCheck(){
	var chartType = window.drawInfo['chartType'];
	$("input[name='changeClumToRow']").change(function(){
		var value = $("input[name='changeClumToRow']:checked").val();
		window.drawInfo.rowOrColumn = value;
		window.isEdit=true;
		if(window.drawInfo['chartType'] == 'scatter'){
			window.drawInfo['scatterX'] = null;
			window.drawInfo['scatterY'] = null;
			window.drawInfo['scatterSize'] = null;
		}
		drawChart();
	});
}
//======================页面初始化 开始================================
/*** 获取登录用户 */
function getLoginPerson(){
	$.ajax({
		url : rootPath+'/admin/getLoginName.do',
		type : 'post',
		async: true,
		success : function(data){
			var login = data["loginName"] || "";
			if(login){
				$("#sy-user").append(login +", 欢迎您登录！");				
			}else{
				$("#sy-user").append("欢迎您登录！");
			}
		}
	});
}

function _loaddataCatelog(){
    var param = {
    	url: rootPath+"/metadataservice/themeMeasureTreeCatalog.do?projectId="+curItemId,
        callback: function(data){
        	if($.isArray(data["data"])){
        		window._catalog = data["data"];
        	}
        	window.isTreeNeedLoaded = true;
//			if(window.isTreeLoaded && window.isTreeNeedLoaded){
			if(window.isTreeLoaded){
				hideLoading();
			}
        }
    };
	window.isTreeNeedLoaded = false;
//	showLoading("正在请求属性树数据,请等待...");
    $.DataAdapter.submit(param);
}
/*** 根据屏幕计算页面各模块的大小 */
function _initframe(){
	var aHeight = jazz.util.windowHeight();
	var aWidth = jazz.util.windowWidth();
	//组件宽度计算
	var centerWidth = aWidth - $("#content_west").outerWidth();
	$("#content2_center").css("width",centerWidth);
	//组件高度计算
	contentHeight = aHeight - $(".sy-header").outerHeight() - $("#bottom_1").outerHeight() - 22;
	$("#paramGrid .jazz-grid-tables").eq(0).css("background-color","#F2F2BE");
	var lefttreeHeight =  contentHeight- $(".content_west_title").outerHeight() - $(".sy-tree-query").outerHeight() - 20;
	$("#lefttree").css("height",lefttreeHeight);
	$(".noData").css("min-height",contentHeight - $("#content2_north").height() - 19);
}
/*** 获取页面传参 */
function getPageParam(){
	var thisURL = document.URL;
	var getval =thisURL.split('?')[1];
	var showval = "";
	if(getval){
		showval = getval.split("=")[1];
	}
	return showval;
}
//=============================页面初始化 结束===============================
$(".paramDelete").live("click", function(){
	window.click = "paramDelete";
	var val = $(this).attr("val");
    var row = $('#paramGrid').gridpanel('getRowDataByParam', "__uuid", val);
    if(row){
    	
    	count--;
    	$('#paramGrid').gridpanel('removeRow',row);
    	
    }
    paramShowAndHidden();
	$(document).trigger("removeParam", [row]);
});

//变量选择区文字的显示隐藏逻辑
function paramShowAndHidden(){
	var length = $('#paramGrid').gridpanel('getRowLength');
	
	if(length == 0){
		$(".noDataText").show();
    	$("#paramGrid").hide();
    	
    	$(".ctrl-btn-img-tqsj").css("background","url('images/tqsj1.png') no-repeat center center");
	}else{
		$(".noDataText").hide();
    	$("#paramGrid").show();
    	$("#paramGrid").gridpanel("recalculateGridpanelWidth");
    	$(".ctrl-btn-img-tqsj").css("background","url('images/tqsj2.png') no-repeat center center");
	}
}

var setting = {
	view : {
		dblClickExpand : false,
		showLine: false,
		showIcon: true
	},
	data : {
		key: {
			name: "text"
		},
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pId",
			
			filter:true,    //是否启动过滤
			expandLevel:0,  //展开层级
			showFilterChildren:true, //是否显示过滤数据孩子节点
			showFilterParent:true,   //是否显示过滤数据父节点
			showLine : false
		}
	},

	callback : {
		onClick : treeOnClick,
		onNodeCreated: function(event, treeId, treeNode){
			
				if(!treeNode.isparent){
					
					var _text = [];
					_text.push(treeNode["text"]);
					
					var usage = treeNode["usage"];
					if($.isArray(usage)){
						var _usage = null, _name = "";
						for(var i=0, len=usage.length; i<len; i++){
							_usage = usage[i];
							if(_usage["bgqb"]=="N"){
								_name = "年";
							}else if(_usage["bgqb"]=="BN"){
								_name = "半年";
							}else if(_usage["bgqb"]=="J"){
								_name = "季";
							}else if(_usage["bgqb"]=="Y"){
								_name = "月";
							}
							if(i==0){
								_text.push("<div bgqb='"+_usage["bgqb"]+"' class='jazz-inline bl-bgq bl-bgq-margin bl-bgq-"+_usage["bgqb"]+"'>"+_name+"</div>");
							}else{
								_text.push("<div class='jazz-inline bl-bgq-line'>|</div>");
								_text.push("<div bgqb='"+_usage["bgqb"]+"' class='jazz-inline bl-bgq bl-bgq-"+_usage["bgqb"]+"'>"+_name+"</div>");								
							}
						}
					}
					$("#"+treeNode["tId"]+"_span").html(_text.join(""));					
				}
		}
	}
};

function _catalog_JG(tdata, list, t_tId, randomId, catalog, bgqb){
	var _c = null, treeId = randomId+"_tree";
	var obj = list.find("#"+randomId), t_icon = $("#"+t_tId+"_ico"), t_span = $("#"+t_tId+"_span");
	var variableId = catalog["variableId"];
	for(var i=0,len=window._catalog.length; i<len; i++){
		_c = window._catalog[i][variableId];
		if(_c){
//			obj.prev(".t-bl-list-title2").text(catalog["variableLabel"]+"：");
			obj.append("<div id='"+treeId+"' class='ztree jazz-inline t-bl-codelist-tree'></div>");
			
			var _setting = {
				view : {
					showLine: false,
					dblClickExpand : false
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				callback : {
					onClick:function(event, treeId, treeNode){
						var _icon = $("#"+treeNode.tId+"_ico"), _span = $("#"+treeNode.tId+"_span");
						var obj = tdata.data("varibleObj");
						
						var catalog = obj["catalog"];
							var _vv = $.extend({}, obj, {t_tId: treeNode.tId});
							//_vv["value"] = treeNode["value"];
							_vv["text"] = _vv["text"]+"—"+treeNode["text"];
							_vv["columnName"] = _vv["columnName"]+"-"+treeNode["value"]; 
							
							_vv["catalog"] = [{value: treeNode["value"], variableName: catalog[0]["variableName"]}];
							
						if(!_icon.hasClass("ico_docu2")){
							_icon.addClass("ico_docu2");
							_span.addClass("selectnode");

							_vv['source'] = $(".sy-tab-ctrl-select").html();
							window.addParams({node: _vv});
							
							t_icon.addClass("ico_docu2");
							t_span.addClass("selectnode").addClass("bl-bgq-selected");

							$("#"+t_tId).find("div.bl-bgq-"+bgqb).addClass("bl-bgq-selected");
							
						}else{
							_icon.removeClass("ico_docu2");
							_span.removeClass("selectnode");
							_vv['source'] = $(".sy-tab-ctrl-select").html();
							window.removeParams({
								node: _vv
							});
							
							//当前弹出页面中，是否有选中的记录，如果没有，就将变量树的选中状态还原成未选中
							if(list.find("span.selectnode").length === 0 //查找树
								&& list.find("div.t-bl-list-check").length === 0){ //查找无目录
								t_icon.removeClass("ico_docu2");
								t_span.removeClass("selectnode").removeClass("bl-bgq-selected");
								$("#"+t_tId).find("div.bl-bgq-"+bgqb).removeClass("bl-bgq-selected");
							}
							
						}						
					}
				}
			};
			$.fn.zTree.init($("#"+treeId), _setting, _c);			
		}
	}
}

function isExtend(usage, bgqb){
	var _qb = "";
	for(var i=0, len=usage.length; i<len; i++){
		_qb = usage[i]["bgqb"];
		if(_qb == bgqb){
			var data = usage[i]["data"];
			if(data.length===1){
				if(data[0]["catalog"] && data[0]["catalog"]["length"] > 0){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		}
	}
}


var __tempTreeClickInfo = {};
var __tempTreeClickTimer = null;
function treeOnClick(event, treeId, treeNode) {
	
	var treeobj =  $.fn.zTree.getZTreeObj("lefttree");
	var scrollTop=$(window).scrollTop();
	if(treeNode.isparent){
		if(treeNode.open){
			treeobj.expandNode(treeNode, false, false, true);
		}else{
			treeobj.expandNode(treeNode, true, false, true);
		}
	}else{
		var po = null, obj = null, listid = null,  _usage = null;
		var tIdObj = $("#"+treeNode.tId);
		
		//有目录的变量
//		if(treeNode["isExtend"] == "Y"){
		//	var target = $(event.originalEvent.target);
			var target = $(event.target);

			var usage = treeNode["usage"];
			if($.isArray(usage)){
					var _BJ = "", _isextend = false;
					if(target.hasClass("bl-bgq-N") || target.hasClass("bl-bgq-BN") || target.hasClass("bl-bgq-J") || target.hasClass("bl-bgq-Y")){
						_BJ = target.attr("bgqb");
						_isextend = isExtend(usage, _BJ);
					}else{
						var bgqObj = tIdObj.find(".bl-bgq").eq(0);
						_BJ = bgqObj.attr("bgqb");
						_isextend = isExtend(usage, _BJ);
					}
					//先隐藏列表
					$(".t-bl").hide();
					
					if(_isextend){
						//有目录的情况下要先判断目录信息是否请求回来了
						//看isTreeNeedLoaded值
						if(!isTreeNeedLoaded){
							showLoading();
							jazz.info("正在请求数据请等待");
							__tempTreeClickInfo = {
								event: event,
								treeId: treeId, 
								treeNode: treeNode
							}
							__tempTreeClickTimer = setInterval(function(){
								if(isTreeNeedLoaded){
									clearInterval(__tempTreeClickTimer);
									__tempTreeClickTimer = null;
									hideLoading();
									treeOnClick(__tempTreeClickInfo['event'], __tempTreeClickInfo['treeId'], __tempTreeClickInfo['treeNode']);
								}
							}, 50);
							return;
						}
						for(var i=0, len=usage.length; i<len; i++){
							_usage = usage[i];
							if(_usage["bgqb"] == _BJ){				
								po = tIdObj.find("div.bl-bgq-"+_BJ);
								if(po.data("codelist")){
									obj = po.data("codelist");
								}else{
									listid = "list_id_"+jazz.getRandom() + jazz.getRandom();
									obj = $('<div class="t-bl"><div class="t-bl-list" id="'+listid+'"></div></div>').appendTo("body");
									
									var list = obj.children("#"+listid);
									var catalog = null, tdata = null, islist = "Y";	//islist Y有目录 W无目录						
									
									var _data = _usage["data"];
									for(var j=0, k=_data.length; j<k; j++){
										catalog = _data[j]["catalog"], randomId = "r_d_"+jazz.getRandom()+jazz.getRandom();
										if(j>0){ list.append("<div class='t-bl-data-line'></div>"); }
										if($.isArray(catalog) && catalog.length>0){
											tdata = $("<div class='t-bl-data'><div class='t-bl-list-title'>"+_data[j]["logicStorageName"]+"</div><div id='"+randomId+"' class='t-bl-codelist'></div></div>").appendTo(list);
											islist = "Y";
										}else{
											tdata = $("<div class='t-bl-data'><div id='"+randomId+"' class='t-bl-list-title t-bl-list-click'>"+_data[j]["logicStorageName"]+"</div></div>").appendTo(list);
											islist = "W";
										}
										
										tdata.data("varibleObj", $.extend({text: treeNode.text, columnName: treeNode.variableName,
											variableId: treeNode.variableId, tId: treeNode.tId,variableType:treeNode.variableType},
											_data[j], {"bgqb": _BJ, "isExtend": "Y", "islist": islist, "listid": listid, "randomId": randomId}));
										
										if($.isArray(catalog)){
											for(var p=0, q=catalog.length; p<q; p++){
												_catalog_JG(tdata, list, treeNode.tId, randomId, catalog[p], _BJ);
											}
										}
									}
									
									list.find(".t-bl-list-click").off("mousedown").on("mousedown", function(e){
										var icon = $("#"+treeNode.tId+"_ico"), span = $("#"+treeNode.tId+"_span");
										if($(this).hasClass("t-bl-list-check")){
											$(this).removeClass("t-bl-list-check");
											var obj = $(this).closest(".t-bl-data").data("varibleObj");
											window.removeParams({node: obj});
											
											//当前弹出页面中，是否有选中的记录，如果没有，就将变量树的选中状态还原成未选中
											if(list.find("span.selectnode").length === 0      //查找树
													&& list.find("div.t-bl-list-check").length === 0){ //查找无目录
												tIdObj.find("div[bgqb='"+_BJ+"']").removeClass("bl-bgq-selected");
											}
											
											//判断多个报告期状态,如果多个报告期的数据都没有选中状态,则还原变量树的状态
											if(bgq_select_num(tIdObj) === 0){
												$("#"+treeNode.tId+"_ico").removeClass("ico_docu2");
												$("#"+treeNode.tId+"_span").removeClass("selectnode").removeClass("bl-bgq-selected");
											}
										}else{
											$(this).addClass("t-bl-list-check");
											var obj = $(this).closest(".t-bl-data").data("varibleObj");
											obj['source'] = $(".sy-tab-ctrl-select").html();
											window.addParams({node: obj});
											
											icon.addClass("ico_docu2");
											span.addClass("selectnode").addClass("bl-bgq-selected");
											tIdObj.find("div.bl-bgq-"+_BJ).addClass("bl-bgq-selected");
										}
										e.stopPropagation();
									});
									//obj.append('<div class="dcdx-con-btn-wrap"><div class="dcdx-con-btn"></div></div>');
									po.data("codelist", obj);
									//阻止冒泡
									obj.on("mousedown", function(e){ e.stopPropagation(); });
								}
								
								var left = po.offset().left, top = po.offset().top;
								
								var winheight = $(window).height() || document.body.clientHeight;
								if((top+5)+obj.outerHeight() > winheight + $(document).scrollTop()){
									top = winheight + $(document).scrollTop() - obj.outerHeight() - 5;
								}else{
									top = top + 5;
								}
								obj.css({"position": "absolute", "top": top, "left": left + 30});			
								obj.show();							
							}
						}
						
					}else{
						var icon = $("#"+treeNode.tId+"_ico"), span = $("#"+treeNode.tId+"_span");
						
						var usage = treeNode["usage"];
//						var _target;
						if(target[0].tagName == "A"){
							target = target.find('span').eq(1);
						}
						if($.isArray(usage)){
							if(!target.hasClass("bl-bgq-selected")){
								icon.addClass("ico_docu2");
								span.addClass("selectnode").addClass("bl-bgq-selected");
			
								var _BJ = "N";
								if(target.hasClass("bl-bgq-N") || target.hasClass("bl-bgq-BN") || target.hasClass("bl-bgq-J") || target.hasClass("bl-bgq-Y")){
									_BJ = target.attr("bgqb");
								}else{
									var bgqObj = tIdObj.find(".bl-bgq").eq(0);
									bgqObj.addClass("bl-bgq-selected");
									_BJ = bgqObj.attr("bgqb");
								}
								target.addClass("bl-bgq-selected");
								
								var _usage = null;
								for(var i=0, len=usage.length; i<len; i++){
									_usage = usage[i];
									if(_usage["bgqb"] == _BJ){
										$.extend(treeNode, _usage["data"][0], {"bgqb": _BJ});
									}
								}
			
								treeNode["columnName"] = treeNode["variableName"];					

								treeNode['source'] = $(".sy-tab-ctrl-select").html();
								window.addParams({
									node: treeNode
								});
								
								count++;
							}else{
								count--;
								target.removeClass("bl-bgq-selected");
								var _BJ = "N";
								if(target.hasClass("bl-bgq-N") || target.hasClass("bl-bgq-BN") || target.hasClass("bl-bgq-J") || target.hasClass("bl-bgq-Y")){
									_BJ = target.attr("bgqb");
								}else{
									var bgqObj = tIdObj.find(".bl-bgq").eq(0);
									while(bgqObj.length  && !bgqObj.hasClass("bl-bgq-selected")){
										bgqObj = bgqObj.next();
									}
									bgqObj.removeClass("bl-bgq-selected");
									_BJ = bgqObj.attr("bgqb");
								}
								
								var _usage = null;
								for(var i=0, len=usage.length; i<len; i++){
									_usage = usage[i];
									if(_usage["bgqb"] == _BJ){
										$.extend(treeNode, _usage["data"][0], {"bgqb": _BJ});
									}
								}
			
								treeNode["columnName"] = treeNode["variableName"];			
								
								if(bgq_select_num(tIdObj) === 0){
									icon.removeClass("ico_docu2");
									span.removeClass("selectnode").removeClass("bl-bgq-selected");					
								}
								window.removeParams({
									node: treeNode
								});	
							}
						}
					}
			}			
						
	}
}

function bgq_select_num(obj){
//	alert("bgq_select_num");
	var n = 0;
	$.each(obj.find(".bl-bgq"), function(){
		if($(this).hasClass("bl-bgq-selected")){
			n++;
		};
	});
	return n;
}

//处理已选变量被删除后，变量树的回显
$(document).on("removeParam", function(e, d){
	//目录
	if(d["isExtend"] == "Y"){
		var tId = d["tId"], listid = d["listid"], randomId = d["randomId"], t_tId = d["t_tId"], bgqb = d["bgqb"];
		var obj = $("#"+tId);
		
		//有目录列表的
		if(d["islist"] == "Y"){
			$("#"+t_tId+"_ico").removeClass("ico_docu2");
			$("#"+t_tId+"_span").removeClass("selectnode");
		}else{
			//无目录列表的
			if($("#"+randomId).hasClass("t-bl-list-check")){
				$("#"+randomId).removeClass("t-bl-list-check");
			}			
		}
		
		//还原当前报告期状态
		if($("#"+listid).find("div.t-bl-list-check").length === 0  //查找所有无目录的
		     && $("#"+listid).find("span.selectnode").length === 0){  //查找所有弹出树的
			  //满足以上条件，还原当前报告期状  
			obj.find("div[bgqb='"+bgqb+"']").removeClass("bl-bgq-selected");			
		}
		
		//判断多个报告期状态,如果多个报告期的数据都没有选中状态,则还原变量树的状态
		if(bgq_select_num(obj) === 0){
			$("#"+tId+"_ico").removeClass("ico_docu2");
			$("#"+tId+"_span").removeClass("selectnode").removeClass("bl-bgq-selected");
			$("#"+tId+"_span").find(".bl-bgq-selected").removeClass("bl-bgq-selected");
		}
		
	}else{
	//非目录
		var tId = d["tId"], bgqb = d["bgqb"];
		var obj = $("#"+tId);
		obj.find("div[bgqb='"+bgqb+"']").removeClass("bl-bgq-selected");
		
		if(bgq_select_num(obj) === 0){
			$("#"+tId+"_ico").removeClass("ico_docu2");
			$("#"+tId+"_span").removeClass("selectnode").removeClass("bl-bgq-selected");
			$("#"+tId+"_span").find(".bl-bgq-selected").removeClass("bl-bgq-selected");
		}
	}
});



//=============================变量树事件 开始====================================
function _treeEvent(projectId){
    //用于变量树查询
    $("#treetext").autocompletecomboxfield("option", "dataurl", rootPath+"/metadataservice/queryMeasures.do?projectId="+projectId);
    $("#treetext").find(".jazz-field-comp-input").off("keyup.input").on("keyup.input", function(e){
    	tree_search(e);
    });	
}

function tree_search(e){
	if(e.keyCode == 13){
		$("div[name='dropdownpanel_treetext']").hide();
		tree_filter();		
	}
}
/*** 树节点过滤 ***/
function tree_filter(){
	var treetext = $("#treetext").autocompletecomboxfield("getText")+"";
	treeobj = $.fn.zTree.init($("#lefttree"), setting, treedata);
	if(treetext || treetext===0){
		treeobj.expandAll(true);
	}

	if(treetext){
		var listfolder = [], removefolder = [];
		function filterFolderNode(node){
			var pnode = node.getParentNode();
			if(pnode){
				listfolder.push(pnode["tId"]);
				filterFolderNode(pnode);
			}
		};
		
		function filterNode(node){
			var cNode = node["children"];
			if((node["text"]+"").indexOf(treetext) > -1){
				if(!cNode){ //子节点
					//高亮
					var text = $("#"+node.tId+"_span").get(0).innerHTML + "";
					var newtext = text.replace(eval("/("+treetext+")/g"),"<div class='ztree-filter-font'>$1</div>");  
					$("#"+node.tId+"_span").get(0).innerHTML = newtext;
					filterFolderNode(node);
				}else{ //父节点
					//高亮
					var text = $("#"+node.tId+"_span").get(0).innerHTML + "";
					var newtext = text.replace(eval("/("+treetext+")/g"),"<div class='ztree-filter-font'>$1</div>");  
					$("#"+node.tId+"_span").get(0).innerHTML = newtext;
					listfolder.push(node["tId"]);
					filterFolderNode(node);
				}
			}else{
				if(!cNode){
					//treeobj.hideNode(node);
					//$("#"+node.tId).hide();
					removefolder.push(node["tId"]);
				}else{
					//存入移除节点文件夹
					removefolder.push(node["tId"]);
				}
			}
			if(cNode){
				for(var i=0, len=cNode.length; i<len; i++){
					filterNode(cNode[i]);
				}
			}
		};
		
		var nodes = treeobj.getNodes();
		if(nodes){
			for(var i=0, len=nodes.length; i<len; i++){
				filterNode(nodes[i]);
			}
		}
		
		for(var i=0, len=removefolder.length; i<len; i++){
			if($.inArray(removefolder[i], listfolder) == -1){
				$("#"+removefolder[i]).hide();			
			}
		}
		
	}
}

function refreshFuncTree(projectId) {
	var str = "";
	if(projectId){
		str = "&projectId="+projectId;
	}

	var params = {
		url : rootPath+'/metadataservice/themeMeasureTree.do?random='+Math.random()+str,  //url: "tree.json",
		//url: "data.json",
		callback : function(data, r, res) {
			
			treedata = data["data"];
			var id;
			for(var i = 0, j = treedata.length; i < j; i++){
				if(treedata[i].pId === "" || treedata[i].pId === undefined){
					id = treedata[i].id;
					break;
				}
			}
			treeobj = $.fn.zTree.init($("#lefttree"), setting, treedata);
			
			var node = treeobj.getNodesByParam("id", id)[0];
			window.isTreeLoaded = true;
//			if(window.isTreeLoaded && window.isTreeNeedLoaded){
			if(window.isTreeLoaded){
				hideLoading();
			}
			if(node){
			}
		}
	};
	showLoading();
	window.isTreeLoaded = false;
	$.DataAdapter.submit(params);
}
//========================变量树事件  结束=================================


/*** 多tab页联动 ***/
function tabTogether(){
	
	$("#tab-change").find(".jazz-tabview-default").on("click", function(){
		if(!$(this).hasClass("jazz-tabview-selected")){
			if($(this).attr("id")=="tab-li-2"){
				//判断已选变量是否选中，当未选中时，提示要选中已选变量
				var yxobj = window.getDataByParam();
				if(yxobj.length == 0){
					jazz.info("已选变量不得为空，请选择后再进行调查对象选择操作！");
					return false;
				}
			}
			//window.__getObjectType = "changeTab";
			_changeTab($(this), true);
		}
	});
}

function _changeTab(obj, flag){
//	alert("_changeTab");
	//切换tab标签
	$("#tab-change").find(".jazz-tabview-selected")
    				.removeClass("jazz-tabview-selected")
    				.removeClass("jazz-state-active");
	
	obj.addClass("jazz-tabview-selected");
	obj.addClass("jazz-state-active");
	
	if(obj.attr("id")=="tab-li-2"){
		$("#tab_1").show();
		
		//刷新调查对象
		refrash_dcObject(flag);
		
		$("#objectGrid").gridpanel("recalculateGridpanelWidth");
	}else{
		$("#tab_1").show();
		
		$("#paramGrid").gridpanel("recalculateGridpanelWidth");
	}	
}

function refrash_dcObject(flag){
	var yxobj = window.getDataByParam();
	var yxbl_num =window.yxbl_rowuuid.length, yx_num=yxobj.length, _num = 0;
	//比较缓存中变量和列表中
	for(var i=0,len=yxbl_num; i<len; i++){
		for(var j=0,k=yx_num; j<k; j++){
			if(window.yxbl_rowuuid[i] == yxobj[j]["rowuuid"]){
				_num++;
			}
		}
	}

	if(yxbl_num===0 || yxbl_num != yx_num || yxbl_num != _num || isConditionMeasureChange){//已选变量发生改变
		window.gridId2objectIdMap = {};
		_dataload(flag);
		//存储上次已选变量的rowuuid
		window.yxbl_rowuuid = [];
		for(var i=0,len=yx_num; i<len; i++){
			window.yxbl_rowuuid.push(yxobj[i]["rowuuid"]);
		}
	}else{//已选变量未发生改变
		window.isRefreshObject = true;
		window.isGetObjectGridHeadInfo=true;
	}	
}

/*** 创建筛选条件 ****/
function createCondition(to, obj){
	var mcobj = null, name = obj["variableName"], id = obj['variableId'];
	if(jazz.getStringLength(name)>12){ //名称大于12个字符时，需要显示省略号，并进行提示
		mcobj = $('<div class="dcdx-tj-mc" title="'+name+'" id="'+id+'">' + name + '</div>').appendTo(to);	        				
	}else{
		mcobj = $('<div class="dcdx-tj-mc" id="'+id+'">' + name + '</div>').appendTo(to);
	}
	mcobj.data("condition", obj);
}

/*** 初始化左侧调查对象的grid ****/
function initObjectGrid(id){
//	alert("initObjectGrid");
	var dcdx = $("#"+id).find(".dcdx-data");
	var grid = dcdx.find('div[vtype="gridpanel"]');
	if(grid.length == 0){
		var g = [], _id = id+"_grid";
		window._grid_id.push(_id);	
		g.push('<div id="'+_id+'" name="'+_id+'" vtype="gridpanel" width="344" titledisplay="false" isshowselecthelper="false" selecttype="2" lineno="false" linenowidth="36" linetype="1" datarender="_r_data()">');
		g.push('    <div vtype="gridcolumn" name="'+id+'_column" id="'+id+'_column" style="display:none">');
		g.push('         <div>');
		g.push('         </div>');
		g.push('    </div>');
		g.push('    <div vtype="gridtable" name="grid_table" style="display:none"></div>');
		g.push('    <div vtype="paginator" name="grid_paginator" pagerows="50" theme="1" cardnumber="5"></div>');
		g.push('</div>');
		dcdx.append(g.join(''));
	}
	
}
function _r_data(e, d){
	var data = d["data"], uuid = null;
	for(var i=0,len=data.length; i<len; i++){
		uuid = data[i]["rowuuid"];
//		data[i]["rowuuid"] = uuid;
		data[i]["xh"] = i+1;
		data[i]["img"] = "<div class='g-left-img'></div>";
		e.currentClass.rows[i]["rowuuid"] = uuid;
	}
	return data;
}

//通过右侧“已选调查对象”清楚左侧选中
$(document).bind("removeObject", function(e, d){
	for(var i=0,len=window._grid_id.length; i<len; i++){
		for(var j=0,k=d.length; j<k; j++){
			$("#"+window._grid_id[i]).gridpanel("unselectRowById", d[j]);
		}
	}
});

function isKeyResultColumn(columnVariables){
	var html = [];
	html.push("<div>");
	html.push("<div name='img' text='' textalign='center' width='26'></div>");
	html.push("<div name='xh'  text='序号' textalign='center' width='38'></div>");
	for(var i=0, len=columnVariables.length; i<len; i++){
		var col = columnVariables[i];
		if(col['codelistId']){
			html.push("<div name='"+col["columnName"]+"' text='"+col["variableName"]+"' textalign='center' width='140' dataurl='" + (rootPath + "/dictionary/queryData.do?dicId=" + col['codelistId']) + "'></div>");
		}else{
			html.push("<div name='"+col["columnName"]+"' text='"+col["variableName"]+"' textalign='center' width='140'></div>");
		}
	}
	html.push("</div>");
	return html.join("");
}

var curgriddata = [];
function initGridColumn(id, variables, objectId, objectName, isClickDcObject){
	var _id = "#"+id+"_grid", _column = "#"+id+"_column";
	window._mask_grid = false;
	var param = {
    	url: rootPath+"/soweb/getSoKeyResult.do?random="+Math.random(),
    	params: {objectId: objectId},
        callback: function(data){
        	$(_id).gridpanel('destroyComp');
        	$(_column, $(_id)).children().remove();
        	$(_column, $(_id)).append(isKeyResultColumn(data["data"]));
        	$(_id).parseComponent();
        	
        	if(isClickDcObject){
        		datalist(id, objectId, objectName, variables, false);	
        	}else{
        		datalist(id, objectId, objectName, variables, true);
        	}
        	
        	$(_id).attr("objectId", objectId);
        	
            $(_id).gridpanel("option", "rowselect", function(e, data){
            	window.addObject({object: data, variables: variables, objectId: objectId, objectName: objectName});
            });
            
            $(_id).gridpanel("option", "rowunselect", function(e, data){
            	window.removeObject({object: data, variables: variables});
            });

            $(_id).gridpanel("option", "selectall", function(e, ui){
            	var data = ui.data;
            	for(var i=0,len=data.length; i<len; i++){
            		window.addObject({object: data[i], variables: variables, objectId: objectId, objectName: objectName});            		
            	}
            });
            
            $(_id).gridpanel("option", "unselectall", function(e, ui){
            	var data = ui.data;
            	for(var i=0,len=data.length; i<len; i++){
            		window.removeObject({object: data[i], variables: variables});            		
            	}
            });            
            
            $(_id).gridpanel("option", "paginatorcallback", function(a){
            	//showLoading();
//            	_datCallBackVariable(objectId, _id);
//            	
            	if(_id.substring(1,_id.length)==window.gridId){
            		window.gridId="";
            		$(_id).gridpanel("unselectAllRows");
            	}else{
            		$(_id).gridpanel("selectAllRows");
            	}
            
            	window.objectCount++;
            	getPageData(window.objectCount);
            	window.closeHt();
            });
            
            window._mask_grid = true;
            if(window._mask_condition && window._mask_grid && window._mask_datalist){
            	if(window.__getObjectType == "changeTab"){
            		//window.hideLoading();
            	}
        	}
        }
    };
    $.DataAdapter.submit(param);
}
/***  查询调查对象内容  ***/
function datalist(id, objectId, objectName, variables, init){
	gridDataNumbers++;
	var con = [];
	
	window._mask_datalist = false;
	
	$.each($("#"+id).find(".dcdx-data-tj"), function(){
		con.push({columnName: $(this).attr("columnname") || "", value: $(this).attr("columnvalue") || ""});
	});
	
	var _id = "#"+id+"_grid";
    
    $(_id).gridpanel("option", "dataurlparams",  {objectId: objectId, variables: variables, condition: con});
    $(_id).gridpanel("option", "dataurl",  rootPath+"/soweb/getSoData.do?random="+Math.random());
    $(_id).gridpanel("reload", null, function(){
    	if(init){
    		//数据回显
    		_dataCallBackVariable(objectId, _id);
    	}
    	
    	$(_id).gridpanel("selectAllRows");
    	
    	datalist_num++;
    	
    	window._mask_datalist = true;
    	gridDataNumbers--;
    	if(window._mask_condition && window._mask_grid && window._mask_datalist){
    		//console.log("datalist-gridDataNumbers:"+gridDataNumbers);
        	if(window.__getObjectType == "changeTab"){
        		//window.hideLoading();
        	}
    	}
    });
}

function datalistFilter(id, objectId, objectName, variables,con, init){
	//console.log(gridDataNumbers);
	window._mask_datalist = false;
	gridDataNumbers++;
	var _id = "#"+id+"_grid";
    
    $(_id).gridpanel("option", "dataurlparams",  {objectId: objectId, variables: variables, condition: con});
    $(_id).gridpanel("option", "dataurl",  rootPath+"/soweb/getSoData.do?random="+Math.random());
    $(_id).gridpanel("reload", null, function(){
    	if(init){
    		//数据回显
    		_dataCallBackVariable(objectId, _id);
    	}
    	
    	$(_id).gridpanel("selectAllRows");
    	
    	window._mask_datalist = true;
    	gridDataNumbers--;
    	if(window._mask_condition && window._mask_grid && window._mask_datalist){
    		//console.log(gridDataNumbers);
        	if(window.__getObjectType == "changeTab"){
        		//window.hideLoading();
        	}
    	}
    });
}
/*** 数据回显 ***/
function _dataCallBackVariable(objectId, gridId){
	var obj = null;
	for(var p in window.coreInfo){
		if(window.coreInfo[p]["objectId"] == objectId){
			obj = window.coreInfo[p]["object"];
			for(var j in obj){
				$(gridId).gridpanel("selectRowById", j);
			}
		}
	}
}

/*** 筛选条件时生成的组件 ***/
function textfieldComponent(vtype, $this, tabcontent, id, objectId, objectName, variables){
	var list = null, comp = null, compName = null;
	if($this.data("cxlist")){
		list = $this.data("cxlist");
		compName = $this.data("compName");
		comp = list.find('div[name="'+compName+'"]');
	}else{
		list = $('<div class="dcdx-con-list"><div class="dcdx-con-comp"></div></div>').appendTo("body");
		compName = 'tj_' + jazz.util.getRandom();
		$this.data("cxlist", list);
		$this.data("compName", compName);
		var cobj = list.children(".dcdx-con-comp");
		comp = $('<div vtype="'+vtype+'" name="'+compName+'" width="200" height="26"></div>').appendTo(cobj);
		cobj.append('<div class="dcdx-btn-qd dcdx-btn-qd2 jazz-inline"></div>');
		comp.parseComponent();
	}
	function _ctrdata(){
		var value = comp[vtype]("getValue");
		if(value || value=="0"){		
			var search = tabcontent.find(".dcdx-search");
			var bj = search.find("#"+compName);
			var condition = $this.data("condition");
			if(bj.length == 0){
				search.append('<div id="'+compName+'" columnname="'+condition["columnName"]+'" class="dcdx-data-tj jazz-inline"><div class="dcdx-data-font"></div><div class="dcdx-font-img"></div></div>');
			}
			search.find(".dcdx-font-img").off(".dcdx_img").on("click.dcdx_img", function(){
				$("#"+compName).remove();
				//刷新表格
				datalist(id, objectId, objectName, variables);
			});
			$("#"+compName).attr("columnvalue", value+"%");
			list.hide();
			$("#"+compName).find(".dcdx-data-font").empty().append(condition["variableName"]+": "+value);
			
			//刷新表格
			datalist(id, objectId, objectName, variables);
		}else{
			list.hide();
		}		
	}
	$("div[name='"+compName+"']").textfield("option", "keyup", function(e){
		if(e.keyCode == 13){
			_ctrdata();
		}
	});
	list.find(".dcdx-btn-qd").off("click").on("click", function(){
		_ctrdata();
	});	        					
//	list.find(".dcdx-btn-qx").off("click").on("click", function(){
//		list.hide();
//	});
	list.off("mousedown").on("mousedown", function(e){
		e.stopPropagation();
	});
	var offset = $this.offset(), left = offset.left+$this.outerWidth(), top = offset.top;
	list.css({left: left, top: top}).show();	
}
function doublefieldComponent(vtype, $this, tabcontent, id, objectId, objectName, variables){
	var list = null, comp = null, compName = null;
	if($this.data("cxlist")){
		list = $this.data("cxlist");
		compName = $this.data("compName");
		comp = list.find('div[name="'+compName+'"]');
		comp2 = list.find('div[name="'+compName+'_2"]');
	}else{
		list = $('<div class="dcdx-con-list"><div class="dcdx-con-comp"></div></div>').appendTo("body");
		compName = 'tj_' + jazz.util.getRandom();
		$this.data("cxlist", list);
		$this.data("compName", compName);
		var cobj = list.children(".dcdx-con-comp");
		comp = $('<div vtype="'+vtype+'" name="'+compName+'" width="80" labelwidth="30" height="30" class="jazz-inline"></div>').appendTo(cobj);
		comp2 = $('<div vtype="'+vtype+'" name="'+compName+'_2" label="～" labelsplit="" width="110" labelwidth="25" height="30" class="jazz-inline"></div>').appendTo(cobj);
		cobj.append('<div class="dcdx-btn-qd dcdx-btn-qd2 jazz-inline"></div>');
		//cobj.append('<div class="dcdx-con-btn-wrap"><div class="dcdx-con-btn"><div class="dcdx-btn-qd jazz-inline">确定</div><div class="dcdx-btn-qx jazz-inline">取消</div></div></div>');
		comp.parseComponent();
		comp2.parseComponent();
	}
	list.find(".dcdx-btn-qd").off("click").on("click", function(){
		var value = comp[vtype]("getValue"), value2 = comp2[vtype]("getValue");        						
		if(value && value2 || value==0 || value2==0){
			var search = tabcontent.find(".dcdx-search");
			var bj = search.find("#"+compName);
			var condition = $this.data("condition");
			if(bj.length == 0){
				search.append('<div id="'+compName+'" columnname="'+condition["columnName"]+'" class="dcdx-data-tj jazz-inline"><div class="dcdx-data-font"></div><div class="dcdx-font-img"></div></div>');
			}
			search.find(".dcdx-font-img").off(".dcdx_img").on("click.dcdx_img", function(){
				$("#"+compName).remove();
				//刷新表格
				datalist(id, objectId, objectName, variables);
			});
			$("#"+compName).attr("columnvalue", value + "~" + value2);
			list.hide();
			$("#"+compName).find(".dcdx-data-font").empty().append(condition["variableName"]+": "+value + "～" + value2);
			
			//刷新表格
			datalist(id, objectId, objectName, variables);
		}else{
			list.hide();
		}
	});       					
//	list.find(".dcdx-btn-qx").off("click").on("click", function(){
//		list.hide();
//	});
	list.off("mousedown").on("mousedown", function(e){
		e.stopPropagation();
	});
	var offset = $this.offset(), left = offset.left+$this.outerWidth(), top = offset.top;
	list.css({left: left, top: top}).show();		
}

function treeComponent(vtype, $this, tabcontent, id, objectId, objectName, variables, codeList){
	var list = null, comp = null, compName = null;
	var nodeValue = "", nodeText = "";
	if($this.data("cxlist")){
		list = $this.data("cxlist");
		compName = $this.data("compName");
		comp = list.find('#'+compName+"_tree");
		list.find(".dcdx-btn-qd").off("click").on("click", function(){
			var columnvalue = comp.attr("nodevalue"); 
			if(columnvalue || columnvalue=="0"){
				var search = tabcontent.find(".dcdx-search");
				var bj = search.find("#"+compName);
				var condition = $this.data("condition");
				if(bj.length == 0){
					search.append('<div id="'+compName+'" columnname="'+condition["columnName"]+'" class="dcdx-data-tj jazz-inline"><div class="dcdx-data-font"></div><div class="dcdx-font-img"></div></div>');
				}
				search.find(".dcdx-font-img").off(".dcdx_img").on("click.dcdx_img", function(){
					$("#"+compName).remove();
					//刷新表格
					datalist(id, objectId, objectName, variables);
				});
				$("#"+compName).attr("columnvalue", columnvalue||"");
				list.hide();
				
				$("#"+compName).find(".dcdx-data-font").empty().append(condition["variableName"]+": "+comp.attr("nodetext")||"");
				
				//刷新表格
				datalist(id, objectId, objectName, variables);
			}else{
				$("#"+compName).remove();
				list.hide();
				//刷新表格
				datalist(id, objectId, objectName, variables);			
			}
		});	        					
		list.find(".dcdx-btn-qx").off("click").on("click", function(){
			list.hide();
		});
		list.off("mousedown").on("mousedown", function(e){
			e.stopPropagation();
		});
		var offset = $this.offset(), left = offset.left+$this.outerWidth(), top = offset.top;
		var winheight = $(window).height() || document.body.clientHeight;
		if(top + list.outerHeight() > winheight + $(document).scrollTop()){
			top = winheight + $(document).scrollTop() - list.outerHeight() - 5;
		}	
		list.css({left: left, top: top}).show();	
	}else{
		var variableId = $this.attr("id");
		var params = {
			url : rootPath+'/soweb/getSoColCodeList.do?objectId='+objectId,
			callback : function(data, r, res) {
				for(var i = 0, j = data.data.length; i < j; i++){
					if(data.data[i][variableId]){
						codeList = data.data[i][variableId];
						break;
					}
				}
				hideLoading();
				list = $('<div class="dcdx-con-list"><div class="dcdx-con-comp"></div></div>').appendTo("body");
				compName = 'tj_' + jazz.util.getRandom();
				$this.data("cxlist", list);
				$this.data("compName", compName);
				var cobj = list.children(".dcdx-con-comp");
				comp = $('<div class="ztree dcdx-con-tree" id="'+compName+'_tree"></div>').appendTo(cobj);
				cobj.append('<div class="dcdx-con-btn-wrap"><div class="dcdx-con-btn"><div class="dcdx-btn-qd jazz-inline">确定</div><div class="dcdx-btn-qx jazz-inline">取消</div></div></div>');

				var _setting = {
					view : {
						dblClickExpand : false
					},
					check: {
						enable: true,
						chkboxType : { "Y" : "", "N" : "" }
					},
					data : {
						simpleData : {
							enable : true
						}
					},
					callback : {
						onCheck:function(event, treeId, treeNode){
							nodeText = "", nodeValue = "";
							var treeObj = $.fn.zTree.getZTreeObj(compName+"_tree");
							var nodes = treeObj.getCheckedNodes(true);
							var node = null;
							for(var i = 0, j = nodes.length; i < j; i++){
								node = nodes[i];
								if(i==0){
									nodeValue = node.value; nodeText = node.text;		
								}else{
									nodeValue += "," + node.value; nodeText += "," + node.text;
								}
							}
							comp.attr("nodetext", nodeText);
							comp.attr("nodevalue", nodeValue);
						}
					}
				};
				$.fn.zTree.init($("#"+compName+"_tree"), _setting, codeList);
				list.find(".dcdx-btn-qd").off("click").on("click", function(){
					var columnvalue = comp.attr("nodevalue"); 
					if(columnvalue || columnvalue=="0"){
						var search = tabcontent.find(".dcdx-search");
						var bj = search.find("#"+compName);
						var condition = $this.data("condition");
						if(bj.length == 0){
							search.append('<div id="'+compName+'" columnname="'+condition["columnName"]+'" class="dcdx-data-tj jazz-inline"><div class="dcdx-data-font"></div><div class="dcdx-font-img"></div></div>');
						}
						search.find(".dcdx-font-img").off(".dcdx_img").on("click.dcdx_img", function(){
							$("#"+compName).remove();
							//刷新表格
							datalist(id, objectId, objectName, variables);
						});
						$("#"+compName).attr("columnvalue", columnvalue||"");
						list.hide();
						
						$("#"+compName).find(".dcdx-data-font").empty().append(condition["variableName"]+": "+comp.attr("nodetext")||"");
						
						//刷新表格
						datalist(id, objectId, objectName, variables);
					}else{
						$("#"+compName).remove();
						list.hide();
						//刷新表格
						datalist(id, objectId, objectName, variables);			
					}
				});	        					
				list.find(".dcdx-btn-qx").off("click").on("click", function(){
					list.hide();
				});
				list.off("mousedown").on("mousedown", function(e){
					e.stopPropagation();
				});
				var offset = $this.offset(), left = offset.left+$this.outerWidth(), top = offset.top;
				var winheight = $(window).height() || document.body.clientHeight;
				if(top + list.outerHeight() > winheight + $(document).scrollTop()){
					top = winheight + $(document).scrollTop() - list.outerHeight() - 5;
				}	
				list.css({left: left, top: top}).show();	
			}
		};
		showLoading();
		$.DataAdapter.submit(params);
		
	}
}
window.gridDataNumbers = 0;

/** 调查对象下的筛选对象 **/
function filterCondition(id, objectId, objectName, variables, isClickDcObject){
	if(objectId){
	    //初始化时，筛选条件对应的左侧grid数据
		initGridColumn(id, variables, objectId, objectName, isClickDcObject);
		
		window._mask_condition = false;
		
		var tabcontent = $("#"+id), sxtj = tabcontent.find(".dcdx-tj-auto");
		
	    var param = {
	    	url: rootPath+"/soweb/getSoCase.do?random="+Math.random(),
	    	params: {objectId: objectId},
	        callback: function(data){
	            var newdata = data["data"];
	        	if(newdata.length > 0){
	        		var logicStorageId = "", bgqb = [];
	        		for(var i=0,len=variables.length; i<len; i++){
	        			logicStorageId = variables[i]["logicStorageId"];
	        			bgqb.push(variables[i]["bgqb"]);
	        		}
	        		newdata[0]["bgqb"] = bgqb;
	        		newdata[0]["logicStorageId"] = logicStorageId;
	        		window._filterCondition.push(newdata);
	        		
	        		sx_condition_numm++;
					
	        	}
	        	window._mask_condition = true;
	        	if(window._mask_condition && window._mask_grid && window._mask_datalist){
	            	if(window.__getObjectType == "changeTab"){
	            		window.hideLoading();
	            	}
	        	}
	        }
	    };
	
	   $.DataAdapter.submit(param);		
	}
}

/** 新建调查对象tab页面 **/
function newSoTab(objectName, objectId, variables, isClickDcObject){
    var i = $('#tab_dcdx').tabpanel('getLength'),
    randomid = jazz.util.getRandom();
    option = {
      tabid: randomid,       //tab页的id
      tabtitle: objectName,  //tab页的标题 
      tabindex: i,           //tab的索引位置 
      tabtitlewidth: 130,
      taburl: null,          //tab页的url 
      tabcontent: '<div class="dcdx-wrap"><div class="dcdx-tj"><div class="dcdx-tj-auto"></div></div><div class="dcdx-data-wrap"><div class="dcdx-data"><div class="dcdx-search"></div></div></div></div>' //tab页的内容 
    };
    $('#tab_dcdx').tabpanel('addTab', option);
    
    var id = "jazz_tabpanel_custom_" + randomid;

    //初始化左侧调查对象中的grid
    initObjectGrid(id);

    $("#tab_dcdx").parseComponent();

    //调用筛选对象
    gridId2objectIdMap[id] = {
		objectId: objectId,
		objectName: objectName,
		variables: variables
    }
    filterCondition(id, objectId, objectName, variables, isClickDcObject);
}

/** 查询调查对象 **/
function findWqSo(isClickDcObject){
	var yxobj = window.getDataByParam();
	if($.isArray(yxobj)){
	    var param = {
	    	url: rootPath+"/soweb/getSurveyObject.do?random="+Math.random(),
	    	params: {params: yxobj},
	        callback: function(data){
	            var newdata = data["data"];
	        	if(newdata.length > 0){
	        		var item = null, so_object_num = newdata.length;
	        		
	        		//判断调查对象选择中的tab页数量，如果存在则将存在的tab页面删除
	        		var tablen = $("#tab_dcdx").tabpanel("getLength");
	        		for(var i=0, len=tablen; i<len; i++){
	        			$("#tab_dcdx").tabpanel("remove", 0);
	        		}
	        		
	        		window._filterCondition = [];
	        		window._filterObject = {};
	        		window._grid_id = [];

    	    		sx_condition_numm = 0;
    	    		datalist_num = 0;
    	    		
	        		for(var i=0, len=newdata.length; i<len; i++){
	        			item = newdata[i];
	        			newSoTab(item["objectName"], item["objectId"], item["variables"], isClickDcObject);
	        			var o_id = item["objectId"];
	        			window._filterObject[o_id] = item["variables"];
	        		}
	        	    //默认选 中第一个调查对象
	        	    $("#tab_dcdx").tabpanel("select", 0);
	        	    
	        	    //抛出事件加载完后的事件
	        	    var _timeout = setInterval(function(){
	        	    	//console.log("so_object_num="+so_object_num+" sx_condition_numm="+sx_condition_numm+" datalist_num="+datalist_num);
	        	    	if(so_object_num == sx_condition_numm && so_object_num == datalist_num && so_object_num!=0){
	        	    		sx_condition_numm = 0;
	        	    		datalist_num = 0;
	        	    		coreGridPage();
	        	    		clearInterval(_timeout);
	        	    		$(document).trigger("conditionData");
	        	    	}
	        	    }, 200);
	        	    
	        	}
	        }
	    };
	    $.DataAdapter.submit(param);
	}else{
	}
}

/** 返回首页 **/
function first() {
	window.location.href = rootPath + "/page/webquery/index.html";
}

/*** 退出系统 ***/
function quit() {
	var params = {
		url : rootPath+'/admin/quit.do',
		callback : function(data, r, res) {
			window.location.href = rootPath + "/page/systemmanager/admin/login.html";
		}
	};
	$.DataAdapter.submit(params);
}

function _dataload(flag){
	showLoading();
	
	if(flag){
		window.isGetObjectGridHeadInfo = false;
	}	
	//查询调查对象中的数据
	findWqSo(flag);
}

window.getGridIdByObjectId = function (objectId){
	for(var id in gridId2objectIdMap){
		if(gridId2objectIdMap[id]['objectId']==objectId){
			return id;
		}
	}
}

/* 强制向Object对象中回填数据 */


function addObjectForced(){
	for(var id in gridId2objectIdMap){
		var flag = true;
		var objectId = gridId2objectIdMap[id]['objectId'];
		var objectName = gridId2objectIdMap[id]['objectName'];
		var variables = gridId2objectIdMap[id]['variables'];
		for(var p in window.coreInfo){
			if(window.coreInfo[p]["objectId"] == objectId && !jazz.isEmptyObject(window.coreInfo[p]["object"])){
				flag = false;
				break;
			}
		}
		if(flag){
	    	var len = $("#"+id).find("[vtype=gridpanel]").gridpanel("getRowLength");
	    	if(len > 5){
	    		len = 5;
	        }
	    	var row = $("#"+id).find("[vtype=gridpanel]").gridpanel("getAllData");
	    	for(var i=0,j=len; i<j; i++){
	    		$("#"+id).find("[vtype=gridpanel]").gridpanel("selectRowById", row[i]['rowuuid']);
	    	}
	    	curgriddata = $("#"+id).find("[vtype=gridpanel]").gridpanel("getSelectedRowData");  				
			window.addObject({object: curgriddata, variables: variables, objectId: objectId, objectName: objectName, isChecked: true});
		}
		
	}
	
}

function _createCoreGrid(){
	//showLoading();
	//获取已选择的变量数据
	var selectParams = $("#paramGrid").gridpanel('getSelectedRowData');
	var selectObjects = [];
	$("#tab_dcdx").find("div[vtype=gridpanel]").each(function(){
		$(this).gridpanel("selectAllRows");
		var objectId = $(this).attr("objectId");
		var arr = $(this).gridpanel("getSelectedRowData");
		for(var i = 0, j = arr.length; i < j; i++){
			arr[i]['objectId'] = objectId;
		}
		selectObjects = selectObjects.concat(arr);
	});
	window.createCoreGrid(selectParams, selectObjects);
}
window.getObject = function(){
	 selectObjectsTimer = setInterval(function(){
		 //console.log("gridDataNumbers:"+gridDataNumbers);
			if(!gridDataNumbers && isGetObjectGridHeadInfo){
				showLoading();
				$("#coreGridPaginator").show();
				_createCoreGrid();
				coreGridPage();
//				$(".jazz-window-modal").hide();
//				$("body").css("overflow","auto");
//				$("body").css("overflow-x","hidden");
//				win1.hide();
				win1.window("close");
				coreGridPage();
			    clearInterval(selectObjectsTimer);
			}
	},500);
};
window.createCoreGridFil = function(){
	var selectObjects = [];
	$("#tab_dcdx").find("div[vtype=gridpanel]").each(function(){
		$(this).gridpanel("selectAllRows");
		var objectId = $(this).attr("objectId");
		var arr = $(this).gridpanel("getSelectedRowData");
		for(var i = 0, j = arr.length; i < j; i++){
			arr[i]['objectId'] = objectId;
		}
		selectObjects = selectObjects.concat(arr);
	});
	return selectObjects;
};
window._createCoreGridTimer;
window.objectFilterFlag = false;
window.isConditionMeasureChange = false;
/*** 提取数据 ***/
function tqsj(){
	isWinReInit = true;
	isWin1ReInit = true;
	if(window.conditionMeasure.length > 0){
		window.conditionMeasure=[];
		isConditionMeasureChange = true;
	}
	parent.closeHt();
	window.pageStart = 0;
	window.objectFilterFlag = true;
	window.isGetObjectGridHeadInfo = false;
	$("#content2_center").css({"background":"#d9d9d9","border":"none"});
	$("#tab_2").css("display","");
	var yxobj = window.getDataByParam();
	if(yxobj.length == 0){
		return false;
	}
		
		//showLoading();
		resizeContent2NorthAndTitleAreaTwo();
		window.__getObjectType = "getData";
	 	_changeTab($("#tab-li-2"), false);		

		_createCoreGridTimer = setInterval(function(){
			//console.log("tqsj-gridDataNumbers:"+gridDataNumbers);
			if(!gridDataNumbers && isGetObjectGridHeadInfo){
				_createCoreGrid();
			 clearInterval(_createCoreGridTimer);
			 /**  画图区域的单选框  **/
			    changeRadioCheck();
			}
		},500);
		if(isDrew){
			ht();
		}
		$("#coreGridPaginatorWrapper").show();
		window.titleAreaWidth=$("#titleArea").width();
}

//=================制造横向滚动条====================

function initScroll(){
	
	var coreGridColumn=getCoreGridColumn();
	var scrollInnerWidth=coreGridColumn.width()+$("#content_west").width()+20;
	$("#scrollOuter").css("width",$(window).width()+"px");
	$("#scrollInner").css("width",scrollInnerWidth+"px");
    if($(window).width()<scrollInnerWidth){
    	$("#scrollWrapper").show();
    }
}

function initTitleMask(){
	
	$("#titleMask").css({"position":"fixed","top":$("#titleArea").height()+"px",
	     "left":($("#content_west").width()+20)+"px",
	     "height":($("div[name='grid_column3']").find("table").height())+"px",
	     "width":($("div[name='grid_column3']").find("div").eq(0).width()+"px"),
	     "overflow":"hidden"
    });  
	
}

//===============滚动条滚动事件=================
$(function(){
	
	$("#scrollOuter").on("scroll",function(){
	 var coreGridTable=$("#coreGrid div[name='grid_table']").find("table").eq(0);
	 var coreGridColumn=getCoreGridColumn();
	 coreGridColumn.css("marginLeft",-($("#scrollOuter").scrollLeft()+10)+"px");
	 coreGridTable.css("marginLeft",-($("#scrollOuter").scrollLeft()+10)+"px");
	});
	
});

function getCoreGridColumn(){
	
	var coreGridColumn;
	
	if(window.popupstate==1){
		
		 coreGridColumn=$("div[name='grid_column3']").find("table");
	}else if(window.popupstate==2){
		
		 coreGridColumn=$("#titleMask").find("table");
	}
	return coreGridColumn;
	
}

function resizeContent2NorthAndTitleAreaTwo(){
	
	var width=$("#content").width()-$("#content_west").width()-$("#content_middle").width()-20;
	$("#content2_north").css("width",width+"px");
	$("#titleArea").css("width",width+2+"px");
	$("#paramGrid").gridpanel("option","width",width);
	
	
}

/***  根据调查对象对数据表格分页  ***/
function coreGridPage(){
	//初始化分页条
	var dcdxObj = $("#tab_dcdx").find("div[vtype='gridpanel']");
	var totalrows = 0;
	dcdxObj.each(function(){
		var rows = $(this).gridpanel('getPaginationInfo')['totalrows'];
		if(rows > totalrows)
			totalrows = rows;
	});
	
//	$("#coreGridPaginator").paginator(option,"totalrecords",totalrows);
//	$("#coreGridPaginator").paginator(option,"pagerows",50);
	window.varibleObj = [];
	$("#coreGridPaginator").paginator({
	 	"gopage":function(page){
	 		newPageClick();
		}
	});
	
	$('#coreGridPaginator').paginator("updatePage", {
        "page": 0,
        "pagerows": 50,
        "totalrecords":totalrows
    });
	window.pageStart = 0;
	
	//newPageClick();
}
/***  给新分页添加点击事件  ***/
function newPageClick(){
	
	showLoading();
	varibleObj = [];
	$("#titleArea").find("div[vtype='comboxfield']").each(function(){
		obj = {name:$(this).attr('name'),value:$(this).comboxfield('getValue')};
		varibleObj.push(obj);

	});
	var page = $("#coreGridPaginator").paginator("option",'page');
	var pagerows = $("#coreGridPaginator").paginator("option",'pagerows');
	window.pageStart = page*pagerows;
	
	$("#tab_dcdx").find("div[vtype='paginator']").each(function(){

		var totalrecords = $(this).paginator('option','totalrecords');
		var pageRows_temp = $(this).paginator('option','pagerows');
		
		if(pageRows_temp!=pagerows){
			if(pagerows>=totalrecords){
				hideLoading();
			}

			$(this).paginator('option','pagerows',pagerows);

			var page_temp = $(this).paginator("option",'page');
			
			if(page_temp<1){
				page_temp=1;
			}else{
				page_temp=0;
			}
			$(this).paginator('setPage',page_temp,false);
		}else{
			var pageAll = $(this).paginator("option",'totalrecords')/$(this).paginator('option','pagerows');
			if(pageAll>=page){
				$(this).paginator('setPage',page,false);
			}else{
				window.gridId = $(this).paginator('getParentComponent').gridpanel().attr("id");
				var temp =  ($(this).paginator("option",'page')+1)%Math.floor(pageAll);
				
				$(this).paginator('setPage',temp,false);
			}
		}


	});
	window.objectCount=0;	
}

/**
 * 点击pagerows时对各个部分的判断
 * 
 * */

function onCoreGridPaginatorClick(){
	
	  var scrollTop=$(window).scrollTop();
	  var criticalHeight=getCriticalHeight();
	  var drawDivHeight=getDrawDivHeight();
	  var width=21+$("#content_west").width();
	  
	  if(scrollTop<criticalHeight){
		 $("#coreGridColumn").children("table").removeClass("table_topleft_popup")
			.addClass("table_topleft_nopopup");
		 $("div[name='grid_column3']").find(".jazz-grid-columns").empty();
	         
	  }else if(scrollTop>criticalHeight){
		   
		    /* $("#titleMask").children("table").removeClass("table_topleft_nopopup")
			.addClass("table_topleft_popup").css({"top":(39+drawDivHeight)+"px","left":width+"px","z-index":"203"});*/
	  }
}

/**
 * 清空变量区的选择
 * 1、清空展示的内容
 * 2、清空缓存相关的内容
 */
function qkxz(){	
	
	//清空缓存的变量信息
	
	$("#paramGrid .jazz-grid-data-table .paramDelete").each(function(){
		var val = $(this).attr("val");
	    var row = $('#paramGrid').gridpanel('getRowDataByParam', "__uuid", val);
	    if(row){
	    	$('#paramGrid').gridpanel('removeRow',row);
	    }
	    paramShowAndHidden();
		$(document).trigger("removeParam", [row]);
	});
	
	//清空展示的内容
	$(".noDataText").show();
	
	count=0;
	$("#paramGrid").hide();
}
var wdzh;
function wdzh(){
	window.showBoxlist();
}


function getPageData(objectCount){
	
	if(objectCount==$("#tab_dcdx").find("div[vtype=gridpanel]").length){
		
		var selectParams = $("#paramGrid").gridpanel('getSelectedRowData');
		var selectObjects = [];
		$("#tab_dcdx").find("div[vtype=gridpanel]").each(function(){
			//$(this).gridpanel("selectAllRows");
			var objectId = $(this).attr("objectId");
			var arr = $(this).gridpanel("getSelectedRowData");
			for(var i = 0, j = arr.length; i < j; i++){
				arr[i]['objectId'] = objectId;
			}
			selectObjects = selectObjects.concat(arr);
		});
		window.createCoreGrid(selectParams, selectObjects,true);
	}
}
var chartEdit;
function editCharts(){
	var type = window.drawInfo.chartType;
	chartEdit=jazz.widget({
		vtype: "window",
		name: "chart",
		frameurl: "editChart.html",
		title: "编辑",
		titlealign: "center",
		titledisplay: true,
		modal: true,
		resizable: true,
		visible: true,
		width: 300,
		height: 420
	});
}

/***  左侧变量树筛选清空  */
function clearCondition(){
	$("#treetext").autocompletecomboxfield("setText","");
	tree_filter();
}




//==============================实现滚动功能代码====================================

$(function(){

	  $(window).on("scroll",function(event){
		 onScroll();
	});
});


//计算drawdiv宽度
function calculateDrawDivWidth(){
	
	 if($("#drawdiv").css("display")!="none"){
		  
		  $("#drawdiv").css("width",($(window).width()-$("#content_west").width()-30)+"px");
		  
	  }
}

/**
 * window滚动事件的事件处理函数 
 * */
function onScroll(){
	
	   var scrollTop=$(window).scrollTop();
	   var scrollLeft=$(window).scrollLeft();
	   var width=21+$("#content_west").width()-scrollLeft;
	   judgeTopLeft(scrollTop,width,scrollLeft);

}




window.popupstate=1;
function judgeTopLeft(scrollTop,width,scrollLeft){
	
	
	var coreGridColumn=$("div[name='grid_column3']").find("table");
	var gridColumn3=$("div[name='grid_column3']");
	var coreGridFirstColumn=$("#coreGridColumn").children("table");
	var height=getCriticalHeight();
	var titleArea=$("#titleArea");
	var titleAreaWrapper=$("#titleAreaWrapper");
	var drawDiv=$("#drawdiv");
	var drawDivHeight=getDrawDivHeight();
	var drawDivWrapper=$("#drawDivWrapper");
	var topWidth=height-scrollTop+37;
    var columnHeight=coreGridColumn.height();
    var titleMask=$("#titleMask");
    
    gridColumn3.css("height",columnHeight);
    
	if(scrollTop>height){
		
		    window.popupstate=2;
			if(!window.flag){
				
				coreGridColumn=$("div[name='grid_column3']").find(".jazz-grid-column-table");
				coreGridColumn.removeClass("table_element_popup");
				titleMask.show()
				.css({"left":width+"px",top:(37+drawDivHeight)+"px","height":$("div[name='grid_column3'] .jazz-grid-columns").height()+"px","width":$("div[name='grid_column3']").find("div").eq(0).width()+"px"})
				.empty()
				.append(coreGridColumn);
				window.flag=true;
			}
			
			calculateDrawDivWidth();
			$("div[name='grid_column3']").find(".jazz-grid-columns").css("height",35+"px");
			titleArea.addClass("table_element_popup")
				.css({"left":width+"px",top:0,"width":($(window).width()-$("#content_west").width()-30)+"px"});
			titleAreaWrapper.css("height","37px");
			drawDiv.addClass("table_element_popup")
				.css({"left":width+"px",top:37+"px"});
			coreGridFirstColumn.removeClass("table_topleft_nopopup")
				.addClass("table_topleft_popup").css({"top":($("#titleArea").height()+drawDivHeight)+"px","left":width+"px","z-index":"204"});
			
			if(window.isCloseHt){
				
				$("#htButton").addClass("table_element_popup");
			}
		
	} else if(scrollTop<=height){
		 
		window.popupstate=1;
		$("div[name='grid_column3']").find(".jazz-grid-columns").css("height","auto");
		titleMask.hide();
		if(window.flag){
			
			coreGridColumn=titleMask.children("table");
			$("div[name='grid_column3']").find(".jazz-grid-columns")
			.empty()
			.append(coreGridColumn);
			window.flag=false;
		}
		drawDiv.removeClass("table_element_popup");
		coreGridFirstColumn.removeClass("table_topleft_popup")
			.addClass("table_topleft_nopopup");
		titleArea.removeClass("table_element_popup");
		titleAreaWrapper.css("height","37px");
		
		if(window.isCloseHt){
			$("#htButton").removeClass("table_element_popup").addClass("htButton");
		}
	}
}

/*function judgeFlag(){
	
	if(!window.flag){
		window.flag=true;
	}else{
		window.flag=false;
	}
	
}*/

function getCriticalHeight(){
	
	var height;	
	height=$(".sy-header").height()+$("#content2_north").height()+25;
	return height;
}

function getDrawDivHeight(){
	 var drawDivWidth;
	 if($("#drawdiv").css("display")=="none"){
		 drawDivHeight=0
	 }else{
		 drawDivHeight=$("#drawdiv").height();
	 }
	 return drawDivHeight;
}	


function getLeftWidth(){
  var leftWidth=$("#content_west").width()+20;
  return leftWidth;	 
	
}


//===========================拖动====================================================== 	

var sliderMoving=false;

//获取发生某事件时的鼠标位置
function mousePosition(event){
	
	if(!event){
		event=window.event;
	}
	if(event.pageX || event.pageY) { 
		
		return { x: event.pageX, y: event.pageY }; 
	} 
}

function sliderGhostMoving(event){
	$("#content_middle1").css({"left":mousePosition(event).x,"display":"block"});
}


function getElCoordinate(dom) { 
	var t = dom.offsetTop; 
	var l = dom.offsetLeft; 
	dom = dom.offsetParent; 
	while (dom) { 
	t += dom.offsetTop; 
	l += dom.offsetLeft; 
	dom = dom.offsetParent; 
	}; 
	return { top: t, left: l }; 
}


function sliderHorizontalMove(event){
   	
  var lWidth = getElCoordinate($("#content_middle1")[0]).left;
  var leftWidth=lWidth+10;
  var rWidth=$("#content").width()-leftWidth-16;
  $("#content_west").css("width", lWidth + "px"); 
  resizeContentCenter(leftWidth,rWidth);
  $("#content_middle1").css("display", "none"); 
  $("#lefttree").show();
  $("#middle_btn").css("left",(lWidth+10)+"px");
  $("#coreGridColumn").children("table").css("left",leftWidth+10+"px");
  $(".content_west_title").css("display","block");
  resizeTitleAreaOne();
  calculateDrawDivWidth();
 
} 

function resizeTitleAreaOne(){
	
	var width=$(".sy-header").width()-$("#content_west").width()-31;
	$("#titleArea").css("width",width+"px");
	$("#coreGridPaginator").css("width",width+"px");
}



function resizeContentCenter(leftWidth,rWidth){
	
	  $("#content_center").css("marginLeft",leftWidth+ "px"); 
	  $("#content_center").css("width", rWidth+"px"); 
	  $("#content2_center").css("width",rWidth+"px"); 
	  $("#content2_north").css("width",rWidth+"px");
	  //改变gridpanel长度的时候使用gridpanel方法。
	  $("#paramGrid").gridpanel("option","width",rWidth);
	  $("#coreGrid").gridpanel("option","width",rWidth);
	  $("#content2_north").css("width",rWidth+"px");
	var gridWidth = $("#coreGrid").find("div[vtype='gridtable']").find("table").eq(0).width();
	var titleWidth = $("#titleArea").width();
	var coreGridPaginator = gridWidth;// < titleWidth?gridWidth:titleWidth;
	$("#coreGridPaginator").css("width",coreGridPaginator+"px");
}

function initContentMiddle(){
	var height=$("#content2_north").height()+15+$(".noData").height();
	$("#content_middle").css("height",height+"px");
	$("#content_middle1").css("height",height+"px");
}

//拖动按钮事件处理函数
function reload(){
	var width=$("#content_west").width();
		if(width!=0){
			$("#content_west").css("width","0");
			$("#middle_btn").css("left",10+"px");
		    $("#content_center").css("marginLeft",10+ "px");
		    
		    var rWidth =$("#content").width()-25;
	       // $("#content_center").css("width", rWidth+"px"); 
	        $("#content2_center").css("width",rWidth+"px"); 
	        $("#content2_north").css("width",rWidth+"px");
	        $("#titleArea").css("width",rWidth+"px");
	        $("#coreGridPaginator").width(rWidth);
	        //改变gridpanel长度的时候使用gridpanel方法。
	        $("#paramGrid").gridpanel("option","width",rWidth);
	        $("#coreGrid").gridpanel("option","width",rWidth);
	        $(".content_west_title").css("display","none");
	        $("#content_west").css({"border":"none"});
	        $("#lefttree").hide();
	        calculateDrawDivWidth();
	  
		}else{
			
			$("#content_west").css("width",350+"px");
			$("#middle_btn").css("left",360 +"px");
		    $("#content_center").css("marginLeft",360+"px");
		    var rWidth=$("#content").width()-375;
	        $("#content_center").css("width", rWidth+"px"); 
	        $("#content2_center").css("width",rWidth+"px"); 
	        $("#content2_north").css("width",rWidth+"px");
	        $("#paramGrid").gridpanel("option","width",rWidth);
	        $("#coreGrid").gridpanel("option","width",rWidth);
	        $("#titleArea").css("width",rWidth+"px");
	        $("#coreGridPaginator").width(rWidth); 
	        $(".content_west_title").css("display","block");
	        $("#content_west").css({"border":"1px #c2c1c1 solid"});
	        $("#lefttree").show();
	        calculateDrawDivWidth();
		}
}

//初始化页面大小
function refreshSize(){
	
	var width=$(window).width()-$("#content_west").width()-16;
	$("#content").width($(window).width()-4);
    $("#content2_north").width(width);        	
    $("#content2_center").width(width);
    $("#content_center").width(width);
}


$(function(){
 
 refreshSize();	
 initContentMiddle();
	 $("#content_middle").on("mousedown",function(event){
		sliderMoving=true; 
	  
	 });
	
	 $("#content").on("mousemove",function(event){
		
		if(sliderMoving){
		   sliderGhostMoving(event)
		}
	});
	
	$("#content_middle1").on("mouseup",function(event){
		
		if(sliderMoving){
			sliderMoving=false;
		}
		sliderHorizontalMove(event); 
	});
});
