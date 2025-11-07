/**
 * Enum representing the possible marks in a Tic-Tac-Toe game.
 */
enum Mark{BLANK, X, O;
    /**
     * Returns the string representation of the mark.
     * @return "X" for X, "O" for O, null for BLANK
     */
    public String toString(){
        switch(this){
            case X:
                return "X";
            case O:
                return "O";
            default:
                return null;
        }
    }
}