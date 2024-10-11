package com.assignment.tictactoe.service;

public interface BoardUI extends Board{
    void update(int col,int row,boolean isHuman);
    void notifyWinner();
}
