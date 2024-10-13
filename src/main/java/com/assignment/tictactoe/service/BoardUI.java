package com.assignment.tictactoe.service;

public interface BoardUI {
    void update(int col, int row, boolean isHuman);
    void notifyWinner();
}
