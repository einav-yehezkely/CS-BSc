package pepse.world.avatar.Energy;

/**
 * A listener interface for energy change events.
 */
@FunctionalInterface
public interface EnergyChangeListener {
    void onEnergyChange(int newEnergy);
}
