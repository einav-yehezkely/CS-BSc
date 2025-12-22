package pepse.world.avatar;

import java.awt.event.KeyEvent;

public class RunState implements MovementState{
    @Override
    public void handleInput(Avatar avatar) {
        if(avatar.onGround() &&
                avatar.getInputListener().isKeyPressed(KeyEvent.VK_SPACE)){
            avatar.jump();
            avatar.setState(StateFactory.JUMP_STATE);
        }
    }

    @Override
    public void update(Avatar avatar, float deltaTime) {
        float vx = 0;
        if (avatar.getInputListener().isKeyPressed(KeyEvent.VK_LEFT))
            vx -= Avatar.VELOCITY_X;
        if (avatar.getInputListener().isKeyPressed(KeyEvent.VK_RIGHT))
            vx += Avatar.VELOCITY_X;
        avatar.setVelocityX(vx);
        if(vx == 0){
            avatar.setState(StateFactory.IDLE_STATE);
        }
    }
}
