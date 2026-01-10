package ex5.validation;

import ex5.entities.VarType;
import ex5.entities.Variable;

/**
 * AssignmentValidator is responsible for validating variable assignments in Sjava.
 */
public class AssignmentValidator {
    private static final String INT_REGEX = "^[-+]?\\d+$";
    private static final String DOUBLE_REGEX =
            "^[-+]?\\d*\\.\\d+$|^[-+]?\\d+\\.\\d*$|^[-+]?\\d+$";

    /**
     * Validates the assignment of a value to a variable.
     *
     * @param variable The variable to which the value is being assigned
     * @param value    The value being assigned
     * @throws SyntaxException If the assignment is invalid
     */
    public static void validateAssignment(Variable variable, String value)
            throws SyntaxException {
        if (!matchType(value, variable.getType())) {
            throw new SyntaxException("Cannot assign value " +
                    value + " to variable of type " + variable.getType());
        }
        if(variable.isFinal() && variable.isInitialized()){
            throw new SyntaxException("Cannot reassign to a final variable.");
        }
    }


    /**
     * Checks if the value matches the expected type.
     *
     * @param value The value to check
     * @param type  The expected variable type
     * @return True if the value matches the type, false otherwise
     */
    private static boolean matchType(String value, VarType type) {
        switch (type) {
            case INT:
                return value.matches(INT_REGEX);
            case DOUBLE:
                return value.matches(DOUBLE_REGEX);
            case STRING:
                return value.matches("^\".*\"$");
            case BOOLEAN:
                return value.equals("true") ||
                        value.equals("false") ||
                        value.matches(INT_REGEX) ||
                        value.matches(DOUBLE_REGEX);
            case CHAR:
                return value.matches("^'.'$");
            default:
                return false;
        }
    }
}
