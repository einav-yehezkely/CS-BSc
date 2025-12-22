package pepse.world.avatar;

/** Interface representing the movement state of an avatar. */
public interface MovementState {
    /**
     * Handles discrete input events.
     * This method is responsible for state transitions and one-time actions
     * @param avatar the avatar object
     */
    void handleInput(Avatar avatar);
    /**
     * Updates continuous behavior of the avatar while in this state.
     * @param avatar the avatar object
     * @param deltaTime time elapsed since last update
     */
    void update(Avatar avatar, float deltaTime);
}
