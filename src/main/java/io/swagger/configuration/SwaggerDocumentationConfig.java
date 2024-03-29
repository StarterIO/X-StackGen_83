package io.swagger.configuration;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@javax.annotation.Generated(value = "io.starter.ignite.generator.swagger.languages.StackGenSpringCodegen", date = "2020-06-10T12:46:05.059-07:00")


@Configuration
@PropertySources({ @PropertySource("classpath:application.properties") })
public class SwaggerDocumentationConfig extends WebSecurityConfigurerAdapter {

	private final String			adminContextPath	= "";

	protected static final Logger	logger				= LoggerFactory
			.getLogger(SwaggerDocumentationConfig.class);

	@Value("${CORSOrigins:http://localhost:8100}")
	public String					CORSOrigins;

	@Value("${CORSMapping:/**}")
	public String					CORSMapping;

	/**
	 * the CORS configuration for the REST api
	 *
	 * @return
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		logger.warn("Init CORS Config Origins: CORSOrigins "
				+ CORSOrigins);
		logger.warn("Init CORS Config Mapping: CORSMapping "
				+ CORSMapping);
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addExposedHeader("X-Content-Type-Options");
		configuration.addExposedHeader("WWW-Authenticate");
		configuration.addExposedHeader("Access-Control-Allow-Origin");
		configuration.addExposedHeader("Access-Control-Allow-Headers");

		configuration.setAllowedOrigins(Arrays.asList(CORSOrigins));
		configuration.setAllowedMethods(Arrays
				.asList("GET", "POST", "INSERT", "DELETE", "HEAD", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(CORSMapping, configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        final SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");

		// TODO: CHANGE THIS TO ENABLE AUTH
		 http.cors().and().csrf().disable();

		/*
        http.authorizeRequests()
            .antMatchers(adminContextPath + "/assets/**").permitAll()
            .antMatchers(adminContextPath + "/login**").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).loginProcessingUrl("/login").and()
        .logout().logoutUrl(adminContextPath + "/logout").and()
        .httpBasic().and()
        .csrf().disable();
        */
        // @formatter:on
	}
	
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Starter StackGen API")
            .description("Starter StackGen API")
            .license("AGPL 3.0")
            .licenseUrl("https://www.gnu.org/licenses/agpl-3.0.html")
            .termsOfServiceUrl("")
            .version("1.0.4")
            .contact(new Contact("","", "info@StackGen.io"))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("io.starter.StackGen.api"))
                    .build()
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

}
