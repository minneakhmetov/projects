// function connect() {
//     var socket = new WebSocket('ws://localhost:8080/greeting');
//     ws = Stomp.over(socket);
//
//     ws.connect({}, function(frame) {
//         ws.subscribe("/user/queue/errors", function(message) {
//             alert("Error " + message.body);
//         });
//
//         ws.subscribe("/user/queue/reply", function(message) {
//             alert("Message " + message.body);
//         });
//     }, function(error) {
//         alert("STOMP error " + error);
//     });
// }
//
// function disconnect() {
//     if (ws != null) {
//         ws.close();
//     }
//     setConnected(false);
//     console.log("Disconnected");
// }


/**
 * Open the web socket connection and subscribe the "/notify" channel.
 */
function connect() {

    // Create and init the SockJS object
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    // Subscribe the '/notify' channell
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/notify', function (notification) {

            // Call the notify function when receive a notification

            let message = JSON.parse(notification.body);

            console.log(message.content);

            if (chatId === message.chatId) {
                $("<div>", {
                    class: "incoming_msg",
                    //href: "#",
                    //id: v.id, //change
                    append: $('<div>', {
                        class: "incoming_msg_img",
                        append: $('<img>', {
                            class: "rounded-circle",
                            src: photo
                        })
                    }).add($('<div>', {
                        class: "received_msg",
                        append: $('<div>', {
                            class: "received_withd_msg",
                            append: $('<p>', {
                                text: message.text
                            }).add($('<span>', {
                                class: "time_date",
                                text: message.time
                            }))
                        })
                    }))
                }).appendTo("#chat");

                let chatbox = document.getElementById("chatbox");
                chatbox.scrollTop = chatbox.scrollHeight;
            }

        });
    });

} // function connect

/**
 * Display the notification message.
 */
// function notify(message) {
//     $("#notifications-area").append(message + "\n");
//     return;
// }

/**
 * Init operations.
 */
$(document).ready(function () {

    // Start the web socket connection.
    connect();
    addUser();
    survey();
});


function addUser() {

    // Create and init the SockJS object
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    // Subscribe the '/notify' channell
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/adduser', function (notification) {

            // Call the notify function when receive a notification

            let message = JSON.parse(notification.body);
            if(message.chatId === 0 && message.partnerId === 0 && message.userId === 0 ){

                leavingChat("Все собеседники заняты. Попробуйте еще раз. ")

            } else {
                console.log(message.content);
                console.log(message.chatId);

                setTimeout(function () {
                    isSearching = false;
                    openChat(message.chatId);

                    chatsIds.forEach(function (v) {
                        let classList = document.getElementById("chat" + v).classList;
                        if (classList.contains("active_chat"))
                            classList.remove("active_chat")

                    });

                    $("<div>", {
                        class: "chat_list active_chat",
                        onclick: 'openChat(' + message.chatId + ')',
                        //href: "#",
                        id: 'chat' + message.chatId, //change
                        append: $('<div>', {
                            class: "chat_people",
                            append: $('<div>', {
                                class: "chat_img",
                                append: $('<img>', {
                                    class: "rounded-circle",
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


                }, 2000);

                //
                // if(chatId === message.chatId) {
                //     $("<div>", {
                //         class: "incoming_msg",
                //         //href: "#",
                //         //id: v.id, //change
                //         append: $('<div>', {
                //             class: "incoming_msg_img",
                //             append: $('<img>', {
                //                 class: "rounded-circle",
                //                 src: photo
                //             })
                //         }).add($('<div>', {
                //             class: "received_msg",
                //             append: $('<div>', {
                //                 class: "received_withd_msg",
                //                 append: $('<p>', {
                //                     text: message.text
                //                 }).add($('<span>', {
                //                     class: "time_date",
                //                     text: message.time
                //                 }))
                //             })
                //         }))
                //     }).appendTo("#chat");
                //
                //     let chatbox = document.getElementById("chatbox");
                //     chatbox.scrollTop = chatbox.scrollHeight;
                // }
            }
        });
    });
}

function survey() {
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    // Subscribe the '/notify' channell
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/survey', function (notification) {
            let message = JSON.parse(notification.body);
            if(message === "ALERT") {
                if(document.getElementById("survey") != null)
                    document.getElementById("survey").remove();

                showAlert();
            } else if(message === "USER_LEAVED"){
                document.getElementById("survey").remove();
                leavingChat("Собеседник вышел из беседы. ");
            }
            else if(message === "ADD_TO_FRIENDS"){
                $('#friendRequest').modal('toggle');
                continueChatting();
            }
            else if(message === "NOT_CONFIRM"){
                $('#notConfirm').modal('toggle');
                continueChatting();
            } else {
                let survey = document.getElementById("survey");
                if(survey != null)
                    survey.remove();
                addingToFriends(message);
            }
        });
    });
}