package com.assignment.tictactoe.service;

import com.assignment.tictactoe.controller.BoardController;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
@Getter
@Setter

public class BoardImpl implements Board, Serializable {
    private Piece[][] pieces;
    private BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        this.pieces = new Piece[3][3];
        initializeBoard();
    }

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pieces[i][j] = Piece.EMPTY;
            }
        }
    }

    @Override
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(pieces[i][j] == Piece.EMPTY ? " - " : " " + pieces[i][j] + " ");
                if (j < 2) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    @Override
    public boolean isLegalMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && pieces[row][col] == Piece.EMPTY;
    }

    @Override
    public void updateMove(int row, int col, Piece piece) {
        if (isLegalMove(row, col)) {
            pieces[row][col] = piece;
            boardUI.update(col, row, piece == Piece.X);
        }
    }

    @Override
    public Winner checkWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (pieces[i][0] != Piece.EMPTY && pieces[i][0] == pieces[i][1] && pieces[i][1] == pieces[i][2]) {
                return new Winner(pieces[i][0], 0, i, 1, i, 2, i);
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (pieces[0][j] != Piece.EMPTY && pieces[0][j] == pieces[1][j] && pieces[1][j] == pieces[2][j]) {
                return new Winner(pieces[0][j], j, 0, j, 1, j, 2);
            }
        }

        // Check diagonals
        if (pieces[0][0] != Piece.EMPTY && pieces[0][0] == pieces[1][1] && pieces[1][1] == pieces[2][2]) {
            return new Winner(pieces[0][0], 0, 0, 1, 1, 2, 2);
        }
        if (pieces[0][2] != Piece.EMPTY && pieces[0][2] == pieces[1][1] && pieces[1][1] == pieces[2][0]) {
            return new Winner(pieces[0][2], 2, 0, 1, 1, 0, 2);
        }

        // Check for a tie
        boolean isTie = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    isTie = false;
                    break;
                }
            }
            if (!isTie) break;
        }
        if (isTie) {
            return new Winner(Piece.EMPTY);
        }

        return null;
    }

    public int[] findNextAvailableSpot() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
