package hk.edu.polyu.comp.comp2021.jungle.controller;

import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import hk.edu.polyu.comp.comp2021.jungle.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private static ByteArrayOutputStream outPutContent;
    private static ByteArrayInputStream inPutContent;

    @BeforeEach
    public void setUpTestStreams(){
        outPutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outPutContent));

        inPutContent = new ByteArrayInputStream("My string".getBytes());
        System.setIn(inPutContent);
    }

    @Test
    void playGame() {

    }
}