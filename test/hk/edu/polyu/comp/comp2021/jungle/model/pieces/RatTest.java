package hk.edu.polyu.comp.comp2021.jungle.model.pieces;

import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatTest {
    private GameBoard gameBoard = new GameBoard();
    @Test
    void move() {
        gameBoard.empty();
        boolean temp;
        int [][] arr = new int[][]{ //{from_x, fromY, to_X, to_y
                {2,2,  2,3}, // go up vertically across water
                {0,3,  1,3}, // go right horizontally across water
                {2,3,  2,2}, // go down vertically across water
                {1,3,  0,3}, // go left horizontally across water
                {1,1,  1,1}
        };
        // test Water jumps:
        for(int i = 0; i < arr.length; i++){
            Rat testAnimal = new Rat(arr[i][0], arr[i][1], true,  gameBoard);
            temp = testAnimal.move(arr[i][2], arr[i][3]);
            int x = testAnimal.getX_location();
            int y = testAnimal.getY_location();
            int [] actualPosition = new int [] {x,y};
            int [] truth = new int [] {arr[i][2], arr[i][3]};
            assertEquals(truth[0], actualPosition[0], "Tests if Animal.move() returns correct x_location after move");
            assertEquals(truth[1], actualPosition[1], "Tests if Animal.move() returns correct y_location after move");
        }
        Rat testAnimal = new Rat(6, 4, true,  gameBoard);
        gameBoard.insertAnimal(testAnimal);
        //Eat Elephant:
        Elephant toEat = new Elephant(6, 5, false, gameBoard);
        gameBoard.insertAnimal(toEat);
        assertEquals(gameBoard.getAnimal(6, 5),toEat,  "Animal was correctly inserted!");
        assertEquals( true, testAnimal.move(6, 5), "Animal correctly eaten!");
        assertEquals(testAnimal, gameBoard.getAnimal(6, 5), "Rat on correct position.");
        //Cant eat stronger Animal:
        Cat stronger = new Cat(6, 6, false, gameBoard);
        gameBoard.insertAnimal(stronger);
        assertEquals(false, testAnimal.move(6, 6), "This move should not work!");
        //own animal in way:
        Cat ownAnimal = new Cat(6, 4, true, gameBoard);
        gameBoard.insertAnimal(ownAnimal);
        assertEquals(false, testAnimal.move(6, 4), "This move should not work!");
        //Can't eat Rat in water if both aren't on the same ground type
        Rat inWater = new Rat(5, 5, false, gameBoard);
        gameBoard.insertAnimal(inWater);
        assertEquals(false, testAnimal.move(5, 5), "This move should not work!");
        // Can eat Rat in water
        gameBoard.empty();
        testAnimal = new Rat(5, 5, true,  gameBoard);
        gameBoard.insertAnimal(testAnimal);
        Rat testAnimalInWater = new Rat(4, 5, false,  gameBoard);
        gameBoard.insertAnimal(testAnimalInWater);
        assertEquals(true, testAnimal.move(4, 5), "This move should not work!");
        assertEquals(testAnimal, gameBoard.getAnimal(4, 5), "Rat on correct position.");
    }
}