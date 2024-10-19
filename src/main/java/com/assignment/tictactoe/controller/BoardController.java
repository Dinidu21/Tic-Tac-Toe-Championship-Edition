package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    private int humanPlayerScore = 0;
    private int aiPlayerScore = 0;
    private int tiesCount = 0;
    private String winnerName;

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

        // If the player selected Piece O, let the AI make the first move
        if (currentPlayerPiece == Piece.O) {
            statusMessage.setText("AI's Turn");  // Set status message to AI's turn
            makeAIMove();  // AI makes the first move
            board.printBoard();
            statusMessage.setText("Your Turn");  // Set status message to Human's turn
        }
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
            disableAllButtons();  // Disable buttons after game ends
            playagainbtn.setDisable(false);

            // Determine if it's a tie
            if (winner.getWinningPiece() == Piece.EMPTY) {
                tiesCount++; // Increment ties count
                statusMessage.setText("It's a tie!");
                disableAllButtons();
                playagainbtn.setDisable(false);  // Enable Play Again button after tie
            } else {
                notifyWinner();  // Only notify the winner if it's not a tie
            }
        }
    }

    @Override
    public void notifyWinner() {
        Winner winner = board.checkWinner();
        if (winner != null) {
            winnerName = (winner.getWinningPiece() == humanPlayer.getSelectedPiece())
                    ? humanPlayer.getName() : "AI";

            // Update scores
            if (winner.getWinningPiece() == humanPlayer.getSelectedPiece()) {
                humanPlayerScore++;
            } else if (winner.getWinningPiece() == aiPlayer.getSelectedPiece()) {
                aiPlayerScore++;
            }
            saveScores(); // Save scores after updating them

            statusMessage.setText(winnerName + " wins!");
            updateWinningButtons(winner.getWinningPositions()); // Highlight winning buttons
            disableAllButtons();
            difficultyBox.setDisable(true);
            isGameOver = true;
            playagainbtn.setDisable(false);
        }
    }

    private void updateWinningButtons(int[][] winningPositions) {
        for (int[] pos : winningPositions) {
            int row = pos[1];
            int col = pos[0];
            Button button = gameButtons[row][col];
            button.setStyle("-fx-background-color: green;"); // Set background color to green
        }
    }

    private void playAgain(ActionEvent event) {
        resetGame(); // Reset the game when Play Again is pressed
        // Re-enable piece selection, difficulty selection, and name input
        difficultyBox.setDisable(false);
        pieceSelection.setDisable(false);
        playerNameField.setDisable(false);
        startGameButton.setDisable(false); // Enable the start button so the user can start a new game
        playagainbtn.setDisable(true);  // Disable Play Again button until game is over again

    }

    private void resetGame() {
        board.initializeBoard(); // Reset the board state
        isGameOver = false; // Reset game-over state

        // Clear the text and styles of all buttons and re-enable them
        for (Button[] row : gameButtons) {
            for (Button button : row) {
                button.setText(""); // Clear the button text
                button.setStyle(""); // Reset the background to default
                button.setDisable(false); // Re-enable the button
            }
        }

        // Reset the status message to guide the user for the next steps
        statusMessage.setText("Select piece & difficulty,'Start' to begin!");

        // Reset or clear player scores if needed (optional)
         humanPlayerScore = 0;
         aiPlayerScore = 0;
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
            makeHardAIMove();
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

    private void makeHardAIMove() {
        int[] bestMove = aiPlayer.findBestMove(board);
        if (board.isLegalMove(bestMove[0], bestMove[1])) {
            board.updateMove(bestMove[0], bestMove[1], aiPlayer.getSelectedPiece());
            update(bestMove[1], bestMove[0], false);
        } else {
            System.out.println("Best Move is already filled");
        }
    }

    private void saveScores() {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><head><link rel='stylesheet' type='text/css' href='/styles/score.css'></head><body>")
                .append("<div class='container'>")
                .append("<h1>Tic Tac Toe Scores</h1>")
                .append("<p class='score'>Human Player (").append(humanPlayer.getName()).append("): <span class='winner'>").append(humanPlayerScore).append("</span></p>")
                .append("<p class='score'>AI Player: ").append(aiPlayerScore).append("</p>")
                .append("<p class='tie'>Ties: ").append(tiesCount).append("</p>")
                .append("<p class='difficulty ").append(currentDifficulty.equals("Hard") ? "hard" : "").append("'>Current Difficulty: ").append(currentDifficulty).append("</p>")
                .append("<a href='#' class='button'>Play Again</a>")
                .append("</div>")
                .append("<div class='footer'>")
                .append("</body></html>");

        String SCORE_FILE = "scores.html";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            writer.write(htmlContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int col, int row, boolean isHuman) {
        Button button = gameButtons[row][col];
        Piece piece = isHuman ? humanPlayer.getSelectedPiece() : aiPlayer.getSelectedPiece();
        button.setText(piece.toString());  // Update the button with the current player's piece (X or O)
        button.setDisable(true);  // Disable the button after it's been clicked

    }
}