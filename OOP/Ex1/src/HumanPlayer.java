/**
 * HumanPlayer class that implements the Player interface.
 * This class allows a human player to input their move via the console.
 * @author einavye
 */
class HumanPlayer implements Player{
    /**
     * Constructor for HumanPlayer
     */
    public HumanPlayer(){}

    /**
     * Prompts the human player to input their move and updates the board accordingly.
     * @param board the current game board
     * @param mark the mark of the current player (X or O)
     */
    @Override
    public void playTurn(Board board, Mark mark){
        int row;
        int col;
        System.out.printf("Player %s, type coordinates: ", mark.toString());
        while(true){
            int input = KeyboardInput.readInt();
            row = input / 10;
            col = input % 10;

            if(row < 0 || row > board.getSize() - 1 || col < 0 || col > board.getSize() - 1){
                System.out.println("Invalid mark position. Please choose a valid position: ");
                continue;
            }

            if(board.getMark(row, col) != Mark.BLANK){
                System.out.println("Mark position is already occupied. Please choose a valid position: ");
                continue;
            }

            break;
        }

        board.putMark(mark, row, col);


    }


}