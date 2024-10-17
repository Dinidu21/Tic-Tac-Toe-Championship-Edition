package com.assignment.tictactoe.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Random;
import java.io.Serializable;

/**
 * AIPlayer class represents the artificial intelligence for the Tic-Tac-Toe game.
 * It uses the minimax algorithm to find the optimal move during gameplay.
 */
@Getter
@Setter
public class AIPlayer extends Player implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Added for consistent serialization

    private Random random;
    private Piece selectedPiece;
    public Piece opponentPiece;

    /**
     * Constructor to initialize the AI player with the selected piece and set the opponent's piece.
     *
     * @param board The game board where moves are made.
     * @param selectedPiece The piece chosen by the AI (X or O).
     */
    public AIPlayer(Board board, Piece selectedPiece) {
        super(board);
        this.random = new Random();
        this.selectedPiece = selectedPiece;
        this.opponentPiece = (selectedPiece == Piece.X) ? Piece.O : Piece.X;
    }

    /**
     * Makes a move for the AI player. If a spot is available, it updates the board with AI's selected piece.
     *
     * @param row The row where the AI will place its piece.
     * @param col The column where the AI will place its piece.
     */
    @Override
    public void move(int row, int col) {
        int[] spot = ((BoardImpl) board).findNextAvailableSpot();
        if (spot != null) {
            board.updateMove(spot[0], spot[1], selectedPiece);
        }
    }

    /**
     * Finds the best possible move for the AI using the minimax algorithm.
     *
     * @param board The game board to analyze.
     * @return An array containing the best row and column for the next move.
     */
    public int[] findBestMove(Board board) {
        int bestValue = Integer.MIN_VALUE;
        int[] bestMove = new int[2]; // To store the best move as {row, col}

        // Traverse all cells, evaluate the minimax function for all legal moves, and return the best move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if the move is legal
                if (board.isLegalMove(i, j)) {
                    // Make the move
                    board.updateMove(i, j, selectedPiece);
                    // Call minimax and evaluate the current move
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

    /**
     * Minimax algorithm to find the optimal move. It recursively simulates all possible moves.
     *
     * @param board The game board to evaluate.
     * @param depth The depth of recursion in the minimax algorithm.
     * @param isMaximizing True if the current player is the maximizer (AI), false if it's the minimizer (opponent).
     * @return The score of the board at the current state.
     */
    private int minimax(BoardImpl board, int depth, boolean isMaximizing) {
        Winner winner = board.checkWinner(); // Check for a winner

        // Base case: if the game is over, return the appropriate score
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
}
