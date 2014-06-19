<%--
  Created by IntelliJ IDEA.
  User: pwwpche
  Date: 2014/6/7
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <!-- Bootstrap core CSS -->
    <link href="./dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/navbar.css" rel="stylesheet">

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Administration</title>
    <script type="text/javascript" src="script/jquery.min.js"></script>
    <script type="text/javascript" src="script/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/icon.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <link rel="stylesheet" type="text/css" href="css/signin.css">
    <script type="text/javascript" src="script/easyui-lang-en.js"></script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            color:#666;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
    </style>

    <title>User Interface</title>

    <script type="text/javascript">

        var url = "GetAllCustomerInfo";
        function newUser(){
            $('#dlg').dialog('open').dialog('setTitle','New User');
            $("#inputUser").show();
            $('#fm').form('clear');
            url = "<s:url action="CreateCustomer"/>";
        }
        function editUser(){
            $("#inputUser").hide();
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','Edit User');
                $('#fm').form('load',row);
                url = '<s:url action="ModifyCustomer"/>';
            }
        }
        function saveUser(){
            $('#fm').form('submit',{
                dataType: 'json',
                url: url,
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                        $('#dlg').dialog('close');		// close the dialog
                        $('#dg').datagrid('reload');	// reload the user data
                },
                error: function(result){
                    $.messager.show({
                        title: 'Error',
                        msg: result.msg
                    });
                    console.log(result);
                }

            });
        }
        function removeUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('Confirm','Are you sure you want to remove this user?',function(r){
                    if (r){
                        console.log(row);
                        $.post('<s:url action="RemoveCustomer"/>' +'?username=' + row.username,{id:row.id},function(result){
                            console.log(result);
                            if (result.success){
                                $('#dg').datagrid('reload');	// reload the user data
                            } else {
                                $.messager.show({	// show error message
                                    title: 'Error',
                                    msg: result.msg
                                });
                            }
                        },'json');
                    }
                });
            }
        }
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
                    <li class="active"><a href="adminUser.jsp">Manage User</a></li>
                    <li><a href="adminBooks.jsp">Manage Books</a></li>
                    <li><a href="statistics.jsp">Sale Statistics</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="./">Sign out</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </div>
    <div class="row">
        <div class="col-lg-3"></div>
        <div class="col-lg-6">
            <table id="dg" title="My Users" class="easyui-datagrid" style="width:700px;height:250px"
                   url="/GetAllCustomerInfo.action"
                   toolbar="#toolbar" pagination="true"
                   rownumbers="true" fitColumns="true" singleSelect="true" method="GET">
                <thead>
                <tr>
                    <th field="username" width="50">Username</th>
                    <th field="password" width="50">Password</th>
                    <th field="email" width="50">Email</th>
                    <th field="birthday" width="50">Birthday</th>
                </tr>
                </thead>
            </table>
            <div id="toolbar">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">Remove User</a>
            </div>

            <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
                 closed="true" buttons="#dlg-buttons">
                <div class="ftitle">User Information</div>
                <form id="fm" method="post" novalidate>

                    <div class="fitem" id="inputUser">
                        <label>Username:</label>
                        <input name="username" class="easyui-validatebox" required="true" >
                    </div>
                    <div class="fitem">
                        <label>Password:</label>
                        <input type="password" name="password" required="true" >
                    </div>
                    <div class="fitem">
                        <label>Email:</label>
                        <input name="email" class="easyui-validatebox" validType="email" required="true">
                    </div>
                    <div class="fitem">
                        <label>Birthday:</label>
                        <input name="birthday" class="easyui-validatebox" validType="date">
                    </div>
                </form>
            </div>
            <div id="dlg-buttons">
                <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">Save</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
