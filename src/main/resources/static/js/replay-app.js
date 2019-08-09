function showMessage(message) {
    if (message.playStatus === 'BREAK') {
        disconnect();
        $("#goToStatistics").click();
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

function startReplay(id) {
    jQuery.get("/app/replay", {gameId:id});
}

$(function () {
    let gameId = $("#gameId").val();
    connect();
    openChanel(gameId, showMessage);
    startReplay(gameId);
});