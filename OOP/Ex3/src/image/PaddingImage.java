package image;

import java.awt.*;

public class PaddingImage {

    public static Image padImg(Image image){
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        int newWidth = nextPowerOfTwo(originalWidth);
        int newHeight = nextPowerOfTwo(originalHeight);

        // If no padding is needed, return original
        if (newWidth == originalWidth && newHeight == originalHeight) {
            return image;
        }

        Color[][] newPixelArray = new Color[newHeight][newWidth];

        // Fill padded image with white pixels
        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < newWidth; col++) {
                newPixelArray[row][col] = Color.WHITE;
            }
        }

        // Compute padding amounts (symmetric)
        int topPad = (newHeight - originalHeight) / 2;
        int leftPad = (newWidth - originalWidth) / 2;

        // Copy original image pixels to the new pixel array
        for (int row = 0; row < originalHeight; row++) {
            for (int col = 0; col < originalWidth; col++) {
                newPixelArray[row + topPad][col + leftPad] = image.getPixel(row, col);
            }
        }

        return new Image(newPixelArray, newWidth, newHeight);

    }

    private static int nextPowerOfTwo(int n){
        int power = 1;
        while (power < n) {
            power *= 2;
        }
        return power;
    }
}
