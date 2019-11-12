package hk.edu.polyu.comp.comp2021.jungle;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame; // Model
import hk.edu.polyu.comp.comp2021.jungle.controller.Controller; // Controller
import hk.edu.polyu.comp.comp2021.jungle.view.View ; //View

public class Application {
    public static void main(String[] args){
        View view = new View();
        JungleGame game = new JungleGame(); // Model
        Controller controller = new Controller(view, game);
        // start playing the game
        controller.playGame();
    }
}
