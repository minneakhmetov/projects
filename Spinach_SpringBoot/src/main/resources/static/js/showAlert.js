function showAlert() {
    $("<div>", {
        class: "alert alert-success",
        role: "alert",
        id: "survey",
        append: $('<div>', {
            class: "containter",
            append: $("<div>", {
                class: "row",
                append: $("<div>", {
                    class: "col-md-6",
                    append: $("<span>", {
                        text: "Нравится ли вам новый собеседник?"
                    })
                }).add($("<div>", {
                    class: "col-md-3",
                    append: $('<button>', {
                        class: "btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2",
                        text: "Да",
                        onclick: "surveyYes()"
                    })})).add($("<div>", {
                    class: "col-md-3",
                    append:($('<button>', {
                    class: "btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2",
                    text: "Нет",
                    onclick: "surveyNo()"
                }))}))
            })
        })}).prependTo("#chatWindow");
}

function surveyYes() {

    let survey = document.getElementById("survey");

    if(survey != null)
        survey.remove();

    $("<div>", {
        class: "alert alert-success",
        role: "alert",
        id: "survey",
        append: $('<div>', {
            class: "containter",
            append: $("<div>", {
                class: "row",
                append: $("<div>", {
                    class: "col-md-6",
                    append: $("<span>", {
                        text: "Хотите ли Вы сохранить связь с новым собеседником?"
                    })
                }).add($("<div>", {
                    class: "col-md-3",
                    append: $('<button>', {
                        class: "btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2",
                        text: "Да",
                        onclick: "invite()"
                    })})).add($("<div>", {
                    class: "col-md-3",
                    append:($('<button>', {
                    class: "btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2",
                    text: "Нет",
                    onclick: "continueChatting()"
                }))}))
            })
        })}).prependTo("#chatWindow");
    $.ajax({
        type: 'post',
        url: '/liked'
        //data: {}
    }).done(function (data) {
    }).fail(function () {
        alert("НЕ ОЧ");
    })


}

function surveyNo() {
    let survey = document.getElementById("survey");

    if(survey != null)
        survey.remove();


    $("<div>", {
        class: "alert alert-success",
        role: "alert",
        id: "survey",
        append: $('<div>', {
            class: "containter",
            append: $("<div>", {
                class: "row",
                append: $("<div>", {
                    class: "col-md-6",
                    append: $("<span>", {
                        text: "Хотите ли Вы продолжить общаться с новым собеседником?"
                    })
                }).add($("<div>", {
                    class: "col-md-3",
                    append: $('<button>', {
                        class: "btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2",
                        text: "Да",
                        onclick: "continueChatting()"
                    })})).add($("<div>", {
                    class: "col-md-3",
                    append:($('<button>', {
                    class: "btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2",
                    text: "Нет",
                    onclick: "leaveChat()"
                }))}))
            })
        })}).prependTo("#chatWindow");
    $.ajax({
        type: 'post',
        url: '/notLiked'
        //data: {}
    }).done(function (data) {
    }).fail(function () {
        alert("НЕ ОЧ");
    });
}

// function saveToFriends() {
//     document.getElementById("survey").remove();
//     $.ajax({
//         type: 'post',
//         url: '/saveToFriends'
//         //data: {}
//     }).done(function (data) {
//         addingToFriends(data);
//     }).fail(function () {
//         alert("НЕ ОЧ");
//     });
// }

function leaveChat() {
    document.getElementById("survey").remove();
    document.getElementById("chat").innerHTML =
        '<img class="Centered-2" src="/imgs/spinach_leaves.png" id="chatsload">';
    $.ajax({
        type: 'post',
        url: '/leaveChat'
        //data: {}
    }).done(function (data) {
        leavingChat("Вы вышли из беседы. ");
    }).fail(function () {
        alert("НЕ ОЧ");
    });
}

function continueChatting() {
    document.getElementById("survey").remove();
    $.ajax({
        type: 'post',
        url: '/continueChatting'
        //data: {}
    }).done(function (data) {

    }).fail(function () {
        alert("НЕ ОЧ");
    });
}

function accept() {
 //   document.getElementById("survey").remove();
    $.ajax({
        type: 'post',
        url: '/accept'
        //data: {}
    }).done(function (data) {
        addingToFriends(data);
    }).fail(function () {
        alert("НЕ ОЧ");
    });
}

