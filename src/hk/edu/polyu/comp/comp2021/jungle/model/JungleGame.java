package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Animal;

public class JungleGame {
    private GameBoard board;
    public JungleGame(){
        this.board = new GameBoard();
    }

    public GameBoard getGameBoard(){
        return this.board;
    }

    /*
    newInput() is called each time the Controller registers a new user input.
    -> passes the command on to the Animal that than checks the correctness of the move and performs it if so!
    Returns String with: move successful || move not successful
     */
    public boolean newInput(String animal, int x_value, int y_value) {
        // the players turn is determined by the animals "name".
        Animal actual = this.board.getAnimal(animal);
        //check if animal is alive!
        if (actual == null){
            return false;
        }
        return actual.move(x_value, y_value); // returns true if move was successful!
    }
}
