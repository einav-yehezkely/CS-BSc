class PlayerFactory{

    public PlayerFactory(){}

    public Player buildPlayer(String type){
        Player player = null;

        if(type.equals("human")){
            player = new HumanPlayer();
        } else if (type.equals("naive")) {
            player = new NaivePlayer();
        } else if (type.equals("smart")) {
            player = new SmartPlayer();
        } else if (type.equals("whatever")) {
            player = new WhateverPlayer();
        }
        return player;
    }
}