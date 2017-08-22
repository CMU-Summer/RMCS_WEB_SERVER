<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RMCS</title>

    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/sweetalert2.js"></script>
    <script src="${pageContext.request.contextPath}/js/flat-ui.js"></script>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/sweetalert2.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/flat-ui.css" rel="stylesheet"/>


</head>
<body>
    <!-- 巨幕放进去 -->
    <div class="container" style="margin-top: 5%;">
    <div class="jumbotron">
            <h1 style="text-align:center;padding-bottom: 40px;">Robots Monitor and Control System</h1>
            <form class="form-horizontal" role="form" style="border:1px">
                <div class="form-group" >
                    <label for="userNameInput" class="col-lg-4 control-label">user</label>
                    <div class="col-lg-5">
                        <input type="text" class="form-control" id="userNameInput" placeholder="Username">
                    </div>
                </div>
                <div class="form-group">
                    <label for="passwordInput" class="col-lg-4 control-label">Password</label>
                    <div class="col-lg-5">
                        <input type="password" class="form-control" id="passwordInput" placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-offset-4 col-lg-8">
                        <button type="button" class="btn btn-hg btn-primary" style="width:60%;margin-top: 25px;" id="loginButton">Sign in</button>
                    </div>
                </div>
            </form>
    </div>



     <script>
        $("#loginButton").bind("click", function() {
                var passport = $('#passwordInput').val();
                var password = $('#userNameInput').val();
                $.ajax({
                    url:"${pageContext.request.contextPath}/login",
                    type:"POST",
                data:{
                    "passport":passport,
                    "password":password
                },
                dataType:"JSON",
                success:function(data){
                        if(data.result == "true" || data.result==true){
                        location.href=location.href;
                    }else {
                        swal("error",data.des,"warning");
                    }
                },
                error:function(e){
                    swal("error",e.message,"error");
                }

                });
        });


    </script>
    </div>
</body>
</html>