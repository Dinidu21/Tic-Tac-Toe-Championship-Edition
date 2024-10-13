package com.assignment.tictactoe.service;

public interface Board {
    BoardUI getBoardUI();
    void initializeBoard();
    boolean isLegalMove(int row, int col);
    void updateMove(int row, int col, Piece piece);
    Winner checkWinner();
    void printBoard();
}
