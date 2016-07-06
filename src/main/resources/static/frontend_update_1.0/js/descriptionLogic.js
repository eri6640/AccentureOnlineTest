function description()
{

var list = document.getElementById('testList');

var optionTag = "text";
var selected = (list[list.selectedIndex].value);


var text0 = "<br> <span> Math test</span> <br> <p>This test is will help you to...</p> <p>It covers the following topics: </p> <p>Something new for you</p> <p>Something you allready know</p> <p>Something that you don't remember</p> <br> <p>Try this test!</p>";
var text1 = "<br> <span> Biology test</span> <br> <p>This test is will help you to...</p> <p>It covers the following topics: </p> <p>Something new for you</p> <p>Something you allready know</p> <p>Something that you don't remember</p> <br> <p>Try this test!</p>";
var text2 = "<br> <span> Science test</span> <br> <p>This test is will help you to...</p> <p>It covers the following topics: </p> <p>Something new for you</p> <p>Something you allready know</p> <p>Something that you don't remember</p> <br> <p>Try this test!</p>";
var text3 = "<br> <span> English test</span> <br> <p>This test is will help you to...</p> <p>It covers the following topics: </p> <p>Something new for you</p> <p>Something you allready know</p> <p>Something that you don't remember</p> <br> <p>Try this test!</p>";
var text4 = "<br> <span> Social test</span> <br> <p>This test is will help you to...</p> <p>It covers the following topics: </p> <p>Something new for you</p> <p>Something you allready know</p> <p>Something that you don't remember</p> <br> <p>Try this test!</p>";



$("#descr").fadeOut(200, function() {
   document.getElementById('descr').innerHTML = eval(optionTag + list.selectedIndex);
});
$("#descr").fadeIn(200)
// document.getElementById("descr").style.visibility='visible';



}

