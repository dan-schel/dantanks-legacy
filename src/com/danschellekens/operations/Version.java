package com.danschellekens.operations;

/**
 * Stores a version number.
 * @author Dan2002s
 */
public class Version {

	int a;
	int b;
	int c;
	int d;
	
	int beta;
	
	/**
	 * Creates a version.
	 */
	public Version(int a, int b, int c, int d, int beta) {
		setA(a);
		setB(b);
		setC(c);
		setD(d);
		setBeta(beta);
	}

	/**
	 * Creates a version
	 */
	public Version(int a, int b, int c, int d) {
		this(a, b, c, d, 0);
	}
	
	/**
	 * Creates a version
	 */
	public Version(int a, int b, int c) {
		this(a, b, c, 0);
	}
	
	/**
	 * Creates a version
	 */
	public Version(int a, int b) {
		this(a, b, 0);
	}
	
	/**
	 * Creates a version
	 */
	public Version() {
		this(1, 0);
	}
	
	/**
	 * Sets and returns a version in beta to help in one line constructors.
	 */
	public Version Beta(int beta) {
		this.setBeta(beta);
		return this;
	}

	/**
	 * Returns the 'a' part of the version.
	 */
	public int getA() {
		return a;
	}

	/**
	 * Sets the 'a' part of the version.
	 */
	public void setA(int a) {
		this.a = Math.abs(a);
	}

	/**
	 * Returns the 'b' part of the version.
	 */
	public int getB() {
		return b;
	}

	/**
	 * Sets the 'b' part of the version.
	 */
	public void setB(int b) {
		this.b = Math.abs(b);
	}

	/**
	 * Returns the 'c' part of the version.
	 */
	public int getC() {
		return c;
	}

	/**
	 * Sets the 'c' part of the version.
	 */
	public void setC(int c) {
		this.c = Math.abs(c);
	}

	/**
	 * Returns the 'd' part of the version.
	 */
	public int getD() {
		return d;
	}

	/**
	 * Sets the 'd' part of the version.
	 */
	public void setD(int d) {
		this.d = Math.abs(d);
	}

	/**
	 * Returns the 'beta' part of the version.
	 * ('beta' value of 0 means not in beta)
	 */
	public int getBeta() {
		return beta;
	}
	
	/**
	 * Returns true is the version is in beta.
	 * ('beta' value of 0 means not in beta)
	 */
	public boolean isBeta() {
		return (beta != 0);
	}

	/**
	 * Sets the 'beta' part of the version.
	 * ('beta' value of 0 means not in beta)
	 */
	public void setBeta(int beta) {
		this.beta = Math.abs(beta);
	}
	
	/**
	 * Returns the version in string form.<br>
	 * Eg: "v1.0.2 (Beta 2)"
	 */
	@Override
	public String toString() {
		String result = "v" + getDottedNumbers() + getBetaSuffix();
		
		return result;
	}
	
	/**
	 * Returns the same result as {@link #toString toString()}
	 * @return
	 */
	public String toStringNoV() {
		String result = getDottedNumbers() + getBetaSuffix();
		
		return result;
	}
	
	/**
	 * Returns the version in the string form "x.x.x.xbx"
	 */
	public String toStringTechnical() {
		String result = getDottedNumbersInFull() + getBetaSuffixTechnical();
		
		return result;
	}
	
	/**
	 * Returns the version in the string form "x.x.x.x", but simplified so that
	 * Version 1.0 would be written as "1.0" instead of "1.0.0.0"
	 */
	String getDottedNumbers() {
		String result = String.valueOf(a) + "." + String.valueOf(b);
		
		if (d == 0) {
			if (c != 0) {
				result += "." + String.valueOf(c);
			}
		}
		else {
			result += "." + String.valueOf(c);
			result += "." + String.valueOf(d);
		}
		
		return result;
	}
	
	/**
	 * Returns the version in the string form "x.x.x.x".
	 */
	String getDottedNumbersInFull() {
		String result = String.valueOf(a)
				+ "." + String.valueOf(b)
				+ "." + String.valueOf(c)
				+ "." + String.valueOf(d);
		return result;
	}
	
