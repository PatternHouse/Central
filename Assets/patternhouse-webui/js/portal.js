var ts_alphabetic = true;
var ts_numeric = true;
var ts_pyramid = true;
var ts_series = true;
var ts_spiral = true;
var ts_string = true;
var ts_symbol = true;
var ts_wave = true;

function togglep_alphabetic() {
    if(ts_alphabetic === true) {
        document.getElementById("tbutton-alphabetic").className = "label"; 
        ts_alphabetic = false;
        $('.alphabetic').hide();
    } else {
        document.getElementById("tbutton-alphabetic").className = "label-active"; 
        ts_alphabetic = true;
        $('.alphabetic').show();
    }
}

function togglep_numeric() {
    if(ts_numeric === true) {
        document.getElementById("tbutton-numeric").className = "label"; 
        ts_numeric = false;
        $('.numeric').hide();
    } else {
        document.getElementById("tbutton-numeric").className = "label-active"; 
        ts_numeric = true;
        $('.numeric').show();
    }
}

function togglep_pyramid() {
    if(ts_pyramid === true) {
        document.getElementById("tbutton-pyramid").className = "label"; 
        ts_pyramid = false;
        $('.pyramid').hide();
    } else {
        document.getElementById("tbutton-pyramid").className = "label-active"; 
        ts_pyramid = true;
        $('.pyramid').show();
    }
}

function togglep_series() {
    if(ts_series === true) {
        document.getElementById("tbutton-series").className = "label"; 
        ts_series = false;
        $('.series').hide();
    } else {
        document.getElementById("tbutton-series").className = "label-active"; 
        ts_series = true;
        $('.series').show();
    }
}

function togglep_spiral() {
    if(ts_spiral === true) {
        document.getElementById("tbutton-spiral").className = "label"; 
        ts_spiral = false;
        $('.spiral').hide();
    } else {
        document.getElementById("tbutton-spiral").className = "label-active"; 
        ts_spiral = true;
        $('.spiral').show();
    }
}

function togglep_string() {
    if(ts_string === true) {
        document.getElementById("tbutton-string").className = "label"; 
        ts_string = false;
        $('.string').hide();
    } else {
        document.getElementById("tbutton-string").className = "label-active"; 
        ts_string = true;
        $('.string').show();
    }
}

function togglep_symbol() {
    if(ts_symbol === true) {
        document.getElementById("tbutton-symbol").className = "label"; 
        ts_symbol = false;
        $('.symbol').hide();
    } else {
        document.getElementById("tbutton-symbol").className = "label-active"; 
        ts_symbol = true;
        $('.symbol').show();
    }
}

function togglep_wave() {
    if(ts_wave === true) {
        document.getElementById("tbutton-wave").className = "label"; 
        ts_wave = false;
        $('.wave').hide();
    } else {
        document.getElementById("tbutton-wave").className = "label-active"; 
        ts_wave = true;
        $('.wave').show();
    }
}

window.onload = function() {
    togglep_alphabetic();
    togglep_numeric();
    togglep_pyramid();
    togglep_series();
    togglep_spiral();
    togglep_string();
    togglep_symbol();
    togglep_wave();

    document.getElementById('dash').className = "dash";
    document.getElementById('temp').className = "hide-temp";
    document.getElementById('temp2').className = "inbox";
    document.getElementById('temp2').style = "display: flex;";
};