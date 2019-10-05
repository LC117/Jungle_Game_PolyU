package hk.edu.polyu.comp.comp2021.jungle.model.pieces;
import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;

public class Lion extends Animal{
    public Lion (int x_location, int y_location, boolean frontPlayer, GameBoard gameBoard) {
        super(x_location, y_location, frontPlayer, 7, gameBoard);
    }
}