package com.assignment.tictactoe.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.io.Serializable;
@Getter
@Setter

public class AIPlayer extends Player implements Serializable {
    private Random random;
    private Board board;

    public AIPlayer(Board board) {
        super(board);
        this.random = new Random();
    }

    @Override
    void move(int row, int col) {

    }
}
