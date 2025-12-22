package pepse.world.avatar;

import java.awt.event.KeyEvent;

public class IdleState implements MovementState{
    @Override
    public void handleInput(Avatar avatar) {
        // input - jump
        if(avatar.onGround() &&
                avatar.getInputListener().isKeyPressed(KeyEvent.VK_SPACE)){
            avatar.jump();
            avatar.setState(StateFactory.JUMP_STATE);
            return;
        }

        if(avatar.getInputListener().isKeyPressed(KeyEvent.VK_LEFT) ||
                avatar.getInputListener().isKeyPressed(KeyEvent.VK_RIGHT)){
            avatar.setState(StateFactory.RUN_STATE);
        }

    }

    @Override
    public void update(Avatar avatar, float deltaTime) {
        avatar.setVelocityX(0);
    }
}
