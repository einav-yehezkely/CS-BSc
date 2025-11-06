class Board{
    private int size = 4;
    private Mark[][] board;

    public Board(){
        initializeBoard(this.size);

    }
    public Board(int size){
        this.size = size;
        initializeBoard(size);
    }

    private void initializeBoard(int size){
        board = new Mark[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    public int getSize(){
        return this.size;
    }

    boolean putMark(Mark mark, int row, int col){
        if(board[row][col] == Mark.BLANK){
            board[row][col] = mark;
            return true;
        }
        return false;
    }

    Mark getMark(int row, int col){
        if(row < size && col < size){
            return board[row][col];
        }
        return Mark.BLANK;
    }
}