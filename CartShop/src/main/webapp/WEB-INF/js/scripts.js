/*
 * Developed by Razil Minneakhmetov on 11/12/18 3:52 PM.
 * Last modified 11/12/18 3:52 PM.
 * Copyright © 2018. All rights reserved.
 */
var total = 0;

function addToCart(productId) {
    var xhr = new XMLHttpRequest();
    var body = 'productId=' + productId;
    xhr.open("POST", '/cart', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(body);
}

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}


function sendProductId() {
    $.ajax({
        type: 'post',
        url: '/usercart'

    }).done(function (data) {
        var contentTableHTML = '<div class="container p-0">';
        contentTableHTML += "<ul class=\"list-group\">";
        total = 0;

        for (var i = 0; i < data.length; i++) {
            total += Number(data[i].price);
            contentTableHTML += '<li class="list-group-item pb-0" >';

            contentTableHTML += '<div class="row">';
            contentTableHTML += '<div class="col-md-2">';
            contentTableHTML += '<img src="' + data[i].user.photoURL + '" height="50px" style="margin-bottom: 15px">';
            contentTableHTML += '</div>';
            contentTableHTML += '<div class="col-md-6">';
            contentTableHTML += '<h5 class="card-title" style="color: #495057">' + data[i].user.name + ' ' + data[i].user.surname;
            contentTableHTML += "</h5>";
            contentTableHTML += '<h6 class="card-subtitle mb-2 text-muted"> Price: ' + data[i].price + ' Rub. </h6>';
            contentTableHTML += '</div>';
            contentTableHTML += '<div class="col-md-4">';

            contentTableHTML += "<button type=\"button\" class=\"btn btn-outline-secondary-1 btn-block\" onclick=\"deleteFromCart(" + data[i].id + ", " + data[i].price + ")\">Delete</button> ";
            contentTableHTML += '</div>';
            contentTableHTML += '</div>  </li>'
        }
        contentTableHTML += '<li class="list-group-item">';

        if (total === 0) {
            contentTableHTML += '<h2>The cart is empty</h2>'
        } else {
            contentTableHTML += '<div class="row">';
            contentTableHTML += '<div class="col-md-8 text-right">';
            contentTableHTML += '<h2 class="text-right">Total: ' + total + ' Rub</h2>';
            contentTableHTML += '</div>';
            contentTableHTML += '<div class="col-md-4 ">';
            contentTableHTML += '<input type="hidden" name="sum" value="' + total + '" >';
            contentTableHTML += '<button type="submit" onclick="inputs()" class="btn btn-outline-secondary-1 btn-block" data-toggle="modal"\n' +
                '                data-target="#pay">Checkout</button>';
            contentTableHTML += '</div> </div> </div> </li>';

        }
        //contentTableHTML += "</ul> </div>";

        var contentTableDiv = document.getElementById("table");
        contentTableDiv.innerHTML = contentTableHTML;

    }).fail(function () {
        alert("НЕ ОЧ");
    });
}

