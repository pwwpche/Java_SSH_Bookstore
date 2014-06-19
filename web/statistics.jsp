<%--
  Created by IntelliJ IDEA.
  User: pwwpche
  Date: 2014/5/4
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Admin</title>

    <!-- Bootstrap core CSS -->
    <link href="./dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/navbar.css" rel="stylesheet">

    <script type="text/javascript" src="script/jquery.min.js"></script>
    <script type="text/javascript" src="script/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/icon.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <link rel="stylesheet" type="text/css" href="css/signin.css">
    <script type="text/javascript" src="script/easyui-lang-en.js"></script>
    <script>
        var emptyStr = {"total":0,"rows":[]};
        function createQuery(){

            $("#infoGrid").datagrid("loadData",emptyStr);
            var selectType = typeConvert($('#queryCondition').combo('getValue'));
            console.log("selectType=" + selectType);
            console.log("QueryString=" + $("#QueryString").val());
            $.ajax({
                type: "post",
                dataType: "json",
                url: '<s:url action="getStatisticsByItem"/>',
                data:{
                    type : selectType,
                    dataString:  $("#QueryString").val()
                },
                success:function(data){
                    $("#infoGrid").datagrid("loadData", data);
                    console.log("InfoGrid loaded");
                },
                error: function(data){
                   // $("#infoGrid").datagrid("loadData", itemsStr);
                    console.log(data);
                }
            });
            function typeConvert(type){
                console.log(type);
                var result = "oid";
                if(type == 1)
                    result =  "username";
                if(type == 2)
                    result =  "orderDay";
                if(type == 3)
                    result =  "orderMonth";
                if(type == 4)
                    result =  "orderYear";
                if(type == 5)
                    result =  "catagory";
                console.log(result);
                return result;
            }
            $.ajax({
                type: "post",
                dataType: "json",
                url:  '<s:url action="getCountsInfo"/>',
                data:{
                    type : selectType,
                    dataString : $("#QueryString").val()
                },
                success:function(data){
                    $("#statisGrid").datagrid({
                        title:"Sale Statistics",
                        width:700,
                        height:200,
                        nowrap: true,
                        autoRowHeight: true,
                        striped: true,
                        collapsible:true,
                        sortOrder: 'desc',
                        remoteSort: false,
                        idField:'bid',
                        columns: [[
                            {
                                field: "itemName",
                                width: 100,
                                title: selectType
                            },
                            {
                                field: "totalPrice",
                                width: 100,
                                title: "Total income"
                            }

                        ]]
                    });
                    $("#statisGrid").datagrid("loadData", data);
                    console.log("StatisGrid loaded");
                },
                error: function(data){
                    console.log("ERROR in statisGrid ajax");
                    console.log(data);
                }
            });

        }
        $(document).ready(function(){

            $("#queryCondition").combo({
                required:true,
                editable:false,
                width: 100
            });
            $('#conditionList').appendTo($('#queryCondition').combo('panel'));

            $('#conditionList input').click(function(){
                var v = $(this).val();
                var s = $(this).next('span').text();
                $('#queryCondition').combo('setValue', v).combo('setText', s).combo('hidePanel');
            });
        });
    </script>
</head>

<body>
<div class="container">
<div class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">

            <a class="navbar-brand" href="#">Admin</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li ><a href="adminUser.jsp">Manage User</a></li>
                <li><a href="adminBooks.jsp">Manage Books</a></li>
                <li class="active"><a href="statistics.jsp">Manage Sales</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Welcome admin</a></li>
                <li class="active"><a href="./">Sign out</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div><!--/.container-fluid -->
</div>

<div class="row">

    <div class="col-lg-2">
        <h3 class="panel-heading" style="padding:0px 0px">Query</h3>
        <div>
            <p>Input Query Condition</p>
            <select id="queryCondition" width="100"></select>
        </div>
        <div id="conditionList">
            <div style="color:#99BBE8;background:#fafafa;padding:5px;">Type</div>
            <input type="radio" name="lang" value="01"><span>user</span><br/>
            <input type="radio" name="lang" value="02"><span>day</span><br/>
            <input type="radio" name="lang" value="03"><span>month</span><br/>
            <input type="radio" name="lang" value="04"><span>year</span><br/>
            <input type="radio" name="lang" value="05"><span>catagory</span>
        </div>
        <br>
        <p>
            Input Query String<br>
            <input type="text" name="" id="QueryString"/>
            <p>
                <button onclick="createQuery()" style="width: 100px; margin: 0 auto">Query</button>
            </p>
        </p>
    </div>

    <div class="col-lg-10" >
        <table id="infoGrid" title="Sales Information" class="easyui-datagrid" style="width:900px;height:250px"
               toolbar="#toolbar" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
            <tr>
                <th field="oid" width="100">Order ID</th>
                <th field="username" width="100">Username</th>
                <th field="orderYear" width="50">year</th>
                <th field="orderMonth" width="50">month</th>
                <th field="orderDay" width="50">day</th>
                <th field="isbn" width="150">isbn</th>
                <th field="bookName" width="150">bookname</th>
                <th field="catagory" width="50">catagory</th>
                <th field="quantity" width="50">quantity</th>
                <th field="totalPrice" width="100">Total Price</th>
            </tr>
            </thead>
        </table>

        <table id="statisGrid" title="Sale Statistics" class="easyui-datagrid" style="width:900px;height:250px"
               toolbar="#toolbar" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">

        </table>
    </div>
</div>
</div>

</body>
</html>
