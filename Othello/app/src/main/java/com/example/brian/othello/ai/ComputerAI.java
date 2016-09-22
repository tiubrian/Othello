package com.example.brian.othello.ai;

import com.example.brian.othello.viewmodels.Spot;

import java.util.ArrayList;

/**
 * Created by Brian on 16/6/8.
 */
public class ComputerAI {

    char color;
    private final int DEPTH =4;

    // Constructor
    public ComputerAI(char color) {
        this.color = color;
    }

    public Spot getBestMove(char[][] board) {
        /*
        Spot bestSpot = null;
        int bestValue = Integer.MIN_VALUE;
        ArrayList<Spot> possible = getPossibleSpots(board, color);

        if(possible.size() == 0) {
            return null;
        }

        for(Spot s : possible) {
            // Setup
            int row = s.getRow();
            int col = s.getCol();
            char[][] boardCopy = boardCopy(board);

            // Make move
            boardCopy[row][col] = color;

            // Evaluate
            int tempValue = minMax(board, DEPTH, false, color);
            if(tempValue > bestValue) {
                bestValue = tempValue;
                bestSpot = s;
            }
        }



        if(bestSpot == null) {
            System.out.println("Should never come here");
        }
        return bestSpot;
        */

        Spot bestSpot = null;
        double alpha = Integer.MIN_VALUE;
        double beta = Integer.MAX_VALUE;
        double bestValue = Integer.MIN_VALUE;

        ArrayList<Spot> possible = getPossibleSpots(board, color);

        if(possible.size() == 0) {
            return null;
        }

        char next;
        if(color == 'w') {
            next = 'b';
        } else {
            next = 'w';
        }

        for(Spot s : possible) {
            // Setup
            int row = s.getRow();
            int col = s.getCol();
            char[][] boardCopy = boardCopy(board);

            // Make move
            boardCopy[row][col] = color;
            kill(row, col, boardCopy, color);



            // Evaluate
            double tempValue = alphaBeta(next, boardCopy, alpha, beta, DEPTH);
            if(tempValue > bestValue) {
                bestValue = tempValue;
                bestSpot = s;
            }
        }

        if(bestSpot == null) {
            System.out.println("Should never come here");
        }
        return bestSpot;

    }

    /*
    alpha-beta(player,board,alpha,beta)
    if(game over in current board position)
        return winner

    children = all legal moves for player from this board
    if(max's turn)
        for each child
            score = alpha-beta(other player,child,alpha,beta)
            if score > alpha then alpha = score (we have found a better best move)
            if alpha >= beta then return alpha (cut off)
        return alpha (this is our best move)
    else (min's turn)
        for each child
            score = alpha-beta(other player,child,alpha,beta)
            if score < beta then beta = score (opponent has found a better worse move)
            if alpha >= beta then return beta (cut off)
        return beta (this is the opponent's best move)
     */

    private double alphaBeta(char turn, char[][] board, double alpha, double beta, int depth) {
        depth--;
        if(depth == 0) {
            return evaluate(board);
        }
        ArrayList<Spot> possibleMoves = getPossibleSpots(board, turn);
        if(possibleMoves.size() == 0) {
            return evaluate(board);
        }
        if(turn == color) {
            for(Spot s : possibleMoves) {
                char[][] copy = boardCopy(board);
                int row = s.getRow();
                int col = s.getCol();
                copy[row][col] = turn;
                kill(row, col, copy, turn);
                char otherPlayer;
                if(turn == 'w') {
                    otherPlayer = 'b';
                } else {
                    otherPlayer = 'w';
                }
                double score = alphaBeta(otherPlayer, copy, alpha, beta, depth);
                if(score > alpha) {
                    alpha = score;
                }
                if(alpha >= beta) {
                    return alpha;
                }
            }
            return alpha;
        } else {
            for(Spot s : possibleMoves) {
                char[][] copy = boardCopy(board);
                int row = s.getRow();
                int col = s.getCol();
                copy[row][col] = turn;
                kill(row, col, copy, turn);
                char otherPlayer;
                if(turn == 'w') {
                    otherPlayer = 'b';
                } else {
                    otherPlayer = 'w';
                }
                double score = alphaBeta(otherPlayer, copy, alpha, beta, depth);
                if(score < beta) {
                    beta = score;
                }
                if(alpha >= beta) {
                    return beta;
                }
            }
            return beta;
        }
    }


