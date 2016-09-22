package com.example.brian.othello.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

import com.example.brian.othello.viewmodels.Spot;

/**
 * Created by Brian on 16/6/3.
 */
public class Square extends Button {

    private Spot spot;
    private boolean clicked = false;

    // Constructor
    public Square(Context context, int row, int col) {
        super(context);

        this.spot = new Spot(row, col);
    }


}
