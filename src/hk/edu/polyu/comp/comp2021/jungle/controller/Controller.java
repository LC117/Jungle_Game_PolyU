package hk.edu.polyu.comp.comp2021.jungle.controller;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Animal;
import hk.edu.polyu.comp.comp2021.jungle.view.View;
import java.util.Scanner;
import java.util.Arrays;
public class Controller {
    private View view;
    private JungleGame game;

    public Controller(View view, JungleGame game){
        this.view = view;
        this.game = game;
    }

    /*
    playGame() gets the next Line of user input and test it syntactically.
    Then the line is send to the Model: "game" where it is checked semantically and update the game board.
    Expected format: "*first 3 letter of animal name*-*x_coordinate*-*y_coordinate*"
    */
    public void playGame(){
        Scanner scanner = new Scanner(System.in);
        String input;
        String [] animalXY; // the input String that is split on "-".
        int turnCount = 0;
        String actualPlayerName;

        while (true){// each iteration resembles one turn
            view.displayMessage("Turn: " + turnCount);
            view.displayGameUpdate(game.getGameBoard());
            //TODO: test if winner is determined correctly!
            if(winner(turnCount % 2 == 0)){
                // example: front player is at turn => check if he was defeated in the last turn!
                    String lastPlayer = (turnCount % 2 == 0)? ("back player"): ("front player");
                    view.displayMessage(">>>>>Player " + lastPlayer + " is Victorious<<<<<<");
                    break; //Game Ends
            }
            actualPlayerName = (frontPlayersTurn(turnCount))? ("front (int)"): ("back (abc)"); //decides the actual players name!
            view.displayMessage("Player " + actualPlayerName + ", please use *piece name*-*x_coordinate*-*y_coordinate* to move your piece: \n");

            //read user input: til correct execution possible
            while(true){
                input = scanner.nextLine();
                animalXY = input.split("-");
                if(inputFormatOk(animalXY, turnCount)){
                    if(game.newInput(animalXY[0], Integer.valueOf(animalXY[1]), Integer.valueOf(animalXY[2]))){
                        //input processed correctly!
                        break;
                    }
                    view.displayMessage("Draw not valid, try again!");
                }

            }
            //here the input has correct format!
            turnCount++;
        }
    }

    /*
    inputFormatOk() checks if the syntax of the input is ok: 1)animal has 3 letters 2)3) if the coordinates are Integers.
    returns true if syntax is correct.
     */
    private boolean inputFormatOk(String [] input, int turnCount){
        boolean lengthOk;
        boolean nameOk;
        lengthOk = input.length == 3;

        try{//check if coordinates are Integers
            Integer.parseInt(input[1]);
            Integer.parseInt(input[2]);
        }catch (Exception e){
            return false;
        }

        if(frontPlayersTurn(turnCount)){ // frontPlayers turn: int
            nameOk = input[0].charAt(0) >= 48 && input[0].charAt(0) <= 56;
        }else{ // backPlayers turn: abc
            nameOk = input[0].charAt(0) >= 97 && input[0].charAt(0) <= 104;
        }
        // If the wrong player thinks its is turn: the Error is printed!
        if(!nameOk){
            view.displayMessage("ERROR: Back player please use alphabetical characters, front player please use numbers.");
        }
        return lengthOk && nameOk;
    }  


    private boolean winner(boolean playerBefore){ // true if front player has the turn

        Animal [] playerPieces;

        if(playerBefore){
            playerPieces = game.getGameBoard().getplayerFront();
        }
        else{
            playerPieces = game.getGameBoard().getPlayerBack();
        }
        for(Animal an: playerPieces){
            if(an != null){
                return false;
            }
        }
        return true;
    }

    private boolean frontPlayersTurn(int turnCount){
        return turnCount % 2 == 0;
    }


}