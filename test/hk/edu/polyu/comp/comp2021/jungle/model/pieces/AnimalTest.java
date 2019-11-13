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
            {1,2,1}, // valid move
            {2,1,1}, // valid move
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
    }
}