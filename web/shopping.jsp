
<%--
  Created by IntelliJ IDEA.
  User: pwwpche
  Date: 2014/5/2
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<%
    String username = session.getAttribute("username") != null ? (String)session.getAttribute("username") : "";
    String welcomeTitle = (username.equals("")) ? "<a href=\"index.jsp\">Login</a>" :  "<a href=\"index.jsp\">Sign out</a>";
    String cartInfo = session.getAttribute("cartContent") == null ? "{\"total\":0,\"rows\":[]}" : (String)session.getAttribute("cartContent");
            /*
    if(CookieManager.getSessionIdByNameInCookie(request, "cart") == null)
    {
        //Create Cookie to store current session of this user
        CookieManager.addCookie(response, "cart", session_id, 1000000);
    }
    else if (CookieManager.getSessionIdByNameInCookie(request, "cart").equals(
            CookieManager.getSessionIdByNameInCookie(request, "JSESSIONID")))
    {
        //If session found, then return content of Cart stored in the session
        cartInfo =(String) SessionListener.getSessionById(CookieManager.getSessionIdByNameInCookie(request, "cart")).getAttribute("cartContent");
        if(cartInfo == null || cartInfo.equals("null")){
            cartInfo = "{\"total\":0,\"rows\":[]}";
        }
        System.out.println("cartInfo get");
        System.out.println(cartInfo);
    }
    else
    {
        //If SessionID has been changed, then reset it to current sessionId
        //And cart content will not be loaded..

        System.out.println("request.getRequestedSessionId()=" + request.getRequestedSessionId());
        System.out.println("CookieManager.getSessionIdByNameInCookie(request, \"cart\")=" + CookieManager.getSessionIdByNameInCookie(request, "cart"));
        System.out.println("CookieManager.getSessionIdByNameInCookie(request, \"JSESSIONID\")=" + CookieManager.getSessionIdByNameInCookie(request, "JSESSIONID"));
        CookieManager.setCookie(request,"cart", request.getRequestedSessionId(), response);
    }
    */

