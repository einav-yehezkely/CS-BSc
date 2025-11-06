import java.util.Random;

class WhateverPlayer implements Player{
    Random random = new Random();

    public WhateverPlayer(){}

    @Override
    public void playTurn(Board board, Mark mark){
        int randomRow = random.nextInt(board.getSize());
        int randomCol = random.nextInt(board.getSize());

        board.putMark(mark, randomRow, randomCol);
    }
}