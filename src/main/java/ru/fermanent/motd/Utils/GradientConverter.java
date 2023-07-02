package ru.fermanent.motd.Utils;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradientConverter {
    public static String convertHexToGradient(String input) {
        Pattern gradientPattern = Pattern.compile("<g:(#[a-fA-F0-9]{6}):(#[a-fA-F0-9]{6})>(.+?)</g>");
        Matcher gradientMatcher = gradientPattern.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (gradientMatcher.find()) {
            String startColor = gradientMatcher.group(1);
            String endColor = gradientMatcher.group(2);
            String text = gradientMatcher.group(3);
            String gradient = createGradient(startColor, endColor, text);
            gradientMatcher.appendReplacement(sb, gradient);
        }
        gradientMatcher.appendTail(sb);

        Pattern singleColorPattern = Pattern.compile("<g:(#[a-fA-F0-9]{6})>(.+?)</g>");
        Matcher singleColorMatcher = singleColorPattern.matcher(sb.toString());
        sb = new StringBuffer();
        while (singleColorMatcher.find()) {
            String color = singleColorMatcher.group(1);
            String text = singleColorMatcher.group(2);
            String coloredText = createColoredText(color, text);
            singleColorMatcher.appendReplacement(sb, coloredText);
        }
        singleColorMatcher.appendTail(sb);

        return sb.toString();
    }

    private static String createGradient(String startColor, String endColor, String text) {
        Color start = Color.decode(startColor);
        Color end = Color.decode(endColor);
        float[] startHSB = Color.RGBtoHSB(start.getRed(), start.getGreen(), start.getBlue(), null);
        float[] endHSB = Color.RGBtoHSB(end.getRed(), end.getGreen(), end.getBlue(), null);

        StringBuilder gradient = new StringBuilder();
        int length = text.codePointCount(0, text.length());
        for (int i = 0; i < length; i++) {
            float fraction = (float) i / (length - 1);
            float[] interpolatedHSB = interpolate(startHSB, endHSB, fraction);
            int rgb = Color.HSBtoRGB(interpolatedHSB[0], interpolatedHSB[1], interpolatedHSB[2]);
            gradient.append(String.format("§x§%X§%X§%X§%X§%X§%X", (rgb >> 20) & 0xF, (rgb >> 16) & 0xF, (rgb >> 12) & 0xF, (rgb >> 8) & 0xF, (rgb >> 4) & 0xF, rgb & 0xF));
            int codePoint = text.codePointAt(text.offsetByCodePoints(0, i));
            gradient.appendCodePoint(codePoint);
        }
        return gradient.toString();
    }

    private static String createColoredText(String color, String text) {
        Color c = Color.decode(color);
        int rgb = c.getRGB();
        StringBuilder coloredText = new StringBuilder();
        coloredText.append(String.format("§x§%X§%X§%X§%X§%X§%X", (rgb >> 20) & 0xF, (rgb >> 16) & 0xF, (rgb >> 12) & 0xF, (rgb >> 8) & 0xF, (rgb >> 4) & 0xF, rgb & 0xF));
        coloredText.append(text);
        return coloredText.toString();
    }

    private static float[] interpolate(float[] start, float[] end, float fraction) {
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            result[i] = start[i] + (end[i] - start[i]) * fraction;
        }
        return result;
    }
}
