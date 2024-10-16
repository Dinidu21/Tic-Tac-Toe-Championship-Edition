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

    public int[] findBestMove(Board board) {
        int bestValue = Integer.MIN_VALUE;
        int[] bestMove = new int[2]; // To store the best move as {row, col}

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if the move is legal
                if (board.isLegalMove(i, j)) {
                    // Make the move
                    board.updateMove(i, j, selectedPiece);
                    // Call minimax
                    int moveValue = minimax((BoardImpl) board, 0, false);
                    // Undo the move
                    board.updateMove(i, j, Piece.EMPTY);

                    // If the value of this move is greater than the best value found
                    if (moveValue > bestValue) {
                        bestMove[0] = i; // Update best row
                        bestMove[1] = j; // Update best column
                        bestValue = moveValue; // Update best value
                    }
                }
            }
        }

        // Return the best move found
        return bestMove;
    }


    private int minimax(BoardImpl board, int depth, boolean isMaximizing) {
        Winner winner = board.checkWinner(); // Check for a winner

        // Check if the game is over and return the appropriate score
        if (winner != null) {
            if (winner.getWinningPiece() == selectedPiece) {
                return 10 - depth; // AI wins
            } else if (winner.getWinningPiece() == opponentPiece) {
                return depth - 10; // Opponent wins
            } else {
                return 0; // Tie
            }
        }

        // If the game is not over and we are maximizing
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
            // If the game is not over and we are minimizing
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