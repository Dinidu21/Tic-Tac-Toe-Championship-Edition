package com.assignment.tictactoe.service;

public abstract class Player {
    Board board;
    abstract void move(int row, int col);

    public Player(Board board) {
        this.board = board;
    }
}
