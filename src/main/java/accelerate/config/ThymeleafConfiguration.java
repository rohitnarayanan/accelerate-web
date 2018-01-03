package accelerate.config;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * {@link Configuration} class for Thymeleaf settings
 * 
 * @version 1.0 Initial Version
 * @author JHipster
 * @since January 03, 2018
 */
@Configuration
public class ThymeleafConfiguration {
	/**
	 * @return
	 */
	@Bean
	@Description("Thymeleaf template resolver serving HTML 5 emails")
	public static ClassLoaderTemplateResolver emailTemplateResolver() {
		ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
		emailTemplateResolver.setPrefix("mails/");
		emailTemplateResolver.setSuffix(".html");
		emailTemplateResolver.setTemplateMode("HTML5");
		emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
		emailTemplateResolver.setOrder(1);
		return emailTemplateResolver;
	}
}
