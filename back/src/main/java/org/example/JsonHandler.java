package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class JsonHandler {

    public static Forest readParameter(String filename) {
        JSONParser parser = new JSONParser();
        Forest forest = null;
        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONObject jsonObject = (JSONObject) obj;

            int height = Math.toIntExact((long) jsonObject.get("height"));
            int width = Math.toIntExact((long) jsonObject.get("width"));
            double proba = (double) jsonObject.get("proba");

            ArrayList<int[]> fireCoord = new ArrayList<>();
            JSONArray fireAtStart = (JSONArray) jsonObject.get("fire");
            for (JSONArray coordJson : (Iterable<JSONArray>) fireAtStart) {
                int x = Math.toIntExact((long) coordJson.get(0));
                int y = Math.toIntExact((long) coordJson.get(1));
                int[] coord = {x,y};
                fireCoord.add(coord);
            }

            forest = new Forest(height, width, proba, fireCoord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forest;
    }

    public static void writeOutput(Forest forest, String filename) {
        int height = forest.getHeight();
        int width = forest.getWidth();
        int t_max = forest.get_time_simulation();

        JSONObject obj = new JSONObject();
        obj.put("height", height);
        obj.put("width", width);
        obj.put("t_max", t_max);

        Vector<Cell[][]> forestOverTime = forest.getForestOverTime();
        JSONArray forestOverTimeJson = new JSONArray();
        for (int t = 0; t < t_max; t++) {
            JSONArray forestAtTimeT = new JSONArray();
            for (int x = 0; x < height; x++) {
                JSONArray forestRow = new JSONArray();
                for (int y = 0; y < width; y++) {
                    String cell = "";
                    switch (forestOverTime.get(t)[x][y].getType()) {
                        case ASH -> cell = "ash";
                        case FIRE -> cell = "fire";
                        case FOREST -> cell = "forest";
                    }
                    forestRow.add(cell);
                }
                forestAtTimeT.add(forestRow);
            }
            forestOverTimeJson.add(forestAtTimeT);
        }

        obj.put("forest", forestOverTimeJson);

        try (FileWriter file = new FileWriter("output.json")) {
            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
