package bricker.gameobjects;

import bricker.brick_strategies.ExtraPaddleStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ExtraPaddle extends Paddle{
    private GameObjectCollection gameObjects;
    private Counter hitCounter;
    private ExtraPaddleStrategy extraPaddleStrategy;

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
