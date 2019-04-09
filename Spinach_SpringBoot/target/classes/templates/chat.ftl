<!DOCTYPE html>
<html>
<head>
    <title>Чаты</title>
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

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet"
          id="bootstrap-css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <#--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>-->
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Rubik" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css"
          rel="stylesheet">
    <link href="/styles/style.css" rel="stylesheet">
    <script src="/js/getChats.js"></script>
    <script src="/js/sockjs.js"></script>
    <script src="/js/stomp.js"></script>
    <script src="/js/showAlert.js"></script>
</head>


<body background="/imgs/fonchat.jpg" onload="getChats()">

<div class="modal fade" id="friendRequest" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Заявка в друзья</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Собеседник хочет сохранить с Вами связь.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="accept()">Добавить в друзья</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="notAccept()">Не добавлять в друзья</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="notConfirm" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Заявка в друзья</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Собеседник отказался добавлять Вас в друзья.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                <#--<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="notAccept()">Не добавлять в друзья</button>-->
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="questionCancel" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Заявка в друзья</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Вы действительно хотите отменить поиск собеседника?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="cancelSearching()">Отменить</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">Не отменять</button>
            </div>
        </div>
    </div>
</div>



<div class="container">
    <div class="row">
        <div class="col-md-3"></div>
        <p class="col-md-6 mb-2 mt-2" style="text-align: center"><img class="shadowed" src="/imgs/spinach.png"

                                                                      height="50vh"></p>
        <p class="col-md-3 mb-2 mt-2 shadowed"
           style="text-align: right; font-size: large; margin-top: 1% !important; font-family: 'Rubik'"
           id="profileInfo">
            </p>
    </div>
    <div class="messaging">
        <div class="inbox_msg">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Сохраненные</h4>
                    </div>
                    <#--<div class="srch_bar">-->
                        <#--<div class="stylish-input-group">-->
                            <#--<input type="text" class="search-bar" placeholder="Search">-->
                            <#--<span class="input-group-addon">-->
                <#--<button type="button"> <i class="fa fa-search" aria-hidden="true"></i> </button>-->
                <#--</span></div>-->
                    <#--</div>-->
                </div>
                <div class="inbox_chat" id="chats">
                    <#--<img src="imgs/spinach_leaves.png" class="img-fluid" style="visibility: visible !important; display: block !important; text-align: center !important" id="chatsload">-->
                </div>
            </div>
            <div class="mesgs" id="chatWindow">

                <div class="msg_history shadowed" id="chatbox">
                    <div id="chat">

                        <span class="Centered-3">
                            Выберите чат слева или найдите нового собеседника
                        </span>
                    </div>
                </div>

                <div class="send-message" id="sender">

                </div>
            </div>

        </div>
    </div>
</div>
<script src="/js/sendMessage.js"></script>
<script src="/js/socket.js"></script>

</body>
</html>