import java.util.Random;
/**
 * A player that plays by putting its mark in a random position on the board.
 * @author einavye
 */
class WhateverPlayer implements Player{
    Random random = new Random();
    /**
     * Constructor for WhateverPlayer
     */
    public WhateverPlayer(){}
    /**
     * Puts a mark in a random position on the board.
     * If the position is already occupied, it keeps trying until it finds an empty spot.
     * @param board the current game board
     * @param mark the mark of the current player (X or O)
     */
    @Override
    public void playTurn(Board board, Mark mark){
        while(true){
            int randomRow = random.nextInt(board.getSize());
            int randomCol = random.nextInt(board.getSize());

            if(board.putMark(mark, randomRow, randomCol)){
                return;
            }
        }

    }
}