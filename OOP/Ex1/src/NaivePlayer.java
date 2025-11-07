/**
 * A naive implementation of a Player for a Tic-Tac-Toe game.
 * This player simply places its mark in the first available cell
 * on the board, scanning from the top-left to the bottom-right.
 * @author einavye
 */
class NaivePlayer implements Player{
    public NaivePlayer(){};

    @Override
    public void playTurn(Board board, Mark mark){
        for(int row = 0 ; row < board.getSize() ; row++){
            for(int col = 0 ; col < board.getSize() ; col++){
                if (board.getMark(row, col) == Mark.BLANK){
                    board.putMark(mark, row, col);
                    return;
                }
            }
        }
    }
}