package org.example;

import java.util.ArrayList;
import java.util.Vector;

public class Forest {
    private int height;
    private int width;
    private double proba;
    private ArrayList<int[]> fireAtStart;
    private Vector<Cell[][]> forestOverTime;
    private boolean simulated;

    public Forest(int height, int width, double proba, ArrayList<int[]> fireAtStart) {
        this.height = height;
        this.width = width;
        this.proba = proba;
        this.fireAtStart = fireAtStart;

        this.forestOverTime = new Vector<>();
        // to create the state at start first the grid is filled by forest cells
        Cell[][] forestAtStart = new Cell[this.height][this.width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                forestAtStart[i][j] = new Cell(CellType.FOREST);
            }
        }
        // then fireAtStart coordinates are modified to fire cells
        for (int[] fireCell : fireAtStart) {
            int x = fireCell[0];
            int y = fireCell[1];
            forestAtStart[x][y] = new Cell(CellType.FIRE);
        }
        this.forestOverTime.add(forestAtStart);

        this.simulated = false;
    }

    // try the propagation of fire into a cell and add it to the list of fire cells
    // for next step if propagation succeeds
    private void propagate(int x, int y, ArrayList<int[]> fireAtT) {
        boolean turnToFire;
        turnToFire = this.forestOverTime.lastElement()[x][y].propagate(this.proba);
        if(turnToFire){
            fireAtT.add(new int[]{x, y});
        }
    }

    // create a copy of the last state of the forest, the copy will then be modified
    // to realize the next step of simulation
    private Cell[][] copyLastForest(){
        Cell[][] lastForest = this.forestOverTime.lastElement();
        Cell[][] copy = new Cell[this.height][this.width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                copy[i][j] = new Cell(lastForest[i][j].getType());
            }
        }

        return copy;
    }

    private ArrayList<int[]> next_step(ArrayList<int[]> fireTMinus1) {
        this.forestOverTime.add(this.copyLastForest());
        ArrayList<int[]> fireAtT = new ArrayList<>();

        for (int[] fireCell : fireTMinus1) {
            int x = fireCell[0];
            int y = fireCell[1];

            if (x-1 >= 0) {
                propagate(x-1, y, fireAtT);
            }
            if (x+1 < this.height) {
                propagate(x+1, y, fireAtT);
            }
            if (y-1 >= 0) {
                propagate(x, y-1, fireAtT);
            }
            if (y+1 < this.width) {
                propagate(x, y+1, fireAtT);
            }

            this.forestOverTime.lastElement()[x][y].to_ashes();
        }
        return fireAtT;
    }

    public void simulate() {
        if (!simulated) {
            ArrayList<int[]> firecells = new ArrayList<>(fireAtStart);
            while (!firecells.isEmpty()) {
                firecells = this.next_step(firecells);
            }
            this.simulated = true;
        }
    }

    public int get_time_simulation() {
        return forestOverTime.size();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Vector<Cell[][]> getForestOverTime() {
        return forestOverTime;
    }


}
