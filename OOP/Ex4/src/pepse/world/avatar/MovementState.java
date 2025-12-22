package pepse.world.avatar;

/** Interface representing the movement state of an avatar. */
public interface MovementState {
    /** Handle user input to potentially change the avatar's state.
     * @param avatar the avatar object
     */
    void handleInput(Avatar avatar);
    /** Update the avatar's state.
     * @param avatar the avatar object
     * @param deltaTime time elapsed since last update
     */
    void update(Avatar avatar, float deltaTime);
}
