var partnerId = 0;
var chatId = 0;
var photo;
var chatsIds = [];
let isSearching = false;
let anonimChat = 0;

function logout() {
    window.location.href = "/logout";
}

function getChats() {
    //todo: сделать active
    //todo: сделать css изменение курсора
    //todo: сделать прокрутку вниз
    //todo: сделать иконку загрузки
    //todo: сделать rounded иконки

    document.getElementById("chats").innerHTML =
        '<img class="Centered" src="/imgs/spinach_leaves.png" id="chatsload">';
    //document.getElementById("sender").hidden = false;

    $.ajax({
        type: 'post',
        url: '/profileInfo'
        //data: {}
    }).done(function (data) {
        document.getElementById("profileInfo").innerHTML = data.name + '<img class="shadowed rounded-circle" src="'+ data.photo + '" width="16%" style="margin-left: 5px"> ' +
            '<button class="btn btn-outline-secondary-1" onclick="logout()">Выйти</button>'
    }).fail(function () {
        alert("НЕ ОЧ");
    });


    $.ajax({
        type: 'post',
        url: '/getChats'
        //data: {}
    }).done(function (data) {
        //let plus = '';
        //     document.createElement('div');
        // div.classList.add("chat_list", "gradient");
        // let chatPeople = document.createElement('div');
        // chatPeople.classList.add("chat_people");
        // plus.append(chatPeople);

        let objects = [];
        if(data.searching){
            isSearching = true;
            document.getElementById("chat").innerHTML =
                '<div class="Centered-3">' +
                '<img class="Centered-2" src="/imgs/spinach_leaves.png" id="chatsloadchat"> ' +
                '<span>Идет поиск собеседника...</span>'+
                '<button class="btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2" onclick="question()">Отменить</button>' +
            '</div>';

            document.getElementById("sender").innerHTML = "";
            if (data.chats != null) {
                data.chats.forEach(function (v) {
                    chatsIds.push(v.id);
                    objects.push($("<div>", {
                        class: "chat_list",
                        onclick: 'openChat(' + v.id + ')',
                        //href: "#",
                        id: 'chat' + v.id, //change
                        append: $('<div>', {
                            class: "chat_people",
                            append: $('<div>', {
                                class: "chat_img",
                                append: $('<img>', {
                                    class: "rounded-circle",
                                    src: v.photo //change
                                })
                            }).add($('<div>', {
                                class: "chat_ib",
                                append: $("<h5>", {
                                    text: v.name, //change
                                    append: $('<span>', {
                                        class: "chat_date",
                                        text: v.time
                                    })
                                }).add($('<p>', {
                                    text: v.message //change
                                }))
                            }))
                        })
                    }))
                });
            }
        } else {
            isSearching = false;
            if (data.available) {
                objects.push($("<div>", {
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
                }))
            }
            if (data.chats != null) {
                data.chats.forEach(function (v) {
                    chatsIds.push(v.id);
                    objects.push($("<div>", {
                        class: "chat_list",
                        onclick: 'openChat(' + v.id + ')',
                        //href: "#",
                        id: 'chat' + v.id, //change
                        append: $('<div>', {
                            class: "chat_people",
                            append: $('<div>', {
                                class: "chat_img",
                                append: $('<img>', {
                                    class: "rounded-circle",
                                    src: v.photo //change
                                })
                            }).add($('<div>', {
                                class: "chat_ib",
                                append: $("<h5>", {
                                    text: v.name, //change
                                    append: $('<span>', {
                                        class: "chat_date",
                                        text: v.time
                                    })
                                }).add($('<p>', {
                                    text: v.message //change
                                }))
                            }))
                        })
                    }))
                });
            }
        }
        // var contentTableDiv = document.getElementById("chats");
        // contentTableDiv.append(plus);
        document.getElementById("chatsload").remove();
        objects.forEach(function (v) {
            v.appendTo("#chats");
        })
    }).fail(function () {
        alert("НЕ ОЧ");
    });
}

