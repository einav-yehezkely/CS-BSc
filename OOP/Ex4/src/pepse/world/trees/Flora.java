package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flora {
    private static final float TREE_CHANCE = 0.2f;
    private final Terrain terrain;
    private final Random random;

    public Flora(Terrain terrain, int seed) {
        this.terrain = terrain;
        this.random = new Random(seed);
    }

    public List<GameObject> createInRange(int minX, int maxX) {
        List<GameObject> objects = new ArrayList<>();

        int startX = (int) Math.floor((float) minX / Block.SIZE) * Block.SIZE;
        int endX = (int) Math.ceil((float) maxX / Block.SIZE) * Block.SIZE;

        for (int x = startX; x < endX; x += Block.SIZE*2) {
            if (random.nextFloat() < TREE_CHANCE) {
                float alignedX = x + Block.SIZE / 2f; // center of the block
                // Get the ground height and align it to block size
                float rawGroundY = terrain.groundHeightAt(x);
                float alignedY =
                        (float)(Math.floor(rawGroundY / Block.SIZE) * Block.SIZE) + 2;
                Tree tree = new Tree(new Vector2(alignedX, alignedY), random);
                objects.addAll(tree.getParts());
            }
        }

        return objects;
    }
}