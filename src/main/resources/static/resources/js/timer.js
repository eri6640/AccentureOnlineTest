  //Timer function
  window.onload = function() {
      myFunction();

  };

  function myFunction() {

      timer('timerPlace', 3600);
  }

  function timer(tag, sec) {
      document.getElementById(tag).innerHTML = "<div id= 'inTime'>" +
          (sec / 60 >> 0) + 'min ' + sec % 60 + 'sec' + '<br>' + "</div>";



      if ((sec / 60 >> 0) != 0 || (sec % 60) != 0) {
          setTimeout(function() {
              timer(tag, sec);
          }, 1000);
          sec -= 1;
      } else {
          document.getElementById(tag).innerHTML = "Time is over!";
      }
  }