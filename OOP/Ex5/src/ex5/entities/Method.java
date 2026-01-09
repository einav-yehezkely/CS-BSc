package ex5.entities;

import java.util.List;

/**
 * Represents a method with its name, return type, and parameters.
 */
public class Method {

    private final String name;
    private final List<Variable> parameters;

    /**
     * Constructs a Method with the specified name, return type, and parameters.
     *
     * @param name The name of the method
     * @param parameters The list of parameters of the method
     */
    public Method(String name, List<Variable> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * Gets the name of the method.
     *
     * @return The name of the method
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of parameters of the method.
     *
     * @return The list of parameters
     */
    public List<Variable> getParameters() {
        return parameters;
    }
}
