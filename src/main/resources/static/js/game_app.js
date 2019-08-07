// import { connect } from 'ws/ws-app';
// import { disconnect } from 'ws/ws-app';
// import { sendMessage } from 'ws/ws-app';

var timeNow = 0;

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
    console.log(message);
    if (message.playStatus === 'PUCK') {
        console.log(message);
    }
    statusText.text(message.playStatus);
    timeText.text(convertMs(message.tick) + '');
    scoreMyText.text(message.player1.playAccount + '');
    scoreEnemyText.text(message.player2.playAccount + '');
    enemyBat.cx(message.player2.x + fieldX).cy(fieldHeight - message.player2.y + fieldY);
    myBat.cx(message.player1.x + fieldX).cy(fieldHeight - message.player1.y + fieldY);
    rpuck.cx(message.puck.x + fieldX).cy(fieldHeight - message.puck.y + fieldY);
}

function convertMs(ms) {
    min = 0|((ms - timeNow)/1000/60);
    sec = 0|((ms - timeNow)/1000) % 60;
    return min + ':' + sec;
}

function setStart(id) {
    jQuery.get("/app/start", {gameId:id});
    timeNow = new Date().getTime();
}

function setBot(id) {
    jQuery.get("/app/bot-game", {gameId:id, level: 'MIDDLE'});
    // jQuery.post("/app/bot-game", {gameId:id, level: 'MIDDLE'});
    timeNow = new Date().getTime();
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); openChanel($("#socketid").val(), showMessage);});
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#start" ).click(function () { setStart($("#socketid").val());});
    $( "#bot" ).click(function () { setBot($("#socketid").val());});
});