package accelerate.config;

import java.io.File;
import java.nio.file.Paths;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.MetricsServlet;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.web.filter.CachingHttpHeadersFilter;
import io.undertow.UndertowOptions;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, EmbeddedServletContainerCustomizer {
	/**
	 * {@link Logger} instance
	 */
	private static final Logger _logger = LoggerFactory.getLogger(WebConfigurer.class);

	/**
	 * {@link Environment} instance
	 */
	private final Environment environment;

	/**
	 * {@link JHipsterProperties} instance
	 */
	private final JHipsterProperties jHipsterProperties;

	/**
	 * {@link MetricRegistry} instance
	 */
	private MetricRegistry metricRegistry;

	/**
	 * @param aEnvironment
	 * @param aJHipsterProperties
	 */
	public WebConfigurer(Environment aEnvironment, JHipsterProperties aJHipsterProperties) {

		this.environment = aEnvironment;
		this.jHipsterProperties = aJHipsterProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.boot.web.servlet.ServletContextInitializer#onStartup(
	 * javax.servlet.ServletContext)
	 */
	/**
	 * @param servletContext
	 */
	@Override
	public void onStartup(ServletContext servletContext) {
		if (this.environment.getActiveProfiles().length != 0) {
			_logger.info("Web application configuration, using profiles: {}",
					(Object[]) this.environment.getActiveProfiles());
		}

		EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD,
				DispatcherType.ASYNC);
		initMetrics(servletContext, disps);

		if (this.environment.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
			initCachingHttpHeadersFilter(servletContext, disps);
		}

		if (this.environment.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)) {
			initH2Console(servletContext);
		}

		_logger.info("Web application fully configured");
	}

	/**
	 * Customize the Servlet engine: Mime types, the document root, the cache.
	 */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		// IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
		mappings.add("html", MediaType.TEXT_HTML_VALUE + ";charset=utf-8");
		// CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
		mappings.add("json", MediaType.TEXT_HTML_VALUE + ";charset=utf-8");
		container.setMimeMappings(mappings);
		// When running in an IDE or with ./gradlew bootRun, set location of the static
		// web assets.
		setLocationForStaticAssets(container);

		/*
		 * Enable HTTP/2 for Undertow -
		 * https://twitter.com/ankinson/status/829256167700492288 HTTP/2 requires HTTPS,
		 * so HTTP requests will fallback to HTTP/1.1. See the JHipsterProperties class
		 * and your application-*.yml configuration files for more information.
		 */
		if (this.jHipsterProperties.getHttp().getVersion().equals(JHipsterProperties.Http.Version.V_2_0)
				&& container instanceof UndertowEmbeddedServletContainerFactory) {

			((UndertowEmbeddedServletContainerFactory) container)
					.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
		}
	}

	/**
	 * @param aContainer
	 */
	private void setLocationForStaticAssets(ConfigurableEmbeddedServletContainer aContainer) {
		File root;
		String prefixPath = resolvePathPrefix();
		root = new File(prefixPath + "build/www/");
		if (root.exists() && root.isDirectory()) {
			aContainer.setDocumentRoot(root);
		}
	}

	/**
	 * Resolve path prefix to static resources.
	 * 
	 * @return
	 */
	private String resolvePathPrefix() {
		String fullExecutablePath = this.getClass().getResource("").getPath();
		String rootPath = Paths.get(".").toUri().normalize().getPath();
		String extractedPath = fullExecutablePath.replace(rootPath, "");
		int extractionEndIndex = extractedPath.indexOf("build/");
		if (extractionEndIndex <= 0) {
			return "";
		}
		return extractedPath.substring(0, extractionEndIndex);
	}

	/**
	 * Initializes the caching HTTP Headers Filter.
	 * 
	 * @param aServletContext
	 * @param aDispatcherTypes
	 */
	private void initCachingHttpHeadersFilter(ServletContext aServletContext,
			EnumSet<DispatcherType> aDispatcherTypes) {
		_logger.debug("Registering Caching HTTP Headers Filter");
		FilterRegistration.Dynamic cachingHttpHeadersFilter = aServletContext.addFilter("cachingHttpHeadersFilter",
				new CachingHttpHeadersFilter(this.jHipsterProperties));

		cachingHttpHeadersFilter.addMappingForUrlPatterns(aDispatcherTypes, true, "/content/*");
		cachingHttpHeadersFilter.addMappingForUrlPatterns(aDispatcherTypes, true, "/app/*");
		cachingHttpHeadersFilter.setAsyncSupported(true);
	}

	/**
	 * Initializes Metrics.
	 * 
	 * @param aServletContext
	 * @param aDispatcherTypes
	 */
	private void initMetrics(ServletContext aServletContext, EnumSet<DispatcherType> aDispatcherTypes) {
		_logger.debug("Initializing Metrics registries");
		aServletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, this.metricRegistry);
		aServletContext.setAttribute(MetricsServlet.METRICS_REGISTRY, this.metricRegistry);

		_logger.debug("Registering Metrics Filter");
		FilterRegistration.Dynamic metricsFilter = aServletContext.addFilter("webappMetricsFilter",
				new InstrumentedFilter());

		metricsFilter.addMappingForUrlPatterns(aDispatcherTypes, true, "/*");
		metricsFilter.setAsyncSupported(true);

		_logger.debug("Registering Metrics Servlet");
		ServletRegistration.Dynamic metricsAdminServlet = aServletContext.addServlet("metricsServlet",
				new MetricsServlet());

		metricsAdminServlet.addMapping("/management/metrics/*");
		metricsAdminServlet.setAsyncSupported(true);
		metricsAdminServlet.setLoadOnStartup(2);
	}

	/**
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = this.jHipsterProperties.getCors();
		if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
			_logger.debug("Registering CORS filter");
			source.registerCorsConfiguration("/api/**", config);
			source.registerCorsConfiguration("/management/**", config);
			source.registerCorsConfiguration("/v2/api-docs", config);
		}
		return new CorsFilter(source);
	}

	/**
	 * Initializes H2 console.
	 * 
	 * @param aServletContext
	 */
	private static void initH2Console(ServletContext aServletContext) {
		_logger.debug("Initialize H2 console");
		try {
			// We don't want to include H2 when we are packaging for the "prod" profile and
			// won't
			// actually need it, so we have to load / invoke things at runtime through
			// reflection.
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Class<?> servletClass = Class.forName("org.h2.server.web.WebServlet", true, loader);
			Servlet servlet = (Servlet) servletClass.newInstance();

			ServletRegistration.Dynamic h2ConsoleServlet = aServletContext.addServlet("H2Console", servlet);
			h2ConsoleServlet.addMapping("/h2-console/*");
			h2ConsoleServlet.setInitParameter("-properties", "src/main/resources/");
			h2ConsoleServlet.setLoadOnStartup(1);

		} catch (ClassNotFoundException | LinkageError e) {
			throw new RuntimeException("Failed to load and initialize org.h2.server.web.WebServlet", e);

		} catch (IllegalAccessException | InstantiationException e) {
			throw new RuntimeException("Failed to instantiate org.h2.server.web.WebServlet", e);
		}
	}

	/**
	 * @param aMetricRegistry
	 */
	@Autowired(required = false)
	public void setMetricRegistry(MetricRegistry aMetricRegistry) {
		this.metricRegistry = aMetricRegistry;
	}
}
