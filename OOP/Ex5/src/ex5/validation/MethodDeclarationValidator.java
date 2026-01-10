package ex5.validation;

import ex5.entities.Method;
import ex5.entities.VarType;
import ex5.entities.Variable;
import ex5.entities.VariableException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MethodDeclarationValidator {
    private static final Pattern METHOD_DECLARATION_PATTERN = Pattern.compile(
            "^\\s*void\\s+([a-zA-Z]\\w*)\\s*\\(([^)]*)\\)\\s*\\{\\s*$"
    );

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

    public static List<Variable> parseParameters(String params) throws SyntaxException{
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

    private static Variable parseSingleParameter(String txt) throws SyntaxException {
        boolean isFinal = false;

        if(txt.startsWith("final ")){
            isFinal = true;
            txt = txt.substring(6).trim();
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

        return new Variable(name, type, isFinal, true);
    }

    public static boolean isMethodDeclaration(String line) {
        return METHOD_DECLARATION_PATTERN.matcher(line).matches();
    }
}