%>

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


    <title>User Interface</title>

    <script>

    //Shopping Cart
    var cartData = <%=cartInfo%>;
    var emptyData = {"total":0,"rows":[]};
    var username = "<%=username%>";
    var totalCost = 0;

        $(document).ready(function(){

            $('#cartcontent').datagrid({
                singleSelect:true,
                data: cartData.rows,
                columns:[[
                    {field:'bookName',title:'Name',width:100},
                    {field: 'quantity', title: 'quantity', width: 50,  sortable: true,
                        sorter:function(a,b){
                            return (a>b?1:-1);
                        }
                    },
                    {field: 'price', title: 'Price', width: 70,  sortable: true,
                        sorter:function(a,b){
                            return (a>b?1:-1);
                        }
                    },
                    {field:'opt',title:'Operation',width:0,align:'center',
                        formatter:function(value, row ,index){
                            cartData = $("#cartcontent").datagrid("getData");
                            totalCost = 0;
                            for(var i = 0 ; i < cartData.total ; i++)
                            {
                                totalCost += (cartData.rows[i].price) * (cartData.rows[i].quantity);
                            }
                            totalCost = Math.round(totalCost*100)/100;
                            $('div.cart .total').html('Total: '+totalCost);
                            console.log(cartData);
                            return '<button class="removeCart" onclick="removeCartSelected(' + index + ')">Remove</button>';
                        }
                    }
                ]]
            });

                $('#bookView').datagrid({
                    title:'Books overview',
                    iconCls:'icon-save',
                    width:700,
                    height:350,
                    nowrap: true,
                    autoRowHeight: true,
                    striped: true,
                    collapsible:true,
                    url:'<s:url action="GetAllBooks"/>',
                    sortOrder: 'desc',
                    remoteSort: false,
                    idField:'isbn',
                    columns:[[
                        {field:'isbn',title:'isbn',width:0},
                        {field:'bookName',title:'Name',width:200},
                        {field:'bookCatagory',title:'Catagory',width:100,sortable:true,
                            sorter:function(a,b){
                                return (a>b?1:-1);
                            }
                        },
                        {field: 'bookPrice', title: 'Price', width: 100,  sortable: true,
                            sorter:function(a,b){
                                return (a>b?1:-1);
                            }
                        },
                        {field:'opt',title:'Operation',width:0,align:'center',
                            formatter:function(value, row, index){
                                console.log("add to cart index=", index);
                                return '<button class="addCart" onclick="getCartSelected(' + index + ')">Add to cart</button>';
                            }
                        }
                    ]],
                    pagination:true,
                    rownumbers:true
                });
        });

        function getCartSelected(index){
            $("#bookView").datagrid("selectRow", index);
            var selected = $('#bookView').datagrid('getSelected');
            console.log("selected = " + selected);
            if (selected){
                addProduct(index, selected.isbn, selected.bookName, parseFloat(selected.bookPrice) );
            }
        }

        function removeCartSelected(index)
        {
            $("#cartcontent").datagrid("selectRow", index);
            var selected = $('#cartcontent').datagrid('getSelected');
            if(selected)
            {
                removeProduct(index, selected.isbn)
            }
        }

        function addProduct(index,isbn,name,price){
            console.log("adding");
            console.log($("#cartcontent").datagrid("getData"));
            cartData = $("#cartcontent").datagrid("getData");
            if(isNaN(cartData.total))
            {
                cartData = {"total":0,"rows":[]};
            }
            function add(){
                console.log("in add");
                for(var i=0; i<cartData.total; i++){
                    var row = cartData.rows[i];
                    if (row.isbn == isbn){
                        row.quantity += 1;
                        return;
                    }
                }
                console.log("create new");
                cartData.total += 1;
                cartData.rows.push({
                    index_id: index,
                    isbn: isbn,
                    bookName:name,
                    quantity:1,
                    price:price
                });
            }
            add();
            totalCost += price;
            totalCost = Math.round(totalCost*100)/100;
            $('#cartcontent').datagrid('loadData', cartData);
            console.log("loaded");
            $('div.cart .total').html('Total: '+totalCost);
            console.log("div update");
        }

        function removeProduct(index, isbn){
            console.log("removing");
            console.log("isbn=" + isbn);
            cartData =  $('#cartcontent').datagrid('getData');
            console.log(cartData);

            function remove(){
                console.log(cartData);
                for(var i = 0; i<cartData.total; i++){
                    var row = cartData.rows[i];
                    console.log("remove i=" + i);
                    console.log(row.isbn);
                    console.log(isbn);
                    if (row.isbn == isbn){
                        cartData.rows[i].quantity -= 1;
                        totalCost -= row.price;
                        totalCost = Math.round(totalCost*100)/100;
                        if(row.quantity == 0)
                        {
                            cartData.total -= 1;
                            cartData.rows.splice(i,1);
                        }
                        return;
                    }
                }

            }
            remove();
            console.log("remove done");
            $('#cartcontent').datagrid('loadData', cartData);
            $('div.cart .total').html('Total: '+totalCost);
            console.log("div update");
        }

        function upload(){
            if(username == ""){
                alert("Please login first!");
                saveCartToSession();
                window.location.href = "index.jsp";
            }
            $.ajax({
                url: "<s:url action="buy"/>",
                type: "post",
                dataType: "json",
                data:{
                    rowData: JSON.stringify($('#cartcontent').datagrid('getData'))
                },
                success: function(data){
                    alert("Success!");
                    $('#cartcontent').datagrid('loadData', emptyData);
                },
                error: function(data){
                    alert("upload failed\n" + data.msg);
                }
            });
        }

        function saveCartToSession(){
            $.ajax({
                url: "<s:url action="saveCartToSession"/>",
                type: "post",
                dataType: "json",
                data:{
                    cartContent: JSON.stringify($('#cartcontent').datagrid('getData'))
                },
                success: function(data){
                    alert("Cart Saved");
                },
                error: function(data){
                    alert("Cart saving failed");
                }
            });
        }
    </script>
</head>



<body >

<div class="container">
    <div class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">

                <a class="navbar-brand" href="./">Book view</a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><%=welcomeTitle%></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="./">Welcome <%=username%></a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </div>
    <div class="row">
        <div class="col-lg-8">
            <p></p>
            <table id="bookView" singleSelect="true"></table>
        </div>
        <div class="col-lg-4">
            <div class="cart">
                <p>Shopping Cart</p>
                <table id="cartcontent" style="width:auto ;height:auto;">
                    <thead>
                    <tr>
                        <th field="isbn" width=0></th>
                        <th field="index_id" width=0></th>
                        <th field="bookName" width=140>Book Name</th>
                        <th field="quantity" width=60 align="right">Quantity</th>
                        <th field="price" width=60 align="right">Price</th>
                        <th field="Operation" width=60 align="right">
                        </th>
                    </tr>
                    </thead>
                </table>
                <p class="total">Total: $0</p>
                <button onclick="saveCartToSession()">Save My Cart</button>
                <button onclick="upload()">Buy</button>
            </div>
        </div>
    </div>
    </div>
</body>
</html>
