package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Interface for defining collision strategies between game objects.
 */
public interface CollisionStrategy {
    /**
     * Method to handle collision between two game objects.
     *
     * @param obj0 The first game object involved in the collision - the brick.
     * @param obj1 The second game object involved in the collision - the ball/puck.
     */
    void onCollision(GameObject obj0, GameObject obj1);
}