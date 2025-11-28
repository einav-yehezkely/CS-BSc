package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A collision strategy that spawns two smaller balls (pucks) when a brick is hit.
 */
public class MoreBallsStrategy implements CollisionStrategy{
    private static final int PUCK_VELOCITY = 200;
    private static final float SIZE_FACTOR = 0.75f;
    private final GameObjectCollection gameObjects;
    private final Renderable puckImage;
    private final Sound collisionSound;
    private final Vector2 ballDimensions;
    private final CollisionStrategy basicCollisionStrategy;
    private final Vector2 windowDimensions;

    /**
     * Constructor for MoreBallsStrategy.
     *
     * @param basicCollisionStrategy The basic collision strategy.
     * @param gameManager            The game manager instance.
     */
    public MoreBallsStrategy(CollisionStrategy basicCollisionStrategy, BrickerGameManager gameManager) {
        this.gameObjects = gameManager.getGameObjects();
        this.puckImage =
            gameManager.getImageReader().readImage("assets/mockBall.png", true);
        this.collisionSound =
                gameManager.getCollisionSound();
        this.ballDimensions = gameManager.getBallDimensions();
        this.windowDimensions = gameManager.getWindowDimensions();
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles the collision event by spawning two smaller pucks.
     *
     * @param brick The brick that was hit.
     * @param other The other game object involved in the collision - the ball/puck.
     */
    @Override
    public void onCollision(GameObject brick, GameObject other) {
        basicCollisionStrategy.onCollision(brick, other);

        Vector2 center = brick.getCenter();
        Vector2 puckSize = ballDimensions.mult(SIZE_FACTOR);

        for (int i = 0; i < 2; i++) {
            Puck puck = new Puck(
                    center.subtract(puckSize.mult(0.5f)),
                    puckSize,
                    puckImage,
                    collisionSound,
                    PUCK_VELOCITY,
                    windowDimensions,
                    gameObjects);
            gameObjects.addGameObject(puck);

        }
    }
}
