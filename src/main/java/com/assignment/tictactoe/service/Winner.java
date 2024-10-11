package com.assignment.tictactoe.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Winner implements Serializable {
    private Piece winningPiece;
    private int col1;
    private int row1;
    private int col2;
    private int row2;
    private int col3;
    private int row3;

    public Winner(Piece winningPiece) {this.winningPiece = winningPiece;
    }
}
