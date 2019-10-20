package hk.edu.polyu.comp.comp2021.jungle.controller;
import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Animal;
import hk.edu.polyu.comp.comp2021.jungle.view.View;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.imageio.IIOException;
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
    Then the line is send to the Model: "game" where it is checked semantically and update the game board.
    Expected format: "*command(save, open, move)*-*x_coordinate or path*-*y_coordinate*"
    */
    public void playGame(){
        String actualPlayerName;

        //Check if a saved game should be loaded:
        view.displayMessage("Welcome! \nDo you want to load a saved game? [y/n]");
        if(getYesOrNo()) {// yes = true
            String path = getPath();
            loadGame(path);
            //TODO: the saved game has to be implemented!
        }

        //Read the player names:
        view.displayMessage("Player in the front, please input your name:");
        this.frontPlayerName = getPlayerName();
        view.displayMessage("Player in the back, please input your name:");
        this.backPlayerName = getPlayerName();

        // --The Game starts here!--
        while (true){// each iteration resembles one turn
            view.displayMessage("Turn: " + turnCount);
            view.displayGameUpdate(game.getGameBoard());
            //TODO: test if winner is determined correctly!
            if(winner(turnCount % 2 == 0)){
                // example: front player is at turn => check if he was defeated in the last turn!
                    String lastPlayer = (frontPlayersTurn())? ("back player"): ("front player");
                    view.displayMessage(">>>>>Player " + lastPlayer + " is Victorious<<<<<<");
                    break; //Game Ends
            }
            actualPlayerName = (frontPlayersTurn())? (frontPlayerName): (backPlayerName);
            view.displayMessage("Player " + actualPlayerName +
                    ", please use **command(save, open, move)*-*x_coordinate or path*" +
                    "-*y_coordinate* to perform action: \n");


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

    /*
    processInput() takes in the next input and returns true if the input was a command for movement,
    on "save" and "open" false is returned!
     */
    private boolean processInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] actualLine = input.split(" ");
        //Read input:
        switch (actualLine[0]) {
            //TODO: implement the functionality!
            case ("save"):
                saveGame();
                return false;
            case ("open"):
                //TODO: ask if game should be saved! use function YesOrNo()!!!!!
                String path = getPath();
                loadGame(path);
                return false;
            case ("move"):
                if (actualLine.length == 3 && performMove(actualLine[1], actualLine[2])) {
                    //if here the move was successful:
                    return true;
                } else {
                    view.displayMessage("Draw not valid, try again!");
                    return processInput();
                }
            default:
                view.displayMessage("Draw not valid, try again!");
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

    private boolean winner(boolean playerBefore){ // true if front player has the turn

        Animal [] playerPieces;

        if(playerBefore){
            playerPieces = game.getGameBoard().getPlayerFrontAnimals();
        }
        else{
            playerPieces = game.getGameBoard().getPlayerBackAnimals();
        }
        for(Animal an: playerPieces){
            if(an != null){
                return false;
            }
        }
        return true;
    }

    private void loadGame(String path){
        //TODO: read the relevant informations here and than pass on to model!
        //TODO: the logic needs to be implemented!
        try {
            File file = new File(path);
            String content = FileUtils.readFileToString(file);
            JSONObject game = new JSONObject(content);

            //read all inserted data front the Jason File:
            turnCount = (int) game.get("turnCount");
            JSONObject playerFront = (JSONObject) game.get("playerFront");
            JSONObject playerBack = (JSONObject) game.get("playerBack");
            frontPlayerName = playerFront.getString("name");
            backPlayerName = playerBack.getString("name");
        }catch (IOException e){
            view.displayMessage("Error with reading the file. Please reenter the path!");
            //TODO: quit OK?
            System.exit(1);
        }
    }

    private void saveGame(){
        game.saveGame(frontPlayerName, backPlayerName, turnCount);
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

    private String getPath(){
        Scanner scanner = new Scanner(System.in);
        view.displayMessage("Please insert the Path to the saved game:");
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