package bricker.brick_strategies;

import danogl.GameObject;

public class DoubleBehaviourStrategy implements CollisionStrategy{
    CollisionStrategy s1;
    CollisionStrategy s2;

    public DoubleBehaviourStrategy(CollisionStrategy s1, CollisionStrategy s2){
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public void onCollision(GameObject obj0, GameObject obj1) {
        s1.onCollision(obj0, obj1);
        s2.onCollision(obj0, obj1);
    }
}
