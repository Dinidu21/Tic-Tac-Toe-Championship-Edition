package com.assignment.tictactoe.service;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter

public class HumanPlayer extends Player implements Serializable {
    private String name;

    public HumanPlayer() {
        super(null);
    }

    public HumanPlayer(Board board) {
        super(board);
    }

    @Override
    public void move(int row, int col) {

    }
}
