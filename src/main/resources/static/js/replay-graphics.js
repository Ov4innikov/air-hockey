var svgWidth = 1000, svgHeight = 750;
var fieldWidth = 450, fieldHeight = 650;
var fieldX = 50, fieldY = 35;
var rpuckDiameter = 20;
var batDiameter = 40;
var goalWidth = 200, goalHeight = 15;
var scoreMy = scoreEnemy = 0;

var draw = SVG('air').size(svgWidth, svgHeight).attr({'background-color': '#0f0b4cf0'});

var field = draw.group();

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

var nameMyText = draw.text($('#user1').val() + '').font({
    size: 72,
    family: 'Mistral',
    fill: '#4e55d4',
    x: fieldWidth + 100,
    y: fieldHeight - 120
});

var scoreEnemyText = scoreMyText.clone().text(scoreEnemy + '').font({
    y: 70
});

var nameEnemyText = nameMyText.clone().text($('#user2').val()  + '').font({
    y: 0
});

var timeText = nameMyText.clone().text('Time').font({
    y: fieldY + fieldHeight / 2 - 30
});

var statusText = nameMyText.clone().text('Status').font({
    y: fieldY + fieldHeight / 2 - 100
});
	