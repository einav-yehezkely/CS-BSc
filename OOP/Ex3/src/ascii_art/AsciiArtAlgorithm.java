package ascii_art;

import image.Image;
import image.PaddingImage;
import image.SubImagesHandler;
import image_char_matching.SubImgCharMatcher;

public class AsciiArtAlgorithm {

    private final Image image;
    private final int resolution;
    private final char[] charset;

    /** Constructs an AsciiArtAlgorithm object with the specified image, resolution, and character set.
     * @param image The input image to be converted to ASCII art.
     * @param resolution The number of characters in each row of the resulting ASCII art.
     * @param charset The set of characters to be used for the ASCII art.
     */
    public AsciiArtAlgorithm(Image image, int resolution, char[] charset) {
        this.image = image;
        this.resolution = resolution;
        this.charset = charset;
    }

    /** Runs the ASCII art conversion algorithm.
     * @return A 2D array of characters representing the ASCII art.
     */
    public char[][] run(){
        // Step 1: Pad the image
        PaddingImage paddingImage = new PaddingImage(image);
        Image paddedImage = paddingImage.padImg();
        // Step 2: Divide the image into sub-images
        SubImagesHandler subImagesHandler = new SubImagesHandler(paddedImage);
        Image[][] subImages = subImagesHandler.divideImage(resolution);
        // Step 3: Match sub-images to characters
        SubImgCharMatcher matcher = new SubImgCharMatcher(charset);
        int numRows = subImages.length;
        int numCols = subImages[0].length;
        char[][] asciiArt = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                double brightness = subImagesHandler.computeSubImageBrightness(subImages[i][j]);
                char matchedChar = matcher.getCharByImageBrightness(brightness);
                asciiArt[i][j] = matchedChar;
            }
        }
        return asciiArt;
    }

    public static void main(String[] args) {
        try {
            Image img = new Image("examples/board.jpeg");

            AsciiArtAlgorithm algorithm =
                    new AsciiArtAlgorithm(img, 2, new char[]{'m', 'o'});

            char[][] ascii = algorithm.run();

            for (char[] row : ascii) {
                System.out.println(new String(row));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

