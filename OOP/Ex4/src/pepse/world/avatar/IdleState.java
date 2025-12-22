package pepse.world.avatar;

import java.awt.event.KeyEvent;

/** Represents the idle movement state of an avatar. */
public class IdleState implements MovementState{
    /**
     * Handles discrete input events.
     * This method is responsible for state transitions and one-time actions
     * @param avatar the avatar object
     */
    @Override
    public void handleInput(Avatar avatar) {
        // input - jump
        if(avatar.onGround() && avatar.getEnergy().canJump() &&
                avatar.getInputListener().isKeyPressed(KeyEvent.VK_SPACE)){
            avatar.jump();
            avatar.getEnergy().consumeForJump();
            avatar.setState(StateFactory.newJumpState());
            return;
        }

        // input - run
        if ((avatar.getInputListener().isKeyPressed(KeyEvent.VK_LEFT) ||
                avatar.getInputListener().isKeyPressed(KeyEvent.VK_RIGHT))
                && avatar.getEnergy().canRun()) {

            avatar.setState(StateFactory.RUN_STATE);
        }

    }

    /**
     * Updates continuous behavior of the avatar while in this state.
     * @param avatar the avatar object
     * @param deltaTime time elapsed since last update
     */
    @Override
    public void update(Avatar avatar, float deltaTime) {
        avatar.setVelocityX(0);
        avatar.setIdleAnimation();
        if (avatar.onGround()) {
            avatar.getEnergy().recover();
        }
    }
}
