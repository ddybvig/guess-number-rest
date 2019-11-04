# guess-number-rest

This was my favorite project that I completed at the Software Guild. It allows the user to play a simple guessing game through 
HTTP requests. The program will generate a four digit non-repeating number for you to guess.

Before starting, you will need to run ```GuessTheNumberDBCreationScript.sql``` 
and ```GuessTheNumberTESTDBCreationScript.sql``` to create a local database
to persist data from the project. The project is set up to 
connect to a MySQL database (I used MySQL Workbench 8.0, so you 
will need that or some equivalent on your machine). If you clone the project, the ```application.properties``` file will have the
password for the database set to "rootroot." Change it to whatever you use and then build for it to stop throwing that error.

## How To Play

This project was designed to use Postman as the front end. While you can view the "GET" endpoints in a standard browser, playing
the game requires sending "POST" requests as raw JSON. Postman makes this easy, so I recommend downloading it as a standalone
app or Chrome extension. In the following instructions I'll assume you're using Postman or something similar.

### Start a game
To start a new game, run the project then type ```localhost:8080/begin``` into Postman as a POST request. The server should return
the data for the game as JSON with the answer set to "HIDDEN." It should look something like this:

      {
      "id": 21,
      "answer": "HIDDEN",
      "inProgress": true
      }
      
### Make a guess
To make a guess, first type ```localhost:8080/guess``` into Postman as a POST request. Then, select "Body" and "raw" and enter
your guess in JSON format. For example, if you wanted to guess "1234" for game 21, you would type the following into the request body.

     {
      "gameId": 21,
      "userGuess": 1234
      }
      
If you have not made any errors, the server will return a guess object with data about your guess. The guess example from above would
return something resembling this:

    {
    "id": 43,
    "guess": "1234",
    "timeOfGuess": "2019-11-04T10:13:46",
    "result": "e:0p:1",
    "game": {
        "id": 21,
        "answer": "HIDDEN",
        "inProgress": true
        }
    }

The "result" field tells you that you got 0 exact matches and 1 partial match. This means that one of the digits you guessed
(either 1, 2, 3, or 4) appears somewhere in the answer, but not at the position it was in your guess. For example,
if there was a 1 in the answer, it must be in either the second, third, or fourth position (or index 1, 2, or 3 if you're
a hard core programmer I suppose :)).

When you get 4 exact matches, you win! The program will set inProgress to false and you can start a new game or go back
to an old one that hasn't been finished yet.

You can also view data for all games, one game, or all guesses for a particular game through GET requests. 

All games: ```localhost:8080/game```

One game: ```localhost8080/game/{gameId}```

All guesses for a game: ```localhost:8080/rounds/{gameId}```



