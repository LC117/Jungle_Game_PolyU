package hk.edu.polyu.comp.comp2021.jungle.model;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Tiger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class GameBoardTest {


    @Test
    void getGameboard1() {
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