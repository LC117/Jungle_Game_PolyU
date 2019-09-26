package hk.edu.polyu.comp.comp2021.jungle.model;

import java.util.Arrays;

/*
"GameBoard" is a two-dimensional array containing the single pieces:
    1)Rat
    2)Cat
    3)Dog
    4)Wolf
    5)Leopard
    6)Tiger
    7)Lion
    8)Elephant
    -> If the field is has the value "null" it is empty
    Each Animal will have a toString() method that returns its string representation: e.g elephant -> "8" | none -> "_"
 */
public class GameBoard {
    private static int height = 9;
    private static int width = 7;
    private String [][] boardArray = new String [height][width]; // y, x
    //TODO change the array type to Animal and use its toString() method

    public GameBoard() {
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0 || y == height - 1) {
                    if (x == 3) {
                        boardArray[y][x] = "{_}"; // den
                    } else if (x == 2 || x == 4) {
                        boardArray[y][x] = "[_]"; //traps
                    }else{
                        boardArray[y][x] = "|_|"; //water
                    }
                } else if (y == 1 || y == height - 2) {
                    if (x == 3) {
                        boardArray[y][x] = "[_]";
                    } else {
                        boardArray[y][x] = "|_|";
                    }
                } else if (y == 3 || y == 4 || y == 5) {
                    if (x == 1 || x == 2 || x == width - 2 || x == width - 3) {
                        boardArray[y][x] = "(_)";
                    }else{
                        boardArray[y][x] = "|_|";
                    }
                } else {
                    boardArray[y][x] = "|_|";
                }
            }
        }
    }
    @Override
    public String toString(){
        String boardString = "";
        for (int i = 0; i < 9; i++) {
            boardString += Arrays.toString(this.boardArray[i]) + "\n";
        }
        return boardString;
    }
//    public static void main (String []args){
//        GameBoard g = new GameBoard();
//        System.out.println(g.toString());
//    }
}


