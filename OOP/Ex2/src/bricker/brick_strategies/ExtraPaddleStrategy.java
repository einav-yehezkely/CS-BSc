package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ExtraPaddleStrategy implements CollisionStrategy{
    private static final int EXTRA_PADDLE_HITS = 4;

    private GameObjectCollection gameObjects;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private Vector2 paddleDimensions;
    private Renderable paddleImage;
    private ExtraPaddle extraPaddle;
    private CollisionStrategy basicCollision;
    private StrategyFactory factory;

    public ExtraPaddleStrategy(GameObjectCollection gameObjects,
                               CollisionStrategy basicCollision,
                               UserInputListener inputListener,
                               Vector2 windowDimensions,
                               Vector2 paddleDimensions,
                               Renderable paddleImage,
                               StrategyFactory factory) {
        this.gameObjects = gameObjects;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddleDimensions = paddleDimensions;
        this.paddleImage = paddleImage;
        this.extraPaddle = null;
        this.factory = factory;
        this.basicCollision = basicCollision;
    }

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

    public void notifyExtraPaddleRemoved() {
        extraPaddle = null;
        factory.setGlobalExtraPaddle(null);
    }
}
