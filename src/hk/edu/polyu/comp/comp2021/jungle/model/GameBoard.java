package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.model.pieces.*;

import java.util.Arrays;

/*
"GameBoard" is a two-dimensional array containing the single pieces:
    1)Rat 🐀
    2)Cat🐱
    3)Dog 🐶
    4)Wolf 🐺
    5)Leopard 🐆
    6)Tiger 🐯
    7)Lion 🦁
    8)Elephant 🐘
    -> the animals are inserted in the 2D-String-Array.
    Each Animal will have a toString() method that returns its string representation: e.g elephant -> "8" | none -> "_"
    DONE: maybe one could use number 1-8 and the other letters a-h

    Idea:
    The GameBoard Class has the 2D String representation of itself as private variable. With getter and a function that performs changes.
    Each Animal has its location, strength and abilities stored in its own Class. They have access to information from the game-board playing field. (e.g. which animal is where)
    Each time an animal is moved we have to test if the new location is valid: no stronger animal there, in bounds, not on own Dan and water check.
    -> if the location is possible is done by calling the move() function that performs the move if possible.
 */
public class GameBoard {
    private static int height = 9;
    private static int width = 7;
    private String [][] boardArray = new String [height][width]; // y, x -> like this because of the printing
    private Animal [] playerFront = new Animal[8];
    private Animal [] playerBack = new Animal[8];

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
        initializeAnimals();
    } // Constructor

    private void initializeAnimals(){
        playerFront[0] = new Rat(6, 2, true, this);
        playerFront[1] = new Cat(1, 1 ,true, this);
        playerFront[2] = new Dog(5, 1, true, this);
        playerFront[3] = new Wolf(2, 2, true, this);
        playerFront[4] = new Leopard(4, 2, true,this);
        playerFront[5] = new Tiger (0, 0, true, this);
        playerFront[6] = new Lion (6, 0, true, this);
        playerFront[7] = new Elephant(0, 2, true, this);

        playerBack[0] = new Rat(0, 6, false, this);
        playerBack[1] = new Cat(5, 7, false, this);
        playerBack[2] = new Dog(1, 7, false, this);
        playerBack[3] = new Wolf(4, 6, false, this);
        playerBack[4] = new Leopard(2, 6, false, this);
        playerBack[5] = new Tiger(6, 8, false, this);
        playerBack[6] = new Lion(0, 8, false, this);
        playerBack[7] = new Elephant(6, 6, false, this);

        for (int i = 0; i < 8; i++) {
            insert(playerFront[i]);
            insert(playerBack[i]);
        }
    }

    private void insert(Animal animal){
        boardArray[animal.getY_location()][animal.getX_location()] = "|" + animal.toString() + "|";
    }

    public String[][] getGameboard(){
        return this.boardArray;
    }


    public Animal getAnimal(String animal){
        boolean frontPlayer = animal.charAt(0) < 60;
        if(frontPlayer){ //animal: [1-8] is appartaining to frontPlayer
            return this.playerFront[Integer.parseInt(animal)];
        }else{
            int animalNumber;
            animalNumber = animal.charAt(0) - 97;
            return this.playerFront[animalNumber];
        }
    }


    public Animal getAnimal(int x, int y){

            for (int i = 0; i < 8; i++) {
                if (playerFront[i].getX_location() == x && playerFront[i].getY_location() == y){
                    return playerFront[i];
                }
                if (playerBack[i].getX_location() == x && playerBack[i].getY_location() == y){
                    return playerBack[i];
                }
            }
            return null;
    }

    public void moveAnimal(int from_x, int from_y, Animal movedAnimal){ //Parameter is the Animal with its new location. Is called by the Animal class to submit change.
        boardArray[from_y][from_x] = boardArray[from_x][from_y].charAt(0) + "_" + boardArray[from_x][from_y].charAt(2);
        boardArray[movedAnimal.getY_location()][ movedAnimal.getX_location()] = boardArray[from_x][from_y].charAt(0) + movedAnimal.toString() + boardArray[from_x][from_y].charAt(2);
    }
    
    public void removeAnimal(Animal eatenAnimal){

        if(eatenAnimal.getFrontPlayer()){
            for (int i = 0; i < 8; i++) {
                if (playerFront[i].equals(eatenAnimal)){
                    playerFront[i] = null; // animal is gone
                    return;
                }
            }
        }
        else {
            for (int i = 0; i < 8; i++) {
                if (playerBack[i].equals(eatenAnimal)){
                    playerBack[i] = null; // animal is gone
                    return;
                }
            }
        }
    }


    public boolean stillAlive(boolean frontplayer, String animal){
        Animal [] actual;
        if (frontplayer){
            actual = playerFront;
        }else{
            actual = playerBack;
        }
        for (int i = 0; i < 8; i++) {
            if(actual[i] != null && actual[i].toString().equals(animal)){
                return true;
            }
        }
        return false;
+
    }

    public boolean stillAlive(boolean frontplayer, String animal){
        Animal [] actual;
        if (frontplayer){
            actual = playerFront;
        }else{
            actual = playerBack;
        }
        for (int i = 0; i < 8; i++) {
            if(actual[i] != null && actual[i].toString().equals(animal)){
                return true;
            }
        }
        return false;
    }


    public Animal [] getplayerFront(){
        return playerFront;
    }
    public Animal [] getPlayerBack(){
        return this.playerBack;
    }



    @Override
    public String toString(){
        String boardString = "";
        for (int i = 8; i >= 0; i--) {

            //boardString += Arrays.toString(this.boardArray[i]) + "\n";


            //boardString += i + " " + Arrays.toString(this.boardArray[i]) + "\n";
            boardString += i + "| ";
            for(int x = 6; x >=0; x--){
                boardString += boardArray[i][x] + " ";
            }
            boardString += " \n";
        }
        boardString += "_|_____________________________ \n";
        boardString += " |  0   1   2   3   4   5   6 ";

        return boardString;
    }
}


