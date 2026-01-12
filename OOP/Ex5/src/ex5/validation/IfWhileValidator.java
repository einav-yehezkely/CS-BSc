package ex5.validation;

import ex5.entities.VarType;
import ex5.entities.Variable;
import ex5.scope.Scope;
import ex5.scope.ScopeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IfWhileValidator is responsible for validating if and while statements in Sjava source files.
 */
public class IfWhileValidator {
    private static final Pattern IF_WHILE_PATTERN =
            Pattern.compile("^\\s*(if|while)\\s*\\((.*)\\)\\s*\\{\\s*$");
    private static final String INT_REGEX = "^[-+]?\\d+$";
    private static final String DOUBLE_REGEX =
            "^[-+]?\\d*\\.\\d+$|^[-+]?\\d+\\.\\d*$|^[-+]?\\d+$";

    /**
     * Validates if the given line is a valid if or while statement.
     * @param line The line to validate
     * @return True if the line is a valid if or while statement, false otherwise
     */
    public static boolean isIfWhileStatement(String line) {
        return IF_WHILE_PATTERN.matcher(line).matches();
    }

    /**
     * Validates the condition of an if or while statement.
     * @param line The line containing the if or while statement
     * @param scope The current scope
     * @throws SyntaxException If the condition is invalid
     */
    public static void validateCondition(String line, Scope scope) throws SyntaxException {
        Matcher matcher = IF_WHILE_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new SyntaxException("Invalid if/while statement");
        }

        String condition = matcher.group(2).trim();
        if (condition.isEmpty()) {
            throw new SyntaxException("Empty condition in if/while");
        }

        // split by || or &&
        String[] parts = condition.split("\\|\\||&&");

        for (String part : parts) {
            validateSinglePart(part.trim(), scope);
        }
    }

    /**
     * Validates a single part of the condition.
     * A part can be a boolean literal, a numeric literal, or a variable.
     */
    private static void validateSinglePart(String part, Scope scope) throws SyntaxException{
        if(part.isEmpty()){
            throw new SyntaxException("Empty condition part in if/while");
        }

        if(part.equals("true") || part.equals("false")){
            return;
        }

        if(part.matches(INT_REGEX) || part.matches(DOUBLE_REGEX)){
            return;
        }

        Variable var;
        try {
            var = scope.lookupVar(part);
        } catch (ScopeException e) {
            throw new SyntaxException("Variable " + part + " not declared");
        }

        if (!var.isInitialized()) {
            throw new SyntaxException("Variable " + part + " not initialized");
        }

        VarType type = var.getType();
        if (type != VarType.BOOLEAN &&
                type != VarType.INT &&
                type != VarType.DOUBLE) {
            throw new SyntaxException(
                    "Invalid type in condition: " + type
            );
        }
    }

}
