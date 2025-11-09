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
        if(board.putMark(mark, 0, 1)){
            return;
        } // blocks NaivePlayer

        // fill from bottom-left upward diagonally
        for (int sum = 0; sum <= 2 * (board.getSize() - 1); sum++) {
            for (int row = 0; row < board.getSize(); row++) {
                int col = sum - row;
                if (col >= 0 && col < board.getSize() && board.getMark(row, col) == Mark.BLANK) {
                    board.putMark(mark, row, col);
                    return;
                }
            }
        }

    }
}