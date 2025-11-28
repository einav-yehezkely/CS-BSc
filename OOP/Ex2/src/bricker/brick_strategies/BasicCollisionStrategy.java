package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy{

    private GameObjectCollection gameObjects;
    private Counter brickCounter;
    private final Brick[][] bricksGrid;

    public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
                                  Brick[][] bricksGrid) {
        this.gameObjects = gameObjects;
        this.brickCounter = brickCounter;
        this.bricksGrid = bricksGrid;
    }

    @Override
    public void onCollision(GameObject obj0, GameObject obj1) {
        boolean removed = gameObjects.removeGameObject(obj0, Layer.STATIC_OBJECTS);
        bricksGrid[((Brick)obj0).getRow()][((Brick)obj0).getCol()] = null;
        if(removed){
            brickCounter.decrement();
        }
    }
}