function openChat(id) {

    let result = {};
    let objects = [];

    if(isSearching){
        question()
    } else {
        let survey = document.getElementById("survey")
        if(survey != null)
            survey.remove();
        document.getElementById("chat").innerHTML =
            '<img class="Centered-2" src="/imgs/spinach_leaves.png" id="chatsloadchat">';

        chatsIds.forEach(function (v) {
            let classList = document.getElementById("chat" + v).classList;
            if (v === id) {
                if (!classList.contains("active_chat")) {
                    classList.add("active_chat")
                }
            } else {
                if (classList.contains("active_chat")) {
                    classList.remove("active_chat")
                }
            }
        });


        $.ajax({
            type: 'post',
            url: '/openChat',
            data: {
                chatId: id
            }
        }).done(function (data) {
            chatId = Number(id);

            document.getElementById("chat").innerHTML = '';
            data.dtoList.forEach(function (v) {
                if (v.owner) {
                    objects.push($("<div>", {
                        class: "outgoing_msg",
                        //href: "#",
                        //id: v.id, //change
                        append: $('<div>', {
                            class: "sent_msg",
                            append: $('<p>', {
                                text: v.text
                            }).add($('<span>', {
                                class: "time_date",
                                text: v.time
                            }))
                        })
                    }));

                } else {
                    photo = data.photo;
                    objects.push($("<div>", {
                        class: "incoming_msg",
                        append: $('<div>', {
                            class: "incoming_msg_img",
                            append: $('<img>', {
                                class: "rounded-circle",
                                src: data.photo
                            })
                        }).add($('<div>', {
                            class: "received_msg",
                            append: $('<div>', {
                                class: "received_withd_msg",
                                append: $('<p>', {
                                    text: v.text
                                }).add($('<span>', {
                                    class: "time_date",
                                    text: v.time
                                }))
                            })
                        }))
                    }));
                }
                let survey = document.getElementById("survey");
                if (data.surveyEnum != null && survey === null) {
                    anonimChat = id;
                    if (data.surveyEnum === "MAIN") {
                        showAlert();
                    }
                    if (data.surveyEnum === "YES") {
                        surveyYes();
                    }
                    if (data.surveyEnum === "NO") {
                        surveyNo();
                    }
                }
                result.time = v.time.substring(0, 6);
                result.text = 'Вы: ' + v.text; //todo: локали
                result.name = 'Анонимный пользователь'; //todo : переделать
                result.photo = '/usersPhotos/default/anonim_100.jpg';  //todo : переделать
                objects.forEach(function (v) {
                    v.appendTo("#chat");
                });
            });

            document.getElementById("sender").innerHTML = "";
            $('<div>', {
                class: "col",
                append: $("<form>", {
                    class: "input_msg_write clearfix",
                    id: "form_send",
                    autocomplete: "off",
                    append: $("<input>", {
                        type: "text",
                        class: "write_msg",
                        placeholder: "Введите сообщение", //todo: локали
                        name: "inputPlace",
                        autocomplete: "off"
                    }).add($("<button>", {
                        class: "msg_send_btn",
                        type: "submit",
                        append: $("<i>", {
                            class: "fa fa-paper-plane-o"
                        })
                    }))
                })
            }).appendTo("#sender");
            sendMessage();
            let chatbox = document.getElementById("chatbox");
            chatbox.scrollTop = chatbox.scrollHeight;
            partnerId = Number(data.partnerId);
        }).fail(function () {
            alert("НЕ ОЧ");
        });
    }
    return result;

}


function startChat() {
    document.getElementById("chat").innerHTML =
        '<div class="Centered-3">' +

        '<img class="Centered-2" src="/imgs/spinach_leaves.png" id="chatsloadchat"> ' +
        '<span>Идет поиск собеседника...</span>'+
       // '<span>Идет поиск собеседника...</span>'+
        '<button class="btn fluid btn-outline-secondary-1 form-control mr-sm-2-2-2" onclick="question()">Отменить</button>'+
        '</div>';
        document.getElementById("plus").remove();
        document.getElementById("sender").innerHTML = "";

    $.ajax({
        type: 'post',
        url: '/startChat'
        //data: {}
    }).done(function (data) {

        if(data === "") {
            isSearching = true;
        } else {
           // document.getElementById("plus").remove();
            isSearching = false;
            openChat(data.chatId);
            $("<div>", {
                class: "chat_list active_chat",
                onclick: 'openChat(' + data.chatId + ')',
                //href: "#",
                id: 'chat' + data.chatId, //change
                append: $('<div>', {
                    class: "chat_people",
                    append: $('<div>', {
                        class: "chat_img",
                        append: $('<img>', {
                            class : "rounded-circle",
                            src: "/usersPhotos/default/anonim_100.jpg" //todo: change
                        })
                    }).add($('<div>', {
                        class: "chat_ib",
                        append: $("<h5>", {
                            text: "Анонимный пользователь", //todo: change
                            append: $('<span>', {
                                class: "chat_date",
                                text: "Сегодня" //todo: change
                            })
                        }).add($('<p>', {
                            text: "Аноним: Привет, я твой новый собеседник!" //todo: change
                        }))
                    }))
                })
            }).prependTo("#chats")
        }
        //document.getElementById("profileInfo").innerHTML = data.name + '<img class="shadowed rounded-circle" src="' + data.photo + '" width="16%" style="margin-left: 5px">'
    }).fail(function () {
        alert("НЕ ОЧ");
    });
}
