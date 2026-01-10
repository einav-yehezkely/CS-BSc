package ex5.validation;

import ex5.entities.Method;
import ex5.entities.VarType;
import ex5.entities.Variable;
import ex5.entities.VariableException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * MethodDeclarationValidator is responsible for validating method declarations in Sjava.
 */
public class MethodDeclarationValidator {
    private static final Pattern METHOD_DECLARATION_PATTERN = Pattern.compile(
            "^\\s*void\\s+([a-zA-Z]\\w*)\\s*\\(([^)]*)\\)\\s*\\{\\s*$"
    );

    /**
     * Parses a method declaration line and returns a Method object.
     *
     * @param line The method declaration line
     * @return The Method object representing the method declaration
     * @throws SyntaxException If the method declaration is invalid
     */
    public static Method parseDeclaration(String line) throws SyntaxException {
        var matcher = METHOD_DECLARATION_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new SyntaxException("Invalid method declaration");
        }

        String methodName = matcher.group(1);
        String params = matcher.group(2).trim();
        List<Variable> paramsList = parseParameters(params);

        return new Method(methodName, paramsList);
    }

    /**
     * Parses the parameters string and returns a list of Variable objects.
     *
     * @param params The parameters string
     * @return A list of Variable objects representing the parameters
     * @throws SyntaxException If any parameter declaration is invalid
     */
    private static List<Variable> parseParameters(String params) throws SyntaxException{
        List<Variable> parameters = new ArrayList<>();

        if (params.isEmpty()){
            return parameters;
        }

        String[] parts = params.split(",");

        for(String part : parts){
            parameters.add(parseSingleParameter(part.trim()));
        }

        return parameters;
    }

    /**
     * Parses a single parameter declaration and returns a Variable object.
     *
     * @param txt The parameter declaration string
     * @return The Variable object representing the parameter
     * @throws SyntaxException If the parameter declaration is invalid
     */
    private static Variable parseSingleParameter(String txt) throws SyntaxException {
        boolean isFinal = false;

        if(txt.startsWith("final ")){
            isFinal = true;
            txt = txt.substring(6).trim();
        }

        try{
            VarDeclarationValidator.parseDeclaration(txt + ";");
        } catch (SyntaxException e){
            throw new SyntaxException(e.getMessage());
        }

        String[] parts = txt.split("\\s+");
        if (parts.length != 2) {
            throw new SyntaxException("Invalid parameter declaration");
        }

        VarType type;
        try {
            type = VarType.fromString(parts[0]);
        } catch (VariableException e) {
            throw new SyntaxException(e.getMessage());
        }

        String name = parts[1];

        return new Variable(name, type, isFinal, true, true);
    }

    /**
     * Checks if a line is a method declaration.
     *
     * @param line The line to check
     * @return true if the line is a method declaration, false otherwise
     */
    public static boolean isMethodDeclaration(String line) {
        return METHOD_DECLARATION_PATTERN.matcher(line).matches();
    }
}
