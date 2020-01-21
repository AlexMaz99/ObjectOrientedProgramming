package agh.cs.data;
import agh.cs.structures.Vector2d;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {
    private int width;
    private int height;
    private String pattern;
    private String survivalRules;
    private String birthRules;

    public JSONReader() {
        JSONParser jsonParser = new JSONParser();
        String dir = System.getProperty("user.dir");

        try (FileReader reader = new FileReader(dir + "/src/startParameters/parameters.json")) {
            Object object = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;
            this.width = ((Long) jsonObject.get("width")).intValue();
            this.height = ((Long) jsonObject.get("height")).intValue();
            this.pattern = (String)jsonObject.get("pattern");
            this.survivalRules = (String)jsonObject.get("survivalRules");
            this.birthRules = (String)jsonObject.get("birthRules");
        } catch (IOException | ParseException fe) {
            fe.printStackTrace();
        } catch (IllegalArgumentException iex) {
            System.out.println(iex.toString());
        }
        if (this.width <= 0 || this.height <= 0)
            throw new IllegalArgumentException("Size of board must be positive.");

    }
    public int getWidth(){
        return this.width;
    }

    public int getHeight() {
        return height;
    }
    public String getPattern(){
        return this.pattern;
    }
    public List<Integer> getSurvivalRules(){
        List<Integer> survival = new ArrayList<>();

        for (int i =0; i<this.survivalRules.length(); i++) {
            int x = Character.getNumericValue(this.survivalRules.charAt(i));
            survival.add(x);
        }
        return survival;
    }
    public List<Integer> getBirthRules(){
        List<Integer> birth = new ArrayList<>();

        for (int i =0; i<this.birthRules.length(); i++) {
            int x = Character.getNumericValue(this.birthRules.charAt(i));
            birth.add(x);
        }
        return birth;
    }
}
