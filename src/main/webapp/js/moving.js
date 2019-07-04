var selectedElement = false;

SVG.on(draw, 'mousedown', function(e) {
	selectedElement = e.target;
})

draw.mousemove(function(e) {
	if (selectedElement && selectedElement.classList.contains('draggable')) {
    e.preventDefault();
    var dragX = e.pageX;
    var dragY = e.pageY;
	
    selectedElement.setAttribute('cx', dragX);
    selectedElement.setAttribute('cy', dragY);
	}
})

draw.mouseup(function() {
	selectedElement = null;
})

draw.mouseleave(function() {
	selectedElement = null;
})