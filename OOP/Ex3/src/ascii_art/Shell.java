package ascii_art;

import image.Image;
import image.PaddingImage;
import image.SubImagesHandler;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.Arrays;

/** The Shell class provides a command-line interface
 * for interacting with the ASCII art conversion program.
 */
public class Shell{
    private static int DEFAULT_RESOLUTION = 2;
    private static char[] DEFAULT_CHARSET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private int resolution;
    private int minCharsInRow;
    private int maxCharsInRow;
    private char[] charset;
    private Image image;
    SubImgCharMatcher matcher;


    /** Constructs a Shell object with default resolution and character set.
     */
    public Shell(){
        this.resolution = DEFAULT_RESOLUTION;
        this.charset = Arrays.copyOf(DEFAULT_CHARSET, DEFAULT_CHARSET.length);
        this.matcher = new SubImgCharMatcher(this.charset);
    }

    /** Runs the shell interface with the specified image.
     * @param imageName The name of the image file to be processed.
     */
    public void run(String imageName) {
        try {
            this.image = new Image(imageName);
        } catch (IOException e) {
            System.out.println("Error: Unable to load image '" + imageName + "'.");
            return;
        }
        Image padded = PaddingImage.padImg(image);
        maxCharsInRow = padded.getWidth();
        minCharsInRow = Math.max(1, padded.getWidth() / padded.getHeight());

        while(true){
            System.out.print(">>> ");
            String line = KeyboardInput.readLine();
            line = line.trim();
            String[] parts = line.split("\\s+");
            if(parts[0].equals("exit")){
                return;
            }

            if (parts[0].equals("chars")){
                printCharset();
            }

            if(parts[0].equals("add")){
                if (parts.length < 2) {
                    System.out.println("Did not add due to incorrect format.");
                    continue;
                }
                addSomething(parts[1]);
            }

            if(parts[0].equals("remove")){
                if (parts.length < 2) {
                    System.out.println("Did not remove due to incorrect format.");
                    continue;
                }
                removeSomething(parts[1]);
            }

            if(parts[0].equals("res")){
                if(parts.length == 1){
                    System.out.println("Resolution set to " + resolution);
                }
                if(parts.length > 1){
                    changeRes(parts[1]);
                }
            }
        }
    }

    private void printCharset(){
        if(charset.length == 0){
            return;
        }
        char[] sorted = Arrays.copyOf(charset, charset.length);
        Arrays.sort(sorted);
        for(char c : sorted){
            System.out.print(c + " ");
        }
        System.out.println();
    }

    private void addSomething(String something){
        if(something.length() == 1){
            char c = something.charAt(0);
            if (c < 32 || c > 126){
                System.out.println("Did not add due to incorrect format.");
                return;
            }

            addCharIfNotExists(c);
            return;
        }

        if(something.equals("all")){
            for (char c = 32; c < 127; c++) {
                addCharIfNotExists(c);
            }
            return;
        }

        if(something.equals("space")){
            char c = ' ';
            addCharIfNotExists(c);
            return;
        }

        if(something.length() == 3 && something.charAt(1) == '-'){
            char start = something.charAt(0);
            char end = something.charAt(2);
            if(start < 32 || end > 126 || end < 32 || start > 126){
                System.out.println("Did not add due to incorrect format.");
                return;
            }

            if(start > end){
                char tmp = start;
                start = end;
                end = tmp;
            }

            for(char c = start; c <= end; c++){
                addCharIfNotExists(c);
            }
            return;
        }

        System.out.println("Did not add due to incorrect format.");

    }

    private void addCharIfNotExists(char c) {
        if (!contains(charset, c)) {
            matcher.addChar(c);
            charset = Arrays.copyOf(charset, charset.length + 1);
            charset[charset.length - 1] = c;
        }
    }

    private void removeSomething(String something){
        if(something.length() == 1){
            char c = something.charAt(0);
            if (c < 32 || c > 126){
                System.out.println("Did not remove due to incorrect format.");
                return;
            }

            removeCharIfExists(c);
            return;
        }

        if(something.equals("all")){
            for (char c = 32; c < 127; c++) {
                removeCharIfExists(c);
            }
            return;
        }

        if(something.equals("space")){
            char c = ' ';
            removeCharIfExists(c);
            return;
        }

        if(something.length() == 3 && something.charAt(1) == '-'){
            char start = something.charAt(0);
            char end = something.charAt(2);
            if(start < 32 || end > 126 || end < 32 || start > 126){
                System.out.println("Did not remove due to incorrect format.");
                return;
            }

            if(start > end){
                char tmp = start;
                start = end;
                end = tmp;
            }

            for(char c = start; c <= end; c++){
                removeCharIfExists(c);
            }
            return;
        }

        System.out.println("Did not remove due to incorrect format.");
    }

    private void removeCharIfExists(char c) {
        if (!contains(charset, c)) {
            return;
        }

        matcher.removeChar(c);

        char[] newArr = new char[charset.length - 1];
        int index = 0;

        for (char x : charset) {
            if (x != c) {
                newArr[index] = x;
                index++;
            }
        }
        charset = newArr;
    }

    private boolean contains(char[] arr, char c){
        for (char x : arr){
            if (x == c) return true;
        }
        return false;
    }

    private void changeRes(String askedRes){
        int newResolution = resolution;
        if(askedRes.equals("up")){
            newResolution *= 2;
        } else if (askedRes.equals("down")) {
            newResolution /= 2;
        }
        else{
            System.out.println("Did not change resolution due to incorrect format.");
            return;
        }
        if(newResolution < minCharsInRow || newResolution > maxCharsInRow){
            System.out.println("Did not change resolution due to exceeding boundaries.");
        }
        else {
            resolution = newResolution;
            System.out.println("Resolution set to " + resolution);
        }
    }

    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.run(args[0]);
    }
}
