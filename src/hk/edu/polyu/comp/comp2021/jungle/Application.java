package hk.edu.polyu.comp.comp2021.jungle;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame; // Model
import hk.edu.polyu.comp.comp2021.jungle.controller.Controller; // Controller
import hk.edu.polyu.comp.comp2021.jungle.view.View ; //View

public class Application {
    public static void main(String[] args){
        JungleGame game = new JungleGame(); // this is the Model, but we should only access the Controller
        Controller controller = new Controller();
        View view = new View();
        // start playing the game
        controller.playGame(view, game); // we consider this the first input from the user.
    }
}
