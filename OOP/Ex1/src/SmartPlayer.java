class SmartPlayer implements Player{
    public SmartPlayer(){}

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