<%--
  Created by IntelliJ IDEA.
  User: pwwpche
  Date: 2014/5/2
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="assets/ico/favicon.ico">

    <title>User Registration</title>

    <!-- Bootstrap core CSS -->
    <link href="dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

    <script src="script/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            $("#submit").click(function(){
                var username = document.getElementById("username").value;
                var password = document.getElementById("password").value;
                var confirmPass = document.getElementById("confirmPass").value;
                var email = document.getElementById("email").value;
                var birthday = document.getElementById("birthday").value;
                if(username.length == 0){
                    alert("Enter user name");
                    return ;
                }
                if(password != confirmPass){
                    alert("The password you entered are not the same.")
                    return ;
                }
                var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                if(reg.test(email) == false){
                    alert("Email address is not correct");
                    return ;
                }
                if(password == confirmPass
                    && username != ""
                    && email != ""
                    && password != ""
                    && birthday != "")
                {
                    $.ajax({
                        url: '<s:url action="registerUser"/>',
                        type: "post",
                        dataType: 'json',
                        data: {
                            "username" : username,
                            "password" : password,
                            "email" : email,
                            "birthday": birthday
                        },
                        success: function(data)
                        {
                            if(data.status != "error")
                            {
                                alert("Registration Complete, Please Login.");
                                window.location.href = "index.jsp";
                            }
                            else
                            {
                                alert(data.message);
                            }
                        },
                        error: function(data){
                            alert("fail" + data);
                            alert("Hmmm...some error occurred.");
                        }

                    });
                }
                else
                {
                    alert("Check Information again!");
                }
            });

            $("#check").click(function(){
                $("#check").text("Wait...");
                var username = document.getElementById("username").value;
                console.log("check start");
                $.ajax({
                    url: '<s:url action="checkUserExistance"/>',
                    type: "post",
                    dataType: 'json',
                    data: {
                        "username" : username
                    },
                    success: function(data)
                    {
                        console.log(data);
                        if(data.status == true)
                        {
                            alert("Available");
                        }
                        else
                        {
                            alert("Error!\n" + data.message);
                        }
                        $("#check").text("Check");
                    },
                    error: function(){
                        alert("Hmmm...some error occurred.");
                        $("#check").text("Check");
                    }

                });
            });
        });
    </script>
</head>
<body>

<div class="container">

    <form class="form-signin" role="form">

        <h2 class="form-signin-heading">Register</h2>
        <input name="Username" id="username" type="text" class="form-control" placeholder="Username" required autofocus>
        <!--<button class="btn" id="check">Check</button>-->
        <input name="password" id="password" type="password" class="form-control" placeholder="Password" required>
        <input name="confirmPass" id="confirmPass" type="password" class="form-control" placeholder="Confirm Password" required>
        <input name="email" id="email" type="text" class="form-control" placeholder="Email" required>
        <input name="birthday" id="birthday" type="date" class="form-control" placeholder="birthday" required>
        <!--
        <label class="checkbox">
            <input type="checkbox" value="remember-me"> Remember me
        </label>
        -->
        <br>
        <button id="submit" class="btn btn-lg btn-primary" type="button">Sign in</button>
    </form>

</div> <!-- /container -->




</body>
</html>
