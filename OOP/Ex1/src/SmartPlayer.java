/**
 * A SmartPlayer that blocks the NaivePlayer's winning move
 * and then plays in the first available spot.
 * @author einavye
 */
class SmartPlayer implements Player{
    /**
     * Constructor for SmartPlayer
     */
    public SmartPlayer(){}
    /**
     * Plays a turn by first blocking the NaivePlayer's move at (0,1),
     * then placing its mark in the first available cell on the board.
     * @param board the current game board
     * @param mark the mark of the current player (X or O)
     */
    @Override
    public void playTurn(Board board, Mark mark){
        board.putMark(mark, 0, 1); // blocks NaivePlayer
        for(int row = 1 ; row < board.getSize() ; row++){
            for(int col = 0 ; col < board.getSize() ; col++){
                if (board.getMark(row, col) == Mark.BLANK){
                    board.putMark(mark, row, col);
                    return;
                }
            }
        }
    }
}