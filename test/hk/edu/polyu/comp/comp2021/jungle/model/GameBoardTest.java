package hk.edu.polyu.comp.comp2021.jungle.model;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Tiger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.Arrays;

class GameBoardTest {
    private GameBoard gameBoard = new GameBoard();

    @Test
    void getGameboard1() {
        String[][] board = new String[][]{
                {"|g|","|_|","[_]","{_}","[_]","|_|","|f|"},
                {"|_|","|c|","|_|","[_]","|_|","|b|","|_|"},
                {"|a|","|_|","|e|","|_|","|d|","|_|","|h|"},
                {"|_|","(_)","(_)","|_|","(_)","(_)","|_|"},
                {"|_|","(_)","(_)","|_|","(_)","(_)","|_|"},
                {"|_|","(_)","(_)","|_|","(_)","(_)","|_|"},
                {"|8|","|_|","|4|","|_|","|5|","|_|","|1|"},
                {"|_|","|2|","|_|","[_]","|_|","|3|","|_|"},
                {"|6|","|_|","[_]","{_}","[_]","|_|","|7|"}
        };

        String [][] temp = gameBoard.getGameBoard();
        System.out.println(temp[0].toString());
        System.out.println(board[0].toString());
        assertTrue(Arrays.equals(board, temp), "tests if get Gameboard returns proper array");
    }

    @Test
    void getAnimal2() {
        GameBoard gameBoard = new GameBoard();
        Tiger expected = new Tiger(0, 0, true, gameBoard);
        assertEquals(gameBoard.getAnimal(0, 0).toString(), expected.toString(),
                "Tests if view.displayMessage() prints a test message correctly.");
    }

    @Test
    void getAnimal3() {
    }

    @Test
    void moveAnimal1() {
    }

    @Test
    void removeAnimal1() {
    }

    @Test
    void animalStillAlive1() {
    }

    @Test
    void getplayerFront1() {
    }

    @Test
    void getPlayerBack1() {
    }

    @Test
    void toString2() {
    }
}