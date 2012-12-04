package org.simplesql.om.util;

public class ExceptionUtil {

	/**
	 * Converts the Throwable into a runtime exception only if its not a runtime
	 * exception already
	 * 
	 * @param t
	 */
	public static final void throwRuntime(Throwable t) throws RuntimeException {
		if (t instanceof RuntimeException) {
			throw ((RuntimeException) t);
		} else {
			RuntimeException rte = new RuntimeException(t.toString(), t);
			rte.setStackTrace(t.getStackTrace());
			throw rte;
		}
	}

}
