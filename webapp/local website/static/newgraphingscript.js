function initialize() {
    var graph = document.getElementById("js_scatterplot");
    var ctx = graph.getContext('2d');
    ctx.fillStyle = 'rgb(0, 0, 0)';
    ctx.beginPath();
    ctx.arc(100,75,2,0,2*Math.PI);
    ctx.fill();
    fetch('http://perlman.mathcs.carleton.edu:5107/matches', {method: 'get'})
    .then((response) => response.json())
    .then(function(matchDictionary) {
        alert(matchDictionary.substring(0,100));
    });
}

window.onload = initialize;

