package data;

import javax.swing.JButton;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AI {

    private Board board;
    private JButton[][] buttons;

    public AI(Board b, JButton[][] j, int side, boolean t) {
        board = b;
        buttons = j;
    }

    public void move() {
        int[][] grid = board.getGrid();
        ArrayList<Point> moves = possibleMoves();
        for (Point p : moves) {
            if (new Board(trialBoard(p, 2)).checkVictory(p.x, p.y)) {
                buttons[p.x][p.y].doClick();
                return;
            }
        }
        for (Point p : moves) {
            if (new Board(trialBoard(p, 1)).checkVictory(p.x, p.y)) {
                buttons[p.x][p.y].doClick();
                return;
            }
        }
        int[][] availableWins = availableWins();
        int maxScore = 0;
        Point maxPoint = new Point(-1,-1);
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                if (grid[x][y] == 0 && availableWins[x][y] > maxScore) {
                    maxScore = availableWins[x][y];
                    maxPoint = new Point(x, y);
                }
        if (maxScore == 0) {
            Random r = new Random();
            Point p = moves.get(r.nextInt(moves.size()));
            buttons[p.x][p.y].doClick();
        } else {
            buttons[maxPoint.x][maxPoint.y].doClick();
        }
    }

    private ArrayList<Point> possibleMoves() {
        ArrayList<Point> moves = new ArrayList<>();
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                if (board.getGrid()[x][y] == 0)
                    moves.add(new Point(x, y));
        return moves;
    }

    private int[][] trialBoard(Point p, int side) {
        int[][] grid = new int[3][3];
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                grid[x][y] = board.getGrid()[x][y];
        grid[p.x][p.y] = side;
        return grid;
    }

    private boolean[] listWinnable() {
        int[][] grid = board.getGrid();
        boolean[] ret = new boolean[8];
        int[][][] winConds = winConditions();
        Arrays.fill(ret, true);
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                if (grid[x][y] == 1) 
                    for (int i = 0; i < ret.length; i++)
                        if (winConds[i][x][y] == 1)
                            ret[i] = false;
        return ret;
    }

    private int[][][] winConditions() {
        int[][][] ret = new int[8][3][3];
        for (int i = 0; i < 8; i++)
            for (int x = 0; x < 3; x++)
                for (int y = 0; y < 3; y++)
                    ret[i][x][y] = 0;
        for (int i = 0; i < 8; i++) {
            if (i >= 0 && i <= 2) {
                for (int j = 0; j < 3; j++)
                    ret[i][j][i] = 1;
            } else if (i >= 3 && i <= 5) {
                for (int j = 0; j < 3; j++)
                    ret[i][i - 3][j] = 1;
            } else if (i == 6) {
                ret[i][0][0] = 1;
                ret[i][1][1] = 1;
                ret[i][2][2] = 1;
            } else {
                ret[i][2][0] = 1;
                ret[i][1][1] = 1;
                ret[i][0][2] = 1;
            }
        }
        return ret;
    }

    private int[][] availableWins() {
        int[][] ret = new int[3][3];
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                ret[x][y] = 0;
        int[][][] winConds = winConditions();
        boolean[] isWinnable = listWinnable();
        assert winConds.length == isWinnable.length;
        for (int i = 0; i < winConds.length; i++)
            if (isWinnable[i])
                for (int x = 0; x < 3; x++)
                    for (int y = 0; y < 3; y++)
                        ret[x][y] += winConds[i][x][y];
        return ret;

    }
}