function onButtonPress() {
    alert('Hi from onButtonPress!');
}

function initialize() {
    var button = document.getElementById('generateButton');
    button.onclick = onButtonPress;
}

window.onload = initialize;
