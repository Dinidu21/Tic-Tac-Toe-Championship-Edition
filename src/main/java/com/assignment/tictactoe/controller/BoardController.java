package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.AIPlayer;
import com.assignment.tictactoe.service.Board;
import com.assignment.tictactoe.service.BoardImpl;
import com.assignment.tictactoe.service.HumanPlayer;
import com.assignment.tictactoe.service.Piece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class BoardController {
    @FXML
    private TextField playerNameField;
    @FXML
    private ComboBox<Piece> pieceSelection;
    @FXML
    private GridPane gameBoard;
    @FXML
    private Label statusMessage;
    private Board board;

    @FXML
    public void initialize() {
        pieceSelection.getItems().addAll(Piece.X,Piece.O);
    }
    @FXML
    public void startGame() {
        String playerName = playerNameField.getText();
        Piece selectedPiece = pieceSelection.getValue();
        if (selectedPiece == null) {
            statusMessage.setText("Please select a piece.");
            return;
        }

        board = new BoardImpl();
        HumanPlayer humanPlayer = new HumanPlayer(board, playerName, selectedPiece);
        AIPlayer aiPlayer = new AIPlayer(board, Piece.O);
        statusMessage.setText(playerName + " vs  AI - Game Started!");

    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

    }
}
