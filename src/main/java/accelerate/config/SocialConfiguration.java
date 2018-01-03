package accelerate.config;

import accelerate.repository.SocialUserConnectionRepository;
import accelerate.repository.CustomSocialUsersConnectionRepository;
import accelerate.security.jwt.TokenProvider;
import accelerate.security.social.CustomSignInAdapter;

import io.github.jhipster.config.JHipsterProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
// jhipster-needle-add-social-connection-factory-import-package

/**
 * Basic Spring Social configuration.
 *
 * <p>
 * Creates the beans necessary to manage Connections to social services and link
 * accounts from those services to internal Users.
 */
@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {
	/**
	 * 
	 */
	private static final Logger _logger = LoggerFactory.getLogger(SocialConfiguration.class);

	/**
	 * 
	 */
	private final SocialUserConnectionRepository socialUserConnectionRepository;

	/**
	 * 
	 */
	private final Environment environment;

	/**
	 * @param aSocialUserConnectionRepository
	 * @param aEnvironment
	 */
	public SocialConfiguration(SocialUserConnectionRepository aSocialUserConnectionRepository,
			Environment aEnvironment) {

		this.socialUserConnectionRepository = aSocialUserConnectionRepository;
		this.environment = aEnvironment;
	}

	/**
	 * @param aConnectionFactoryLocator
	 * @param aConnectionRepository
	 * @return
	 */
	@Bean
	public ConnectController connectController(ConnectionFactoryLocator aConnectionFactoryLocator,
			ConnectionRepository aConnectionRepository) {

		ConnectController controller = new ConnectController(aConnectionFactoryLocator, aConnectionRepository);
		controller.setApplicationUrl(this.environment.getProperty("spring.application.url"));
		return controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.social.config.annotation.SocialConfigurer#
	 * addConnectionFactories(org.springframework.social.config.annotation.
	 * ConnectionFactoryConfigurer, org.springframework.core.env.Environment)
	 */
	/**
	 * @param aConnectionFactoryConfigurer
	 * @param aEnvironment
	 */
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer aConnectionFactoryConfigurer,
			Environment aEnvironment) {
		// Google configuration
		String googleClientId = aEnvironment.getProperty("spring.social.google.client-id");
		String googleClientSecret = aEnvironment.getProperty("spring.social.google.client-secret");
		if (googleClientId != null && googleClientSecret != null) {
			_logger.debug("Configuring GoogleConnectionFactory");
			aConnectionFactoryConfigurer
					.addConnectionFactory(new GoogleConnectionFactory(googleClientId, googleClientSecret));
		} else {
			_logger.error("Cannot configure GoogleConnectionFactory id or secret null");
		}

		// Facebook configuration
		String facebookClientId = aEnvironment.getProperty("spring.social.facebook.client-id");
		String facebookClientSecret = aEnvironment.getProperty("spring.social.facebook.client-secret");
		if (facebookClientId != null && facebookClientSecret != null) {
			_logger.debug("Configuring FacebookConnectionFactory");
			aConnectionFactoryConfigurer
					.addConnectionFactory(new FacebookConnectionFactory(facebookClientId, facebookClientSecret));
		} else {
			_logger.error("Cannot configure FacebookConnectionFactory id or secret null");
		}

		// Twitter configuration
		String twitterClientId = aEnvironment.getProperty("spring.social.twitter.client-id");
		String twitterClientSecret = aEnvironment.getProperty("spring.social.twitter.client-secret");
		if (twitterClientId != null && twitterClientSecret != null) {
			_logger.debug("Configuring TwitterConnectionFactory");
			aConnectionFactoryConfigurer
					.addConnectionFactory(new TwitterConnectionFactory(twitterClientId, twitterClientSecret));
		} else {
			_logger.error("Cannot configure TwitterConnectionFactory id or secret null");
		}

		// jhipster-needle-add-social-connection-factory
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.social.config.annotation.SocialConfigurer#getUserIdSource
	 * ()
	 */
	/**
	 * @return
	 */
	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.social.config.annotation.SocialConfigurer#
	 * getUsersConnectionRepository(org.springframework.social.connect.
	 * ConnectionFactoryLocator)
	 */
	/**
	 * @param connectionFactoryLocator
	 * @return
	 */
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return new CustomSocialUsersConnectionRepository(this.socialUserConnectionRepository, connectionFactoryLocator);
	}

	/**
	 * @param aUserDetailsService
	 * @param aJHipsterProperties
	 * @param aTokenProvider
	 * @return
	 */
	@Bean
	public static SignInAdapter signInAdapter(UserDetailsService aUserDetailsService,
			JHipsterProperties aJHipsterProperties, TokenProvider aTokenProvider) {
		return new CustomSignInAdapter(aUserDetailsService, aJHipsterProperties, aTokenProvider);
	}

	/**
	 * @param aConnectionFactoryLocator
	 * @param aUsersConnectionRepository
	 * @param signInAdapter
	 * @return
	 */
	@Bean
	public ProviderSignInController providerSignInController(ConnectionFactoryLocator aConnectionFactoryLocator,
			UsersConnectionRepository aUsersConnectionRepository, SignInAdapter signInAdapter) {
		ProviderSignInController providerSignInController = new ProviderSignInController(aConnectionFactoryLocator,
				aUsersConnectionRepository, signInAdapter);
		providerSignInController.setSignUpUrl("/social/signup");
		providerSignInController.setApplicationUrl(this.environment.getProperty("spring.application.url"));
		return providerSignInController;
	}

	/**
	 * @param aConnectionFactoryLocator
	 * @param aUsersConnectionRepository
	 * @return
	 */
	@Bean
	public static ProviderSignInUtils getProviderSignInUtils(ConnectionFactoryLocator aConnectionFactoryLocator,
			UsersConnectionRepository aUsersConnectionRepository) {
		return new ProviderSignInUtils(aConnectionFactoryLocator, aUsersConnectionRepository);
	}
}
