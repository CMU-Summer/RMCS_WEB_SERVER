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
<link href="${pageContext.request.contextPath}/css/font-awesome.min1.css"
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
					Monitor And Control System</a>
			</div>
			<div class="navbar-collapse collapse">

				<ul class="nav navbar-nav navbar-right" style="margin-right: 16px;">
					<li><a href="${pageContext.request.contextPath}/logOut"><strong style="font-size: 20px;">sign out</strong> <span class="fa fa-sign-out" style="font-size: 25px;padding-left: 10px;"></span>
					</a></li>
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
									<li class="active"><a href="#">Groups</a></li>
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
									<li class="groupItemNops" ><a href="#"><i class="icon-spinner icon-spin"></i></a></li>
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
									<li class="addGroupItem"  style="text-align:center"><a href="#"><span class="fa fa-plus" style="padding: 5px;font-size: 20px;"></span></a></li>
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
				<div class="jumbotron" style="background-color: #f9f9f9;">
					<div class="container" id="groupContent">
					<div><img src="img/icons/svg/retina.svg" alt="Retina" style="margin-left: 45%;"></div>
					<h1 class="text-info">Welcome to use RCMS,please to chose one group to monitor</h1>
					</div>
				</div>
				<div id="groupShowDiv" class="jumbotron"
					style="padding-right: 0px; padding-left: 0px; background-color: #f9f9f9;display:none">
					<!--最基本的应该是个巨幕，然后在巨幕里面进行-->
					<div class="container" style="width: 100%">
						<!--第一行是两列，2 10-->
						<div class="row pbl ">
							<!--2这里面需要放一个展示groupName和Moudle的块-->
							<div class="col-md-2">
								<!--展示group信息的-->
								<div class="row pbl" style="margin-bottom: -25%;">
									<div class="sidebar-nav">
										<div class="navbar navbar-default" role="navigation">
											<div class="navbar-collapse collapse sidebar-navbar-collapse">
												<ul class="nav navbar-nav">
													<li class="active"><a href="#">Modules</a></li>
												</ul>
											</div>
											<!--/.nav-collapse -->
										</div>
									</div>
								</div>
								<div class="row pbl">
									<div class="sidebar-nav" style="text-align: right;">
										<div class="navbar navbar-default" role="navigation">
											<div class="navbar-collapse collapse sidebar-navbar-collapse">
												<ul class="nav navbar-nav moduleMenu">
													<li class="moduleItem" moduleIndex="1" family="" name=""><a href="#">SEA snake | XA011</a></li>
													<li class="moduleItem" moduleIndex="2" family="" name=""><a href="#">Spare | D021</a></li>

												</ul>
											</div>
											<!--/.nav-collapse -->
										</div>
									</div>
								</div>
							</div>
							<!--这个用来展示,表的，一大堆表的-->
							<div class="col-md-10">
								<!--一共四行-->
								<!--第一行第一列显示小圆点表示灯，第二列显示最近一次的更新时间-->
								<div class="row pbl"
									style="text-align: left;; padding-bottom: 40px;">
									<!-- 显示小圆点，然后显示RGB的值  -->
									<div class="col-md-5">
										<div>
											<span style="color: #e74c3c" class="fa fa-circle"></span>&nbsp&nbsp&nbsp[R:243,G:0:B:0]
										</div>
									</div>
									<!-- 显示最新一次的更新时间  -->
									<div class="col-md-5">
										<div class="feedbackTimeDiv">
											LastFeedBack: <strong>2017-08-23 23:59:59</strong>
										</div>
									</div>
								</div>
								<div class="row pbl">
									<!-- 显示两个图表，各占一半 -->
									<!-- 电压 电流 -->
									<div class="col-md-5">
										<!-- 表明是电压表 -->
										<div class="row pbl">
											<div class="col-md-5">
												<div class="bootstrap-switch-square">
													<input checked type="checkbox" data-toggle="switch"
														class="voltage-switch" />
												</div>
											</div>
										</div>
										<div class="row pbl">
											<div class="voltageCharts dataChart"></div>
											<div class="dataShow" style="display:none"></div>
										</div>
									</div>
									<div class="col-md-5">
										<!-- 表明是电流表 -->
										<!-- 开关 -->
										<div class="row pbl">
											<div class="col-md-5">
												<div class="bootstrap-switch-square">
													<input checked type="checkbox" data-toggle="switch"
														class="current-switch" />
												</div>
											</div>
										</div>
										<div class="row pbl">
											<div class="currentCharts dataChart"></div>
											<div class="dataShow" style="display:none"></div>
										</div>
									</div>

								</div>
								<div class="row pbl">
									<!-- 速度 扭矩 -->
									<div class="col-md-5">
										<!-- 表明是速度表 -->
										<!-- 开关 -->

										<div class="row pbl">
											<div class="col-md-5">
												<div class="bootstrap-switch-square">
													<input checked type="checkbox" data-toggle="switch"
														class="velocity-switch" />
												</div>
											</div>
										</div>
										<div class="row pbl">
											<div class="velocityCharts dataChart"></div>
											<div class="dataShow" style="display:none"></div>
										</div>

									</div>
									<div class="col-md-5">
										<!-- 表明是扭矩表 -->
										<!-- 开关 -->
										<div class="row pbl">
											<div class="col-md-5">
												<div class="bootstrap-switch-square">
													<input checked type="checkbox" data-toggle="switch"
														class="torque-switch" />
												</div>
											</div>
										</div>
										<div class="row pbl">
											<div class="torqueCharts dataChart"></div>
											<div class="dataShow" style="display:none"></div>
										</div>


									</div>

								</div>
								<div class="row pbl">
									<!-- 只有一个图表在这里，不过为了整齐 -->
									<!-- 位置 -->
									<div class="col-md-5">
										<!-- 表明是位置表 -->
										<!-- 开关 -->
										<div class="row pbl">
											<div class="col-md-5">
												<div class="bootstrap-switch-square">
													<input checked type="checkbox" data-toggle="switch"
														class="position-switch" />
												</div>
											</div>
										</div>
										<div class="row pbl">
											<div class="positionCharts dataChart"></div>
											<div class="dataShow" style="display:none"></div>
										</div>
									</div>
									<div class="col-md-5"></div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>

		</div>
	</div>




