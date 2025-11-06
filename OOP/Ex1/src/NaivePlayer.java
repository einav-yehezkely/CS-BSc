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