package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class BoardController {
    public Button startGameButton;
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
    private HumanPlayer humanPlayer;
    private AIPlayer aiPlayer;
    private Piece currentPlayerPiece;

    @FXML
    public void initialize() {
        pieceSelection.getItems().addAll(Piece.X, Piece.O);
        gameButtons = new Button[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j] = (Button) gameBoard.getChildren().get(i * 3 + j);
                gameButtons[i][j].setDisable(true);  // Disable until game starts
            }
        }

        // Restrict player name input to alphabets
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
        currentPlayerPiece = pieceSelection.getValue();

        if (currentPlayerPiece == null) {
            statusMessage.setText("Please select a piece.");
            return;
        } else if (playerName == null || playerName.trim().isEmpty()) {
            statusMessage.setText("Please enter your name.");
            return;
        }
        enableAllButtons();

        board = new BoardImpl();
        humanPlayer = new HumanPlayer(board, playerName, currentPlayerPiece);
        aiPlayer = new AIPlayer(board, currentPlayerPiece == Piece.X ? Piece.O : Piece.X);
        System.out.println(playerName+" s Piece is : "+humanPlayer.getSelectedPiece());
        System.out.println("AI s Piece is :" +aiPlayer.getSelectedPiece());
        statusMessage.setText(playerName + " vs AI - Game Started!");
        startGameButton.setDisable(true);
        playerNameField.setDisable(true);
        pieceSelection.setDisable(true);
        board.printBoard();
    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int row = GridPane.getRowIndex(clickedButton);
        int col = GridPane.getColumnIndex(clickedButton);

        if (board.isLegalMove(row, col)) {
            board.updateMove(row, col, humanPlayer.getSelectedPiece());
            clickedButton.setText(humanPlayer.getSelectedPiece().toString());
            clickedButton.setDisable(true);
        } else {
            statusMessage.setText("This move is not allowed. Try another spot.");
        }
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j].setDisable(true);
            }
        }
    }
    private void enableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j].setDisable(false);
            }
        }
    }
}
