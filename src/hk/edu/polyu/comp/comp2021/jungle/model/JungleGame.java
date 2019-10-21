package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Animal;

public class JungleGame {
    private GameBoard board;
    public JungleGame(){
        this.board = new GameBoard();
    }

    /*
    setGameBoard() returnes true if the gameBoard is set successful, else false!
     */
    public boolean setGameBoard(String path){
        SaveAndLoad saveAndLoad = new SaveAndLoad();
        GameBoard gameBoard = saveAndLoad.loadGame(path);
        if(gameBoard == null){
            return false;
        }
        this.board = gameBoard;
        return true;
    }

    public GameBoard getGameBoard(){
        return this.board;
    }

    /*
    newInput() is called each time the Controller registers a new user input.
    -> passes the command on to the Animal that than checks the correctness of the move and performs it if so!
    Returns String with: move successful || move not successful
     */
    public boolean newInput(int from_x,int from_y ,int to_x, int to_y, boolean frontPlayersTurn ) {
        // the players turn is determined by the animals "name".
        Animal actual = this.board.getAnimal(from_x, from_y);
        //check if animal is alive!
        if (actual == null || (frontPlayersTurn != actual.getFrontPlayer())){
            return false;
        }
        //from here the coordinates are numbers beginning with 0!!
        return actual.move(to_x, to_y); // returns true if move was successful!
    }

    /*
    saveGame() returns true if successful, else false.
     */
    public  boolean saveGame(String path, String frontName, String backName, int turnCount){
        SaveAndLoad saveAndLoad = new SaveAndLoad();
        return saveAndLoad.saveGame(path, frontName, backName, turnCount, board);
    }
}
