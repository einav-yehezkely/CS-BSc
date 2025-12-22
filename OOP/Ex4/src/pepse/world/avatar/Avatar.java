package pepse.world.avatar;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.avatar.Energy.Energy;
import pepse.world.trees.Fruit;

/** Avatar class representing the player character. */
public class Avatar extends GameObject {
    public static final Vector2 AVATAR_DIMENSIONS = new Vector2(50, 50);
    private static final float GRAVITY = 600;
    public static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float FRAME_TIME = 0.15f;

    private final UserInputListener inputListener;

    private MovementState state;
    private Energy energy;

    private AnimationRenderable idleAnimation;
    private AnimationRenderable runAnimation;
    private AnimationRenderable jumpAnimation;

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

        loadAnimations(imageReader);
        renderer().setRenderable(idleAnimation);
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

        if (other.getTag().equals("fruit")) {
            Fruit fruit = (Fruit) other;
            fruit.eat();
            energy.eatFruitRecover();
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

    /**
     * @return the avatar's energy
     */
    public Energy getEnergy() {
        return energy;
    }

    /**
     * Load the avatar's animations.
     * @param imageReader the image reader to load images
     */
    private void loadAnimations(ImageReader imageReader) {
        idleAnimation = new AnimationRenderable(
                new Renderable[]{
                        imageReader.readImage("assets/idle_0.png", true),
                        imageReader.readImage("assets/idle_1.png", true),
                        imageReader.readImage("assets/idle_2.png", true),
                        imageReader.readImage("assets/idle_3.png", true)
                },
                FRAME_TIME
        );

        runAnimation = new AnimationRenderable(
                new Renderable[]{
                        imageReader.readImage("assets/run_0.png", true),
                        imageReader.readImage("assets/run_1.png", true),
                        imageReader.readImage("assets/run_2.png", true),
                        imageReader.readImage("assets/run_3.png", true),
                        imageReader.readImage("assets/run_4.png", true),
                        imageReader.readImage("assets/run_5.png", true)
                },
                FRAME_TIME
        );

        jumpAnimation = new AnimationRenderable(
                new Renderable[]{
                        imageReader.readImage("assets/jump_0.png", true),
                        imageReader.readImage("assets/jump_1.png", true),
                        imageReader.readImage("assets/jump_2.png", true),
                        imageReader.readImage("assets/jump_3.png", true)
                },
                FRAME_TIME
        );
    }

    /**
     * Set the avatar's animation to idle.
     */
    public void setIdleAnimation() {
        renderer().setRenderable(idleAnimation);
    }

    /**
     * Set the avatar's animation to running.
     */
    public void setRunAnimation() {
        renderer().setRenderable(runAnimation);
    }

    /**
     * Set the avatar's animation to jumping.
     */
    public void setJumpAnimation() {
        renderer().setRenderable(jumpAnimation);
    }

    /**
     * Face the avatar's animation in the direction of movement.
     * @param vx the horizontal velocity
     */
    public void faceDirection(float vx) {
        if (vx != 0) {
            renderer().setIsFlippedHorizontally(vx < 0);
        }
    }

}
