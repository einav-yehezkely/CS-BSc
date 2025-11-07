/**
 * Represents a Tic-Tac-Toe game with two players, a board, and a renderer.
 * @author einavye
 */
class Game {
    Player playerX;
    Player playerO;
    Renderer renderer;
    Board board;
    int winStreak;
    /**
     * Initializes a new Game with two players and a renderer.
     * The board is initialized to size 4x4 and winStreak to 3.
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.board = new Board();
        this.winStreak = 3;
    }
    /**
     * Initializes a new Game with two players, a board size, winStreak, and a renderer.
     * @param playerX Player for mark X
     * @param playerO Player for mark O
     * @param size Size of the board (size x size)
     * @param winStreak Number of consecutive marks needed to win
     * @param renderer Renderer to display the board
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.winStreak = winStreak;
        this.board = new Board(size);
    }
    /**
     * @return the winStreak value
     */
    public int getWinStreak() {
        return winStreak;
    }
    /**
     * @return the size of the board
     */
    public int getBoardSize() {
        return board.getSize();
    }
    /**
     * Runs the game until there is a winner or a tie.
     * @return Mark.X if player X wins, Mark.O if player O wins, or Mark.BLANK for a tie
     */
    public Mark run() {
        Mark currentMark = Mark.X;
        Mark nextMark;
        renderer.renderBoard(board);
        while(true){
            if(currentMark == Mark.X){
                playerX.playTurn(board, Mark.X);
                nextMark = Mark.O;
            }
            else{
                playerO.playTurn(board, Mark.O);
                nextMark = Mark.X;
            }
            renderer.renderBoard(board);
            if(markWon(currentMark)){
                return currentMark;
            }
            if(checkTie()){
                return Mark.BLANK;
            }
            currentMark = nextMark;
        }

    }

    private boolean markWon(Mark mark) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getMark(row, col) == mark) {
                    if (winCheck(row, col, mark)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean winCheck(int row, int col, Mark mark) {
        // check row
        if(countMarks(row, col, 0, 1, mark) >= winStreak){
            return true;
        }

        // check col
        if(countMarks(row, col, 1, 0, mark) >= winStreak){
            return true;
        }

        // check diagonal \
        if(countMarks(row, col, 1, 1, mark) >= winStreak){
            return true;
        }

        // check diagonal /
        if(countMarks(row, col, 1, -1, mark) >= winStreak){
            return true;
        }

        return false;
    }

    private int countMarks(int row, int col, int directionRow, int directionCol, Mark mark) {
        int count = 0;
        while(row >= 0 && row < board.getSize() &&
              col >= 0 && col < board.getSize() &&
              board.getMark(row, col) == mark)
        {
            count++;
            row += directionRow;
            col += directionCol;
        }
        return count;
    }

    private boolean checkTie() {
        for(int row = 0 ; row < board.getSize() ; row++){
            for(int col = 0 ; col < board.getSize() ; col++){
                if(board.getMark(row, col) == Mark.BLANK){
                    return false;
                }
            }
        }
        return true;
    }
}