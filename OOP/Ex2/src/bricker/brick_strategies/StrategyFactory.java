package bricker.brick_strategies;

public class StrategyFactory {
    public CollisionStrategy generateStrategy(){
        return new DoubleBehaviourStrategy();
    }
}
