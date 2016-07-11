 
  // 0. Uncheck all checkboxes by default. 
 window.onload = function() {
 var checkboxes = document.getElementsByTagName('input');

for (var i=0; i<checkboxes.length; i++)  {
  if (checkboxes[i].type == 'checkbox')   {
    checkboxes[i].checked = false;
  }
}
 }
  // 1. Count amount of questions 
  // 2. Create an array qStatistics, that will store info - which questions was answered, which wasn't.
  // 3. Fill qStatistics array with 0-s (unanswered by fefault), amount of 0-s is equal to amount of questions, and index of elements (0-s) is equal to number of the question.
  
  //ATTENTION!!!!! Write id of the divs with questions, (without number at the end) instead of the "test" word - here: var divId = "test". Quotes are nesesarry!  
  var divId = "test";
  var numQ = 0;
  for (var index = 0; document.getElementById(divId+index)!=null; index++) {
  numQ += 1;
}
 var qStatistics = [];
 for (var i = 1; i<=numQ; i++) {
 qStatistics.push(0);
 }
  
  // 4. Get id of the div of the clicked checkbox.
  // 5. Rewrite 0-s by 1-s or 0-s(1 - if checked, 0 - if not checked) in qStatistics arr (rewrited element index = div of the clicked checkbox).
  var ansQ;
var checkboxes = document.getElementsByName('check-1');

for(var i=0;i<checkboxes.length;i++){
  checkboxes[i].onclick = getParentAndReplaceInArr;
}

function getParentAndReplaceInArr(){
  ansQDivId = (this.parentNode.id);
 ansQ = ansQDivId.substr(ansQDivId.length - 1);
 
 
    var checks = document.querySelectorAll('#' + ansQDivId + ' input[type="checkbox"]');
    for(var i =0; i< checks.length;i++){
        var check = checks[i];
        if(check.checked){
            qStatistics[ansQ] = 1;
			break
			} else {
			qStatistics[ansQ] = 0; //try break instead?
			
			}
        }
		// alert("qStatistics = " + qStatistics);
    }



/* var qUnanswered = [];
for (var i = 1; i<=numQ; i++) {
 qUnanswered.push(0);
}
*/

// If "Submit" button is clicked:
// Create an array qCompareToAllChcked

function submit(){


/*
for(var y = 0; y<=numQ; y++) {
 if (qStatistics[y] !== 0 && qStatistics[y] !== undefined){
 qUnanswered[y]= 1; 
  } else if (qStatistics[y] == 0){
qUnanswered[y]= 0; 	
} else if (qStatistics[y] == undefined) {
	break
}
 }
alert ("qUnanswered = " + qUnanswered);
*/
var qCompareToAllChcked = [];
 for (var i = 1; i<=numQ; i++) {
 qCompareToAllChcked.push(1);
 }
// alert("qStatistics = " + qStatistics);
// alert("qCompareToAllChcked = " + qCompareToAllChcked);

var is_same = qStatistics.length == qCompareToAllChcked.length && qStatistics.every(function(element, index) {
    return element === qCompareToAllChcked[index]; 
});

for (var i = 0; i<numQ; i++) {

	if (qStatistics[i] == 0) {
	// alert("Unanswered Question is found!"); 
	document.getElementById(divId + i).style.color="red"; 
	} else {
	document.getElementById(divId + i).style.color="black"; 
	}

// alert("Compare = " + is_same);


}
if (is_same) {
alert ("YOU FINISHED THE TEST!");
} 
}
