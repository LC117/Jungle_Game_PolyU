package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.model.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {
    private static ByteArrayOutputStream outPutContent;

    @BeforeEach
    public void setUpTestStreams(){
        outPutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outPutContent));
    }


    @Test
    void displayMessage() {
        String testMessage = "This is a test message! \n";
        View view = new View();
        view.displayMessage(testMessage);
        assertEquals(testMessage + System.getProperty("line.separator"), outPutContent.toString(),
                "Tests if view.displayMessage() prints a test message correctly.");
    }

    @Test
    void displayGameUpdate() {
        GameBoard testGameBoard = new GameBoard();
        View view = new View();
        view.displayGameUpdate(testGameBoard);
        assertEquals(testGameBoard.toString() + System.getProperty("line.separator"), outPutContent.toString(),
                "Tests if displayGameUpdate() correctly prints a GameBoard object.");
    }
}