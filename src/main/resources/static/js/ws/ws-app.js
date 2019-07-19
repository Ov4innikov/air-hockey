// TODO Доработать в моменте, что у одного пользователя может быть несколько прослушиваемых каналов
// TODO Возможно при вызове connect сперва вызывать disconnect и добавить вызов openChannel
// TODO Всё это решиться, когда начнём подключать поиск игры для игроков

let stompClient = null;

/**
 * Создаёт подключение к вебсокету
 */
function connect() {
    let socket = new SockJS('/air-hockey');
    stompClient = Stomp.over(socket);
}

/**
 * Начинает прослушивать свой канал. Для тестовой работы заменить "/topic/message/" на "/topic/test/".
 * @param chanel строковое значение, название канала
 * @param showMessage Важно! Это функция, входным значением которой будет полученнное в вебсокете сообщение. Передавать без скобок
 */
function openChanel(chanel, showMessage) {
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/message/' + chanel, function (message) {
            console.log(message.body);
            showMessage(JSON.parse(message.body));
        });
    });
}

/**
 * Закрывает прослушивание сокета
 */
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

/**
 * Отправить сообщение на сервер. Для тестовой работы заменить "/app/message/" на "/app/test/".
 * @param chanel строковое значение, название канала
 * @param message сообщение для сервера, должен быть в формате JSON
 */
function sendMessage(chanel, message) {
    stompClient.send("/app/message/" + chanel, {}, JSON.stringify(message));
}
