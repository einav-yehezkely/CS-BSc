package ascii_art;

import image.Image;
import image.PaddingImage;
import image.SubImagesHandler;
import image_char_matching.SubImgCharMatcher;

public class AsciiArtAlgorithm {

    private final Image image;
    private final int resolution;
    private final char[] charset;

    private double[][] prevBrightness;
    private int prevResolution;
    private Image prevImage;
    private char[] prevCharset;
    private SubImgCharMatcher prevMatcher;


    /** Constructs an AsciiArtAlgorithm object with the specified image, resolution, and character set.
     * @param image The input image to be converted to ASCII art.
     * @param resolution The number of characters in each row of the resulting ASCII art.
     * @param charset The set of characters to be used for the ASCII art.
     */
    public AsciiArtAlgorithm(Image image, int resolution, char[] charset) {
        this.image = image;
        this.resolution = resolution;
        this.charset = charset;

        this.prevBrightness = null;
        this.prevResolution = -1;
        this.prevImage = null;
        this.prevCharset = null;
        this.prevMatcher = null;
    }

    /** Runs the ASCII art conversion algorithm.
     * @return A 2D array of characters representing the ASCII art.
     */
    public char[][] run(){
        // Step 1: Pad the image
        Image paddedImage = PaddingImage.padImg(image);
        // Step 2: Divide the image into sub-images
        Image[][] subImages = SubImagesHandler.divideImage(paddedImage, resolution);

        int numRows = subImages.length;
        int numCols = subImages[0].length;

        // check if we can use cached brightness values
        boolean canUseCache =
                (prevBrightness != null &&
                        prevResolution == resolution &&
                        prevImage == image &&
                        prevBrightness.length == numRows &&
                        prevBrightness[0].length == numCols);

        double[][] brightness = new double[numRows][numCols];

        if (!canUseCache) {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    brightness[i][j] = SubImagesHandler.computeSubImageBrightness(subImages[i][j]);
                }
            }
            prevBrightness = brightness;
            prevResolution = resolution;
            prevImage = image;
        }
        else {
            brightness = prevBrightness;
        }

        SubImgCharMatcher matcher;

        // Step 3: Match sub-images to characters
        if(prevCharset == null || !java.util.Arrays.equals(prevCharset, charset)){
            matcher = new SubImgCharMatcher(charset);
            prevCharset = java.util.Arrays.copyOf(charset, charset.length);
            prevMatcher = matcher;
        }
        else{
            matcher = prevMatcher;
        }
        // Generate ASCII art based on brightness values
        char[][] asciiArt = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                asciiArt[i][j] = matcher.getCharByImageBrightness(brightness[i][j]);
            }
        }
        return asciiArt;
    }

//    public static void main(String[] args) {
//        try {
//            Image img = new Image("examples/board.jpeg");
//
//            AsciiArtAlgorithm algorithm =
//                    new AsciiArtAlgorithm(img, 2, new char[]{'m', 'o'});
//
//            char[][] ascii = algorithm.run();
//
//            for (char[] row : ascii) {
//                System.out.println(new String(row));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

