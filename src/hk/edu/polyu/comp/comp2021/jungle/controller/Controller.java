package hk.edu.polyu.comp.comp2021.jungle.controller;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Animal;
import hk.edu.polyu.comp.comp2021.jungle.view.View;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
    private View view;
    private JungleGame game;
    private int turnCount;
    private String frontPlayerName;
    private String backPlayerName;

    public Controller(View view, JungleGame game){
        this.view = view;
        this.game = game;
    }

    /*
    playGame() gets the next Line of user input and test it syntactically.
    Then the line is send to the Model: "game" where it is checked semantically and updates the game board.
    Expected format: "*command(save, open, move)*-*x_coordinate or path*-*y_coordinate*"
    */
    public void playGame(){

        //Check if a saved game should be loaded:
        view.displayMessage("Welcome! \nDo you want to load a saved game? [y/n]");
        if(getYesOrNo()) {// yes = true
            String path = getPath();
            loadGame(path);
        }

        //Read the player names:
        view.displayMessage("Player in the front, please input your name:");
        this.frontPlayerName = getPlayerName();
        view.displayMessage("Player in the back, please input your name:");
        this.backPlayerName = getPlayerName();

        // --The Game starts here!--
        while (true){// each iteration resembles one turn
            checkForWinner();
            view.displayMessage("Turn: " + turnCount);
            view.displayGameUpdate(game.getGameBoard());
            if(processInput()){
                //here the input is a move!
                this.turnCount++;
            }
        }
    }

    /*
    playGame(GameBoard gameBoard) is called if a saved game should be started!
     */
    public void continueGame(){
        //frontPlayerName, backPlayerName ant turnCont are set by the loadGame()!
        // --The Game continues here!--
        while (true){// each iteration resembles one turn

            checkForWinner();
            view.displayMessage("Turn: " + turnCount);
            view.displayGameUpdate(game.getGameBoard());
            if(processInput()){
                //here the input is a move!
                this.turnCount++;
            }
        }
    }

    /*
    inputFormatOk() checks if the syntax of the input is ok: 1)animal has 3 letters 2)3) if the coordinates are Integers.
    returns true if syntax is correct.
     */
    private boolean inputFormatOk(String from, String to){
        if(from.length() != 2 || to.length() != 2){
            return false;
        }
        // are true if the respective coordinate is syntactically Ok!
        boolean fromXOk = from.charAt(0) <= 71 && from.charAt(0) >= 65;
        boolean fromYOk = from.charAt(1) <= 57 && from.charAt(1) >= 49;

        boolean toXOk = to.charAt(0) <= 71 && to.charAt(0) >= 65;
        boolean toYOk = to.charAt(1) <= 57 && to.charAt(1) >= 49;

        return (fromXOk && toXOk && fromYOk && toYOk);
    }

    private void checkForWinner(){
        //TODO: test if winner is determined correctly!
        if(lastPlayerWon()){
            String lastPlayer = (frontPlayersTurn())? ("back player"): ("front player");
            view.displayMessage(">>>>>Player " + lastPlayer + " is Victorious<<<<<<");
            System.exit(0); //Game Ends
        }
    }

    /*
    processInput() takes in the next input and returns true if the turnCount should be updated!
     */
    private boolean processInput() {
        String actualPlayerName;

        //Demand Input:
        actualPlayerName = (frontPlayersTurn())? (frontPlayerName): (backPlayerName);
        view.displayMessage("Player " + actualPlayerName +
                ", please use **command(save, open, move)*-*x_coordinate or path*" +
                "-*y_coordinate* to perform action: \n");

        //Read input:
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] actualLine = input.split(" ");
        switch (actualLine[0]) {
            case ("save"):
                if (actualLine.length == 2 && saveGame(actualLine[1])){
                    view.displayMessage("Save successful!");
                }else if (saveGame(getPath())){
                    view.displayMessage("Save successful!");
                }
                return false;
            case ("open"):
                //TODO: check instructions: if game save yet ? aks teacher!
                view.displayMessage("Have you saved the game, if not the progress will be lost. Save game ?[y/n]");
                if(getYesOrNo()){
                    saveGame(getPath());
                }
                if (actualLine.length == 2){
                    loadGame(actualLine[1]);
                }else {
                    loadGame(getPath());
                }
                return false;
            case ("move"):
                if (actualLine.length == 3 && performMove(actualLine[1], actualLine[2])) {
                    //if here the move was successful:
                    return true;
                } else {
                    view.displayMessage("Draw not valid, try again!");
                    view.displayGameUpdate(game.getGameBoard());
                    return processInput();
                }
            default:
                view.displayMessage("Input not valid, try again!");
        }
        return processInput();
    }

    private boolean performMove(String from, String to){
        if(inputFormatOk(from, to)){
            //TODO: find the animal by "from"
            int from_x = from.charAt(0) - 65;
            int from_y = from.charAt(1) - 49;

            int to_x = to.charAt(0) - 65;
            int to_y = to.charAt(1) - 49;

            return game.newInput(from_x,from_y ,to_x, to_y , frontPlayersTurn());
        }else{
            return false;
        }
    }

    /*
    winner() checks if a winning condition was met in the round before:
    player before moved on den or the actual player has no animals!
     */
    private boolean lastPlayerWon(){

        boolean actualPlayerIsFrontPlayer = frontPlayersTurn(); // true => playerBefore was backPlayer, else he was frontPlayer
        return animalOnActualDen(actualPlayerIsFrontPlayer) || allPiecesDead(actualPlayerIsFrontPlayer);
    }

    private boolean animalOnActualDen(boolean actualPlayerIsFrontPlayer){
        if(actualPlayerIsFrontPlayer){
            //the player before was the back player, if he put an animal on the front players den he won.
            return game.getGameBoard().getAnimal(3, 0) != null;
        }
        else{
            //the player before was the front player, if he put an animal on the back players den, he won.
            return game.getGameBoard().getAnimal(3, 8) != null;
        }

    }
    private boolean allPiecesDead(boolean actualPlayerIsFrontPlayer){
        Animal [] animalPieces;
        if(actualPlayerIsFrontPlayer){
            //the player before was the back player, if he won all front player animals need to be dead.
            animalPieces = game.getGameBoard().getPlayerFrontAnimals();
        }
        else{
            //the player before was the front player, if he won all back player animals need to be dead.
            animalPieces = game.getGameBoard().getPlayerBackAnimals();
        }
        for(Animal an: animalPieces){
            if(an != null){
                //Here at least one animal is still alive.
                return false;
            }
        }
        return true; //all animals dead.
    }

    /*loadGame() is called if the user wants to load a saved game from a certain path.
      After being called loadGame will read the Data necessary for the Controller to run, generate the new game board
      with the help of the model and start the saved game.
     */
    private void loadGame(String path) {
        try {
            File file = new File(path);
            String content = FileUtils.readFileToString(file);
            JSONObject saveGame = new JSONObject(content);

            //read all inserted data front the Jason File:
            turnCount = (int) saveGame.get("turnCount");
            JSONObject playerFront = (JSONObject) saveGame.get("playerFront");
            JSONObject playerBack = (JSONObject) saveGame.get("playerBack");
            frontPlayerName = playerFront.getString("name");
            backPlayerName = playerBack.getString("name");
            this.game.setGameBoard(path);
            continueGame();
        } catch (IOException e) {
            view.displayMessage("\n Error with reading the file. Trying to load it from -SaveGames- folder!");
            String newPath = System.getProperty("user.dir") + "\\SaveGames\\\\testJump" + ".json";
            try {
                File file = new File(newPath);
                if (file.exists()) {
                    loadGame(newPath);
                }
            } catch (Exception eII) {
                view.displayMessage("\n Path not found. Program will be terminated!");
                System.exit(1);
            }
        }
    }

    private boolean saveGame(String path){
        return game.saveGame( path, frontPlayerName, backPlayerName, turnCount);
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

    private String getPath(){
        Scanner scanner = new Scanner(System.in);
        view.displayMessage("Please insert the Path:");
        return scanner.nextLine();
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

    private boolean frontPlayersTurn(){
        return turnCount % 2 == 0;
    }


}