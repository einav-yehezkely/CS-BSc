package pepse.world.avatar;

import java.awt.event.KeyEvent;

/** Represents the jumping movement state of an avatar. */
public class JumpState implements MovementState{
    private int jumpsLeft = 1;
    private boolean spaceReleased = false;

    /**
     * Handles discrete input events.
     * This method is responsible for state transitions and one-time actions
     * @param avatar the avatar object
     */
    public void handleInput(Avatar avatar) {
        boolean spacePressed =
                avatar.getInputListener().isKeyPressed(KeyEvent.VK_SPACE);

        // detect release
        if (!spacePressed) {
            spaceReleased = true;
        }

        // second jump - only after release
        if (spacePressed && spaceReleased && jumpsLeft > 0 &&
                avatar.getEnergy().canDoubleJump()) {
            avatar.jump();
            avatar.getEnergy().consumeForDoubleJump();
            jumpsLeft--;
            spaceReleased = false;
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
        if (avatar.getInputListener().isKeyPressed(KeyEvent.VK_LEFT))
            vx -= Avatar.VELOCITY_X;
        if (avatar.getInputListener().isKeyPressed(KeyEvent.VK_RIGHT))
            vx += Avatar.VELOCITY_X;
        avatar.setVelocityX(vx);

        if (avatar.onGround()) {
            if(avatar.getEnergy().canRun() && vx != 0){
                avatar.setState(StateFactory.RUN_STATE);
            }
            else {
                avatar.setState(StateFactory.IDLE_STATE);
            }
        }
    }
}
