package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Animal;
import org.json.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.apache.commons.io.FileUtils;

//TODO: should me in model!
public class SaveAndLoad {

    private boolean loadGame (String path) {
        try {
            File file = new File(path);
            String content = FileUtils.readFileToString(file);
            JSONObject game = new JSONObject(content);
            //TODO: set up the loaded things as a Game Board!
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean saveGame (String path, GameBoard gameBoard, String frontName, String backName, int turnCount){
        JSONObject gameJason = gameBoardToJson(gameBoard, frontName,backName, turnCount);
        try {
            // paths attribute needs form: "Z:\\Programming\\java_test\\test.txt":
            FileWriter file = new FileWriter(path);
            file.write(gameJason.toString());
            file.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    gameBoardToJson() returns true if saving was successful!
     */
    private JSONObject gameBoardToJson(GameBoard gameBoard, String frontName, String backName, int turnCount){
        JSONObject playerFront = playerToJason(true, gameBoard.getPlayerFront(), frontName);
        JSONObject playerBack =  playerToJason(true, gameBoard.getPlayerBack(), backName);
        JSONObject game = new JSONObject();
        game.put("turnCount", turnCount);
        game.put("playerFront", playerFront);
        game.put("playerBack", playerBack);
        return game;
    }

    private JSONObject playerToJason(boolean frontPlayer, Animal [] animals, String playerName){
        JSONObject player = new JSONObject();
        player.put("name", playerName);
        player.put("isFrontPlayer", frontPlayer);
        JSONObject pieces = new JSONObject();
        for (Animal currentAnimal : animals){
            if(currentAnimal == null){
                pieces.put(currentAnimal.getStrength() + "", animalToJson(currentAnimal, false));
            }else{
                pieces.put(currentAnimal.getStrength() + "", animalToJson(currentAnimal, true));
            }
        }
        player.put("pieces", pieces);
        return player;
    }

    private JSONObject animalToJson(Animal animal, boolean alive){
        JSONObject animalJson = new JSONObject();
        if(!alive){
            animalJson.put("alive", false);
            return animalJson;
        }
        animalJson.put("ownedByFrontPlayer", animal.getFrontPlayer());
        animalJson.put("x_coordinate", animal.getX_location());
        animalJson.put("y_coordinate", animal.getY_location());
        animalJson.put("strength", animal.getStrength());
        animalJson.put("alive", alive);
        return animalJson;
    }

    private Animal[] jsonToPlayer(boolean frontPlayer){
        return  null;
    }

    public static void main(String[] args) {
        SaveAndLoad a = new SaveAndLoad();
        a.saveGame("Z:\\Programming\\java_test\\test.json", new GameBoard(), "front", "back", 10);
    }
}
