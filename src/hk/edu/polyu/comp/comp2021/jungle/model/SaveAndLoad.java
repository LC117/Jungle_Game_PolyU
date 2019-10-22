package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Animal;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Lion;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Rat;
import hk.edu.polyu.comp.comp2021.jungle.model.pieces.Tiger;
import org.json.*;
import java.io.File;
import java.io.FileWriter;
import org.apache.commons.io.FileUtils;

public class SaveAndLoad {

    /*
    loadGame() returns the loaded game board (initialized with all animals) if successful, else null.
     */
    public GameBoard loadGame (String path) {
        try {
            //Read the root element from the saved JSON file:
            File file = new File(path);
            String content = FileUtils.readFileToString(file);
            JSONObject game = new JSONObject(content);

            //create new GameBoard:
            GameBoard gameBoard = new GameBoard();

            //read all inserted data front the Jason File:
            JSONObject playerFront = (JSONObject) game.get("playerFront");
            JSONObject playerBack = (JSONObject) game.get("playerBack");
            JSONObject playerFrontPieces = playerFront.getJSONObject("pieces");
            JSONObject playerBackPieces = playerBack.getJSONObject("pieces");

            //parse the Animals:
            Animal [] frontPlayerAnimals = new Animal [8];
            Animal [] backPlayerAnimals = new Animal [8];
            JSONObject currentFrontPlayerAnimal;
            JSONObject currentBackPlayerAnimal;

            for (int i = 0; i < 8; i++) {
                currentFrontPlayerAnimal = (JSONObject) playerFrontPieces.get((i+1) + "");
                frontPlayerAnimals[i] = parseAnimal(currentFrontPlayerAnimal, gameBoard);
                currentBackPlayerAnimal = (JSONObject) playerBackPieces.get((i+1) + "");
                backPlayerAnimals[i] = parseAnimal(currentBackPlayerAnimal, gameBoard);
            }

            gameBoard.resetGameBoard(frontPlayerAnimals, backPlayerAnimals);
            return gameBoard;
        }catch (Exception e){
            return null;
        }
    }

    private Animal parseAnimal(JSONObject animal, GameBoard gameBoard){
        boolean alive = (boolean) animal.get("alive");
        if(alive) {
            boolean ownedByFrontPlayer = (boolean) animal.get("ownedByFrontPlayer");
            int x = (int) animal.get("x_coordinate");
            int y = (int) animal.get("y_coordinate");
            int strength = (int) animal.get("strength");
            return createAnimal(x, y, ownedByFrontPlayer, strength, gameBoard);
        }else{
            return null;
        }
    }

    /*
    createAnimal() is necessary as the Rat, Tiger and Lion have special abilities!
     */
    private Animal createAnimal(int x, int y, boolean ownedByFrontPlayer, int strength, GameBoard gameBoard){
        switch (strength){
            case(1):
                return new Rat(x, y, ownedByFrontPlayer, gameBoard);
            case(6):
                return new Tiger(x, y, ownedByFrontPlayer, gameBoard);
            case(7):
                return new Lion(x, y, ownedByFrontPlayer, gameBoard);
                default:
                    return new Animal(x, y, ownedByFrontPlayer, strength, gameBoard);
        }
    }

    /*
    saveGame() returns true if successful else false.
    Path example: Z:\\Jungle_Game_PolyU\\SaveGames\\save.json
     */
    public boolean saveGame (String path, String frontName, String backName, int turnCount, GameBoard gameBoard){
        JSONObject gameJason = gameBoardToJson(gameBoard, frontName, backName, turnCount);
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

    private JSONObject gameBoardToJson(GameBoard gameBoard, String frontName, String backName, int turnCount){
        JSONObject playerFront = playerToJason(gameBoard.getPlayerFrontAnimals(), frontName);
        JSONObject playerBack =  playerToJason(gameBoard.getPlayerBackAnimals(), backName);
        JSONObject game = new JSONObject();
        game.put("turnCount", turnCount);
        game.put("playerFront", playerFront);
        game.put("playerBack", playerBack);
        return game;
    }

    private JSONObject playerToJason(Animal [] animals, String playerName){
        JSONObject player = new JSONObject();
        player.put("name", playerName);
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
}
