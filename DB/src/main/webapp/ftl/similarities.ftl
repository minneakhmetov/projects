<!DOCTYPE html>
<html>
<head>


    <title>Схожести</title>
<#--<link href="https://fonts.googleapis.com/css?family=Francois+One" rel="stylesheet">-->
<#--<link href="https://fonts.googleapis.com/css?family=Sunflower:300" rel="stylesheet">-->
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<#--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>-->
<#--<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>-->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>


    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/css/styles.css" rel="stylesheet">

</head>
<body>

<div class="container" style="margin-top:  140px">
    <h1 style="margin-bottom: 25px">Схожесть с вашими друзьями</h1>
    <div class="row" >
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-1">
                    <h4>No.</h4>
                </div>
                <div class="col-md-3">
                    <h4>Фото</h4>
                </div>
                <div class="col-md-5">
                    <h4>Имя Фамилия</h4>
                </div>
                <div class="col-md-3">
                    <h4>% схожести</h4>
                </div>
            </div>
        </div>
    </div>

         <#list similarityUsers as users>
         <div class="row">
             <div class="container-fluid">
                     <div class="card" style="margin-top: 15px;">
                         <div class="card-body">
                             <div class="row">
                             <div class="col-md-1">
                                 <h4>${users.no}</h4>
                             </div>
                             <div class="col-md-3">
                                 <img src="${users.photoUrl}">
                             </div>
                             <div class="col-md-5">
                                 <h4>${users.name} ${users.surname}</h4>
                             </div>
                             <div class="col-md-3">
                                 <h4>${users.similarity} %</h4>
                             </div>
                             </div>
                         </div>
                     </div>
             </div>
         </div>
         </#list>

</div>

</body>
</html>