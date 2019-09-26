package hk.edu.polyu.comp.comp2021.jungle;

import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame; // Model
import hk.edu.polyu.comp.comp2021.jungle.controller.Controller; // Controller
import hk.edu.polyu.comp.comp2021.jungle.view.GameBoard; //View

public class Application {

    public static void main(String[] args){
        JungleGame game = new JungleGame(); // this is the Model, but we should only access the Controller
        Controller controller = new Controller();
        GameBoard gameBoard = new GameBoard();

        // start playing the game
    }
}
