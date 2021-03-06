package agh.cs.map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;

public class JSONReader {
    private int mapWidth;
    private int mapHeight;
    private int plantEnergy;
    private int moveEnergy;
    private int startEnergy;
    private double jungleRatio;
    private int startNumberOfAnimals;


    public JSONReader() {
        JSONParser jsonParser = new JSONParser();
        String dir = System.getProperty("user.dir");

        try (FileReader reader = new FileReader (dir+"/src/startParameters/parameters.json")){
            Object object = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;
            this.mapWidth = ((Long) jsonObject.get("width")).intValue();
            this.mapHeight = ((Long) jsonObject.get("height")).intValue();
            this.plantEnergy = ((Long) jsonObject.get("plantEnergy")).intValue();
            this.moveEnergy = ((Long) jsonObject.get("moveEnergy")).intValue();
            this.startEnergy = ((Long) jsonObject.get("startEnergy")).intValue();
            this.startNumberOfAnimals = ((Long) jsonObject.get("startNumberOfAnimals")).intValue();
            this.jungleRatio = (Double) jsonObject.get("jungleRatio");
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (org.json.simple.parser.ParseException pe ) {
            pe.printStackTrace();
        } catch (IllegalArgumentException iex) {
            System.out.println(iex.toString());
        }

    }

    double getJungleRatio() {
        return jungleRatio;
    }

    int getMapHeight() {
        return mapHeight;
    }

    int getMapWidth() {
        return mapWidth;
    }

    int getMoveEnergy() {
        return moveEnergy;
    }

    int getPlantEnergy() {
        return plantEnergy;
    }

    int getStartEnergy() {
        return startEnergy;
    }

    int getStartNumberOfAnimals() {
        return startNumberOfAnimals;
    }
}


