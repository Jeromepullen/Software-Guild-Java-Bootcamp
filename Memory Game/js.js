var cardContainer = $("#card-container");
//global variables
var gameId;
var rows;
var columns;
var row;
var column;
var message;
var value;
var firstCard;
var secondCard;
var baseURL = "http://localhost:8080";
var isGameOver = false;

var isPlayer1 = true;
var p1Pts = 0;
var p2Pts = 0;







// $('#strtBtn').click(function () {
//     $.ajax({
//         type: 'POST',
//         url: 'http://localhost:8080/start/3/4',
//         success: function (newgame) {
//             //            console.log(`row: ${row}, columns ${column}`);
//             gameId = newgame.gameId;
//             matchesToWin = (newgame.rows * newgame.columns) / 2;
//             resetGame();

//             var cardsDiv = $('#cards');
//             cardsDiv.empty();
//             var html = '';

//             for (var i = 0; i < newgame.rows; i++) {
//                 html += '<div class="row">';
//                 for (var j = 0; j < newgame.columns; j++) {
//                     html += `<div id="memory-card-${i}-${j}"  data-row="${i}" data-col="${j}" class="col-md card memory-card">
//                     ${cardFront}
//                     </div>`;
//                 };
//                 html += '</div>';
//             };
//             cardsDiv.append(html);
//         }
//     });
// });


function createGame(rows, columns) {
  cardContainer.empty();
  //use rows and columns to generate gameboard.
  for (let i = 0; i < rows; i++) {
    for (let j = 0; j < columns; j++) {
      var newCardTemplate = '<div class="card" data-row="' + i + '"' + ' data-col="' + j + '" id="row' + i + 'col' + j + '"></div>';

      console.log(newCardTemplate);
      cardContainer.append(newCardTemplate);
        
    }
  }
}


// start game
function start() { //random
  var promise = $.ajax({
    url: baseURL + "/start",
    type: "POST"

  });
  promise.done(function(data) {
    //show different functions
    $(cardContainer).show();
    $('#pts-display').show();

    var rows = data.rows;
    var columns = data.columns;
    gameId = data.gameId;
    matchesToWin = (rows * columns)/2
    createGame(rows, columns)
    console.log("Call one success", new Date());

  }); //
  promise.fail(function() {
        $('#errors').empty();
        $('#errors')
            .append($('<li>')
                .attr({
                    class: 'list-group-item list-group-item-danger'
                })
                .text('Error calling web service.  Please try again later.'));

  });

  return promise;
}


gameStartView();

//hide info
function gameStartView() {
    $(cardContainer).hide();
    $('#pts-display').hide();
    $('#playerTwoTurn').hide();
}


$("#strtBtn").click(function() {
  console.log("Before $.ajax.");
  start();
  console.log("After $.ajax.");
  
})


function handleError(xhr, status, err) {
    console.log("Error", xhr, status, err);
}



function valErr(input) {
    $('#errors').empty();

    var errorMessage = [];

    input.each(function () {
        if (!this.validity.valid) {
            var errorField = $('label[for=' + this.gameId + ']').text();
            errorMessage.push(errorField + ' ' + this.validationMessage);
        }
    });

    if (errorMessage.length > 0) {
        $.each(errorMessage, function (index, message) {
            $('#errors').append($('<li>').attr({
                class: 'list-group-item list-group-item-danger'
            }).text(message));
        });
        return true;
    } else {
        return false;
    }
};


function addToScore(data) {
    if (data.status == "MATCH" || data.status == "GAME_OVER") {
        if (isPlayer1 == true) {
            p1Pts = p1Pts + 1;
            $('#first-Player-Score').replaceWith('<h4 id="first-Player-Score">Player One:  ' + p1Pts + '</h4>');
        } else if (isPlayer1 == false) {
            p2Pts = p2Pts + 1;
            $('#second-Player-Score').replaceWith('<h4 id="second-Player-Score">Player Two:  ' + p2Pts + '</h4>');
        }
    }

}

function deleteMatchedCards(data) {
    if (data.status == "MATCH") {
        setTimeout(() => {
            firstCard.hide();
            setTimeout(() => {
                secondCard.hide();
            })
        })
    }
}

function whoseTurn(data) {
    if (data.status == "MATCH" || data.status == "NO_MATCH") {
        if (isPlayer1 == true) {
            isPlayer1 = false;
            $('#playerTwoTurn').show(10);
            $('#isPlayer1').hide();
        } else if (isPlayer1 == false) {
            isPlayer1 = true;
            $('#playerTwoTurn').hide();
            $('#isPlayer1').show(10);
        }
    }
}

function gameOverChecker(data) {
    if (data.status == "GAME_OVER") {
        isGameOver = true;
        gameOverBanner();
        $('#startBtn').hide();

    }
}


function gameOverBanner(isGameOver, p1Pts, p2Pts) {
    if (isGameOver == true) {
        setTimeout(() => {
            $(cardContainer).hide()
        }, 1000);
        
//checking scores
        if (p1Pts > p2Pts) {
            alert("Player 1 Wins");
        } else if (p1Pts < p2Pts) {
            alert("Player 2 Wins");
        } else if (p1Pts == p2Pts) {
            alert("It's a tie");
        }
        location.reload();
    }
}




cardContainer.on("click", '.card', function() {

      var cardArea = $(this).data("gameId");
      console.log(cardArea);
      var specificCardArea = $(this);
      console.log(specificCardArea);

      $.ajax({
        url: baseURL + "/move",
        type: "PUT",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
          gameId: gameId,
          row: $(this).data("row"),
          column: $(this).data("col")

        }),

        success: function(data) {
          var value = data.value;
          console.log(value);
          console.log(this);
          specificCardArea.append(value);
          addToScore(data);
          console.log("Player One Score: " + p1Pts);
          console.log("Player Two Score: " + p2Pts);

          if (data.status == "NEXT") {
                firstCard = specificCardArea;
            } else if (data.status == "MATCH" || data.status == "NO_MATCH") {
                secondCard = specificCardArea;
            }
            if (data.status == "NO_MATCH") {
                setTimeout(() => {
                    firstCard.empty()
                }, 300);
                setTimeout(() => {
                    secondCard.empty()
                }, 300);
            }

            if (data.status == "MATCH" || data.status == "GAME_OVER") {
                setTimeout(() => {
                    firstCard.animate({
                      opacity: 0
                    });
                }, 0);
                setTimeout(() => {
                    secondCard.animate({
                      opacity: 0
                    });
                }, 0);
            }

            whoseTurn(data);
            console.log("Is Player 1 Turn? After Check." + isPlayer1);

            gameOverChecker(data);

            gameOverBanner(isGameOver, p1Pts, p2Pts);

           console.log("Is the game over? " + isGameOver);
        },
        error: function() {
          console.log("Call one fail");
        }
      });

    });



