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
        for (Piece[] piece : pieces) {
            Arrays.fill(piece, Piece.EMPTY);
        }
    }

    @Override
    public void printBoard() {

    }

    @Override
    public boolean isLegalMove(int row, int col) {

        return false;
    }

    @Override
    public void updateMove(int row, int col, Piece piece) {


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
