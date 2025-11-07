class SmartPlayer implements Player{
    public SmartPlayer(){}

    @Override
    public void playTurn(Board board, Mark mark){
        // TODO: check if
        for(int row = 0 ; row < board.getSize() ; row++){
            for(int col = 0 ; col < board.getSize() ; col++){
                // check win

                // check lose if opponent put mark there
            }
        }
    }
}