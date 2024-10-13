package com.assignment.tictactoe.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.io.Serializable;

@Getter
@Setter
public class AIPlayer extends Player implements Serializable {
    private Random random;
    private Piece selectedPiece;

    public AIPlayer(Board board, Piece selectedPiece) {
        super(board);
        this.random = new Random();
        this.selectedPiece = selectedPiece;
    }

    @Override
    public void move(int row, int col) {
        int[] spot = ((BoardImpl) board).findNextAvailableSpot();
        if (spot != null) {
            board.updateMove(spot[0], spot[1], selectedPiece);
        }
    }
}
