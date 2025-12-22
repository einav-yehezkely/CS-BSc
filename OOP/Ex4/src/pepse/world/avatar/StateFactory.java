package pepse.world.avatar;

/** Factory class for creating movement state instances for an avatar. */
public class StateFactory {
    public static final MovementState IDLE_STATE = new IdleState();
    public static final MovementState RUN_STATE = new RunState();

    /** Create a new instance of JumpState.
     * needed because JumpState has fields - internal states (jumpsLeft and spaceReleased)
     * @return a new JumpState instance
     */
    public static MovementState newJumpState() {
        return new JumpState();
    }
}
