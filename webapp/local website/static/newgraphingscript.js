function drawGraph(matches, xIndex, yIndex, context) {
	var lowX = matches[0][xIndex];
    var lowY = matches[0][yIndex];
    var highX = lowX;
    var highY = lowY;

	for(var i = 0; i < matches.length; i++){
		if(matches[i][xIndex] > highX){
            highX = matches[i][xIndex];
        }else if(matches[i][xIndex] < lowX){
            lowX = matches[i][xIndex];
        }
        if(matches[i][yIndex] > highY){
            highY = matches[i][yIndex];
        }else if(matches[i][yIndex] < lowY){
            lowY = matches[i][yIndex];
        }
	}
    var xGap = (highX-lowX)/4;
    var yGap = (highY-lowY)/4;
    context.font = "20px Georgia";
    context.fillStyle = "black";
    context.textAlign = "center";
    context.fillText(lowX,150,475);
    context.fillText(lowX + xGap,325,475);
    context.fillText(lowX + xGap * 2,500,475);
    context.fillText(lowX + xGap * 3,675,475);
    context.fillText(highX,850,475);

    context.textAlign = "right";
    context.fillText(lowY,125,450);
    context.fillText(lowY + yGap,125,350);
    context.fillText(lowY + yGap * 2,125,250);
    context.fillText(lowY + yGap * 3,125,150);
    context.fillText(highY,125,50);
    //x = 900, y = 500 (50-850, 50-450)
    
    for(var i = 0; i < matches.length; i++){
        var x = matches[i][xIndex];
        x -= lowX;
        x /= (highX-lowX);
        x *= 700;
        x += 150;
        var y = matches[i][yIndex];
        y -= lowY;
        y /= (highY-lowY);
        y *= 400;
        y += 50;
        y = 500 - y;
        drawCircle(x, y, !matches[i][9], context);
    }
    

}

function scream(){
    alert("boop");
}
document.getElementById('generateButton').addEventListener("click", initialize);
function drawCircle(x, y, t, ctx){
    if(t){
        ctx.fillStyle = 'rgba(100, 0, 0, 0.2)';
    }else{
        ctx.fillStyle = 'rgba(0, 100, 0, 0.2)';
    }
    ctx.beginPath();
    ctx.arc(x,y,1,0,2*Math.PI);
    ctx.fill();
}


//match id, start time, duration, tower status r, tower status d, barracks status d, barracks status r, fb time, gamemode, radiantWin (t/f), negative votes, positive votes, TOTAL HEALTH, DIRE HEALTH, RADIANT HEALTH
function initialize() {
	var xIndex = parseInt(document.getElementById('xAxisDropBox').value, 10);
	var yIndex = parseInt(document.getElementById('yAxisDropBox').value, 10);

    var graph = document.getElementById("js_scatterplot");
    var ctx = graph.getContext('2d');
    ctx.fillStyle = "white";
    ctx.fillRect(0,0,graph.width,graph.height);
    fetch('http://perlman.mathcs.carleton.edu:5107/matches', {method: 'get'})
    .then((response) => response.json())
    .then(function(matchDictionary) {
        var matches = new Array(Object.keys(matchDictionary).length);
        for(var i = 0; i < matches.length; i++){
            workingArr = new Array(15);
            for(var j = 0; j < workingArr.length; j++){
                workingArr[j] = matchDictionary[i.toString()][j];
            }
            workingArr[12] = workingArr[3] + workingArr[4] + workingArr[5] + workingArr[6];
            workingArr[13] = workingArr[4] + workingArr[5];
            workingArr[14] = workingArr[3] + workingArr[6];
            matches[i] = workingArr;
        }
        drawGraph(matches, xIndex, yIndex, ctx);
    });
}

window.onload = initialize;

