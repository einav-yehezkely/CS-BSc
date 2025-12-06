package image;

import java.awt.*;

/**
 * A class that handles sub-images within a larger image.
 * Provides functionality to divide an image into smaller sub-images
 * and compute the brightness of a given sub-image.
 * @author Einav Yehezkely
 */
public class SubImagesHandler {
    /**
     * Divides the image into smaller sub-images arranged in a grid.
     * Each sub-image will have dimensions determined by the number of images in each row.
     * @param numImagesInRow The number of sub-images to be placed in each row - The resolution.
     * @return A 2D array of sub-images.
     */
    public static Image[][] divideImage(Image image, int numImagesInRow) {
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        int subImageWidth = originalWidth / numImagesInRow;
        int subImageHeight = subImageWidth;

        int numRows = originalHeight / subImageHeight;
        int numCols = numImagesInRow;

        Image[][] subImages = new Image[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {

                Color[][] subImage = new Color[subImageHeight][subImageWidth];

                int startX = row * subImageHeight;
                int startY = col * subImageWidth;

                for (int i = 0; i < subImageHeight; i++) {
                    for (int j = 0; j < subImageWidth; j++) {
                        subImage[i][j] = image.getPixel(startX + i, startY + j);
                    }
                }

                subImages[row][col] = new Image(subImage, subImageWidth, subImageHeight);
            }
        }

        return subImages;
    }

    /**
     * Computes the brightness of a given sub-image
     * @param subImage a given sub-image
     * @return the brightness
     */
    public static double computeSubImageBrightness(Image subImage) {
        int width = subImage.getWidth();
        int height = subImage.getHeight();

        double greyPixelsSum = 0.0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color pixelColor = subImage.getPixel(i, j);
                double greyPixel = (
                        pixelColor.getRed() * 0.2126 +
                        pixelColor.getGreen() * 0.7152 +
                        pixelColor.getBlue() * 0.0722
                        );
                greyPixelsSum += greyPixel;
            }
        }

        return greyPixelsSum / (width * height * 255.0);
    }

}
