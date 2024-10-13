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
    private int col1, row1, col2, row2, col3, row3;
    public Winner(Piece winningPiece) {this.winningPiece = winningPiece;}
}
