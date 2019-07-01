var svgWidth = 600, svgHeight = 700
var fieldWidth = 450, fieldHeight = 650
var fieldX = 50, fieldY = 35
var goalWidth = 140, goalHeight = 15

window.onload = function() {
    alert( 'Document is ready' )
	var draw = SVG('air').size(svgWidth, svgHeight)
	var field = draw.group()
	var background = field.rect(fieldWidth, fieldHeight).attr({ 
																x:fieldX,
																y:fieldY,
																fill:'#0055d4', 
																stroke:'#bdea94',
																'stroke-width': 30,
																'stroke-linejoin':'round',
																})
	var centerCircle = field.circle(200).attr({ 
												cx:fieldX + fieldWidth/2,
												cy:fieldY + fieldHeight/2,
												fill:'#0055d4', 
												stroke:'#bdea94',
												})
	var centerLine = field.line(fieldX + 15, fieldY + fieldHeight/2, fieldX + fieldWidth - 15, fieldY + fieldHeight/2)
	centerLine.stroke({color: '#bdea94'})
	var myGoal = field.rect(goalWidth, goalHeight).attr({
														fill:'#0055d4',
														x:fieldX + fieldWidth/2 - goalWidth/2,
														y:fieldY
														})
	var enemyGoal = myGoal.clone()
	enemyGoal.attr({
					fill:'#0055d4',
					x:fieldX + fieldWidth/2 - goalWidth/2,
					y:fieldY + fieldHeight - 15
					})
					
	var puck = draw.circle(30).attr({ 
									cx:fieldX + fieldWidth/2,
									cy:fieldY + fieldHeight/2,
									fill:'#f4ffc9', 
									stroke:'#bdeadb',
									'stroke-width': 4
									})
									
	var myBat = draw.circle(30).attr({ 
									cx:fieldX + fieldWidth/2,
									cy:fieldY + fieldHeight - 30,
									fill:'#dbe3de', 
									stroke:'#cdd1c9',
									'stroke-width': 12
									})
									
	var enemyBat = myBat.clone();
	enemyBat.attr({ 
				  cy:fieldY + 30
				  })					
  };