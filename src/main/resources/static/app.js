// import { connect } from 'ws/ws-app';
// import { disconnect } from 'ws/ws-app';
// import { sendMessage } from 'ws/ws-app';

let stompClient = null;

function connect(id) {
    let socket = new SockJS('/air-hockey');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/test/'+id, function (message) {
            console.log(message.body);
            showMessage(JSON.parse(message.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage(id, message) {
    stompClient.send("/app/test/"+id, {}, JSON.stringify(message));
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#userinfo").html("");
}

function showMessage(message) {
    $("#userinfo").append("<tr><td>" + message.from + "</td> <td>" + message.message + "</td></tr>");
}

function setServerSay(id) {
    $.get("/app/serversay", {key:id});
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect($('#socketid').val()); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage($('#socketid').val(), { 'from': 'mememe', 'message': $("#name").val() }); });
    $( "#serversay" ).click(function () { setServerSay($('#socketid').val()); })
});