<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">

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
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <title>Регистрация</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link href="/styles/style.css" rel="stylesheet">

</head>

<body background="/imgs/fon.jpg">

<div class="container">

    <div class="row">
        <div class="col-md-3"></div>
        <p class="col-md-6 mb-2 mt-2" style="text-align: center">
            <img class="shadowed" src="/imgs/spinach.png" height="50vh" alt="Spinach"></p>
    </div>

    <hr class="featurette-divider">

    <div class="row">

        <div class="col-md-10">

            <div class="card shadow-lg"
                 style="width: 100%; height: 100%; margin-left: 10%; background-color: #efefef; border-color: #e0e0e0">

                <a class="card-body">

                    <#if userexists??>
                        <div class="alert alert-danger" role="alert" id="userExists">
                            Пользователь с таким email адресом уже существует
                        </div>
                    </#if>
                    <#if donotmatch??>
                        <div class="alert alert-danger" role="alert" id="doNotMatch">
                            Пароли не совпадают
                        </div>
                    </#if>
                    <#if donotfillall??>
                        <div class="alert alert-danger" role="alert" id="doNotFillAll">
                            Пожалуйста, заполните все поля
                        </div>
                    </#if>

                    <form method="post" action="/register" enctype = "multipart/form-data" id="regForm">

                        <h6 style="margin-bottom: 15px; font-family: 'Montserrat'">Зарегистрируйтесь сейчас:</h6>

                        <div class="form-group col-md-10">

                            <label for="validationTooltipEmail">Email адрес</label>

                            <div class="input-group">

                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="validationTooltipEmailPrepend">
                                        <img src="/imgs/icon/icons8-эл.-адрес-filled-50.png" class="img-icon" >
                                    </span>
                                </div>

                                <input type="email" name="email"
                                       style="font-family: 'Montserrat'"
                                       class="form-control" id="validationTooltipEmail"
                                       placeholder="someemail@example.com"
                                       aria-describedby="validationTooltipEmailPrepend"
                                       required=""
                                       autofocus=""
                                       onclick="deleteRegisterAlert()">

                                <div class="invalid-tooltip">
                                    Введите Ваш валидный email адрес
                                </div>

                            </div>

                        </div>

                        <div class="row col-md-10">

                            <div class="form-group col-md-5">

                                <label for="validationTooltipName">Имя</label>

                                <div class="input-group">

                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="validationTooltipNamePrepend">
                                            <img src="/imgs/icon/icons8-добавить-пользователя-filled-50(1).png" class="img-icon">
                                        </span>
                                    </div>

                                    <input type="text" name="name"
                                           style="font-family: 'Montserrat'"
                                           class="form-control" id="validationTooltipName"
                                           placeholder="Иван"
                                           aria-describedby="validationTooltipEmailPrepend"
                                           required=""
                                           autofocus=""
                                           onclick="deleteRegisterAlert()">

                                    <div class="invalid-tooltip">
                                        Введите ваше имя
                                    </div>

                                </div>

                            </div>

                            <div class="form-group col-md-5">

                                <label for="validationTooltipEmail">Фамилия</label>

                                <div class="input-group">

                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="validationTooltipSurnamePrepend">
                                            <img src="/imgs/icon/icons8-добавить-пользователя-filled-50.png" class="img-icon">
                                        </span>
                                    </div>

                                    <input type="text" name="surname"
                                           style="font-family: 'Montserrat'"
                                           class="form-control" id="validationTooltipSurname"
                                           placeholder="Иванов"
                                           aria-describedby="validationTooltipEmailPrepend"
                                           required=""
                                           autofocus=""
                                           onclick="deleteRegisterAlert()">

                                    <div class="invalid-tooltip">
                                        Введите Вашу фамилию
                                    </div>

                                </div>

                            </div>

                        </div>

                        <div class="row col-md-10">

                            <div class="form-group col-md-5">

                                <label for="validationTooltipEmail">Дата рождения</label>

                                <div class="input-group">

                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="validationTooltipBirthdatePrepend">
                                            <img src="/imgs/icon/icons8-день-рождения-24.png" class="img-icon">
                                        </span>
                                    </div>

                                    <input type="date" name="birthdate"
                                           style="font-family: 'Montserrat'"
                                           class="form-control" id="validationTooltipBirthdate"
                                           placeholder="someemail@example.com"
                                           aria-describedby="validationTooltipEmailPrepend"
                                           required=""
                                           autofocus=""
                                           onclick="deleteRegisterAlert()">

                                    <div class="invalid-tooltip">
                                        Введите вашу дату рождения
                                    </div>

                                </div>

                            </div>

                            <div class="form-group col-md-5">

                                <label for="validationTooltipEmail">Город</label>

                                <div class="input-group">

                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="validationTooltipCityPrepend">
                                            <img src="/imgs/icon/icons8-город-50.png" class="img-icon">
                                        </span>
                                    </div>

                                    <input type="text" name="city"
                                           style="font-family: 'Montserrat'"
                                           class="form-control" id="validationTooltipCity"
                                           placeholder="город"
                                           aria-describedby="validationTooltipEmailPrepend"
                                           required=""
                                           autofocus=""
                                           onclick="deleteRegisterAlert()">

                                    <div class="invalid-tooltip">
                                        Введите Ваш город
                                    </div>

                                </div>

                            </div>

                        </div>

                        <div class="row col-md-10">

                            <div class="form-group col-md-5">

                                <label for="validationTooltipEmail">Пароль</label>

                                <div class="input-group">

                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="validationTooltipPasswordPrepend">
                                            <img src="/imgs/icon/icons8-пароль-24.png" class="img-icon">

                                        </span>
                                    </div>

                                    <input type="password" name="password"
                                           style="font-family: 'Montserrat'"
                                           class="form-control" id="validationTooltipPassword"
                                           placeholder="пароль"
                                           aria-describedby="validationTooltipEmailPrepend"
                                           required=""
                                           autofocus=""
                                           onclick="deleteRegisterAlert()">

                                    <div class="invalid-tooltip">
                                        Введите Ваш пароль
                                    </div>

                                </div>

                            </div>

                            <div class="form-group col-md-5">

                                <label for="validationTooltipEmail">Подтвердите пароль</label>

                                <div class="input-group">

                                    <div class="input-group-prepend">
                                        <span class="input-group-text"
                                              id="validationTooltipPasswordConfirmPrepend">
                                            <img src="/imgs/icon/icons8-пароль-24(1).png" class="img-icon">
                                        </span>
                                    </div>

                                    <input type="password" name="confirmpassword"
                                           style="font-family: 'Montserrat'"
                                           class="form-control" id="validationTooltipPasswordConfirm"
                                           placeholder="подтвердите пароль"
                                           aria-describedby="validationTooltipEmailPrepend"
                                           required=""
                                           autofocus=""
                                           onclick="deleteRegisterAlert()">

                                    <div class="invalid-tooltip">
                                        Подтвердите Ваш пароль
                                    </div>

                                </div>

                            </div>

                        </div>

                        <div class="form-group col-md-8">

                            <label for="validationTooltipEmail">Фото</label>

                            <div class="input-group">

                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="validationTooltipPhotoPrepend">
                                        <img src="/imgs/icon/icons8-изображение-24.png" class="img-icon">
                                    </span>
                                </div>

                                <input type="file" name="file"
                                       style="font-family: 'Montserrat'"
                                       class="form-control" id="validationTooltipPhoto"
                                       placeholder="someemail@example.com"
                                       aria-describedby="validationTooltipEmailPrepend"
                                       <#--required=""-->
                                       autofocus=""
                                       onclick="deleteRegisterAlert()">

                            </div>

                        </div>


                        <!--<input class="form-control mr-sm-2-2" type="email" placeholder="Email" aria-label="First name"-->
                        <!--id="email" style="margin-bottom: 10px; font-family: 'Montserrat'" name="email">-->

                        <!--<input class="form-control mr-sm-2-2" type="password" placeholder="Пароль"-->
                        <!--aria-label="First name" id="password" name="pass"-->
                        <!--style="margin-bottom: 10px; font-family: 'Montserrat'">-->

                        <div class="container p-0" style="margin-bottom: 10px">
                            <div class="row">

                                <div class="col-md-12">
                                    <input class="btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2 "
                                           type="submit" name=""
                                           value="Регистрация"
                                           onclick="deleteRegisterAlert()">
                                </div>

                                <#--<div class="col-md-6 pl-1">-->
                                    <#--<button class="btn btn-block btn-outline-secondary-2 " type="submit" name=""-->
                                            <#--value="Log in with VK" style="margin-bottom: 10px"><img class="img-fluid"-->
                                                                                                    <#--src="/imgs/vk-logo-png-white-2.png"-->
                                                                                                    <#--style="height: 17px; margin-right: 5px; margin-bottom: 3px"-->
                                                                                                    <#--onclick="deleteRegisterAlert()">Регистрация через-->
                                        <#--с помощью ВК-->
                                    <#--</button>-->
                                <#--</div>-->
                            </div>
                        </div>


                        <!--<h6 style="margin-bottom: 10px">If you don't have an account:</h6>-->
                    </form>
                </a>
            </div>

        </div>

    </div>
</div>
<script src="/js/register.js"></script>
</body>
</html>