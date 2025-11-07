class Game {
    Player playerX;
    Player playerO;
    Renderer renderer;
    Board board;
    int winStreak;

    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.board = new Board();
        this.winStreak = 3;
    }

    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.winStreak = winStreak;
        this.board = new Board(size);
    }

    public int getWinStreak() {
        return winStreak;
    }

    public int getBoardSize() {
        return board.getSize();
    }

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
        /**
         * check current row, col, diagonals for winStreak
         */
        // check row
        if(countMarks(row, col, 0, 1, mark) >= winStreak){
//           countMarks(row, col, 0, -1, mark) - 1 >= winStreak){
            return true;
        }

        // check col
        if(countMarks(row, col, 1, 0, mark) >= winStreak){
//           countMarks(row, col, -1, 0, mark) - 1 >= winStreak){
            return true;
        }

        // check diagonal \
        if(countMarks(row, col, 1, 1, mark) >= winStreak){
//           countMarks(row, col, -1, -1, mark) - 1 >= winStreak){
            return true;
        }

        // check diagonal /
        if(countMarks(row, col, 1, -1, mark) >= winStreak){
//           countMarks(row, col, -1, 1, mark) - 1 >= winStreak){
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