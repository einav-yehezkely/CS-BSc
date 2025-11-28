package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.ExtraPaddle;
import bricker.main.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.Random;

/**
 * A factory for generating collision strategies for bricks.
 */
public class StrategyFactory {

    private final Counter brickCounter;
    private final Random rand = new Random();
    private final Brick[][] bricksGrid;
    private final BrickerGameManager gameManager;
    private ExtraPaddle globalExtraPaddle = null;
    private final CollisionStrategy basicStrategy;
    private final GameObjectCollection gameObjects;


    /**
     * Constructor for StrategyFactory.
     * @param bricksGrid the grid of bricks
     * @param gameManager the game manager
     */
    public StrategyFactory(Brick[][] bricksGrid, BrickerGameManager gameManager) {

        this.bricksGrid = bricksGrid;
        this.gameManager = gameManager;
        this.brickCounter = gameManager.getBrickCounter();
        this.gameObjects = gameManager.getGameObjects();
        this.basicStrategy = new BasicCollisionStrategy(gameObjects, brickCounter, bricksGrid);

    }

    /**
     * Generates a collision strategy.
     * @param depthOfDouble the current depth of double strategies
     * @return a CollisionStrategy
     */
    public CollisionStrategy generateStrategy(int depthOfDouble){
        float p = rand.nextFloat();

        if (p<0.5f){
            return new BasicCollisionStrategy(gameObjects, brickCounter, bricksGrid);
        }

        if(p<0.6f){
            return new MoreBallsStrategy(
                    basicStrategy, gameManager
            );
        }

        if(p<0.7f){
            return new ExtraPaddleStrategy(
                    basicStrategy, this, gameManager
                    );
        }

        if(p<0.8f){
            return new ExplosionStrategy(gameManager, basicStrategy, bricksGrid);
        }

        if(p<0.9f){
            return new RestoreLifeStrategy(gameManager, basicStrategy);
        }

        if(depthOfDouble < 2){
            return new DoubleBehaviourStrategy(
                    generateSpecialStrategy(depthOfDouble + 1),
                    generateSpecialStrategy(depthOfDouble + 1)
            );
        }

        return generateSpecialStrategyNotDouble();
    }

    /**
     * Generates a special collision strategy, allowing for double strategies.
     * @param depthOfDouble the current depth of double strategies
     * @return a CollisionStrategy
     */
    private CollisionStrategy generateSpecialStrategy(int depthOfDouble){
        float p = rand.nextFloat();

        if(p<0.2f){
            return new MoreBallsStrategy(
                    basicStrategy, gameManager
            );
        }

        if(p<0.4f){
            return new ExtraPaddleStrategy(
                    basicStrategy, this, gameManager
            );
        }

        if(p<0.6f){
            return new ExplosionStrategy(gameManager, basicStrategy, bricksGrid);
        }

        if (p<0.8f){
            return new RestoreLifeStrategy(gameManager, basicStrategy);
        }

        if(depthOfDouble < 2){
            return new DoubleBehaviourStrategy(
                    generateSpecialStrategy(depthOfDouble + 1),
                    generateSpecialStrategy(depthOfDouble + 1)
            );
        }

        return generateSpecialStrategyNotDouble();
    }

    /**
     * Generates a special collision strategy, without double strategies.
     * @return a CollisionStrategy
     */
    private CollisionStrategy generateSpecialStrategyNotDouble(){
        float p = rand.nextFloat();

        if(p<0.25f){
            return new MoreBallsStrategy(
                    basicStrategy, gameManager
            );
        }

        if(p<0.5f){
            return new ExtraPaddleStrategy(
                    basicStrategy, this, gameManager
            );
        }

        if(p<0.75f){
            return new ExplosionStrategy(gameManager, basicStrategy, bricksGrid);
        }

        return new RestoreLifeStrategy(gameManager, basicStrategy);
    }

    /**
     * Gets the global extra paddle.
     * @return the global extra paddle
     */
    public ExtraPaddle getGlobalExtraPaddle() {
        return globalExtraPaddle;
    }

    /**
     * Sets the global extra paddle.
     * @param paddle the extra paddle to set
     */
    public void setGlobalExtraPaddle(ExtraPaddle paddle) {
        this.globalExtraPaddle = paddle;
    }
}
