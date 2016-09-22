package com.example.brian.othello.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;

import com.example.brian.othello.R;
import com.example.brian.othello.viewmodels.BoardViewModel;

/**
 * Created by Brian on 16/6/3.
 */
public class BoardView extends AppCompatActivity {

    private BoardViewModel model = new BoardViewModel();
    private GridView gv;

    // Constructor
    public BoardView() {
        //super.onCreate(savedInstanceState);

        //setContentView(R.layout.board_view);
/*
        gv = (GridView)findViewById(R.id.gridview);
        gv.setAdapter(new SquareAdapter(this, getModel().getBoard()));
*/
        //this.setContentView(gv);


    }



    public BoardViewModel getModel() {
        return model;
    }

    public void setModel(BoardViewModel model) {
        this.model = model;
    }

    public GridView getGv() {
        return gv;
    }

    public void setGv(GridView gv) {
        this.gv = gv;
    }
}
