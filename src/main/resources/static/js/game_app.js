function showMessage(message) {
    console.log(message);
    if (message.playStatus === 'BREAK') {
        disconnect();
        if (user1 != -1) jQuery.get("/app/end", {gameId:$("#gameId").val()});
        else jQuery.get("/app/bot-game-end", {gameId:$("#gameId").val()});
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
    if (user1 != -1) jQuery.get("/app/start", {gameId:id ,user1:user1, user2:user2});
    else jQuery.get("/app/bot-game", {gameId:id , level:'MIDDLE', user: user2});
}

$(function () {
    let gameId = $("#gameId").val();
    connect();
    openChanel(gameId, showMessage);
    setStart(gameId,$("#user1").val(),$("#user2").val());
});