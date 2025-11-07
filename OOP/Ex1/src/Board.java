/**
 * Represents a square game board for a two-player game
 * @author einavye
 */
class Board{
    private int size = 4;
    private Mark[][] board;

    /**
     * Default constructor for a 4x4 board
     */
    public Board(){
        initializeBoard(this.size);
    }
    /**
     * Constructor for a board of given size
     * @param size size of the board
     */
    public Board(int size){
        this.size = size;
        initializeBoard(size);
    }
    /**
     * Initializes the board with BLANK marks
     * @param size size of the board
     */
    private void initializeBoard(int size){
        board = new Mark[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }
    /**
     * @return size of the board
     */
    public int getSize(){
        return this.size;
    }
    /**
     * Puts a mark on the board at the given position if it's BLANK
     * @param mark Mark to be placed
     * @param row Row index
     * @param col Column index
     * @return true if the mark was placed, false otherwise
     */
    boolean putMark(Mark mark, int row, int col){
        if(board[row][col] == Mark.BLANK){
            board[row][col] = mark;
            return true;
        }
        return false;
    }
    /**
     * Gets the mark at the given position
     * @param row Row index
     * @param col Column index
     * @return Mark at the given position, or BLANK if out of bounds
     */
    Mark getMark(int row, int col){
        if(row < size && col < size){
            return board[row][col];
        }
        return Mark.BLANK;
    }
}