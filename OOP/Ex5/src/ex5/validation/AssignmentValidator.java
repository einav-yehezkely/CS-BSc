package ex5.validation;

import ex5.entities.VarType;
import ex5.entities.Variable;
import ex5.scope.Scope;
import ex5.scope.ScopeException;

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
     * @param target The variable to which the value is being assigned
     * @param value    The value being assigned
     * @param scope    The current scope
     * @throws SyntaxException If the assignment is invalid
     */
    public static void validateAssignment(Variable target, String value, Scope scope)
            throws SyntaxException {
        if(target.isFinal() && target.isInitialized()){
            throw new SyntaxException("Cannot reassign to a final variable.");
        }
        if (matchType(value, target.getType())) {
            return;
        }
        Variable sourceVar;
        try{
            sourceVar = scope.lookupVar(value);
        } catch (ScopeException e){
            throw new SyntaxException("Variable " + value + " not declared.");
        }

        if (!sourceVar.isInitialized()) {
            throw new SyntaxException("Variable " + value + " not initialized.");
        }
        
        if(!valMatchSourceType(sourceVar.getType(), target.getType())){
            throw new SyntaxException("Cannot assign " + value +
                    " to variable of type " + target.getType());
        }
    }

    /**
     * Checks if the source type can be assigned to the target type.
     *
     * @param sourceType The type of the source variable
     * @param targetType The type of the target variable
     * @return True if the source type can be assigned to the target type, false otherwise
     */
    private static boolean valMatchSourceType(VarType sourceType, VarType targetType) {
        if(sourceType == targetType){
            return true;
        }
        if(sourceType == VarType.INT && targetType == VarType.DOUBLE){
            return true;
        }
        if (targetType == VarType.BOOLEAN &&
                (sourceType == VarType.INT || sourceType == VarType.DOUBLE)) return true;
        return false;
    }

    /**
     * Checks if the value matches the expected type.
     *
     * @param value The value to check
     * @param type  The expected variable type
     * @return True if the value matches the type, false otherwise
     */
    public static boolean matchType(String value, VarType type) {
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
