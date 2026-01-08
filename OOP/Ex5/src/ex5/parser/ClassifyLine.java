package ex5.parser;

/**
 * Classify line as one of the options from LineType enum
 */
public class ClassifyLine {
    /**
     * Classifies a line of Sjava code into one of the LineType categories.
     *
     * @param line The line of code to classify
     * @return The LineType of the given line
     * @throws ParserException If the line contains syntax errors
     */
    public static LineType classify(String line) throws ParserException{
        String trimmed = line.trim();

        if (trimmed.isEmpty()) {
            return LineType.EMPTY;
        }

        if (line.startsWith("//")) {
            return LineType.COMMENT;
        }

        if (trimmed.endsWith("{")) {
            if (trimmed.equals("{")) {
                throw new ParserException(
                        "'{' must appear after a method/if/while declaration"
                );
            }
            return LineType.OPEN_BRACKET;
        }

        if (trimmed.equals("}")) {
            return LineType.CLOSE_BRACKET;
        }

        return LineType.CODE;
    }
}
