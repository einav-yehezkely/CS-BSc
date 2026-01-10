package ex5.scope;

import ex5.entities.Variable;

import java.util.HashMap;
import java.util.Map;

/**
 * Scope represents a variable scope in Sjava.
 */
public class Scope {
    private final Scope parentScope;
    private final Map<String, Variable> variables = new HashMap<>();

    /**
     * Constructs a new Scope with the specified parent scope.
     *
     * @param parentScope The parent scope
     */
    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    /**
     * Declares a new variable in the current scope.
     *
     * @param variable The variable to declare
     * @throws ScopeException If the variable is already declared in this scope
     */
    public void declareVar(Variable variable) throws ScopeException{
        String varName = variable.getName();
        if(variables.containsKey(varName)){
            throw new ScopeException("Variable " + varName + " already declared in this scope.");
        }
        variables.put(varName, variable);
    }

    /**
     * Looks up a variable by name in the current scope and its parent scopes.
     *
     * @param name The name of the variable
     * @return The Variable object if found, null otherwise
     */
    public Variable lookupVar(String name) throws ScopeException{
        if(variables.containsKey(name)){
            return variables.get(name);
        }
        if(parentScope != null){
            return parentScope.lookupVar(name);
        }
        throw new ScopeException("Variable " + name + " not declared.");
    }

    /**
     * Gets the parent scope.
     *
     * @return The parent scope
     */
    public Scope getParentScope() {
        return parentScope;
    }

    /**
     * Creates a new child scope of the current scope.
     *
     * @return The new child scope
     */
    public Scope createChildScope() {
        return new Scope(this);
    }
}
