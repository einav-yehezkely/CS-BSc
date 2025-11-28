package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class FallingHeart extends GameObject {
    private static final int FALL_SPEED = 100;
    private final BrickerGameManager gameManager;
    private final Paddle mainPaddleReference;

    /**
     * Constructs a new Heart object (falling life).
     * @param topLeftCorner the position of the top-left corner
     * @param dimensions heart dimensions
     * @param renderable heart renderable
     * @param gameManager reference to the game manager
     * @param mainPaddleReference reference to the original Paddle object
     */
    public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        BrickerGameManager gameManager, Paddle mainPaddleReference) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.mainPaddleReference = mainPaddleReference;

        setVelocity(Vector2.DOWN.mult(FALL_SPEED));
    }

    /**
     * Enforces the rule: The Heart can only collide with the original paddle.
     * @param other the object potentially colliding with the heart.
     * @return true if the object is the main paddle (by reference), false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other == mainPaddleReference;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        gameManager.gainLife();

        gameManager.getGameObjects().removeGameObject(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float y = getCenter().y();

        if(y>gameManager.getWindowDimensions().y()){
            gameManager.getGameObjects().removeGameObject(this);
        }
    }
}
