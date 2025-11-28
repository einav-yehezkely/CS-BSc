package bricker.brick_strategies;

import bricker.gameobjects.FallingHeart;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A collision strategy that, upon collision, removes the brick and spawns a FallingHeart
 * power-up that grants the player an extra life when collected by the paddle.
 */
public class RestoreLifeStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;
    private final CollisionStrategy basicStrategy;

    /**
     * Constructor for RestoreLifeStrategy.
     * @param gameManager the main game manager
     * @param basicStrategy the basic collision strategy to delegate brick removal
     */
    public RestoreLifeStrategy(BrickerGameManager gameManager, CollisionStrategy basicStrategy) {
        this.gameManager = gameManager;
        this.basicStrategy = basicStrategy;
    }


    /**
     * When a collision occurs, this method first invokes the basic collision strategy to handle
     * brick removal. Then, it spawns a FallingHeart object at the center of the brick
     * that was hit, allowing the player to collect it for an extra life.
     * @param obj0 the brick
     * @param obj1 the colliding object (e.g., ball/puck)
     */
    @Override
    public void onCollision(GameObject obj0, GameObject obj1) {
        basicStrategy.onCollision(obj0, obj1);
        Vector2 heartDimensions = gameManager.getHeartDimensions();
        Vector2 centerOfBrick = obj0.getCenter().subtract(heartDimensions.mult(0.5f));
        Renderable heartImage = gameManager.getImageReader().readImage(
                "assets/heart.png", true);
        FallingHeart heart = new FallingHeart(centerOfBrick,
                heartDimensions,
                heartImage,
                gameManager,
                gameManager.getMainPaddle());

        gameManager.getGameObjects().addGameObject(heart);

    }
}
