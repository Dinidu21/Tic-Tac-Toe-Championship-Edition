package com.assignment.tictactoe.service;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class HumanPlayer extends Player implements Serializable {
    private String name;

    public HumanPlayer(Board board) {
        super(board);
    }
    public HumanPlayer(Board board, String name) {
        super(board);
        this.name = name;
    }

    @Override
    public void move(int row, int col) {

    }
}
