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
    statusText.text(message.playStatus);
    timeText.text(message.tick + '');
    scoreMyText.text(message.player2.score + '');
    scoreEnemyText.text(message.player1.score + '');
    enemyBat.x(message.player1.x + fieldX + batBorder).y(message.player1.y + fieldY + batBorder);
    myBat.x(message.player2.x + fieldX + batBorder).y(message.player2.y + fieldY + batBorder);
    rpuck.x(message.puck.x + message.puck.speed.x + fieldX - fieldBorder/2 - rpuckRadius).y(message.puck.y + message.puck.speed.x + fieldY - fieldBorder/2 - rpuckRadius);
}

function setDemoSay(id) {
    $.get("/app/demosay", {key:id});
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); openChanel($('#socketid').val(), showMessage);});
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#demo" ).click(function () { setDemoSay($('#socketid').val());});
});