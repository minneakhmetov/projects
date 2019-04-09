<!DOCTYPE html>
<html>
<head>
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>

    <!-- FAVICON -->
    <link rel="apple-touch-icon" sizes="57x57" href="/favicons/apple-touch-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="/favicons/apple-touch-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/favicons/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="/favicons/apple-touch-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/favicons/apple-touch-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="/favicons/apple-touch-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/favicons/apple-touch-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="/favicons/apple-touch-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="/favicons/apple-touch-icon-180x180.png">
    <link rel="icon" type="img/png" href="/favicons/favicon-32x32.png" sizes="32x32">
    <link rel="icon" type="img/png" href="/favicons/favicon-96x96.png" sizes="96x96">
    <link rel="icon" type="img/png" href="/favicons/android-chrome-192x192.png" sizes="192x192">
    <link rel="icon" type="img/png" href="/favicons/favicon-16x16.png" sizes="16x16">
    <link rel="manifest" href="/favicons/manifest.json">
    <link rel="shortcut icon" href="/favicons/favicon.ico">
    <meta name="msapplication-config" content="/favicons/browserconfig.xml">


    <title>Главная</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link href="/styles/style.css" rel="stylesheet">
</head>

<body background="/imgs/fon.jpg">
<!--<div id="carouselExampleSlidesOnly" class="carousel slide" data-ride="carousel">-->

<!--<div class="carousel-inner">-->

<!--<div class="carousel-item active">-->
<!--<img src="one.jpg" class="d-block w-100" alt="...">-->
<!--</div>-->
<!--<div class="carousel-item">-->
<!--<img src="two.jpg" class="d-block w-100" alt="...">-->
<!--</div>-->
<!--<div class="carousel-item">-->
<!--<img src="three.jpg" class="d-block w-100" alt="...">-->
<!--</div>-->
<!--<div class="carousel-caption" style="margin-left: 112vh; margin-bottom: 5vh">-->
<div class="container">
    <div class="row" style="margin-top: 30vh">
        <div class="col-md-7">
            <img class="img-fluid shadowed" src="/imgs/spinach.png">
            <h3 style="text-align: center; margin-top: 10px; font-family: 'Montserrat'">Лучший сервис по подбору
                собеседников</h3>
        </div>
        <div class="col-md-5">
                <div class="card shadow-lg"
                     style="width: 22rem; height: ${height} rem; background-color: #efefef; border-color: #e0e0e0" id="card">


                    <!--    <a class="card-body"> -->
                    <div class="login-form">
                        <#if error??>
                        <div class="alert alert-danger" role="alert" id="errorAlert">
                           Неверный логин или пароль!
                        </div>
                        </#if>

                    <form method="post" action="/main">
                        <!--<img class="img-fluid mb-3" src="marmalade2.png" style="width: 500px">-->
                        <h6 style="margin-bottom: 15px; font-family: 'Montserrat'">Войдите или зарегистируйтесь</h6>

                        <input class="form-control mr-sm-2-2" type="email" placeholder="Email" aria-label="First name"
                               id="email" style="margin-bottom: 10px; font-family: 'Montserrat'" name="email" onclick="deleteAlert()">

                        <input class="form-control mr-sm-2-2" type="password" placeholder="Пароль"
                               aria-label="First name" id="password" name="password"
                               style="margin-bottom: 10px; font-family: 'Montserrat'" onclick="deleteAlert()">

                        <div class="container p-0" style="margin-bottom: 10px">
                            <div class="row">
                                <div class="col-md-12">
                                    <input class="btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2 "
                                           type="submit" name=""
                                           value="Войти" onclick="deleteAlert()">
                                </div>
                                <#--<div class="col-md-6 pl-1">-->

                                <#--</div>-->
                            </div>
                        </div>
                    </form>
                        <a href="/register"
                           name=""><button class="btn fluid btn-outline-secondary-1 form-control mr-sm-2-2">Регистрация</button>
                        </a>
                    <#--<a href="/regvk">-->
                        <#--<button class="btn btn-block btn-outline-secondary-2 " type="submit" name=""-->
                                <#--value="Log in with VK" style="margin-bottom: 10px"><img class="img-fluid"-->
                                                                                        <#--src="/imgs/vk-logo-png-white-2.png"-->
                                                                                        <#--style="height: 17px; margin-right: 5px; margin-bottom: 3px">Вход-->
                            <#--с помощью ВК-->
                        <#--</button>-->
                    <#--</a>-->

                    <!--<h6 style="margin-bottom: 10px">If you don't have an account:</h6>-->


                    <!-- </a> -->
                </div>
                </div>
        </div>

    </div>
</div>


<!--</div>-->
<!--</div>-->
<!--</div>-->
<!--</div>-->

<script src="/js/login.js"></script>
</body>
</html>