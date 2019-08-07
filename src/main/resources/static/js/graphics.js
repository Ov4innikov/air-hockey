var svgWidth = 1000, svgHeight = 750;
var fieldWidth = 450, fieldHeight = 650;
var fieldX = 50, fieldY = 35;
var rpuckDiameter = 20;
var batDiameter = 40;
var goalWidth = 200, goalHeight = 15;
var scoreMy = scoreEnemy = 0;
var nameMy = 'Me';
var nameEnemy = 'Enemy';

var selectedElement = false;

var draw = SVG('air').size(svgWidth, svgHeight).attr({'background-color': '#0f0b4cf0'});

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

var field = draw.group();

// SVG.on(field, 'mousedown', function(e) {
//     selectedElement = e.target;
// })

field.mousedown(function(e) {
    selectedElement = e.target;
});

field.mousemove(function(e) {
    e.preventDefault();
    if (selectedElement) {
        console.log((fieldHeight - e.offsetY) - (fieldHeight - enemyBat.cy()));
        sendMessage($('#socketid').val(), {
            'gameId': $('#socketid').val(),
            'playerPosition': 'UP',
            'playerMoveStatus': 'YES',
            'direction': getCorner(e.offsetX - enemyBat.cx(), enemyBat.cy() - e.offsetY)
        });
    }
});

field.mouseup(function() {
    selectedElement = null;

    sendMessage($('#socketid').val(), {
        'gameId': $('#socketid').val(),
        'playerPosition': 'DOWN',
        'playerMoveStatus': 'NO',
        'direction': 0.0
    });
});

// field.mouseleave(function() {
//     selectedElement = null;
//     sendMessage($('#socketid').val(), {
//         'gameId': $('#socketid').val(),
//         'playerPosition': 'DOWN',
//         'playerMoveStatus': 'NO',
//         'direction': 0.0
//     });
// });


// field.on('mousemove', function (e) {
//     sendMessage($('#socketid').val(), {
//         'gameId': $('#socketid').val(),
//         'playerPosition': 'DOWN',
//         'playerMoveStatus': 'YES',
//         'direction': getCorner(e.offsetX - myBat.cx(), e.offsetY - myBat.cy())
//     });
// });

var downLayer = field.rect(fieldWidth + 60, fieldHeight + 60).attr({
    x: fieldX - 30,
    y: fieldY - 30,
    fill: '#bdea94',
    rx: 30
});

var background = field.rect(fieldWidth, fieldHeight).attr({
    x: fieldX,
    y: fieldY,
    fill: '#0055d4',
});

var centerCircle = field.circle(200).attr({
    cx: fieldX + fieldWidth / 2,
    cy: fieldY + fieldHeight / 2,
    fill: '#0055d4',
    stroke: '#bdea94'
});

var centerLine = field.line(fieldX, fieldY + fieldHeight / 2, fieldX + fieldWidth, fieldY + fieldHeight / 2);
centerLine.stroke({color: '#bdea94'});

var myGoal = field.rect(goalWidth, goalHeight).attr({
    fill: '#0055d4',
    x: fieldX + fieldWidth / 2 - goalWidth / 2,
    y: fieldY - goalHeight
});

var enemyGoal = myGoal.clone();
enemyGoal.attr({
    fill: '#0055d4',
    x: fieldX + fieldWidth / 2 - goalWidth / 2,
    y: fieldY + fieldHeight
});

var rpuck = field.circle(rpuckDiameter).attr({
    cx:fieldX + fieldWidth/2,
    cy:fieldY + fieldHeight/2,
    fill:'#f4ffc9'
})

var myBat = field.circle(batDiameter).attr({
    cx: fieldX + fieldWidth / 2,
    cy: fieldY + fieldHeight - 30,
    fill: '#dbe3de'
});

var enemyBat = myBat.clone();
enemyBat.attr({
    cy: fieldY + 30
});

var scoreMyText = draw.text(scoreMy + '').font({
    size: 72,
    family: 'Mistral',
    fill: '#4e55d4',
    x: fieldWidth + 100,
    y: fieldHeight - 50
});

var nameMyText = draw.text(nameMy + '').font({
    size: 72,
    family: 'Mistral',
    fill: '#4e55d4',
    x: fieldWidth + 100,
    y: fieldHeight - 120
});

var scoreEnemyText = scoreMyText.clone().text(scoreEnemy + '').font({
    y: 70
});

var nameEnemyText = nameMyText.clone().text(nameEnemy + '').font({
    y: 0
});

var timeText = nameMyText.clone().text('Time').font({
    y: fieldY + fieldHeight / 2 - 30
});

var statusText = nameMyText.clone().text('Status').font({
    y: fieldY + fieldHeight / 2 - 100
});
