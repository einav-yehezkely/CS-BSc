package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class BasicCollisionStrategy implements CollisionStrategy{

    private GameObjectCollection gameObjects;

    public BasicCollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public void onCollision(GameObject obj0, GameObject obj1) {
        gameObjects.removeGameObject(obj0, Layer.STATIC_OBJECTS);
    }
}
