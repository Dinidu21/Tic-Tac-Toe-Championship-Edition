package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BoardController implements Serializable {
    private Player humanPlayer;
    private Player aiPlayer;

    @FXML
    private GridPane gameBoard;  // Reference to the grid in the FXML file

    @FXML
    private Label statusMessage; // Status message for displaying player turns or game outcome

    private boolean isPlayerXTurn = true; // Track if it's Player X's turn
    private boolean gameOver = false; // Track if the game has ended

//    public BoardController() {
//        // Initialize players, could be done in the FXML initialization or constructor
//        this.humanPlayer = new Player("Human", "X");
//        this.aiPlayer = new Player("AI", "O");
//    }

//    // Initialize the game, attach click event handlers to each cell
//    public void initialize() {
//        for (var node : gameBoard.getChildren()) {
//            if (node instanceof Pane) {
//                node.setOnMouseClicked(event -> handleCellClick((Pane) node));
//            }
//        }
//    }

    // Handle a click event on a cell
//    private void handleCellClick(Pane cell) {
//        if (!gameOver && cell.getChildren().isEmpty()) {
//            Label playerMove = new Label(isPlayerXTurn ? humanPlayer.getSymbol() : aiPlayer.getSymbol());
//            playerMove.getStyleClass().add("grid-cell");  // Add CSS styling to the move
//            cell.getChildren().add(playerMove);
//
//            // Check if the player wins after this move
//            checkGameStatus();
//
//            // If the game is not over, switch turns
//            if (!gameOver) {
//                togglePlayer();
//                // AI makes a move if it's their turn
//                if (!isPlayerXTurn) {
//                    aiMove();
//                }
//            }
//        }
//    }

    // Toggle between Player X and Player O
    private void togglePlayer() {
        isPlayerXTurn = !isPlayerXTurn;
        statusMessage.setText(isPlayerXTurn ? "Player X's Turn" : "Player O's Turn");
    }

    // Simulate the AI move, here you can add your AI logic
//    private void aiMove() {
//        // Basic AI move logic (you can improve this with smarter AI)
//        for (var node : gameBoard.getChildren()) {
//            if (node instanceof Pane && ((Pane) node).getChildren().isEmpty()) {
//                Label aiMove = new Label(aiPlayer.getSymbol());
//                aiMove.getStyleClass().add("grid-cell");
//                ((Pane) node).getChildren().add(aiMove);
//                break; // For simplicity, AI chooses the first empty cell it finds
//            }
//        }
//
//        // Check if AI wins or game is over after the move
//        checkGameStatus();
//        if (!gameOver) {
//            togglePlayer(); // Switch back to human player
//        }
//    }

    // Check if there's a winner or if the game ends in a tie
    private void checkGameStatus() {
        // Add logic to check for win conditions and update statusMessage accordingly
        // For example: check rows, columns, diagonals
        if (isWin()) {
            gameOver = true;
            statusMessage.setText(isPlayerXTurn ? "Player X Wins!" : "Player O Wins!");
        } else if (isTie()) {
            gameOver = true;
            statusMessage.setText("It's a Tie!");
        }
    }

    // Placeholder method to check for win condition (implement actual win-checking logic)
    private boolean isWin() {
        // You should check the board here to see if there is a winning combination
        return false; // This should return true if a player has won
    }

    // Placeholder method to check for a tie
    private boolean isTie() {
        // Check if all cells are filled and no player has won
        for (var node : gameBoard.getChildren()) {
            if (node instanceof Pane && ((Pane) node).getChildren().isEmpty()) {
                return false; // Not a tie if any cell is still empty
            }
        }
        return true;
    }
}
