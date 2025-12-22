package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tree {
    private static final float MIN_HEIGHT = 120f;
    private static final float MAX_HEIGHT = 200f;

    private final List<GameObject> parts = new ArrayList<>();

    public Tree(Vector2 groundPosition, Random random) {
        float height =
                MIN_HEIGHT + random.nextFloat() * (MAX_HEIGHT - MIN_HEIGHT);
        Trunk trunk = new Trunk(groundPosition, height);
        parts.add(trunk);
        parts.addAll(Leaf.createLeaves(trunk, random));
//        parts.addAll(Fruit.createFruits(trunk, random));
    }

    public List<GameObject> getParts() {
        return parts;
    }
}
