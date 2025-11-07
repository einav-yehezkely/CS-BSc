/**
 * Factory class to create different types of Player objects.
 * @author einavye
 */
class PlayerFactory{
    /**
     * Constructor for PlayerFactory
     */
    public PlayerFactory(){}
    /**
     * Builds a Player object based on the given type.
     * @param type the type of player to create ("human", "naive", "smart", "whatever")
     * @return a Player object of the specified type, or null if the type is unrecognized
     */
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