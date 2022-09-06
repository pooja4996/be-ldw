package com.wsa.app.constant;

public class ExceptionMessage {

	private ExceptionMessage() {

	}

	public static final String ACCOUNT_LOCKED = "Your account has been locked. please contact administration";
	public static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
	public static final String INCORRECT_CRENDENTIALS = "Ungültiger Benutzername oder ungültiges Passwort.";
	public static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
	public static final String ERROR_PROCESSING_FILE = "Error occured while processing file";
	public static final String NOT_ENOUGH_PERMISSION = "Sie haben nicht genug Erlaubnis.";
	public static final String NOT_FOUND = "Dienst von der angegebenen ID nicht gefunden.";
	public static final String BUCKET_ALREADY_EXIST = "Bucket Name existiert bereits";
	public static final String FILE_NOT_EXIST = "Die Datei existiert nicht.";
	public static final String DEFAULT_NOT_FOUND_MESSAGE = "Resource not found";
	public static final String DEFAULT_BAD_REQUEST_MESSAGE = "Bad Request";
	public static final String USER_NOT_FOUND = "User not found by given username";
}
