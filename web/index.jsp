<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: pwwpche
  Date: 2014/6/4
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Book Market</title>

    <!-- Bootstrap core CSS -->
    <link href="dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">
    <script>
        function reg(){
            window.location.href="Register.jsp";
        }
    </script>

</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">

            <s:form cssClass="form-signin" action="loginAction" method="POST" namespace="/">
                <h2 class="form-signin-heading">Please log in</h2>
                Username<s:textfield type="text" cssClass="form-control" placeholder="Username" name="username" label="Name?" />
                Password<s:password  type="password" cssClass="form-control" placeholder="Password" name="password"  />
                <s:submit cssClass="btn btn-lg btn-primary btn-block" type="submit" value="submit"/>
            </s:form>
            <s:if test="!validate(username, password) && username.length() > 0">
                <script>
                    alert("Wrong username or password");
                </script>
            </s:if>
            <!--
            <form class="form-signin" role="form" action="loginCheck">
                <h2 class="form-signin-heading">Please log in</h2>
                <input name="username" type="text" class="form-control" placeholder="Username" required autofocus>
                <input name="password" type="password" class="form-control" placeholder="Password" required >
                <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
            </form>
            -->
        </div>
    </div>
    <div class="row">
        <div class="col-lg-4">
            <s:property value="#session.done"/>
            <s:property value="#parameters.done"/>
        </div>
        <div class="col-sm-4">
            <button class="btn btn-lg" onclick="reg()" style="float: right">Register</button>
        </div>

    </div>
</div> <!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
</body>
</html>
