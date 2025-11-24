package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy{

    private GameObjectCollection gameObjects;
    private final Counter brickCounter;

    public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter) {
        this.gameObjects = gameObjects;
        this.brickCounter = brickCounter;
    }

    @Override
    public void onCollision(GameObject obj0, GameObject obj1) {
        gameObjects.removeGameObject(obj0, Layer.STATIC_OBJECTS);
        brickCounter.decrement();
    }
}
