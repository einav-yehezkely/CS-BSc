package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.ExtraPaddle;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class StrategyFactory {

    private Counter brickCounter;
    private final Random rand = new Random();
    private final Brick[][] bricksGrid;
    private final BrickerGameManager gameManager;
    private ExtraPaddle globalExtraPaddle = null;
    private final CollisionStrategy basicStrategy;
    private final GameObjectCollection gameObjects;


    public StrategyFactory(Brick[][] bricksGrid, BrickerGameManager gameManager) {

        this.bricksGrid = bricksGrid;
        this.gameManager = gameManager;
        this.brickCounter = gameManager.getBrickCounter();
        this.gameObjects = gameManager.getGameObjects();
        this.basicStrategy = new BasicCollisionStrategy(gameObjects, brickCounter, bricksGrid);

    }

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

    public ExtraPaddle getGlobalExtraPaddle() {
        return globalExtraPaddle;
    }

    public void setGlobalExtraPaddle(ExtraPaddle paddle) {
        this.globalExtraPaddle = paddle;
    }
}
