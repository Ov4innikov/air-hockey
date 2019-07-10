// import { connect } from 'ws/ws-app';
// import { disconnect } from 'ws/ws-app';
// import { sendMessage } from 'ws/ws-app';

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
    $( "#connect" ).click(function() { connect(); openChanel($('#socketid').val(), showMessage);});
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage($('#socketid').val(), { 'from': 'mememe', 'message': $("#name").val() }); });
    $( "#serversay" ).click(function () { setServerSay($('#socketid').val()); })
});