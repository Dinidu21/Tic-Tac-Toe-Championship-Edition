module com.assignment.tictactoe.tictactoechampionshipedition {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.assignment.tictactoe to javafx.fxml;
    exports com.assignment.tictactoe;
    opens com.assignment.tictactoe.controller to javafx.fxml;
}