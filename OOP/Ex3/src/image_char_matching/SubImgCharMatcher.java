package image_char_matching;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Matches sub-image brightness to characters based on their brightness levels.
 */
public class SubImgCharMatcher {
    private Map<Character, Double> normalizedCharBrightnessMap;
    private final Map<Character, Double> charBrightnessMap;
    private boolean reverseMode;
    private Map<Character, Double> reversedCharBrightnessMap;

    /**
     * Initializes the matcher with a given character set.
     * @param charset Array of characters to be used for matching.
     */
    public SubImgCharMatcher(char[] charset){
        this.charBrightnessMap = calcCharBrightness(charset);
        this.normalizedCharBrightnessMap = calcNormalizedCharBrightness();
        this.reversedCharBrightnessMap = calcReversedBrightness();
        this.reverseMode = false;
    }

    /**
     * Finds the character that best matches the given brightness level.
     * @param brightness Brightness level of the sub-image (0.0 to 1.0).
     * @return Character that best matches the brightness.
     */
    public char getCharByImageBrightness(double brightness){
        Map<Character, Double> mapToUse;
        if(reverseMode){
            mapToUse = reversedCharBrightnessMap;
        }
        else{
            mapToUse = normalizedCharBrightnessMap;
        }

        char closestChar = ' ';
        double closestDifference = Double.MAX_VALUE;

        for (Map.Entry<Character, Double> entry : mapToUse.entrySet()) {
            double diff = Math.abs(entry.getValue() - brightness);
            if (diff < closestDifference) {
                closestDifference = diff;
                closestChar = entry.getKey();
            }
        }

        return closestChar;
    }

    /**
     * Adds a new character to the matcher and recalculates brightness mappings.
     * @param c Character to be added.
     */
    public void addChar(char c){
        if (charBrightnessMap.containsKey(c)) {
            return;
        }

        double brightness = calcSingleCharBrightness(c);
        charBrightnessMap.put(c, brightness);
        // Recalculate normalized brightness map and reversed map
        this.normalizedCharBrightnessMap = calcNormalizedCharBrightness();
        this.reversedCharBrightnessMap = calcReversedBrightness();
    }

    /**
     * Removes a character from the matcher and recalculates brightness mappings.
     * @param c Character to be removed.
     */
    public void removeChar(char c){
        if (!charBrightnessMap.containsKey(c)) {
            return;
        }

        charBrightnessMap.remove(c);

        if (charBrightnessMap.isEmpty()) {
            normalizedCharBrightnessMap = new HashMap<>();
            return;
        }

        // Recalculate normalized brightness map and reversed map
        this.normalizedCharBrightnessMap = calcNormalizedCharBrightness();
        this.reversedCharBrightnessMap = calcReversedBrightness();

    }

    private Map<Character, Double> calcCharBrightness(char[] charset){
        Map<Character, Double> charBrightnessMap = new HashMap<>();
        for(char c : charset){
            double brightness = calcSingleCharBrightness(c);
            charBrightnessMap.put(c, brightness);
        }

        return charBrightnessMap;
    }

    private double calcSingleCharBrightness(char c){
        boolean[][] charImage = CharConverter.convertToBoolArray(c);
        int rows = charImage.length;
        int cols = charImage[0].length;

        int numOfWhites = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(charImage[row][col]) {
                    numOfWhites++;
                }
            }
        }
        return (double) numOfWhites / (rows * cols);
    }

    private Map<Character, Double> calcNormalizedCharBrightness(){
        Map<Character, Double> normalizedCharBrightnessMap = new HashMap<>();
        if (charBrightnessMap.isEmpty()){
            return normalizedCharBrightnessMap;
        }
        Double minBrightness = Collections.min(charBrightnessMap.values());
        Double maxBrightness = Collections.max(charBrightnessMap.values());
        for(Map.Entry<Character, Double> entry : charBrightnessMap.entrySet()){
            double normalizedBrightness =
                    normalizeBrightness(entry.getValue(), minBrightness, maxBrightness);
            normalizedCharBrightnessMap.put(entry.getKey(), normalizedBrightness);
        }

        return normalizedCharBrightnessMap;
    }

    private double normalizeBrightness(double brightness, double minBrightness, double maxBrightness){
        return (brightness - minBrightness) / (maxBrightness - minBrightness);
    }

    /**
     * Turns on reverse mode, which inverts the brightness mapping.
     */
    public void turnOnReverse() {
        reverseMode = true;
    }

    private Map<Character, Double> calcReversedBrightness() {
        Map<Character, Double> reversed = new HashMap<>();
        for (Map.Entry<Character, Double> entry : normalizedCharBrightnessMap.entrySet()) {
            reversed.put(entry.getKey(), 1.0 - entry.getValue());
        }
        return reversed;
    }

}
