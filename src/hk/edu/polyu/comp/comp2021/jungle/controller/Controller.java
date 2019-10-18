package hk.edu.polyu.comp.comp2021.jungle.controller;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Animal;
import hk.edu.polyu.comp.comp2021.jungle.view.View;
import java.util.Scanner;

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
        String frontPlayerName;
        String backPlayerName;
        String actualPlayerName;

        //Check if a saved game should be loaded:
        view.displayMessage("Welcome! \nDo you want to load a saved game? [y/n]");
        if(getYesOrNo()) {// yes = true
            loadGame();
            //TODO: the saved game has to be implemented!
        }

        view.displayMessage("Player in the front, please input your name:");
        frontPlayerName = getPlayerName();
        view.displayMessage("Player in the back, please input your name:");
        backPlayerName = getPlayerName();

        while (true){// each iteration resembles one turn
            view.displayMessage("Turn: " + turnCount);
            view.displayGameUpdate(game.getGameBoard());
            //TODO: test if winner is determined correctly!
            if(winner(turnCount % 2 == 0)){
                // example: front player is at turn => check if he was defeated in the last turn!
                    String lastPlayer = (frontPlayersTurn(turnCount))? ("back player"): ("front player");
                    view.displayMessage(">>>>>Player " + lastPlayer + " is Victorious<<<<<<");
                    break; //Game Ends
            }
            actualPlayerName = (frontPlayersTurn(turnCount))? (frontPlayerName): (backPlayerName); //decides the actual players name!
            view.displayMessage("Player " + actualPlayerName + ", please use *piece name*-*x_coordinate*-*y_coordinate* to move your piece: \n");

            //read user input: til correct execution possible
            while(true){
                //TODO: check for "save", "open" or "move" command!
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
        //test input array length:
        lengthOk = input.length == 3;
        //assure the input is not empty, because get the "first" char will throw NullP. error.
        if(input[0].equals("") || input[1].equals("") || input[2].equals("")){
            view.displayMessage("Please fill in all properties!");
            return false;
        }

        try{//check if coordinates are Integers
            Integer.parseInt(input[1]);
            Integer.parseInt(input[2]);
        }catch (Exception e){
            view.displayMessage("X and Y coordinates have to be numbers!");
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

    private void loadGame(){
        //TODO: the logic needs to be implemented!
    }

    private boolean getYesOrNo() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.length() == 1 && input.charAt(0) == 121) {
                return true;
            } else if (input.length() == 1 && input.charAt(0) == 110) {
                return false;
            } else {
                view.displayMessage("Please retry: [y/n]");
            }
        }
    }

    private String getPlayerName(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if(input.length() > 0){
            return input;
        }
        else{
            return getPlayerName();
        }
    }

    private boolean frontPlayersTurn(int turnCount){
        return turnCount % 2 == 0;
    }


}