</body>
<script>
	//全局性的变量
	//这里面有所有的东西了
	var state = {
		nowGroup : null, //字符串，当前group的名字
		nowModuleIndex : -1 //在group里面的偏移
	};
	var websokcets={
			g_sock:null,
			fd_sock:null
			
	};
	var chartsObj={
			voltage:null,
			current:null,
			velocity:null,
			torque:null,
			position:null
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
			led : {
				r : 145,
				g : 0,
				b : 0
			}
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
			led : {
				r : 145,
				g : 0,
				b : 0
			}
		}, ],
	};

	$(document).ready(
			function() {
				//创建图表
				creatCharts();
				//建立socket
				establishGroupSocket();
				//正确设置div显示
			
			});

	
	
	function establishGroupSocket(){
		//建立链接
		if ('WebSocket' in window) {
			websokcets.g_sock = new WebSocket("ws://localhost:8080/websocket/g_sock");
	    } 
	    else if ('MozWebSocket' in window) {
	    	websokcets.g_sock = new MozWebSocket("ws://localhost:8080/g_sock");
	    } 
	    else {
	    	websokcets.g_sock = new SockJS("ws://localhost:8080/g_sock");
	    }
		websokcets.g_sock.onopen=groupSocketOpen;
		websokcets.g_sock.onmessage =groupSocketMessage ;
		websokcets.g_sock.onerror =groupSocketError ;
		websokcets.g_sock.onmessage =groupSocketClose ;
	}
	function groupSocketOpen(evt){
	    //g_sock建立链接
	}
	function groupSocketMessage(evt){
	    //g_sock收到消息,这个消息是个json对象
        //里面字段为  deleteList addList stateList
        //转json
        var cmd= JSON.parse(evt.data);
        if(cmd.addList.length>0){
            //需要add操作
            addGroup(cmd.addList)
        }
		swal("",evt.data,"info");
	}
	function groupSocketError(e){
	    //g_sock发生错误
		swal("",e.message,"error");
	}
	function groupSocketClose(evt){
	    //g_sock关闭链接
		swal("","closed","info");
	}
	function addGroup(listToadd){
        //这里面要做的事情，将group添加到字典里，字典存的对象比现在的复杂
        $(".groupItemNops").css("display","none");//把那个转圈圈的隐藏起来
        for(var groupInfo in listToadd){
                //遍历这里面的数组
                //1.创建字典条目
                creatGroupIndist(groupInfo)
                //2 放在页面上,注意字符串切割,还有设置key值
                appendGroupItem(groupInfo);
        }

    }
    function creatGroupIndist(groupInfo){
        //在字典中创建条目
            var newGroupItem = {
                        groupName : "",
                        modules : [],//数组，每个成员是个对象，有module的信息
                        fdList : [],
                    };
            //
            newGroupItem.groupName=groupInfo.groupName;
            newGroupItem.modules=groupInfo.modules;
            groupMap[groupInfo.groupName]=newGroupItem;//放入字典
    }
    function appendGroupItem(groupInfo){
        //放置在菜单中去
        //1 创建 条目元素
        var groupName=groupInfo.groupName;
        var groupName_sub=groupName.substring(0,groupName.indexOf("_g")); //截取，用来显示
        var liElement=$('<li class="groupItem" ><a href="#"></a></li>');
        //2 设置显示文本
        liElement.find("a").text(groupName_sub);
        //3 设置key值
        liElement.attr("key",groupName);
        //4 加入到ul中去
        $(".groupMenu").append(liElement);
        //5 重置点击方法
        $(".groupItem").bind("click",function(){
                onClick_groupMenu($(this));//进行处理
        });
    }
    function onClick_groupMenu(thisJqEle){
        //group菜单的点击事件
        //1.右侧面板显示设置切换
        //2.

    }


