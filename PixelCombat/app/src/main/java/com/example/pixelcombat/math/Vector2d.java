package com.example.pixelcombat.math;

import org.jetbrains.annotations.NotNull;

/**
 * 2D vector with w as homogeneous coordinate <br>
 * w is always 1.0f and is needed to perform translation operations via vector-matrix-multiplication
 *
 * @author BillyG
 * @version 0.1
 */
public class Vector2d implements Comparable<Vector2d> {

    public float x, y;

    /**
     * Constructor of Vector2D <br>
     * Creates an empty vector
     */
    public Vector2d() {
        x = 0.0f;
        y = 0.0f;
    }

    /**
     * Constructor of Vector2D
     *
     * @param x x-position
     * @param y y-position
     */
    public Vector2d(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds two vectors v1 and v2
     *
     * @param v vector
     * @return v1 + v2
     */
    public Vector2d add(Vector2d v) {
        return new Vector2d(x + v.x, y + v.y);
    }

    /**
     * Subtracts two vectors v1 and v2
     *
     * @param v vector
     * @return v1 - v2
     */
    public Vector2d sub(Vector2d v) {
        return new Vector2d(x - v.x, y - v.y);
    }

    /**
     * Computes the point product of two vectors v1 and v2
     *
     * @param v vector
     * @return v1 x v2
     */
    public float pointProduct(Vector2d v) {
        Vector2d tmp1 = normalize();
        Vector2d tmp2 = v.normalize();

        return tmp1.x * tmp2.x + tmp1.y * tmp2.y;
    }

    /**
     * Multiplies a vector v and a constant value f
     *
     * @param f constant value
     * @return v * f
     */
    public Vector2d multiple(float f) {
        return new Vector2d(x * f, y * f);
    }


    /**
     * Divides a vector v by a constant value f
     *
     * @param f constant value
     * @return v / f
     */
    public Vector2d div(float f) {
        return new Vector2d(x / f, y / f);
    }

    /**
     * Computes the length of the vector
     *
     * @return length
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Normalizes a vector
     *
     * @return normalized vector
     */
    public Vector2d normalize() {
        return div(length());
    }

    /**Creates a Vector from polar coordinates angle and length
     *
     *
     *
     * @param angle direction of the vector
     * (needs to be converted first in Math.ToRadians)
     * @param r length of vector
     * @return new Vector2d showing into the direction angle with length r, start point is (0,0)
     */
    public static Vector2d setVector(float angle, float r){
        float sx = r*(float)Math.cos(angle);
        float sy = r*(float)Math.sin(angle);

        return new Vector2d(sx,sy);
    }


    /**
     * Computes the distance between the two vectors v1 and v2
     *
     * @param v vector
     * @return distance between v1 and v2
     */
    public float distance(Vector2d v) {
        return sub(v).length();
    }

    /**
     * Compares two vectors
     *
     * @param v vector
     * @return are equal
     */
    @Override
    public boolean equals(Object v) {
        if (!(v instanceof Vector2d)) {
            return false;
        }
        return (x == ((Vector2d) v).x) && (y == ((Vector2d) v).y);
    }

    /**
     * Simple toString method
     *
     * @return string
     */
    @NotNull
    @Override
    public String toString() {
        return "(" + (x) + ")(" + (y) + ")";
    }

    /**
     * Compares two vectors
     *
     * @param v vector
     * @return are equal
     */

    public int compareTo(Vector2d v) {
        return Float.compare(length(), v.length());
    }
}
