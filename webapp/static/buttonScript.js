function onButtonPress() {
    document.getElementById('linechart_material').initialize();
    alert("bleep");
}

function initialize() {
    var button = document.getElementById('generateButton');
    button.onclick = onButtonPress;
}

window.onload = initialize;