    private double minMax(char[][] board, int depth, boolean maximizingPlayer, char turn) {
        if(turn == 'w') {
            turn = 'b';
        } else {
            turn = 'w';
        }

        double bestValue;
        depth--;
        if(depth == 0) {
            bestValue = evaluate(board);
        } else if(maximizingPlayer) {
            bestValue = Integer.MIN_VALUE;
            ArrayList<Spot> possibleMoves = getPossibleSpots(board, turn);
            if(possibleMoves.size() == 0) {
                return evaluate(board);
            }
            for(Spot s : possibleMoves) {
                char[][] boardCopy = boardCopy(board);
                boardCopy[s.getRow()][s.getCol()] = turn;
                kill(s.getRow(), s.getCol(), boardCopy, turn);
                double childValue = minMax(boardCopy, depth, false, turn);
                bestValue = Math.max(bestValue, childValue);
            }
        } else {
            bestValue = Integer.MAX_VALUE;
            ArrayList<Spot> possibleMoves = getPossibleSpots(board, turn);
            if(possibleMoves.size() == 0) {
                return evaluate(board);
            }
            for(Spot s : possibleMoves) {
                char[][] boardCopy = boardCopy(board);
                boardCopy[s.getRow()][s.getCol()] = turn;
                kill(s.getRow(), s.getCol(), boardCopy, turn);

                double childValue = minMax(boardCopy, depth, true, turn);
                bestValue = Math.min(bestValue, childValue);
            }
        }

        //System.out.println(bestValue);
        return bestValue;
    }


    public ArrayList<Spot> getPossibleSpots(char[][] board, char turn) {
        ArrayList<Spot> list =  new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if((board[i][j] == '?' || board[i][j] == '-') && canKill(i, j, board, turn)) {
                    list.add(new Spot(i, j));
                }
            }
        }
        return list;
    }
    public boolean canKill(int row, int col, char[][] board, char turn) {
        int tempRow;
        int tempCol;
        boolean keepGoing;
        boolean success;
        int count;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == '?') {
                    board[i][j] = '-';
                }
            }
        }

        // up
        count = 0;
        tempRow = row;
        keepGoing = true;
        tempRow--;
        success = false;
        while(tempRow >= 0 && keepGoing) {
            if(board[tempRow][col] == '-' ) {
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

    private char[][] boardCopy(char[][] board) {
        char[][] copy = new char[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    private void kill(int row, int col, char[][] board, char turn) {

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
            killSpots(toKill, board, turn);
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
            killSpots(toKill, board, turn);
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
            killSpots(toKill, board, turn);
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
            killSpots(toKill, board, turn);
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
            killSpots(toKill, board, turn);
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
            killSpots(toKill, board, turn);
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
            killSpots(toKill, board, turn);
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
            killSpots(toKill, board, turn);
        }
        toKill.clear();



    }

    private void killSpots(ArrayList<Spot> toKill, char[][] board, char turn) {
        for(Spot s : toKill) {
            board[s.getRow()][s.getCol()] = turn;
        }
    }

    public double evaluate(char[][] board) {
        int turnsLeft = getNumTurnsLeft(board);
        double proportion;//turnsLeft/64;

        if(turnsLeft < 4) {
            proportion = 0;//turnsLeft/64;
        } else {
            proportion = 1;
        }
        double total = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != '-' &&  board[i][j] != '?') {
                    if(board[i][j] == color) {
                        if(isCorner(i, j)) {
                            total += (75*proportion);
                        } else if(isEdgeGood(i, j)){
                            total += (8*proportion);
                        } else if(isBadCorner(i, j)) {
                            total -= (8*proportion);
                        } else if(badInnerEdge(i, j)) {
                            total -= (1*proportion);
                        }
                        total++;
                    } else {
                        if(isCorner(i, j)) {
                            total -= (75*proportion);
                        } else if(isEdgeGood(i, j)){
                            total -= (8*proportion);
                        } else if(isBadCorner(i, j)) {
                            total += (8*proportion);
                        } else if(badInnerEdge(i, j)) {
                            total += (1*proportion);
                        }
                        total--;
                    }
                }
            }
        }
        return total;
    }

    private int getNumTurnsLeft(char[][] board) {
        int numTurns = 64;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == 'w' || board[i][j] == 'b') {
                    numTurns--;
                }
            }
        }

        return numTurns;
    }

    private boolean badInnerEdge(int row, int col) {
        if(row == 1 && (col > 2 && col < 5) ||
                row == 6 && (col > 2 && col < 5) ||
                col == 1 && (row > 2 && row < 5) ||
                col == 6 && (row > 2 && row < 5)) {
            return true;
        }
        return false;
    }

    private boolean isCorner(int row, int col) {
        if(row == 0 && col == 0 ||
                row == 0 & col == 7 ||
                row == 7 && col == 0 ||
                row == 7 && col == 7) {
            return true;
        }
        return false;
    }
    private boolean isBadCorner(int row, int col) {
        if(row == 0 && col == 1 ||
                row == 1 && col == 1 ||
                row == 1 && col == 0 ||

                row == 0 && col == 6 ||
                row == 1 && col == 6 ||
                row == 1 && col == 7 ||

                row == 6 && col == 0 ||
                row == 6 && col == 1 ||
                row == 7 && row == 1 ||

                row == 7 && col == 6 ||
                row == 6 && col == 6 ||
                row == 6 && col == 7) {
            return true;
        }
        return false;
    }


    private boolean isEdgeGood(int row, int col) {
        if(row == 0 && (col > 1 && col < 6) ||
                row == 7 && (col > 1 && col < 6) ||
                col == 0 && (row > 1 && row < 6) ||
                col == 7 && (row > 1 && row < 6)) {
            return true;
        }
        return false;
    }



}

