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
        int [][] arr = new int[][]{
                {2,3,  2,7}, // jump up vertically across water
                {1,4,  4,4}, // jump right horizontally across water
                {2,7,  2,3}, // jump down vertically across water
                {4,4,  1,4} // jump left horizontally across water
        };
        for(int i = 0; i < arr.length; i++){
            Animal testAnimal = new Animal(arr[i][0], arr[i][1], true, 7, gameBoard);
            temp = testAnimal.move(arr[i][2], arr[i][3]);
            System.out.println(temp);
            int x = testAnimal.getX_location();
            int y = testAnimal.getY_location();
            System.out.println(x);
            System.out.println(y);
            int [] value = new int [] {x,y};
            int [] truth = new int [] {arr[i][2], arr[i][3]};

            assertEquals(truth, value, "Tests if Animal.move() returns correct location after move");
        }

    }
}