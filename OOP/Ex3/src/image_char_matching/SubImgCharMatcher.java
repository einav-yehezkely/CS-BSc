package image_char_matching;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SubImgCharMatcher {
    private Map<Character, Double> normalizedCharBrightnessMap;
    private final Map<Character, Double> charBrightnessMap;

    public SubImgCharMatcher(char[] charset){
        this.charBrightnessMap = calcCharBrightness(charset);
        this.normalizedCharBrightnessMap = calcNormalizedCharBrightness();
    }

    public char getCharByImageBrightness(double brightness){
        char closestChar = ' ';
        double closestDifference = Double.MAX_VALUE;

        for(Map.Entry<Character, Double> entry : normalizedCharBrightnessMap.entrySet()){
            double charBrightness = entry.getValue();
            double difference = Math.abs(charBrightness - brightness);

            // Find the character with the closest brightness
            if(difference < closestDifference){
                closestDifference = difference;
                closestChar = entry.getKey();
            }
        }

        return closestChar;
    }

    public void addChar(char c){
        if (charBrightnessMap.containsKey(c)) {
            return;
        }

        double brightness = calcSingleCharBrightness(c);
        charBrightnessMap.put(c, brightness);
        // Recalculate normalized brightness map
        this.normalizedCharBrightnessMap = calcNormalizedCharBrightness();
    }

    public void removeChar(char c){
        if (!charBrightnessMap.containsKey(c)) {
            return;
        }

        charBrightnessMap.remove(c);

        if (charBrightnessMap.isEmpty()) {
            normalizedCharBrightnessMap = new HashMap<>();
            return;
        }

        // Recalculate normalized brightness map
        this.normalizedCharBrightnessMap = calcNormalizedCharBrightness();
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

}
