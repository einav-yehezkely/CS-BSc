package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Interface for defining collision strategies between game objects.
 */
public interface CollisionStrategy {
    void onCollision(GameObject obj0, GameObject obj1);
}