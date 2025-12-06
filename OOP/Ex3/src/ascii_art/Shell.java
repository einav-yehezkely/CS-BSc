package ascii_art;

import image.Image;

import java.io.IOException;

public class Shell{
    private static int DEFAULT_RESOLUTION = 2;
    private static char[] DEFAULT_CHARSET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private int resolution;
    private char[] charset;
    private Image image;

    public Shell(){
        this.resolution = DEFAULT_RESOLUTION;
        this.charset = DEFAULT_CHARSET;
    }

    public void run(String imageName) {
        try {
            this.image = new Image(imageName);
        } catch (IOException e) {
            System.out.println("Error: Unable to load image '" + imageName + "'.");
            return;
        }

        while(true){
            System.out.print(">>> ");
            String line = KeyboardInput.readLine();
            if(line.equals("exit")){
                break;
            }
        }
    }

    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.run(args[0]);
    }
}
