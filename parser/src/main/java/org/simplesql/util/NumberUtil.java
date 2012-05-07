package org.simplesql.util;

public final class NumberUtil {
	public static final int intValueOf(Object obj){
		return intValueOf(obj.toString());
	}
	
	public static final int intValueOf(String str) {
		int ival = 0, idx = 0, end;
		boolean sign = false;
		char ch;

		if (str == null
				|| (end = str.length()) == 0
				|| ((ch = str.charAt(0)) < '0' || ch > '9')
				&& (!(sign = ch == '-') || ++idx == end || ((ch = str
						.charAt(idx)) < '0' || ch > '9')))
			throw new NumberFormatException(str);

		for (;; ival *= 10) {
			ival += '0' - ch;
			if (++idx == end)
				return sign ? ival : -ival;
			if ((ch = str.charAt(idx)) < '0' || ch > '9')
				throw new NumberFormatException(str);
		}
	}

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

	public static final String getConvertClassMethod(Class<?> cls){
		if (cls.equals(int.class)) {
			return NumberUtil.class.getName() + ".intValueOf";
		} else {
			return "new " + getWrapperClass(cls).getName();
		}
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