function deleteFromCart(productId, price) {
    total -= price;
    var xhr = new XMLHttpRequest();
    var body = 'productId=' + productId;
    xhr.open("POST", '/deletecart', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(body);
}


function inputs() {
    var contentHTML = '<h3>What payment type do you prefer?</h3>';
    contentHTML += '<input type="hidden" name="receiver" value="41001698207752">';
    //contentHTML += '<input type="hidden" name="formcomment" value="Проект «Железный человек»: реактор холодного ядерного синтеза">';
    //contentHTML += '<input type="hidden" name="short-dest" value="Проект «Железный человек»: реактор холодного ядерного синтеза">';
    contentHTML += '<input type="hidden" name="label" value="$order_id">';
    contentHTML += '<input type="hidden" name="quickpay-form" value="donate">';
    contentHTML += '<input type="hidden" name="targets" value="Покупка услуг">';
    contentHTML += '<input type="hidden" name="sum" value="' + total + '" data-type="number">';
    // contentHTML += '<input type="hidden" name="comment" value="Хотелось бы получить дистанционное управление.">';
    contentHTML += '<input type="hidden" name="need-fio" value="false">';
    contentHTML += '<input type="hidden" name="need-email" value="false">';
    contentHTML += '<input type="hidden" name="need-phone" value="false">';
    contentHTML += '<input type="hidden" name="need-address" value="false">';
    contentHTML += '<input type="hidden" name="successURL" value="http://localhost:8000/checkouted">';
    contentHTML += '<div class="btn-group btn-group-toggle btn-block" data-toggle="buttons">';
    contentHTML += '<label class="btn btn-outline-secondary-2 btn-block" style="margin-top: 8px">';
    contentHTML += '<input type="radio" name="paymentType" value="PC" id="option1" autocomplete="off" checked>Yandex.Money';
    contentHTML += '</label>';
    contentHTML += '<label class="btn btn-outline-secondary-2 btn-block">';
    contentHTML += '<input type="radio" name="paymentType" value="AC" id="option2" autocomplete="off">Credit card';
    contentHTML += '</label>';
    contentHTML += '<label class="btn btn-outline-secondary-2 btn-block" style="border-left-color: transparent">';
    contentHTML += '<input type="radio" name="paymentType" value="MC" id="option3" autocomplete="off">Mobile';
    contentHTML += '</label>';
    contentHTML += '</div>';
    contentHTML += '<input class="btn btn-outline-secondary-1 btn-block" type="submit" value="Pay">';
    var contentTableDiv = document.getElementById("inputs");
    contentTableDiv.innerHTML = contentHTML;
}

function checkCookies() {
    $.ajax({
        type: 'post',
        url: '/getUser',

    }).done(function (data) {

        var contentHTML = '';
        if (data.vkId != undefined) {
            contentHTML += '<div class="col-md-2 px-0">'
            contentHTML += '<img src="' + data.photoURL + '" height="40" style="margin-right: 19px; border: 1px solid lightgray" class="rounded shadow"> </div>';
            contentHTML += '<div class="col-md-6 px-0" style="padding-right:15px !important">'
            contentHTML += '    <h5 style="margin-right: 10px; margin-top: 5px; color: lightgray">' + data.name;
            contentHTML += '    </h5> </div>';
            contentHTML += '<div class="col-md-3 px-0">'
            contentHTML += '    <a href="/logout">';
            contentHTML += '    <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" style="margin-right: 9px">Log out</button>';
            contentHTML += ' </a> </div>'
            var contentHTMLCart = '';
            contentHTMLCart += '<div class="dropdown">';
            contentHTMLCart += '<button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" style="margin-bottom:2px !important; margin-right: 9px; padding-bottom: 6px; padding-top: 6px " data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="sendProductId()">';
            contentHTMLCart += '<i class="fa fa-shopping-cart" aria-hidden="true"></i>';
            contentHTMLCart += '</button>';
            contentHTMLCart += '<div class="dropdown-menu dropdown-menu-right py-0 shadow-lg" style="width: 500px">';
            contentHTMLCart += '<ul class="list-group" id="table" style="overflow-y:scroll; max-height:80vh;">';
            contentHTMLCart += '</ul>';

            var contentTableDivCart = document.getElementById("cart");
            contentTableDivCart.innerHTML = contentHTMLCart;
        } else {
            contentHTML += '<a href="/auth">';
            contentHTML += '<button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" style="margin-right: 9px ">Log in';
            contentHTML += '</button>';
            contentHTML += '</a>';
        }
        var contentTable = document.getElementById("login");
        contentTable.innerHTML = contentHTML;

}).fail(function () {
        alert("НЕ ОЧ");
    });
}

function toggle() {
    $('#myModal').modal('toggle');
}

function deleteFromSeller(productId) {
    $.ajax({
        type: "post",
        url: "/deleteSeller",
        data:{
            productId: productId
        }
    }).done(function (data) {
       document.getElementById("sale" + productId).remove();
    });
}

function deleteFromBuyer(productId) {
    $.ajax({
        type: "post",
        url: "/deleteBuyer",
        data:{
            productId: productId
        }
    }).done(function (data) {
        document.getElementById("order" + productId).remove();
    });
}

