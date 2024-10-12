package com.assignment.tictactoe.service;

public interface BoardUI{
    void initializeBoard();
    void printBoard();
    Boolean isLegalMove(int row, int col);
    void updateMove(int row, int col, Piece piece);
    Winner checkWinner();
    void update(int col, int row, boolean isHuman);
    void notifyWinner();
}
