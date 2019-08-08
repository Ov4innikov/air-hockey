var svgWidth = 1000, svgHeight = 700;
var fieldWidth = 450, fieldHeight = 650;
var fieldX = 50, fieldY = 35;
var fieldBorder = 30;
var rpuckBorder = 4;
var batBorder = 12;
var rpuckRadius = 30;
var batRadius = 30;
var goalWidth = 140, goalHeight = 15;
var scoreMy = scoreEnemy = 0;
var nameEnemy = 'Enemy';

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

field.on('mousemove', function (e) {
    sendMessage($('#gameId').val(), {
        'gameId': $('#gameId').val(),
        'playerPosition': $('#userPosition').val(),
        'playerMoveStatus': 'YES',
        'direction': getCorner(e.offsetX - myBat.cx(), e.offsetY - myBat.cy())
    });
});

var background = field.rect(fieldWidth, fieldHeight).attr({
    x: fieldX,
    y: fieldY,
    fill: '#0055d4',
    stroke: '#bdea94',
    'stroke-width': fieldBorder,
    'stroke-linejoin': 'round'
});


var centerCircle = field.circle(200).attr({
    cx: fieldX + fieldWidth / 2,
    cy: fieldY + fieldHeight / 2,
    fill: '#0055d4',
    stroke: '#bdea94'
});
var centerLine = field.line(fieldX + 15, fieldY + fieldHeight / 2, fieldX + fieldWidth - 15, fieldY + fieldHeight / 2);
centerLine.stroke({color: '#bdea94'});
var myGoal = field.rect(goalWidth, goalHeight).attr({
    fill: '#0055d4',
    x: fieldX + fieldWidth / 2 - goalWidth / 2,
    y: fieldY
});
var enemyGoal = myGoal.clone();
enemyGoal.attr({
    fill: '#0055d4',
    x: fieldX + fieldWidth / 2 - goalWidth / 2,
    y: fieldY + fieldHeight - 15
});


var rpuck = field.circle(rpuckRadius).attr({
    cx: fieldX + fieldWidth / 2,
    cy: fieldY + fieldHeight / 2,
    fill: '#f4ffc9',
    stroke: '#bdeadb',
    'stroke-width': rpuckBorder
});


var myBat = field.circle(batRadius).attr({
    cx: fieldX + fieldWidth / 2,
    cy: fieldY + fieldHeight - 30,
    fill: '#dbe3de',
    stroke: '#cdd1c9',
    'stroke-width': batBorder
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

var nameMyText = draw.text($('#userName1').val() + '').font({
    size: 72,
    family: 'Mistral',
    fill: '#4e55d4',
    x: fieldWidth + 100,
    y: fieldHeight - 120
});

var scoreEnemyText = scoreMyText.clone().text(scoreEnemy + '').font({
    y: 70
});

var nameEnemyText = nameMyText.clone().text($('#userName2').val()  + '').font({
    y: 0
});

var timeText = nameMyText.clone().text('Time').font({
    y: fieldY + fieldHeight / 2 - 30
});

var statusText = nameMyText.clone().text('Status').font({
    y: fieldY + fieldHeight / 2 - 100
});
	