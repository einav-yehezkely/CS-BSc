package ex5.main;

import ex5.parser.ParserException;
import ex5.parser.SjavacParser;

import java.io.File;

/**
 * Sjavac is a simple compiler for the Sjava programming language.
 * It validates Sjava source files and reports whether they are legal or illegal.
 */
public class Sjavac {

    private static final int LEGAL = 0;
    private static final int ILLEGAL = 1;
    private static final int IO_ERROR = 2;

    /**
     * The main method that drives the Sjavac verifier.
     * It takes a single command-line argument: the path to the Sjava source file.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        try {
            String path = validateArgs(args);
            SjavacParser parser = new SjavacParser(path);
            parser.validateSjavaFile();
            System.out.println(LEGAL);
        }
        catch (IOErrorException e) {
            System.out.println(IO_ERROR);
            System.err.println(e.getMessage());
        }
        catch (ParserException e) { // TODO: change to specific exception
            System.out.println(ILLEGAL);
            System.err.println(e.getMessage());
        }


    }

    /**
     * Validates the command-line arguments.
     *
     * @param args Command-line arguments
     * @return The path to the Sjava source file
     * @throws IOErrorException If the arguments are invalid or the file is not accessible
     */
    private static String validateArgs(String[] args) throws IOErrorException{
        if(args==null || args.length!=1){
            throw new IOErrorException("Expected one argument");
        }

        String path = args[0];

        if(!path.endsWith(".sjava")){
            throw new IOErrorException("File must be .sjava file");
        }

        File file = new File(path);
        if (!file.exists()) {
            throw new IOErrorException("File does not exist");
        }

        if (!file.isFile() || !file.canRead()) {
            throw new IOErrorException("File is not readable");
        }

        return path;
    }
}
