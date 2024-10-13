package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class BoardController implements BoardUI {
    public static final String EASY = "Easy";
    public static final String HARD = "Hard";
    public ComboBox<String> difficultyBox;
    public Button playagainbtn;
    @FXML
    private Button startGameButton;
    @FXML
    private TextField playerNameField;
    @FXML
    private ComboBox<Piece> pieceSelection;
    @FXML
    private GridPane gameBoard;
    @FXML
    private Label statusMessage;

    private HumanPlayer humanPlayer;
    private AIPlayer aiPlayer;
    private Board board;
    private Button[][] gameButtons;
    private Piece currentPlayerPiece;
    private boolean isGameOver = false;

    @FXML
    public void initialize() {
        playagainbtn.setDisable(true);
        pieceSelection.getItems().addAll(Piece.X, Piece.O);
        difficultyBox.getItems().addAll(EASY,HARD);
        gameButtons = new Button[3][3];

        // Initialize the buttons in the GridPane
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j] = (Button) gameBoard.getChildren().get(i * 3 + j);
                gameButtons[i][j].setDisable(true);  // Disable until game starts
            }
        }

        // Only allow alphabetic characters in player name
        playerNameField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            if (!character.matches("[a-zA-Z]")) {
                event.consume();
            }
        });
        // Add Play Again button click event
        playagainbtn.setOnAction(this::playAgain);
    }

    @FXML
    public void startGame() {
        String playerName = playerNameField.getText();
        currentPlayerPiece = pieceSelection.getValue();
        String difLvl = String.valueOf(difficultyBox.getValue());

        if (currentPlayerPiece == null) {
            statusMessage.setText("Please select a piece.");
            return;
        } else if (playerName == null || playerName.trim().isEmpty()) {
            statusMessage.setText("Please enter your name.");
            return;
        }

        board = new BoardImpl(this);
        humanPlayer = new HumanPlayer(board, playerName, currentPlayerPiece);
        aiPlayer = new AIPlayer(board, currentPlayerPiece == Piece.X ? Piece.O : Piece.X);

        enableAllButtons();
        statusMessage.setText(playerName + " vs AI - Game Started!");
        System.out.println(playerName + "'s Piece is: " + humanPlayer.getSelectedPiece());
        System.out.println("AI's Piece is: " + aiPlayer.getSelectedPiece());
        startGameButton.setDisable(true);
        playerNameField.setDisable(true);
        pieceSelection.setDisable(true);
        board.printBoard();
    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        if (isGameOver) return;
        Button clickedButton = (Button) event.getSource();
        int row = GridPane.getRowIndex(clickedButton);
        int col = GridPane.getColumnIndex(clickedButton);

        if (board.isLegalMove(row, col)) {
            System.out.println("Now its your turn "+playerNameField.getText());
            humanPlayer.move(row, col);  // Human player makes a move
            update(col, row, true);      // Update board UI for human move
            board.printBoard();
            checkGameState();            // Check if human wins or if there's a tie
            if (!isGameOver) {
                System.out.println("Now AI going to make move");
                makeAIMove();             // AI makes a move after human
                board.printBoard();
                checkGameState();         // Check if AI wins or if there's a tie
            }
        }
    }

    private void checkGameState() {
        Winner winner = board.checkWinner();
        if (winner != null) {
            isGameOver = true;
            notifyWinner();
            disableAllButtons();  // Disable buttons after game ends
            playagainbtn.setDisable(false);
            // Determine the game result tie
            if (winner.getWinningPiece() == Piece.EMPTY) {
                statusMessage.setText("It's a tie!");
                disableAllButtons();
                playagainbtn.setDisable(false);  // Enable Play Again button after tie
            }
        }
    }

    @Override
    public void notifyWinner() {
        Winner winner = board.checkWinner();
        if (winner != null) {
            String winnerName = (winner.getWinningPiece() == humanPlayer.getSelectedPiece())
                    ? humanPlayer.getName() : "AI";
            statusMessage.setText(winnerName + " wins!");
            disableAllButtons();
            difficultyBox.setDisable(true);
            isGameOver = true;
            playagainbtn.setDisable(false);  // Enable Play Again button after game over
        }
    }

    @Override
    public void update(int col, int row, boolean isHuman) {
        Button button = gameButtons[row][col];
        Piece piece = isHuman ? humanPlayer.getSelectedPiece() : aiPlayer.getSelectedPiece();
        button.setText(piece.toString());  // Update the button with the current player's piece (X or O)
        button.setDisable(true);  // Disable the button after it's been clicked
    }

    private void playAgain(ActionEvent event) {
        resetGame(); // Reset the game when Play Again is pressed
        difficultyBox.setDisable(true);
        startGameButton.setDisable(true);
        pieceSelection.setDisable(true);
    }

    private void resetGame() {
        board.initializeBoard(); // Resetting the board state
        isGameOver = false; // Reset game-over state

        // Clear the text of all buttons and re-enable them
        for (Button[] row : gameButtons) {
            for (Button button : row) {
                button.setText(""); // Clear the button text
                button.setDisable(false); // Re-enable the button
            }
        }
        statusMessage.setText(humanPlayer.getName() + " vs AI - Game Restarted!");
    }

    private void disableAllButtons() {
        for (Button[] row : gameButtons) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    private void enableAllButtons() {
        for (Button[] row : gameButtons) {
            for (Button button : row) {
                button.setDisable(false);
            }
        }
    }

    private void makeAIMove() {
        int[] spot = ((BoardImpl) board).findNextAvailableSpot();  // Get the next available spot
        if (spot != null) {
            aiPlayer.move(spot[0], spot[1]);  // Let the AI player make its move
            update(spot[1], spot[0], false);  // Update the board UI after the move (col, row)
        }
    }
}
