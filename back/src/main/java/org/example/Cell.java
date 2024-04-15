package org.example;


public class Cell {
    private CellType type;

    public Cell(CellType type) {
        this.type = type;
    }

    public boolean propagate(double proba) {
        if (type == CellType.FOREST){
            if (Math.random()<proba) {
                this.type = CellType.FIRE;
                return true;
            }
        }
        return false;
    }

    public void to_ashes() {
        this.type = CellType.ASH;
    }

    public CellType getType() {
        return type;
    }
}
