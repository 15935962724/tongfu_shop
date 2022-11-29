//图层切换

var last = new Array();

function ChangeDiv(divId, divName, zDivCount, obj, title) {
    console.log(1)
    $("."+divName+divId).siblings("div").hide();
    $("."+divName+divId).show();
    console.log(last)
    // for (i = 0; i <= zDivCount; i++) {
    //     var div = document.getElementById(divName + i);
    //     if (div != null) {
    //         div.style.display = "none";
    //     }
    // }
    // document.getElementById(divName + divId).style.display = "block";
    if (!last[divName]) last[divName] = document.getElementById(divName + "DEF");
    last[divName].className = "";
    last[divName] = obj;
    last[divName].className = "Selected";

    if (title == '软件教程') {
        document.getElementById('light1').style.display = 'block';
        document.getElementById('fade1').style.display = 'block'
    }
}


function clisewm() {
    var clse = document.getElementById('ewm');
    clse.style.display = "none";
}

function appnone() {
    var app = document.getElementById('appl');
    app.style.display = "none";
}




