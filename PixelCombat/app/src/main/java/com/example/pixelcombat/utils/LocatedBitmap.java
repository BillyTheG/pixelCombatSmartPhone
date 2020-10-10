package com.example.pixelcombat.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.pixelcombat.math.Vector2d;

public class LocatedBitmap {

    public Bitmap image;
    public Vector2d pos;


    public LocatedBitmap(Bitmap image, Vector2d pos) {
        this.image = image;
        this.pos = pos;
    }

    public static Bitmap TrimImage(Bitmap bmp) {
        int imgHeight = bmp.getHeight();
        int imgWidth = bmp.getWidth();

        //TRIM WIDTH
        int widthStart = imgWidth;
        int widthEnd = 0;
        for (int i = 0; i < imgHeight; i++) {
            for (int j = imgWidth - 1; j >= 0; j--) {
                if (bmp.getPixel(j, i) != Color.TRANSPARENT &&
                        j < widthStart) {
                    widthStart = j;
                }
                if (bmp.getPixel(j, i) != Color.TRANSPARENT &&
                        j > widthEnd) {
                    widthEnd = j;
                    break;
                }
            }
        }
        //TRIM HEIGHT
        int heightStart = imgHeight;
        int heightEnd = 0;
        for (int i = 0; i < imgWidth; i++) {
            for (int j = imgHeight - 1; j >= 0; j--) {
                if (bmp.getPixel(i, j) != Color.TRANSPARENT &&
                        j < heightStart) {
                    heightStart = j;
                }
                if (bmp.getPixel(i, j) != Color.TRANSPARENT &&
                        j > heightEnd) {
                    heightEnd = j;
                    break;
                }
            }
        }

        int finalWidth = widthEnd - widthStart;
        int finalHeight = heightEnd - heightStart;

        return Bitmap.createBitmap(bmp, widthStart, heightStart, finalWidth, finalHeight);
    }
}
