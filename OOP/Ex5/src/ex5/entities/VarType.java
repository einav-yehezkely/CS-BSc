package ex5.entities;

/**
 * Enum representing variable types in the Sjava programming language.
 */
public enum VarType {
    INT,
    DOUBLE,
    STRING,
    BOOLEAN,
    CHAR;

    /**
     * Converts a string representation of a variable type to its corresponding VarType enum.
     *
     * @param typeStr The string representation of the variable type
     * @return The corresponding VarType enum
     * @throws VariableException If the type string is unknown
     */
    public static VarType fromString(String typeStr) throws VariableException {
        switch (typeStr) {
            case "int":
                return INT;
            case "double":
                return DOUBLE;
            case "String":
                return STRING;
            case "boolean":
                return BOOLEAN;
            case "char":
                return CHAR;
            default:
                throw new VariableException("Unknown variable type: " + typeStr);
        }
    }
}
