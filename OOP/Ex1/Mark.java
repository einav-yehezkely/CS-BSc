enum Mark{BLANK, X, O;
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