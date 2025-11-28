package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A collision strategy that adds an extra paddle to the game upon collision.
 */
public class ExtraPaddleStrategy implements CollisionStrategy{
    private static final int EXTRA_PADDLE_HITS = 4;

    private final GameObjectCollection gameObjects;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final Vector2 paddleDimensions;
    private final Renderable paddleImage;
    private ExtraPaddle extraPaddle;
    private final CollisionStrategy basicCollision;
    private final StrategyFactory factory;

    /**
     * Constructor for ExtraPaddleStrategy.
     *
     * @param basicCollision The basic collision strategy.
     * @param factory The strategy factory for managing global state of the extra paddle.
     * @param gameManager The main game manager.
     */
    public ExtraPaddleStrategy(CollisionStrategy basicCollision,
                               StrategyFactory factory,
                               BrickerGameManager gameManager) {
        this.gameObjects = gameManager.getGameObjects();
        this.inputListener = gameManager.getInputListener();
        this.windowDimensions = gameManager.getWindowDimensions();
        this.paddleDimensions = gameManager.getPaddleDimensions();
        this.paddleImage = gameManager.getPaddleImage();
        this.extraPaddle = null;
        this.factory = factory;
        this.basicCollision = basicCollision;
    }

    /**
     * Handles collision by adding an extra paddle if one does not already exist.
     *
     * @param obj0 The first game object involved in the collision - a brick.
     * @param obj1 The second game object involved in the collision - the ball/puck.
     */
    @Override
    public void onCollision(GameObject obj0, GameObject obj1) {
        basicCollision.onCollision(obj0, obj1);
        if (factory.getGlobalExtraPaddle() != null){
            return;
        }

        Vector2 startPos = new Vector2(
                windowDimensions.x() / 2 - paddleDimensions.x() / 2,
                windowDimensions.y() / 2
        );

        Counter hitCounter = new Counter(EXTRA_PADDLE_HITS);

        extraPaddle = new ExtraPaddle(
                startPos,
                paddleDimensions,
                paddleImage,
                inputListener,
                windowDimensions,
                gameObjects,
                hitCounter,
                this
        );

        factory.setGlobalExtraPaddle(extraPaddle);

        gameObjects.addGameObject(extraPaddle);

    }

    /**
     * Notifies that the extra paddle has been removed from the game.
     */
    public void notifyExtraPaddleRemoved() {
        extraPaddle = null;
        factory.setGlobalExtraPaddle(null);
    }
}
