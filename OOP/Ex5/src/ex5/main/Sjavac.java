package ex5.main;

import java.io.File;

public class Sjavac {

    private static final int LEGAL = 0;
    private static final int ILLEGAL = 1;
    private static final int IO_ERROR = 2;

    public static void main(String[] args) {
        try {
            String path = validateArgs(args);
        } catch (IOErrorException e) {
            System.out.println(IO_ERROR);
            System.err.println(e.getMessage());
        }


    }

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
