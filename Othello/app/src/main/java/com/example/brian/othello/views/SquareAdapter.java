package com.example.brian.othello.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.brian.othello.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Brian on 16/6/4.
 */
public class SquareAdapter extends BaseAdapter {

    private Context context;
    char[][] board;
    private ArrayList<View> views = new ArrayList<>();
    private LayoutInflater inflater;
    private BoardView boardView;
    private GridView gv;



    // Constructor
    public SquareAdapter(Context context, char[][] board, BoardView boardView, GridView gv) {
        this.gv = gv;
        this.boardView = boardView;
        this.context = context;
        this.board = board;
        this.inflater = LayoutInflater.from(context.getApplicationContext());

    }

    @Override
    public int getCount() {
        return board.length * board.length;
    }

    @Override
    public Object getItem(int position) {
        return views.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View imageView = convertView;
        boolean good = false;
        final int row = position/8;
        final int col = position%8;
        if(imageView == null) {


            imageView = inflater.inflate(R.layout.square, null);
//            imageView.setBackgroundColor(Color.GREEN);
            /*
            ViewHolder viewHolder = new ViewHolder(context);

            viewHolder.square = (ImageView) imageView.findViewById(R.id.innersquare);
            */

            /*
            if(board[row][col] == 'b') {
                imageView.setBackgroundColor(Color.BLACK);
            } else if(board[row][col] == 'w'){
                imageView.setBackgroundColor(Color.WHITE);
            }*/


        }

        final ImageView oImageView = (ImageView)imageView.findViewById(R.id.innersquare);
        switch(board[row][col]) {
            case '-': oImageView.setImageResource(R.color.colorGreen); break;
            case 'w': oImageView.setImageResource(R.color.colorWhite); break;
            case 'b': oImageView.setImageResource(R.color.colorBlack); break;
            case '?': oImageView.setImageResource(R.color.possible); break;


        }
        imageView.setTag(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //oImageView.setImageResource(R.color.colorBlack);

                if(boardView.getModel().getSquare(row, col) == '?' &&
                        boardView.getModel().canKill(row, col)) {
                    boardView.getModel().buttonPressed(row, col);
                    System.out.println(boardView.getModel().toString());
                    repaint();
                }

            }
        });

        return imageView;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public ArrayList<View> getViews() {
        return views;
    }

    public void repaint() {
        this.notifyDataSetChanged();
       // this.notifyDataSetInvalidated();
      //  gv.invalidateViews();
        //gv.setAdapter(new SquareAdapter(context,board,boardView, gv));


    }

    static class ViewHolder extends ImageView {
        public ImageView square;
        public ViewHolder(Context context) {
            super(context);
        }
    }

}











