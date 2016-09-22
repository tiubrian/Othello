package com.example.brian.othello.viewmodels;

/**
 * Created by Brian on 16/6/3.
 */
public class Spot {

    private int row;
    private int col;

    public Spot(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
