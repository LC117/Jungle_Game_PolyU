package hk.edu.polyu.comp.comp2021.jungle.controller;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import hk.edu.polyu.comp.comp2021.jungle.view.View;
import java.util.Scanner;
import java.util.Arrays;
public class Controller {

    /*
    playGame() gets the next Line of user input and test it syntactically.
    Then the line is send to the Model: "game" where it is checked semantically and update the game board.
    Expected format: "*first 3 letter of animal name*-*x_coordinate*-*y_coordinate*"
    */
    public void playGame(View view, JungleGame game){
        view.displayGameUpdate(game.getGameBoard());
        Scanner scanner = new Scanner(System.in);
        String input;
        String [] animalXY; // the input String that is split on "-".

        boolean gameState = true; //true for ongoing, false for game finish

        String playerName = "int";
        int turnCount = 0;
        boolean success = true;

        while (gameState){
            String turnMessage = "Player " + playerName + ", please use *piece name*-*x_coordinate*-*y_coordinate* to move your piece";


            if(success){
                view.displayMessage("Turn: " + turnCount);
                game.updateView(view);}

            view.displayMessage(turnMessage);
            view.displayMessage("");
            input = scanner.nextLine();
            animalXY = input.split("-");
            if(!inputFormatOk(animalXY)){
                view.displayMessage("ERROR: Input has wrong format: use *piece name*-*x_coordinate*-*y_coordinate* and try again: ");
                success = false;
            }
            else if(!typeCheck(playerName, animalXY[0])){
                view.displayMessage("ERROR: Player abc use alphabetical characters, Player int please use the numbers");
                success = false;
            }
            else{
                //pass the input on to the model
                game.newInput(animalXY[0], Integer.valueOf(animalXY[1]), Integer.valueOf(animalXY[2]));
                turnCount +=1;
                success = true;
                if(playerName.equals("int")){
                    playerName = "abc";
                }
                else if(playerName.equals("abc")){
                    playerName = "int";
                }
            }
        }
    }

    /*
    inputFormatOk() checks if the syntax of the input is ok: 1)animal has 3 letters 2)3) if the coordinates are Integers.
    returns true if syntax is correct.
     */
    private boolean inputFormatOk(String [] input){
        boolean lengthOk = input.length == 3;
        try{//check if coordinates are Integers
            Integer.parseInt(input[1]);
            Integer.parseInt(input[2]);
        }catch (Exception e){
            return false;
        }
        boolean nameOk = input[0].length() == 1;
        return lengthOk && nameOk;
    }

    private boolean typeCheck(String playerName, String pieceName){
        String [] intPieces =   {"1", "2", "3", "4", "5", "6", "7", "8"};
        String [] abcPieces = {"a", "b", "c", "d", "e", "f", "g", "h"};


        if(playerName.equals("int")){
            for (String element : intPieces) {
                if (element.equals(pieceName)) {
                    return true;
                }
            }
        }
        else if(playerName.equals("abc")){
            for (String element : abcPieces) {
                if (element.equals(pieceName)) {
                    return true;
                }
            }
        }
        return false;
    }

}