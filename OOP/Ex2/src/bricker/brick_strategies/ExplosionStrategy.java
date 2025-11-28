package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.util.Counter;

public class ExplosionStrategy implements CollisionStrategy{
    private final Sound explosionSound;
    private final CollisionStrategy basicStrategy;
    private final Brick[][] brickGrid;


    public ExplosionStrategy(Sound explosionSound, CollisionStrategy basicStrategy,
                             Brick[][] brickGrid) {
        this.explosionSound = explosionSound;
        this.basicStrategy = basicStrategy;
        this.brickGrid = brickGrid;
    }

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
