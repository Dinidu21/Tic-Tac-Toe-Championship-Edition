package com.assignment.tictactoe.service;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class HumanPlayer extends Player implements Serializable {
    private String name;
    private Piece selectedPiece;

    public HumanPlayer(Board board, String name, Piece selectedPiece) {
        super(board);
        this.name = name;
        this.selectedPiece = selectedPiece;
    }

    @Override
    public void move(int row, int col) {
        if (board.isLegalMove(row, col)) {
            board.updateMove(row, col, selectedPiece);
        }
    }
}
