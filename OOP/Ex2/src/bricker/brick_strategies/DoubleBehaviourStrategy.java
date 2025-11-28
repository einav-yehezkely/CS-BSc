package bricker.brick_strategies;

import danogl.GameObject;

/**
 * A collision strategy that combines two other strategies,
 * executing both when a collision occurs.
 */
public class DoubleBehaviourStrategy implements CollisionStrategy{
    CollisionStrategy s1;
    CollisionStrategy s2;

    /**
     * Constructs a DoubleBehaviourStrategy with two given strategies.
     * @param s1 The first collision strategy.
     * @param s2 The second collision strategy.
     */
    public DoubleBehaviourStrategy(CollisionStrategy s1, CollisionStrategy s2){
        this.s1 = s1;
        this.s2 = s2;
    }

    /**
     * Handles collision by invoking both strategies.
     * @param obj0 the brick object
     * @param obj1 the ball/puck object
     */
    @Override
    public void onCollision(GameObject obj0, GameObject obj1) {
        s1.onCollision(obj0, obj1);
        s2.onCollision(obj0, obj1);
    }
}
