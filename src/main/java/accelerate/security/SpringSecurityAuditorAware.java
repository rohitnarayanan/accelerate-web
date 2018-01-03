package accelerate.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import accelerate.config.Constants;

/**
 * Implementation of AuditorAware based on Spring Security.
 * 
 * @version 1.0 Initial Version
 * @author JHipster
 * @since January 03, 2018
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
	 */
	/**
	 * @return
	 */
	@Override
	public String getCurrentAuditor() {
		return SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT);
	}
}
