package org.example;

public class Main {
    public static void main(String[] args) {
        Forest forest = JsonHandler.readParameter("./input.json");
        forest.simulate();
        JsonHandler.writeOutput(forest, "output.json");
    }
}