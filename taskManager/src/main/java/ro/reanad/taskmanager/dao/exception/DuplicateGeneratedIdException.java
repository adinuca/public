package ro.reanad.taskmanager.dao.exception;

public class DuplicateGeneratedIdException extends Exception {

	/**
	 * Exception is thrown if the generatedID for tasks is already in the
	 * database. A new generatedId is assigned to that task.
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