function invite() {
    document.getElementById("survey").remove();
    $.ajax({
        type: 'post',
        url: '/addToFriends'
        //data: {}
    }).done(function (data) {

    }).fail(function () {
        alert("НЕ ОЧ");
    });
}

function notAccept() {
    //document.getElementById("survey").remove();
    $.ajax({
        type: 'post',
        url: '/notAccept'
        //data: {}
    }).done(function (data) {

    }).fail(function () {
        alert("НЕ ОЧ");
    });
}

function leavingChat(message) {
    document.getElementById("chat").innerHTML =
        '<span class="Centered-3">' + message +
        'Выберите чат слева или найдите нового собеседника' +
        '</span>';
    let chatIdSt = "chat" + chatId;

    let chatName = document.getElementById(chatIdSt);

    if(chatName != null)
        chatName.remove();

    $("<div>", {
        class: "chat_list gradient",
        onclick: 'startChat()',
        //href: "#",
        id: "plus", //change
        append: $('<div>', {
            class: "chat_people",
            append: $('<div>', {
                class: "chat_img",

                append: $('<img>', {
                    // class: "",
                    css: {
                        paddingRight: '10px'
                    },
                    src: "/imgs/plus.png" //todo: change
                })
            }).add($('<div>', {
                // class: "ml-1",
                append: $("<h5>", {
                    class: "mt-1 shadowed",
                    css: {
                        color: "white",

                        // paaddingBottom: '5px'
                    },
                    text: ' Новый собеседник', //todo: change
                })
            }))
        })
    }).prependTo("#chats");
    document.getElementById("sender").innerHTML = "";
}

function addingToFriends(data) {
    let chatId = "chat" + data.id;
    document.getElementById(chatId).innerHTML = "";
    $('<div>', {
        class: "chat_people",
        append: $('<div>', {
            class: "chat_img",
            append: $('<img>', {
                class: "rounded-circle",
                src: data.photo //change
            })
        }).add($('<div>', {
            class: "chat_ib",
            append: $("<h5>", {
                text: data.name, //change
                append: $('<span>', {
                    class: "chat_date",
                    text: data.time
                })
            }).add($('<p>', {
                text: data.message //change
            }))
        }))
    }).appendTo("#" + chatId);

    $("<div>", {
        class: "chat_list gradient",
        onclick: 'startChat()',
        //href: "#",
        id: "plus", //change
        append: $('<div>', {
            class: "chat_people",
            append: $('<div>', {
                class: "chat_img",

                append: $('<img>', {
                    // class: "",
                    css: {
                        paddingRight: '10px'
                    },
                    src: "/imgs/plus.png" //todo: change
                })
            }).add($('<div>', {
                // class: "ml-1",
                append: $("<h5>", {
                    class: "mt-1 shadowed",
                    css: {
                        color: "white",

                        // paaddingBottom: '5px'
                    },
                    text: ' Новый собеседник', //todo: change
                })
            }))
        })
    }).prependTo("#chats");
}

function question() {
    $('#questionCancel').modal('toggle');
}


function cancelSearching() {
    $.ajax({
        type: 'post',
        url: '/cancelSearching'
        //data: {}
    }).done(function (data) {
        isSearching = false;
        if(data) {
            document.getElementById("chat").innerHTML =
                '<span class="Centered-3">' +
                'Выберите чат слева или найдите нового собеседника' +
                '</span>';
            $("<div>", {
                class: "chat_list gradient",
                onclick: 'startChat()',
                //href: "#",
                id: "plus", //change
                append: $('<div>', {
                    class: "chat_people",
                    append: $('<div>', {
                        class: "chat_img",

                        append: $('<img>', {
                            // class: "",
                            css: {
                                paddingRight: '10px'
                            },
                            src: "/imgs/plus.png" //todo: change
                        })
                    }).add($('<div>', {
                        // class: "ml-1",
                        append: $("<h5>", {
                            class: "mt-1 shadowed",
                            css: {
                                color: "white",

                                // paaddingBottom: '5px'
                            },
                            text: ' Новый собеседник', //todo: change
                        })
                    }))
                })
            }).prependTo("#chats");
        }
    }).fail(function () {
        alert("НЕ ОЧ");
    });
}

