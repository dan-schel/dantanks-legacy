package com.danschellekens.dantanks.exceptions;

import java.util.ArrayList;

public class OopsieException extends RuntimeException {
	public static final ArrayList<String> CLASS_NAMES_THAT_DONT_COUNT = GetClassNamesThatDontCount();
	
	private static final long serialVersionUID = 630754894637584703L;

	public OopsieException(Exception e) {
		super(GetMethodName() + " had error: " + e.getMessage());
	}
	
	public OopsieException(String message) {
		this(new Exception(message));
	}

	public static String GetMethodName() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		
		int i = 0;
		while (CLASS_NAMES_THAT_DONT_COUNT.contains(elements[i].getClassName())) {
			i++;
		}
		
		Class<?> badClass;
		try {
			badClass = Class.forName(elements[i].getClassName());
			return badClass.getSimpleName() + "." + elements[i].getMethodName();
		} 
		catch (ClassNotFoundException e) {
			return "?";
		}
	}

	public static ArrayList<String> GetClassNamesThatDontCount() {
		ArrayList<String> names = new ArrayList<String>();
		names.add(OopsieException.class.getName());
		names.add(Thread.class.getName());
		
		return names;
	}
}
