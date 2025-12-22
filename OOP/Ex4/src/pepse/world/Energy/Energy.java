package pepse.world.Energy;

public class Energy {
    private static final int MIN_ENERGY = 0;
    private static final int MAX_ENERGY = 100;
    private static final int RUN_COST = 2; // on every update
    private static final int JUMP_COST = 20; // one time
    private static final int DOUBLE_JUMP_COST = 50; // one time
    private static final int RECOVERY_RATE = 1; // per update

    private int energy;

    public Energy() {
        this.energy = MAX_ENERGY;
    }

    public boolean canRun() {
        return energy >= RUN_COST;
    }

    public boolean canJump() {
        return energy >= JUMP_COST;
    }

    public boolean canDoubleJump() {
        return energy >= DOUBLE_JUMP_COST;
    }

    /**
     * Sets energy to valid range.
     */
    private void enforceBounds() {
        energy = Math.max(0, Math.min(MAX_ENERGY, energy));
    }

    public void consumeForRun() {
        energy -= RUN_COST;
        enforceBounds();
    }

    public void consumeForJump() {
        energy -= JUMP_COST;
        enforceBounds();
    }

    public void consumeForDoubleJump() {
        energy -= DOUBLE_JUMP_COST;
        enforceBounds();
    }

    public void recover() {
        energy += RECOVERY_RATE;
        enforceBounds();
    }
    /**
     * @return the current energy level
     */
    public int getEnergy() {
        return energy;
    }

}