	/**
	 * Returns the beta suffix " (Beta x)" if the version is beta, or "" otherwise.
	 */
	String getBetaSuffix() {
		if (isBeta()) {
			return " (Beta " + String.valueOf(beta) + ")";
		}
		return "";
	}
	
	/**
	 * Returns the beta suffix "bx" if the version is beta, or "" otherwise.
	 */
	String getBetaSuffixTechnical() {
		if (isBeta()) {
			return "b" + String.valueOf(beta);
		}
		return "";
	}

	/**
	 * Returns true if this version is newer than the input.
	 */
	public boolean isNewerThan(Version input) {
		return Newer(input, this);
	}
	
	/**
	 * Returns true if this version is older than the input.
	 */
	public boolean isOlderThan(Version input) {
		return Older(input, this);
	}
	
	/**
	 * Returns true if this version is equal to the input.
	 */
	public boolean isEqualTo(Version input) {
		return Equal(input, this);
	}
	
	/**
	 * Returns the version object that the string represented.<br>
	 * String should be in the "[v]X.X[.X][.X] [(Beta X)]" format.
	 * @throws Exception 
	 */
	public static Version parse(String input) throws Exception {
		if (!validVersionString(input)) { 
			throw new Exception("Invalid version string: " + input); 
		}
		
		String versionString = parseFormat(input);
		String[] segments = versionString.split("b");
		String[] numbers = segments[0].split("\\.");
		
		int a = Integer.valueOf(numbers[0]);
		int b = Integer.valueOf(numbers[1]);
		int c = numbers.length > 2 ? Integer.valueOf(numbers[2]) : 0;
		int d = numbers.length > 3 ? Integer.valueOf(numbers[3]) : 0;
		int beta = 0;
		
		if (segments.length > 1) {
			String betaNumber = segments[1].replace("b", "");
			beta = Integer.valueOf(betaNumber);
		}
		
		return new Version(a, b, c, d, beta);
	}
	
	/**
	 * Returns the input but in a standard format that a parser can
	 * understand.
	 */
	static String parseFormat(String input) {
		String versionString = input.toLowerCase();
		versionString = versionString.replace("v", "");
		versionString = versionString.replace("(", "");
		versionString = versionString.replace(")", "");
		versionString = versionString.replace("beta", "b");
		versionString = versionString.replace(" ", "");
		
		return versionString;
	}
	
	/**
	 * Returns true if the version string is valid.
	 */
	public static boolean validVersionString(String versionString) {
		return versionString.matches("^ *[Vv]?([0-9]+\\.){1,3}[0-9]+( ?\\(?[Bb]([Ee][Tt][Aa])? ?[0-9]+\\)?|) *$");
	}
	
	/**
	 * Returns true if subject is newer than original.
	 */
	public static boolean Equal(Version original, Version subject) {
		return (subject.getA() == original.getA() &&
			subject.getB() == original.getB() &&
			subject.getC() == original.getC() &&
			subject.getD() == original.getD() &&
			subject.getBeta() == original.getBeta());
	}
	
	/**
	 * Returns true if subject is newer than original.
	 */
	public static boolean Newer(Version original, Version subject) {
		if (subject.getA() > original.getA()) { return true; }
		if (subject.getA() < original.getA()) { return false; }
		
		if (subject.getB() > original.getB()) { return true; }
		if (subject.getB() < original.getB()) { return false; }
		
		if (subject.getC() > original.getC()) { return true; }
		if (subject.getC() < original.getC()) { return false; }
		
		if (subject.getD() > original.getD()) { return true; }
		if (subject.getD() < original.getD()) { return false; }
		
		if (!subject.isBeta() && original.isBeta()) { return true; }
		if (subject.isBeta() && !original.isBeta()) { return false; }
		if (subject.getBeta() > original.getBeta()) { return true; }
		if (subject.getBeta() < original.getBeta()) { return false; }
		
		return false;
	}
	
	/**
	 * Returns true if subject is older than original.
	 */
	public static boolean Older(Version original, Version subject) {
		return (Newer(subject, original) && !Equal(original, subject));
	}
}
