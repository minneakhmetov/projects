<%@ page import="services.ProductServiceImpl" %>
<%@ page import="repositories.ProductRepositoryImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Product" %>
<%@ page import="repositories.CartRepositoryImpl" %>
<%@ page import="services.CartServiceImpl" %>
<%@ page import="app.Constants" %>
<%@ page import="context.MyApplicationContext" %>
<%--
  ~ Developed by Razil Minneakhmetov on 10/25/18 8:00 PM.
  ~ Last modified 10/25/18 8:00 PM.
  ~ Copyright © 2018. All rights reserved.
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>

    <title>Title</title>
    <link href="https://fonts.googleapis.com/css?family=Francois+One" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Sunflower:300" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <script src="/js/scripts.js"></script>

    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/css/styles.css" rel="stylesheet">
    <%
        Cookie[] cookies = request.getCookies();
        String userName = "";
        String photo = "";

        Long userId = null;
        boolean flag = false;
        boolean code = false;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("vk_id")) {
                flag = true;
                userId = Long.valueOf(cookies[i].getValue());
            }
            if (cookies[i].getName().equals("userName")) {
                userName = cookies[i].getValue();
            }
            if (cookies[i].getName().equals("userPhoto")) {
                photo = cookies[i].getValue();
            }
            if (cookies[i].getName().equals("code")) {
                code = true;
            }
        }
    %>
<body>
<div class="modal" id="myModal">
    <div class="modal-dialog shadow-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Ender a code</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form method="post" action="/getusers">
                <div class="modal-body">
                    <div class="input-group mb-3">
                        <input type="text"
                               class="form-control myForm" id="customUsername" placeholder="Code"
                               aria-label="Username"
                               aria-describedby="basic-addon1" name="code">
                        <input type="text"
                               class="form-control myForm" id="chatId" placeholder="Chat ID"
                               aria-label="Username"
                               aria-describedby="basic-addon1" name="chatId">
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="/code" target="_blanc"><button type="button" class="btn btn-outline-secondary-1">Get a code</button> </a>
                    <input type="submit" class="btn btn-outline-secondary-1" name="" value="Send">
                    <button type="button" class="btn btn-outline-secondary-1" data-dismiss="modal">Close</button>
                </div>
            </form>

        </div>
    </div>
</div>
<div class="modal fade" id="pay" >
    <div class="modal-dialog shadow-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Checkout</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <form method="POST" action="https://money.yandex.ru/quickpay/confirm.xml">
                <div class="modal-body" id="inputs">


                </div>
                <%--<div class="modal-footer">--%>
                <%--<a href="/code" target="_blanc"><button type="button" class="btn btn-outline-secondary-1">Get a code</button> </a>--%>
                <%--<input type="submit" class="btn btn-outline-secondary-1" name="" value="Send">--%>
                <%--<button type="button" class="btn btn-outline-secondary-1" data-dismiss="modal">Close</button>--%>
                <%--</div>--%>
            </form>

        </div>
    </div>
</div>
<nav class="navbar navbar-expand-lg navbar-light bg-dark shadow-lg fixed-top">
    <a class="navbar-brand" href="/main"><img src="/pictures/logo.png" style="height: 50px"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <form class="form-inline my-2 my-lg-0" action="/search" method="post">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search"
                       name="query">
                <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" style="color: lightgrey;">Search
                </button>
            </form>
        </ul>

        <%
            if (flag) {
        %>
        <img src="<%=photo%>" height="40" style="margin-right: 10px; border: 1px solid lightgray"
             class="rounded shadow">
        <h5 style="margin-right: 10px; margin-top: 5px; color: lightgray"><%=userName%>
        </h5>
        <a href="/logout">
            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" style="margin-right: 9px "
                    >Log out
            </button>
        </a>
        <%

        } else {
        %>
        <a href="/auth">
            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" style="margin-right: 9px "
                    >Log in
            </button>
        </a>

        <%
            }
            if (!code & flag) {
        %>

        <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" style="margin-right: 9px "
                data-toggle="modal"
                data-target="#myModal">Enter a code
        </button>
        <%
            } if(code & flag) {

        %>
        <div class="dropdown">

            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" style="margin-right: 9px; padding-bottom: 10px; padding-top: 10px " data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="sendProductId()">
                <i class="fa fa-shopping-cart" aria-hidden="true"></i>
            </button>
            <div class="dropdown-menu dropdown-menu-right py-0 shadow-lg" style="width: 500px">

                <ul class="list-group" id ="table" style="overflow-y:scroll; max-height:80vh;">
                    </ul>
            </div>


        <%
            }
        %>


    </div>
</nav>

<%

    ProductServiceImpl service
            = (ProductServiceImpl) MyApplicationContext.getMyContext().getAttribute("productService");

    String query = request.getParameter("query");
    List<Product> list = service.find(query);

%>

<div class="container" style="margin-top:  140px">
    <div class="row">
        <div class="col-md-12 px-0">
        <form action="/search" method="post">
            <div class="row">
            <div class="col-md-9">
            <input class="form-control mr-sm-2-2 btn-lg btn-block" type="text" placeholder="<%=query%>" aria-label="First name"
                   id="username" style="margin-bottom: 10px; background-color: transparent" name="query">
            </div>
            <div class="col-md-3" style="padding-left: 0px">
            <button class="btn btn-outline-secondary-1 btn-lg btn-block" type="submit" name="delete">Search
            </button>
            </div>
            </div>
        </form>
        </div>
    </div>
    <div class="row">
        <h1 style="margin-right: 10px">Search results for <%=query%>: <%=list.size()%></h1>


    </div>

    <div class="row">
    <%
        for (int i = 0; i < list.size(); i++) {
            Long id = list.get(i).getId();
            String price = list.get(i).getPrice();
            String photoURL = list.get(i).getAvatar();
            String name = list.get(i).getName();
    %>

        <div class="card shadow" style="width: 16.85rem; margin-right: 15px; margin-bottom: 15px">
            <div class="card-body">
                <h5 class="card-title" style="color: #495057"><%=name%>
                </h5>
                <img src="<%=photoURL%> " height="100px" style="margin-bottom: 15px">
                <h6 class="card-subtitle mb-2 text-muted"> Price: <%=price%> Rub.
                </h6>
                <button type="button" class="btn btn-outline-secondary-1 btn-block" onclick="addToCart(<%=id%>, <%=userId%>)">Add to cart</button>
            </div>

        </div>
        <%
            }
        %>

    </div>
</div>

</body>
</html>