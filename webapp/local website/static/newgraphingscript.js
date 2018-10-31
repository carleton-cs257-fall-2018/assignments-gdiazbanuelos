function onButtonPress() {
    alert('Hi from onButtonPress!');
}

function initialize() {



    var graph = document.getElementById("js_scatterplot");
    var ctx = graph.getContext('2d');
    ctx.fillStyle = 'rgb(0, 0, 0)';
    ctx.beginPath();
    ctx.arc(100,75,2,0,2*Math.PI);
    ctx.fill();

    var request = new XMLHttpRequest();
    request.open('GET', 'http://perlman.mathcs.carleton.edu:5107/matches');

    request.onLoad = function(){
        alert('n');
        var data = JSON.parse(this.response);
        if (request.status >= 200 && request.status < 400) {
            alert('nic e');
        }
    }

    request.send();
}

window.onload = initialize;

