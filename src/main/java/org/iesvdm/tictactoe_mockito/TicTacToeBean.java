package org.iesvdm.tictactoe_mockito;

import org.jongo.marshall.jackson.oid.Id;

import java.util.Objects;

public class TicTacToeBean {
    private int x;
    private int y;
    @Id
    private int turn;
    private char player;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public char getPlayer() {
        return player;
    }

    public void setPlayer(char player) {
        this.player = player;
    }

    public TicTacToeBean() {
    }

    public TicTacToeBean(int x, int y, int turn, char player) {
        this.x = x;
        this.y = y;
        this.turn = turn;
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicTacToeBean that = (TicTacToeBean) o;
        return x == that.x &&
                y == that.y &&
                turn == that.turn &&
                player == that.player;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, turn, player);
    }

    @Override
    public String toString() {
        return "TicTacToeBean{" +
                "x=" + x +
                ", y=" + y +
                ", turn=" + turn +
                ", player=" + player +
                '}';
    }
}
