package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.Player;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter

public class BoardController implements Serializable {
    private Player humanPlayer;
    private Player aiPlayer;

    public BoardController() {}



}
