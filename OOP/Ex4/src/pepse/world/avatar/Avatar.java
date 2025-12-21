package pepse.world.avatar;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/** Avatar class representing the player character. */
public class Avatar extends GameObject{
    public static final Vector2 AVATAR_DIMENSIONS = new Vector2(50, 50);
    private static final float GRAVITY = 600;
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;

    private final UserInputListener inputListener;

    /** Constructor for the Avatar class.
     * @param topLeftCorner the top-left corner of the avatar
     * @param inputListener the user input listener
     * @param imageReader the image reader to load the avatar image
     */
    public Avatar(Vector2 topLeftCorner,
                  UserInputListener inputListener,
                  ImageReader imageReader){

        super(topLeftCorner,
                AVATAR_DIMENSIONS,
                imageReader.readImage("assets\\idle_0.png", true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
    }

    /**
     * Update the avatar's velocity based on user input.
     * @param deltaTime time elapsed since last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            xVel -= VELOCITY_X;
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
            xVel += VELOCITY_X;
        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0)
            transform().setVelocityY(VELOCITY_Y);
    }

    /**
     * Handle collision with the ground to stop falling and snap on top.
     * @param other the other game object involved in the collision
     * @param collision the collision information
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        if (other.getTag().equals("ground") &&
                collision.getNormal().y() < 0) {

            // stop falling
            transform().setVelocityY(0);

            // snap avatar exactly on top of the block
            float groundTop = other.getTopLeftCorner().y();
            transform().setTopLeftCornerY(groundTop - getDimensions().y());
        }
    }
}
