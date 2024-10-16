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
    public Piece opponentPiece;

    public AIPlayer(Board board, Piece selectedPiece) {
        super(board);
        this.random = new Random();
        this.selectedPiece = selectedPiece;
        this.opponentPiece = (selectedPiece == Piece.X) ? Piece.O :Piece.X;
    }

    @Override
    public void move(int row, int col) {
        int[] spot = ((BoardImpl) board).findNextAvailableSpot();
        if (spot != null) {
            board.updateMove(spot[0], spot[1], selectedPiece);
        }
    }

//    public int[] findBestMove(Board board) {
//        System.out.println("Find the best move method in AI Player");
//
//    }

    private int minimax(BoardImpl board, int depth, boolean isMaximizing) {
        Winner winner = board.checkWinner();
        if (winner.getWinningPiece() == selectedPiece) return 10 - depth;
        if (winner.getWinningPiece() == opponentPiece) return depth - 10;
        if (isBoardFull()) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isLegalMove(i, j)) {
                        board.updateMove(i, j, selectedPiece);
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board.updateMove(i, j, Piece.EMPTY);
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isLegalMove(i, j)) {
                        board.updateMove(i, j, opponentPiece);
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board.updateMove(i, j, Piece.EMPTY);
                    }
                }
            }
            return best;
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isLegalMove(i, j)) {
                    return false; // Found an empty cell, board is not full
                }
            }
        }
        return true; // No empty cells found, board is full
    }
}