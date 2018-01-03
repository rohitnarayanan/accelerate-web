package accelerate;

import accelerate.config.DefaultProfileUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * This is a helper Java class that provides an alternative to creating a
 * web.xml. This will be invoked only when the application is deployed to a
 * servlet container like Tomcat, JBoss etc.
 * 
 * @version 1.0 Initial Version
 * @author JHipster
 * @since January 03, 2018
 */
public class ApplicationWebXml extends SpringBootServletInitializer {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.boot.web.support.SpringBootServletInitializer#configure(
	 * org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	/**
	 * @param aApplication
	 * @return
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder aApplication) {
		/**
		 * set a default to use when no profile is configured.
		 */
		DefaultProfileUtil.addDefaultProfile(aApplication.application());
		return aApplication.sources(AccelerateWebApp.class);
	}
}
