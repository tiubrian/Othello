package com.example.brian.othello.viewmodels;

import com.example.brian.othello.ai.ComputerAI;
import com.example.brian.othello.views.SquareAdapter;

import java.util.ArrayList;

/**
 * Created by Brian on 16/6/3.
 */
public class BoardViewModel {

    private char[][] board;
    private char turn = 'b';
    private ArrayList<Spot> possible = new ArrayList<>();
    //private ComputerAI computerPlayer = new ComputerAI('w');
    private ComputerAI computerPlayer = new ComputerAI('b');
    private char ai = 'b';
    SquareAdapter sa;

    // Constructor
    public BoardViewModel() {
        board = new char[8][8];
        initializeBoard();
        possible = getPossibleSpots();
        for(Spot s : possible) {
            int row = s.getRow();
            int col = s.getCol();
            board[row][col] = '?';
        }
        Spot s = computerPlayer.getBestMove(board);
        setSquare(s.getRow(), s.getCol());

    }



    public void update(int row, int col, char color) {
        board[row][col] = color;
    }

    public void setSquare(int row, int col) {
        board[row][col] = turn;
        //guiBoard[row][col].setImage(turn);
        //bv.refresh();

        kill(row,col);
        switchTurn();
        System.out.println();
        //System.out.println(toString());
        System.out.println("value = " + computerPlayer.evaluate(board));
    }

