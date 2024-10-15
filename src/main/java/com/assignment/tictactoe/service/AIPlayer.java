package com.assignment.tictactoe.service;

import com.assignment.tictactoe.controller.BoardController;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.io.Serializable;

@Getter
@Setter
public class AIPlayer extends Player implements Serializable {
    private Random random;
    private Piece selectedPiece; // AI's piece (e.g., X or O)
    private String difficulty;

    public AIPlayer(Board board, Piece selectedPiece, String difficulty) {
        super(board);
        if (board == null) {
            throw new NullPointerException("Board cannot be null");
        }
        if (selectedPiece == null) {
            throw new NullPointerException("Selected piece cannot be null");
        }
        this.random = new Random();
        this.selectedPiece = selectedPiece;
        this.difficulty = difficulty;
    }

    @Override
    public void move(int row, int col) {
        if (difficulty.equals(BoardController.EASY)) {
            System.out.println("Difficulty is Easy...");
            makeEasyMove();
        } else if (difficulty.equals(BoardController.HARD)) {
            System.out.println("Difficulty is Hard...");
            //makeHardMove();
        }
    }



    private void makeEasyMove() {
        try {
            int[] spot = ((BoardImpl) board).findNextAvailableSpot();
            if (spot == null) {
                System.out.println("No available spot found.");
                return;
            }
            board.updateMove(spot[0], spot[1], selectedPiece);
        } catch (ClassCastException e) {
            System.err.println("Error: Board is not an instance of BoardImpl");
        } catch (Exception e) {
            System.err.println("Error updating board: " + e.getMessage());
        }
    }
}
