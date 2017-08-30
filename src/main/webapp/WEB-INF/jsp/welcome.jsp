<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<script src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/sweetalert2.js"></script>
<script src="${pageContext.request.contextPath}/js/flat-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap-treeview.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/application.js"></script>
<link
	href="${pageContext.request.contextPath}/css/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/sweetalert2.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/flat-ui.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/css/bootstrap-treeview.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/css/font-awesome.min1.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/chartCss.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/verticalMenu.css"
	rel="stylesheet" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/favicon.ico" />
</head>
<body class="skin-black">
	<!-- header logo: style can be found in header.less -->
	<!-- Static navbar -->
	<div class="navbar navbar-inverse navbar-lg navbar-static-top "
		style="margin-bottom: 10px;" role="navigation">
		<div class="container" style="width: 100%">
			<div class="navbar-header">

				<a class="navbar-brand" href="#"><span class="fui-video"
					style="padding-right: 23px; font-size: 25px; color: #afffff;"></span>Robotics
					Monitor and Control System</a>
			</div>
			<div class="navbar-collapse collapse">

				<ul class="nav navbar-nav navbar-right" style="margin-right: 16px;">
					<li><a href="${pageContext.request.contextPath}/logOut"><strong
							style="font-size: 20px;">sign out</strong> <span
							class="fa fa-sign-out"
							style="font-size: 25px; padding-left: 10px;"></span> </a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>




	</div>

	<!--应该是个3 9布局-->
	<div class="container" style="width: 100%">
		<div class="row pbl">
			<div class="col-md-2">
				<!--菜单,菜单里面的内容是由websocket写入的，增加的时候，会给下面的添加，减少的时候，会删除下面的，改变的时候，状态，会修改-->
				<div class="row" style="margin-bottom: -23px;">
					<div class="sidebar-nav">
						<div class="navbar navbar-default" role="navigation">
							<div class="navbar-collapse collapse sidebar-navbar-collapse">
								<ul class="nav navbar-nav">
									<li class="active"><a href="#">Group</a></li>
								</ul>
							</div>
							<!--/.nav-collapse -->
						</div>
					</div>
				</div>

				<div class="row">
					<div class="sidebar-nav">
						<div class="navbar navbar-default" role="navigation">
							<div class="navbar-header">
								<button type="button" class="navbar-toggle"
									data-toggle="collapse" data-target=".sidebar-navbar-collapse">
									<span class="sr-only">Groups</span> <span class="icon-bar"></span>
									<span class="icon-bar"></span> <span class="icon-bar"></span>
								</button>
								<span class="visible-xs navbar-brand">Sidebar menu</span>
							</div>
							<div class="navbar-collapse collapse sidebar-navbar-collapse">
								<ul class="nav navbar-nav groupMenu">
									<!--这里会变得多起来-->
									<li class="groupItemNops"><a href="#" ><i
											class="icon-spinner icon-spin"></i></a></li>
								</ul>
							</div>
							<!--/.nav-collapse -->
						</div>
					</div>
				</div>
				<div class="row" style="margin-top: -15px;">
					<div class="sidebar-nav">
						<div class="navbar navbar-default" role="navigation">
							<div class="navbar-collapse collapse sidebar-navbar-collapse">
								<ul class="nav navbar-nav">
									<li class="addGroupItem" data-toggle="modal" data-target="#addGroupModal" style="text-align: center"><a
										href="#"><span class="fa fa-plus"
											style="padding: 5px; font-size: 20px;"></span></a></li>
								</ul>
							</div>
							<!--/.nav-collapse -->
						</div>
					</div> 

				</div>
			</div>
			<div class="col-md-10">
				<!--底下这个div是用来展示group的详细信息的，里面有每个group的一个tab,group增加和减少会，改变这里面的数量，跳转用data-taglog,根据菜单里面的数据源进行tab的隐藏和显示-->
				<!--统一放到jumbotron里面-->
				<!--刚打开的时候就一个展示的东西就行，然后等内容过来-->
				<div id="tempShowDiv" class="jumbotron"
					style="background-color: #f9f9f9;">
					<div class="container" id="groupContent">
						<div>
							<img src="img/icons/svg/retina.svg" alt="Retina"
								style="margin-left: 45%;">
						</div>
						<h1 class="text-info">Start module tracking and better control your robotics!</h1>
					</div>
				</div>
				<div id="groupShowDiv" class="jumbotron"
					style="padding-right: 0px; padding-left: 0px; background-color: #f9f9f9; display: none">
					<!--最基本的应该是个巨幕，然后在巨幕里面进行-->
					<div class="container" style="width: 100%">
						<!--第一行是两列，2 10-->
						<div class="row pbl ">
							<!--2这里面需要放一个展示groupName和Moudle的块-->
							<div class="col-md-3">
								<!--展示group信息的-->
								<div class="row pbl" style="margin-bottom: -15%;">
									<div class="sidebar-nav">
										<div class="navbar navbar-default" role="navigation">
											<div class="navbar-collapse collapse sidebar-navbar-collapse">
												<ul class="nav navbar-nav" style="text-align: left;">
													<li class="active"><a href="#">Module</a></li>
												</ul>
											</div>
											<!--/.nav-collapse -->
										</div>
									</div>
								</div>
								<div class="row pbl">
									<div class="sidebar-nav" style="text-align: left;">
										<div class="navbar navbar-default" role="navigation">
											<div class="navbar-collapse collapse sidebar-navbar-collapse">
												<ul class="nav navbar-nav moduleMenu">
													<li class="moduleItem" moduleIndex="1" family="" name=""><a
														href="#">SEA snake | XA011</a></li>
													<li class="moduleItem" moduleIndex="2" family="" name=""><a
														href="#">Spare | D021</a></li>

												</ul>
											</div>
											<!--/.nav-collapse -->
										</div>
									</div>
								</div>
							</div>
							<!--这个用来展示,表的，一大堆表的-->
							<div class="col-md-9">
								<!--一共四行-->
								<!--第一行第一列显示小圆点表示灯，第二列显示最近一次的更新时间-->
								<div class="row pbl"
									style="text-align: left;; padding-bottom: 40px;">
									<!-- 显示小圆点，然后显示RGB的值  -->
									<div class="col-md-6">
										<div>
											<span style="color: #e74c3c" class="fa fa-circle led"></span><strong
												class="ledData">[R:243,G:0:B:0]</strong>
										</div>
									</div>
									<!-- 显示最新一次的更新时间  -->
									<div class="col-md-6">
										<div class="feedbackTimeDiv">
											LastFeedBack: <strong>2017-08-23 23:59:59</strong>
										</div>
									</div>
								</div>
								<div class="row pbl">
									<!-- 显示两个图表，各占一半 -->
									<!-- 电压 电流 -->
									<div class="col-md-6 datazone">
										<!-- 表明是电压表 -->
										<div class="row pbl"> 
											<div class="col-md-6">
												<label class="checkbox" for="voltage-switch"> <input
													type="checkbox" class="voltage-switch chartsWitch"
													data-toggle="checkbox" value="" id="voltage-switch">
													Voltage
												</label>
											</div>
										</div>
										<div class="row pbl">
											<div class="voltageCharts dataChart"></div>
											<div class="dataShow voltageDataShow" style="display: none"></div>
										</div>
									</div>
									<div class="col-md-6 datazone">
										<!-- 表明是电流表 -->
										<!-- 开关 -->
										<div class="row pbl">
											<div class="col-md-6">
												<label class="checkbox" for="current-switch"> <input
													type="checkbox" class="current-switch chartsWitch"
													data-toggle="checkbox" value="" id="current-switch">
													Current
												</label>
											</div>
										</div>
										<div class="row pbl">
											<div class="currentCharts dataChart"></div>
											<div class="dataShow currentDataShow" style="display: none"></div>
										</div>
									</div>

								</div>
								<div class="row pbl">
									<!-- 速度 扭矩 -->
									<div class="col-md-6 datazone">
										<!-- 表明是速度表 -->
										<!-- 开关 -->

										<div class="row pbl">
											<div class="col-md-6">
												<label class="checkbox" for="velocity-switch"> <input
													type="checkbox" class="velocity-switch chartsWitch"
													data-toggle="checkbox" value="" id="velocity-switch">
													Velocity
												</label>
											</div>
										</div>
										<div class="row pbl">
											<div class="velocityCharts dataChart"></div>
											<div class="dataShow velocityDataShow" style="display: none"></div>
										</div>

									</div>
									<div class="col-md-6 datazone">
										<!-- 表明是扭矩表 -->
										<!-- 开关 -->
										<div class="row pbl">
											<div class="col-md-6">

												<label class="checkbox" for="torque-switch"> <input
													type="checkbox" class="torque-switch chartsWitch"
													data-toggle="checkbox" value="" id="torque-switch">
													Torque
												</label>
											</div>
										</div>
										<div class="row pbl">
											<div class="torqueCharts dataChart"></div>
											<div class="dataShow torqueDataShow" style="display: none"></div>
										</div>


									</div>

								</div>
								<div class="row pbl">
									<!-- 只有一个图表在这里，不过为了整齐 -->
									<!-- 位置 -->
									<div class="col-md-6 datazone">
										<!-- 表明是位置表 -->
										<!-- 开关 -->
										<div class="row pbl">
											<div class="col-md-6">

												<label class="checkbox" for="position-switch"> <input
													type="checkbox" class="position-switch chartsWitch"
													data-toggle="checkbox" value="" id="position-switch">
													Position
												</label>
											</div>
										</div>
										<div class="row pbl">
											<div class="positionCharts dataChart"></div>
											<div class="dataShow positionDataShow" style="display: none"></div>
										</div>
									</div>
									<div class="col-md-6 datazone"></div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="addGroupModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">RMCS</button>
					<h4 class="modal-title" id="myModalLabel">Add Group</h4>
				</div>
				<div class="desNote"> 1.chose the moudles the group has!</div>
				<div class="modal-body familyMap" id="familyMap">
					<!--将会在这里出现个树的结构-->
				</div>
				<div class="desNote"> 2.input the group name ,it can't contain '_' or any space!,then you can submit</div>
				<div class="inputDiv">
						<input type="text" class="form-control gnameInput" placeholder="input groupName" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default addGroupCancelButton" data-dismiss="modal">close
					</button>
					<button type="button" class="btn btn-primary addGroupButton">submit</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>



