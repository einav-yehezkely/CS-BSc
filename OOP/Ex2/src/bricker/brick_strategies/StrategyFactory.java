package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
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

    private Sound collisionSound;
    private Vector2 ballDimensions;
    private UserInputListener inputListener;
    private GameObjectCollection gameObjects;
    private Counter brickCounter;
    private Random rand;
    private Renderable puckImage;
    private Vector2 windowDimensions;
    private Vector2 paddleDimensions;
    private Renderable paddleImage;
    private ExtraPaddle globalExtraPaddle = null;


    public StrategyFactory(GameObjectCollection gameObjects, Counter brickCounter, ImageReader imageReader,
                           Sound collisionSound, Vector2 ballDimensions, Vector2 windowDimensions,
                           UserInputListener inputListener, Vector2 paddleDimensions) {
        this.collisionSound = collisionSound;
        this.ballDimensions = ballDimensions;
        this.inputListener = inputListener;
        this.paddleDimensions = paddleDimensions;
        this.rand = new Random();
        this.gameObjects = gameObjects;
        this.brickCounter = brickCounter;
        this.windowDimensions = windowDimensions;
        this.puckImage =
                imageReader.readImage("assets/mockBall.png", true);
        this.paddleImage =
                imageReader.readImage("assets/paddle.png", true);
    }

    public CollisionStrategy generateStrategy(int depthOfDouble){
        float p = rand.nextFloat();

        if (p<0.5f){
            return new BasicCollisionStrategy(gameObjects, brickCounter);
        }

        if(p<0.6f){
            return new MoreBallsStrategy(
                    gameObjects, brickCounter,
                    puckImage, collisionSound,
                    ballDimensions, windowDimensions
            );
        }

        if(p<0.7f){
            return new ExtraPaddleStrategy(
                    gameObjects, brickCounter, inputListener, windowDimensions,
                    paddleDimensions, paddleImage, this
                    );
        }

        if(p<0.8f){
            return new ExplosionStrategy();
        }

        if(p<0.9f){
            return new RestoreLifeStrategy();
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
                    gameObjects, brickCounter, puckImage, collisionSound, ballDimensions, windowDimensions
            );
        }

        if(p<0.4f){
            return new ExtraPaddleStrategy(gameObjects, brickCounter, inputListener, windowDimensions,
                    paddleDimensions, paddleImage, this
            );
        }

        if(p<0.6f){
            return new ExplosionStrategy();
        }

        if (p<0.8f){
            return new RestoreLifeStrategy();
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
                    gameObjects, brickCounter, puckImage, collisionSound, ballDimensions, windowDimensions
            );
        }

        if(p<0.5f){
            return new ExtraPaddleStrategy(gameObjects, brickCounter, inputListener, windowDimensions,
                    paddleDimensions, paddleImage, this
            );
        }

        if(p<0.75f){
            return new ExplosionStrategy();
        }

        return new RestoreLifeStrategy();
    }

    public ExtraPaddle getGlobalExtraPaddle() {
        return globalExtraPaddle;
    }

    public void setGlobalExtraPaddle(ExtraPaddle paddle) {
        this.globalExtraPaddle = paddle;
    }
}
