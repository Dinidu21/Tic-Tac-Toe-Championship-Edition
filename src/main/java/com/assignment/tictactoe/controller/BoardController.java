package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BoardController implements Serializable {
    private Player humanPlayer;
    private Player aiPlayer;

    @FXML
    private GridPane gameBoard;

    @FXML
    private Label statusMessage;

    @FXML
    private TextField playerNameField;

    @FXML
    private ComboBox<String> pieceSelection;

    @FXML
    private Board board;

    @FXML
    public void initialize() {
        pieceSelection.getItems().addAll("X", "O");
    }

    @FXML
    public void startGame() {
        String playerName = playerNameField.getText();
        Piece selectedPiece = Piece.valueOf(pieceSelection.getValue());
        if (playerName.isEmpty()) {
            statusMessage.setText("Please enter your name and select a piece!");
            return;
        }
        if (board == null) {
            board = new BoardImpl();
        }
        humanPlayer = new HumanPlayer(board, playerName, selectedPiece);
        aiPlayer = new AIPlayer(board);
        statusMessage.setText(playerName + " has chosen " + selectedPiece + ". Game starts!");
    }

}
