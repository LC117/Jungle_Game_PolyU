package hk.edu.polyu.comp.comp2021.jungle.model.pieces;
//Animals is part of the model as we consider it part of the games database.
public class Animal{
    private int x_location;
    private int y_location;
    private boolean frontPlayer; //true if Animal is owned by front Player.
    private int strength;

    public Animal (int x_location, int y_location, boolean frontPlayer, int strength){
       if (!isMoveLegal(x_location, y_location)) {
           System.out.println("initialization Parameters are wrong!");
           System.exit(-1);
       } else {
           this.x_location = x_location;
           this.y_location = y_location;
           this.frontPlayer = frontPlayer;
           this.strength = strength;
       }
    }

    public int getStrength(){
        return this.strength;
    }
    public int getX_location(){
        return  this.x_location;
    }
    public int getY_location(){
        return this.y_location;
    }

    /*
    isMoveLegal() returns true if the location is possible: in Bounds [0, ..., 6], not on own den or water.
     */
    private boolean isMoveLegal (int x, int y){
        boolean inBounds =  !(x < 0 || x > 6 || y < 0 || y > 8);
        boolean water = (y == 3 || y == 4 || y == 5) && (x == 1 || x == 2 || x == 4 || x == 5);
        boolean ownDen = frontPlayer ? (x == 3 && y == 0) : (x == 3 && y == 8);
        return !water || inBounds || !ownDen; // not in water, in Bound, not on OWN Den
    }

    /*
    move() allows an animal to change its location.
    If the new location is not possible because of the animals movement abilities ->returns false.
     */
    private boolean move (int x_new, int y_new){
        //check if new position is left, right, above or under the current location
        //as well as if the move is possible
        if (!isMoveLegal(x_new, y_new)) {
            return false;
        }
        boolean top = this.x_location == x_new && this.y_location == x_new - 1;
        boolean left = this.x_location == x_new + 1 && this.y_location == x_new;
        boolean right = this.x_location == x_new - 1 && this.y_location == x_new;
        boolean bottom = this.x_location == x_new && this.y_location == x_new + 1;
        //TODO check if on the fiels is another animal (own or stronger enemy one)
        if(top || right || left || bottom){ // perform move
            this.x_location = x_new;
            this.y_location = y_new;
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        if (!frontPlayer){
            return (char)(this.strength+96) + "";
        }else{
            return this.strength + "";
        }
    }
}

