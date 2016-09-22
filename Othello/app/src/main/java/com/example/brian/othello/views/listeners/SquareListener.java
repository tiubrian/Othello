package com.example.brian.othello.views.listeners;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.brian.othello.R;
import com.example.brian.othello.viewmodels.BoardViewModel;
import com.example.brian.othello.views.BoardView;
import com.example.brian.othello.views.Square;
import com.example.brian.othello.views.SquareAdapter;

/**
 * Created by Brian on 16/6/7.
 */
public class SquareListener implements AdapterView.OnItemClickListener {
    private BoardView boardView;
    private GridView gv;
    private SquareAdapter sa;

    // Constructor
    public SquareListener(BoardView boardView, GridView gv, SquareAdapter sa) {
        this.boardView = boardView;
        this.gv = gv;
        this.sa = sa;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int row = position / 8;
        int col = position % 8;
        System.out.println();
        System.out.println("clicked!");
        System.out.println(sa.getViews().size()+"~~~~~~~~~~~~~~");


        if(boardView.getModel().getSquare(row, col) == '-' &&
                boardView.getModel().canKill(row, col)) {
            System.out.println("can click!");
            boardView.getModel().buttonPressed(row, col);

            ImageView v = (ImageView)sa.getViews().get(position);
            v.setImageResource(R.color.background);



            sa.notifyDataSetChanged();
            //gv.invalidateViews();


            //view.setBackgroundColor(Color.BLACK);
        }
    }

}
