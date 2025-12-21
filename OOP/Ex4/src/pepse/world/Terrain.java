package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import pepse.util.ColorSupplier;

/**
 * Terrain class that generates terrain blocks.
 */
public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;

    private final float groundHeightAtX0;

    /** Constructor
     * @param windowDimensions the dimensions of the window
     * @param seed seed for random terrain generation
     */
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.y() * (2f/3f);
    }

    /** Get the ground height at a given x coordinate
     * @param x the x coordinate
     * @return the ground height at the given x coordinate
     */
    public float groundHeightAt(float x) { return groundHeightAtX0; }   // TODO: improve terrain variation

    /** Create terrain blocks in a given x range
     * @param minX the minimum x coordinate
     * @param maxX the maximum x coordinate
     * @return a list of terrain blocks in the given x range
     */
    public List<Block> createInRange(int minX, int maxX) {
        // Empty list to hold the blocks
        List<Block> blocks = new ArrayList<>();

        // Calculate the start and end x coordinates aligned to block size
        int startX = (int) Math.floor((float) minX / Block.SIZE) * Block.SIZE;
        int endX = (int) Math.ceil((float) maxX / Block.SIZE) * Block.SIZE;

        // Iterate over the x range and create blocks
        for (int x = startX; x <= endX; x += Block.SIZE) {
            // Get the ground height at the current x coordinate
            float groundY = groundHeightAt(x);
            // Calculate the top block y coordinate aligned to block size
            int topBlockY =
                    (int) Math.floor(groundY / Block.SIZE) * Block.SIZE;

            // Create blocks from the top block y coordinate down to the terrain depth
            for (int i = 0; i < TERRAIN_DEPTH; i++) {
                Vector2 topLeft = new Vector2(x, topBlockY + i * Block.SIZE);
                Block block = new Block(
                        topLeft,
                        new RectangleRenderable(
                                ColorSupplier.approximateColor(BASE_GROUND_COLOR)
                        )
                );
                block.setTag("ground");
                blocks.add(block);
            }
        }
        return blocks;
    }
}
