package com.pisces.core.utils;

public class DoubleUtils {
	private static final double PRECISION = 0.000001;
	
	public static boolean isZero(double value) {
		return value > -PRECISION && value < PRECISION;
	}
	
	public static boolean isLess(double value1, double value2) {
		return (value2 - value1) > PRECISION;
	}
	
	public static boolean isEqual(double v1, double v2) {
		return isZero(v1 - v2);
	}
	
	public static boolean isLessEqual(double v1, double v2) {
		return (v2 - v1) > PRECISION || isZero(v1 - v2);
	}
	
	public static boolean isGreater(double value1, double value2) {
		return !isLessEqual(value1, value2);
	}
	
	public static boolean isGreaterEqual(double value1, double value2) {
		return !isLess(value1, value2);
	}
}
