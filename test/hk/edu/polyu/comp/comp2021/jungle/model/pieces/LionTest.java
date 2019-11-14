package hk.edu.polyu.comp.comp2021.jungle.model.pieces;

import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

class LionTest {
    private GameBoard gameBoard = new GameBoard();
    private Lion testAnimal = new Lion(2, 3, true, gameBoard);

    @Test
    void move() {
        boolean temp;
        int [][] arr = new int[][]{ //{from_x, fromY, to_X, to_y
                {2,2,  2,6}, // jump up vertically across water
                {0,3,  3,3}, // jump right horizontally across water
                {2,6,  2,2}, // jump down vertically across water
                {3,3,  0,3} // jump left horizontally across water
        };
        for(int i = 0; i < arr.length; i++){
            Animal testAnimal = new Animal(arr[i][0], arr[i][1], true, 7, gameBoard);
            temp = testAnimal.move(arr[i][2], arr[i][3]);
            System.out.println(temp);
            int x = testAnimal.getX_location();
            int y = testAnimal.getY_location();
            int [] actualPosition = new int [] {x,y};
            int [] truth = new int [] {arr[i][2], arr[i][3]};

            assertEquals(truth[0], actualPosition[0], "Tests if Animal.move() returns correct x_location after move");
            assertEquals(truth[1], actualPosition[1], "Tests if Animal.move() returns correct y_location after move");
        }

    }
}