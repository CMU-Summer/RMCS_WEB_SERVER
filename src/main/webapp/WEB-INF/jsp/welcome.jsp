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
<script src="${pageContext.request.contextPath}/assets/js/application.js"></script>
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
	<link href="${pageContext.request.contextPath}/css/chartCss.css"
	rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" />
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
		
				<ul class="nav navbar-nav navbar-right" style="margin-right: 16px">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" style="font-size: 21px;">menu <b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="/logOut">Logout</a></li>
						</ul></li>
					<li><a href="#"> <span class="visible-md visible-lg"><span
								class="fui-gear" style="top: 10px;"></span></span>
					</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>




	</div>

	<!--应该是个3 9布局-->
	<div class="container" style="width: 100%">
		<div class="row">
			<div class="col-md-2">
				<!--菜单,菜单里面的内容是由websocket写入的，增加的时候，会给下面的添加，减少的时候，会删除下面的，改变的时候，状态，会修改-->
				<div id="treeMenu"></div>
			</div>
			<div class="col-md-10">
				<!--底下这个div是用来展示group的详细信息的，里面有每个group的一个tab,group增加和减少会，改变这里面的数量，跳转用data-taglog,根据菜单里面的数据源进行tab的隐藏和显示-->
				<!--统一放到jumbotron里面-->
				<!--刚打开的时候就一个展示的东西就行，然后等内容过来-->
				<%--<div class="jumbotron" style="background-color: #f9f9f9;">--%>
				<%--<div class="container" id="groupContent">--%>
				<%--<div><img src="img/icons/svg/retina.svg" alt="Retina" style="margin-left: 45%;"></div>--%>
				<%--<h1 class="text-info">Welcome to use RCMS,please to chose one group to monitor</h1>--%>
				<%--</div>--%>
				<%--</div>--%>
				<div id="group1_g" class="jumbotron"
					style="padding-right: 0px; padding-left: 0px; background-color: #f9f9f9">
					<!--最基本的应该是个巨幕，然后在巨幕里面进行-->
					<div class="container" style="width: 100%">
						<!--第一行是两列，2 10-->
						<div class="row pbl " >
							<!--2这里面需要放一个展示groupName和Moudle的块-->
							<div class="col-md-2">
								<!--展示group信息的-->
								<div class="showGroupInfo tile" style="text-align: right;">
									<h6>family | name</h6>
									<hr style="color: #000000" />
									<div style="color: #3498db">SEA snake| X011</div>
									<div style="color: #dbdcd6">Spare|A011</div>
								</div>
							</div>
							<!--这个用来展示,表的，一大堆表的-->
							<div class="col-md-10">
								<!--一共四行-->
								<!--第一行第一列显示小圆点表示灯，第二列显示最近一次的更新时间-->
								<div class="row" style="text-align: left;;padding-bottom: 40px;">
									<!-- 显示小圆点，然后显示RGB的值  -->
									<div class="col-md-5">
										<div>
											<span style="color: #e74c3c" class="fa fa-circle"></span>&nbsp&nbsp&nbsp[R:243,G:0:B:0]
										</div>
									</div>
									<!-- 显示最新一次的更新时间  -->
									<div class="col-md-5">
										<div>
											lastUpdateTime: <strong>2017-08-23 23:59:59</strong>
										</div>
									</div>
								</div>
								<div class="row">
									<!-- 显示两个图表，各占一半 -->
									<!-- 电压 电流 -->
									<div class="col-md-5">
										<!-- 表明是电压表 -->
										<div class="row pbl">
											<div class="col-md-5">
												 	<div class="bootstrap-switch-square">
														<input type="checkbox" data-toggle="switch"
															id="voltage-switch" />
													</div>											</div>
										</div>
										<div class="row">
											<div  class="voltageCharts dataChart"></div>
										</div>
									</div>
									<div class="col-md-5">
										<!-- 表明是电流表 -->
										<!-- 开关 -->
										<div class="row pbl">
											<div class="col-md-5">
												<div class="bootstrap-switch-square">
													<input type="checkbox" data-toggle="switch"
														id="current-switch" />
												</div>
											</div>
										</div>
										<div class="row">
											<div  class="currentCharts dataChart"></div>
										</div>
									</div>

								</div>
								<div class="row">
									<!-- 速度 扭矩 -->
									<div class="col-md-5">
										<!-- 表明是速度表 -->
										<!-- 开关 -->

										<div class="row pbl">
											<div class="col-md-5">
												<div class="bootstrap-switch-square">
													<input type="checkbox" data-toggle="switch"
														id="velocity-switch" />
												</div>
											</div>
										</div>
										<div class="row">
											<div  class="velocityCharts dataChart" ></div>
										</div>

									</div>
									<div class="col-md-5">
										<!-- 表明是扭矩表 -->
										<!-- 开关 -->
										<div class="row pbl">
											<div class="col-md-5">
												<div class="bootstrap-switch-square">
													<input type="checkbox" data-toggle="switch"
														id="torque-switch" />
												</div>
											</div>
										</div>
										<div class="row">
											<div  class="torqueCharts dataChart"></div>
										</div>


									</div>

								</div>
								<div class="row">
									<!-- 只有一个图表在这里，不过为了整齐 -->
									<!-- 位置 -->
									<div class="col-md-5">
										<!-- 表明是位置表 -->
										<!-- 开关 -->
										<div class="row pbl">
											<div class="col-md-5">
												<div class="bootstrap-switch-square">
													<input type="checkbox" data-toggle="switch"
														id="position-switch" />
												</div>
											</div>
										</div>
										<div class="row">
											<div class="positionCharts dataChart"></div>
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
	//全局变量，group的字典，key为group的在cache中的name,value为一个对象，
	//这里面有所有的东西了

	var groupMap = [];
	groupMap["group1"] = {
		groupName : "group1",
		familyList : [ {
			name : "f1",
			nameList : [ {
				connected : false,
				name : "n1"
			} ]
		} ],//数组，每个成员是个对象，里面第二个字段又是个数组
		fdList : [ {
			motorCurrent : [ {
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
			motorCurrent : [ {
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
		divEle : null,
		charts : null
	};
	groupMap["group2"] = {
		groupName : "group2",
		familyList : [ {
			name : "f1",
			nameList : [ {
				connected : false,
				name : "n1"
			} ]
		} ],//数组，每个成员是个对象，里面第二个字段又是个数组
		fdList : [ {
			motorCurrent : [ {
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
			motorCurrent : [ {
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
		divEle : null,
		charts : null
	};
	groupMap["group3"] = {
		groupName : "group3",
		familyList : [ {
			name : "f1",
			nameList : [ {
				connected : false,
				name : "n1"
			} ]
		} ],//数组，每个成员是个对象，里面第二个字段又是个数组
		fdList : [ {
			motorCurrent : [ {
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
			motorCurrent : [ {
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
		divEle : null,
		charts : null
	};
	var tempJsonData = {
		groupName : "group1",
		familyList : [ {
			name : "SEA-snake",
			nameList : [ {
				name : "X011",
				connected : false,
			} ]
		}, {
			name : "SEA-snake",
			nameList : [ {
				name : "X011",
				connected : true,
			} ]
		}

		]
	};

	function getTree() {
		// Some logic to retrieve, or generate tree structure

		var data = [ {
			text : "groups",
			id : "groups",
			nodes : [ {
				text : "group1",
				id : 'group1_g',
				groupKey : "group1_g"
			}, {
				text : "group1",
				id : 'group2_g',
				groupKey : "group2_g"
			}, {
				text : "group1",
				id : 'group3_g',
				groupKey : "group3_g"
			} ]
		}, {
			text : "addGroup",
			id : "addGroup",
		} ];
		return data;
	}
	$(document).ready(
			function() {
				$("#treeMenu").treeview({
					data : getTree(),
					multiSelect : false

				});
				//创建几个panel放到字典里面，
				// 基于准备好的dom，初始化echarts实例
				var myChart1 = echarts.init(document
						.getElementsByClassName("voltageCharts")[0]);
				var myChart2 = echarts.init(document
						.getElementsByClassName("currentCharts")[0]);
				var myChart3 = echarts.init(document
						.getElementsByClassName("velocityCharts")[0]);
				var myChart4 = echarts.init(document
						.getElementsByClassName("torqueCharts")[0]);
				var myChart5 = echarts.init(document
						.getElementsByClassName("positionCharts")[0]);
				// 指定图表的配置项和数据
				var option = {
					title : {
						text : 'dataShow'
					},
					tooltip : {},
					legend : {
						data : [ '销量' ]
					},
					xAxis : {
						data : [ "衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子" ]
					},
					yAxis : {},
					series : [ {
						name : '销量',
						type : 'bar',
						data : [ 5, 20, 36, 10, 10, 20 ]
					} ]
				};

				// 使用刚指定的配置项和数据显示图表。
				myChart1.setOption(option);
				myChart2.setOption(option);
				myChart3.setOption(option);
				myChart4.setOption(option);
				myChart5.setOption(option);
			});

	function createPanel(jsonData) {
		//这个jsonData是从g_sock获得的字符串

	}
</script>

</html>