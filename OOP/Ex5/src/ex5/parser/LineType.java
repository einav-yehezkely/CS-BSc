package ex5.parser;

/**
 * Enum representing different types of lines in a Sjava source file.
 */
public enum LineType {
    /**
     * Represents an empty line
     */
    EMPTY,
    /**
     * Represents a comment line
     */
    COMMENT,
    /**
     * Represents a line with an opening bracket
     */
    OPEN_BRACKET,
    /**
     * Represents a line with a closing bracket
     */
    CLOSE_BRACKET,
    /**
     * Represents a code line
     */
    CODE
}
