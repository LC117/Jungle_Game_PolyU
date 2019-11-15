package hk.edu.polyu.comp.comp2021.jungle.model.pieces;

import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

class LionTest {
    private GameBoard gameBoard = new GameBoard();

    @Test
    void move() {
        gameBoard.empty();
        boolean temp;
        int [][] arr = new int[][]{ //{from_x, fromY, to_X, to_y
                {2,2,  2,6}, // jump up vertically across water
                {0,3,  3,3}, // jump right horizontally across water
                {2,6,  2,2}, // jump down vertically across water
                {3,3,  0,3},
                {1,1,  1,1}// jump left horizontally across water
        };
        // test Water jumps:
        for(int i = 0; i < arr.length; i++){
            Lion testAnimal = new Lion(arr[i][0], arr[i][1], true,  gameBoard);
            temp = testAnimal.move(arr[i][2], arr[i][3]);
            int x = testAnimal.getX_location();
            int y = testAnimal.getY_location();
            int [] actualPosition = new int [] {x,y};
            int [] truth = new int [] {arr[i][2], arr[i][3]};

            assertEquals(truth[0], actualPosition[0], "Tests if Animal.move() returns correct x_location after move");
            assertEquals(truth[1], actualPosition[1], "Tests if Animal.move() returns correct y_location after move");
        }
        //Jump over both lakes not allowed:
        Lion testAnimal = new Lion(6, 4, true,  gameBoard);
        gameBoard.insertAnimal(testAnimal);
        assertEquals(testAnimal.move(0, 4), false, "Tests if Animal.move() returns correct y_location after move");
        //Eat weaker Animal:
        Wolf toEat = new Wolf(6, 5, false, gameBoard);
        gameBoard.insertAnimal(toEat);
        assertEquals(gameBoard.getAnimal(6, 5),toEat,  "Animal was correctly inserted!");
        assertEquals( true, testAnimal.move(6, 5), "Animal correctly eaten!");
        assertEquals(testAnimal, gameBoard.getAnimal(6, 5), "Lion on correct position.");
        //Cant eat stronger Animal:
        Elephant stronger = new Elephant(6, 6, false, gameBoard);
        gameBoard.insertAnimal(stronger);
        assertEquals(false, testAnimal.move(6, 6), "This move should not work!");
        //own animal in way:
        Rat ownAnimal = new Rat(6, 4, true, gameBoard);
        gameBoard.insertAnimal(ownAnimal);
        assertEquals(false, testAnimal.move(6, 4), "This move should not work!");
        //Jump not possible if animal in water between
        Rat inWater = new Rat(5, 5, true, gameBoard);
        gameBoard.insertAnimal(inWater);
        assertEquals(false, testAnimal.move(3, 5), "This move should not work!");

    }
}