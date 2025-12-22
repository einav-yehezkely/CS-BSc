package pepse.world.avatar;

import java.awt.event.KeyEvent;

/** Represents the running movement state of an avatar. */
public class RunState implements MovementState{
    /**
     * Handles discrete input events.
     * This method is responsible for state transitions and one-time actions
     * @param avatar the avatar object
     */
    @Override
    public void handleInput(Avatar avatar) {
        if(avatar.onGround() && avatar.getEnergy().canJump() &&
                avatar.getInputListener().isKeyPressed(KeyEvent.VK_SPACE)){
            avatar.jump();
            avatar.getEnergy().consumeForJump();
            avatar.setState(StateFactory.newJumpState());
            return;
        }
    }

    /**
     * Updates continuous behavior of the avatar while in this state.
     * @param avatar the avatar object
     * @param deltaTime time elapsed since last update
     */
    @Override
    public void update(Avatar avatar, float deltaTime) {
        float vx = 0;
        if(avatar.getEnergy().canRun()){
            if (avatar.getInputListener().isKeyPressed(KeyEvent.VK_LEFT))
                vx -= Avatar.VELOCITY_X;
            if (avatar.getInputListener().isKeyPressed(KeyEvent.VK_RIGHT))
                vx += Avatar.VELOCITY_X;
            avatar.setVelocityX(vx);
            avatar.faceDirection(vx);
            if(vx!=0){
                avatar.setRunAnimation();
                avatar.getEnergy().consumeForRun();
            }
            else{
                avatar.setVelocityX(0);
                avatar.setState(StateFactory.IDLE_STATE);
            }
        }
        if (vx == 0) {
            avatar.setState(StateFactory.IDLE_STATE);
        }
    }
}
