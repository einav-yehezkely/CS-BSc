package bricker.gameobjects;

import bricker.brick_strategies.ExtraPaddleStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents an extra paddle that can be added to the game.
 * The extra paddle will be removed after a certain number of hits.
 */
public class ExtraPaddle extends Paddle{
    private final GameObjectCollection gameObjects;
    private final Counter hitCounter;
    private final ExtraPaddleStrategy extraPaddleStrategy;

    /**
     * Constructs a new ExtraPaddle object.
     *
     * @param topLeftCorner   The position of the top-left corner.
     * @param dimensions      The dimensions of the paddle.
     * @param renderable      The loaded image renderable.
     * @param inputListener   The user input listener.
     * @param windowDimensions The dimensions of the game window.
     * @param gameObjects     Reference to the game object collection.
     * @param hitCounter      Counter for the number of hits before removal.
     * @param extraPaddleStrategy Strategy to notify when the paddle is removed.
     */
    public ExtraPaddle(
            Vector2 topLeftCorner,
            Vector2 dimensions,
            Renderable renderable,
            UserInputListener inputListener,
            Vector2 windowDimensions,
            GameObjectCollection gameObjects,
            Counter hitCounter,
            ExtraPaddleStrategy extraPaddleStrategy) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.gameObjects = gameObjects;
        this.hitCounter = hitCounter;
        this.extraPaddleStrategy = extraPaddleStrategy;
    }

    /**
     * Handles collision when the extra paddle is hit by a ball.
     * Decrements the hit counter and removes the paddle if the counter reaches zero.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!other.getTag().equals("ball"))
            return;
        hitCounter.decrement();

        if(hitCounter.value()<=0){
            gameObjects.removeGameObject(this);
            extraPaddleStrategy.notifyExtraPaddleRemoved();
        }
    }
}
