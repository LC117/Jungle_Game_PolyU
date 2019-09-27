package hk.edu.polyu.comp.comp2021.jungle.model;
import hk.edu.polyu.comp.comp2021.jungle.view.View;

public class JungleGame {
    private GameBoard board;
    public JungleGame(){
        this.board = new GameBoard();
    }

    /*
    updateView() is called each time some change is made final.
    -> It tells the view what to display next.
     */
    public void updateView(View view){
        view.displayGameUpdate(this.board);
    }

    /*
    newInput() is called each time the Controller registers a new user input.
    -> checks the semantic correctness of the input (on the Board) and
    applies the changes.
     */
    public void newInput(String animal, int x_value, int y_value){
        //TODO check ipus syntactically and implement the logic behind it!
    }

}
