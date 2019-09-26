package hk.edu.polyu.comp.comp2021.jungle.model.pieces;

public class Animal{
    private int x_location;
    private int y_location;

    public Animal (int x_location, int y_location){
       if (x_location < 0 || x_location > 6 || y_location < 0 || y_location >6) {
           System.out.println("initialization Parameters are wrong!");
           System.exit(-1);
       } else {
           this.x_location = x_location;
           this.y_location = y_location;
       }
    }

    /*
    move() allows an animal to change its location.
    If the new location is not possible because of the animals movement abilities ->returns false.
     */
    //TODO
    private boolean movePossible (int x_new, int y_new){
        return false;
    }
}

