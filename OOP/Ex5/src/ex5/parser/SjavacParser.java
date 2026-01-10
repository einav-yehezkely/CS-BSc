package ex5.parser;

import ex5.entities.Method;
import ex5.entities.Value;
import ex5.entities.Variable;
import ex5.scope.Scope;
import ex5.scope.ScopeException;
import ex5.validation.AssignmentValidator;
import ex5.validation.MethodDeclarationValidator;
import ex5.validation.SyntaxException;
import ex5.validation.VarDeclarationValidator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * SjavacParser is responsible for parsing and validating Sjava source files.
 */
public class SjavacParser {
    private final String path;
    private int openBrackets = 0;
    private final Scope globalScope;
    private Scope currentScope;
    private boolean inMethod;

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
    }

    /**
     * Validates the Sjava source file.
     *
     * @throws ParserException If the file contains syntax errors
     */
    public void validateSjavaFile() throws ParserException{
        try (FileReader fileReader = new FileReader(this.path);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String line;
            int lineNumber = 1;
            while ((line = bufferedReader.readLine()) != null) {
                validateLine(line, lineNumber);
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
                handleMethod(line);
                return;
            case CLOSE_BRACKET:
                openBrackets--;
                if(openBrackets < 0){
                    throw new ParserException("Unmatched closing bracket } at line " + lineNumber);
                }
                currentScope = currentScope.getParentScope();
                if (currentScope == globalScope) {
                    inMethod = false;
                }
                return;
            case CODE:
                handleCodeLine(line, lineNumber);

        }
    }

    private void handleCodeLine(String line, int lineNumber) throws ParserException{
        String trimmedLine = line.trim();
        if(!trimmedLine.endsWith(";")){
            throw new ParserException("Missing ; at line " + lineNumber);
        }
        // try declaration
        if (VarDeclarationValidator.isDeclaration(trimmedLine)) {
            try {
                List<Variable> variables =
                        VarDeclarationValidator.parseDeclaration(trimmedLine);
                for (Variable var : variables) {
                    currentScope.declareVar(var);
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

            AssignmentValidator.validateAssignment(var, varValue);

            var.assignValue(new Value(varValue, var.getType()));

        } catch (ScopeException | SyntaxException e) {
            throw new ParserException(e.getMessage());
        }

    }

    private void handleMethod(String line) throws ParserException{
        String trimmedLine = line.trim();
        // check if method declaration
        if (!inMethod && MethodDeclarationValidator.isMethodDeclaration(trimmedLine)) {
            try {
                Method method =
                        MethodDeclarationValidator.parseDeclaration(trimmedLine);

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
}
