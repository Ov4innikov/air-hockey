function showMessage(message) {
    statusText.text(message.playStatus);
    timeText.text(convertMs(message.tick) + '');
    scoreMyText.text(message.player1.playAccount + '');
    scoreEnemyText.text(message.player2.playAccount + '');
    enemyBat.cx(message.player2.x + fieldX).cy(fieldHeight - message.player2.y + fieldY);
    myBat.cx(message.player1.x + fieldX).cy(fieldHeight - message.player1.y + fieldY);
    rpuck.cx(message.puck.x + fieldX).cy(fieldHeight - message.puck.y + fieldY);

    if (message.playStatus === 'BREAK') {
        disconnect();
        if (user1 != -1) jQuery.get("/app/end", {gameId:$("#gameId").val()});
        else jQuery.get("/app/bot-game-end", {gameId:$("#gameId").val()});

        if (message.player1.playAccount != message.player2.playAccount) {
            if (($('#userPosition').val() === 'UP')) {
                if ((message.player2.playAccount > message.player1.playAccount)) {
                    $(".modal-body").text('Вы выиграли со счетом ' + message.player2.playAccount + ':' + message.player1.playAccount);
                } else {
                    $(".modal-body").text('Вы проиграли со счетом ' + message.player2.playAccount + ':' + message.player1.playAccount);
                }
            } else {
                if ((message.player1.playAccount > message.player2.playAccount)) {
                    $(".modal-body").text('Вы выиграли со счетом ' + message.player1.playAccount + ':' + message.player2.playAccount);
                } else {
                    $(".modal-body").text('Вы проиграли со счетом ' + message.player1.playAccount + ':' + message.player2.playAccount);
                }
            }
        } else {
            if (($('#userPosition').val() === 'UP')) {
                if ((message.player2.score > message.player1.score)) {
                    $(".modal-body").text('Вы выиграли по показателю агрессивной игры ' + message.player2.score + ':' + message.player1.score);
                } else {
                    $(".modal-body").text('Вы проиграли по показателю агрессивной игры ' + message.player2.score + ':' + message.player1.score);
                }
            } else {
                if ((message.player1.score > message.player2.score)) {
                    $(".modal-body").text('Вы выиграли по показателю агрессивной игры ' + message.player1.score + ':' + message.player2.score);
                } else {
                    $(".modal-body").text('Вы проиграли по показателю агрессивной игры ' + message.player1.score + ':' + message.player2.score);
                }
            }
        }
        $("#myModalBox").modal('show');

        return;
    }
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