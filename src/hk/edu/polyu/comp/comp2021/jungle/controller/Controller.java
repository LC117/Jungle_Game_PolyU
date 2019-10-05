package hk.edu.polyu.comp.comp2021.jungle.controller;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import hk.edu.polyu.comp.comp2021.jungle.view.View;
import java.util.Scanner;
public class Controller {

    /*
    playGame() gets the next Line of user input and test it syntactically.
    Then the line is send to the Model: "game" where it is checked semantically and update the game board.
    Expected format: "*first 3 letter of animal name*-*x_coordinate*-*y_coordinate*"
    */
    public void playGame(View view, JungleGame game){
        game.updateView(view);
        Scanner scanner = new Scanner(System.in);
        String input;
        String [] animalXY; // the input String that is split on "-".
        while (true){
            input = scanner.nextLine();
            animalXY = input.split("-");
            if(!inputFormatOk(animalXY)){
                view.displayError("Input has wrong format: use *first 3 letter of animal name*-*x_coordinate*-*y_coordinate* and try again: ");
            }else{
                //pass the input on to the model
                game.newInput(animalXY[0], Integer.valueOf(animalXY[1]), Integer.valueOf(animalXY[2]));
            }
        }
    }

    /*
    inputFormatOk() checks if the syntax of the input is ok: 1)animal has 3 letters 2)3) if the coordinates are Integers.
    returns true if syntax is correct.
     */
    private boolean inputFormatOk(String [] input){
        boolean lengthOk = input.length == 1;
        try{//check if coordinates are Integers
            Integer.parseInt(input[1]);
            Integer.parseInt(input[2]);
        }catch (Exception e){
            return false;
        }
        boolean nameOk = input[0].length() == 3;
        return lengthOk && nameOk;
    }
}