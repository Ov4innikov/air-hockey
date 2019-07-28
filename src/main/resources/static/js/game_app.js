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
    if (message.playStatus === 'PUCK') {
        console.log(message)
    }
    statusText.text(message.playStatus);
    timeText.text(message.tick + '');
    scoreMyText.text(message.player1.score + '');
    scoreEnemyText.text(message.player2.score + '');
    enemyBat.cx(message.player2.x).cy(message.player2.y);
    myBat.cx(message.player1.x).cy(message.player1.y);
    rpuck.cx(message.puck.x).cy(message.puck.y);
}

function setStart(id) {
    jQuery.get("/app/start", {gameId:id});
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

function calculateAbsolutePositionX(svgElement) {
    return svgElement.cx() + svgElement.transform().x;
}

function calculateAbsolutePositionY(svgElement) {
    return svgElement.cy() + svgElement.transform().y;
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); openChanel($("#socketid").val(), showMessage);});
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#start" ).click(function () { setStart($("#socketid").val());});

    Draggable.create("#SvgjsCircle1018", {
        bounds: {top:fieldY + 20, left: fieldX + 20, width:fieldWidth - 40, height:fieldHeight/2 - 60}, cursor:"grabbing",
        onDrag:function() {
            sendMessage($('#socketid').val(), {
                    'gameId':$('#socketid').val(),
                    // 'player':{
                        'playerPosition':'UP',
                        // 'x':calculateAbsolutePositionX(enemyBat),
                        // 'y':calculateAbsolutePositionY(enemyBat),
                        // 'playAccount':0.0,
                        // 'score':parseFloat(scoreEnemyText.text()),
                        // 'updateTime':0
                    // },
                    'playerMoveStatus':'YES',
                    'direction':getCorner(this.deltaX, this.deltaY)
            });
        },

        onDragEnd:function() {
            sendMessage($('#socketid').val(), {
                'gameId':$('#socketid').val(),
                // 'player':{
                    'playerPosition':'UP',
                    // 'x':calculateAbsolutePositionX(enemyBat),
                    // 'y':calculateAbsolutePositionY(enemyBat),
                    // 'playAccount':0.0,
                    // 'score':parseFloat(scoreEnemyText.text()),
                    // 'updateTime':0
                // },
                'playerMoveStatus':'NO',
                'direction':0
            });
        }
    });

    Draggable.create("#SvgjsCircle1016", {
        bounds: {top:fieldY + fieldHeight/2 + 40, left: fieldX + 20, width:fieldWidth - 40, height:fieldHeight/2 - 60}, cursor:"grabbing",
        onDrag:function (e) {
            sendMessage($('#socketid').val(), {
                'gameId':$('#socketid').val(),
                // 'player':{
                    'playerPosition':'DOWN',
                //     'x':calculateAbsolutePositionX(myBat),
                //     'y':calculateAbsolutePositionY(myBat),
                //     'playAccount':0.0,
                //     'score':parseFloat(scoreMyText.text()),
                //     'updateTime':0
                // },
                'playerMoveStatus':'YES',
                'direction':getCorner(this.deltaX, this.deltaY)
            });
        },

        onDragEnd:function() {
            sendMessage($('#socketid').val(), {
                'gameId': $('#socketid').val(),
                // 'player': {
                    'playerPosition': 'DOWN',
                //     'x': calculateAbsolutePositionX(myBat),
                //     'y': calculateAbsolutePositionY(myBat),
                //     'playAccount': 0.0,
                //     'score': parseFloat(scoreMyText.text()),
                //     'updateTime': 0
                // },
                'playerMoveStatus': 'NO',
                'direction': 0
            });
        }
    });
});