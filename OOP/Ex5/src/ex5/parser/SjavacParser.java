package ex5.parser;

import ex5.entities.Method;
import ex5.entities.Value;
import ex5.entities.VarType;
import ex5.entities.Variable;
import ex5.scope.Scope;
import ex5.scope.ScopeException;
import ex5.validation.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SjavacParser is responsible for parsing and validating Sjava source files.
 */
public class SjavacParser {
    private static final Pattern METHOD_CALL_PATTERN =
            Pattern.compile("^\\s*([a-zA-Z]\\w*)\\s*\\(([^)]*)\\)\\s*;\\s*$");

    private final String path;
    private int openBrackets;
    private final Scope globalScope;
    private Scope currentScope;
    private boolean inMethod;
    private int methodDepth;
    private String lastStatementInCurrentMethod;
    private final Map<String, Method> methods;


    /**
     * Constructs a SjavacParser for the specified file path.
     *
     * @param path The path to the Sjava source file
     */
    public SjavacParser(String path) {
        this.path = path;
        this.globalScope = new Scope(null);
        this.currentScope = globalScope;
        this.inMethod = false;
        this.methodDepth = 0;
        this.openBrackets = 0;
        this.methods = new HashMap<>();
    }

    /**
     * Validates the Sjava source file.
     *
     * @throws ParserException If the file contains syntax errors
     */
    public void validateSjavaFile() throws ParserException{
        List<String> lines = new ArrayList<>();

        try (FileReader fileReader = new FileReader(this.path);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            collectMethods(lines);
            resetParserState();
            int lineNumber = 1;
            for (String line_ : lines) {
                validateLine(line_, lineNumber);
                lineNumber++;
            }
            if(openBrackets > 0){
                throw new ParserException("Unmatched opening brackets {");
            }

        } catch (FileNotFoundException e) {
            throw new ParserException("File not found: " + this.path);
        } catch (IOException e) {
            throw new ParserException("IO error while reading file: " + this.path);
        } catch (ParserException e) {
            throw new ParserException(e.getMessage());
        }
    }

    /**
     * Validates a single line of the Sjava source file.
     *
     * @param line       The line to validate
     * @param lineNumber The line number in the source file
     * @throws ParserException If the line contains syntax errors
     */
    private void validateLine(String line, int lineNumber) throws ParserException{
        LineType lineType = ClassifyLine.classify(line);

        switch (lineType) {
            case EMPTY:
                return;
            case COMMENT:
                return;
            case OPEN_BRACKET:
                if (!inMethod && MethodDeclarationValidator.isMethodDeclaration(line.trim())) {
                    handleMethod(line);
                    return;
                }

                if (IfWhileValidator.isIfWhileStatement(line.trim())) {
                    handleIfWhile(line);
                    return;
                }

                throw new ParserException("Invalid block opening");

            case CLOSE_BRACKET:
                openBrackets--;
                if(openBrackets < 0){
                    throw new ParserException("Unmatched closing bracket } at line " + lineNumber);
                }
                currentScope = currentScope.getParentScope();
                if(inMethod && openBrackets == 0){
                    if(!lastStatementInCurrentMethod.equals("return;")){
                        throw new ParserException("Missing return statement at line " + lineNumber);
                    }
                    lastStatementInCurrentMethod = null;
                }
                if (methodDepth > 0 && openBrackets == 0) {
                    methodDepth--;
                    inMethod = false;
                }
                return;
            case CODE:
                if(inMethod){
                    lastStatementInCurrentMethod = line.trim();
                }
                handleCodeLine(line, lineNumber);

        }
    }

