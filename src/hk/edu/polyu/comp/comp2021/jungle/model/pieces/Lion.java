package hk.edu.polyu.comp.comp2021.jungle.model.pieces;
import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;

public class Lion extends Animal{
    public Lion (int x_location, int y_location, boolean frontPlayer, GameBoard gameBoard) {
        super(x_location, y_location, frontPlayer, 7, gameBoard);
    }
    private int x_location = super.getX_location();
    private int y_location = super.getY_location();
    private boolean frontPlayer = super.getFrontPlayer(); //true if Animal is owned by front Player.
    private int strength = super.getStrength();
    private GameBoard gameBoard = super.getGameBoard();


    private boolean isMoveLegal (int x, int y){
        boolean inBounds =  !(x < 0 || x > 6 || y < 0 || y > 8);
        boolean water = false;
        boolean ownDen = frontPlayer ? (x == 3 && y == 0) : (x == 3 && y == 8);
        boolean animalInWay = false; //true if: stronger Enemy, own animal
        boolean onLand = !((this.y_location == 3 || this.y_location == 4 || this.y_location == 5) && (this.x_location == 1 || this.x_location == 2 || this.x_location == 4 || this.x_location == 5));
        Animal collisionAnimal =  null;
        try{
            collisionAnimal = gameBoard.getAnimal(x, y);
        }
        catch(Exception e){
            collisionAnimal = null;
        }
        if (collisionAnimal == null){
            animalInWay = false;
        }
        else if (collisionAnimal.getFrontPlayer() == frontPlayer){
            animalInWay = true;
        }else if (collisionAnimal.getStrength() > this.strength){
            animalInWay = true;
        }else if (collisionAnimal.getStrength() <= this.strength){ //other Animal will be eaten!
            animalInWay = false;
        }


        return !water && inBounds && !ownDen && !animalInWay; // not in water, in Bound, not on OWN Den, not on own Animal, not stronger enemy Animal!
    }
    public boolean checkWater(int x, int y){
        return (y == 3 || y == 4 || y == 5) && (x == 1 || x == 2 || x == 4 || x == 5);
    }

    //Lion and Tiger check for next space being across the river
    public boolean ligerJump(int x, int y){
        boolean isValid = false;
        if(x == this.x_location){
            //means that check for up and down
            //checks if original position is next to water
            if(checkWater(this.x_location,this.y_location + 1) || checkWater(this.x_location, this.y_location -1)){
                //checks if new position is next to water
                if(checkWater(x, y-1) || checkWater(x, y+1)){
                    return true;
                }
            }
        }
        if(y == this.y_location){
            //means that check for side to side
            //checks if original position is next to water
            if(checkWater(this.x_location + 1,this.y_location) || checkWater(this.x_location - 1, this.y_location)){
                //checks if new position is next to water
                if(checkWater(x-1, y) || checkWater(x +1, y)){
                    return true;
                }
            }
        }


        return isValid;
    }


    //Lion and Tiger jumps over the river with new x and y being the closest water square
    public int[] liger(int x, int y){
        int[] newPos = new int[2];
        boolean water = (y == 3 || y == 4 || y == 5) && (x == 1 || x == 2 || x == 4 || x == 5);
        if(water && (this.strength == 6 || this.strength == 7)){
            boolean vertical = false;
            boolean up = true;
            if(this.y_location == y){
                vertical = true;
            }
            if((this.y_location > y) || (this.x_location > x)){
                up = false;
            }

            Animal collisionAnimal = gameBoard.getAnimal(x, y);
            //Checks to see if there is an animal in the way on the water (i.e. rat)

            while (water) {
                if(collisionAnimal != null){
                    //Puts an extreme number in x to signify that movement should fail
                    x = 5000;
                    break;
                }
                if (vertical) {
                    if (up)
                        y += 1;
                    else
                        y -= 1;
                    water = (y == 3 || y == 4 || y == 5) && (x == 1 || x == 2 || x == 4 || x == 5);
                } else {
                    if (up)
                        x += 1;
                    else
                        x -= 1;
                    water = (y == 3 || y == 4 || y == 5) && (x == 1 || x == 2 || x == 4 || x == 5);
                }

                collisionAnimal = gameBoard.getAnimal(x, y);

            }

        }
        newPos[0] = x;
        newPos[1] = y;

        return newPos;
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
        if(top || left || right || bottom || ligerJump(x_new, y_new)){
            int[] newSpace = liger(x_new, y_new);
            //checks for something blocking the lion or tiger from moving across the water
            if(newSpace[0] == 5000){
                return false;
            }
            else{
                x_new = newSpace[0];
                y_new = newSpace[1];
            }

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
}