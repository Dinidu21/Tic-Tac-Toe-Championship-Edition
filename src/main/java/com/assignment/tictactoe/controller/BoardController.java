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
import javafx.scene.input.KeyEvent;
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
    private Button[][] gameButtons;
    private Board board;

    @FXML
    public void initialize() {
        pieceSelection.getItems().addAll(Piece.X, Piece.O);
        gameButtons = new Button[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j] = (Button) gameBoard.getChildren().get(i * 3 + j);
                gameButtons[i][j].setDisable(true);
            }
        }

        playerNameField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            if (!character.matches("[a-zA-Z]")) {
                event.consume();
            }
        });
    }

    @FXML
    public void startGame() {
        String playerName = playerNameField.getText();
        Piece selectedPiece = pieceSelection.getValue();

        if (selectedPiece == null) {
            statusMessage.setText("Please select a piece.");
            return;
        } else if (playerName == null || playerName.trim().isEmpty()) {
            statusMessage.setText("Please enter your name.");
            return;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j].setDisable(false);
            }
        }

        board = new BoardImpl();
        HumanPlayer humanPlayer = new HumanPlayer(board, playerName, selectedPiece);
        AIPlayer aiPlayer = new AIPlayer(board, Piece.O);
        statusMessage.setText(playerName + " vs AI - Game Started!");
    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);
    }
}
