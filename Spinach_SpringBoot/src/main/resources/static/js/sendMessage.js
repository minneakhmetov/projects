let messId = 0;

function sendMessage() {
    $('#form_send').on('submit', function (e) {
        console.log(e);

        // отменяю дефолтную отправку формы
        e.preventDefault();

        // Получаю все поля формы как массив. Конвертирую массив в объект для отправки.
        let dataToSend = {};
        $('#form_send').serializeArray().forEach(function (el) {
            dataToSend[el.name] = el.value;
        });
        dataToSend.partnerId = partnerId;
        dataToSend.chatId = chatId;

        if(dataToSend.inputPlace != "") {

            messId++;

            $("<div>", {
                class: "outgoing_msg",
                //href: "#",
                //id: v.id, //change
                append:
                    $('<div>', {
                        //id: "message",
                        class: "sent_msg",
                        append: $('<p>', {
                            text: dataToSend.inputPlace
                        }).add($('<span>', {
                            class: "time_date",
                            id: "time" + messId,
                            text: "Отправка..."
                        }))
                    })
            }).appendTo("#chat");

            let chatbox = document.getElementById("chatbox");
            chatbox.scrollTop = chatbox.scrollHeight;


            // Отправляю данные ajax'ом
            $.ajax({
                type: "POST",
                url: "/sendMessage",
                data: dataToSend,
                success: {},
                dataType: 'json'
            }).done(function (data) {
                $("#time" + messId).text(data.time)
            }).fail(function (data) {
                document.getElementById("#time" + messId).style.color = "red";
                $("#time" + messId).text("Ошибка")
            });

            // Очищаю форму
            $(':input', '#form_send')
                .not(':button, :submit, :reset, :hidden')
                .val('')
                .prop('checked', false)
                .prop('selected', false);
        }
    });
}