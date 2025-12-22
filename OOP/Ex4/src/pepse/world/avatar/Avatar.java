package pepse.world.avatar;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import pepse.world.Energy.Energy;

/** Avatar class representing the player character. */
public class Avatar extends GameObject {
    public static final Vector2 AVATAR_DIMENSIONS = new Vector2(50, 50);
    private static final float GRAVITY = 600;
    public static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;

    private final UserInputListener inputListener;

    private MovementState state;
    private Energy energy;

    /**
     * Constructor for the Avatar class.
     *
     * @param topLeftCorner the top-left corner of the avatar
     * @param inputListener the user input listener
     * @param imageReader   the image reader to load the avatar image
     */
    public Avatar(Vector2 topLeftCorner,
                  UserInputListener inputListener,
                  ImageReader imageReader) {

        super(topLeftCorner,
                AVATAR_DIMENSIONS,
                imageReader.readImage("assets\\idle_0.png", true));

        this.inputListener = inputListener;
        this.state = StateFactory.IDLE_STATE;
        this.energy = new Energy();

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);

        this.setTag("avatar");
    }

    /**
     * Update the avatar's velocity based on user input.
     *
     * @param deltaTime time elapsed since last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        state.handleInput(this);
        state.update(this, deltaTime);
    }

    /**
     * Handle collision with the ground to stop falling and snap on top.
     *
     * @param other     the other game object involved in the collision
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

    /**
     * @return true if the avatar is on the ground
     */
    public boolean onGround() {
        return getVelocity().y() == 0;
    }

    /**
     * @return user input listener
     */
    public UserInputListener getInputListener() {
        return inputListener;
    }

    /**
     * set the avatar's movement state
     * @param newState the new movement state
     */
    public void setState(MovementState newState){
        this.state = newState;
    }

    /**
     * Make the avatar jump by setting its vertical velocity.
     */
    public void jump(){
        transform().setVelocityY(VELOCITY_Y);
    }

    /**
     * Set the avatar's horizontal velocity.
     * @param vx the new horizontal velocity
     */
    public void setVelocityX(float vx){
        transform().setVelocityX(vx);
    }

    public Energy getEnergy() {
        return energy;
    }

}
