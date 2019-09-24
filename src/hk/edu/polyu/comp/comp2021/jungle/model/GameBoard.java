package hk.edu.polyu.comp.comp2021.jungle.model;

import java.util.Arrays;

public class GameBoard {

    private String[][] createBoard(){
        int height = 9;
        int width = 7;

        String [][]  board = new String [height][width]; // y, x

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0 || y == height - 1) {
                    if (x == 3) {
                        board[y][x] = "{_}"; // den
                    } else if (x == 2 || x == 4) {
                        board[y][x] = "[_]"; //traps
                    }else{
                        board[y][x] = "(_)"; //water
                    }
                } else if (y == 1 || y == height - 2) {
                    if (x == 3) {
                        board[y][x] = "[_]";
                    } else {
                        board[y][x] = "(_)";
                    }
                } else if (y == 3 || y == 4 || y == 5) {
                    if (x == 1 || x == 2 || x == width - 1 || x == width - 2) {
                        board[y][x] = "(_)";
                    }else{
                        board[y][x] = "(_)";
                    }
                } else {
                    board[y][x] = "|_|";
                }
            }
        }
        return board;
    }
    @Override
    public String toString(){
        String board = "";
        for (int i = 0; i < 9; i++) {

        }
        return "";
    }
    public static void main (String []args){
        GameBoard g = new GameBoard();
        System.out.println(Arrays.deepToString(g.createBoard()));
    }
}


