package hk.edu.polyu.comp.comp2021.jungle.model.pieces;
import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;

public class Cat extends Animal{

    public Cat(int x_location, int y_location, boolean frontPlayer, GameBoard gameBoard){
        super(x_location, y_location, frontPlayer, 2, gameBoard);
    }

}