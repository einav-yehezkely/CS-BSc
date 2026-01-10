package ex5.entities;

/**
 * Represents a variable with its name, type, finality, initialization status, and value.
 */
public class Variable {

    private final String name;
    private final VarType type;
    private final boolean isFinal;
    private boolean isInitialized;
    private Value value;
    private final boolean isGlobal;
    /**
     * Constructs a Variable with the specified name, type, and finality.
     *
     * @param name    The name of the variable
     * @param type    The type of the variable
     * @param isFinal Whether the variable is final
     */
    public Variable(
            String name, VarType type, boolean isFinal, boolean isInitialized, boolean isGlobal
    ) {
        this.name = name;
        this.type = type;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
        this.value = null;
        this.isGlobal = isGlobal;
    }

    /**
     * Gets the name of the variable.
     * @return The name of the variable
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the variable.
     * @return The type of the variable
     */
    public VarType getType() {
        return type;
    }

    /**
     * Checks if the variable is final.
     * @return true if the variable is final, false otherwise
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Checks if the variable is initialized.
     * @return true if the variable is initialized, false otherwise
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Sets the value of the variable and marks it as initialized.
     * @param value The value to set
     */
    public void assignValue(Value value) {
        this.value = value;
        if (!isGlobal) {
            isInitialized = true;
        }
    }

    /**
     * Gets the value of the variable.
     * @return The value of the variable
     */
    public Value getValue() {
        return value;
    }


}
