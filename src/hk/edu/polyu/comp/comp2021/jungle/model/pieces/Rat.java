package hk.edu.polyu.comp.comp2021.jungle.model.pieces;
import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;
public class Rat extends Animal{

    private int x_location;
    private int y_location;
    private boolean frontPlayer; //true if Animal is owned by front Player.
    private int strength;
    private GameBoard gameBoard;


    public Rat(int x_location, int y_location, boolean frontPlayer, GameBoard gameBoard) {
        super(x_location, y_location, frontPlayer, 1, gameBoard);

        this.x_location = x_location;
        this.y_location = y_location;
        this.frontPlayer = frontPlayer;
        this.strength = 1;
        this.gameBoard = gameBoard;
    }

    /*
    isMoveLegal() returns true if the location is possible: in Bounds [0, ..., 6], not on own den or water.
     */
    private boolean isMoveLegal (int x, int y){ // x and y are the new coordinates
        boolean inBounds =  !(x < 0 || x > 6 || y < 0 || y > 8);
        boolean water = (y == 3 || y == 4 || y == 5) && (x == 1 || x == 2 || x == 4 || x == 5);
        boolean ownDen = frontPlayer ? (x == 3 && y == 0) : (x == 3 && y == 8);
        boolean animalInWay = false; //true if: stronger Enemy, own animal
        boolean onLand = !((this.y_location == 3 || this.y_location == 4 || this.y_location == 5) &&
                (this.x_location == 1 || this.x_location == 2 || this.x_location == 4 || this.x_location == 5));
        Animal collisionAnimal;

        collisionAnimal = gameBoard.getAnimal(x, y);

        if (collisionAnimal == null){
            animalInWay = false;
        }else if(collisionAnimal.onWater() != this.onWater()){ //collision animal on water, this rat not on water.
            animalInWay = true;
        }else if (collisionAnimal.getFrontPlayer() == frontPlayer){
            animalInWay = true;
        }else if (collisionAnimal.getStrength() > this.strength){
            animalInWay = true;
        }else if (collisionAnimal.getStrength() <= this.strength){ //other Animal will be eaten!
            animalInWay = false;
        }
        //allows the rat to traverse on water and eat the elephant
        if(water && this.strength == 1){
            water = false;
        }
        if(collisionAnimal != null && collisionAnimal.getStrength() == 8 && collisionAnimal.getFrontPlayer() != frontPlayer && onLand){
            animalInWay = false;
        }
        return !water && inBounds && !ownDen && !animalInWay; // not in water, in Bound, not on OWN Den, not on own Animal, not stronger enemy Animal!
    }

    /*
    move() allows an animal to change its location.
    If the new location is not possible because of the animals movement abilities ->returns false.
     */
    public boolean move (int x_new, int y_new){
        //check if new position is left, right, above or under the current location
        //as well as if the move is possible
        if (!isMoveLegal(x_new, y_new)) {
            return false;
        }
        boolean top = this.x_location == x_new && this.y_location == y_new - 1;
        boolean left = this.x_location == x_new + 1 && this.y_location == y_new;
        boolean right = this.x_location == x_new - 1 && this.y_location == y_new;
        boolean bottom = this.x_location == x_new && this.y_location == y_new + 1;
        //TODO check if on the fiels is another animal (own or stronger enemy one)
        if(top || left || right || bottom){
            if(gameBoard.getAnimal(x_new, y_new) != null) { // this test is ok here because in is MoveLegal() we only left open if there is a weaker animal.
                gameBoard.removeAnimal(gameBoard.getAnimal(x_new, y_new));//remove eaten animal
            }
            int from_x = this.x_location;
            int from_y = this.y_location;
            this.x_location = x_new;
            this.y_location = y_new;
            gameBoard.moveAnimal(from_x, from_y, this);
            return true;
        }
        return false;
    }

    public int getX_location(){
        return  this.x_location;
    }

    public int getY_location(){
        return this.y_location;
    }
}