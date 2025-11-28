package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * A basic collision strategy that removes the brick upon collision
 * and decrements the brick counter.
 */
public class BasicCollisionStrategy implements CollisionStrategy{

    private final GameObjectCollection gameObjects;
    private final Counter brickCounter;
    private final Brick[][] bricksGrid;

    /**
     * Constructor for BasicCollisionStrategy.
     *
     * @param gameObjects  The collection of game objects in the game.
     * @param brickCounter A counter tracking the number of remaining bricks.
     * @param bricksGrid   A 2D array representing the grid of bricks.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
                                  Brick[][] bricksGrid) {
        this.gameObjects = gameObjects;
        this.brickCounter = brickCounter;
        this.bricksGrid = bricksGrid;
    }

    /**
     * Handles the collision between a brick and another game object.
     * Removes the brick from the game and updates the brick counter.
     * @param obj0 The brick involved in the collision.
     * @param obj1 The other game object involved in the collision - the ball/puck.
     */
    @Override
    public void onCollision(GameObject obj0, GameObject obj1) {
        boolean removed = gameObjects.removeGameObject(obj0, Layer.STATIC_OBJECTS);
        bricksGrid[((Brick)obj0).getRow()][((Brick)obj0).getCol()] = null;
        if(removed){
            brickCounter.decrement();
        }
    }
}
