package com.danschellekens.dantanks.exceptions;

public class MissingPlayerException extends Exception {
	private static final long serialVersionUID = 4981837224003429666L;

	public MissingPlayerException(String message) {
		super(message);
	}
}
