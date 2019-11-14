package hk.edu.polyu.comp.comp2021.jungle.model.pieces;
import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;

public class Lion extends Animal {

    private int x_location;
    private int y_location;
    private boolean frontPlayer; //true if Animal is owned by front Player.
    private int strength;
    private GameBoard gameBoard;

    public Lion(int x_location, int y_location, boolean frontPlayer, GameBoard gameBoard) {
        super(x_location, y_location, frontPlayer, 7, gameBoard);

        this.x_location = x_location;
        this.y_location = y_location;
        this.frontPlayer = frontPlayer;
        this.strength = 7;
        this.gameBoard = gameBoard;
    }

    private boolean isMoveLegal(int x, int y) {
        boolean inBounds = !(x < 0 || x > 6 || y < 0 || y > 8);
        boolean water = checkWater(x, y);
        boolean ownDen = frontPlayer ? (x == 3 && y == 0) : (x == 3 && y == 8);
        boolean animalInWay = false; //true if: stronger Enemy, own animal

        Animal collisionAnimal;

        collisionAnimal = gameBoard.getAnimal(x, y);

        if (collisionAnimal == null) {
            animalInWay = false;
        } else if (collisionAnimal.getFrontPlayer() == frontPlayer) {
            animalInWay = true;
        } else if (collisionAnimal.getStrength() > this.strength) {
            animalInWay = true;
        } else if (collisionAnimal.getStrength() <= this.strength) { //other Animal will be eaten!
            animalInWay = false;
        }


        return !water && inBounds && !ownDen && !animalInWay; // not in water, in Bound, not on OWN Den, not on own Animal, not stronger enemy Animal!
    }

    public boolean checkWater(int x, int y) {
        return (y == 3 || y == 4 || y == 5) && (x == 1 || x == 2 || x == 4 || x == 5);
    }

    //returns true if all positions in between are water and no rat rat is in between as well.
    private boolean ligerJump(int x_to, int y_to){
        //horizontally we must check if between the two spots is only water (two water areas could be in between!)
        int x_smaller = Math.min(x_to, x_location);
        int x_bigger = Math.max(x_to, x_location);
        int y_smaller = Math.min(y_to, y_location);
        int y_bigger = Math.max(y_to, y_location);

        boolean onlyWaterNoAnimal = true; //No water and no animals in water between!

        if (x_location != x_to && y_location == y_to){
            for (int i = x_smaller + 1 ; i < x_bigger ; i++) {
                onlyWaterNoAnimal = onlyWaterNoAnimal && gameBoard.isWater(i, y_location) && gameBoard.getAnimal(i, y_location) == null;
            }
        }else if (y_location != y_to && x_location == x_to){
            for (int i = y_smaller + 1; i < y_bigger ; i++) {
                onlyWaterNoAnimal = onlyWaterNoAnimal && gameBoard.isWater(x_location, i) && gameBoard.getAnimal(x_location, i) == null;
            }
        }else {
            return false;
        }
        return onlyWaterNoAnimal;
    }

    /*
    move() allows an animal to change its location.
    If the new location is not possible because of the animals movement abilities ->returns false.
     */
    public boolean move(int x_new, int y_new) {
        //check if new position is left, right, above or under the current location
        //as well as if the move is possible
        if (!isMoveLegal(x_new, y_new)) {
            return false;
        }
        boolean top = this.x_location == x_new && this.y_location == y_new - 1;
        boolean left = this.x_location == x_new + 1 && this.y_location == y_new;
        boolean right = this.x_location == x_new - 1 && this.y_location == y_new;
        boolean bottom = this.x_location == x_new && this.y_location == y_new + 1;
        //TODO check if on the fields is another animal (own or stronger enemy one)
        if (top || left || right || bottom || ligerJump(x_new, y_new)) {
            if (gameBoard.getAnimal(x_new, y_new) != null) { // this test is ok here because in is MoveLegal() we only left open if there is a weaker animal.
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