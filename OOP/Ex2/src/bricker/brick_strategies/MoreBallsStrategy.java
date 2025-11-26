package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class MoreBallsStrategy implements CollisionStrategy{
    private static final int PUCK_VELOCITY = 200;
    private static final float SIZE_FACTOR = 0.75f;
    private GameObjectCollection gameObjects;
    private Renderable puckImage;
    private Sound collisionSound;
    private Vector2 ballDimensions;
    private CollisionStrategy basicCollisionStrategy;
    private Vector2 windowDimensions;

    public MoreBallsStrategy(GameObjectCollection gameObjects, Counter brickCounter,
    Renderable puckImage, Sound collisionSound, Vector2 ballDimensions, Vector2 windowDimensions) {
        this.gameObjects = gameObjects;
        this.puckImage = puckImage;
        this.collisionSound = collisionSound;
        this.ballDimensions = ballDimensions;
        this.windowDimensions = windowDimensions;
        this.basicCollisionStrategy = new BasicCollisionStrategy(gameObjects, brickCounter);
    }

    @Override
    public void onCollision(GameObject brick, GameObject other) {
        basicCollisionStrategy.onCollision(brick, other);

        Vector2 center = brick.getCenter();
        Vector2 puckSize = ballDimensions.mult(SIZE_FACTOR);

        for (int i = 0; i < 2; i++) {
            Puck puck = new Puck(center.subtract(puckSize.mult(0.5f)), puckSize, puckImage, collisionSound, PUCK_VELOCITY, windowDimensions, gameObjects);
            gameObjects.addGameObject(puck);

        }
    }
}
