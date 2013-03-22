package ro.reanad.taskmanager.dao.exception;

public class DuplicateUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4514808919596016128L;
	
	public DuplicateUserException(String message, Throwable throwable) {
		super(message,throwable);
	}

}
