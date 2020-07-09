package data;

public class Board{
    
    private int[][] grid;
    private int count;
    public int turn;
    public int state;

    public Board(){
        count = 0;
        turn = 0;
        state = 0;
        grid = new int[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grid[i][j] = 0;
    }

    public Board(int[][] grid) {
        this.grid = grid;
    }

    public int getTurn() {
        return turn;
    }

    public int getState() {
        return state;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void insert(int x, int y){
        grid[x][y] = turn + 1;
        count++;
        boolean isWin = checkVictory(x,y);
        if (isWin)
            state = turn + 1;
        if (!isWin && count == 9)
            state = 3;
        turn ^= 1;
    }

    private boolean checkMiddleVictory(){
        int val = grid[1][1];
        if (grid[1][0] == val && grid[1][2] == val)
            return true;
        else if (grid[2][0] == val && grid[0][2] == val)
            return true;
        else if (grid[2][1] == val && grid[0][1] == val)
            return true;
        else if (grid[2][2] == val && grid[0][0] == val)
            return true;
        return false;
    }

    private boolean checkSideVictory(int x, int y){
        int val = grid[x][y];
        boolean flag = false;
        for (int i = 1; i <= 2; i++){
            if (!flag)
                flag = grid[(x + i) % 3][y] == val;
            else
                return grid[(x + i) % 3][y] == val;
        }
        flag = false;
        for (int j = 1; j <= 2; j++){ 
            if (!flag)
                flag = grid[x][(y + j) % 3] == val;
            else
                return grid[x][(y + j) % 3] == val;
        }
        return false;
    }

    private boolean checkDiagVictory(int x, int y){
        int val = grid[x][y];
        if (!isCorner(x, y))
            return false;
        else {
            return grid[(x + 2) % 4][(y + 2) % 4] == val && grid[1][1] == val;
        }
    }

    private boolean isCorner(int x, int y){
        if (x == 0)
            return y == 0 || y == 2;
        else if (x == 2)
            return y == 0 || y == 2;
        else
            return false;
    }

    public boolean checkVictory(int x, int y){
        if (x == 1 && y == 1) {
            return checkMiddleVictory();
        }
        else {
            return checkSideVictory(x, y) || checkDiagVictory(x, y);
        }
    }


}