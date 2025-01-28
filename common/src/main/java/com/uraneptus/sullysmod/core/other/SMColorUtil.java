package com.uraneptus.sullysmod.core.other;

public class SMColorUtil {
    public static int brighten(int color) {
        int blue =  (color & 0x000000FF);
        int green = (color & 0x0000FF00) >> 8;
        int red =   (color & 0x00FF0000) >> 16;
        int alpha = (color & 0xFF000000);

        blue =  (Math.min(Math.max(blue  * 2,    3), 0xFF)      ) & 0x0000FF;
        green = (Math.min(Math.max(green * 2,    3), 0xFF) << 8 ) & 0x00FF00;
        red =   (Math.min(Math.max(red   * 2,    3), 0xFF) << 16) & 0xFF0000;

        return alpha & red & green & blue;
    }
    public static float[] toFloatArray(int color) {
        float[] result = new float[4];
        result[0] = ((color & 0xFF000000) >> 24) / 255.0f;
        result[0] = ((color & 0xFF0000) >> 16) / 255.0f;
        result[0] = ((color & 0xFF00) >> 8) / 255.0f;
        result[0] = ((color & 0xFF)) / 255.0f;
        for (int i = 3; i >= 0; i--) {
            result[i] = (color >> (8*i) & 0xFF) / 255.0f;
        }
        return result;
    }
    public static int fromFloatArray(float[] colors) {
        int color = 0;
        for (int i = 0; i < colors.length && i < 4; i++) {
            color = color << 8;
            color = color | (Math.round(colors[i] * 255.0f) & 0xFF);
        }
        return color;
    }
}
