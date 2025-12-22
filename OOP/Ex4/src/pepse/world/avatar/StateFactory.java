package pepse.world.avatar;

public class StateFactory {
    public static final MovementState IDLE_STATE = new IdleState();
    public static final MovementState RUN_STATE = new RunState();

    /** Create a new instance of JumpState.
     * needed because JumpState has a field - internal state (double jump used)
     * @return a new JumpState instance
     */
    public static MovementState newJumpState() {
        return new JumpState();
    }
}
