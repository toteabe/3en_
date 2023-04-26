package org.iesvdm.tictactoe_mockito;

public class TicTacToe {
    private TicTacToeCollection ticTacToeCollection;

    private Character[][] board = {{'\0', '\0', '\0'}, {'\0', '\0', '\0'}, {'\0', '\0', '\0'}};
    private char lastPlayer = '\0';
    private int turnCounter = 0;
    private static final int SIZE = 3;
    public static final String NO_WINNER = "No winner";
    public static final String RESULT_DRAW = "The result is draw";

    public String play(int x, int y) {
        checkAxis(x);
        checkAxis(y);
        lastPlayer = nextPlayer();
//        setBox(x, y, lastPlayer);
        setBox(new TicTacToeBean( x, y, ++turnCounter, lastPlayer));
        if (isWin(x, y)) {
            return lastPlayer + " is the winner";
        } else if (isDraw()) {
            return RESULT_DRAW;
        } else {
            return NO_WINNER;
        }
    }

    public char nextPlayer() {
        if (lastPlayer == 'X') {
            return 'O';
        }
        return 'X';
    }

    private void checkAxis(int axis) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException("X is outside board");
        }
    }

    private void setBox(TicTacToeBean bean) {
        if (board[bean.getX() - 1][bean.getY() - 1] != '\0') {
            throw new RuntimeException("Box is occupied");
        } else {
            board[bean.getX() - 1][bean.getY() - 1] = lastPlayer;
//            getCollection().saveMove(bean);
            if(!getCollection().saveMove(bean)){
                throw new RuntimeException("saving to DB failed");
            }
        }
    }

    private boolean isWin(int x, int y) {
        int playerTotal = lastPlayer * SIZE;
        char horizontal, vertical, diagonal1, diagonal2;
        horizontal = vertical = diagonal1 = diagonal2 = '\0';
        for (int i = 0; i < SIZE; i++) {
            horizontal += board[i][y - 1];
            vertical += board[x - 1][i];
            diagonal1 += board[i][i];
            diagonal2 += board[i][SIZE - i - 1];
        }
        if (horizontal == playerTotal
                || vertical == playerTotal
                || diagonal1 == playerTotal
                || diagonal2 == playerTotal) {
            return true;
        }
        return false;
    }

    private boolean isDraw() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (board[x][y] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    public void setTicTacToeCollection(TicTacToeCollection ticTacToeCollection) {
        this.ticTacToeCollection = ticTacToeCollection;
    }

    public TicTacToeCollection getCollection() {
        return ticTacToeCollection;
    }
}
