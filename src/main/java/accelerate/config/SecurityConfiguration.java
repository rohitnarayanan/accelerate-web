package accelerate.config;

import accelerate.security.*;
import accelerate.security.jwt.*;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.annotation.PostConstruct;

/**
 * {@link Configuration} class for security settings
 * 
 * @version 1.0 Initial Version
 * @author JHipster
 * @since January 03, 2018
 */
@Configuration
@Import(SecurityProblemSupport.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	/**
	 * {@link AuthenticationManagerBuilder} instance
	 */
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	/**
	 * {@link UserDetailsService} instance
	 */
	private final UserDetailsService userDetailsService;

	/**
	 * {@link TokenProvider} instance
	 */
	private final TokenProvider tokenProvider;

	/**
	 * {@link CorsFilter} instance
	 */
	private final CorsFilter corsFilter;

	/**
	 * {@link SecurityProblemSupport} instance
	 */
	private final SecurityProblemSupport problemSupport;

	/**
	 * @param aAuthenticationManagerBuilder
	 * @param aUserDetailsService
	 * @param aTokenProvider
	 * @param aCorsFilter
	 * @param aProblemSupport
	 */
	public SecurityConfiguration(AuthenticationManagerBuilder aAuthenticationManagerBuilder,
			UserDetailsService aUserDetailsService, TokenProvider aTokenProvider, CorsFilter aCorsFilter,
			SecurityProblemSupport aProblemSupport) {
		this.authenticationManagerBuilder = aAuthenticationManagerBuilder;
		this.userDetailsService = aUserDetailsService;
		this.tokenProvider = aTokenProvider;
		this.corsFilter = aCorsFilter;
		this.problemSupport = aProblemSupport;
	}

	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		try {
			this.authenticationManagerBuilder.userDetailsService(this.userDetailsService)
					.passwordEncoder(passwordEncoder());
		} catch (Exception e) {
			throw new BeanInitializationException("Security configuration failed", e);
		}
	}

	/**
	 * @return
	 */
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.config.
	 * annotation.web.builders.WebSecurity)
	 */
	/**
	 * @param web
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/app/**/*.{js,html}").antMatchers("/i18n/**")
				.antMatchers("/content/**").antMatchers("/swagger-ui/index.html").antMatchers("/test/**")
				.antMatchers("/h2-console/**");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.config.
	 * annotation.web.builders.HttpSecurity)
	 */
	/**
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(this.corsFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling()
				.authenticationEntryPoint(this.problemSupport).accessDeniedHandler(this.problemSupport).and().csrf()
				.disable().headers().frameOptions().disable().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/register").permitAll().antMatchers("/api/activate").permitAll()
				.antMatchers("/api/authenticate").permitAll().antMatchers("/api/account/reset-password/init")
				.permitAll().antMatchers("/api/account/reset-password/finish").permitAll()
				.antMatchers("/api/profile-info").permitAll().antMatchers("/api/**").authenticated()
				.antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/websocket/**")
				.permitAll().antMatchers("/management/health").permitAll().antMatchers("/management/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers("/swagger-resources/configuration/ui").permitAll().antMatchers("/swagger-ui/index.html")
				.hasAuthority(AuthoritiesConstants.ADMIN).and().apply(securityConfigurerAdapter());

	}

	/**
	 * @return
	 */
	private JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(this.tokenProvider);
	}

	/**
	 * @return
	 */
	@Bean
	public static SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}
}