    public void switchTurn() {
        /*
        for(Spot s : possible) {
            int row = s.getRow();
            int col = s.getCol();
            bv.highlight(false, row, col);
        }
*/
        // unhighlight
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == '?') {
                    board[i][j] = '-';
                }
            }
        }

        if(turn == 'b') {
            turn = 'w';
        } else {
            turn = 'b';
        }

        possible = getPossibleSpots();

        if(possible.size() == 0) {
            if(turn == 'b') {
                turn = 'w';
            } else {
                turn = 'b';
            }
            possible = getPossibleSpots();
        }

        if(possible.size() == 0) {
            // end the game
            return;
        }
        /*
        for(Spot s : possible) {
            int row = s.getRow();
            int col = s.getCol();
            //bv.highlight(true, row, col);
        }*/
        for(Spot s : possible) {
            int row = s.getRow();
            int col = s.getCol();
            board[row][col] = '?';
        }

        sa.notifyDataSetChanged();

        if(turn == ai) {
            Spot move = computerPlayer.getBestMove(board);
            setSquare(move.getRow(), move.getCol());
            //System.out.println("Score = " + computerPlayer.evaluate(board));
        }


    }

    public ArrayList<Spot> getPossibleSpots() {
        ArrayList<Spot> list =  new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == '-' && canKill(i, j)) {
                    list.add(new Spot(i, j));
                }
            }
        }
        return list;
    }


    // Set up the board
    private void initializeBoard() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = '-';
            }
        }
        board[3][3] = 'w';
        board[4][4] = 'w';
        board[4][3] = 'b';
        board[3][4] = 'b';
    }

    private void kill(int row, int col) {

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == '?') {
                    board[i][j] = '-';
                }
            }
        }


        int tempRow;
        int tempCol;
        boolean keepGoing;
        boolean success;
        ArrayList<Spot> toKill = new ArrayList<>();

        // up
        tempRow = row;
        keepGoing = true;
        tempRow--;
        success = false;
        while(tempRow >= 0 && keepGoing) {
            if(board[tempRow][col] == '-' ) {
                keepGoing = false;
            } else if(board[tempRow][col] != turn) {
                toKill.add(new Spot(tempRow, col));
                keepGoing = true;
            } else {
                keepGoing = false;
                success = true;
            }
            tempRow--;
        }
        if(success) {
            killSpots(toKill);
        }
        toKill.clear();

        // down
        tempRow = row;
        keepGoing = true;
        tempRow++;
        success = false;
        while(tempRow < 8 && keepGoing) {
            if(board[tempRow][col] == '-') {
                keepGoing = false;
            } else if(board[tempRow][col] != turn) {
                toKill.add(new Spot(tempRow, col));
                keepGoing = true;
            } else {
                keepGoing = false;
                success = true;
            }
            tempRow++;
        }
        if(success) {
            killSpots(toKill);
        }
        toKill.clear();

        // left
        tempCol = col;
        keepGoing = true;
        tempCol--;
        success = false;
        while(tempCol >= 0 && keepGoing) {
            if(board[row][tempCol] == '-') {
                keepGoing = false;
            } else if(board[row][tempCol] != turn) {
                toKill.add(new Spot(row, tempCol));
                keepGoing = true;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol--;
        }
        if(success) {
            killSpots(toKill);
        }
        toKill.clear();

        // right
        tempCol = col;
        keepGoing = true;
        tempCol++;
        success = false;
        while(tempCol < 8 && keepGoing) {
            if(board[row][tempCol] == '-') {
                keepGoing = false;
            } else if(board[row][tempCol] != turn) {
                toKill.add(new Spot(row, tempCol));
                keepGoing = true;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol++;
        }
        if(success) {
            killSpots(toKill);
        }
        toKill.clear();

        // upleft
        tempCol = col;
        tempRow = row;
        keepGoing = true;
        tempCol--;
        tempRow--;
        success = false;
        while(tempCol >= 0 && tempRow >= 0 && keepGoing) {
            if(board[tempRow][tempCol] == '-') {
                keepGoing = false;
            } else if(board[tempRow][tempCol] != turn) {
                toKill.add(new Spot(tempRow, tempCol));
                keepGoing = true;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol--;
            tempRow--;
        }
        if(success) {
            killSpots(toKill);
        }
        toKill.clear();

        // up right
        tempCol = col;
        tempRow = row;
        keepGoing = true;
        tempCol++;
        tempRow--;
        success = false;
        while(tempCol < 8 && tempRow >= 0 && keepGoing) {
            if(board[tempRow][tempCol] == '-') {
                keepGoing = false;
            } else if(board[tempRow][tempCol] != turn) {
                toKill.add(new Spot(tempRow, tempCol));
                keepGoing = true;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol++;
            tempRow--;
        }
        if(success) {
            killSpots(toKill);
        }
        toKill.clear();

        // down left
        tempCol = col;
        tempRow = row;
        keepGoing = true;
        tempCol--;
        tempRow++;
        success = false;
        while(tempCol >= 0 && tempRow < 8 && keepGoing) {
            if(board[tempRow][tempCol] == '-') {
                keepGoing = false;
            } else if(board[tempRow][tempCol] != turn) {
                toKill.add(new Spot(tempRow, tempCol));
                keepGoing = true;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol--;
            tempRow++;
        }
        if(success) {
            killSpots(toKill);
        }
        toKill.clear();

        // down right
        tempCol = col;
        tempRow = row;
        keepGoing = true;
        tempCol++;
        tempRow++;
        success = false;
        while(tempCol < 8 && tempRow < 8 && keepGoing) {
            if(board[tempRow][tempCol] == '-') {
                keepGoing = false;
            } else if(board[tempRow][tempCol] != turn) {
                toKill.add(new Spot(tempRow, tempCol));
                keepGoing = true;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol++;
            tempRow++;
        }
        if(success) {
            killSpots(toKill);
        }
        toKill.clear();



    }

    private void killSpots(ArrayList<Spot> toKill) {
        for(Spot s : toKill) {
            board[s.getRow()][s.getCol()] = turn;
        }
    }

    public boolean canKill(int row, int col) {
        int tempRow;
        int tempCol;
        boolean keepGoing;
        boolean success;
        int count;

        // up
        count = 0;
        tempRow = row;
        keepGoing = true;
        tempRow--;
        success = false;
        while(tempRow >= 0 && keepGoing) {
            if(board[tempRow][col] == '-') {
                keepGoing = false;
            } else if(board[tempRow][col] != turn) {
                keepGoing = true;
                count++;
            } else {
                keepGoing = false;
                success = true;
            }
            tempRow--;
        }
        if(success && count > 0) {
            return true;
        }

        // down
        count = 0;
        tempRow = row;
        keepGoing = true;
        tempRow++;
        success = false;
        while(tempRow < 8 && keepGoing) {
            if(board[tempRow][col] == '-') {
                keepGoing = false;
            } else if(board[tempRow][col] != turn) {
                count++;
                keepGoing = true;
            } else {
                keepGoing = false;
                success = true;
            }
            tempRow++;
        }
        if(success && count > 0) {
            return true;
        }

        // left
        count = 0;
        tempCol = col;
        keepGoing = true;
        tempCol--;
        success = false;
        while(tempCol >= 0 && keepGoing) {
            if(board[row][tempCol] == '-') {
                keepGoing = false;
            } else if(board[row][tempCol] != turn) {
                keepGoing = true;
                count++;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol--;
        }
        if(success && count > 0) {
            return true;
        }

        // right
        count = 0;
        tempCol = col;
        keepGoing = true;
        tempCol++;
        success = false;
        while(tempCol < 8 && keepGoing) {
            if(board[row][tempCol] == '-') {
                keepGoing = false;
            } else if(board[row][tempCol] != turn) {
                keepGoing = true;
                count++;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol++;
        }
        if(success && count > 0) {
            return true;
        }

        // upleft
        count = 0;
        tempCol = col;
        tempRow = row;
        keepGoing = true;
        tempCol--;
        tempRow--;
        success = false;
        while(tempCol >= 0 && tempRow >= 0 && keepGoing) {
            if(board[tempRow][tempCol] == '-') {
                keepGoing = false;
            } else if(board[tempRow][tempCol] != turn) {
                keepGoing = true;
                count++;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol--;
            tempRow--;
        }
        if(success && count > 0) {
            return true;
        }

        // up right
        count = 0;
        tempCol = col;
        tempRow = row;
        keepGoing = true;
        tempCol++;
        tempRow--;
        success = false;
        while(tempCol < 8 && tempRow >= 0 && keepGoing) {
            if(board[tempRow][tempCol] == '-') {
                keepGoing = false;
            } else if(board[tempRow][tempCol] != turn) {
                keepGoing = true;
                count++;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol++;
            tempRow--;
        }
        if(success && count > 0) {
            return true;
        }

        // downleft
        count = 0;
        tempCol = col;
        tempRow = row;
        keepGoing = true;
        tempCol--;
        tempRow++;
        success = false;
        while(tempCol >= 0 && tempRow < 8 && keepGoing) {
            if(board[tempRow][tempCol] == '-') {
                keepGoing = false;
            } else if(board[tempRow][tempCol] != turn) {
                keepGoing = true;
                count++;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol--;
            tempRow++;
        }
        if(success && count > 0) {
            return true;
        }

        // down right
        count = 0;
        tempCol = col;
        tempRow = row;
        keepGoing = true;
        tempCol++;
        tempRow++;
        success = false;
        while(tempCol < 8 && tempRow < 8 && keepGoing) {
            if(board[tempRow][tempCol] == '-') {
                keepGoing = false;
            } else if(board[tempRow][tempCol] != turn) {
                keepGoing = true;
                count++;
            } else {
                keepGoing = false;
                success = true;
            }
            tempCol++;
            tempRow++;
        }
        if(success && count > 0) {
            return true;
        }

        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                sb.append(board[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public char getSquare(int row, int col) {
        return board[row][col];
    }

    // Getters and setters

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public char getTurn() {
        return turn;
    }

    public void setTurn(char turn) {
        this.turn = turn;
    }

    // Action listeners
    public void buttonPressed(int row, int col) {
        System.out.println("row = " + row);
        System.out.println("col = " + col);
        this.setSquare(row, col);
    }

    public void setSA(SquareAdapter sa) {
        this.sa = sa;
    }
}
