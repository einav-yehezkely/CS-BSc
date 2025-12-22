package pepse.world.avatar.Energy;

import pepse.world.trees.Fruit;

/**
 * Manages the energy levels of the avatar, including consumption and recovery.
 */
public class Energy {
    private static final int MIN_ENERGY = 0;
    private static final int MAX_ENERGY = 100;
    private static final int RUN_COST = 2; // on every update
    private static final int JUMP_COST = 20; // one time
    private static final int DOUBLE_JUMP_COST = 50; // one time
    private static final int RECOVERY_RATE = 1; // per update

    private int energy;
    private EnergyChangeListener listener;

    /**
     * Initializes the energy to maximum level.
     */
    public Energy() {
        this.energy = MAX_ENERGY;
    }

    /**
     * Checks if there is enough energy to run.
     * @return true if enough energy to run, false otherwise
     */
    public boolean canRun() {
        return energy >= RUN_COST;
    }

    /**
     * Checks if there is enough energy to jump.
     * @return true if enough energy to jump, false otherwise
     */
    public boolean canJump() {
        return energy >= JUMP_COST;
    }

    /**
     * Checks if there is enough energy to double jump.
     * @return true if enough energy to double jump, false otherwise
     */
    public boolean canDoubleJump() {
        return energy >= DOUBLE_JUMP_COST;
    }

    /**
     * Consumes energy for running.
     */
    public void consumeForRun() {
        updateEnergy(energy - RUN_COST);
    }

    /**
     * Consumes energy for jumping.
     */
    public void consumeForJump() {
        updateEnergy(energy - JUMP_COST);
    }

    /**
     * Consumes energy for double jumping.
     */
    public void consumeForDoubleJump() {
        updateEnergy(energy - DOUBLE_JUMP_COST);
    }

    /**
     * Recovers energy over time on Idle state.
     */
    public void recover() {
        updateEnergy(energy + RECOVERY_RATE);
    }

    /**
     * Recovers energy by eating a fruit.
     */
    public void eatFruitRecover(){
        updateEnergy(energy + Fruit.ENERGY_GAIN);
    }

    /**
     * Sets the listener for energy change events.
     * @param listener the listener to set
     */
    public void setEnergyChangeListener(EnergyChangeListener listener) {
        this.listener = listener;
        listener.onEnergyChange(energy);
    }

    /**
     * Updates the energy level and notifies the listener if there is a change.
     * @param newEnergy the new energy level
     */
    private void updateEnergy(int newEnergy) {
        int oldEnergy = energy;
        energy = Math.max(MIN_ENERGY, Math.min(MAX_ENERGY, newEnergy));

        if (listener != null && energy != oldEnergy) {
            listener.onEnergyChange(energy);
        }
    }

}

