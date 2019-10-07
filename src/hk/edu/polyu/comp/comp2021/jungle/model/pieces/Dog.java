package hk.edu.polyu.comp.comp2021.jungle.model.pieces;
import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;

public class Dog extends Animal{
    public Dog(int x_location, int y_location, boolean frontPlayer, GameBoard gameBoard){
        super(x_location, y_location, frontPlayer, 3, gameBoard);
    }

}