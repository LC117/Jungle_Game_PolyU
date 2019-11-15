package hk.edu.polyu.comp.comp2021.jungle.model;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.*;

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
    private String [][] boardArray; // y, x -> like this because of the printing
    private Animal [] playerFrontAnimals = new Animal[8];
    private Animal [] playerBackAnimals = new Animal[8];

    public GameBoard() {
        this.boardArray = setUpGameBoard();
        initializeAnimals();
    }

    public GameBoard(Animal [] playerFrontAnimals, Animal [] playerBackAnimals, String [][] boardArray  ) {
        this.boardArray = boardArray;
        this.playerFrontAnimals = playerFrontAnimals;
        this.playerBackAnimals = playerBackAnimals;
    }

    public void resetGameBoard(Animal [] playerFrontAnimals, Animal [] playerBackAnimals){ //
        this.boardArray = setUpGameBoard();
        this.playerFrontAnimals = playerFrontAnimals;
        this.playerBackAnimals = playerBackAnimals;
        for (int i = 0; i < 8; i++) {
            insert(playerFrontAnimals[i]);
            insert(playerBackAnimals[i]);
        }

    }

    private String [][] setUpGameBoard(){
        int height = 9;
        int width = 7;
        String [][] boardArray = new String [height][width];
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0 || y == height - 1) {
                    if (x == 3) {
                        boardArray[y][x] = "{_}"; // den
                    } else if (x == 2 || x == 4) {
                        boardArray[y][x] = "[_]"; //traps
                    }else{
                        boardArray[y][x] = "|_|"; //land
                    }
                } else if (y == 1 || y == height - 2) {
                    if (x == 3) {
                        boardArray[y][x] = "[_]";
                    } else {
                        boardArray[y][x] = "|_|";
                    }
                } else if (y == 3 || y == 4 || y == 5) {
                    if (x == 1 || x == 2 || x == width - 2 || x == width - 3) {
                        boardArray[y][x] = "(_)"; //water
                    }else{
                        boardArray[y][x] = "|_|";
                    }
                } else {
                    boardArray[y][x] = "|_|";
                }
            }
        }
        return boardArray;
    }

    private void initializeAnimals(){

        playerFrontAnimals[0] = new Rat(6, 2, true, this);
        playerFrontAnimals[1] = new Cat(1, 1 ,true, this);
        playerFrontAnimals[2] = new Dog(5, 1, true, this);
        playerFrontAnimals[3] = new Wolf(2, 2, true, this);
        playerFrontAnimals[4] = new Leopard(4, 2, true,this);
        playerFrontAnimals[5] = new Tiger (0, 0, true, this);
        playerFrontAnimals[6] = new Lion (6, 0, true, this);
        playerFrontAnimals[7] = new Elephant(0, 2, true, this);

        playerBackAnimals[0] = new Rat(0, 6, false, this);
        playerBackAnimals[1] = new Cat(5, 7, false, this);
        playerBackAnimals[2] = new Dog(1, 7, false, this);
        playerBackAnimals[3] = new Wolf(4, 6, false, this);
        playerBackAnimals[4] = new Leopard(2, 6, false, this);
        playerBackAnimals[5] = new Tiger(6, 8, false, this);
        playerBackAnimals[6] = new Lion(0, 8, false, this);
        playerBackAnimals[7] = new Elephant(6, 6, false, this);

        for (int i = 0; i < 8; i++) {
            insert(playerFrontAnimals[i]);
            insert(playerBackAnimals[i]);
        }
    }

    private void insert(Animal animal) {
        int x = animal.getX_location();
        int y = animal.getY_location();
        char bracket = boardArray[y][x].charAt(0);
        char closingBracket;

        if (bracket == '|') {
            boardArray[y][x] = "|" + animal.toString() + "|";
        } else if (bracket == '(') {
            closingBracket = (char) (bracket + 1);
            boardArray[y][x] = bracket + animal.toString() + closingBracket;
        } else {
            closingBracket = (char) (bracket + 2);
            boardArray[y][x] = bracket + animal.toString() + closingBracket;
        }
    }

    public String[][] getGameBoard(){
        return this.boardArray;
    }

    public Animal getAnimal(String animal){
        boolean frontPlayer = animal.charAt(0) < 60;
        if(frontPlayer){ //animal: [1-8] is appartaining to frontPlayer
            return this.playerFrontAnimals[Integer.parseInt(animal)-1];
        }else{
            int animalNumber;
            animalNumber = animal.charAt(0) - 97;
            return this.playerBackAnimals[animalNumber];
        }
    }

    public Animal getAnimal(int x, int y){
            // the != null Statement in the if beginning assures that the animal is still alive
            for (int i = 0; i < 8; i++) {
                if (playerFrontAnimals[i] != null && playerFrontAnimals[i].getX_location() == x && playerFrontAnimals[i].getY_location() == y){
                    return playerFrontAnimals[i];
                }
                if (playerBackAnimals[i] != null && playerBackAnimals[i].getX_location() == x && playerBackAnimals[i].getY_location() == y){
                    return playerBackAnimals[i];
                }
            }
            return null;
    }

    public void moveAnimal(int from_x, int from_y, Animal movedAnimal){ //Parameter is the Animal with its new location. Is called by the Animal class to submit change.
        String temp = this.boardArray[from_y][from_x];
        this.boardArray[from_y][from_x] = temp.charAt(0) + "_" + temp.charAt(2);
        temp = this.boardArray[movedAnimal.getY_location()][ movedAnimal.getX_location()];
        this.boardArray[movedAnimal.getY_location()][ movedAnimal.getX_location()] = temp.charAt(0) + movedAnimal.toString() + temp.charAt(2);
    }
    
    public void removeAnimal(Animal eatenAnimal){

        if(eatenAnimal.getFrontPlayer()){
            for (int i = 0; i < 8; i++) {
                if (playerFrontAnimals[i] != null && playerFrontAnimals[i].equals(eatenAnimal)){
                    playerFrontAnimals[i] = null; // animal is gone
                    return;
                }
            }
        }
        else {
            for (int i = 0; i < 8; i++) {
                if (playerBackAnimals[i] != null && playerBackAnimals[i].equals(eatenAnimal)){
                    playerBackAnimals[i] = null; // animal is gone
                    return;
                }
            }
        }
    }

    public boolean animalStillAlive(String animal){

        for (int i = 0; i < 8; i++) {
            if(playerFrontAnimals[i] != null && playerFrontAnimals[i].toString().equals(animal)){
                return true;
            }else if (playerBackAnimals[i] != null && playerBackAnimals[i].toString().equals(animal)){
                return true;
            }
        }
        return false;
    }

    public Animal [] getPlayerFrontAnimals(){
        return this.playerFrontAnimals;
    }

    public Animal [] getPlayerBackAnimals(){
        return this.playerBackAnimals;
    }

    public boolean isTrap(int x, int y){
        return this.boardArray[y][x].indexOf('[') == 0;
    }

    public boolean isWater(int x, int y){
        return this.boardArray[y][x].indexOf('(') == 0;
    }

    public void empty(){ //removes all animals
        for (int i = 0; i < 8; i++) {
            if(playerFrontAnimals[i] != null) {
                playerFrontAnimals[i] = null;
            }if(playerBackAnimals[i] != null) {
                playerBackAnimals[i] = null;
            }
        }
    }
    public void insertAnimal(Animal animal){
        int position = animal.getStrength() - 1;
        if(animal.getFrontPlayer()){
            playerFrontAnimals[position] = animal;
        }else {
            playerBackAnimals[position] = animal;
        }
    }

    @Override
    public String toString(){
        String boardString = "";
        for (int i = 8; i >= 0; i--) {

            //boardString += Arrays.toString(this.boardArray[i]) + "\n";


            //boardString += i + " " + Arrays.toString(this.boardArray[i]) + "\n";
            boardString += (i+1) + "| ";
            for(int x = 6; x >=0; x--){
                boardString += boardArray[i][6-x] + " ";
            }
            boardString += " \n";
        }
        boardString += "_|_____________________________ \n";
        boardString += " |  A   B   C   D   E   F   G ";

        return boardString;
    }
}


