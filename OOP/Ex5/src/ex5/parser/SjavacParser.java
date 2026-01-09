package ex5.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * SjavacParser is responsible for parsing and validating Sjava source files.
 */
public class SjavacParser {
    private final String path;
    private int openBrackets = 0;

    /**
     * Constructs a SjavacParser for the specified file path.
     *
     * @param path The path to the Sjava source file
     */
    public SjavacParser(String path) {
        this.path = path;
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
                openBrackets++;
                return;
            case CLOSE_BRACKET:
                openBrackets--;
                if(openBrackets < 0){
                    throw new ParserException("Unmatched closing bracket } at line " + lineNumber);
                }
                return;
            case CODE:
                if(!line.trim().endsWith(";")){
                    throw new ParserException("Missing ; at line " + lineNumber);
                }
                // TODO
                return;
        }
    }
}
