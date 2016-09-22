package com.example.brian.othello;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.brian.othello.views.BoardView;
import com.example.brian.othello.views.SquareAdapter;
import com.example.brian.othello.views.listeners.SquareListener;


public class MainActivity extends AppCompatActivity {

    GridView gv;
    BoardView boardView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_view);


        boardView = new BoardView();
        gv = (GridView)findViewById(R.id.gridview);

        SquareAdapter sa = new SquareAdapter(this, boardView.getModel().getBoard(), boardView, gv);
        boardView.getModel().setSA(sa);
        gv.setAdapter(sa);
        //gv.setBackgroundColor(0xFF444444);
        //gv.setOnItemClickListener(new SquareListener(boardView, gv, sa));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
