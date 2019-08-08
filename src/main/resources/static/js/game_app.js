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
    if (message.playStatus === 'BREAK') {
        disconnect();
        jQuery.get("/app/end", {gameId:$("#gameId").val()});
        return;
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
    var date = new Date(ms);
    return date.toLocaleTimeString();
}

function setStart(id,user1,user2) {
    jQuery.get("/app/start", {gameId:id ,user1:user1, user2:user2});
}

function setBot(id) {
    jQuery.get("/app/bot-game", {gameId:id, level: 'MIDDLE'});
    // jQuery.post("/app/bot-game", {gameId:id, level: 'MIDDLE'});
    timeNow = new Date().getTime();
}
$(function () {
    let gameId = $("#gameId").val();
    connect();
    openChanel(gameId, showMessage);
    setStart(gameId,$("#user1").val(),$("#user2").val());
})