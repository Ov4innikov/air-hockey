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
    console.log(message);
    if (message.playStatus === 'BREAK') {
        console.log('gracias');
        disconnect();
        if (user1 != -1) jQuery.get("/app/end", {gameId:$("#gameId").val()});
        else jQuery.get("/app/bot-game-end", {gameId:$("#gameId").val()});
        return;
    }
    statusText.text(message.playStatus);
    timeText.text(message.tick + '');
    scoreMyText.text(message.player1.score + '');
    scoreEnemyText.text(message.player2.score + '');
    enemyBat.cx(message.player2.x).cy( fieldHeight - message.player2.y);
    myBat.cx(message.player1.x).cy(fieldHeight - message.player1.y);
    rpuck.cx(message.puck.x).cy(fieldHeight - message.puck.y);
}

function setStart(id,user1,user2) {
    if (user1 != -1) jQuery.get("/app/start", {gameId:id ,user1:user1, user2:user2});
    else jQuery.get("/app/bot-game", {gameId:id , level:'MIDDLE', user: user2});
}

function setBot(id) {
    jQuery.get("/app/bot-game", {gameId:id, level: 'MIDDLE'});
    // jQuery.post("/app/bot-game", {gameId:id, level: 'MIDDLE'});
}

function getCorner(x, y) {
    var corner = 0;
    if (x >= 0 && y >= 0) {
        corner = (Math.atan(Math.abs(y) / Math.abs(x)) * 180 / Math.PI) % 360;
    } else if (x < 0 && y >= 0) {
        corner = (Math.atan(Math.abs(x) / Math.abs(y)) * 180 / Math.PI + 90) % 360;
    } else if (x < 0 && y < 0) {
        corner = (Math.atan(Math.abs(y) / Math.abs(x)) * 180 / Math.PI + 180) % 360;
    } else if (x >= 0 && y < 0) {
        corner = (Math.atan(Math.abs(x) / Math.abs(y)) * 180 / Math.PI + 270) % 360;
    }

    return corner;
}
$(function () {
    let gameId = $("#gameId").val();
    connect();
    openChanel(gameId, showMessage);
    setStart(gameId,$("#user1").val(),$("#user2").val());
});