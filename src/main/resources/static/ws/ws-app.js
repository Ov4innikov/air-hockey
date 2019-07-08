let stompClient = null;

export function connect(id) {
    let socket = new SockJS('/air-hockey');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/message/'+id, function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

export function sendMessage(id, message) {
    stompClient.send("/app/message/"+id, {}, JSON.stringify(message));
}