    private void handleCodeLine(String line, int lineNumber) throws ParserException {
        String trimmedLine = line.trim();
        if (!trimmedLine.endsWith(";")) {
            throw new ParserException("Missing ; at line " + lineNumber);
        }
        // check for return statement
        if (trimmedLine.equals("return;")) {
            if (!inMethod) {
                throw new ParserException("return not allowed outside method");
            }
            return;
        }
        // check for method call
        Matcher matcher = METHOD_CALL_PATTERN.matcher(trimmedLine);
        if (matcher.matches()) {
            if (!inMethod) {
                throw new ParserException("Method call not allowed outside method");
            }

            String methodName = matcher.group(1);
            String argsStr = matcher.group(2).trim();
            Method method = methods.get(methodName);

            if (!methods.containsKey(methodName)) {
                throw new ParserException("Method " + methodName + " not declared");
            }
            validateMethodCall(method, argsStr, currentScope);

            lastStatementInCurrentMethod = trimmedLine;
            return;
        }
        // try declaration
        if (VarDeclarationValidator.isDeclaration(trimmedLine)) {
            try {
                List<Variable> variables =
                        VarDeclarationValidator.parseDeclaration(trimmedLine);
                boolean isGlobal = !inMethod;

                for (Variable var : variables) {
                    Variable realVar = new Variable(
                            var.getName(),
                            var.getType(),
                            var.isFinal(),
                            var.isInitialized(),
                            isGlobal
                    );

                    if (var.isInitialized()) {
                        AssignmentValidator.validateRhs(
                                var.getValue().getRawValue(),
                                realVar.getType(),
                                currentScope
                        );
                    }

                    if (inMethod && currentScope.isDeclaredInAnyParent(var.getName())) {
                        throw new ParserException(
                                "Variable " + var.getName() + " already declared."
                        );
                    }

                    currentScope.declareVar(realVar);

                }
                return;
            } catch (SyntaxException | ScopeException e) {
                throw new ParserException(e.getMessage());
            }
        }

        String content = trimmedLine.substring(0, trimmedLine.length() - 1); // remove ;
        String[] parts = content.split("=", 2);

        if (parts.length != 2) {
            throw new ParserException("Invalid statement at line " + lineNumber);
        }

        String varName = parts[0].trim();
        String varValue = parts[1].trim();

        try {
            Variable var = currentScope.lookupVar(varName);

            AssignmentValidator.validateAssignment(var, varValue, currentScope);

            var.assignValue(new Value(varValue, var.getType()));

        } catch (ScopeException | SyntaxException e) {
            throw new ParserException(e.getMessage());
        }
    }

    private void validateMethodCall(Method method, String argsStr, Scope scope) throws ParserException{
        List<Variable> params = method.getParameters();
        String[] args;

        if (argsStr.isEmpty()) {
            args = new String[0];
        } else {
            args = argsStr.split(",");
        }

        if (args.length != params.size()) {
            throw new ParserException("Wrong number of arguments in call to " +
                    method.getName());
        }

        for (int i = 0; i < args.length; i++) {
            String arg = args[i].trim();
            VarType paramType = params.get(i).getType();

            validateSingleArgument(arg, paramType, scope);
        }
    }

    private void validateSingleArgument(String arg, VarType paramType, Scope scope) throws ParserException{
        if(AssignmentValidator.matchType(arg, paramType)){
            return;
        }

        Variable var;
        try {
            var = scope.lookupVar(arg);
        } catch (ScopeException e) {
            throw new ParserException("Variable " + arg + " not declared");
        }

        if (!var.isInitialized()) {
            throw new ParserException("Variable " + arg + " not initialized");
        }

        if (!typesMatch(var.getType(), paramType)) {
            throw new ParserException("Types don't match in method call argument: " + arg);
        }
    }

    private boolean typesMatch(VarType source, VarType target) {
        if (source == target) return true;
        if (target == VarType.DOUBLE && source == VarType.INT) return true;
        if (target == VarType.BOOLEAN &&
                (source == VarType.INT || source == VarType.DOUBLE)) return true;
        return false;
    }

    private void handleMethod(String line) throws ParserException{
        lastStatementInCurrentMethod = null;
        String trimmedLine = line.trim();
        // check if method declaration
        if (!inMethod && MethodDeclarationValidator.isMethodDeclaration(trimmedLine)) {
            try {
                Method method =
                        MethodDeclarationValidator.parseDeclaration(trimmedLine);

                methodDepth++;
                inMethod = true;
                currentScope = new Scope(globalScope);

                for (Variable param : method.getParameters()) {
                    currentScope.declareVar(param);
                }

                openBrackets++;
                currentScope = currentScope.createChildScope();

            } catch (SyntaxException | ScopeException e) {
                throw new ParserException(e.getMessage());
            }
        }
    }

    private void handleIfWhile(String line) throws ParserException {
        if (!inMethod) {
            throw new ParserException("if/while not allowed outside method");
        }

        try {
            IfWhileValidator.validateCondition(line.trim(), currentScope);
        } catch (SyntaxException e) {
            throw new ParserException(e.getMessage());
        }

        openBrackets++;
        currentScope = currentScope.createChildScope();
    }


    /**
     * Collects method declarations from the given lines.
     *
     * @param lines The lines to parse
     * @throws ParserException If a method declaration is invalid or duplicated
     */
    private void collectMethods(List<String> lines) throws ParserException {
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.endsWith("{") && MethodDeclarationValidator.isMethodDeclaration(trimmed)) {
                try {
                    Method m = MethodDeclarationValidator.parseDeclaration(trimmed);
                    if (methods.containsKey(m.getName())) {
                        throw new ParserException("Method " + m.getName() + " already declared");
                    }
                    methods.put(m.getName(), m);
                } catch (SyntaxException e) {
                    throw new ParserException(e.getMessage());
                }
            }
        }
    }

    /**
     * Resets the parser state for a new parsing session.
     */
    private void resetParserState() {
        openBrackets = 0;
        inMethod = false;
        methodDepth = 0;
        currentScope = globalScope;
        lastStatementInCurrentMethod = null; // אם יש לך
    }


}
