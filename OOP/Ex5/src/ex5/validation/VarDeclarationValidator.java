package ex5.validation;

import ex5.entities.Value;
import ex5.entities.VarType;
import ex5.entities.Variable;
import ex5.entities.VariableException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * VarDeclarationValidator is responsible for validating variable declaration lines in Sjava.
 */
public class VarDeclarationValidator {
    private static final Pattern VAR_DECLARATION_PATTERN =
            Pattern.compile("^\\s*(final\\s+)?(int|double|String|boolean|char)\\s+(.+);\\s*$");
    private static final Pattern VAR_NAME_PATTERN =
            Pattern.compile("^([a-zA-Z]\\w*|_[a-zA-Z0-9]\\w*)$");

    /**
     * Parses a variable declaration line and returns a list of Variable objects.
     *
     * @param line The variable declaration line
     * @return A list of Variable objects
     * @throws SyntaxException If the declaration is invalid
     */
    public static List<Variable> parseDeclaration(String line)
            throws SyntaxException {

        Matcher matcher = VAR_DECLARATION_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new SyntaxException("Invalid variable declaration");
        }

        boolean isFinal = matcher.group(1) != null;
        VarType type;
        try {
            type = VarType.fromString(matcher.group(2));
        } catch (VariableException e) {
            throw new SyntaxException(e.getMessage());
        }
        String rest = matcher.group(3);

        String[] parts = rest.split(",");
        List<Variable> variables = new ArrayList<>();

        for (String part : parts) {
            parseSingleVariable(part.trim(), type, isFinal, variables);
        }

        return variables;
    }

    /**
     * Parses a single variable declaration and adds it to the list of variables.
     *
     * @param txt The variable declaration text
     * @param type The variable type
     * @param isFinal Whether the variable is final
     * @param variables The list to add the variable to
     * @throws SyntaxException If the variable declaration is invalid
     */
    private static void parseSingleVariable(
            String txt, VarType type, boolean isFinal, List<Variable> variables)
            throws SyntaxException {
        if(txt.isEmpty()){
            throw new SyntaxException("Empty variable declaration");
        }
        String[] parts = txt.split("=", -1); // -1 to include trailing empty strings
        if(parts.length > 2){
            throw new SyntaxException("Invalid variable assignment");
        }
        String varName = parts[0].trim();
        if(!VAR_NAME_PATTERN.matcher(varName).matches()) {
            throw new SyntaxException("Invalid variable name: " + varName);
        }
        boolean initialized = false;
        if(parts.length == 2){
            initialized = true;
        }
        if(isFinal && !initialized){
            throw new SyntaxException("Final variable must be initialized: " + varName);
        }
        Variable variable = new Variable(varName, type, isFinal, initialized);
        if(initialized){
            String valueStr = parts[1].trim();
            if(valueStr.isEmpty()){
                throw new SyntaxException("Empty value for variable: " + varName);
            }
            Value value = new Value(valueStr, type);
            variable.assignValue(value);
        }

        variables.add(variable);

    }
}