</body>
<script>
	//全局性的变量
	//这里面有所有的东西了
	var state = {
		nowGroup : null, //字符串，当前group的名字
		nowModuleIndex : -1,//在group里面的偏移
		groupChange : 0,
		familyMap:null,
	};
	var websokcets = {
		g_sock : null,
		fd_sock : null

	};
	var chartsObj = {
		voltage : null,
		current : null,
		velocity : null,
		torque : null,
		position : null
	};
	var opts = {
		title : {
			text : 'data',
			left : "3%",
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ 'value' ]
		},
		xAxis : {
			type : "time",
			name : "time",
		},
		yAxis : {
			name : "value",
		},
		dataZoom : [ {
			start:95,
			end:100
		} ],
		series : [ {
			name : 'value',
			type : 'line',
			data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
					[ 1503564706152, 6 ], [ 1503564707152, 8 ],
					[ 1503564708152, 10 ], ]
		} ]
	};
	var groupMap = [];

	groupMap["group1"] = {
		groupName : "group1",
		modules : [ {
			family : "SEA snake",
			name : "A011",
			connected : true,
		}, {
			family : "Spare",
			name : "D011",
			connected : true,
		} ],//数组，每个成员是个对象，有module的信息
		fdList : [ {//list，兼顾数据传输，,成员是对象，对象的5个字段是数组1个是对象，数组里面又是对象，对象里面是时间戳和值
			current : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			position : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			velocity : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			torque : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			voltage : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			led_fields : [ {
				led_R : 145,
				led_G : 0,
				led_B : 0
			} ]
		}, {
			current : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			position : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			velocity : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			torque : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			voltage : [ {
				timeStamp : 124153647474,
				value : 4.6
			}, {
				timeStamp : 124153647474,
				value : 4.6
			} ],
			led_fields : [{
				led_R : 145,
				led_G : 0,
				led_B : 0
			}]
		}, ],
	};
	window.onbeforeunload = function checkLeave(e) {
	
		if (websokcets.g_sock != null && websokcets.g_sock != undefined)
			websokcets.g_sock.close();
		if (websokcets.fd_sock != null && websokcets.fd_sock != undefined)
			websokcets.fd_sock.close();
	}

	$(document).ready(function() {

		//创建图表
		creatCharts();

		//给开关设置绑定时间
		$(".chartsWitch").bind("click", function(e) {
			onClick_switch($(this));
		});
		//添加group按钮绑定事件

		$(".addGroupItem").bind("click", function(e) {
				getFamilyAndItNames();//去访问服务器，获得familyMap
		});
		//添加 group submit绑定事件
		$(".addGroupButton").bind("click", function(e) {
			sumitGroupToAdd();//1.抽取树内容，2 . 去访问服务器，3. 获得提交结果
		});
		$(".addGroupCancelButton").bind("click", function(e) {
			closeGroupAddingModal();//销毁树
		});


		//建立socket
		establishGroupSocket();
		establishFeedbackSocket();
		//正确设置div显示【这个可以静态设置的】

	});
	function getFamilyAndItNames(){
		//从服务器获得family和它的names的对象 {list:[{name:"",nameList:["","",""]}]}
		//之前的树没销毁，就把他销毁掉
		if(state.familyMap !=null)$("#familyMap").treeview("remove");
		$.ajax({
				url:"${pageContext.request.contextPath}/getFamilyAndItNames", //获取family和names
				type:"POST",
				data:{},
				dataType:"JSON",
				beforeSend:function(){
					//送之前可以显示一句话
					var tempDiv=$('<div><i class="icon-spinner icon-spin"></i><strong>please wait to get modules</strong></div>')
					$(".familyMap").empty();//清空
					//然后坠上
					$(".familyMap").append(tempDiv);
				},
				success:function(data){
					//这个data就是json格式的，现在把他转换成一个树,坠在上面
					changeFamilyAndItNamesMapToTree(data);
				},
				error:function(e){
						//出错了，应该关闭
						swal({
								title: "Sorry",
								text: "you can't add group,please notice the admin!",
								showCancelButton: false,
								type: "error",
								confirmButtonText: "ok",
								closeOnConfirm: false,
							}).then(
							function(){
							//1.关闭modal,2 销毁familyMap树
								closeGroupAddingModal();
							});
				}


		});
	}
	function changeFamilyAndItNamesMapToTree(data){
		//转换成树，然后坠上去
		 var list=data.list;
		 var treeOpt=[];
		 for(var i=0;i<list.length;i++){
			var fNode={};
			fNode.text=list[i].name;//根节点是name
			fNode.nodeId=list[i].name;//根节点是name
			fNode.selectable=false;//不让选根
			fNode.nodes=[];
			var nameList=list[i].nameList; //字符串的数组
			for(var j=0;j<nameList.length;j++){
				fNode.nodes.push({text:nameList[j],nodeId:nameList[j]})
			}
			treeOpt.push(fNode); //把这个节点放进去
		}
		//清空树在的div
		$("#familyMap").empty();
		//显示树
		$("#familyMap").treeview({
			data:treeOpt,
			multiSelect:true,
			levels:5,
		});

		state.familyMap="tree";

	}

	function sumitGroupToAdd(){
		//提交group
		if(state.familyMap == null){
			swal("sorry!","you must choose some modules!","error");
		}else {
			//先判断输入group的名字合不合法
			if(checkGroupName() == false){
				swal("Sorry","the name cannot contain the '_' or any space","error");
				return ;
			}
			var list=	$('#familyMap').treeview('getSelected');
			if(list.length<0){
					//没有选,
				swal("Sorry","you must chose one module !","error");
				return ;
			}
			//从树种获取所有选择的节点
			//转换成一个json结构，
			var json=changeToGroupJsonObject(list);
			//访问服务器
			$.ajax({
				url:"${pageContext.request.contextPath}/addGroup", //获取family和names
				type:"POST",
				data:JSON.stringify(json),
				dataType:"JSON",
				contentType:"application/json",
				success:function(data){
					if(data.result == false || data.result == "false"){
						swal("Sorry",data.des,"error");

					}else {

					 	swal("Ok!",data.des,"success");
						//关闭模态框触发，
						$('#addGroupModal').modal('hide');
					}
				},
				error:function(e){
					swal("Sorry","add group failed","error");
				}
			});

			//用弹出框展示结果

		}
	}
	function checkGroupName(){
		var value=$(".gnameInput").eq(0).val();
		value=value.replace(/\s+/g, "");
		if(value.indexOf("_")!=-1 || value =="" || value.indexOf(" ")!=-1 ){
			return false;
		}
		return true;
	}
	function changeToGroupJsonObject(list){
		//吧树内容转换成json结构

		//先抽取所有父节点，

		var familyKey=[];//family的字典
		//选了的，一定是子节点，去找他的父节点，然后保存成json
		for(var i=0;i<list.length;i++){
				var familyNode= $('#familyMap').treeview('getParent', list[i]); //找到family所在的节点
				if(familyKey[familyNode.text] ==null || familyKey[familyNode.text] ==undefined){
						//这个字典创建一个对象
					familyKey[familyNode.text]={
							nameList:[]
					};
					//push进来
					familyKey[familyNode.text].nameList.push({
							name:list[i].text,
							connected:0,//未连接
					});
				}else{
					//已经有了
					//push进来
					familyKey[familyNode.text].nameList.push({
							name:list[i].text,
							connected:0,//未连接
					});
				}
		}
		//开始构建json对象
		var json={
			name:$(".gnameInput").eq(0).val(),
			familyList:[]

		}
		for(var key in familyKey){
				//
				var fstruct={
					name:key,
					nameList:[]
				}
				for(var i=0;i<familyKey[key].nameList.length;i++){
						//这里面的每个都push 到naeList里面去
					fstruct.nameList.push(familyKey[key].nameList[i]);
				}
				json.familyList.push(fstruct);
		}
		return json;//返回组建好的
	}
	function closeGroupAddingModal(){
		//关闭模态框触发，
		$('#addGroupModal').modal('hide');
		//销毁树
		if(state.familyMap != null){$("#familyMap").treeview("remove");}
		//清空
		state.familyMap=null;
	}
	function establishFeedbackSocket() {
		if ('WebSocket' in window) {
			websokcets.fd_sock = new WebSocket(
					"ws://${ip_}:${port_}/websocket/fd_sock");
		} else if ('MozWebSocket' in window) {
			websokcets.fd_sock = new MozWebSocket(
					"ws://${ip_}:${port_}/websocket/fd_sock");
		} else {
			websokcets.fd_sock = new SockJS(
					"ws://${ip_}:${port_}/websocket/fd_sock");
		}
		websokcets.fd_sock.onopen = feedbackSocketOpen;
		websokcets.fd_sock.onmessage = feedbackSocketMessage;
		websokcets.fd_sock.onerror = feedbackSocketError;
		websokcets.fd_sock.onclose = feedbackSocketClose;

	}

	function establishGroupSocket() {
		//建立链接
		if ('WebSocket' in window) {
			websokcets.g_sock = new WebSocket(
					"ws://${ip_}:${port_}/websocket/g_sock");
		} else if ('MozWebSocket' in window) {
			websokcets.g_sock = new MozWebSocket(
					"ws://${ip_}:${port_}/websocket/g_sock");
		} else {
			websokcets.g_sock = new SockJS(
					"ws://${ip_}:${port_}/websocket/g_sock");
		}
		websokcets.g_sock.onopen = groupSocketOpen;
		websokcets.g_sock.onmessage = groupSocketMessage;
		websokcets.g_sock.onerror = groupSocketError;
		websokcets.g_sock.onclose = groupSocketClose;
	}
	function groupSocketOpen(evt) {
		//g_sock建立链接
	}
	function groupSocketMessage(evt) {
		//g_sock收到消息,这个消息是个json对象
		//里面字段为  deleteList addList stateList
		//转json
		var cmd = JSON.parse(evt.data);
		if (cmd.addList.length > 0) {
			//需要add操作
			addGroup(cmd.addList);
		}
		if (cmd.deleteList.length > 0) {
			//删除
			deleteGroup(cmd.deleteList)
		}
		if (cmd.stateList.length > 0) {
			//更新链接状态
			stateGroup(cmd.stateList);
		}

	}
	function groupSocketError(e) {
		//g_sock发生错误
		swal("", "group socket error,please reflush the web page" + e.message, "error");
	}
	function groupSocketClose(e) {
		//g_sock关闭链接
		swal("", "group socket close,please reflush the web page" + e.message, "info");
	}

	function feedbackSocketOpen() {
		//打开什么也不做

	}
	function feedbackSocketMessage(evt) {
		var fdJson = JSON.parse(evt.data);//取出各个group的list
		var glist = fdJson.glist;
		for (var i = 0; i < glist.length; i++) {
			//每一个都是一个group的 fdList
			var fd_info = glist[i];
			if (groupMap[fd_info.groupName] != null
					&& groupMap[fd_info.groupName] != undefined) {
				//先把这些消息push进dist
				for (var j = 0; j < fd_info.fdList.length; j++) {
					//push进去
					groupMap[fd_info.groupName].fdList.push(fd_info.fdList[j]);

				}
				if (fd_info.groupName == state.nowGroup) {
					//这个group是当前的group,更新下数据
					//就是用这个fdList去刷新表格
					paintChartsDataDivAndLedAndTime(fd_info.fdList);//
				}
			}

		}
	}

	function feedbackSocketError(e) {
		swal("", "feedback socket error,please reflush the web page or notice the admin" , "error");
	}
	function feedbackSocketClose(e) {
		swal("", "feedback socket closed,please reflush the web page or notice the admin" , "info");

	}

	function addGroup(listToadd) {
		//这里面要做的事情，将group添加到字典里，字典存的对象比现在的复杂
		$(".groupItemNops").css("display", "none");//把那个转圈圈的隐藏起来
		for (var i = 0; i < listToadd.length; i++) {
			//遍历这里面的数组
			//1.创建字典条目
			creatGroupIndist(listToadd[i]);
			//2 放在页面上,注意字符串切割,还有设置key值 
			appendGroupItem(listToadd[i]);
		}

	}
	function stateGroup(listToadd) {
		//需要更新的group
		for (var i = 0; i < listToadd.length; i++) {
			//在字典李更新
			refreshConnectIndis(listToadd[i]);
			//是当前显示的，需要在界面上更新
			if (state.nowGroup == listToadd[i].groupName) {
				refreshConnectIndiv(listToadd[i]);//不用穿参数，自己去字典里面取数据
			}
		}
	}
	function refreshConnectIndis(groupInfo) {
		//字典里面更新下
		var group = groupMap[groupInfo.groupName]
		if (group != null && group != undefined) {
			//存在，那么久更新
			for (var i = 0; i < groupInfo.modules.length; i++) {
				//遍历每一个module
				groupMap[groupInfo.groupName].modules[i].connected = groupInfo.modules[i].connected;

			}
		}

	}
	function refreshConnectIndiv(groupInfo) {
		//在面板上刷新,去取数据刷新,已经更新在map里面了
		for (var i = 0; i < groupInfo.modules.length; i++) {
			var selector = ".moduleItem[" + "moduleindex=" + i + "]";
			var thisModule = groupInfo.modules[i];
			if (thisModule.connected == true || thisModule.connected == "true") {
				$(selector).find(".moduleConnectIcon").css("color", "#1abc9c");//小圆圈变成底色
			} else {
				$(selector).find(".moduleConnectIcon").css("color", "#e67e22");//小圆圈变成红色

			}

		}
	}

	function deleteGroup(listToadd) {

		for (var i = 0; i < listToadd.length; i++) {
			//删除group,这里要做的事情，如果nowGroup是要删的group，里面都是groupName

			//1.从字典里面删除，
			deleteFromDist(listToadd[i]);
			//2.从菜单里面删除
			deleteFromGroupMenu(listToadd[i]);
		}
		//没必要动右边的东西,就没有其他的事情了吧,feedback放的时候，判断下字典里面是不是为空就行了
	}
	function deleteFromDist(groupName) {
		delete groupMap[groupName];
	}
	function deleteFromGroupMenu(groupName) {
		//找到类里面key为group的，然后删除掉

	}
	function creatGroupIndist(groupInfo) {
		//在字典中创建条目
		var newGroupItem = {
			groupName : "",
			modules : [],//数组，每个成员是个对象，有module的信息
			fdList : [],
		};
		//创建
		newGroupItem.groupName = groupInfo.groupName;
		newGroupItem.modules = groupInfo.modules;
		groupMap[groupInfo.groupName] = newGroupItem;//放入字典
	}
	function appendGroupItem(groupInfo) {
		//放置在菜单中去
		//1 创建 条目元素
		var groupName = groupInfo.groupName;
		var groupName_sub = groupName.substring(0, groupName.indexOf("_g")); //截取，用来显示
		var liElement = $('<li class="groupItem" ><a href="#"><i class="icon-play menuIcon"></i></a></li>'); //图标默认是display = none
		//2 设置显示文本
		liElement.find("a").append(groupName_sub);
		//3 设置key值
		liElement.attr("key", groupName);
		liElement.find(".menuIcon").css("display", "none");
		liElement.find(".menuIcon").css("padding-right", "20px");
		liElement.find(".menuIcon").css("font-size", "12px");
		//4 加入到ul中去
		$(".groupMenu").append(liElement);
		//5 重置点击方法
		$(".groupItem").bind("click", function() {
			onClick_groupMenu($(this));//进行处理
		});
	}
	function onClick_groupMenu(thisJqEle) {
		//group菜单的点击事件
		//如果选择的就是当前的，return
		var selectedGroup = thisJqEle.attr("key");
		if (state.nowGroup != undefined && state.nowGroup != null
				&& state.nowGroup == selectedGroup)
			return;//什么事情都不会发生,点击的是当前的
		//1.清空其他小小的箭头[隐藏]【如果点击的不是已经坚实的】
		$(".menuIcon").css("display", "none");//隐藏

		//2.添加一个小小的箭头在这个选中元素的左侧,把这个条目的icon显示出来
		thisJqEle.find("i").css("display", "inline");
		//3.触发选择group事件
		selectGroup(selectedGroup);
	}
	function selectGroup(selectedGroup) {
		//选择group
		//1.如果右侧不是数据展示版，则切换到数据展示板
		$("#tempShowDiv").css("display", "none");
		$("#groupShowDiv").css("display", "block");
		//2.设置全局状态变量 [当前group为这个group的名字，当前moduleIndex在selectModule里面设置，从0开始]
		state.nowGroup = selectedGroup;
		//3.清空面板上内容/包括设置表的数据/数据div/灯数据/最后时间数据 / moduleMenu，clear掉
		//菜单清除
		$(".moduleMenu").empty();
		//重新搞moudle的菜单
		module_menu_reset(selectedGroup);
		//表格显示 / 数据div隐藏
		$(".dataChart").css("display", "block");
		$(".dataShow").css("display", "none");
		//5.设置groupChange状态为1，默认选中第一个
		state.groupChange = 1;
		//选择第一个module
		selectModule(selectedGroup, 0);

	}
	function onClick_switch(thisChartsWitch) {
		//点击开关的处理
		thisChartsWitch.attr("checked", !thisChartsWitch.attr("checked"))
		if (thisChartsWitch.attr("checked")) {
			//被选中
			thisChartsWitch.closest(".datazone").find(".dataChart").css(
					"display", "block");
			thisChartsWitch.closest(".datazone").find(".dataShow").css(
					"display", "none");
		} else {
			thisChartsWitch.closest(".datazone").find(".dataChart").css(
					"display", "none");
			thisChartsWitch.closest(".datazone").find(".dataShow").css(
					"display", "block");

		}

	}
	function module_change_div_init() {
		//这个函数是在调用selectModule后，被调用
		//用于让开关置位 数据板隐藏，表格显示
		//原数据清除
		$(".led").css("color", "#cccccc");
		$(".ledData").empty();
		$(".feedbackTimeDiv").empty();
		//开关置 on
		$(".chartsWitch").attr("checked", "true");
		$(".chartsWitch").prop("checked", "true");
		//表格显示 / 数据div隐藏
		$(".dataChart").css("display", "block");
		$(".dataShow").css("display", "none");

	}
	function module_menu_reset(selectedGroup) {
		//根据点击的元素的key，设置moduleMenu
		var moduleMenu = $(".moduleMenu").eq(0);
		//根据当前nowGroup,在字典里面找groupInfo
		var groupInfo = groupMap[state.nowGroup];
		if (groupInfo == null || groupInfo == undefined)
			return;//这个group不存在
		for (var i = 0; i < groupInfo.modules.length; i++) {
			//遍历module
			//创建这个module对应的元素
			var module = groupInfo.modules[i];
			var moduleMenuItem = $('<li class="moduleItem" moduleIndex="" family="" name=""><a href="#"><i class="icon-play moduleMenuIcon"> </i></a></li>');
			//设置属性
			moduleMenuItem.attr("moduleindex", i);
			moduleMenuItem.attr("family", module.family);
			moduleMenuItem.attr("name", module.name);
			//小箭头看不到

			//设置文本
			moduleMenuItem.find("a").append(module.family + "|" + module.name);
			//不同的状态，意味着不同的颜色
			if (module.connected == true || module.connected == "true") {
				//连接上,坠一个绿色的小圆圈
				var signCircle = $('<i class="icon-circle moduleConnectIcon"></i>');
				signCircle.css("color", "#1abc9c");//标准色

				moduleMenuItem.find("a").append(signCircle);
				signCircle.css("padding-left", "2px");//标准色
			} else {
				//没有连接上，坠一个红色的小圆圈
				var signCircle = $('<i class="icon-circle moduleConnectIcon"></i>');
				signCircle.css("color", "#e67e22");//没有连接的颜色

				moduleMenuItem.find("a").append(signCircle);
				signCircle.css("padding-left", "2px");//标准色
			}
			//需要设置点击事件

			//放在菜单后面
			moduleMenu.append(moduleMenuItem);
			moduleMenuItem.find(".moduleMenuIcon").css("display", "none");
			moduleMenuItem.find(".moduleMenuIcon").css("padding-right", "5px");
			moduleMenuItem.find(".moduleMenuIcon").css("font-size", "12px");
			moduleMenuItem.find(".moduleConnectIcon")
					.css("padding-left", "2px");//左边
		}
		//需要设置点击事件,毕竟菜单刷新了
		$(".moduleItem").bind("click", function() {
			onClick_ModuleItem($(this));//进行处理
		});
	}
	function onClick_ModuleItem(thisLi) {

		var index = parseInt(thisLi.attr("moduleindex"));
		//直接选择Module
		selectModule(state.nowGroup, index);

	}
	function selectModule(selectedGroup, index) {
		//选择module函数
		//1.设置module的index【如果点击的不是已经监视的,如果等于已监视的值但groupchange变为1，则还是向下进行】
		if (state.nowModuleIndex == index) {
			if (state.groupChange == 0)
				return;//一样，而且group还没变过
		}
		state.nowModuleIndex = index;//更新当前的moudleIndex
		//1 .初始化面板
		module_change_div_init();
		//2.清空其他moduleItem的小小箭头【隐藏起来】
		//3.设置小箭头到这个moduleItem
		module_icon_switch(index);
		//4.清空原来表的数据
		reInitCharts();
		//4.根据当前module的Index和div,修改led / charts / 数据div / lastFeedback
		//跟进式修改，以merge的形式显示,数据从全局字典里面去取的,取好穿进去
		paintChartsDataDivAndLedAndTime(groupMap[state.nowGroup].fdList);
		//5.groupChange 设置为0
		state.groupChange = 0;
	}
	function module_icon_switch(index) {
		//函数用来切换module小箭头显示
		//1.先全部消失
		$(".moduleItem").find(".moduleMenuIcon").css("display", "none");
		//2 显示选择的这个
		var selector = ".moduleItem[" + "moduleindex=" + index + "]";
		$(selector).find(".moduleMenuIcon").css("display", "inline");
	}
	function paintChartsDataDivAndLedAndTime(fdlist) {
		//这里完成对所有数据的更新
		//首先取出这个group所有的feedbackList，
		//然后抽出 电压 电流 速度 扭矩 位置 LED 灯 ，最后时间,的可渲染数据
		var voltageSeries = {
			name : "voltage",
			type : 'line',
			data : []
		// 这个数组就是要填充的电压
		};
		var currentSeries = {
			name : "current",
			type : 'line',
			data : []
		// 这个数组就是要填充的电流
		};
		var velocitySeries = {
			name : "velocity",
			type : 'line',
			data : []
		// 这个数组就是要填充的速度
		};
		var torqueSeries = {
			name : "torque",
			type : 'line',
			data : []
		// 这个数组就是要填充的扭矩
		};
		var positionSeries = {
			name : "position",
			type : 'line',
			data : []
		// 这个数组就是要填充位置
		};

		var led = {
			led_R : null,
			led_G : null,
			led_B : null

		};
		//下面这个函数就用来抽数据,【数据应该穿进来，这个是追加的形式填充表格的】
		//var fdlist = groupMap[state.nowGroup].fdList;
		var timeStamp = getSeriesFromFeedBackList(fdlist, state.nowModuleIndex,
				voltageSeries, currentSeries, torqueSeries, positionSeries,
				velocitySeries, led);//返回一个最迟更新时间
		//此时数据抽好了，该更新了
		updateCharts(voltageSeries, currentSeries, torqueSeries,
				positionSeries, velocitySeries);
		//更新数据div，就是表格藏起来后的展示数据的div
		paintDataDiv(voltageSeries, currentSeries, torqueSeries,
				positionSeries, velocitySeries);
		//灯的信息直接取就完事，绘制灯加修改元素文本
		paintLedAndTimeDiv(led, timeStamp);
	}
	function colorRGB2Hex(r, g, b) {
		//获得颜色字符串
		var hex = "#"
				+ ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
		return hex;
	}

	function toLocalFormTime(inputTime) {
		//时间戳格式转换
		var date = new Date(inputTime);
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		m = m < 10 ? ('0' + m) : m;
		var d = date.getDate();
		d = d < 10 ? ('0' + d) : d;
		var h = date.getHours();
		h = h < 10 ? ('0' + h) : h;
		var minute = date.getMinutes();
		var second = date.getSeconds();
		minute = minute < 10 ? ('0' + minute) : minute;
		second = second < 10 ? ('0' + second) : second;
		return m + '/' + d + '/' + y + '   ' + h + ':' + minute + ':' + second;
	};
	function paintLedAndTimeDiv(led, timeStamp) {
		//设置灯的颜色
		$(".led").css("color", colorRGB2Hex(led.led_R, led.led_G, led.led_B));
		
		$(".ledData")
				.text(
						"[R:" + led.led_R + "   G: " + led.led_G + "   B:"
								+ led.led_B + "]");//设置文本
		$(".feedbackTimeDiv").text(
				"UpdateOn:   " + toLocalFormTime(timeStamp));//设置时间文本

	}
	function paintDataDiv(voltageSeries, currentSeries, torqueSeries,
			positionSeries, velocitySeries) {
		//根据传进来的电压 电流 速度 扭矩 位置 更新div上的内容
		if (voltageSeries.data.length > 0) {
			var value = voltageSeries.data[voltageSeries.data.length - 1][1];//1是值
			$(".voltageDataShow").text(value+" V");
		}
		if (currentSeries.data.length > 0) {
			var value = currentSeries.data[currentSeries.data.length - 1][1];//1是值
			$(".currentDataShow").text(value+" A");
		}
		if (torqueSeries.data.length > 0) {
			var value = torqueSeries.data[torqueSeries.data.length - 1][1];//1是值
			$(".torqueDataShow").text(value);
		}
		if (positionSeries.data.length > 0) {
			var value = positionSeries.data[positionSeries.data.length - 1][1];//1是值
			$(".positionDataShow").text(value);
		}
		if (velocitySeries.data.length > 0) {
			var value = velocitySeries.data[velocitySeries.data.length - 1][1];//1是值
			$(".velocityDataShow").text(value);
		}

	}
	function getSeriesFromFeedBackList(fdList, index, voltageSeries,
			currentSeries, torqueSeries, positionSeries, velocitySeries, led) {
		//从当前group里面的feedbacklist里面抽取指定index的module的数据
		var specModule = {
			voltage : [],
			current : [],
			torque : [],
			position : [],
			velocity : [],
			led_field : {
				led_R : 0,
				led_G : 0,
				led_B : 0,

			}
		};
		var timeStamp = 0;
		for (var i = 0; i < fdList.length; i++) {
			//遍历这个list,每一个都抽取
			var oneFd = fdList[i];
			specModule.voltage.push(oneFd.voltage[index]);
			specModule.current.push(oneFd.current[index]);
			specModule.torque.push(oneFd.torque[index]);
			specModule.position.push(oneFd.position[index]);
			specModule.velocity.push(oneFd.velocity[index]);
			if (i == fdList.length - 1) {
				//取led和时间戳,最新的灯和最新的时间戳
				specModule.led_field.led_R = oneFd.led_fields[index].led_R;
				specModule.led_field.led_G = oneFd.led_fields[index].led_G;
				specModule.led_field.led_B = oneFd.led_fields[index].led_B;
				timeStamp = fdList[i].velocity[index].timeStamp;//最后一个时间戳
			}

		}
		if (specModule != null && specModule != undefined) {
			//不是null也不是undefined，才有价值
			//电压数据
			voltageSeries.data = getSeries(specModule.voltage);
			//电流数据
			currentSeries.data = getSeries(specModule.current);
			//扭矩数据
			torqueSeries.data = getSeries(specModule.torque);
			//位置数据
			positionSeries.data = getSeries(specModule.position);
			//速度数据
			velocitySeries.data = getSeries(specModule.velocity);
			//获取led
			led.led_R = specModule.led_field.led_R;
			led.led_G = specModule.led_field.led_G;
			led.led_B = specModule.led_field.led_B;
			//返回时间
			return timeStamp;
		}
		return 0;

	}
	function getSeries(list, type) {
		var data = [];//首先是个数组
		for (var i = 0; i < list.length; i++) {
			var oneEntry = [];//0元素是时间，1元素是值
			oneEntry[0] = list[i].timeStamp;
			oneEntry[1] = list[i].value;
			data[i] = oneEntry;//放进去
		}
		return data;//返回这个二维数组
	}
	function updateCharts(voltageSeries, currentSeries, torqueSeries,
			positionSeries, velocitySeries) {
		//以追加的方式设置设置表格属性
		//直接setOpt就行
		var pot = chartsObj.position.getOption();
		for (var i = 0; i < positionSeries.data.length; i++) {
			pot.series[0].data.push(positionSeries.data[i]);
		}
		chartsObj.position.setOption(pot, false);

		pot = chartsObj.voltage.getOption();
		for (var i = 0; i < voltageSeries.data.length; i++) {
			pot.series[0].data.push(voltageSeries.data[i]);
		}
		chartsObj.voltage.setOption(pot, false);

		pot = chartsObj.current.getOption();
		for (var i = 0; i < currentSeries.data.length; i++) {
			pot.series[0].data.push(currentSeries.data[i]);
		}
		chartsObj.current.setOption(pot, false);

		pot = chartsObj.torque.getOption();
		for (var i = 0; i < torqueSeries.data.length; i++) {
			pot.series[0].data.push(torqueSeries.data[i]);
		}
		chartsObj.torque.setOption(pot, false);

		pot = chartsObj.velocity.getOption();
		for (var i = 0; i < velocitySeries.data.length; i++) {
			pot.series[0].data.push(velocitySeries.data[i]);
		}
		chartsObj.velocity.setOption(pot, false);

	}
	function reInitCharts() {
		//初始化所有表格
		reInitSpecChart("voltage");
		reInitSpecChart("current");
		reInitSpecChart("velocity");
		reInitSpecChart("torque");
		reInitSpecChart("position");
	}
	function reInitSpecChart(type) {
		//对已经init过的表格进行初始化
		if (type == "voltage") {
			var opt = chartsObj.voltage.getOption();
			opt.series[0].data = []
			chartsObj.voltage.setOption(opt, true);

		} else if (type == "current") {
			var opt = chartsObj.current.getOption();
			opt.series[0].data = []
			chartsObj.current.setOption(opt, true);

		} else if (type == "velocity") {
			var opt = chartsObj.velocity.getOption();
			opt.series[0].data = []
			chartsObj.velocity.setOption(opt, true);

		} else if (type == "torque") {
			var opt = chartsObj.torque.getOption();
			opt.series[0].data = []
			chartsObj.torque.setOption(opt, true);

		} else if (type == "position") {
			var opt = chartsObj.position.getOption();
			opt.series[0].data = []
			chartsObj.position.setOption(opt, true);
		}
	}

	//--------------------------
	function creatCharts() {
		// 指定图表的配置项和数据

		creatSpecChart("voltage");
		creatSpecChart("current");
		creatSpecChart("velocity");
		creatSpecChart("torque");
		creatSpecChart("position");
	}
	function creatSpecChart(type) {
		if (type == "voltage") {
			opts.title.text = "Voltage";
			opts.legend.data = [ "voltage" ];
			opts.yAxis.name = "V";
			opts.series = [ {
				name : "voltage",
				type : 'line',
				data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
						[ 1503564706152, 6 ], [ 1503564707152, 8 ],
						[ 1503564708152, 10 ], ]
			} ];

			chartsObj.voltage = echarts.init(($(".voltageCharts").eq(0))[0]);
			chartsObj.voltage.setOption(opts);

		} else if (type == "current") {
			opts.title.text = "Current";
			opts.legend.data = [ "current" ];
			opts.yAxis.name = "A";
			opts.series = [ {
				name : "current",
				type : 'line',
				data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
						[ 1503564706152, 6 ], [ 1503564707152, 8 ],
						[ 1503564708152, 10 ], ]
			} ];

			chartsObj.current = echarts.init(($(".currentCharts").eq(0))[0]);
			chartsObj.current.setOption(opts);

		} else if (type == "velocity") {
			opts.title.text = "Velocity";
			opts.legend.data = [ "velocity" ];
			opts.yAxis.name = "rad/s";
			opts.series = [ {
				name : "velocity",
				type : 'line',
				data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
						[ 1503564706152, 6 ], [ 1503564707152, 8 ],
						[ 1503564708152, 10 ], ]
			} ];

			chartsObj.velocity = echarts.init(($(".velocityCharts").eq(0))[0]);
			chartsObj.velocity.setOption(opts);

		} else if (type == "torque") {
			opts.title.text = "Torque";
			opts.legend.data = [ "torque" ];
			opts.yAxis.name = "torque";
			opts.series = [ {
				name : "torque",
				type : 'line',
				data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
						[ 1503564706152, 6 ], [ 1503564707152, 8 ],
						[ 1503564708152, 10 ], ]
			} ];

			chartsObj.torque = echarts.init(($(".torqueCharts").eq(0))[0]);
			chartsObj.torque.setOption(opts);

		} else if (type == "position") {
			opts.title.text = "Position";
			opts.legend.data = [ "position" ];
			opts.yAxis.name = "position";
			opts.series = [ {
				name : "position",
				type : 'line',
				data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
						[ 1503564706152, 6 ], [ 1503564707152, 8 ],
						[ 1503564708152, 10 ], ]
			} ];

			chartsObj.position = echarts.init(($(".positionCharts").eq(0))[0]);
			chartsObj.position.setOption(opts);

		}

	}
</script>

</html>