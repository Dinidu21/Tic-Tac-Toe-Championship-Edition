package com.assignment.tictactoe.service;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Arrays;

@Getter
@Setter
public class BoardImpl implements BoardUI, Serializable {
    private Piece[][] pieces;
    private BoardUI boardUI;

    public BoardImpl() {
        this.pieces = new Piece[3][3];
        initializeBoard();
    }

    public BoardImpl(BoardUI boardUI) {
        this();
    }

    @Override
    public void initializeBoard() {
        for (int i = 0; i < pieces.length; i++) {
            Arrays.fill(pieces[i], Piece.EMPTY);
        }
    }

    @Override
    public void printBoard() {

    }

    @Override
    public Boolean isLegalMove(int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3) && (pieces[row][col] == Piece.EMPTY);
    }

    @Override
    public void updateMove(int row, int col, Piece piece) {
        if (!isLegalMove(row, col)) {
            System.out.println("Please Enter a Legal Move");
        } else {
            pieces[row][col] = piece;
            System.out.println("Move updated: " + piece + " placed at (" + row + ", " + col + ")");
        }
    }

@Override
    public Winner checkWinner() {
        return null;
    }

    @Override
    public void update(int col, int row, boolean isHuman) {

    }

    @Override
    public void notifyWinner() {

    }
}
