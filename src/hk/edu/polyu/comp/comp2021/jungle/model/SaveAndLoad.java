package hk.edu.polyu.comp.comp2021.jungle.model;

import org.json.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//TODO: should me in model!
public class SaveAndLoad {

    private void loadGame (String path){

    }
    private void saveGame (String path){
        File file = new File(path);

        JSONObject jo = new JSONObject();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            List<String> lineList = Files.readAllLines(Paths.get("Z:\\Programming\\java_test\\test.txt")); //Works with Path!!
            System.out.println(Arrays.toString(lineList.toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
