package com.assignment.tictactoe.service;

public interface Board {
    BoardUI getBoardUI();
    void initializeBoard();
    boolean isLegalMove(int row,int col);
    void updateMove(int row,int col,Piece piece);
    Winner checkWinner();
    void printBoard();

    void update(int col, int row, boolean isHuman);

    void notifyWinner();
}
