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
    if (message.playStatus === 'PUCK') {
        console.log(message);
    }
    if (message.playStatus === 'BREAK') {
        console.log('gracias');
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
    min = 0|((ms - timeNow)/1000/60);
    sec = 0|((ms - timeNow)/1000) % 60;
    return min + ':' + sec;
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
// $(function () {
//     $("form").on('submit', function (e) {
//         e.preventDefault();
//     });
//     $( "#connect" ).click(function() { connect(); openChanel($("#socketid").val(), showMessage);});
//     $( "#disconnect" ).click(function() { disconnect(); });
//     $( "#start" ).click(function () { setStart($("#socketid").val());});
//     $( "#bot" ).click(function () { setBot($("#socketid").val());});

    // Draggable.create("#SvgjsCircle1018", {
    //     bounds: {top:fieldY + 20, left: fieldX + 20, width:fieldWidth - 40, height:fieldHeight/2 - 60}, cursor:"grabbing",
    //     onDrag:function() {
    //         sendMessage($('#socketid').val(), {
    //                 'gameId':$('#socketid').val(),
    //                 // 'player':{
    //                     'playerPosition':'UP',
    //                     // 'x':calculateAbsolutePositionX(enemyBat),
    //                     // 'y':calculateAbsolutePositionY(enemyBat),
    //                     // 'playAccount':0.0,
    //                     // 'score':parseFloat(scoreEnemyText.text()),
    //                     // 'updateTime':0
    //                 // },
    //                 'playerMoveStatus':'YES',
    //                 'direction':getCorner(this.deltaX, this.deltaY)
    //         });
    //     },
    //
    //     onDragEnd:function() {
    //         sendMessage($('#socketid').val(), {
    //             'gameId':$('#socketid').val(),
    //             // 'player':{
    //                 'playerPosition':'UP',
    //                 // 'x':calculateAbsolutePositionX(enemyBat),
    //                 // 'y':calculateAbsolutePositionY(enemyBat),
    //                 // 'playAccount':0.0,
    //                 // 'score':parseFloat(scoreEnemyText.text()),
    //                 // 'updateTime':0
    //             // },
    //             'playerMoveStatus':'NO',
    //             'direction':0
    //         });
    //     }
    // });

    // Draggable.create("#SvgjsCircle1016", {
    //     bounds: {top:fieldY + fieldHeight/2 + 40, left: fieldX + 20, width:fieldWidth - 40, height:fieldHeight/2 - 60}, cursor:"grabbing",
    //     onDrag:function (e) {
    //         sendMessage($('#socketid').val(), {
    //             'gameId':$('#socketid').val(),
    //             // 'player':{
    //                 'playerPosition':'DOWN',
    //             //     'x':calculateAbsolutePositionX(myBat),
    //             //     'y':calculateAbsolutePositionY(myBat),
    //             //     'playAccount':0.0,
    //             //     'score':parseFloat(scoreMyText.text()),
    //             //     'updateTime':0
    //             // },
    //             'playerMoveStatus':'YES',
    //             'direction':getCorner(this.deltaX, this.deltaY)
    //         });
    //     },
    //
    //     onDragEnd:function() {
    //         sendMessage($('#socketid').val(), {
    //             'gameId': $('#socketid').val(),
    //             // 'player': {
    //                 'playerPosition': 'DOWN',
    //             //     'x': calculateAbsolutePositionX(myBat),
    //             //     'y': calculateAbsolutePositionY(myBat),
    //             //     'playAccount': 0.0,
    //             //     'score': parseFloat(scoreMyText.text()),
    //             //     'updateTime': 0
    //             // },
    //             'playerMoveStatus': 'NO',
    //             'direction': 0
    //         });
    //     }
    // });
//});