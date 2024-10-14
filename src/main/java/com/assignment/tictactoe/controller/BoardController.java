package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private String currentDifficulty;

    @FXML
    public void initialize() {
        playagainbtn.setDisable(true);
        pieceSelection.getItems().addAll(Piece.X, Piece.O);
        difficultyBox.getItems().addAll(EASY, HARD);
        difficultyBox.setValue(EASY); // Set default difficulty to Easy
        currentDifficulty = EASY;

        Image image = new Image(getClass().getResource("/icons/pAgain.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        playagainbtn.setGraphic(imageView);

        gameButtons = new Button[3][3];

        // Initialize the buttons in the GridPane
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j] = (Button) gameBoard.getChildren().get(i * 3 + j);
                gameButtons[i][j].setDisable(true);
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
        currentDifficulty = difficultyBox.getValue();

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
            // Human player makes a move
            humanPlayer.move(row, col);
            update(col, row, true);      // Update board UI for human move
            statusMessage.setText("AI's Turn");  // Set status message to AI's turn

            board.printBoard();
            checkGameState();  // Check if human wins or if there's a tie

            // Let AI move if the game is not over
            if (!isGameOver) {
                makeAIMove();
                board.printBoard();
                checkGameState();  // Check if AI wins or if there's a tie

                if (!isGameOver) {
                    statusMessage.setText("Your Turn");  // Set status message to Human's turn
                }
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
        if (currentDifficulty.equals(HARD)) {
            //makeHardAIMove();
        } else {
            makeEasyAIMove();
        }
        // After AI makes a move, update the status message
        statusMessage.setText("Your Turn");
    }

    private void makeEasyAIMove() {
        int[] spot = ((BoardImpl) board).findNextAvailableSpot();
        if (spot != null) {
            aiPlayer.move(spot[0], spot[1]);
            update(spot[1], spot[0], false);
        }
    }

/*    private void makeHardAIMove() {
        int[] bestMove = aiPlayer.findBestMove(board);
        if (bestMove != null) {
            aiPlayer.move(bestMove[0], bestMove[1]);
            update(bestMove[1], bestMove[0], false);
        }
    }*/
}
