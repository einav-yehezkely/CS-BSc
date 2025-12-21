package pepse.world.avatar;

public class StateFactory {
    public static final MovementState IDLE_STATE = new IdleState();
    public static final MovementState RUN_STATE = new RunState();
    public static final MovementState JUMP_STATE = new JumpState();
}
