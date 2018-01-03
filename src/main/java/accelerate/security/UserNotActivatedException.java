package accelerate.security;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a not activated user trying to
 * authenticate.
 * 
 * @version 1.0 Initial Version
 * @author JHipster
 * @since January 03, 2018
 */
public class UserNotActivatedException extends AuthenticationException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param aMessage
	 */
	public UserNotActivatedException(String aMessage) {
		super(aMessage);
	}

	/**
	 * @param aMessage
	 * @param aError
	 */
	public UserNotActivatedException(String aMessage, Throwable aError) {
		super(aMessage, aError);
	}
}
