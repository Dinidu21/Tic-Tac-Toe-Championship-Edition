package com.assignment.tictactoe.service;

public enum Piece {
    X,
    O,
    EMPTY;

    @Override
    public String toString() {
        return switch (this) {
            case X -> "X";
            case O -> "O";
            case EMPTY -> "   ";
        };
    }
}

