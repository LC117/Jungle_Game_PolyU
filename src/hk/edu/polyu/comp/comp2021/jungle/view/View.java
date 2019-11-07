package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;

public class View{

    /*
    displayError() displays an error message, this is the case is the user input is syntactically or semantically wrong.
     */
    public void displayMessage(String message){
        System.out.println(message);
    }

    /*
    displays the passed GameBoard
     */
    public void displayGameUpdate(GameBoard gameBoard){
        System.out.println(gameBoard.toString());
    }

}