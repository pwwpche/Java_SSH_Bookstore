<%--
  Created by IntelliJ IDEA.
  Book: pwwpche
  Date: 2014/5/3
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Book Administration</title>
    <!-- Bootstrap core CSS -->
    <link href="./dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="navbar.css" rel="stylesheet">
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
    <script type="text/javascript">

        var url = '<s:url action="createBooks"/>';
        function newBook(){
            $("#inputISBN").show();
            $('#dlg').dialog('open').dialog('setTitle','New Book');
            $('#fm').form('clear');
            url = '<s:url action="createBooks"/>';
        }
        function editBook(){
            $("#inputISBN").hide();
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','Edit Book');
                $('#fm').form('load',row);
                url = '<s:url action="updateBooks"/>';

            }
        }
        function saveBook(){
            console.log("saveBook");
            $('#fm').form('submit',{
                url: url,
                dataType: 'json',
                onSubmit: function(){
                     return $(this).form('validate');
                },
                success: function(result){
                    console.log(result);
                    $('#dlg').dialog('close');		// close the dialog
                    $('#dg').datagrid('reload');	// reload the book data
                },
                error:function(result){
                    console.log(result);
                    $.messager.show({
                        title: 'Error',
                        msg: result.msg
                    });
                 }

            });
        }
        function removeBook(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('Confirm','Are you sure you want to remove this book?',function(r){
                    if (r){
                        console.log(row);
                        $.post('<s:url action="removeBooks"/>'+'?isbn=' + row.isbn,{id:row.id},function(result){
                            console.log(result);
                            if (result.success){
                                $('#dg').datagrid('reload');	// reload the book data
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
                    <li><a href="adminUser.jsp">Manage User</a></li>
                    <li  class="active"><a href="adminBooks.jsp">Manage Books</a></li>
                    <li><a href="statistics.jsp">Manage Sales</a></li>
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


            <table id="dg" title="My Books" class="easyui-datagrid" style="width:700px;height:250px"
                   url='<s:url action="GetAllBooks"/>'
                   toolbar="#toolbar" pagination="true"
                   rownumbers="true" fitColumns="true" singleSelect="true">
                <thead>
                <tr>
                    <th field="isbn" width="50">ISBN</th>
                    <th field="bookName" width="50">Book Name</th>
                    <th field="authors" width="50">Book Authors</th>
                    <th field="bookCatagory" width="50">Catagory</th>
                    <th field="bookPrice" width="50">price</th>
                </tr>
                </thead>
            </table>
            <div id="toolbar">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBook()">New Book</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBook()">Edit Book</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeBook()">Remove Book</a>
            </div>

            <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
                 closed="true" buttons="#dlg-buttons">
                <div class="ftitle">Book Information</div>

                <form id="fm" method="post" novalidate>
                    <div class="fitem" id="inputISBN">
                        <label>ISBN:</label>
                        <input name="isbn" class="easyui-validatebox" required="true">
                    </div>

                    <div class="fitem" id="inputBookname">
                        <label>Bookname:</label>
                        <input name="bookName" class="easyui-validatebox" required="true" >
                    </div>

                    <div class="fitem" id="inputBookauthor">
                        <label>Author:</label>
                        <input name="authors" class="easyui-validatebox" required="true" >
                    </div>

                    <div class="fitem">
                        <label>Catagory:</label>
                        <input name="bookCatagory" class="easyui-validatebox" validType="catagory" required="true">
                    </div>
                    <div class="fitem">
                        <label>Price:</label>
                        <input type="text" name="bookPrice" id=""/>
                    </div>
                </form>
            </div>
            <div id="dlg-buttons">
                <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBook()">Save</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
            </div>

        </div>

    </div>

</div>


</body>
</html>