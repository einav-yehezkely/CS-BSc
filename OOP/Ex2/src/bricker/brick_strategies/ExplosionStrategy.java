package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.Sound;

/**
 * A collision strategy that causes an explosion effect,
 * destroying surrounding bricks upon collision.
 */
public class ExplosionStrategy implements CollisionStrategy{
    private final Sound explosionSound;
    private final CollisionStrategy basicStrategy;
    private final Brick[][] brickGrid;

    /**
     * Constructs an ExplosionStrategy.
     *
     * @param gameManager  Reference to the main game manager.
     * @param basicStrategy The basic collision strategy.
     * @param brickGrid    2D array representing the grid of bricks.
     */
    public ExplosionStrategy(BrickerGameManager gameManager,
                             CollisionStrategy basicStrategy,
                             Brick[][] brickGrid) {
        this.explosionSound =
                gameManager.getSoundReader().readSound("assets/explosion.wav");
        this.basicStrategy = basicStrategy;
        this.brickGrid = brickGrid;
    }

    /**
     * Handles collision by destroying the brick and its surrounding bricks.
     *
     * @param brickObj    The brick that was collided with.
     * @param colliderObj The object that collided with the brick - the ball/puck.
     */
    @Override
    public void onCollision(GameObject brickObj, GameObject colliderObj) {
        basicStrategy.onCollision(brickObj, colliderObj);
        explosionSound.play();

        Brick brick = (Brick) brickObj;
        int row = brick.getRow();
        int col = brick.getCol();

        // Destroy surrounding bricks
        if (row - 1 >= 0) {
            Brick up = brickGrid[row - 1][col];
            if (up != null)
                up.getStrategy().onCollision(up, colliderObj);
        }

        if (row + 1 < brickGrid.length) {
            Brick down = brickGrid[row + 1][col];
            if (down != null)
                down.getStrategy().onCollision(down, colliderObj);
        }

        if (col - 1 >= 0) {
            Brick left = brickGrid[row][col - 1];
            if (left != null)
                left.getStrategy().onCollision(left, colliderObj);
        }

        if (col + 1 < brickGrid[0].length) {
            Brick right = brickGrid[row][col + 1];
            if (right != null)
                right.getStrategy().onCollision(right, colliderObj);
        }
    }
}
