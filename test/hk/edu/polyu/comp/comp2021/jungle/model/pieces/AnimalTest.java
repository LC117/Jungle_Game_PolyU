package hk.edu.polyu.comp.comp2021.jungle.model.pieces;

import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private GameBoard gameBoard = new GameBoard();
    private Animal testAnimal = new Animal(1, 1, true, 4, gameBoard);
    @Test
    void getStrength() {
        int temp = testAnimal.getStrength();
        assertEquals(4, temp,
                "Tests if Animal.getStrength() returns correct strength");
    }

    @Test
    void getX_location() {
        int temp = testAnimal.getX_location();
        assertEquals(1, temp,
                "Tests if Animal.getX_location() returns correct x location");
    }

    @Test
    void getY_location() {
        int temp = testAnimal.getY_location();
        assertEquals(1, temp,
                "Tests if Animal.getY_location() returns correct y location");
    }

    @Test
    void move() {
        boolean temp;
        int [][] arr = new int[][]{
            {1,2,1}, // valid  - up
            {1,0,1}, //down
            {0,1,1}, // left
            {2,1,1}, // valid move - right
            {1,3,0}, // illegal move
            {3,1,0}, // illegal move
            {1,1,0}, // Stationary
            {2,2,0}  // diagonal
        };
        for(int i = 0; i < arr.length; i++){
            Animal testAnimal = new Animal(1,1, true, 4, gameBoard);
            temp = testAnimal.move(arr[i][0], arr[i][1]);
            int value = temp ? 1 : 0;
            assertEquals(arr[i][2], value, "Tests if Animal.move() returns correct boolean");
        }
        //test for Water:
        Animal testAnimal = new Animal(3,4, true, 4, gameBoard);
        temp = testAnimal.move(4, 4);
        assertEquals(false, temp, "Tests if Animal.move() returns correct boolean");
        // test for our of game board
        testAnimal = new Animal(6,0, true, 4, gameBoard);
        temp = testAnimal.move(7, 0);
        assertEquals(false, temp, "Tests if Animal.move() returns correct boolean");
        //Stronger collision animal:
        gameBoard.empty();
        testAnimal = new Animal(0,0, true, 4, gameBoard);
        Animal toEat = new Animal(1,0, false, 5, gameBoard);
        gameBoard.insertAnimal(testAnimal);
        gameBoard.insertAnimal(toEat);
        assertEquals(false, testAnimal.move(1, 0), "Tests if Animal.move() returns correct boolean");
        //Weaker collision animal:
        gameBoard.empty();
        testAnimal = new Animal(0,0, true, 4, gameBoard);
        toEat = new Animal(1,0, false, 3, gameBoard);
        gameBoard.insertAnimal(testAnimal);
        gameBoard.insertAnimal(toEat);
        assertEquals(true, testAnimal.move(1, 0), "Tests if Animal.move() returns correct boolean");
        assertEquals(testAnimal, gameBoard.getAnimal(1, 0), "Tests if Animal.move() returns correct boolean");
        // Eat stronger Enemy on Trap
        gameBoard.empty();
        testAnimal = new Animal(2,1, true, 4, gameBoard);
        toEat = new Animal(2,0, false, 8, gameBoard);
        gameBoard.insertAnimal(testAnimal);
        gameBoard.insertAnimal(toEat);
        assertEquals(true, testAnimal.move(2, 0), "Tests if Animal.move() returns correct boolean");
        assertEquals(testAnimal, gameBoard.getAnimal(2, 0), "Tests if Animal.move() returns correct boolean");

    }

    @Test
    void toString1() {
        Animal testAnimal = new Animal(1,1, true, 4, gameBoard);
        String temp = testAnimal.toString();
        assertEquals("4", temp, "Tests if Animal.toString() returns correct strength as a string");
    }

    @Test
    void getFrontPlayer() {
        Animal testAnimal = new Animal(1,1, true, 4, gameBoard);
        boolean temp = testAnimal.getFrontPlayer();
        assertEquals(true, temp, "Tests if Animal.getFrontPlayer() returns correct boolean");

        testAnimal = new Animal(1,1, false, 4, gameBoard);
        temp = testAnimal.getFrontPlayer();
        assertEquals(false, temp, "Tests if Animal.getFrontPlayer() returns correct boolean");
    }
    @Test
    void getGameboard(){
        GameBoard gameBoard = new GameBoard();
        Animal animal = new Animal(0, 0, true, 1, gameBoard);
        assertEquals(gameBoard, animal.getGameBoard(), "Tests if Gameboard is returned correctly.");
    }
}