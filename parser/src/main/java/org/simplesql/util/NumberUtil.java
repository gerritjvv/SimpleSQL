package org.simplesql.util;

public class NumberUtil {

	public static final boolean isPrimitiveNumber(Class<?> cls) {
		return cls.equals(double.class) || cls.equals(int.class)
				|| cls.equals(short.class) || cls.equals(float.class)
				|| cls.equals(long.class);
	}

	public static final boolean isPrimitive(Class<?> cls) {
		return cls.equals(boolean.class) || isPrimitiveNumber(cls);
	}

	public static final boolean isNumber(Class<?> cls) {
		return Number.class.isAssignableFrom(cls) || isPrimitiveNumber(cls);
	}

	/**
	 * Returns a Number or Boolean wrapper class, if the class is not a java
	 * primitive the same class passed in the parameter is returned.
	 * 
	 * @param cls
	 * @return
	 */
	public static final Class<?> getWrapperClass(Class<?> cls) {
		if (cls.equals(double.class)) {
			return Double.class;
		} else if (cls.equals(long.class)) {
			return Long.class;
		} else if (cls.equals(int.class)) {
			return Integer.class;
		} else if (cls.equals(short.class)) {
			return Short.class;
		} else if (cls.equals(float.class)) {
			return Float.class;
		} else if (cls.equals(long.class)) {
			return Long.class;
		} else if (cls.equals(boolean.class)) {
			return Boolean.class;
		} else {
			return cls;
		}
	}

}
