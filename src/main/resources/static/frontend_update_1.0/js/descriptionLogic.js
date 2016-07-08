function description()
{

var list = document.getElementById('testList');

var optionTag = "text";
var selected = (list[list.selectedIndex].value);


var text0 = "<br> <span> Math test</span> <br> <p><span>Mathematics is the science that deals with the logic of shape, quantity and arrangement. <br> Mathematics (from Greek μάθημα máthēma, “knowledge, study, learning”) is the study of topics such as quantity (numbers), structure, space, and change. </span></p> <p>This test will help you to understand the level of your actual knowledge in this subject.</p><p>Pay attention to the questions (there may be tricky ones) and to the timer - you have only one hour to complete the whole test!</p> <p>Try this test!</p>";
var text1 = "<br> <span> Biology test</span> <br> <p><span>Biology is a natural science concerned with the study of life and living organisms, including their structure, function, growth, evolution, distribution, identification and taxonomy.</span></p> <p>This test will help you to understand the level of your actual knowledge in this subject.</p><p>Pay attention to the questions (there may be tricky ones) and to the timer - you have only one hour to complete the whole test!</p><p>Try this test!</p>";
var text2 = "<br> <span> Science test</span> <br> <p><span>Science is a systematic enterprise that builds and organizes knowledge in the form of testable explanations and predictions about the universe.</span></p> <p>This test will help you to understand the level of your actual knowledge in this subject.</p><p>Pay attention to the questions (there may be tricky ones) and to the timer - you have only one hour to complete the whole test!</p><p>Try this test!</p>";
var text3 = "<br> <span>English test</span> <br> <p> <span>English is a West Germanic language that was first spoken in early medieval England and is now a global lingua franca. </span> </p> <p>This test will help you to understand the level of your actual knowledge in this subject.</p><p>Pay attention to the questions (there may be tricky ones) and to the timer - you have only one hour to complete the whole test!</p> <p>Try this test!</p>";
var text4 = "<br> <span> Social test</span> <br>  <p> <span>Social science is a major category of academic disciplines, concerned with society and the relationships among individuals within a society.</span></p> <p>This test will help you to understand the level of your actual knowledge in this subject.</p><p>Pay attention to the questions (there may be tricky ones) and to the timer - you have only one hour to complete the whole test!</p><p>Try this test!</p>";




$("#descr").fadeOut(200, function() {
   document.getElementById('descr').innerHTML = eval(optionTag + list.selectedIndex);
});
$("#descr").fadeIn(200)
// document.getElementById("descr").style.visibility='visible';



}