//--------------------------
    function creatCharts(){
		// 指定图表的配置项和数据
	
		creatSpecChart("voltage");
		creatSpecChart("current");
		creatSpecChart("velocity");
		creatSpecChart("torque");
		creatSpecChart("position");
	}
	function creatSpecChart(type){
		if(type=="voltage"){
			opts.title.text="voltage";
			opts.legend.data=["voltage"];
			opts.yAxis.name="voltage";
			opts.series=[ {
				name : "voltage",
				type : 'line',
				data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
						[ 1503564706152, 6 ], [ 1503564707152, 8 ],
						[ 1503564708152, 10 ], ]
			} ];

			chartsObj.voltage=echarts.init(($(".voltageCharts").eq(0))[0]);
			chartsObj.voltage.setOption(opts);

		}else if(type=="current"){
			opts.title.text="current";
				opts.legend.data=["current"];
				opts.yAxis.name="current";
				opts.series=[ {
					name : "current",
					type : 'line',
					data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
					[ 1503564706152, 6 ], [ 1503564707152, 8 ],
					[ 1503564708152, 10 ], ]
					}
				];

				chartsObj.current=echarts.init(($(".currentCharts").eq(0))[0]);
				chartsObj.current.setOption(opts);

		}else if(type=="velocity"){
			opts.title.text="velocity";
			opts.legend.data=["velocity"];
			opts.yAxis.name="velocity";
			opts.series=[ {
			name : "velocity",
			type : 'line',
			data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
			[ 1503564706152, 6 ], [ 1503564707152, 8 ],
			[ 1503564708152, 10 ], ]
			} ];

			chartsObj.velocity=echarts.init(($(".velocityCharts").eq(0))[0]);
			chartsObj.velocity.setOption(opts);

		}else if(type=="torque"){
			opts.title.text="torque";
			opts.legend.data=["torque"];
			opts.yAxis.name="torque";
			opts.series=[ {
				name : "torque",
				type : 'line',
				data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
				[ 1503564706152, 6 ], [ 1503564707152, 8 ],
				[ 1503564708152, 10 ], ]
				} 
			];

			chartsObj.torque=echarts.init(($(".torqueCharts").eq(0))[0]);
			chartsObj.torque.setOption(opts);

		}else if(type=="position"){
			opts.title.text="position";
			opts.legend.data=["position"];
			opts.yAxis.name="position";
			opts.series=[ {
				name : "position",
				type : 'line',
				data : [ [ 1503564704152, 2 ], [ 1503564705152, 3 ],
				[ 1503564706152, 6 ], [ 1503564707152, 8 ],
				[ 1503564708152, 10 ], ]
				} 
			];

			chartsObj.position=echarts.init(($(".positionCharts").eq(0))[0]);
			chartsObj.position.setOption(opts);

		}
		
		
	}
	
</script>

</html>