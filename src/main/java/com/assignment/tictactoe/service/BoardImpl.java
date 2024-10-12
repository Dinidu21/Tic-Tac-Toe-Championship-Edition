package com.assignment.tictactoe.service;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Arrays;

@Getter
@Setter
public class BoardImpl implements Board, Serializable {
    private Piece[][] pieces;
    private BoardUI boardUI;

    public BoardImpl() {
        this.pieces = new Piece[3][3];
        initializeBoard();
    }

    @Override
    public void initializeBoard() {
        for (int i = 0; i < pieces.length; i++) {
            Arrays.fill(pieces[i], Piece.EMPTY);
        }
    }

    @Override
    public void printBoard() {
        for (Piece[] row : pieces) {
            System.out.println(Arrays.toString(row));
        }
    }

    @Override
    public boolean isLegalMove(int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3) && (pieces[row][col] == Piece.EMPTY);
    }

    @Override
    public void updateMove(int row, int col, Piece piece) {
        if (!isLegalMove(row, col)) {
            System.out.println("Please Enter a Legal Move");
        } else {
            pieces[row][col] = piece;
            System.out.println("Move updated: " + piece + " placed at (" + row + ", " + col + ")");
            if (boardUI != null) {
                boardUI.update(col, row, true);
            }
        }
    }

    @Override
    public Winner checkWinner() {
        return null;
    }

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public void update(int col, int row, boolean isHuman) {
    }

    @Override
    public void notifyWinner() {
    }
}
